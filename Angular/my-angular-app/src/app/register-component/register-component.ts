import { Component, inject, signal } from '@angular/core';
import {
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../services/auth-service';
import { Router, RouterLink } from '@angular/router';
import { MatButton } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

type RegisterForm = {
  email: FormControl<string>;
  username: FormControl<string>;
  password: FormControl<string>;
  firstName: FormControl<string>;
  lastName: FormControl<string>;
};

@Component({
  selector: 'RegisterPage',
  imports: [MatButton, ReactiveFormsModule, MatFormFieldModule, MatInputModule, RouterLink],
  templateUrl: './register-component.html',
  styleUrl: './register-component.css',
})
export class RegisterComponent {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly errorMessage = signal<string | null>(null);

  protected readonly registerFormGroup = this._formBuilder.group<RegisterForm>({
    email: this._formBuilder.control('', [
      Validators.required,
      Validators.email,
      Validators.maxLength(50),
    ]),
    username: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
    ]),
    password: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(255),
    ]),
    firstName: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(100),
    ]),
    lastName: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(2),
      Validators.maxLength(100),
    ]),
  });

  onFormSubmit(): void {
    if (this.registerFormGroup.invalid) return;

    const request = this.registerFormGroup.getRawValue();

    this.authService.register(request).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Registration failed. Please try again.');
      },
    });
  }
}
