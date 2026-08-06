import { Component, inject, OnInit } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { WordCountPipe } from '../pipes/word-count-pipe';
import { App } from '../app';
import { DogService } from './dog-service';

@Component({
  selector: 'Home',
  imports: [MatButton, RouterLink, WordCountPipe],
  templateUrl: './home-component.html',
  providers: [DogService],
})
export class HomeComponent implements OnInit {
  private readonly dogService = inject(DogService);
  dogos = this.dogService.dogos;

   ngOnInit() {
        this.dogService.fetchDogPics();
  }

  onButtonClick(): void {
    this.dogService.fetchDogPics();
  }
}
