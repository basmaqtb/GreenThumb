import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private authUrl = 'http://localhost:8088/auth';

  constructor(private http: HttpClient) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.authUrl}/register`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.authUrl}/login`, credentials);
  }

  // New method to get current user information
  getCurrentUser(): { role: string; id: number } | null {
    const user = JSON.parse(localStorage.getItem('currentUser') || 'null');
    return user; // Ensure that the user object has the 'role' and 'id' properties
  }

  // Optionally, you can add a method to check if the user is authenticated
  isAuthenticated(): boolean {
    return !!this.getCurrentUser(); // Returns true if a user is logged in
  }
}
