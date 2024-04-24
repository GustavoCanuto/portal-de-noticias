import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrincipaisNoticiasComponent } from './principais-noticias.component';

describe('PrincipaisNoticiasComponent', () => {
  let component: PrincipaisNoticiasComponent;
  let fixture: ComponentFixture<PrincipaisNoticiasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrincipaisNoticiasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrincipaisNoticiasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
