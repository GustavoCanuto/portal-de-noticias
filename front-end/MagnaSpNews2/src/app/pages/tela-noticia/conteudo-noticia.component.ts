import { Component, ErrorHandler, OnInit } from '@angular/core';
import { Noticia } from '../../core/models/noticia';
import { ActivatedRoute, Router } from '@angular/router';
import { RoteamentoService } from '../../core/services/rotas/roteamento.service';
import { DomSanitizer, SafeHtml, Title } from '@angular/platform-browser';
import { HttpErrorResponse } from '@angular/common/http';
import { NoticiaService } from '../../core/services/noticia/noticia.service';
import { PreviaNoticia } from '../../core/models/previa-noticia';
import { format } from 'date-fns';
import { Subscription, catchError, of, timeout } from 'rxjs';
import { VerificarApiService } from '../../core/services/verificacao-api/verificar-api.service';

declare var twttr: any;

@Component({
  selector: 'app-conteudo-noticia',
  templateUrl: './conteudo-noticia.component.html',
  styleUrl: './conteudo-noticia.component.css',
})
export class ConteudoNoticiaComponent implements OnInit {
  noticia: Noticia = {
    id: 0,
    linkDaNoticiaOficial: '',
    categoria: '',
    autoria: '',
    dataPublicacao: '',
    dataModificada: '',
    titulo: '',
    sinopse: '',
    conteudo: '',
    siteBuscado: '',
    numeroVisualizacao: 0,
    tags: [],
  };

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dadosRotas: RoteamentoService,
    private sanitizer: DomSanitizer,
    private noticiaService: NoticiaService,
    private titleService: Title,
    private verificarApiService: VerificarApiService
  ) { }

  conteudoInjetavel!: SafeHtml;
  autoriaInjetavel!: SafeHtml;

  noticiasRelacionadas: PreviaNoticia[] = [];
  paginaNoticiasRelacionadas: number = 0;
  buscarPorTitulo: boolean = false;
  buscarPorCategoria: boolean = false;
  buscarPorTag: boolean = false;
  quantidadeDeTags!: number;
  tagBuscada: number = 0;
  possuiMaisNoticias: boolean = true;
  noticiaEncontrada: boolean = false;
  loading: boolean = false;
  loadingInicial: boolean = true;

  dataAtual: Date = new Date();
  fimPeriodo: string = format(this.dataAtual, "yyyy-MM-dd'T'HH:mm:ss");

  private subscriptionBuscaNoticia!: Subscription;
  private subscriptionBuscaNoticiasRelacionadas!: Subscription;

  tentarNovamenteNoticia: boolean = false;
  tentarNovamentePrevias: boolean = false;
  timeoutValue: number = 10000;

  linkPortal!: String;
  idNoticaEscolhida: number = this.dadosRotas.getIdNoticia();

  ngOnInit(): void {
    this.verificarApiService.verificarConexao();
    if (this.idNoticaEscolhida) {
      this.buscarNoticiaPeloId(this.idNoticaEscolhida);
    } else {
      this.buscarNoticiaPeloLink();
    }
  }

  buscarNoticiaPeloId(idNoticia: number) {
    this.subscriptionBuscaNoticia = this.noticiaService
      .exibirNoticiaCompletaPorId(idNoticia)
      .pipe(
        timeout(this.timeoutValue),
        catchError(error => {
          if (error.name == 'TimeoutError') {
            this.ativarTentarNovamente(this.subscriptionBuscaNoticia);
          } else if (error.status == 404) {
            this.loadingInicial = false;
            this.router.navigate(['/nao-encontrada']);
          }
          return of();
        })
      )
      .subscribe({
        next: (noticiaCompleta) => {
          this.noticia = noticiaCompleta;
          this.sanitizarConteudo();
          this.alterarLinkParaBuscaDaNoticiaOficial();
          this.noticiaEncontrada = true;
          this.buscarNoticiasRelacionadas();
          this.loadingInicial = false;
          this.titleService.setTitle(noticiaCompleta.titulo);
        },
        error: (error: HttpErrorResponse) => {
          this.loadingInicial = false;
          this.router.navigate([`/nao-encontrada`]);
        },
      });
  }

  buscarNoticiaPeloLink() {
    const tituloReportagem = this.route.snapshot.paramMap
      .get('nomeNoticia')?.replace(/_/g, ' ')!;
    const siteBuscado = this.route.snapshot.paramMap.get('portal')!;
    const dataPublicacao =
      this.route.snapshot.paramMap.get('dataPublicacao')!;
    this.subscriptionBuscaNoticia = this.noticiaService
      .exibirNoticiaCompletaPorParametrosLink(
        siteBuscado,
        dataPublicacao,
        tituloReportagem
      )
      .pipe(
        timeout(this.timeoutValue),
        catchError(error => {
          if (error.name == 'TimeoutError') {
            this.ativarTentarNovamente(this.subscriptionBuscaNoticia);
          } else if (error.status == 404) {
            this.loadingInicial = false;
            this.router.navigate(['/nao-encontrada']);
          }
          return of();
        })
      )
      .subscribe({
        next: (noticiaCompleta) => {
          this.noticia = noticiaCompleta;
          this.sanitizarConteudo();
          this.alterarLinkParaBuscaDaNoticiaOficial();
          this.noticiaEncontrada = true;
          this.buscarNoticiasRelacionadas();
          this.loadingInicial = false;
          this.titleService.setTitle(noticiaCompleta.titulo);
        },
        error: (error: HttpErrorResponse) => {
          this.loadingInicial = false;
          this.router.navigate(['/nao-encontrada']);
        },
      });
  }

  sanitizarConteudo() {
    this.conteudoInjetavel = this.sanitizer.bypassSecurityTrustHtml(
      this.noticia.conteudo
    );
    this.autoriaInjetavel = this.sanitizer.bypassSecurityTrustHtml(
      this.noticia.autoria
    );
  }

  alterarLinkParaBuscaDaNoticiaOficial() {
    switch (this.noticia.siteBuscado) {
      case 'G1':
        this.linkPortal = 'g1.globo.com';
        break;
      case 'UOL':
        this.linkPortal = 'uol.com.br';
        break;
      case 'VEJA':
        this.linkPortal = 'veja.abril.com.br';
        break;
      case 'CNN':
        this.linkPortal = 'cnnbrasil.com.br';
        break;
      case 'GOV':
        this.linkPortal = 'saopaulo.sp.gov.br';
        break;
    }
  }

  buscarNoticiasRelacionadas() {
    this.quantidadeDeTags = this.noticia.tags.length;
    if (this.noticiaEncontrada) {
      if (this.quantidadeDeTags > 0) {
        this.buscarPorTag = true;
      } else {
        this.buscarPorCategoria = true;
      }

      if (this.buscarPorTag) {
        this.buscaPorTag(this.fimPeriodo);
      }

      if (this.buscarPorTitulo) {
        this.buscaPorTitulo(this.fimPeriodo);
      }

      if (this.buscarPorCategoria) {
        this.buscaPorCategoria(this.fimPeriodo);
      }
    }
  }

  carregarMaisNoticias() {
    this.loading = true;
    this.paginaNoticiasRelacionadas = ++this.paginaNoticiasRelacionadas;

    if (this.buscarPorTag) {
      this.buscaPorTag(this.fimPeriodo);
    }

    if (this.buscarPorTitulo) {
      this.buscaPorTitulo(this.fimPeriodo);
    }

    if (this.buscarPorCategoria) {
      this.buscaPorCategoria(this.fimPeriodo);
    }
  }

  buscaPorTag(fimPeriodo: string) {
    this.subscriptionBuscaNoticiasRelacionadas =
      this.noticiaService
        .buscarNoticiaPorTag(
          this.paginaNoticiasRelacionadas,
          this.noticia.tags[this.tagBuscada].nome,
          fimPeriodo
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionBuscaNoticiasRelacionadas);
            return of();
          })
        )
        .subscribe({
          next: (previaNoticia) => {
            if (!previaNoticia.empty) {
              previaNoticia.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.noticiasRelacionadas.push(previaNoticia);
                }
              });
              this.loading = false;
            } else {
              if (this.tagBuscada < this.quantidadeDeTags - 1) {
                this.tagBuscada++;
                this.buscaPorTag(fimPeriodo);
              } else {
                this.paginaNoticiasRelacionadas = 0;
                this.buscarPorTag = false;
                this.buscarPorTitulo = true;
                this.buscaPorTitulo(fimPeriodo);
              }
            }
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
          },
        });
  }

  buscaPorTitulo(fimPeriodo: string) {
    this.noticia.tags.forEach((tag) => {
      this.subscriptionBuscaNoticiasRelacionadas =
        this.noticiaService
          .buscarNoticiaPorTitulo(
            this.paginaNoticiasRelacionadas,
            tag.nome,
            fimPeriodo
          )
          .pipe(
            timeout(this.timeoutValue),
            catchError(error => {
              this.ativarTentarNovamenteNoticiasRelacionadas(this.subscriptionBuscaNoticiasRelacionadas);
              return of();
            })
          )
          .subscribe({
            next: (previaNoticia) => {
              if (!previaNoticia.empty) {
                previaNoticia.content.forEach((previaNoticia) => {
                  if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                    this.noticiasRelacionadas.push(previaNoticia);
                  }
                });
              } else {
                this.paginaNoticiasRelacionadas = 0;
                this.buscarPorTitulo = false;
                this.buscarPorCategoria = true;
                this.buscaPorCategoria(fimPeriodo);
              }
            },
            error: (error: HttpErrorResponse) => {
              this.loading = false;
            },
          });
    });
  }

  buscaPorCategoria(fimPeriodo: string) {
    this.subscriptionBuscaNoticiasRelacionadas =
      this.noticiaService
        .buscarNoticiaPorCategoria(
          this.paginaNoticiasRelacionadas,
          fimPeriodo,
          this.noticia.categoria
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionBuscaNoticiasRelacionadas);
            return of();
          })
        )
        .subscribe({
          next: (previaNoticia) => {
            if (!previaNoticia.empty) {
              previaNoticia.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.noticiasRelacionadas.push(previaNoticia);
                }
              });
            } else {
              this.possuiMaisNoticias = false;
            }
            this.loading = false;
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
          },
        });
  }

  verificarSeJaContemPrevia(idPrevia: number) {
    if (
      !(
        this.noticiasRelacionadas.find((noticia) => noticia.id === idPrevia) ||
        idPrevia === this.noticia.id
      )
    ) {
      return true;
    } else {
      return false;
    }
  }

  redirecionarTelaDeBuscaPorTag(tag: String) {
    this.router.navigate([`/tag/${tag}`]);
  }

  ativarTentarNovamente(subscripition: Subscription) {
    subscripition.unsubscribe();
    this.loading = false;
    this.loadingInicial = false;
    this.tentarNovamenteNoticia = true;
  }

  recarregarNoticia() {
    this.tentarNovamenteNoticia = false;
    if (this.idNoticaEscolhida) {
      this.buscarNoticiaPeloId(this.idNoticaEscolhida);
    } else {
      this.buscarNoticiaPeloLink();
    }
  }

  ativarTentarNovamenteNoticiasRelacionadas(subscripition: Subscription) {
    subscripition.unsubscribe();
    this.loading = false;
    this.tentarNovamentePrevias = true;
  }

  recarregarPrevias() {
    this.tentarNovamentePrevias = true;
    this.loading = true;
    if (this.buscarPorTag) {
      this.buscaPorTag(this.fimPeriodo);
    }

    if (this.buscarPorTitulo) {
      this.buscaPorTitulo(this.fimPeriodo);
    }

    if (this.buscarPorCategoria) {
      this.buscaPorCategoria(this.fimPeriodo);
    }
  }
}
