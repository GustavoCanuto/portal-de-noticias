import { Component, HostListener, OnInit } from '@angular/core';
import { NoticiaService } from '../../core/services/noticia/noticia.service';
import { HttpErrorResponse } from '@angular/common/http';
import { PreviaNoticia } from '../../core/models/previa-noticia';
import { NavigationStart, Router } from '@angular/router';
import { Subscription, catchError, filter, of, timeout } from 'rxjs';
import { VerificarApiService } from '../../core/services/verificacao-api/verificar-api.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tela-inicio',
  templateUrl: './tela-inicio.component.html',
  styleUrl: './tela-inicio.component.css',
})
export class TelaInicioComponent implements OnInit {
  tempoInativo!: ReturnType<typeof setTimeout>;
  principaisNoticias: PreviaNoticia[] = [];
  paginaPrincipaisNoticias: number = 0;
  maisLidas: PreviaNoticia[] = [];
  paginaMaisLidas: number = 0;
  ultimasNoticias: PreviaNoticia[] = [];
  paginaUltimasNoticias: number = 0;
  previasCards: PreviaNoticia[] = [];
  paginaPreviasCards: number = 0;
  possuiMaisNoticias: boolean = true;
  loading: boolean = false;
  loadingInicial: boolean = true;

  subscriptionAtual!: Subscription;

  tentarNovamenteTelaCompleta: boolean = false;
  tentarNovamentePrevias: boolean = false;
  timeoutValue: number = 7000;

  constructor(
    private noticiaService: NoticiaService,
    private router: Router,
    private verificarApiService: VerificarApiService,
    private titleService: Title,
  ) { }

  @HostListener('document:mousemove', ['$event'])
  @HostListener('document:keypress', ['$event'])
  @HostListener('window:scroll', ['$event'])
  reiniciarContador() {
    clearTimeout(this.tempoInativo);
    this.tempoInativo = setTimeout(() => {
      location.reload();
    }, 300000);
  }

  desativarContador() {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationStart))
      .subscribe(() => {
        clearTimeout(this.tempoInativo);
      });
  }

  ngOnInit(): void {
    this.titleService.setTitle('MagnaSp News');
    this.verificarApiService.verificarConexao();

    this.reiniciarContador();
    this.desativarContador();

    this.buscarPrincipaisNoticias(this.paginaPrincipaisNoticias);
  }

  verMaisNoticias() {
    this.loading = true;
    this.paginaPreviasCards++;
    this.buscarTodasPrevias();
  }

  buscarPrincipaisNoticias(pagina: number) {
    this.noticiaService.listarPreviasPrincipaisNoticias(pagina)
    .pipe(
      timeout(this.timeoutValue),
      catchError(error => {
        this.ativarTentarNovamenteTelaCompleta(this.subscriptionAtual);
        return of();
      })
    )
    .subscribe({
      next: (noticias) => {
        noticias.content.forEach((previa)=>{
          if(this.principaisNoticias.length < 3 && !previa.imagemCapa.includes("src='null'")){
            this.principaisNoticias.push(previa);
          }
        })
        this.loadingInicial = false;
        if (this.principaisNoticias.length < 3 && !noticias.empty) {
          this.buscarPrincipaisNoticias(++pagina)
        }
        if(this.principaisNoticias.length >= 3 || noticias.empty){
          this.buscarUltimasNoticias(this.paginaUltimasNoticias);
        }
      },
      error: (error: HttpErrorResponse) => {
        this.loadingInicial = false;
        this.buscarUltimasNoticias(this.paginaUltimasNoticias);
      },
    });
  }

  buscarUltimasNoticias(pagina: number) {
    this.subscriptionAtual =
    this.noticiaService.listarPreviasUltimasNoticias(pagina)
    .pipe(
      timeout(this.timeoutValue),
      catchError(error => {
        this.ativarTentarNovamenteTelaCompleta(this.subscriptionAtual);
        return of();
      })
    )
    .subscribe({
      next: (noticias) => {
        noticias.content.forEach((previa) => {
          if (this.ultimasNoticias.length < 5 && !previa.imagemCapa.includes("src='null'")) {
            this.ultimasNoticias.push(previa);
          }
        });
        this.loadingInicial = false;
        if (this.ultimasNoticias.length < 5 && !noticias.empty) {
          this.buscarUltimasNoticias(++pagina)
        }
        if(this.ultimasNoticias.length >= 5 || noticias.empty){
          this.buscarMaisLidas(this.paginaMaisLidas);
        }
      },
      error: (error: HttpErrorResponse) => {
        this.loadingInicial = false;
        this.buscarMaisLidas(this.paginaMaisLidas);
      },
    });
  }

  buscarMaisLidas(pagina: number) {
    this.subscriptionAtual =
    this.noticiaService.listarPreviasMaisLidas(pagina)
    .pipe(
      timeout(this.timeoutValue),
      catchError(error => {
        this.ativarTentarNovamenteTelaCompleta(this.subscriptionAtual);
        return of();
      })
    )
    .subscribe({
      next: (noticias) => {
        noticias.content.forEach((previa) => {
          if (this.maisLidas.length < 5 && !previa.imagemCapa.includes("src='null'")) {
            this.maisLidas.push(previa);
          }
        });
        this.loadingInicial = false;
        if (this.maisLidas.length < 5 && !noticias.empty) {
          this.buscarMaisLidas(++pagina)
        }
        if(this.maisLidas.length >=5 || noticias.empty){
          this.buscarTodasPrevias();
        }
      },
      error: (error: HttpErrorResponse) => {
        this.loadingInicial = false;
        this.buscarTodasPrevias();
      },
    });
  }

  buscarTodasPrevias() {
    this.subscriptionAtual =
    this.noticiaService
      .listarTodasPrevias(this.paginaPreviasCards)
      .pipe(
        timeout(this.timeoutValue),
        catchError(error => {
          this.ativarTentarNovamentePrevias(this.subscriptionAtual);
          return of();
        })
      )
      .subscribe({
        next: (noticias) => {
          if (!noticias.empty) {
            this.previasCards.push(...noticias.content);
          } else {
            this.possuiMaisNoticias = false;
          }
          this.loadingInicial = false;
          this.loading = false;
        },
        error: (error: HttpErrorResponse) => {
          this.loading = false;
          this.loadingInicial = false;
        },
      });
  }

  ativarTentarNovamenteTelaCompleta(subscripition: Subscription) {
    subscripition.unsubscribe();
    this.loadingInicial = false;
    this.tentarNovamenteTelaCompleta = true;
  }

  recarregarTelaCompleta() {
    this.tentarNovamenteTelaCompleta = false;
    this.reiniciarContador();
    this.desativarContador();
    this.buscarPrincipaisNoticias(this.paginaPrincipaisNoticias);
  }

  ativarTentarNovamentePrevias(subscripition: Subscription){
    subscripition.unsubscribe();
    this.loading = false;
    this.tentarNovamentePrevias = true;
  }

  recarregarPrevias(){
    this.tentarNovamentePrevias = false;
    this.loading = false;
    this.buscarTodasPrevias();
  }
}
