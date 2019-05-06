import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getSynonym(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/synonym?word="+word);
  }
  getAntonym(word:string) {
    return this.http.get("http://172.104.144.149:8786/api/antonym?antonym="+word);
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
