import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable, catchError, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { UserService } from './user.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiUrl: string = environment.apiUrl;
 

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router,
  ) {}

  login(username: string, password: string): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const payload = { username, password };

    return this.http.post<string>(`${this.apiUrl}public/login`, payload, {
      headers: headers,
      responseType: 'text' as 'json', // Tells Angular to handle the response as plain text
    });
  }
  registra(
    username: string,
    password: string,
  ): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = JSON.stringify({username, password});

    return this.http
      .post(`${this.apiUrl}public/register`, body, { headers })
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
