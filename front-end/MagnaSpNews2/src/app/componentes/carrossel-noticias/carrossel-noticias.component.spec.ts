import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarrosselNoticiasComponent } from './carrossel-noticias.component';

describe('CarrosselNoticiasComponent', () => {
  let component: CarrosselNoticiasComponent;
  let fixture: ComponentFixture<CarrosselNoticiasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CarrosselNoticiasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarrosselNoticiasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
