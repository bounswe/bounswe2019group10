const { sendRequest } = require("../util/baseUtil");

exports.synonym = async function (db, req, res) {
    const {word} = req.query;
    const data = await exports.getSynonym(word);
    res.send(data)
};

exports.getSynonym = async function (word){
    console.time("wordsApi");
    let params = {
        url: "https://wordsapiv1.p.mashape.com/words/" + word + "/synonyms",
        options: {
            headers: {
                "X-RapidAPI-Host": "wordsapiv1.p.rapidapi.com",
                "X-RapidAPI-Key": "f227a30e8fmshf11d3973463d146p107dd6jsn39a197ef13b2"
            }
        }
    };
    console.timeEnd("wordsApi");
    const {error, response, body} = await sendRequest(params);
    const data = JSON.parse(body);
    return data;
}

exports.antonym = async function (db, req, res) {
    const word = req.query.antonym;
    const data = await exports.getAntonym(word);
    res.send(data)
};

exports.getAntonym = async function (word){
    console.time("wordsApi");
    let params = {
        url: "https://wordsapiv1.p.mashape.com/words/" + word + "/antonyms",
        options: {
            headers: {
                "X-RapidAPI-Host": "wordsapiv1.p.rapidapi.com",
                "X-RapidAPI-Key": "f227a30e8fmshf11d3973463d146p107dd6jsn39a197ef13b2"
            }
        }
    };
    console.timeEnd("wordsApi");
    const {error, response, body} = await sendRequest(params);
    const data = JSON.parse(body);
    return data;
}
