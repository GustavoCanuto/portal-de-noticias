import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcabouConteudoComponent } from './acabou-conteudo.component';

describe('AcabouConteudoComponent', () => {
  let component: AcabouConteudoComponent;
  let fixture: ComponentFixture<AcabouConteudoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AcabouConteudoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AcabouConteudoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
