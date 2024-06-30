import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { ChefService } from './chef.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl: string = environment.apiUrl;
  private tokenKey = 'token';
 

  constructor(
    private http: HttpClient,
    private chefService: ChefService,
    private router: Router,
  ) {}

  login(user: string, pass: string): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const payload = { user, pass };

    return this.http.post<string>(`${this.apiUrl}Auth/login`, payload, {
      headers: headers,
      responseType: 'text' as 'json', // Tells Angular to handle the response as plain text
    });
  }

  private getToken = (): string | null => localStorage.getItem(this.tokenKey);

  getCurrentUser(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        console.log(decodedToken.Username);
        return decodedToken.Username; // Assuming 'Username' is the property that stores the username in your JWT payload
      } catch (error) {
        console.error('Error decoding token:', error);
        return null;
      }
    }
    return null;
  }

  isLoggedIn = (): boolean => {
    const token = this.getToken();
    if (!token) return false;

    return !this.isTokenExpired();
  };

  isTokenExpired() {
    const token = this.getToken();
    if (!token) return true;
    const decoded = jwtDecode(token);
    const isTokenExpired = Date.now() >= decoded['exp']! * 1000;
   // console.log("here you see when token expires " + isTokenExpired)
    if (isTokenExpired) this.logout();
   // console.log('is token expired ' + Date.now() + " " + decoded['exp']! * 1000);
    return isTokenExpired;
  }

  logout = (): void => {
    //console.log('Token retreived:', localStorage.getItem(this.tokenKey)); // Should be null
    localStorage.removeItem(this.tokenKey);
    console.log('Token removed:', localStorage.getItem(this.tokenKey)); // Should be null
    //this.router.navigateByUrl('/home');
  };

  registra(
    user: string,
    pass: string,
    img: string = '',
  ): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = JSON.stringify({ user, pass, img });

    // Include the query parameter for 'img'
    return this.http
      .post(`${this.apiUrl}User/register?img=${img}`, body, { headers })
      .pipe(
        catchError((error) => {
          console.error('Registration error:', error);
          return throwError(
            () => new Error('Registration failed due to server error'),
          );
        }),
      );
  }
}

