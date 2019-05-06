import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; 
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { DefinitionComponent } from './definition/definition.component';
import { SynonymComponent } from './synonym/synonym.component';
import { AntonymComponent } from './antonym/antonym.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DefinitionComponent,
    SynonymComponent,
    AntonymComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
