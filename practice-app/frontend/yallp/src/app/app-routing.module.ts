import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { PropertyComponent } from '../app/property/property.component';

// This variable is to determine which component to use according to url path
// in home/:property, property is a url parameter such as our endpoints definition, synonym
// You don't have to make any change in this file since there will be no other url path for now
const routes: Routes = [
  {path:"home/:property" , component: PropertyComponent},
  {path:"home" , component: HomeComponent},
  {path:"**" , redirectTo:"/home", pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
