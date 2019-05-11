const { sendRequest } = require("../util/baseUtil");


exports.synonym = async function (db, req, res) {

    const {word} = req.query;
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

    res.send(data);

};

exports.antonym = async function (db, req, res) {

  const word = req.query.antonym;
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

  res.send(data)

};

exports.definition = async function(db,req,res) {

    const word = req.body.word;
  
    let params = {
      url:"https://wordsapiv1.p.mashape.com/words/"+word+"/definitions",
      options:{
        headers:{
          "X-RapidAPI-Host": "wordsapiv1.p.rapidapi.com",
          "X-RapidAPI-Key": "f227a30e8fmshf11d3973463d146p107dd6jsn39a197ef13b2"
        }
      }
    }
  
    const { error, response, body } = await sendRequest(params);
    const data = JSON.parse(body);
    res.send(data)
  
};
