import { Component, Input, OnInit } from '@angular/core';
import { PreviaNoticia } from '../../core/models/previa-noticia';
import { Router } from '@angular/router';
import { RoteamentoService } from '../../core/services/rotas/roteamento.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-principais-noticias',
  templateUrl: './principais-noticias.component.html',
  styleUrl: './principais-noticias.component.css',
})
export class PrincipaisNoticiasComponent implements OnInit {
  constructor(
    private router: Router,
    private dadosRotas: RoteamentoService,
    private sanitizer: DomSanitizer
  ) { }

  @Input() previas: PreviaNoticia[] = [];

  imagensInjetaveis: SafeHtml[] = [];

  ngOnInit(): void {
    for (let i = 0; i < this.previas.length; i++) {
      this.sanitizar(i, this.previas[i].imagemCapa)
    }
  }

  sanitizar(index: number, imagem: string) {
    const regex = /https?:\/\/\S+(?=\b)/g;
    const linkImagem = imagem.match(regex);

    if (!linkImagem ||
      linkImagem[0] == 'https://conteudo.imguol.com.br/c/geral/3d/2021/05/25/placeholder-image-1621949831997_v2_300x225.jpg' ||
      linkImagem[0] == "https://conteudo.imguol.com.br/c/geral/3d/2021/05/25/placeholder-image-1621949831997_v2_300x200.jpg") {
      this.inserirImagemDefaulf(index);
    }
    else {
      this.imagensInjetaveis[index] = this.sanitizer.bypassSecurityTrustHtml(
        imagem)
    }
  }

  inserirImagemDefaulf(index: number) {
    this.imagensInjetaveis[index] = this.sanitizer.bypassSecurityTrustHtml(
      "<img src='../../../assets/images/imagem-indisponivel.png' alt='Imagem Indisponivel'/>"
    );
  }

  exibirNoticia(noticia: PreviaNoticia) {
    this.dadosRotas.setIdNoticia(noticia.id);
    this.router.navigate([`/noticia/${noticia.siteBuscado}/${noticia.dataPublicacao}/${noticia.titulo.replace(/ /g, '_')}`]);
  }
}
