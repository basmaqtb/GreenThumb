import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service'; // Adjust the path if necessary
import { RendezVous } from '../../Modules/RendezVous'; // Adjust the path if necessary

@Component({
  selector: 'app-add-rendez-vous',
  templateUrl: './addrendez-vous.component.html',
  styleUrls: ['./addrendez-vous.component.css']
})
export class AddrendezVousComponent implements OnInit {

  rendezVousForm: FormGroup;
  isEditMode: boolean = false;
  rendezVousId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private rendezVousService: RendezVousService,
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
      idclient: [null, Validators.required],
      idjardinier: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    // Check if the URL has an 'id' parameter to determine if we're editing or creating
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.rendezVousId = +id;
        // Fetch the existing rendezvous if editing
        this.rendezVousService.getRendezVousById(this.rendezVousId).subscribe((rendezVous: RendezVous) => {
          // Populate the form with the existing rendezvous data
          this.rendezVousForm.patchValue(rendezVous);
        });
      }
    });
  }

  onSubmit(): void {
    if (this.rendezVousForm.invalid) {
      return; // Form validation failed
    }
  
    // Get the current 'heure' value from the form
    let heureValue = this.rendezVousForm.get('heure')?.value;
  
    // If the heure value doesn't already include seconds, append ':00'
    if (heureValue && !heureValue.includes(':')) {
      heureValue = `${heureValue}:00`;
    } else if (heureValue && heureValue.split(':').length === 2) {
      heureValue = `${heureValue}:00`;  // append seconds if they are missing
    }
  
    // Update the form with the modified heure value
    this.rendezVousForm.patchValue({ heure: heureValue });
  
    if (this.isEditMode && this.rendezVousId) {
      // Update the existing rendezvous
      this.rendezVousService.updateRendezVous(this.rendezVousId, this.rendezVousForm.value).subscribe(() => {
        this.router.navigate(['/rendezvous']); // Redirect after successful update
      });
    } else {
      // Assuming the client ID is provided in the form or elsewhere
      const idClient = this.rendezVousForm.value.idclient;
      // Create a new rendezvous
      this.rendezVousService.createRendezVous(this.rendezVousForm.value, idClient).subscribe(() => {
        this.router.navigate(['/rendezvous']); // Redirect after successful creation
      });
    }
  }
}
