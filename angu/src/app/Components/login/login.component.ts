import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/Services/jwt.service';
import { Jwt } from 'src/app/Modules/Jwt';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(
    private service: JwtService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  submitForm(): void {
    if (this.loginForm.invalid) {
      console.error("Form is invalid");
      return; // Prevent submission if the form is invalid
    }

    console.log('Form values:', this.loginForm.value); // Log the form values
    this.service.login(this.loginForm.value).subscribe(
      (response: Jwt) => {
        console.log('API response:', response); // Log the API response
        const jwToken = response.token;
        const role = response.role;
        const id = response.id; // Change this to match your actual API response field for ID

        if (jwToken && role && id) {
          localStorage.setItem('jwt', jwToken);
          localStorage.setItem('role', role);
          localStorage.setItem('id', id.toString()); // Store the user ID
          console.log('JWT, role, and ID stored in localStorage:', { jwToken, role, id }); // Confirm storage
          this.router.navigateByUrl("/dashboard");
        } else {
          console.error('Token, role, or ID is missing from the response');
        }
      },
      (error) => {
        console.error("Login failed", error);
        // Optionally display an error message to the user
      }
    );
  }
}
