import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cards',
  templateUrl: './cards.component.html',
  styleUrls: ['./cards.component.css']
})
export class CardsComponent implements OnInit {

  count = [1,2,3,4,5,6,7,8,9,0];

  constructor() { }

  ngOnInit() {
  }

}
