const request = require('request');
const BB = require('bluebird');

exports.sendRequest = function ({url, options}) {
	return new BB(function (resolve, reject) {
		request(url, options,function (error, response, body) {
				resolve({ error: error, response: response, body: body });
			});
	});
};
