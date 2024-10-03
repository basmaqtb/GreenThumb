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
      return; // Prevent submission if the form is invalid
    }

    console.log(this.loginForm.value);
    this.service.login(this.loginForm.value).subscribe(
      (response: Jwt) => {
        const jwToken = response.token;
        const role = response.role;
        localStorage.setItem('jwt', jwToken);
        localStorage.setItem('role', role);
        this.router.navigateByUrl("/equipements");
      },
      (error) => {
        console.error("Login failed", error);
        // Optionally display an error message to the user
      }
    );
  }
}
