import { ConteudoNoticiaComponent } from './pages/tela-noticia/conteudo-noticia.component';
import { NgModule } from '@angular/core';
import { RouteReuseStrategy, RouterModule, Routes } from '@angular/router';
import { TelaInicioComponent } from './pages/tela-inicio/tela-inicio.component';
import { TelaDeBuscaComponent } from './pages/tela-de-busca/tela-de-busca.component';
import { TelaNotFoundComponent } from './pages/tela-not-found/tela-not-found.component';
import { CustomReuseStrategy } from './custom-reuse-estrategy';
import { SobreNosComponent } from './pages/sobre-nos/sobre-nos.component';
import { TelaManutencaoComponent } from './pages/tela-manutencao/tela-manutencao.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: TelaInicioComponent,
    data: {
      reuseComponent: true,
    },
  },
  {
    path: 'noticias/:portal',
    component: TelaDeBuscaComponent,
    data: {
      reuseComponent: true,
    },
  },
  {
    path: 'busca/:palavra',
    component: TelaDeBuscaComponent,
    data: {
      reuseComponent: true,
    },
  },
  {
    path: 'categoria/:categoria',
    component: TelaDeBuscaComponent,
    data: {
      reuseComponent: true,
    },
  },
  {
    path: 'tag/:tag',
    component: TelaDeBuscaComponent,
    data: {
      reuseComponent: true,
    },
  },
  {
    path: 'noticia/:portal/:dataPublicacao/:nomeNoticia',
    component: ConteudoNoticiaComponent,
    data: {
      reuseComponent: true,
    },
  },
  {
    path: 'nao-encontrada',
    component: TelaNotFoundComponent,
  },
  {
    path: 'sobre-nos',
    component: SobreNosComponent,
  },
  {
    path: 'manutencao',
    component: TelaManutencaoComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
  providers: [{ provide: RouteReuseStrategy, useClass: CustomReuseStrategy }],
})
export class AppRoutingModule {}
