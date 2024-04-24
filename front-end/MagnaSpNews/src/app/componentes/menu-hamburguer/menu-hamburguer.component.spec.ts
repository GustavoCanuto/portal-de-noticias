import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuHamburguerComponent } from './menu-hamburguer.component';

describe('MenuHamburguerComponent', () => {
  let component: MenuHamburguerComponent;
  let fixture: ComponentFixture<MenuHamburguerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuHamburguerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MenuHamburguerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
