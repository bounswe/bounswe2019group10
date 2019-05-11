import { Component } from '@angular/core';

// This is the main component that is executed when program runs. Selector is a tag for this component like <div>.
// When a html file contains <app-root> </app-root>, actually the html inside  templateUrl: './app.component.html' is showed in the webpage and 
// styleUrls: ['./app.component.scss'] is used for css. This is valid for all components.

// No need to modify this file
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'yallp';
}
