import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RendezVous } from '../Modules/RendezVous'; // Adjust the path if necessary

@Injectable({
  providedIn: 'root',
})
export class RendezVousService {
  private apiUrl = 'http://localhost:8088/rendezvous'; // Your base URL

  constructor(private http: HttpClient) {}

  // Get all rendezvous with JWT
  getAllRendezVous(): Observable<RendezVous[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<RendezVous[]>(this.apiUrl, { headers });
  }

  // Get a single rendezvous by ID with JWT
  getRendezVousById(id: number): Observable<RendezVous> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<RendezVous>(`${this.apiUrl}/${id}`, { headers });
  }

  // Create a new rendezvous with JWT and Client ID
  createRendezVous(rendezVousDTO: RendezVous, idClient: number): Observable<RendezVous> {
    const headers = this.createAuthorizationHeader();
    return this.http.post<RendezVous>(`${this.apiUrl}/add/${idClient}`, rendezVousDTO, { headers });
  }

  // Update an existing rendezvous by ID with JWT
  updateRendezVous(id: number, rendezVousDTO: RendezVous): Observable<RendezVous> {
    const headers = this.createAuthorizationHeader();
    return this.http.put<RendezVous>(`${this.apiUrl}/update/${id}`, rendezVousDTO, { headers });
  }

  // Delete a rendezvous by ID with JWT
  deleteRendezVous(id: number): Observable<void> {
    const headers = this.createAuthorizationHeader();
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers });
  }

  // Get all rendezvous by Client with JWT
  getRendezVousByClient(clientId: number): Observable<RendezVous[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<RendezVous[]>(`${this.apiUrl}/client/${clientId}`, { headers }); // Add headers here
  }

  // Get all rendezvous by Jardinier with JWT
  getRendezVousByJardinier(jardinierId: number): Observable<RendezVous[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<RendezVous[]>(`${this.apiUrl}/jardinier/${jardinierId}`, { headers }); // Add headers here
  }

  // Assign a Jardinier to a RendezVous
  assignJardinierToRendezVous(rendezVousId: number, jardinierId: number): Observable<RendezVous> {
    const headers = this.createAuthorizationHeader();
    return this.http.put<RendezVous>(`${this.apiUrl}/attribuer/${rendezVousId}/${jardinierId}`, {}, { headers });
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
