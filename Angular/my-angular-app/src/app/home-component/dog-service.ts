import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';

type DogResponse = {
  message: string;
  status: string;
};

type DogCard = {
  name: string;
  description: string;
  imageUrl: string;
};

Injectable();
export class DogService {
  private readonly http = inject(HttpClient);

  dogos = signal<DogCard[]>([
    {
      name: 'Labrador',
      description: 'They are really friendly, great family dog. Intelligent fluffballs.',
      imageUrl: '',
    },
    { name: 'Vizsla', description: 'Beautiful loyal doggo.', imageUrl: '' },
    { name: 'Akita', description: 'A bit sassy, but extremely loyal to his owner.', imageUrl: '' },
  ]);

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
