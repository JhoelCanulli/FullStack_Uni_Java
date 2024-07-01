import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Risposta } from '../interfaces/risposta';
import { User } from '../models/user';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}


  recuperaProfilo(username: string ): Observable<Risposta> {
    let tokenKey = localStorage.getItem('token');

    let headerCustom = new HttpHeaders({
      Authorization: `Bearer ${tokenKey}`,
    });

    return this.http.get<Risposta>(`${this.apiUrl}User/UserProfile/${username}`, {
      headers: headerCustom,
    });
  }

  getAllUsers(): Observable<Risposta> {
    return this.http.get<Risposta>(`${this.apiUrl}User/ListOfUsers`);
  }

  deleteUser(username: string): Observable<Risposta> {
    let tokenKey = localStorage.getItem('token');

    let headerCustom = new HttpHeaders({
      Authorization: `Bearer ${tokenKey}`,
    });

    const body = { user: username };

    return this.http.delete<Risposta>(`${this.apiUrl}User/DeleteUser`, {
      headers: headerCustom,
      body: body,
    });
  }
}