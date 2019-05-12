var request = require('request');

async function synonymTest() {
    return new Promise((resolve, reject) => {
      request("http://172.104.144.149:8786/api/synonym?word=bad", function (error, response, body) {
          resolve(testSynonym(body));
      });
    });
}


function testSynonym(body) {

  let body_obj = JSON.parse(body);

  let bad_json = {
      "word": "bad",
      "synonyms": [
          "badly",
          "badness",
          "big",
          "uncollectible",
          "forged",
          "unfit",
          "unsound",
          "defective",
          "regretful",
          "sorry",
          "high-risk",
          "risky",
          "speculative",
          "tough",
          "spoiled",
          "spoilt"
      ]
  };

  return JSON.stringify(body_obj) === JSON.stringify(bad_json);

}


async function asyncSynonymTest(){

  let res = await synonymTest();

  if(res){
      return "working";
  }else{
      return "not working";
  }

}

module.exports = asyncSynonymTest;
