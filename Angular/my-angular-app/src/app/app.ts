import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { Navbar } from './navbar/navbar';
import { MatToolbar, MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-root',
  imports: [MatButton, Navbar, MatToolbar, MatToolbarModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  
}
