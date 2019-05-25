var request = require('request');

async function hasTypesTest() {
    return new Promise((resolve, reject) => {
      request("http://localhost:8786/api/frequency?word=monkey", function (error, response, body) {
          resolve(testHasTypes(body));
      });
    });
}


function testHasTypes(body) {

  let body_obj = JSON.parse(body);

  let monkey_json = {
	  "word": "monkey",
	  "hasTypes": [
		"holy terror",
		"terror",
		"brat",
		"little terror",
		"puddle",
		"new world monkey",
		"catarrhine",
		"platyrrhinian",
		"platyrrhine",
		"old world monkey"
		]
	};

  return JSON.stringify(body_obj) === JSON.stringify(monkey_json);

}


async function asyncHasTypesTest(){

  let res = await hasTypesTest();

  if(res){
      return "working";
  }else{
      return "not working";
  }

}

module.exports = asyncHasTypesTest();