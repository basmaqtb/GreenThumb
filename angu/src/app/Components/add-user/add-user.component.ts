import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../../Services/auth.service';
import { UserService } from '../../Services/user.service'; // Import UserService for editing
import { Utilisateur } from '../../Modules/Utilisateur'; // Assuming there's a User model

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  userForm!: FormGroup; // FormGroup for user creation or editing
  isEditMode: boolean = false; // Track if we are in edit mode
  userId: number | null = null; // Track the ID of the user being edited

  constructor(
    private authService: AuthenticationService,
    private userService: UserService, // Service for handling user operations
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize the form with validation
    this.userForm = this.fb.group({
      fullName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required]],
      role: ['CLIENT', [Validators.required]]
    });
  
    // Add the password field only in registration mode
    if (!this.isEditMode) {
      this.userForm.addControl('password', this.fb.control('', [Validators.required, Validators.minLength(6)]));
    }
  
    // Check if we are in edit mode by looking at the route parameters
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.userId = +id; // Convert the string to a number
        this.loadUserData(this.userId); // Load user data for editing
      }
    });
  }
  

  // Function to load user data in edit mode
  loadUserData(userId: number): void {
    this.userService.getUserById(userId).subscribe((user: Utilisateur) => {
      // Populate the form with the existing user data
      this.userForm.patchValue({
        fullname: user.fullName,
        email: user.email,
        phone: user.phone,
        role: user.role
      });
    });
  }

  // Function to handle form submission for both create and edit
  onSubmit(): void {
    if (this.userForm.invalid) {
      alert("Please fill out the form correctly.");
      return;
    }

    if (this.isEditMode && this.userId) {
      // Update the existing user
      this.userService.updateUser(this.userId, this.userForm.value).subscribe(() => {
        alert("User updated successfully");
        this.router.navigate(['/users']); // Redirect after successful update
      }, error => {
        console.error('Error updating user', error);
        alert("Update failed. Please try again.");
      });
    } else {
      // Create a new user
      this.authService.register(this.userForm.value).subscribe(() => {
        alert("User registered successfully");
        this.router.navigate(['/users']); // Redirect after successful registration
      }, error => {
        console.error('Error registering user', error);
        alert("Registration failed. Please try again.");
      });
    }
  }
}
