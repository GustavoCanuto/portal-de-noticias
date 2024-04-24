import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaManutencaoComponent } from './tela-manutencao.component';

describe('TelaManutencaoComponent', () => {
  let component: TelaManutencaoComponent;
  let fixture: ComponentFixture<TelaManutencaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaManutencaoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaManutencaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
