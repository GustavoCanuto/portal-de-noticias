import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaNotFoundComponent } from './tela-not-found.component';

describe('TelaNotFoundComponent', () => {
  let component: TelaNotFoundComponent;
  let fixture: ComponentFixture<TelaNotFoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaNotFoundComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaNotFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
