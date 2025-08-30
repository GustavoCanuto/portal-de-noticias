import { Component, OnInit } from '@angular/core';
import { VerificarApiService } from './core/services/verificacao-api/verificar-api.service';
import { NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'MagnaSpNews';

  constructor(private verificarApiService: VerificarApiService) { }

  ngOnInit(): void {
    this.verificarApiService.verificarConexao();
  }
}
