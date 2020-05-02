import { Component, OnInit, HostListener, ViewChild, ElementRef } from '@angular/core';
import { CardsService } from 'src/app/shared/services/cards.service';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from 'src/app/shared/services/user.service';
import { CartService } from 'src/app/shared/services/cart.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-cards',
  templateUrl: './cards.component.html',
  styleUrls: ['./cards.component.css']
})
export class CardsComponent implements OnInit {

  cards:any[];

  itemsLoaded : number;

  modalImageContent:string;
  modalImageCaption:string;
  @ViewChild('image') img: ElementRef;
  @ViewChild('parent') parent: ElementRef;

  constructor( private cookieService: CookieService,
               private cardService: CardsService,
               private user: UserService,
               private cartService: CartService) { }

  ngOnInit() {

    this.cardService.getItemsForHomePage(environment.homeCardsToLoadPerCall, 0)
        .subscribe((response:any)=>{
          if(response.statusCode == 200){
            console.log("Success");
            this.cardService.items = response.dataList;
            console.log(this.cardService.items);
            this.cards = this.cardService.items;
            this.itemsLoaded = this.cardService.items.length;
          }
        },
        (error)=>{
          alert("something went wrong!");
          console.log("Connection to Backend Failed");
        })

  }

  viewFullImage(card){
    this.img.nativeElement.src = card.image;
    this.modalImageCaption = card.itemName;
    let tag = this.parent.nativeElement;
    tag.classList.remove("modal");
    tag.classList.add("modal-open");
  }

  closeImageOverview(){
    this.img.nativeElement.src = null;
    this.modalImageCaption = null;
    this.parent.nativeElement.classList.remove("modal-open");
    this.parent.nativeElement.classList.add("modal");
  }

  addToCart(item:any){
    if(this.user.token != null && this.cookieService.get("userId") != null){
      let cartItem = { itemId: item.itemId, itemName: item.itemName, quantity: 1 };
      let itemExisting = false;
      // identify if the item is present in cart already and increment values likewise.
      this.user.cartItems.forEach(function(itemValue){
        if(itemValue.itemId === item.itemId){
          itemValue.quantity += 1;
          itemExisting = true;
          cartItem = itemValue;
        }
      });
      if(!itemExisting)
        this.user.cartItems.push(cartItem);
      let removeFromCart = false;
      this.cartService.addCartForUser(cartItem)
          .subscribe((response:any)=>{
            if(response.statusCode === 200){
              console.log("added to cart successfully!");
            }
            else{
              //reset cart item
              this.removeFromCartArray(cartItem);
            }
          },
          (error)=>{
            alert("Failed to add items to cart");
            this.removeFromCartArray(cartItem);
          });
    }
    else{
      alert("please login to add to cart!")
    }
  }

  removeFromCartArray(cartItem){
    for(let i=0; i < this.user.cartItems.length; i++){
      if(this.user.cartItems[i].itemId === cartItem.itemId){
        if((this.user.cartItems[i].quantity - 1) === 0){
          this.user.cartItems.splice(i,1);
        }
        else{
          this.user.cartItems[i].quantity -= 1;
        }
      }
    }
  }

  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {
  //In chrome and some browser scroll is given to body tag
  let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
  let max = document.documentElement.scrollHeight;
  // pos/max will give you the distance between scroll bottom and and bottom of screen in percentage.
  if(pos == max )   {
    //incremental call to push cards on lazy load
    this.cardService.getItemsForHomePage(environment.homeCardsToLoadPerCall, this.itemsLoaded)
        .subscribe((response:any)=>{
          if(response.statusCode == 200){
            console.log("Success");
            this.cardService.items = response.dataList;
            this.cards = this.cards.concat(this.cardService.items);
            this.itemsLoaded += response.dataList.length;
          }
        },
        (error)=>{
          alert("something went wrong!");
          console.log("Connection to Backend Failed");
        })
  }
}

}
