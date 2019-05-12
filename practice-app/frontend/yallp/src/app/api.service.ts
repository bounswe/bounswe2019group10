import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

// Service is a feature of angular. Services are usually used for making get or post request to the endpoints
// Necessary function is defined for each endpoint and the result is returned

// You have to add necessary funtion to this service
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }
  // get takes one parameter which is url, we call this function in our property component and word parameter is assigned
  // in that component
  getSynonym(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/synonym?word="+word);
  }
  getAntonym(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/antonym?antonym="+word);
  }

  //TODO  after deployment localhost has to be updated!
  getSimilar(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/similarTo?word="+word);
  }
  //TODO  after deployment localhost has to be updated!
  getExample(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/examples?word="+word);
  }

  //TODO  after deployment localhost has to be updated!
  getRhyme(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/rhymes?word="+word);
  }

  postDefinition(wordToPost:string) {
    const jsonBody={
      word:wordToPost
    }
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    let options = { headers: headers };
    return this.http.post("http://172.104.144.149:8786/api/definition", JSON.stringify(jsonBody), options);
  }
}
