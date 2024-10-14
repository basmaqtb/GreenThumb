import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';

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
    return this.http.post(`${this.authUrl}/login`, credentials).pipe(
      tap((response: any) => {
        // Check if the response has user data
        if (response && response.user) {
          const userData = {
            id: response.user.id,   // Adjust according to your actual response structure
            role: response.user.role // Adjust according to your actual response structure
          };
          
          // Store the current user in localStorage with the key 'currentUser'
          localStorage.setItem('currentUser', JSON.stringify(userData));
          console.log('User data stored in localStorage:', userData);
        } else {
          console.error('No user data found in the response:', response);
        }
      }),
      catchError(error => {
        console.error('Login error:', error);
        return throwError(error); // Pass the error along
      })
    );
  }
  

  getCurrentUserId(): { role: string; id: number } | null {
    const user = JSON.parse(localStorage.getItem('currentUser') || 'null');
    return user; // Ensure that the user object has the 'role' and 'id' properties
}

  // Optionally, you can add a method to check if the user is authenticated
  isAuthenticated(): boolean {
    return !!this.getCurrentUserId(); // Returns true if a user is logged in
  }
}
