import { Component, Input, OnInit } from '@angular/core';
import { ThemePalette } from '@angular/material/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrl: './loading.component.css'
})
export class LoadingComponent implements OnInit{
  @Input() loadingPrincipal: boolean = false;

  diametro:number = 50;

  ngOnInit(): void {
    if(this.loadingPrincipal == true){
      this.diametro = 100;
    }else{
      this.diametro = 40;
    }
  }
}
