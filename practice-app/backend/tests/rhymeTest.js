var request = require('request');

async function rhymeTest() {
    return new Promise((resolve, reject) => {
      request("http://localhost:8786/api/rhyme?word=turkey", function (error, response, body) {
          resolve(testRhyme(body));
      });
    });
}


function testRhyme(body) {

    let body_obj = JSON.parse(body);

    let turkey_json = {
        "word": "turkey",
        "rhymes": {
          "all": [
            "albuquerque",
            "brush turkey",
            "cold turkey",
            "jerky",
            "mirky",
            "murky",
            "perky",
            "quirky",
            "turkey",
            "turki",
            "talk turkey",
            "tom turkey",
            "hen turkey",
            "old turkey",
            "scrub turkey"
          ]
        }
      };

    return JSON.stringify(body_obj) === JSON.stringify(turkey_json);

}


async function asyncRhymeTest(){

    let res = await rhymeTest();

    if(res){
        return "working";
    }else{
        return "not working";
    }

}

module.exports = asyncRhymeTest;
