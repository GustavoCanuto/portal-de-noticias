import { Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { NoticiaService } from '../../core/services/noticia/noticia.service';
import { TagService } from '../../core/services/tag/tag.service';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, filter, switchMap } from 'rxjs';
import { Tag } from '../../core/models/noticia';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-cabecalho',
  templateUrl: './cabecalho.component.html',
  styleUrl: './cabecalho.component.css',
})
export class CabecalhoComponent implements OnInit {
  @Input() titulo!: string;
  cabecalhoFixo: boolean = false;
  barraDePesquisaAberta: boolean = false;

  @Output() bloquearScroll = new EventEmitter<void>();
  @Output() liberarScroll = new EventEmitter<void>();
  menuAberto: boolean = false;

  pesquisa!: string;
  pagina: number = 0;

  larguraDaTela!: number;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 50) {
      this.cabecalhoFixo = true;
    } else {
      this.cabecalhoFixo = false;
    }
  }

  resultadoBuscaTag: Tag[] = [];

  constructor(private router: Router, private tagService: TagService) { }

  ngOnInit(): void {
    this.larguraDaTela = window.innerWidth;
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.larguraDaTela = window.innerWidth;
  }

  autocompleteAberto: boolean = false;
  timer: any;
  onInput(): void {
    clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      if (this.pesquisa) {
        this.autocompleteAberto = true;
        this.tagService.buscarNomeTag(this.pesquisa).subscribe({
          next: (listaTags) => {
            this.resultadoBuscaTag = listaTags.content;
          },
          error: (error: HttpErrorResponse) => {
            this.resultadoBuscaTag = [];
          },
        });
      } else {
        this.resultadoBuscaTag = [];
      }
    }, 300);
  }

  redirecionar(titulo: String) {
    if (titulo == 'MagnaSp News') {
      this.router.navigate(['']);
    } else {
      this.router.navigate([`/categoria/${titulo.replace(/ /g, '_')}`]);
    }
  }

  pesquisar() {
    if (this.pesquisa.length > 1) {
      this.router.navigate([`/busca/${this.pesquisa.replace(/ /g, '_')}`]);
    }
  }

  completarCampoDeBuca(nomeTag: string) {
    this.pesquisa = nomeTag;
    this.router.navigate([`/tag/${nomeTag}`]);
  }

  fecharAutocomplete() {
    setTimeout(() => {
      this.barraDePesquisaAberta = false;
      this.autocompleteAberto = false;
    }, 200)
  }

  exibirHomeNovaAba(event: MouseEvent) {
    const linkOrigem = window.location.origin;
    if (event.button === 1) {
      const url = `${linkOrigem}/home`;
      window.open(url, '_blank');
    }
  }

  abrirMenu(){
    this.menuAberto = true
    this.bloquearScroll.emit()
  }

  fecharMenu(){
    this.menuAberto = false
    this.liberarScroll.emit()
  }
}
