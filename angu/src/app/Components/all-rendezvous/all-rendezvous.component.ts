import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service'; // Adjust the path if necessary
import { RendezVous } from '../../Modules/RendezVous'; // Adjust the path if necessary
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

  constructor(private rendezVousService: RendezVousService, private router: Router) {}

  ngOnInit(): void {
    this.loadRendezVous();
  }

 


  // Load all rendezvous from the service
  loadRendezVous(): void {
    this.rendezVousService.getAllRendezVous().subscribe(
      (data) => {
        this.rendezVous = data;
        console.log(data);
        this.filteredRendezVous = data;
      },
      (error) => {
        console.error('Error fetching rendezvous', error);
      }
    );
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
