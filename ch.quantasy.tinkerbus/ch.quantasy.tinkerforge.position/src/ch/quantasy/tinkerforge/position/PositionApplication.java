package ch.quantasy.tinkerforge.position;

public class PositionApplication {
	public double radientToDegrees(double x){
	    return x*180.0/Math.PI;
}


	public  void calcAttitudeAcceleration(double ax, double ay, double az){
		ax = ax*9.806/1000.0;
		ay = ay*9.806/1000.0;
				az = az*9.806/1000.0;
	    

	    // Calculate Roll and Pitch from Acceleration
	    // is only valid in quasistatic situations
	    double rollAcceleration = radientToDegrees(Math.atan2(-ay, -az));
	    double pitchAcceleration= radientToDegrees(-Math.atan2(-ax, Math.sqrt(ay*ay+az*az)));

	    System.out.printf("ax:\t%+6.0f\tay:\t%+6.0f\taz:\t%+6.0f\troll:\t%+6.0f\tpitch:\t%+6.0f", ax, ay, az, rollAcceleration, pitchAcceleration);
}
	public void calcAttitude(double x, double y, double z, double w){

	    // Calculate Attitude
	    // Buchholz, J. J. (2013). Vorlesungsmanuskript Regelungstechnik und Flugregler.
	    // GRIN Verlag. Retrieved from http://www.grin.com/de/e-book/82818/regelungstechnik-und-flugregler
	    double yaw = Math.atan2(2.0*(x*y + w*z), w*w+x*x-y*y-z*z);
	    double pitch = -Math.asin(2.0*(w*y - x*z));
	    double roll = -Math.atan2(2.0*(y*z + w*x), -(w*w-x*x-y*y+z*z));
	    System.out.printf("yaw:\t%6+6.0f\tpitch:\t%6+6.0f\roll:\t%6+6.0f",yaw,pitch,roll);
	}

}
