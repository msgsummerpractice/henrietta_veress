import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterRequest, Role, SignInRequest, SignInResponse, UserResponse } from '../models/auth-model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  token = signal<string | null>(localStorage.getItem('token'));
  roles = signal<Role[]>(JSON.parse(localStorage.getItem('roles') ?? '[]'));

  isLoggedIn = computed(() => this.token() !== null);
  isAdmin = computed(() => this.roles().some((r) => r.name === 'ADMIN'));

  login(request: SignInRequest) {
    return this.http.post<SignInResponse>('http://localhost:8080/auth/login', request);
  }

  setSession(res: SignInResponse) {
    localStorage.setItem('token', res.token);
    localStorage.setItem('roles', JSON.stringify(res.roles));
    this.token.set(res.token);
    this.roles.set(res.roles);
  }

  register(request: RegisterRequest) {
    return this.http.post<UserResponse>('http://localhost:8080/auth/register', request);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('roles');
    this.token.set(null);
    this.roles.set([]);
    this.router.navigate(['/home']);
  }
}
