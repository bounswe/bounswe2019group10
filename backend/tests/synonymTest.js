

var request = require('request');

function synonymTest() {


    request("http://localhost:8080/api/synonym?word=bad", function (error, response, body) {


        return testSynonym(body);

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

    if(JSON.stringify(body_obj) == JSON.stringify(bad_json)){

        console.log("working");
    }else{
        console.log("not working");
    }

}


module.exports = synonymTest;