import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Output,
} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-hamburguer',
  templateUrl: './menu-hamburguer.component.html',
  styleUrl: './menu-hamburguer.component.css',
})
export class MenuHamburguerComponent {
  constructor(private router: Router, private elementRef: ElementRef) {}

  @Output() fecharMenu = new EventEmitter<void>();

  categorias: String[] = [
    'Saúde',
    'Política',
    'Transporte',
    'Educação',
  ];
  portais: String[] = ['G1', 'UOL', 'CNN', 'GOV', 'VEJA'];

  buscarPortal(nomePortal: String) {
    this.router.navigate([`noticias/${nomePortal.replace(/ /g, '_')}`]);
  }

  buscarCategoria(categoria: String) {
    this.router.navigate([`categoria/${categoria.replace(/ /g, '_')}`]);
  }

  fechar() {
    this.fecharMenu.emit();
  }

  @HostListener('document:click', ['$event'])
  handleClick(event: MouseEvent) {
    if (this.elementRef.nativeElement.contains(event.target)) {
      this.fechar();
    }
  }
}
