import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EquipementService } from '../../Services/equipement.service';
import { Equipement } from '../../Modules/Equipement';
import { AuthenticationService } from '../../Services/auth.service'; // Import AuthenticationService

@Component({
  selector: 'app-all-equipement',
  templateUrl: './all-equipement.component.html',
  styleUrls: ['./all-equipement.component.css']
})
export class AllEquipementComponent implements OnInit {
  equipements: Equipement[] = [];
  filteredEquipements: Equipement[] = [];
  searchTerm: string = '';
  idTache: number | null = null;
  userRole: string | null = null; // Variable to hold user role

  constructor(
    private equipementService: EquipementService, 
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthenticationService // Inject AuthenticationService
  ) {}

  ngOnInit(): void {
    this.loadUserInfo(); // Load user information
    this.route.paramMap.subscribe(params => {
      const id = params.get('idtache');
      this.idTache = id ? +id : null;
      this.loadEquipements();
    });
  }

  loadUserInfo(): void {
    const role = localStorage.getItem('role'); // Get user role from localStorage
    this.userRole = role; // Store user role
  }

  loadEquipements(): void {
    if (this.idTache) {
      this.equipementService.getEquipementsByTacheId(this.idTache).subscribe(
        (data) => {
          this.equipements = data;
          this.filteredEquipements = data;
        },
        (error) => {
          console.error('Error fetching equipements for task', error);
        }
      );
    } else {
      this.equipementService.getAllEquipements().subscribe(
        (data) => {
          this.equipements = data;
          this.filteredEquipements = data;
        },
        (error) => {
          console.error('Error fetching equipements', error);
        }
      );
    }
  }

  deleteEquipement(id: number): void {
    if (confirm('Are you sure you want to delete this equipement?')) {
      this.equipementService.deleteEquipement(id).subscribe(
        () => {
          this.equipements = this.equipements.filter(equipement => equipement.idequipement !== id);
          this.searchEquipements();
        },
        (error) => {
          console.error('Error deleting equipement', error);
        }
      );
    }
  }

  editEquipement(id: number): void {
    this.router.navigate(['/equipements/update', id]);
  }

  searchEquipements(): void {
    if (this.searchTerm === '') {
      this.filteredEquipements = this.equipements;
    } else {
      this.filteredEquipements = this.equipements.filter(equipement =>
        equipement.marque.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
  }
}
