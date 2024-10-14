import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Utilisateur } from '../Modules/Utilisateur'; // Adjust the path if necessary

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8088/auth/users'; // Your base URL

  constructor(private http: HttpClient) {}

  // Get all users with JWT
  getAllUsers(): Observable<Utilisateur[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Utilisateur[]>(this.apiUrl, { headers });
  }

  // Get a single user by ID with JWT
  getUserById(id: number): Observable<Utilisateur> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Utilisateur>(`${this.apiUrl}/${id}`, { headers });
  }

  // Create a new user with JWT
  createUser(user: Utilisateur): Observable<Utilisateur> {
    const headers = this.createAuthorizationHeader();
    return this.http.post<Utilisateur>(`${this.apiUrl}/add`, user, { headers });
  }

  // Update an existing user by ID with JWT
  updateUser(id: number, user: Utilisateur): Observable<Utilisateur> {
    const headers = this.createAuthorizationHeader();
    return this.http.put<Utilisateur>(`${this.apiUrl}/update/${id}`, user, { headers });
  }

  // Delete a user by ID with JWT
  deleteUser(id: number): Observable<void> {
    const headers = this.createAuthorizationHeader();
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers });
  }

  // Get all Jardinier with JWT
  getAllJardiniers(): Observable<Utilisateur[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Utilisateur[]>(`${this.apiUrl}/jardiniers`, { headers });
  }

  // Get all Clients with JWT
  getAllClients(): Observable<Utilisateur[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Utilisateur[]>(`${this.apiUrl}/clients`, { headers });
  }


  // Helper method to add JWT authorization to the headers
  private createAuthorizationHeader(): HttpHeaders | undefined {
    const jwtToken = localStorage.getItem('jwt'); // Retrieve JWT token from localStorage
    if (jwtToken) {
      return new HttpHeaders().set('Authorization', 'Bearer ' + jwtToken); // Add token to the headers
    }
    return undefined; // Return undefined if no token is found
  }
}
