

function Color() {
    this.r = 0;
    this.g = 0;
    this.b = 0;
}

Color.prototype.toString = function () {
    return ["rgb(", Math.floor(this.r), ",", Math.floor(this.g), ",", Math.floor(this.b), ")"].join("");
};

Color.prototype.toHexString = function () {
    return '#' + ((Math.floor(this.r) << 16) | (Math.floor(this.g) << 8) | Math.floor(this.b)).toString(16);
};

/////////////////////////////////////////
//h=angle (0..360)
//s=saturation (0..100)
//l=light (0..100)
Color.prototype.hsl = function (h, s, l) {
    var m1, m2, hue;
    s /= 100;
    l /= 100;
    if (s === 0)
	this.r = this.g = thisb = (l * 255);
    else {
	if (l <= 0.5)
	    m2 = l * (s + 1);
	else
	    m2 = l + s - l * s;
	m1 = l * 2 - m2;
	hue = h / 360;
	this.r = hueToRgb(m1, m2, hue + 1 / 3);
	this.g = hueToRgb(m1, m2, hue);
	this.b = hueToRgb(m1, m2, hue - 1 / 3);
    }
};

function hueToRgb(m1, m2, hue) {
    var v;
    if (hue < 0)
	hue += 1;
    else if (hue > 1)
	hue -= 1;

    if (6 * hue < 1)
	v = m1 + (m2 - m1) * hue * 6;
    else if (2 * hue < 1)
	v = m2;
    else if (3 * hue < 2)
	v = m1 + (m2 - m1) * (2 / 3 - hue) * 6;
    else
	v = m1;

    return 255 * v;
}
/////////////////////////////////////////