import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service';
import { TacheService } from '../../Services/tache.service';
import { UserService } from '../../Services/user.service'; // Import UserService
import { Tache } from '../../Modules/Tache';
import { Utilisateur } from '../../Modules/Utilisateur'; // Import Utilisateur

@Component({
  selector: 'app-add-rendez-vous',
  templateUrl: './addrendez-vous.component.html',
  styleUrls: ['./addrendez-vous.component.css']
})
export class AddrendezVousComponent implements OnInit {

  rendezVousForm: FormGroup;
  isEditMode: boolean = false;
  isDetailMode: boolean = false; // New variable for detail mode
  rendezVousId: number | null = null;
  clientId: number | null = null; 
  isAdmin: boolean = false; 
  isJardinier: boolean = false; 
  taches: Tache[] = []; 
  jardiniers: Utilisateur[] = []; // Array to hold fetched jardiniers

  constructor(
    private fb: FormBuilder,
    private rendezVousService: RendezVousService,
    private tacheService: TacheService,
    private userService: UserService, // Inject UserService
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.rendezVousForm = this.fb.group({
      heure: ['', Validators.required],
      date: ['', Validators.required],
      lieu: ['', Validators.required],
      statutRendezVous: ['', Validators.required],
      idtache: [null, Validators.required],
      idjardinier: [null] 
    });
  }

  ngOnInit(): void {
    this.loadClientId();
    this.checkUserRole(); 
    this.checkEditMode();
    this.loadTaches(); 
    this.loadJardiniers(); // Load jardiniers on component initialization
  }

  loadTaches(): void {
    this.tacheService.getAllTaches().subscribe((taches: Tache[]) => {
      this.taches = taches; 
    }, (error: any) => {
      console.error('Error fetching tasks:', error);
    });
  }

  loadJardiniers(): void {
    this.userService.getAllJardiniers().subscribe((jardiniers: Utilisateur[]) => {
      this.jardiniers = jardiniers; // Store fetched jardiniers in the array
    }, (error: any) => {
      console.error('Error fetching jardiniers:', error);
    });
  }

  loadClientId(): void {
    const id = localStorage.getItem('id'); 
    if (id) {
      this.clientId = Number(id);
    } else {
      console.error('Client ID is null. Unable to create a rendezvous.');
      this.router.navigate(['/login']); 
    }
  }

  checkUserRole(): void {
    const role = localStorage.getItem('role'); 
    if (role === 'ADMIN') {
      this.isAdmin = true;
    } else if (role === 'JARDINIER') {
      this.isJardinier = true;
    }
  }
  
  checkEditMode(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.rendezVousId = +params['id'];
        this.isEditMode = true;
        this.rendezVousService.getRendezVousById(this.rendezVousId).subscribe(rendezVous => {
          this.rendezVousForm.patchValue(rendezVous);
        });
      } else if (params['detail']) { // Check for detail mode
        this.rendezVousId = +params['detail'];
        this.isDetailMode = true; // Set detail mode
        this.rendezVousService.getRendezVousById(this.rendezVousId).subscribe(rendezVous => {
          this.rendezVousForm.patchValue(rendezVous);
          this.rendezVousForm.disable(); // Disable the form for details view
        });
      }
    });
  }

  onSubmit(): void {
    if (this.rendezVousForm.invalid) {
      return; 
    }

    let heureValue = this.rendezVousForm.get('heure')?.value;

    if (heureValue && !heureValue.includes(':')) {
      heureValue = `${heureValue}:00`;
    } else if (heureValue && heureValue.split(':').length === 2) {
      heureValue = `${heureValue}:00`;
    }

    this.rendezVousForm.patchValue({ heure: heureValue });

    if (this.clientId !== null) {
      const idClient: number = this.clientId; 

      this.rendezVousService.createRendezVous(this.rendezVousForm.value, idClient).subscribe(() => {
        this.router.navigate(['/rendezvous']); 
      }, (error) => {
        console.error('Erreur lors de la création du rendez-vous :', error);
      });
    } else {
      console.error("Client ID is null. Unable to create a rendezvous.");
    }
  }

  // New method for navigating back to the list
  goBackToList(): void {
    this.router.navigate(['/taches']); // Navigate to the list of taches
  }
}
