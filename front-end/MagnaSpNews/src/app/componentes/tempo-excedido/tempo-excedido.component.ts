import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-tempo-excedido',
  templateUrl: './tempo-excedido.component.html',
  styleUrl: './tempo-excedido.component.css'
})
export class TempoExcedidoComponent {

  @Input() classePrevias:boolean = false;

  @Output() tentarNovamente = new EventEmitter<void>();

  emitirClick() {
    this.tentarNovamente.emit();
  }
}
