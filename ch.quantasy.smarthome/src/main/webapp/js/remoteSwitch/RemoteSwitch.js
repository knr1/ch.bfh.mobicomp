$(window).load(function () {
//alert("Document loaded, including graphics and embedded documents (like SVG)");
    var remoteSwitch = document.getElementById("remoteSwitch");
    var svgDoc = remoteSwitch.contentDocument; //get the inner DOM of RemoteSwitch.svg

    var tabs = svgDoc.getElementsByClassName('tab');
    var switches = svgDoc.getElementsByClassName('switcher');
    var dimmers = svgDoc.getElementsByClassName('dimmer');


    for (var i = 0; i < tabs.length; i++) {
	var tab = tabs[i];
	tab.setAttribute("visibility", "hidden");
    }


    for (var i = 0; i < switches.length; i++) {
	var switcher = switches[i];
	$(switcher).click(function (event) {
	    var svgElement = event.target;
	    var json = JSON.stringify({
		"type": "Switch",
		"floor": svgElement.getAttribute("floor"),
		"address": parseInt(svgElement.getAttribute("address")),
		"unit": parseInt(svgElement.getAttribute("unit")),
		"switchTo": svgElement.getAttribute("switchTo") === 'true'
	    });
	    sendText(json);
	    if (svgElement.getAttribute("timerInMilliSeconds").valueOf()) {
		setTimeout(function (event) {
		    var json = JSON.stringify({
			"type": "Switch",
			"floor": svgElement.getAttribute("floor"),
			"address": parseInt(switcher.getAttribute("address")),
			"unit": parseInt(svgElement.getAttribute("unit")),
			"switchTo": !(svgElement.getAttribute("switchTo") === 'true')
		    });
		    sendText(json);
		}, parseInt(svgElement.getAttribute("timerInMilliSeconds")));
	    }
	});
    }

    for (var i = 0; i < dimmers.length; i++) {
	var dimmer = dimmers[i];
	$(dimmer).click(function (event) {
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
		"floor": svgElement.getAttribute("floor"),
		"address": parseInt(svgElement.getAttribute("address")),
		"unit": parseInt(svgElement.getAttribute("unit")),
		"dimValue": dimValue,
		"switchTo": true
	    });
	    sendText(json);
	});
    }
});
