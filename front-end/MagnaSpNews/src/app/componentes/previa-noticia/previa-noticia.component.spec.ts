import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviaNoticiaComponent } from './previa-noticia.component';

describe('PreviaNoticiaComponent', () => {
  let component: PreviaNoticiaComponent;
  let fixture: ComponentFixture<PreviaNoticiaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreviaNoticiaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreviaNoticiaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
