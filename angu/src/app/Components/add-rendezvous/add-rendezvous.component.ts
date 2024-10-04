import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RendezVousService } from '../../Services/rendez-vous.service'; // Adjust path if necessary
import { RendezVous } from '../../Modules/RendezVous'; // Adjust path if necessary

@Component({
  selector: 'app-add-rendezvous',
  templateUrl: './add-rendezvous.component.html',
  styleUrls: ['./add-rendezvous.component.css']
})
export class AddRendezvousComponent implements OnInit {

  rendezvousForm: FormGroup;
  isEditMode: boolean = false;
  rendezvousId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private rendezvousService: RendezVousService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Initialize the form with validators
    this.rendezvousForm = this.fb.group({
      heure: ['', Validators.required], // Assuming heure is a time input
      date: ['', Validators.required],
      lieu: ['', Validators.required],
      statutRendezVous: ['', Validators.required],
      idtache: [null, Validators.required],  // Foreign key reference to Tache
      idclient: [null, Validators.required], // Foreign key reference to Client
      idjardinier: [null, Validators.required] // Foreign key reference to Jardinier
    });
  }

  ngOnInit(): void {
    // Check if the URL has an 'id' parameter to determine if we're editing or creating
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.rendezvousId = +id;
        // Fetch the rendezvous details if editing
        this.rendezvousService.getRendezVousById(this.rendezvousId).subscribe((rendezvous: RendezVous) => {
          // Populate the form with the existing rendezvous data
          this.rendezvousForm.patchValue(rendezvous);
        });
      }
    });
  }

  onSubmit(): void {
    if (this.rendezvousForm.invalid) {
      return; // Form validation failed
    }

    if (this.isEditMode && this.rendezvousId) {
      // Update the existing rendezvous
      this.rendezvousService.updateRendezVous(this.rendezvousId, this.rendezvousForm.value).subscribe(() => {
        this.router.navigate(['/rendezvous']); // Redirect after successful update
      });
    } else {
      // Create a new rendezvous
      this.rendezvousService.createRendezVous(this.rendezvousForm.value, this.rendezvousId.value).subscribe(() => {
        this.router.navigate(['/rendezvous']); // Redirect after successful creation
      });
    }
  }
}
