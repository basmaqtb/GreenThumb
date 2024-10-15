import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TacheService } from '../../Services/tache.service'; 
import { Tache } from '../../Modules/Tache';
import { AuthenticationService } from '../../Services/auth.service'; 
import { TaskImageService } from '../../Services/task-image-service.service';


@Component({
  selector: 'app-all-taches',
  templateUrl: './all-taches.component.html',
  styleUrls: ['./all-taches.component.css']
})
export class AllTachesComponent implements OnInit {
  taches: Tache[] = [];
  filteredTaches: Tache[] = [];
  searchTerm: string = '';
  userRole: string | null = null;  // Variable to hold the user role

  constructor(
    private tacheService: TacheService, 
    private router: Router, 
    private authService: AuthenticationService ,
    private taskImageService: TaskImageService
  ) {}

  ngOnInit(): void {
    this.filteredTaches = this.filteredTaches.map(tache => {
      const imageUrl = this.taskImageService.getImageForTask(tache.idtache);
      console.log(`Task ${tache.idtache} assigned image: ${imageUrl}`);
      return {
        ...tache,
        imageUrl: imageUrl
      };
    });
    this.loadUserInfo(); // Load user role from local storage
    this.loadTaches();

  }

  // Load user role from local storage
  loadUserInfo(): void {
    const role = localStorage.getItem('role');
    if (role) {
      this.userRole = role;  // Save the user role
    }
  }

  // Load all taches
  loadTaches(): void {
    this.tacheService.getAllTaches().subscribe(
      (data) => {
        this.taches = data;
        this.filteredTaches = data;
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
          this.searchTaches();
        },
        (error) => {
          console.error('Error deleting tache', error);
        }
      );
    }
  }

  // Edit a tache
  editTache(id: number): void {
    this.router.navigate(['/taches/update', id]);
  }

  // View tache details
  details(id: number): void {
    this.router.navigate(['/taches/details', id]);
  }

  // Search taches
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
