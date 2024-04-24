import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SiteForaDoArComponent } from './site-fora-do-ar.component';

describe('SiteForaDoArComponent', () => {
  let component: SiteForaDoArComponent;
  let fixture: ComponentFixture<SiteForaDoArComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SiteForaDoArComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SiteForaDoArComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
