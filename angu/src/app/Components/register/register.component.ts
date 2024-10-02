import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../Services/auth.service';

@Component({
  selector: 'app-register', // Ensure you have a valid selector
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'] // Ensure this path is correct
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup; // Declare the FormGroup for registration
  role: string = 'CLIENT'; // Default role (can be set to other values)

  constructor(
    private authService: AuthenticationService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize the form with validation
    this.registerForm = this.fb.group({
      fullname: ['', [Validators.required]], // Validate full name is required
      email: ['', [Validators.required, Validators.email]], // Validate email
      password: ['', [Validators.required, Validators.minLength(6)]], // Validate password is required and has a minimum length
      phone: ['', [Validators.required]], // Validate phone is required
      role: [this.role, [Validators.required]] // Allow role selection with default value
    });
  }

  register(): void {
    if (this.registerForm.valid) {
      const user = {
        ...this.registerForm.value,
        role: this.registerForm.value.role // Dynamically set the user role from form
      };
      this.authService.register(user).subscribe(response => {
        console.log('Registration successful:', response); // Log the response for debugging
        this.router.navigate(['/login']); // Navigate to login on successful registration
      }, error => {
        console.error('Registration error:', error); // Handle registration error
        alert("Registration failed. Please try again."); // Alert user on error
      });
    } else {
      console.log('Form is not valid');
      alert("Please fill out the form correctly."); // Alert for invalid form submission
    }
  }
}
