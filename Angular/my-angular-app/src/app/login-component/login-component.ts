import { Component, inject } from '@angular/core';
import { MatAnchor } from '@angular/material/button';
import { AuthService } from '../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'LoginPage',
  imports: [MatAnchor],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  onLogin() {
    this.authService.login();
    this.router.navigate(['/home']);
  }
}
