$(window).load(function () {
    //alert("Document loaded, including graphics and embedded documents (like SVG)");
    var a = document.getElementById("remoteSwitch");
    var svgDoc = a.contentDocument; //get the inner DOM of alpha.svg
    var ogLeftUp = svgDoc.getElementById("ogLeftUp"); //get the inner element by id
    var ogLeftDown = svgDoc.getElementById("ogLeftDown"); //get the inner element by id
    var ogMiddleUp = svgDoc.getElementById("ogMiddleUp"); //get the inner element by id
    var ogMiddleDown = svgDoc.getElementById("ogMiddleDown"); //get the inner element by id
    var ogRightUp = svgDoc.getElementById("ogRightUp"); //get the inner element by id
    var ogRightDown = svgDoc.getElementById("ogRightDown"); //get the inner element by id
    var ogBackUp = svgDoc.getElementById("ogBackUp"); //get the inner element by id
    var ogBackDown = svgDoc.getElementById("ogBackDown"); //get the inner element by id
    var egLeftUp = svgDoc.getElementById("egLeftUp"); //get the inner element by id
    var egLeftDown = svgDoc.getElementById("egLeftDown"); //get the inner element by id
    var egRightUp = svgDoc.getElementById("egRightUp"); //get the inner element by id
    var egRightDown = svgDoc.getElementById("egRightDown"); //get the inner element by id
    var egMiddleUp = svgDoc.getElementById("egMiddleUp"); //get the inner element by id
    var egMiddleDown = svgDoc.getElementById("egMiddleDown"); //get the inner element by id
    var egKitchenUp = svgDoc.getElementById("egKitchenUp"); //get the inner element by id
    var egKitchenDown = svgDoc.getElementById("egKitchenDown"); //get the inner element by id








    $(ogLeftUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 1,
	    "switchOn": false
	});
	sendText(json);
    });
    $(ogLeftDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 1,
	    "switchOn": true
	});
	sendText(json);
    });
    $(ogRightUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 3,
	    "switchOn": false
	});
	sendText(json);
    });
    $(ogRightDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 3,
	    "switchOn": true
	});
	sendText(json);
    });
    $(ogMiddleUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 2,
	    "switchOn": false
	});
	sendText(json);
    });
    $(ogMiddleDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 2,
	    "switchOn": true
	});
	sendText(json);
    });
    $(ogBackUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 0,
	    "switchOn": false
	});
	sendText(json);
    });
    $(ogBackDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 3,
	    "unit": 0,
	    "switchOn": true
	});
	sendText(json);
    });

    $(egLeftUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 1,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egLeftDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 1,
	    "switchOn": true
	});
	sendText(json);
    });
    $(egMiddleUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 2,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egMiddleDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 2,
	    "switchOn": true
	});
	sendText(json);
    });
    $(egRightUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 3,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egRightDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 3,
	    "switchOn": true
	});
	sendText(json);
    });
    $(egKitchenUp).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 0,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egKitchenDown).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 0,
	    "switchOn": true
	});
	sendText(json);
    });
});