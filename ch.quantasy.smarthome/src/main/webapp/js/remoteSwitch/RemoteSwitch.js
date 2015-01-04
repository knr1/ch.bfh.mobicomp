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

    var egLeftTop = svgDoc.getElementById("egLeftTop"); //get the inner element by id
    var egLeftBottom = svgDoc.getElementById("egLeftBottom"); //get the inner element by id
    var egRightTop = svgDoc.getElementById("egRightTop"); //get the inner element by id
    var egRightBottom = svgDoc.getElementById("egRightBottom"); //get the inner element by id
    var egMiddleTop = svgDoc.getElementById("egMiddleTop"); //get the inner element by id
    var egMiddleBottom = svgDoc.getElementById("egMiddleBottom"); //get the inner element by id
    var egKitchenTop = svgDoc.getElementById("egKitchenTop"); //get the inner element by id
    var egKitchenBottom = svgDoc.getElementById("egKitchenBottom"); //get the inner element by id
    var luOgOn = svgDoc.getElementById("luOgOn");
    var luOgOff = svgDoc.getElementById("luOgOff");
    var luOgDim = svgDoc.getElementById("luOgDim");
    var lightOgGallerieOn = svgDoc.getElementById("lightOgGallerieOn");
    var lightOgGallerieOn = svgDoc.getElementById("lightOgGallerieOn");
    var lightOgGallerieOff = svgDoc.getElementById("lightOgGallerieOff");
    var lightOgGallerieDim = svgDoc.getElementById("lightOgGallerieDim");
    var lightOgBadOn = svgDoc.getElementById("lightOgBadOn");
    var lightOgBadOff = svgDoc.getElementById("lightOgBadOff");
    var lightOgBadDim = svgDoc.getElementById("lightOgBadDim");
    var lightOgDuscheOn = svgDoc.getElementById("lightOgDuscheOn");
    var lightOgDuscheOff = svgDoc.getElementById("lightOgDuscheOff");
    var lightOgDuscheDim = svgDoc.getElementById("lightOgDuscheDim");







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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 1,
		"switchOn": false
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 1,
		"switchOn": true
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 2,
		"switchOn": false
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 2,
		"switchOn": true
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 3,
		"switchOn": false
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 3,
		"switchOn": true
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 0,
		"switchOn": false
	    });
	    sendText(json)
	}
	, 800);
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
	setTimeout(function (event) {
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": "EG",
		"address": 1,
		"unit": 0,
		"switchOn": true
	    });
	    sendText(json)
	}
	, 800);
    });


    $(egLeftTop).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 1,
	    "switchOn": false
	});
	sendText(json);
    });


    $(egLeftBottom).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 1,
	    "switchOn": true
	});
	sendText(json);
    });
    $(egMiddleTop).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 2,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egMiddleBottom).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 2,
	    "switchOn": true
	});
	sendText(json);
    });
    $(egRightTop).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 3,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egRightBottom).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 3,
	    "switchOn": true
	});
	sendText(json);
    });
    $(egKitchenTop).click(function (event) {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 0,
	    "switchOn": false
	});
	sendText(json);
    });
    $(egKitchenBottom).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "EG",
	    "address": 1,
	    "unit": 0,
	    "switchOn": true
	});
	sendText(json);
    });
    $(luOgOn).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 1,
	    "switchOn": true
	});
	sendText(json);
    });
    $(luOgOff).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 1,
	    "switchOn": false
	});
	sendText(json);
    });
    $(luOgDim).click(function (event) {
	var $this = $(this);
	var evtX = event.clientX;
	var evtY = event.clientY;
	var svgElement = event.target;
	var svgDocument = svgElement.ownerDocument;
	var svgRoot = svgDocument.documentElement;
	var svgEvtPoint = svgRoot.createSVGPoint();
	svgEvtPoint.x = evtX;
	svgEvtPoint.y = evtY;
	var a = svgElement.getScreenCTM();
	var b = a.inverse();
	var svgElementEvtPoint = svgEvtPoint.matrixTransform(b);
	var svgElementPoint = svgRoot.createSVGPoint();
	svgElementPoint.x = svgElement.getBoundingClientRect().left;
	svgElementPoint.y = svgElement.getBoundingClientRect().top;
	svgElementPoint = svgElementPoint.matrixTransform(b);
	svgElementEvtPoint.x -= svgElementPoint.x; //in die Matrix hinein?
	svgElementEvtPoint.y -= svgElementPoint.y; //in die Matrix hinein?

	var bBoxWidth = svgElement.getBBox().width;
	var bBoxHeight = svgElement.getBBox().height;
	var relativeEvtPointX = 1.0 / bBoxWidth * svgElementEvtPoint.x;
	var relativeEvtPointY = 1.0 / bBoxHeight * svgElementEvtPoint.y;
	var dimValue = Math.floor((1.0 - relativeEvtPointY) * 16);
	var json = JSON.stringify({
	    "type": "Dim",
	    "floor": "OG",
	    "address": 4,
	    "unit": 1,
	    "dimValue": dimValue,
	    "switchOn": true
	});
	sendText(json);
    });

    $(lightOgGallerieOn).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 0,
	    "switchOn": true
	});
	sendText(json);
    });
    $(lightOgGallerieOff).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 0,
	    "switchOn": false
	});
	sendText(json);
    });
    $(lightOgGallerieDim).click(function (event) {
	var $this = $(this);
	var evtX = event.clientX;
	var evtY = event.clientY;
	var svgElement = event.target;
	var svgDocument = svgElement.ownerDocument;
	var svgRoot = svgDocument.documentElement;
	var svgEvtPoint = svgRoot.createSVGPoint();
	svgEvtPoint.x = evtX;
	svgEvtPoint.y = evtY;
	var a = svgElement.getScreenCTM();
	var b = a.inverse();
	var svgElementEvtPoint = svgEvtPoint.matrixTransform(b);
	var svgElementPoint = svgRoot.createSVGPoint();
	svgElementPoint.x = svgElement.getBoundingClientRect().left;
	svgElementPoint.y = svgElement.getBoundingClientRect().top;
	svgElementPoint = svgElementPoint.matrixTransform(b);
	svgElementEvtPoint.x -= svgElementPoint.x; //in die Matrix hinein?
	svgElementEvtPoint.y -= svgElementPoint.y; //in die Matrix hinein?

	var bBoxWidth = svgElement.getBBox().width;
	var bBoxHeight = svgElement.getBBox().height;
	var relativeEvtPointX = 1.0 / bBoxWidth * svgElementEvtPoint.x;
	var relativeEvtPointY = 1.0 / bBoxHeight * svgElementEvtPoint.y;
	var dimValue = Math.floor((1.0 - relativeEvtPointY) * 16);
	var json = JSON.stringify({
	    "type": "Dim",
	    "floor": "OG",
	    "address": 4,
	    "unit": 0,
	    "dimValue": dimValue,
	    "switchOn": true
	});
	sendText(json);
    });

    $(lightOgBadOn).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 2,
	    "switchOn": true
	});
	sendText(json);
    });
    $(lightOgBadOff).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 2,
	    "switchOn": false
	});
	sendText(json);
    });
    $(lightOgBadDim).click(function (event) {
	var $this = $(this);
	var evtX = event.clientX;
	var evtY = event.clientY;
	var svgElement = event.target;
	var svgDocument = svgElement.ownerDocument;
	var svgRoot = svgDocument.documentElement;
	var svgEvtPoint = svgRoot.createSVGPoint();
	svgEvtPoint.x = evtX;
	svgEvtPoint.y = evtY;
	var a = svgElement.getScreenCTM();
	var b = a.inverse();
	var svgElementEvtPoint = svgEvtPoint.matrixTransform(b);
	var svgElementPoint = svgRoot.createSVGPoint();
	svgElementPoint.x = svgElement.getBoundingClientRect().left;
	svgElementPoint.y = svgElement.getBoundingClientRect().top;
	svgElementPoint = svgElementPoint.matrixTransform(b);
	svgElementEvtPoint.x -= svgElementPoint.x; //in die Matrix hinein?
	svgElementEvtPoint.y -= svgElementPoint.y; //in die Matrix hinein?

	var bBoxWidth = svgElement.getBBox().width;
	var bBoxHeight = svgElement.getBBox().height;
	var relativeEvtPointX = 1.0 / bBoxWidth * svgElementEvtPoint.x;
	var relativeEvtPointY = 1.0 / bBoxHeight * svgElementEvtPoint.y;
	var dimValue = Math.floor((1.0 - relativeEvtPointY) * 16);
	var json = JSON.stringify({
	    "type": "Dim",
	    "floor": "OG",
	    "address": 4,
	    "unit": 2,
	    "dimValue": dimValue,
	    "switchOn": true
	});
	sendText(json);
    });

    $(lightOgDuscheOn).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 3,
	    "switchOn": true
	});
	sendText(json);
    });
    $(lightOgDuscheOff).click(function () {
	var json = JSON.stringify({
	    "type": "Switch",
	    "floor": "OG",
	    "address": 4,
	    "unit": 3,
	    "switchOn": false
	});
	sendText(json);
    });
    $(lightOgDuscheDim).click(function (event) {
	var $this = $(this);
	var evtX = event.clientX;
	var evtY = event.clientY;
	var svgElement = event.target;
	var svgDocument = svgElement.ownerDocument;
	var svgRoot = svgDocument.documentElement;
	var svgEvtPoint = svgRoot.createSVGPoint();
	svgEvtPoint.x = evtX;
	svgEvtPoint.y = evtY;
	var a = svgElement.getScreenCTM();
	var b = a.inverse();
	var svgElementEvtPoint = svgEvtPoint.matrixTransform(b);
	var svgElementPoint = svgRoot.createSVGPoint();
	svgElementPoint.x = svgElement.getBoundingClientRect().left;
	svgElementPoint.y = svgElement.getBoundingClientRect().top;
	svgElementPoint = svgElementPoint.matrixTransform(b);
	svgElementEvtPoint.x -= svgElementPoint.x; //in die Matrix hinein?
	svgElementEvtPoint.y -= svgElementPoint.y; //in die Matrix hinein?

	var bBoxWidth = svgElement.getBBox().width;
	var bBoxHeight = svgElement.getBBox().height;
	var relativeEvtPointX = 1.0 / bBoxWidth * svgElementEvtPoint.x;
	var relativeEvtPointY = 1.0 / bBoxHeight * svgElementEvtPoint.y;
	var dimValue = Math.floor((1.0 - relativeEvtPointY) * 16);
	var json = JSON.stringify({
	    "type": "Dim",
	    "floor": "OG",
	    "address": 4,
	    "unit": 3,
	    "dimValue": dimValue,
	    "switchOn": true
	});
	sendText(json);
    });
});
