import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HttpClient } from '@angular/common/http';
import { WordCountPipe } from '../pipes/word-count-pipe';
import { App } from '../app';
import { DogService } from './dog-service';

@Component({
  selector: 'Home',
  imports: [MatButton, MatToolbarModule, WordCountPipe, App],
  templateUrl: './home-component.html',
  providers: [DogService],
})
export class HomeComponent {

  private readonly dogService = inject(DogService);

  dogos = this.dogService.dogos;

  onButtonClick(): void {
    this.dogService.fetchDogPics();
  }
}
