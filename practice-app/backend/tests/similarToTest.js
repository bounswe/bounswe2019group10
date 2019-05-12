var request = require('request');

async function similarToTest() {
    return new Promise((resolve, reject) => {
      request("http://localhost:8786/api/examples?word=cool", function (error, response, body) {
          resolve(testSimilarTo(body));
      });
    });
}


function testSimilarTo(body) {

  let body_obj = JSON.parse(body);

  let example_json = {
      "word": "cool",
      "similarTo": [
          "composed",
          "satisfactory",
          "fashionable",
          "stylish",
          "cold",
          "water-cooled",
          "air-conditioned",
          "air-cooled",
          "caller",
          "precooled",
          "unresponsive",
          "unqualified"
      ]
  };

  return JSON.stringify(body_obj) === JSON.stringify(example_json);

}


async function asyncSimilarToTest(){

  let res = await similarToTest();

  if(res){
      return "working";
  }else{
      return "not working";
  }

}

module.exports = asyncSimilarToTest();
