import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TacheService } from '../../Services/tache.service'; // Adjust path if necessary
import { Tache } from '../../Modules/Tache'; // Adjust path if necessary

@Component({
  selector: 'app-add-tache',
  templateUrl: './add-tache.component.html',
  styleUrls: ['./add-tache.component.css']
})
export class AddTacheComponent implements OnInit {

  tacheForm: FormGroup;
  isEditMode: boolean = false;
  tacheId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private tacheService: TacheService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize the form with validators
    this.tacheForm = this.fb.group({
      description: ['', Validators.required],
      date: ['', Validators.required],
      statutTache: ['', Validators.required],
      idequipement: [null, Validators.required] // Assuming this is a foreign key reference to Equipement
    });
  }

  ngOnInit(): void {
    // Check if the URL has an 'id' parameter to determine if we're editing or creating
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.tacheId = +id;
        // Fetch the task details if editing
        this.tacheService.getTacheById(this.tacheId).subscribe((tache: Tache) => {
          // Populate the form with the existing task data
          this.tacheForm.patchValue(tache);
        });
      }
    });
  }

  onSubmit(): void {
    if (this.tacheForm.invalid) {
      return; // Form validation failed
    }

    if (this.isEditMode && this.tacheId) {
      // Update the existing task
      this.tacheService.updateTache(this.tacheId, this.tacheForm.value).subscribe(() => {
        this.router.navigate(['/taches']); // Redirect after successful update
      });
    } else {
      // Create a new task
      this.tacheService.createTache(this.tacheForm.value).subscribe(() => {
        this.router.navigate(['/taches']); // Redirect after successful creation
      });
    }
  }
}
