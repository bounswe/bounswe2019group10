var request = require('request');

async function frequencyTest() {
    return new Promise((resolve, reject) => {
      request("http://localhost:8786/api/frequency?word=cool", function (error, response, body) {
          resolve(testFrequency(body));
      });
    });
}


function testFrequency(body) {

  let body_obj = JSON.parse(body);

  let example_json = {
      "word": "cool",
      "frequency": {
          "zipf": 5.32,
          "perMillion": 211.32,
          "diversity": 0.43
      }
  };

  return JSON.stringify(body_obj) === JSON.stringify(example_json);

}


async function asyncFrequencyTest(){

  let res = await frequencyTest();

  if(res){
      return "working";
  }else{
      return "not working";
  }

}

module.exports = asyncFrequencyTest();
