import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
    this.authService.registra(this.username, this.password).subscribe({
      next: (response: any) => {
        if (response.status === "SUCCESS") {
          // Registration successful, handle further logic if needed
          console.log("Registration successful");
        } else {
          // Registration failed
          console.error('Registration failed:', response.data);
        }
      },
      error: error => console.error('Registration failed:', error)
    });
  }
}
