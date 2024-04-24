import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RoteamentoService {
  private idNotica!: number;

  constructor() { }

  setIdNoticia(idNotica: number) {
    this.idNotica = idNotica;
  }

  getIdNoticia() {
    return this.idNotica;
  }
}
