var express = require('express');
var router = express.Router();
var db = require('./DataBase');
var user=require('./User');

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

router.use('/login',function(req,res){
  if(!db.isConnected()){
    res.send("9");
    return;
  }
  user.login(db,req,res);
});

module.exports = router;
