import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EquipementService } from '../../Services/equipement.service'; // Adjust path if necessary
import { Equipement } from '../../Modules/Equipement'; // Adjust path if necessary
import { TacheService } from '../../Services/tache.service'; // Import TacheService
import { Tache } from '../../Modules/Tache'; // Import Tache

@Component({
  selector: 'app-add-equipement',
  templateUrl: './add-equipement.component.html',
  styleUrls: ['./add-equipement.component.css']
})
export class AddEquipementComponent implements OnInit {

  equipementForm: FormGroup;
  isEditMode: boolean = false;
  equipementId: number | null = null;
  taches: Tache[] = []; // To hold the list of taches

  constructor(
    private fb: FormBuilder,
    private equipementService: EquipementService,
    private tacheService: TacheService, // Inject TacheService
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize the form with validators
    this.equipementForm = this.fb.group({
      etat: ['', Validators.required],
      marque: ['', Validators.required],
      model: ['', Validators.required],
      type: ['', Validators.required],
      idtache: ['', Validators.required] // Add idtache to the form
    });
  }

  ngOnInit(): void {
    // Load existing taches when the component initializes
    this.loadTaches();

    // Check if we're in edit mode
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.equipementId = +id;
        // Fetch the equipment details if editing
        this.equipementService.getEquipementById(this.equipementId).subscribe((equipement: Equipement) => {
          // Populate the form with the existing equipement data
          this.equipementForm.patchValue(equipement);
        });
      }
    });
  }

  // Load taches from the backend
  loadTaches(): void {
    this.tacheService.getAllTaches().subscribe({
      next: (data) => {
        this.taches = data;
      },
      error: (err) => {
        console.error('Error loading taches:', err);
      }
    });
  }

  onSubmit(): void {
    if (this.equipementForm.invalid) {
      return; // Form validation failed
    }

    if (this.isEditMode && this.equipementId) {
      // Update the existing equipment
      this.equipementService.updateEquipement(this.equipementId, this.equipementForm.value).subscribe(() => {
        this.router.navigate(['/equipements']); // Redirect after successful update
      });
    } else {
      // Create a new equipment
      this.equipementService.createEquipement(this.equipementForm.value).subscribe(() => {
        this.router.navigate(['/equipements']); // Redirect after successful creation
      });
    }
  }
}
