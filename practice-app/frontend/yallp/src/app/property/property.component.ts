import { Component, OnInit} from '@angular/core';
import { ApiService } from '../api.service'
import { filter, take,} from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router' ;
import { DefinitionModel, SynonymModel, AntonymModel } from '../model';

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.scss']
})
export class PropertyComponent implements OnInit {
  data=null;
  property:string;
  searchWord:string="";
  constructor(private api:ApiService, private route:ActivatedRoute) { }
  
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.resetState();
      this.property = params.get("property");
    });
    
  }
  resetState(){
    this.searchWord="";
    this.data=null;
  }

  async submit(){
    if(this.property=="definition"){
      this.api.postDefinition(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data:DefinitionModel) => {
        this.data = data.definitions;
      });
    }
    else if(this.property=="synonym"){
      this.api.getSynonym(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data:SynonymModel) => {
        this.data = data.synonyms;
      });
    }
    else if(this.property=="antonym"){
      this.api.getAntonym(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data:AntonymModel) => {
        this.data = data.antonyms;
      });
    }
  };
}
