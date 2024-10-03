import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tache } from '../Modules/Tache'; // Adjust the path if necessary

@Injectable({
  providedIn: 'root',
})
export class TacheService {
  private apiUrl = 'http://localhost:8088/taches'; // Your base URL

  constructor(private http: HttpClient) {}

  // Get all taches with JWT
  getAllTaches(): Observable<Tache[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Tache[]>(this.apiUrl, { headers });
  }

  // Get a single tache by ID with JWT
  getTacheById(id: number): Observable<Tache> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Tache>(`${this.apiUrl}/${id}`, { headers });
  }

  // Create a new tache with JWT and Equipement ID
  createTache(tache: Tache): Observable<Tache> { // Removed equipementId parameter since it's not used in the request
    const headers = this.createAuthorizationHeader();
    return this.http.post<Tache>(`${this.apiUrl}/add`, tache, { headers });
  }

  // Update an existing tache by ID with JWT
  updateTache(id: number, tache: Tache): Observable<Tache> {
    const headers = this.createAuthorizationHeader();
    return this.http.put<Tache>(`${this.apiUrl}/update/${id}`, tache, { headers });
  }

  // Delete a tache by ID with JWT
  deleteTache(id: number): Observable<void> {
    const headers = this.createAuthorizationHeader();
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers });
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
