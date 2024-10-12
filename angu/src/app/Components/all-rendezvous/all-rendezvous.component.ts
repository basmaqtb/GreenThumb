import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service'; // Adjust the path if necessary
import { RendezVous } from '../../Modules/RendezVous'; // Adjust the path if necessary
import { AuthenticationService } from '../../Services/auth.service'; // Import the AuthService to access user info
import { Time } from '@angular/common';

@Component({
  selector: 'app-all-rendezvous',
  templateUrl: './all-rendezvous.component.html',
  styleUrls: ['./all-rendezvous.component.css']
})
export class AllRendezvousComponent implements OnInit {
  rendezVous: RendezVous[] = [];
  filteredRendezVous: RendezVous[] = [];
  searchTerm: string = '';
  userRole: string = ''; // To hold the user's role
  userId: number = 0; // To hold the user's ID

  constructor(private rendezVousService: RendezVousService, private router: Router, private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.loadUserInfo();
    this.loadRendezVous();
  }

  loadUserInfo(): void {
    const user = this.authService.getCurrentUser(); // Fetch user info
    if (user) {
      this.userRole = user.role; // Set role
      this.userId = user.id;     // Set ID
    } else {
      console.error('No user is logged in or user data is missing.');
      // Optionally, you can redirect to the login page or show a message to the user
      this.router.navigate(['/login']); // Redirect to login if needed
    }
  }

  // Load rendezvous based on user role
  loadRendezVous(): void {
    if (this.userRole === 'CLIENT') {
      this.rendezVousService.getRendezVousByClient(this.userId).subscribe(
        (data) => {
          this.rendezVous = data;
          this.filteredRendezVous = data;
        },
        (error) => {
          console.error('Error fetching client rendezvous', error);
        }
      );
    } else if (this.userRole === 'JARDINIER') {
      this.rendezVousService.getRendezVousByJardinier(this.userId).subscribe(
        (data) => {
          this.rendezVous = data;
          this.filteredRendezVous = data;
        },
        (error) => {
          console.error('Error fetching jardinier rendezvous', error);
        }
      );
    } else if (this.userRole === 'ADMIN') {
      this.rendezVousService.getAllRendezVous().subscribe( // Assuming you have this method implemented
        (data) => {
          this.rendezVous = data;
          this.filteredRendezVous = data;
        },
        (error) => {
          console.error('Error fetching all rendezvous', error);
        }
      );
    } else {
      console.error('Invalid user role');
    }
  }

  // Delete a rendezvous by ID
  deleteRendezVous(id: number): void {
    if (confirm('Are you sure you want to delete this rendezvous?')) {
      this.rendezVousService.deleteRendezVous(id).subscribe(
        () => {
          this.rendezVous = this.rendezVous.filter(rdv => rdv.idRendezVous !== id);
          this.searchRendezVous(); // Reapply filter after deletion
        },
        (error) => {
          console.error('Error deleting rendezvous', error);
        }
      );
    }
  }

  // Navigate to edit rendezvous page
  editRendezVous(id: number): void {
    this.router.navigate(['/rendezvous/update', id]);
  }

  // Navigate to details rendezvous page
  details(id: number): void {
    this.router.navigate(['/rendezvous/details', id]);
  }

  // Search rendezvous based on the search term
  searchRendezVous(): void {
    if (this.searchTerm === '') {
      this.filteredRendezVous = this.rendezVous;
    } else {
      this.filteredRendezVous = this.rendezVous.filter(rdv =>
        rdv.lieu.toLowerCase().includes(this.searchTerm.toLowerCase()) || // You can search by different fields
        rdv.date.toLocaleString().includes(this.searchTerm) // Add more fields as necessary
      );
    }
  }
}
