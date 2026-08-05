import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { WordCountPipe } from '../pipes/word-count-pipe';
import { App } from '../app';
import { DogService } from '../home-component/dog-service';

@Component({
  selector: 'app-gallery',
  imports: [MatButton, MatToolbarModule, WordCountPipe],
  templateUrl: './gallery-component.html',
  styleUrl: './gallery-component.css',
  providers: [DogService],
})
export class GalleryComponent {
  private readonly dogService = inject(DogService);

  dogos = this.dogService.dogos;

  onButtonClick(): void {
    this.dogService.fetchDogPics();
  }
}
