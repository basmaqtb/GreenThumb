import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TacheService } from '../../Services/tache.service'; // Adjust the path if necessary
import { Tache } from '../../Modules/Tache'; // Adjust the path if necessary

@Component({
  selector: 'app-all-taches',
  templateUrl: './all-taches.component.html',
  styleUrls: ['./all-taches.component.css']
})
export class AllTachesComponent implements OnInit {
  taches: Tache[] = [];
  filteredTaches: Tache[] = [];
  searchTerm: string = '';

  constructor(private tacheService: TacheService, private router: Router) {}

  ngOnInit(): void {
    this.loadTaches();
  }

  // Load all taches from the service
  loadTaches(): void {
    this.tacheService.getAllTaches().subscribe(
      (data) => {
        this.taches = data;
        this.filteredTaches = data; // Initialize filteredTaches with the complete list
      },
      (error) => {
        console.error('Error fetching taches', error);
      }
    );
  }

  // Delete a tache by ID
  deleteTache(id: number): void {
    if (confirm('Are you sure you want to delete this tache?')) {
      this.tacheService.deleteTache(id).subscribe(
        () => {
          this.taches = this.taches.filter(tache => tache.idtache !== id);
          this.searchTaches(); // Reapply filter after deletion
        },
        (error) => {
          console.error('Error deleting tache', error);
        }
      );
    }
  }

  // Navigate to edit tache page
  editTache(id: number): void {
    this.router.navigate(['/taches/update', id]);
  }

  // Search taches based on the search term
  searchTaches(): void {
    if (this.searchTerm === '') {
      this.filteredTaches = this.taches;
    } else {
      this.filteredTaches = this.taches.filter(tache =>
        tache.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
  }
}
