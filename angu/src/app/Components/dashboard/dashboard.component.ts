import { Component } from '@angular/core';
import { Router } from '@angular/router'; // Import Router for navigation
import { JwtService } from '../../Services/jwt.service'; // Import JwtService for token management

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  constructor(private router: Router, private jwtService: JwtService) { }

  // Logout function with confirmation prompt
  logout(): void {
    // Ask for user confirmation
    const confirmLogout = window.confirm('Do you really want to log out?');
    
    if (confirmLogout) {
      // If the user confirms, proceed with the logout
      this.jwtService.clearToken(); // Clear the JWT token

      // Optionally clear other user-related data from localStorage
      localStorage.removeItem('userRole');
      localStorage.removeItem('username');

      // Navigate to the login page after logging out
      this.router.navigate(['/login']);
    }
  }
}
