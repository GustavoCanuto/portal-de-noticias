import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TempoExcedidoComponent } from './tempo-excedido.component';

describe('TempoExcedidoComponent', () => {
  let component: TempoExcedidoComponent;
  let fixture: ComponentFixture<TempoExcedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TempoExcedidoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TempoExcedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
