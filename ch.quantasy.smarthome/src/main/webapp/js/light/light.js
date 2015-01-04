
// Run at document ready
$(function () {

    var o = document.getElementById("myRGB");
    // Make sure, the svg doc is loaded!
    setTimeout(function () {
	var svgDoc = o.contentDocument;
	var colorWheel = svgDoc.getElementById("colorWheel");

	// Register mouse move listener on circles
	// (replace 'mousemove' with 'click' to catch the click event)
	$(colorWheel).click(
		function (event) {
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
		    var ORIGIN = new Point(0.5, 0.5);
		    var p = new Point(relativeEvtPointX, relativeEvtPointY);
		    var angle = ORIGIN.bearing(p);
		    angle = 360 - ((angle + 270) % 360);
		    distance = 200 * ORIGIN.distance(p);
		    var color = new Color();
		    color.hsl(angle, distance, 100 - distance);
		    var rgbString = color.toString();
		    var hexString = color.toHexString();
		    var json = JSON.stringify({
			"type": "ambient",
			"colorHex": hexString,
			"rgbString": rgbString,
			"color": {
			    "r": color.r,
			    "g": color.g,
			    "b": color.b
			}
		    });
		    sendText(json);
		    drawImageText(json);


		    canvas.style.backgroundColor = rgbString;
		    context.fillStyle = rgbString;
		    context.fillRect(0, 0, 150, 37.5);
		});
    }, 1000);

});
var canvas = document.getElementById("myCanvas");
var context = canvas.getContext("2d");

function onColorWheel(event) {
    var evtX = event.pageX;//clientX;
    var evtY = event.pagey;//clientY;
    var svgElement = o;//evt.target;
    var svgDocument = svgElement.contentDocument;//ownerDocument;
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
    var ORIGIN = new Point(0.5, 0.5);
    var p = new Point(relativeEvtPointX, relativeEvtPointY);
    var angle = ORIGIN.bearing(p);
    angle = 360 - ((angle + 270) % 360);
    distance = 200 * ORIGIN.distance(p);
    var color = new Color();
    color.hsl(angle, distance, 100 - distance);
    var rgbString = color.toString();
    var hexString = color.toHexString();
    var json = JSON.stringify({
	"type": "ambient",
	"colorHex": hexString,
	"rgbString": rgbString,
	"color": {
	    "r": color.r,
	    "g": color.g,
	    "b": color.b
	}
    });
    sendText(json);
    drawImageText(json);


    canvas.style.backgroundColor = rgbString;
    context.fillStyle = rgbString;
    context.fillRect(0, 0, 150, 37.5);
}




function drawImageText(image) {
    console.log("drawImageText");
    var json = JSON.parse(image);
    console.log("->" + json.rgbString);
    canvas.style.backgroundColor = json.rgbString;
    context.fillStyle = json.rgbString;
    context.fillRect(0, 0, 150, 37.5);
}