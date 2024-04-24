import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BtnVerMaisComponent } from './btn-ver-mais.component';

describe('BtnVerMaisComponent', () => {
  let component: BtnVerMaisComponent;
  let fixture: ComponentFixture<BtnVerMaisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BtnVerMaisComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BtnVerMaisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
