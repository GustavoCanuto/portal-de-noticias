import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConteudoNoticiaComponent } from './conteudo-noticia.component';

describe('ConteudoNoticiaComponent', () => {
  let component: ConteudoNoticiaComponent;
  let fixture: ComponentFixture<ConteudoNoticiaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConteudoNoticiaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConteudoNoticiaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
