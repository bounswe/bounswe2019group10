var request = require('request');

async function synonymTest() {
    return new Promise((resolve, reject) => {
      request("http://localhost:8786/api/examples?word=example", function (error, response, body) {
          resolve(testSynonym(body));
      });
    });
}


function testSynonym(body) {

  let body_obj = JSON.parse(body);

  let example_json = {
    "word": "example",
    "examples": [
      "I profited from his example",
      "but there is always the famous example of the Smiths",
      "this patient provides a typical example of the syndrome",
      "there is an example on page 10",
      "they decided to make an example of him",
      "you must work the examples at the end of each chapter in the textbook"
    ]
  };

  return JSON.stringify(body_obj) === JSON.stringify(example_json);

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
