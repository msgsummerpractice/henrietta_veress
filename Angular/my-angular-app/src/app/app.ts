import { Component, signal } from '@angular/core';

import { NavbarComponent } from './navbar-component/navbar-component';
import { MatToolbarModule } from '@angular/material/toolbar';

import { CommonModule } from '@angular/common';

import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [CommonModule, NavbarComponent, MatToolbarModule, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}
