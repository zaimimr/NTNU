import lejos.hardware.motor.*;

public class Engine {

	private static NXTRegulatedMotor left; //Left wheel-motor
	private static NXTRegulatedMotor right; //Right wheel-motor
	private static int RPM = 450; //The speed

	public Engine(NXTRegulatedMotor a,NXTRegulatedMotor b){ //Constructor A
		InitializeEngineValues (a,b,RPM);
	}

	public Engine(NXTRegulatedMotor a,NXTRegulatedMotor b,int rpm){ //Constructor B
			this.RPM = rpm;
			InitializeEngineValues (a,b,RPM);
	}

	private void InitializeEngineValues (NXTRegulatedMotor a,NXTRegulatedMotor b,int rpm){  //Initializes the motor values and speed
		this.left = a;
		this.right = b;
		this.left.setSpeed(rpm);
		this.right.setSpeed(rpm);
	}

	public NXTRegulatedMotor getLeftMotor (){
		return left;
	}

	public NXTRegulatedMotor getRightMotor (){
		return right;
	}

	public void moveForward (){
		left.forward ();
		right.forward ();
	}

	public void moveBackwards (){
		left.backward ();
		right.backward ();
	}

	public void stop (){
		left.stop ();
		right.stop ();
	}

	public void rotate(int angle,int direction){
		if(direction == 0){left.rotate(angle);}
		else{right.rotateTo(angle);}
	}

	public void rotate(int angle,NXTRegulatedMotor motor){
			motor.rotateTo(angle);
	}




}
