import { Injectable } from '@angular/core';


export interface Item {
  cId: string;
  itemId: string;
  itemName: string;
  brandName: string;
  cost: number;
  measure: string;
  offer:number;
  image:string;
  active:boolean;
  categoryName:string;
}

@Injectable({
  providedIn: 'root'
})
export abstract class CardsBaseService {

  constructor() { }

  // abstract getItems(): Item[];
}
