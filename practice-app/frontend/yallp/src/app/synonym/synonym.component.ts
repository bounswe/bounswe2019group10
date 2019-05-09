import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service'
import { filter, take} from 'rxjs/operators';

@Component({
  selector: 'app-synonym',
  templateUrl: './synonym.component.html',
  styleUrls: ['./synonym.component.scss']
})
export class SynonymComponent implements OnInit {
  synonyms={};
  searchWord:string="";
  constructor(private api:ApiService) { }

  ngOnInit() {
  }
  async start(word:string){
    this.searchWord = word;
    await this.submit();
  };

  async submit(){
    this.api.getSynonym(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data) => {
      this.synonyms = data;
    });
  };

}
