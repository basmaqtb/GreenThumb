import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipement } from '../Modules/Equipement';  // Ensure the correct path

@Injectable({
  providedIn: 'root',
})
export class EquipementService {
  private apiUrl = 'http://localhost:8088/equipements';  // Your base URL

  constructor(private http: HttpClient) {}

  // Get all equipements with JWT
  getAllEquipements(): Observable<Equipement[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Equipement[]>(`${this.apiUrl}`, { headers });
  }

  // Get a single equipement by ID with JWT
  getEquipementById(id: number): Observable<Equipement> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Equipement>(`${this.apiUrl}/${id}`, { headers });
  }

  // Create a new equipement with JWT
  createEquipement(equipement: Equipement): Observable<Equipement> {
    const headers = this.createAuthorizationHeader();
    return this.http.post<Equipement>(`${this.apiUrl}/add`, equipement, { headers });
  }

  // Update an existing equipement by ID with JWT
  updateEquipement(id: number, equipement: Equipement): Observable<Equipement> {
    const headers = this.createAuthorizationHeader();
    return this.http.put<Equipement>(`${this.apiUrl}/update/${id}`, equipement, { headers });
  }

  // Delete an equipement by ID with JWT
  deleteEquipement(id: number): Observable<void> {
    const headers = this.createAuthorizationHeader();
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers });
  }

  // Helper method to add JWT authorization to the headers
  private createAuthorizationHeader(): HttpHeaders | undefined {
    const jwtToken = localStorage.getItem('jwt');  // Retrieving JWT token from localStorage
    if (jwtToken) {
      console.log("JWT token found in local storage", jwtToken);
      return new HttpHeaders().set("Authorization", "Bearer " + jwtToken);  // Add token to the headers
    } else {
      console.log("JWT token not found in local storage");
      return undefined;
    }
  }
}
