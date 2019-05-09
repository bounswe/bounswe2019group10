import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service'
import { filter, take} from 'rxjs/operators';

@Component({
  selector: 'app-definition',
  templateUrl: './definition.component.html',
  styleUrls: ['./definition.component.scss']
})
export class DefinitionComponent implements OnInit {
  definitions={};
  searchWord:string="";
  constructor(private api:ApiService) { }

  ngOnInit() {
  }
  async start(word:string){
    this.searchWord = word;
    await this.submit();
  };

  async submit(){
    this.api.postDefinition(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data) => {
      this.definitions = data;
    });
  };

}
