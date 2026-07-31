import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatAnchor } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'NotFoundPage',
  imports: [RouterLink, MatAnchor, MatIconModule],
  templateUrl: './not-found-component.html',
  styleUrl: './not-found-component.css',
})
export class NotFoundComponent {}
