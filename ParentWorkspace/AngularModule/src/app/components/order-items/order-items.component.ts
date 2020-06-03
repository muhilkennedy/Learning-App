import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-items',
  templateUrl: './order-items.component.html',
  styleUrls: ['./order-items.component.css']
})
export class OrderItemsComponent implements OnInit {

  reviewOrderEditable = true;
  deliveryInfoEditable = true;
  orderStatusEditable = false;

  constructor() {}

  ngOnInit() {

  }

}
