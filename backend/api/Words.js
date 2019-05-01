const { sendRequest } = require("../util/baseUtil");

exports.synonym = async function(db,req,res) {

  console.time("wordsApi");

  let params = {
    url:"https://wordsapiv1.p.mashape.com/words/example/synonyms",
    options:{
      headers:{
  			"X-RapidAPI-Host": "wordsapiv1.p.rapidapi.com",
  			"X-RapidAPI-Key": "f227a30e8fmshf11d3973463d146p107dd6jsn39a197ef13b2"
  		}
    }
  }
  console.timeEnd("wordsApi");

  const { error, response, body } = await sendRequest(params);
  const data = JSON.parse(body);
  res.send(data)

}
