
//http://www.workwithcolor.com/hsl-color-picker-01.htm
//
////////////////////////////////////////////
//http://codereview.stackexchange.com/questions/32539/best-way-to-work-out-angle-between-points
//http://jsfiddle.net/zvmMN/
function Point(x, y) {
    this.x = x;
    this.y = y;
}

Point.prototype.subtract = function (point) {
    return new Point(this.x - point.x, this.y - point.y);
};

// Computes orientation of point relative to this,
// with 12 o'clock being 0 degrees, angles
// increasing clockwise, with return values in
// the range [0, 360].  Implemented like
// unconditionalAngle().
Point.prototype.bearing = function (point) {
    var delta = point.subtract(this);
    return 180 + 180 / Math.PI * Math.atan2(-delta.x, -delta.y);
};
//////////////////////////////////////////

Point.prototype.distance = function (point) {
    var delta = point.subtract(this);
    return Math.sqrt(delta.x * delta.x + delta.y * delta.y);
}