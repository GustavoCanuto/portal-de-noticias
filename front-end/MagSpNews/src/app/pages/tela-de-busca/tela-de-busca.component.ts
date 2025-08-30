import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NoticiaService } from '../../core/services/noticia/noticia.service';
import { PreviaNoticia } from '../../core/models/previa-noticia';
import { HttpErrorResponse } from '@angular/common/http';
import { format, sub } from 'date-fns';
import { Subscription, catchError, of, timeout } from 'rxjs';
import { VerificarApiService } from '../../core/services/verificacao-api/verificar-api.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tela-de-busca',
  templateUrl: './tela-de-busca.component.html',
  styleUrl: './tela-de-busca.component.css',
})
export class TelaDeBuscaComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private noticiaService: NoticiaService,
    private verificarApiService: VerificarApiService,
    private titleService: Title,
  ) { }
  parametroBusca: String = '';
  parametroEscolhido!: string;
  linkResumido!: String;
  linkPortal!: String;
  resultadoBusca: PreviaNoticia[] = [];
  pagina: number = 0;
  possuiMaisNoticias: boolean = true;
  loading: boolean = false;
  loadingInicial: boolean = true;

  dataAtual: Date = new Date();
  fimPeriodo: string = format(this.dataAtual, "yyyy-MM-dd'T'HH:mm:ss");
  dataFinal!: Date;
  inicioPeriodo!: string;

  subscriptionAtual!: Subscription;

  tentarNovamente: boolean = false;
  timeoutValue: number = 10000;

  siteForaDoAr:boolean = false;

  ngOnInit(): void {
    this.verificarApiService.verificarConexao();

    if (this.route.snapshot.paramMap.get('portal')) {
      this.parametroBusca = 'portal';
      this.buscarPorPortal(this.fimPeriodo);
    } else if (this.route.snapshot.paramMap.get('palavra')) {
      this.parametroBusca = 'palavra';
      this.buscaPorPalavra(this.fimPeriodo);
    } else if (this.route.snapshot.paramMap.get('categoria')) {
      this.parametroBusca = 'categoria';
      this.buscaPorCategoria(this.fimPeriodo);
    } else if (this.route.snapshot.paramMap.get('tag')) {
      this.parametroBusca = 'tag';
      this.buscaPorTag(this.fimPeriodo);
    }
  }

  carregarMaisNoticias() {
    this.loading = true;
    this.pagina = ++this.pagina;
    if (this.parametroBusca == 'portal') {
      this.buscarPorPortal(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'palavra') {
      this.buscaPorPalavra(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'categoria') {
      this.buscaPorCategoria(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'tag') {
      this.buscaPorTag(this.fimPeriodo, this.inicioPeriodo);
    }
  }

  buscarPorPortal(fimPeriodo: string, inicioPeriodo?: string) {
    this.parametroEscolhido = this.route.snapshot.paramMap.get('portal')!;
    this.titleService.setTitle(`Busca por ${this.parametroEscolhido}`);
    switch (this.parametroEscolhido) {
      case 'G1':
        this.linkPortal = 'https://g1.globo.com/';
        this.linkResumido = 'g1.globo.com';
        break;
      case 'UOL':
        this.linkPortal = 'https://www.uol.com.br/';
        this.linkResumido = 'uol.com.br';
        break;
      case 'VEJA':
        this.linkPortal = 'https://veja.abril.com.br/';
        this.linkResumido = 'veja.abril.com.br';
        break;
      case 'CNN':
        this.linkPortal = 'https://www.cnnbrasil.com.br/';
        this.linkResumido = 'cnnbrasil.com.br';
        break;
      case 'GOV':
        this.linkPortal = 'https://www.saopaulo.sp.gov.br/';
        this.linkResumido = 'saopaulo.sp.gov.br';
        break;
    }
    this.subscriptionAtual =
      this.noticiaService
        .buscarPorSiteFonte(
          this.pagina,
          this.parametroEscolhido,
          fimPeriodo,
          inicioPeriodo,
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            if(error.error == "Site fora do Ar!"){
              this.loadingInicial = false;
              this.siteForaDoAr = true;
              setTimeout(()=>{
                this.siteForaDoAr = false;
                this.buscarPorPortal(fimPeriodo, inicioPeriodo)
              },420000)
            }else{
              this.ativarTentarNovamente(this.subscriptionAtual);
            }
            return of();
          })
        )
        .subscribe({
          next: (noticias) => {
            (noticias)
            if (!noticias.empty) {
              this.resultadoBusca.push(...noticias.content);
            } else {
              this.possuiMaisNoticias = false;
            }
            this.loading = false;
            this.loadingInicial = false;
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
            this.loadingInicial = false;
          },
        });
  }

  buscarPorTitulo: boolean = true;
  buscarPelaSinopse: boolean = false;
  buscarPeloConteudo: boolean = false;
  buscarPorCategoria: boolean = false;
  buscaPorPalavra(fimPeriodo: string, inicioPeriodo?: string) {
    this.parametroEscolhido = this.route.snapshot.paramMap
      .get('palavra')
      ?.replace(/_/g, ' ')!;

    this.titleService.setTitle(`Busca por ${this.parametroEscolhido}`);

    if (this.buscarPorTitulo) {
      this.buscaPorTitulo(fimPeriodo, inicioPeriodo);
    }
    if (this.buscarPelaSinopse) {
      this.buscaPelaSinopse(fimPeriodo, inicioPeriodo);
    }
    if (this.buscarPeloConteudo) {
      this.buscaPeloConteudo(fimPeriodo, inicioPeriodo);
    }
    if (this.buscarPorCategoria) {
      this.buscaPorCategoria(fimPeriodo, this.parametroEscolhido);
    }
  }

  buscaPorTitulo(fimPeriodo: string, inicioPeriodo?: string) {
    this.subscriptionAtual =
      this.noticiaService
        .buscarNoticiaPorTitulo(
          this.pagina,
          this.parametroEscolhido,
          fimPeriodo,
          inicioPeriodo
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionAtual);
            return of();
          })
        )
        .subscribe({
          next: (noticias) => {
            if (!noticias.empty) {
              noticias.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.resultadoBusca.push(previaNoticia);
                }
              });
              this.loading = false;
              this.loadingInicial = false;
            } else {
              this.pagina = 0;
              this.buscarPorTitulo = false;
              this.buscarPelaSinopse = true;
              this.buscaPelaSinopse(fimPeriodo, inicioPeriodo);
            }
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
            this.loadingInicial = false;
          },
        });
  }

  buscaPelaSinopse(fimPeriodo: string, inicioPeriodo?: string) {
    this.subscriptionAtual =
      this.noticiaService
        .buscarPelaSinopse(
          this.pagina,
          this.parametroEscolhido,
          fimPeriodo,
          inicioPeriodo
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionAtual);
            return of();
          })
        )
        .subscribe({
          next: (noticias) => {
            if (!noticias.empty) {
              noticias.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.resultadoBusca.push(previaNoticia);
                }
              });
              this.loading = false;
              this.loadingInicial = false;
            } else {
              this.pagina = 0;
              this.buscarPelaSinopse = false;
              this.buscarPeloConteudo = true;
              this.buscaPeloConteudo(fimPeriodo, inicioPeriodo);
            }
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
            this.loadingInicial = false;
          },
        });
  }

  buscaPeloConteudo(fimPeriodo: string, inicioPeriodo?: string) {
    this.subscriptionAtual =
      this.noticiaService
        .buscarPeloConteudo(
          this.pagina,
          this.parametroEscolhido,
          fimPeriodo,
          inicioPeriodo
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionAtual);
            return of();
          })
        )
        .subscribe({
          next: (noticias) => {
            if (!noticias.empty) {
              noticias.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.resultadoBusca.push(previaNoticia);
                }
              });
              this.loading = false;
              this.loadingInicial = false;
            } else {
              this.pagina = 0;
              this.buscarPeloConteudo = false;
              this.buscarPorCategoria = true;
              this.buscaPorCategoria(
                fimPeriodo,
                inicioPeriodo,
                this.parametroEscolhido
              );
            }
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
            this.loadingInicial = false;
          },
        });
  }

  buscaPorCategoria(
    fimPeriodo: string,
    inicioPeriodo?: string,
    palavra?: string
  ) {
    this.parametroEscolhido = this.route.snapshot.paramMap
      .get('categoria')
      ?.replace(/_/g, ' ')!;
    if (palavra) {
      this.parametroEscolhido = palavra;
    }

    this.titleService.setTitle(`Busca por ${this.parametroEscolhido}`);

    const subItensCategoria: string[] = [];
    const subItensSaude: string[] = [
      'Saúde',
      'Bem-estar',
      'Prevenção',
      'Tratamento',
      'Medicina',
      'Diagnóstico',
      'Nutrição',
      'Exercício',
      'Cuidados',
      'Higiene',
      'Vacinação',
      'Dengue',
    ];
    const subItensPolitica: string[] = [
      'Política',
      'Economia',
      'Governo',
      'Democracia',
      'Eleição',
      'Partido político',
      'Orçamento',
      'Inflação',
      'Desenvolvimento econômico',
      'Mercado',
      'Imposto',
    ];
    const subItensTransporte: string[] = [
      'Ônibus',
      'Metro',
      'Trânsito',
      'CPTM',
      'Estrada',
      'Rodovia',
      'Trem',
      'Transporte público',
      'IPVA',
    ];
    const subItensEducacao: string[] = [
      'Escola',
      'Ensino',
      'Aprendizagem',
      'Professor',
      'Aluno',
      'Matéria',
      'Currículo',
      'Pedagogia',
      'Universidade',
      'Conhecimento',
    ];
    switch (this.parametroEscolhido) {
      case 'Saúde':
        subItensCategoria.push(...subItensSaude);
        break;
      case 'Política':
        subItensCategoria.push(...subItensPolitica);
        break;
      case 'Transporte':
        subItensCategoria.push(...subItensTransporte);
        break;
      case 'Educação':
        subItensCategoria.push(...subItensEducacao);
        break;
    }
    this.subscriptionAtual =
      this.noticiaService
        .buscarNoticiaPorCategoria(
          this.pagina,
          fimPeriodo,
          this.parametroEscolhido,
          subItensCategoria,
          inicioPeriodo
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionAtual);
            return of();
          })
        )
        .subscribe({
          next: (noticias) => {
            if (!noticias.empty) {
              noticias.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.resultadoBusca.push(previaNoticia);
                }
              });
            } else {
              this.buscarPorCategoria = false;
              this.possuiMaisNoticias = false;
            }
            this.loading = false;
            this.loadingInicial = false;
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
            this.loadingInicial = false;
          },
        });
  }

  buscaPorTag(fimPeriodo: string, inicioPeriodo?: string) {
    this.parametroEscolhido = this.route.snapshot.paramMap
      .get('tag')
      ?.replace(/_/g, ' ')!;

    this.titleService.setTitle(`Busca por ${this.parametroEscolhido}`);

    this.subscriptionAtual =
      this.noticiaService
        .buscarNoticiaPorTag(
          this.pagina,
          this.parametroEscolhido,
          fimPeriodo,
          inicioPeriodo
        )
        .pipe(
          timeout(this.timeoutValue),
          catchError(error => {
            this.ativarTentarNovamente(this.subscriptionAtual);
            return of();
          })
        )
        .subscribe({
          next: (noticias) => {
            if (!noticias.empty) {
              noticias.content.forEach((previaNoticia) => {
                if (this.verificarSeJaContemPrevia(previaNoticia.id)) {
                  this.resultadoBusca.push(previaNoticia);
                }
              });
            } else {
              this.possuiMaisNoticias = false;
            }
            this.loading = false;
            this.loadingInicial = false;
          },
          error: (error: HttpErrorResponse) => {
            this.loading = false;
            this.loadingInicial = false;
          },
        });
  }

  verificarSeJaContemPrevia(idPrevia: number) {
    if (!this.resultadoBusca.find((noticia) => noticia.id === idPrevia)) {
      return true;
    } else {
      return false;
    }
  }

  mostrarFiltros: boolean = false;
  filtros: String[] = [
    'Todas Notícias',
    'Últimas 24 Horas',
    'Última Semana',
    'Último Mês',
  ];

  filtroSelecionado: String = 'Todas Notícias';
  selecionarFiltro(filtro: String) {
    this.pagina = 0;
    this.possuiMaisNoticias = true;
    this.resultadoBusca = [];
    this.filtroSelecionado = filtro;
    this.loadingInicial = true;

    switch (filtro) {
      case 'Últimas 24 Horas':
        this.dataFinal = sub(this.dataAtual, { hours: 24 });
        this.inicioPeriodo = format(this.dataFinal, "yyyy-MM-dd'T'HH:mm:ss");
        break;
      case 'Última Semana':
        this.dataFinal = sub(this.dataAtual, { days: 7 });
        this.inicioPeriodo = format(this.dataFinal, "yyyy-MM-dd'T'HH:mm:ss");
        break;
      case 'Último Mês':
        this.dataFinal = sub(this.dataAtual, { days: 30 });
        this.inicioPeriodo = format(this.dataFinal, "yyyy-MM-dd'T'HH:mm:ss");
        break;
      case 'Todas Notícias':
        this.inicioPeriodo = '';
        break;
      default:
        this.dataFinal = sub(this.dataAtual, { days: 30 });
        this.inicioPeriodo = format(this.dataAtual, "yyyy-MM-dd'T'HH:mm:ss");
        break;
    }

    if (this.parametroBusca == 'portal') {
      this.buscarPorPortal(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'palavra') {
      this.buscaPorPalavra(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'categoria') {
      this.buscaPorCategoria(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'tag') {
      this.buscaPorTag(this.fimPeriodo, this.inicioPeriodo);
    }
  }

  ativarTentarNovamente(subscripition: Subscription) {
    subscripition.unsubscribe();
    this.loading = false;
    this.loadingInicial = false;
    this.tentarNovamente = true;
  }

  recarregar() {
    this.tentarNovamente = false;
    this.loading = true;
    if (this.parametroBusca == 'portal') {
      this.buscarPorPortal(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'palavra') {
      this.buscaPorPalavra(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'categoria') {
      this.buscaPorCategoria(this.fimPeriodo, this.inicioPeriodo);
    } else if (this.parametroBusca == 'tag') {
      this.buscaPorTag(this.fimPeriodo, this.inicioPeriodo);
    }
  }
}
