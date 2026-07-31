import { Component } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'Navbar',
  imports: [MatToolbar, MatIconModule, MatButtonModule, MatToolbarModule, RouterLink],
  templateUrl: './navbar-component.html',
  styleUrl: './navbar-component.css',
})
export class NavbarComponent {}
