import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from '../app/home/home.component';
import { DefinitionComponent } from '../app/definition/definition.component';
import { SynonymComponent } from '../app/synonym/synonym.component';
import { AntonymComponent } from '../app/antonym/antonym.component';

const routes: Routes = [
  {path:"" , component: HomeComponent},
  {path:"definition" , component: DefinitionComponent},
  {path:"synonym" , component: SynonymComponent},
  {path:"antonym" , component: AntonymComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
