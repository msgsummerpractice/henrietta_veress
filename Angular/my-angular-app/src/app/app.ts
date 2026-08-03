import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { Navbar } from './navbar/navbar';
import { MatToolbar, MatToolbarModule } from '@angular/material/toolbar';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

type DogResponse = {
  message: string;
  status: string;
}

type DogCard = {
  name: string;
  description: string;
  imageUrl:string;
  status: 'loading' | 'loaded' | 'error';
}

@Component({
  selector: 'app-root',
  imports: [CommonModule, MatButton, Navbar, MatToolbarModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {

  dogos = signal<DogCard[]>([
      { 
        name: 'Labrador', 
        description: 'They are really friendly, great family dog. Intelligent fluffballs.', 
        imageUrl: '', 
        status: 'loading' 
      },
      { 
        name: 'Vizsla', 
        description: 'Beautiful loyal doggo.', 
        imageUrl: '', 
        status: 'loading' 
      },
      { 
        name: 'Akita', 
        description: 'A bit sassy, but extremely loyal to his owner.', 
        imageUrl: '', 
        status: 'loading' 
      },
    ]);

  constructor(private http: HttpClient) {}

  fetchDogPics(): void {

    this.http.get<DogResponse>('https://dog.ceo/api/breed/labrador/images/random')
      .subscribe({
         next: (response) => {
          this.dogos.update(list =>
            list.map(dog =>
              dog.name === 'Labrador'
                ? { ...dog, imageUrl: response.message, status: 'loaded' }
                : dog
            )
          );
        },
        error: (err) => {
          console.error('Labrador fetch failed:', err);

          this.dogos.update(list =>
            list.map(dog =>
              dog.name === 'Labrador'
                ? {
                    ...dog,
                    status: 'error'
                  }
                : dog
            )
          );
        },
      });

    this.http.get<DogResponse>('https://dog.ceo/api/breed/vizsla/images/random')
      .subscribe({
        next: (response) => {
          this.dogos.update(list =>
            list.map(dog =>
              dog.name === 'Vizsla'
                ? { ...dog, imageUrl: response.message, status: 'loaded' }
                : dog
            )
          );
        },
        error: (err) => {
          console.error('Vizsla fetch failed:', err);

          this.dogos.update(list =>
            list.map(dog =>
              dog.name === 'Vizsla'
                ? {
                    ...dog,
                    status: 'error'
                  }
                : dog
            )
          );
        },
      });

    this.http.get<DogResponse>('https://dog.ceo/api/breed/akita/images/random')
     .subscribe({
        next: (response) => {
          this.dogos.update(list =>
            list.map(dog =>
              dog.name === 'Akita'
                ? { ...dog, imageUrl: response.message, status: 'loaded' }
                : dog
            )
          );
        },
        error: (err) => {
          console.error('Akita fetch failed:', err);

          this.dogos.update(list =>
            list.map(dog =>
              dog.name === 'Akita'
                ? {
                    ...dog,
                    status: 'error'
                  }
                : dog
            )
          );
        },
      });
  }
   onButtonClick(): void {
    this.fetchDogPics();
  }

}
