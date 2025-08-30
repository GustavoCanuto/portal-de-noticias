import { Component, Input, OnInit } from '@angular/core';
import { PreviaNoticia } from '../../core/models/previa-noticia';
import { Router } from '@angular/router';
import { RoteamentoService } from '../../core/services/rotas/roteamento.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-carrossel-noticias',
  templateUrl: './carrossel-noticias.component.html',
  styleUrl: './carrossel-noticias.component.css',
})
export class CarrosselNoticiasComponent implements OnInit {
  constructor(
    private router: Router,
    private dadosRotas: RoteamentoService,
    private sanitizer: DomSanitizer,
  ) { }

  @Input() tituloCarrosel!: String;
  @Input() previas: PreviaNoticia[] = [];

  previaAtual!: PreviaNoticia;
  imagemCapaInjetavel!: SafeHtml;
  indiceCarrossel: number[] = [];

  ngOnInit(): void {
    setTimeout(() => {
      this.previaAtual = this.previas[0];
      this.sanitizar();
      this.indiceCarrossel = Array.from(
        { length: this.previas.length },
        (_, index) => index
      );
    }, 5);
  }

  sanitizar() {
    const regex = /https?:\/\/\S+(?=\b)/g;
    const linkImagem = this.previaAtual.imagemCapa.match(regex);

    if (!linkImagem || 
      linkImagem[0] == 'https://conteudo.imguol.com.br/c/geral/3d/2021/05/25/placeholder-image-1621949831997_v2_300x225.jpg' || 
      linkImagem[0] == "https://conteudo.imguol.com.br/c/geral/3d/2021/05/25/placeholder-image-1621949831997_v2_300x200.jpg") {
      this.inserirImagemDefaulf();
    }
    else {
      this.imagemCapaInjetavel = this.sanitizer.bypassSecurityTrustHtml(
        this.previaAtual.imagemCapa
      )
    }
  }

  inserirImagemDefaulf() {
    this.imagemCapaInjetavel = this.sanitizer.bypassSecurityTrustHtml(
      "<img src='../../../assets/images/imagem-indisponivel.png' alt='Imagem Indisponivel'/>"
    );
  }

  proximaNoticia() {
    const currentIndex = this.previas.indexOf(this.previaAtual);
    const nextIndex = (currentIndex + 1) % this.previas.length;
    this.previaAtual = this.previas[nextIndex];
    this.sanitizar();
  }

  voltarNoticia() {
    const currentIndex = this.previas.indexOf(this.previaAtual);
    const previousIndex =
      (currentIndex - 1 + this.previas.length) % this.previas.length;
    this.previaAtual = this.previas[previousIndex];
    this.sanitizar();
  }

  selecionarNoticia(index: number) {
    this.previaAtual = this.previas[index];
    this.sanitizar();
  }

  exibirNoticia() {
    this.dadosRotas.setIdNoticia(this.previaAtual.id);
    this.router.navigate([`/noticia/${this.previaAtual.siteBuscado}/${this.previaAtual.dataPublicacao}/${this.previaAtual.titulo.replace(/ /g, '_')}`]);
  }
}
