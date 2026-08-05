import { Routes } from '@angular/router';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { HomeComponent } from './home-component/home-component';
import { GalleryComponent } from './gallery-component/gallery-component';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'gallery',
    loadComponent: () =>
      import('./gallery-component/gallery-component').then((m) => m.GalleryComponent),
    canActivate: [authGuard],
  },
  {
    path: 'login',
    loadComponent: () => import('./login-component/login-component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./register-component/register-component').then((m) => m.RegisterComponent),
  },
  { path: 'notfound', component: NotFoundComponent },
  { path: '**', redirectTo: 'notfound' },
];
