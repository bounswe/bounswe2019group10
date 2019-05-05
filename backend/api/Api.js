const express = require('express');
var router = express.Router();
const db = require('./DataBase');
const user = require('./User');
const words = require('./Words');

db.connect(function(err){
  if(err === null){
    console.log("DB CONNECTED!");
  }
  else{
    console.log("DB CONNECTION FAILED!");
    console.log(err);
  }
});

router.use('/register',function(req,res){
  if(!db.isConnected()){
    res.send("9");
    return;
  }
  user.register(db,req,res);
});

router.use('/synonym',function(req,res){
  if(!db.isConnected()){
    res.send("9");
    return;
  }
  console.log("asd");
  words.synonym(db,req,res);
});

router.use('/antonym',function(req,res){
  if(!db.isConnected()){
    res.send("9");
    return;
  }
  console.log("antonym page is requested");
  words.antonym(db,req,res);
});

module.exports = router;
