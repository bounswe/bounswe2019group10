var request = require('request');

async function antonymTest() {
    return new Promise((resolve, reject) => {
        request("http://172.104.144.149:8786/api/antonym?antonym=black", function (error, response, body) {
            resolve(testAntonym(body));
        });
    });
}


function testAntonym(body) {

    let body_obj = JSON.parse(body);


    let black_json = {
        "word": "black",
        "antonyms": [
            "white"
        ]
    };

    return JSON.stringify(body_obj) === JSON.stringify(black_json);

}


async function asyncAntonymTest(){

    let res = await antonymTest();

    if(res){
        return "working";
    }else{
        return "not working";
    }

}

module.exports = asyncAntonymTest;