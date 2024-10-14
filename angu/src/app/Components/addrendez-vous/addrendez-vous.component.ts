import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service'; // Adjust the path if necessary
import { TacheService } from '../../Services/tache.service'; // Make sure this import path is correct
import { Tache } from '../../Modules/Tache'; // Adjust the path if necessary

@Component({
  selector: 'app-add-rendez-vous',
  templateUrl: './addrendez-vous.component.html',
  styleUrls: ['./addrendez-vous.component.css']
})
export class AddrendezVousComponent implements OnInit {

  rendezVousForm: FormGroup;
  isEditMode: boolean = false;
  rendezVousId: number | null = null;
  clientId: number | null = null; // Variable to hold the client ID
  isAdmin: boolean = false; // Variable to check if user is Admin
  isJardinier: boolean = false; // Variable to check if user is Jardinier
  taches: Tache[] = []; // Array to hold the fetched tasks


  constructor(
    private fb: FormBuilder,
    private rendezVousService: RendezVousService,
    private tacheService: TacheService, // Ensure this line exists
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize the form with validators
    this.rendezVousForm = this.fb.group({
      heure: ['', Validators.required],
      date: ['', Validators.required],
      lieu: ['', Validators.required],
      statutRendezVous: ['', Validators.required],
      idtache: [null, Validators.required],
      idjardinier: [null] // Notez que ce champ n'est pas requis pour le client
    });
  }

  ngOnInit(): void {
    this.loadClientId();
    this.checkUserRole(); // Vérifiez le rôle de l'utilisateur
    this.checkEditMode();
    this.loadTaches(); // Load tasks on component initialization
  }

  loadTaches(): void {
    this.tacheService.getAllTaches().subscribe((taches: Tache[]) => {
      this.taches = taches; // Store fetched tasks in the array
    }, (error: any) => {
      console.error('Error fetching tasks:', error);
    });
  }

  loadClientId(): void {
    const id = localStorage.getItem('id'); // Assurez-vous que l'ID client est récupéré
    if (id) {
      this.clientId = Number(id);
    } else {
      console.error('Client ID is null. Unable to create a rendezvous.');
      this.router.navigate(['/login']); // Rediriger vers la page de connexion
    }
  }

  checkUserRole(): void {
    const role = localStorage.getItem('role'); // Supposons que vous stockez le rôle de l'utilisateur dans localStorage
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
      }
    });
  }

  onSubmit(): void {
    if (this.rendezVousForm.invalid) {
      return; // Si la validation du formulaire échoue, on arrête
    }

    // Récupérer la valeur actuelle de 'heure' depuis le formulaire
    let heureValue = this.rendezVousForm.get('heure')?.value;

    // Si la valeur de 'heure' n'inclut pas déjà les secondes, on ajoute ':00'
    if (heureValue && !heureValue.includes(':')) {
      heureValue = `${heureValue}:00`;
    } else if (heureValue && heureValue.split(':').length === 2) {
      heureValue = `${heureValue}:00`; // ajouter les secondes si elles manquent
    }

    // Mettre à jour le formulaire avec la valeur modifiée de 'heure'
    this.rendezVousForm.patchValue({ heure: heureValue });

    // Vérifier si l'idClient est bien défini et non null
    if (this.clientId !== null) {
      const idClient: number = this.clientId; // S'assurer que idClient est un nombre

      // Création du nouveau rendez-vous
      this.rendezVousService.createRendezVous(this.rendezVousForm.value, idClient).subscribe(() => {
        this.router.navigate(['/rendezvous']); // Redirection après la création réussie
      }, (error) => {
        console.error('Erreur lors de la création du rendez-vous :', error);
      });
    } else {
      console.error("Client ID is null. Unable to create a rendezvous.");
    }
  }
}
