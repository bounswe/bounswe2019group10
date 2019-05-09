import React, { Component } from 'react'

import axios from "axios";

import Layout from '../components/MyLayout.js';
import Word from '../components/Word.js';

const Constants = require('../Constants');

export default class Synonym extends Component {

  constructor(props) {
    super(props);
    this.state = {
      word: '',
      data: []
    };
  }

  _handleChange = (event) => {
    this.setState({word: event.target.value});
  }

  async getSynonyms(word){
    if(!word || word == "") return;
    var url = new URL(Constants.API_URL+'synonym');
    url.searchParams.append('word', word);
    const response = await axios.post(url);
    if(response.status == 200){
      if(response.data.synonyms){
        const words = response.data.synonyms.map(word=>(
          {
            text:word
          }
        ));
        this.setState({
          data: words
        })
      }else{
        alert("word not found");
      }
    }else{
      alert("error")
    }
  }

  _handleSubmit = async(event) => {
    //stops submitting the form
    event.preventDefault();
    await this.getSynonyms(this.state.word);
  }

  render(){
    let {data} = this.state;
    return (
      <Layout>
        <p>Synonym</p>
        <form onSubmit={this._handleSubmit}>
          <label>
            Word:
            <input type="text" value={this.state.value} onChange={this._handleChange} />
          </label>
          <input type="submit" value="Submit" />
        </form>
        {data.map(word=><Word {...word}/>)}
      </Layout>
    )
  }
}
