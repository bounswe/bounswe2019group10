var request = require('request');

async function definitionTest() {

    return new Promise((resolve, reject) => {
    const url = "http://localhost:8080/api/definition"
    const jsonBody= {
        "word":"car"
    };
    request({
        url:url,
        method:"POST",
        json:jsonBody
        }, function (error, response, body) {
        resolve(testDefinition(body));
        });

    });

}


function testDefinition(body) {

    let body_obj = body;

    let car_json = {
        "word": "car",
        "definitions": [
            {
                "definition": "a motor vehicle with four wheels; usually propelled by an internal combustion engine",
                "partOfSpeech": "noun"
            },
            {
                "definition": "the compartment that is suspended from an airship and that carries personnel and the cargo and the power plant",
                "partOfSpeech": "noun"
            },
            {
                "definition": "where passengers ride up and down",
                "partOfSpeech": "noun"
            },
            {
                "definition": "a wheeled vehicle adapted to the rails of railroad",
                "partOfSpeech": "noun"
            },
            {
                "definition": "a conveyance for passengers or freight on a cable railway",
                "partOfSpeech": "noun"
            }
        ]
    };

    return JSON.stringify(body_obj) === JSON.stringify(car_json);

}


async function asyncDefinitionTest(){

    let res = await definitionTest();
    
    if(res){
        return "working";
    }else{
        return "not working";
    }

}

module.exports = asyncDefinitionTest;
