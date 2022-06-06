/*
* Hei_avansert.java  G.S 2015-08-18
*
* Et enkelt program som skriver en tekst i LCD vinduet på ev3 - enheten
* har to metoder(A og C), en som genererer 20 tilfeldig toner, en som snurrer litt på motorene og en som
* spiller en wav-fil (8 bit, 8000 hz, mono)
*/


import java.util.*;

import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.Sound.*;
import lejos.hardware.lcd.*;

import java.io.File;

public class HeiVerdenAvansert {

	public static int  sing(File file) throws Exception{
		return Sound.playSample(file, 50);
	}

    //Method created to speak in R2D2 Language
    public static void speak() throws Exception{
        Random rnd = new Random();

        for(int i=0; i<=20; i++){
            int frequency = rnd.nextInt(1000);
            int duration = rnd.nextInt(1000);
            Sound.playTone(i*frequency, duration);
            Thread.sleep(100);
        }
    }

   //Method created
    public static int motorTest() throws Exception{

       Motor.A.setSpeed(720);// 2 RPM
	   Motor.C.setSpeed(720);
	   Motor.A.forward();
	   Motor.C.forward();
	   Thread.sleep (1000);
	   Motor.A.stop();
	   Motor.C.stop();

	   Motor.A.rotateTo( 360);
	   Motor.A.rotate(-720,true);
	   int angle = Motor.A.getTachoCount(); // should be -360
 	   return angle;

}
    //Main Class method
    public static void main(String[] args) throws Exception
    {
        System.out.println("[EV3 Hei]\nHello");
		Sound.setVolume(10);
      //  speak();

		File file = new File("starwars_imperial_8.wav");
		int wavfilelength = sing(file);
        LCD.clear();
        System.out.println("[EV3 Hei]\nWav-status: " + wavfilelength);
        System.out.println("[EV3 Hei]\nSee you later");

        int vinkel = motorTest();

        LCD.clear();
        System.out.println("Vinkel: " + vinkel);
        Thread.sleep(2000);
    }
}
