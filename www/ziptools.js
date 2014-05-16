/*
    Author: Vishal Rajpal
    Filename: ZipPlugin.js
    Created Date: 21-02-2012
    Modified Date: 21-02-2013
    Modified to comply with Cordova by: Ran Friedlender
*/

var zipTools = {
	extract : function(file, successCallback, errorCallback) 
	{
		cordova.exec(successCallback, errorCallback, "ZipTools", "Extract", [file]);
	}
}

module.exports = zipTools;

