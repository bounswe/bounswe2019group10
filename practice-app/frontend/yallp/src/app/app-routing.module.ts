import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { PropertyComponent } from '../app/property/property.component';

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
