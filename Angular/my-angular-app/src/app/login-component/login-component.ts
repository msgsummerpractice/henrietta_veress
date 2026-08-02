import { Component, inject } from '@angular/core';
import { MatAnchor, MatButton } from '@angular/material/button';
import { AuthService } from '../services/auth-service';
import { Router } from '@angular/router';
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
  imports: [MatAnchor, MatButton, ReactiveFormsModule, MatFormFieldModule, MatInputModule],
  templateUrl: './login-component.html',
})
export class LoginComponent {
  private readonly _formBuilder = inject(NonNullableFormBuilder);

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [Validators.required, Validators.email]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(6)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      console.log('getRawValue():', this.loginFormGroup.getRawValue());
    }
  }
}
