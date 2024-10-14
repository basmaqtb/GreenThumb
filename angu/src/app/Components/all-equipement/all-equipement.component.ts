import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router'; // Import ActivatedRoute
import { EquipementService } from '../../Services/equipement.service';
import { Equipement } from '../../Modules/Equipement';

@Component({
  selector: 'app-all-equipement',
  templateUrl: './all-equipement.component.html',
  styleUrls: ['./all-equipement.component.css']
})
export class AllEquipementComponent implements OnInit {
  equipements: Equipement[] = [];
  filteredEquipements: Equipement[] = [];
  searchTerm: string = '';
  idTache: number | null = null; // Store the task ID

  constructor(
    private equipementService: EquipementService, 
    private router: Router,
    private route: ActivatedRoute // Inject ActivatedRoute to access route parameters
  ) {}

  ngOnInit(): void {
    // Get the ID of the task from the route parameters
    this.route.paramMap.subscribe(params => {
      const id = params.get('idtache'); // Assuming the parameter is named 'idtache'
      this.idTache = id ? +id : null; // Convert to number or null if not present
      this.loadEquipements();
    });
  }

  loadEquipements(): void {
    if (this.idTache) {
      // Load equipements based on the task ID if present
      this.equipementService.getEquipementsByTacheId(this.idTache).subscribe(
        (data) => {
          this.equipements = data;
          this.filteredEquipements = data; // Initialize filteredEquipements with the filtered list
        },
        (error) => {
          console.error('Error fetching equipements for task', error);
        }
      );
    } else {
      // Load all equipements if no task ID is provided
      this.equipementService.getAllEquipements().subscribe(
        (data) => {
          this.equipements = data;
          this.filteredEquipements = data; // Initialize filteredEquipements with the complete list
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
          this.searchEquipements(); // Reapply filter after deletion
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
