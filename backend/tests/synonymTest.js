

var request = require('request');

function synonymTest() {

    var res;
    request("http://localhost:8080/api/synonym?word=bad", function (error, response, body) {
        res = testSynonym(body);
    });

    return new Promise(resolve => {

        setTimeout(() => {
            resolve(res);
        }, 2000);

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

    console.log(res);

    if(res){
        return "working";
    }else{
        return "not working";
    }

}



module.exports = asyncSynonymTest;