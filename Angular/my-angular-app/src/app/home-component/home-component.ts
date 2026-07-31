import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HttpClient } from '@angular/common/http';

type DogResponse = {
  message: string;
  status: string;
};

type DogCard = {
  name: string;
  description: string;
  imageUrl: string;
};

@Component({
  selector: 'Home',
  imports: [MatButton, MatToolbarModule],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css',
})
export class HomeComponent {
  dogos = signal<DogCard[]>([
    {
      name: 'Labrador',
      description: 'They are really friendly, great family dog. Intelligent fluffballs.',
      imageUrl: '',
    },
    { name: 'Vizsla', description: 'Beautiful loyal doggo.', imageUrl: '' },
    { name: 'Akita', description: 'A bit sassy, but extremely loyal to his owner.', imageUrl: '' },
  ]);

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchDogPics();
  }

  fetchDogPics(): void {
    this.http.get<DogResponse>('https://dog.ceo/api/breed/labrador/images/random').subscribe({
      next: (response) => {
        const dogs = this.dogos();
        dogs[0].imageUrl = response.message;
        this.dogos.set([...dogs]);
      },
      error: (err) => console.error('Labrador fetch failed:', err),
    });

    this.http.get<DogResponse>('https://dog.ceo/api/breed/vizsla/images/random').subscribe({
      next: (response) => {
        const dogs = this.dogos();
        dogs[1].imageUrl = response.message;
        this.dogos.set([...dogs]);
      },
      error: (err) => console.error('Vizsla fetch failed:', err),
    });

    this.http.get<DogResponse>('https://dog.ceo/api/breed/akita/images/random').subscribe({
      next: (response) => {
        const dogs = this.dogos();
        dogs[2].imageUrl = response.message;
        this.dogos.set([...dogs]);
      },
      error: (err) => console.error('Akita fetch failed:', err),
    });
  }
  onButtonClick(): void {
    this.fetchDogPics();
  }
}
