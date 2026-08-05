import { Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { Router, NavigationEnd, RouterLink, RouterLinkActive } from '@angular/router';
import { filter, map } from 'rxjs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AuthService } from '../services/auth-service';

@Component({
  selector: 'Navbar',
  imports: [MatToolbarModule, MatIconModule, MatButtonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar-component.html',
})
export class NavbarComponent {
  protected readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  private readonly hiddenRoutes = ['/login', '/register'];

  protected readonly showNavbar = toSignal(
    this.router.events.pipe(
      filter((event) => event instanceof NavigationEnd),
      map((event) => !this.hiddenRoutes.includes(event.urlAfterRedirects)),
    ),
    { initialValue: !this.hiddenRoutes.includes(this.router.url) },
  );

  onLogout(): void {
    this.authService.logout();
  }
}