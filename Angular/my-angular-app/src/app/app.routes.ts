import { Routes } from '@angular/router';
import { LoginComponent } from './login-component/login-component';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { HomeComponent } from './home-component/home-component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'notfound', component: NotFoundComponent },
  { path: '**', redirectTo: 'notfound' },
];
