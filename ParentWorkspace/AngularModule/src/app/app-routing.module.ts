import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './shared/components/about/about.component';
import { RedirectComponent } from './components/redirect/redirect.component';

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
