import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Jwt } from '../Modules/Jwt';

const url = "http://localhost:8088/auth/";

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor(private http: HttpClient) { }

  register(singRequest: any): Observable<Jwt> {
    return this.http.post<Jwt>(url + 'register', singRequest);
  }

  login(loginRequest: any): Observable<Jwt> {
    return this.http.post<Jwt>(url + 'login', loginRequest);
  }

  sayHello(): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.http.get(url + 'demo', { headers });
  }

  private createAuthorizationHeader(): HttpHeaders | undefined {
    const jwtToken = localStorage.getItem('jwt');
    const role = localStorage.getItem('role');
    const id = localStorage.getItem('id');

    if (jwtToken) {
      console.log("JWT token found in local storage", jwtToken);
      return new HttpHeaders().set("Authorization", "Bearer " + jwtToken);
    } else {
      console.log("JWT token not found in local storage");
      return undefined;
    }
  }

  // Clear the JWT token and other data from localStorage (logout)
  clearToken(): void {
    localStorage.removeItem('jwt'); // Remove JWT token
    localStorage.removeItem('role'); // Remove role if stored
    localStorage.removeItem('username'); // Remove any additional user data (e.g., username)
  }
}
