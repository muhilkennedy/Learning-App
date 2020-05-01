import { Injectable } from '@angular/core';
import {Item,CardsBaseService} from '../model/cards-base.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CardsService implements CardsBaseService {

  constructor(private http: HttpClient) { }

  items: Item[];
  getAllItems = "/common/getAllItems";

  getItems(){
    return this.items;
  }

  setItems(itemsList: Item[]){
    this.items = itemsList;
  }

  getItemsForHomePage(limit, offset) : Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Limit': `${limit}`,
        'Offset': `${offset}`
      }),
    };
    return this.http.get(environment.backendHost+this.getAllItems, httpOptions);
  }

}