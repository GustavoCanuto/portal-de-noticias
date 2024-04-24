import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'limitarCaracteres',
})
export class LimitarCaracteresPipe implements PipeTransform {
  transform(value: String | undefined, limite: number): string {
    if (typeof value !== 'string' || value === undefined) {
      return '';
    }
    if (value.length <= limite) {
      return value;
    }
    const textoCortado = value.slice(0, limite);
    const ultimoEspaco = textoCortado.lastIndexOf(' ');
    if (ultimoEspaco === -1) {
      return textoCortado + '...';
    } else {
      return textoCortado.slice(0, ultimoEspaco) + '...';
    }
  }
}
