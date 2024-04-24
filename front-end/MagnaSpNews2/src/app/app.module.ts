import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule, HAMMER_GESTURE_CONFIG, HammerModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BarraNavegacaoComponent } from './componentes/barra-navegacao/barra-navegacao.component';
import { CabecalhoComponent } from './componentes/cabecalho/cabecalho.component';
import { RodapeComponent } from './componentes/rodape/rodape.component';
import { PreviaNoticiaComponent } from './componentes/previa-noticia/previa-noticia.component';
import { CarrosselNoticiasComponent } from './componentes/carrossel-noticias/carrossel-noticias.component';
import { PrincipaisNoticiasComponent } from './componentes/principais-noticias/principais-noticias.component';
import { TelaInicioComponent } from './pages/tela-inicio/tela-inicio.component';
import { ConteudoNoticiaComponent } from './pages/tela-noticia/conteudo-noticia.component';
import { TelaDeBuscaComponent } from './pages/tela-de-busca/tela-de-busca.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TelaNotFoundComponent } from './pages/tela-not-found/tela-not-found.component';
import { LimitarCaracteresPipe } from './core/pipes/limitar-caracteres.pipe';
import { MyHammerConfig } from './hammer-config';
import { MenuHamburguerComponent } from './componentes/menu-hamburguer/menu-hamburguer.component';
import { HttpClientModule } from '@angular/common/http';
import { SobreNosComponent } from './pages/sobre-nos/sobre-nos.component';
import { CustomErrorHandler } from './error-handler';
import { TelaManutencaoComponent } from './pages/tela-manutencao/tela-manutencao.component';
import { SiteForaDoArComponent } from './componentes/site-fora-do-ar/site-fora-do-ar.component';
import { TempoExcedidoComponent } from './componentes/tempo-excedido/tempo-excedido.component';
import { LoadingComponent } from './componentes/loading/loading.component';
import { AcabouConteudoComponent } from './componentes/acabou-conteudo/acabou-conteudo.component';
import { BtnVerMaisComponent } from './componentes/btn-ver-mais/btn-ver-mais.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


@NgModule({
  declarations: [
    AppComponent,
    BarraNavegacaoComponent,
    CabecalhoComponent,
    RodapeComponent,
    PreviaNoticiaComponent,
    CarrosselNoticiasComponent,
    PrincipaisNoticiasComponent,
    TelaInicioComponent,
    ConteudoNoticiaComponent,
    TelaDeBuscaComponent,
    TelaNotFoundComponent,
    LimitarCaracteresPipe,
    MenuHamburguerComponent,
    SobreNosComponent,
    TelaManutencaoComponent,
    SiteForaDoArComponent,
    TempoExcedidoComponent,
    LoadingComponent,
    AcabouConteudoComponent,
    BtnVerMaisComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HammerModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  exports: [LimitarCaracteresPipe],
  providers: [{ provide: HAMMER_GESTURE_CONFIG, useClass: MyHammerConfig }, { provide: ErrorHandler, useClass: CustomErrorHandler }, provideAnimationsAsync()],
  bootstrap: [AppComponent]
})
export class AppModule { }
