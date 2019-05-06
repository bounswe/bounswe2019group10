import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service'
import { filter, take} from 'rxjs/operators';

@Component({
  selector: 'app-antonym',
  templateUrl: './antonym.component.html',
  styleUrls: ['./antonym.component.scss']
})
export class AntonymComponent implements OnInit {
  antonyms={};
  searchWord:string="";
  constructor(private api:ApiService) { }

  ngOnInit() {
  }
  async start(word:string){
    this.searchWord = word;
    await this.submit();
  };

  async submit(){
    this.api.getAntonym(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data) => {
      this.antonyms = data;
    });
  };
}
