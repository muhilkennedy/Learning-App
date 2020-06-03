import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './shared/components/about/about.component';
import { RedirectComponent } from './components/redirect/redirect.component';
import { OrderItemsComponent } from './components/order-items/order-items.component';

const routes: Routes = [
  {
    path:'redirect',
    component: RedirectComponent
  },
  {
    path:'home',
    component: HomeComponent
  },
  {
    path:'about',
    component: AboutComponent
  },
  {
    path:'orderItem',
    component: OrderItemsComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      { 
        enableTracing: true // <-- debugging purposes only
      } 
    )],
  exports: [RouterModule]
})
export class AppRoutingModule { }
