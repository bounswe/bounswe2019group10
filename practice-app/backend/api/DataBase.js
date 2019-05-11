var MongoClient = require('mongodb').MongoClient;

var url = 'mongodb://localhost:27017/test';

var _db = null;

exports.connect = function(callback){
  if(_db === null){
    MongoClient.connect(url,{ useNewUrlParser: true }, function(err, db) {
      //console.log(err);
      if(err === null){
        _db = db.db("sorutabani");
      }
      callback(err);
    });
  }else{
    callback(null);
  }
};

exports.isConnected = function(){
  if(_db === null){
    return false;
  }
  return true;
};

exports.getDB = function(){
  return _db;
};

exports.close = function(){
    _db.close();
    _db = null;
  };

exports.insert = function(collection,doc,callback){
  _db.collection(collection).insert(doc,callback);
};
exports.aggregate = function(collection,query,callback){
  _db.collection(collection).aggregate(query,callback);
};
exports.find = function(collection,query,callback){
  _db.collection(collection).find(query).toArray(callback);
};
exports.findAndProject = function(collection,query,projection,callback){
  _db.collection(collection).find(query,projection).toArray(callback);
};
exports.findAndProjectAndSort = function(collection,query,projection,mySort,callback){
  _db.collection(collection).find(query,projection).sort(mySort).toArray(callback);
};
exports.findAndProjectAndSortAndLimit = function(collection,query,projection,mySort,limit,callback){
  _db.collection(collection).find(query,projection).sort(mySort).limit(limit).toArray(callback);
};
exports.findAndProjectAndSortAndSkipAndLimit = function(collection,query,projection,mySort,skip,limit,callback){
  _db.collection(collection).find(query,projection).sort(mySort).skip(skip).limit(limit).toArray(callback);
};
exports.findAndProjectAndLimit = function(collection,query,projection,limit,callback){
  _db.collection(collection).find(query,projection).limit(limit).toArray(callback);
};
exports.distinct = function(collection,field,query,callback){
  _db.collection(collection).distinct(field,query,callback);
};
exports.findandSort= function(collection,query,mySort,callback){
  _db.collection(collection).find(query).sort(mySort).toArray(callback);
};
exports.findandSortandLimit= function(collection,query,mySort,limit,callback){
  _db.collection(collection).find(query).sort(mySort).limit(limit).toArray(callback);
};
exports.limittedFind = function(collection,query,limit,callback){
  _db.collection(collection).find(query).limit(limit).toArray(callback);
};
exports.remove = function(collection,query,callback) {
  _db.collection(collection).remove(query,callback);
};
exports.update = function(collection,query,updates,callback){
  _db.collection(collection).update(query,updates,callback);
};
exports.updateMany = function(collection,query,updates,callback){
  _db.collection(collection).updateMany(query,updates,callback);
};
exports.findOneAndUpdate = function(collection,query,updates,callback){
  _db.collection(collection).findOneAndUpdate(query,updates,callback);
};
exports.findOneAndDelete = function(collection,query,callback) {
  _db.collection(collection).findOneAndDelete(query,callback);
};
exports.findOneAndProject = function(collection,query,projection,callback) {
  _db.collection(collection).findOne(query,projection,callback);
};
exports.findOne = function(collection,query,callback) {
  _db.collection(collection).findOne(query,callback);
};
