import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthguardService {

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn()) {
      return true;
    } 
    if (this.authService.isTokenExpired()) {
        // Token is expired, redirect to login page
       // this.router.navigateByUrl('/login');
      }
    return false;
    
  }
}
