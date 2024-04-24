import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaDeBuscaComponent } from './tela-de-busca.component';

describe('TelaDeBuscaComponent', () => {
  let component: TelaDeBuscaComponent;
  let fixture: ComponentFixture<TelaDeBuscaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaDeBuscaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaDeBuscaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
