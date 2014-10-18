
var canvas = document.getElementById("myCanvas");
var context = canvas.getContext("2d");
function onColorWheel(evt) {
    var evtX = evt.clientX;
    var evtY = evt.clientY;
    var svgElement = evt.target;
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
	"colorHex": hexString,
	"rgbString": rgbString,
	"color": {
	    "r": color.r,
	    "g": color.g,
	    "b": color.b
	}
    });
    drawImageText(json);
    sendText(json);

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