// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { RendezVousService } from '../../Services/rendez-vous.service'; // Adjust path if necessary
// import { RendezVous } from '../../Modules/RendezVous'; // Adjust path if necessary

// @Component({
//   selector: 'app-add-rendezvous',
//   templateUrl: './add-rendezvous.component.html',
//   styleUrls: ['./add-rendezvous.component.css']
// })
// export class AddRendezVousComponent implements OnInit {

//   rendezVousForm: FormGroup;
//   isEditMode: boolean = false;
//   rendezVousId: number | null = null;

//   constructor(
//     private fb: FormBuilder,
//     private rendezVousService: RendezVousService,
//     private route: ActivatedRoute,
//     private router: Router
//   ) {
//     // Initialize the form with validators
//     this.rendezVousForm = this.fb.group({
//       heure: ['', Validators.required],
//       date: ['', Validators.required],
//       lieu: ['', Validators.required],
//       statutRendezVous: ['', Validators.required],
//       idtache: [null, Validators.required],  // Foreign key reference to Tache
//       idclient: [null, Validators.required], // Foreign key reference to Client
//       idjardinier: [null, Validators.required] // Foreign key reference to Jardinier
//     });
//   }

//   ngOnInit(): void {
//     // Check if the URL has an 'id' parameter to determine if we're editing or creating
//     this.route.paramMap.subscribe(params => {
//       const id = params.get('id');
//       if (id) {
//         this.isEditMode = true;
//         this.rendezVousId = +id;
//         // Fetch the RendezVous details if editing
//         this.rendezVousService.getRendezVousById(this.rendezVousId).subscribe((rendezVous: RendezVous) => {
//           // Populate the form with the existing RendezVous data
//           this.rendezVousForm.patchValue(rendezVous);
//         });
//       }
//     });
//   }

//   onSubmit(): void {
//     if (this.rendezVousForm.invalid) {
//       return; // Form validation failed
//     }

//     if (this.isEditMode && this.rendezVousId) {
//       // Update the existing RendezVous
//       this.rendezVousService.updateRendezVous(this.rendezVousId, this.rendezVousForm.value).subscribe(() => {
//         this.router.navigate(['/rendezvous']); // Redirect after successful update
//       });
//     } else {
//       // Create a new RendezVous
//       // this.rendezVousService.createRendezVous(this.rendezVousId, this.rendezVousForm.value).subscribe(() => {
//       //   this.router.navigate(['/rendezvous']); // Redirect after successful creation
//       // });
//     }
//   }
// }
