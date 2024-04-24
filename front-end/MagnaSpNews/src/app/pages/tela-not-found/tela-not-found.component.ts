import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tela-not-found',
  templateUrl: './tela-not-found.component.html',
  styleUrl: './tela-not-found.component.css',
})
export class TelaNotFoundComponent implements OnInit {
  ngOnInit(): void {
    window.scrollTo(0, 0);
  }
}
