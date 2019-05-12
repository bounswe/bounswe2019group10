import { Component, OnInit} from '@angular/core';
import { ApiService } from '../api.service'
import { filter, take,} from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router' ;
import {DefinitionModel, SynonymModel, AntonymModel, SimilarModel,ExampleModel,RhymeModel} from '../model';

// Propert component where we show the input and button and the results
// It uses property.component.html and property.component.scss

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.scss']
})
export class PropertyComponent implements OnInit {
  // To store the result object comes from the endpoint
  data=null;
  // To store home/:property
  property:string;
  // which word will be searched
  searchWord:string="";
  // we defined the service to be used and route is to be able to get the current url
  constructor(private api:ApiService, private route:ActivatedRoute) { }

  // This is the first function called when this component is used
  // You don't have change this function
  ngOnInit() {
    // We subscribe the current url to reset the data and searchword and to understand which page we are currently in
    // for example if we change the url from /home/definition to /home/synonym
    // data and searchword is resetted and property variable changes to synonym
    this.route.paramMap.subscribe(params => {
      this.resetState();
      this.property = params.get("property");
    });

  }
  resetState(){
    this.searchWord="";
    this.data=null;
  }
  // This function is called when user clicks the button. According to the property value which define which page we are in
  // the correct get or post request is called by using service
  // You should call the function that you wrote in api service

  async submit(){
    // pipe is to be able to call different functions
    // filter makes sures that it waits until the response(data in this case) comes from the endpoint
    // since this is an asynchronous process if we don't write that, data becomes empty
    // take(1) -> after response comes, we take the response and stop waiting response
    // Then we assing the response our data variable but we have to take the array part because we want to show that part

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
    else if(this.property=="similarTo"){
      this.api.getSimilar(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data:SimilarModel) => {
        this.data = data.similars;
      });
    }
    else if(this.property=="example"){
      this.api.getExample(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data:ExampleModel) => {
        this.data = data.examples;
      });
    }
    else if(this.property=="rhyme"){
      this.api.getRhyme(this.searchWord).pipe(filter( data => Object.keys(data).length > 0),take(1)).subscribe(async (data:RhymeModel) => {
        this.data = data.rhymes.all;
      });
    }
  };
}
