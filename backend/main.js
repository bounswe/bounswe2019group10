var http = require('http');
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');

// import api route
var api = require('./api/Api');

// create servers
var httpServer = http.createServer(app);

// for parsing application/json
app.use(bodyParser.json());

// for parsing application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// for parsing cookies
app.use(cookieParser());

app.use(function(req,res,next){
	res.sendResponse = res.send;
	res.send = function(response){
		res.sendResponse(response);
	};
	next();
});

// api route
app.use('/api/',api);

//opening page
app.use('/', function (req, res) {
	res.send("opening page!");
});

// start servers
httpServer.listen(8080,function(){
    console.log('HTTP SERVER listening on port 8080!');
});
