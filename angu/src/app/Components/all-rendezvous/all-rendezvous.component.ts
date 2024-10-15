import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service';
import { UserService } from '../../Services/user.service'; // Import UserService
import { RendezVous } from '../../Modules/RendezVous';
import { Utilisateur } from '../../Modules/Utilisateur'; // Import Utilisateur for Jardinier
import { AuthenticationService } from '../../Services/auth.service';

@Component({
  selector: 'app-all-rendezvous',
  templateUrl: './all-rendezvous.component.html',
  styleUrls: ['./all-rendezvous.component.css']
})
export class AllRendezvousComponent implements OnInit {
  rendezVous: RendezVous[] = [];
  filteredRendezVous: RendezVous[] = [];
  searchTerm: string = '';
  userRole: string | null = null;
  userId: number = 0; 
  jardiniers: Utilisateur[] = []; // To store Jardiniers
  selectedJardinierId: number | null = null; // To hold the selected Jardinier ID

  constructor(
    private rendezVousService: RendezVousService,
    private userService: UserService, // Inject UserService
    private router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.loadUserInfo();
    this.loadRendezVous();
    this.loadJardiniers(); // Load Jardiniers
  }

  loadJardiniers(): void {
    this.userService.getAllJardiniers().subscribe({
      next: (data) => {
        this.jardiniers = data; // Load the list of Jardiniers
      },
      error: (err) => {
        console.error('Error fetching Jardiniers', err);
      }
    });
  }

  loadUserInfo(): void {
    const jwt = localStorage.getItem('jwt');
    const role = localStorage.getItem('role');
    const id = localStorage.getItem('id');

    if (jwt && role && id) {
        this.userRole = role;
        this.userId = Number(id); // Convert the ID from string to number
    } else {
        this.router.navigate(['/login']); 
    }
}

loadRendezVous(): void {
  if (this.userRole === 'Client') {
      this.rendezVousService.getRendezVousByClient(this.userId).subscribe(
          (data) => {
              this.rendezVous = data;
              this.filteredRendezVous = data;
          },
          (error) => {
              console.error('Error fetching client rendezvous:', error);
          }
      );
  } else if (this.userRole === 'Jardinier') {
      this.rendezVousService.getRendezVousByJardinier(this.userId).subscribe(
          (data) => {
              this.rendezVous = data;
              this.filteredRendezVous = data;
          },
          (error) => {
              console.error('Error fetching jardinier rendezvous:', error);
          }
      );
  } else if (this.userRole === 'ADMIN') {
      this.rendezVousService.getAllRendezVous().subscribe(
          (data) => {
              this.rendezVous = data;
              this.filteredRendezVous = data;
          },
          (error) => {
              console.error('Error fetching all rendezvous:', error);
          }
      );
  }
}

assignJardinier(rendezVousId: number): void {
  if (this.selectedJardinierId !== null) {
    this.rendezVousService.assignJardinierToRendezVous(rendezVousId, this.selectedJardinierId).subscribe(
      (response) => {
        console.log('Jardinier assigned successfully:', response);
        this.loadRendezVous(); // Reload the rendezvous list to reflect the changes
        this.selectedJardinierId = null; // Reset the selected Jardinier ID
      },
      (error) => {
        console.error('Error assigning jardinier:', error);
      }
    );
  } else {
    alert('Veuillez sélectionner un Jardinier.');
  }
}


  deleteRendezVous(id: number): void {
    if (confirm('Are you sure you want to delete this rendezvous?')) {
      this.rendezVousService.deleteRendezVous(id).subscribe(
        () => {
          this.rendezVous = this.rendezVous.filter(rdv => rdv.idRendezVous !== id);
          this.searchRendezVous();
        },
        (error) => {
          console.error('Error deleting rendezvous', error);
        }
      );
    }
  }

  editRendezVous(id: number): void {
    this.router.navigate(['/rendezvous/update', id]);
  }

  details(id: number): void {
    this.router.navigate(['/rendezvous/details', id]);
  }

  searchRendezVous(): void {
    if (this.searchTerm === '') {
      this.filteredRendezVous = this.rendezVous;
    } else {
      this.filteredRendezVous = this.rendezVous.filter(rdv =>
        rdv.lieu.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
        rdv.date.toLocaleString().includes(this.searchTerm)
      );
    }
  }
}
