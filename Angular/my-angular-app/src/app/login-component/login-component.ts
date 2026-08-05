import { Component, inject, signal } from '@angular/core';
import { MatAnchor, MatButton } from '@angular/material/button';
import { AuthService } from '../services/auth-service';
import { Router, RouterLink } from '@angular/router';
import {
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'LoginPage',
  imports: [
    MatAnchor,
    MatButton,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    RouterLink,
  ],
  templateUrl: './login-component.html',
})
export class LoginComponent {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [
      Validators.required,
      Validators.email,
      Validators.maxLength(50),
    ]),
    password: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(255),
    ]),
  });

  protected readonly errorMessage = signal<string | null>(null);

  onFormSubmit(): void {
    if (this.loginFormGroup.invalid) return;

    const { email, password } = this.loginFormGroup.getRawValue();

    this.authService.login({ email, password }).subscribe({
      next: (res) => {
        this.authService.setSession(res);
        this.router.navigate(['/home']);
      },
      error: () => {
        this.errorMessage.set('Wrong email or password!');
      },
    });
  }
}
