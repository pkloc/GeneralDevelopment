// SPI and the pair of SFE_CC3000 include statements are required
// for using the CC300 shield as a client device.
//#include "Arduino.h"
#include <Arduino.h>
#include <Phant.h>
#include <SPI.h>
#include <WebClient.h>

////////////////////
// WiFi Constants //
////////////////////
char ap_ssid[] = "attic";                // SSID of network
char ap_password[] = "dupasalata";        // Password of network
uint8_t ap_security = WLAN_SEC_WPA2; // Security of network
uint16_t timeout = 30000;             // Milliseconds

WebClient *webClient = NULL;


/////////////////
// Phant Stuff //
/////////////////
const char phantHost[] = "data.sparkfun.com";      // Remote host site
const uint16_t hostPort = 80;
const char publicKey[] = "7JOaE6DMLwt0m6nAndKx";
const char privateKey[] = "mzZ2N5dbJXiy5EkDkaAm";

//////////////////

const float WEIGHT_DIFF_THRESHOLD = 5.0;
float oldWeight = 0.0;

String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete
boolean firstRun = true;


void setupWiFi()
{
 	webClient = new WebClient(phantHost, hostPort, publicKey, privateKey);

	Serial.print("Connecting to: ");
	Serial.println(ap_ssid);

	if(webClient->connectToWifi(ap_ssid, ap_security, ap_password, timeout)){
		String *ipAddress = new String();
		if(webClient->getIpAddress(*ipAddress)){
			Serial.print("Got IP: ");
			Serial.println(*ipAddress);
		}
		else{
			Serial.println("Could not get IP address");
		}
	}
	else{
		Serial.print("Could not connect to: ");
		Serial.println(ap_ssid);
	}
}

void setup()
{
	inputString.reserve(75);

	Serial.begin(9600);

	delay(1000);

	// Set Up WiFi:
	setupWiFi();

	Serial1.begin(9600);
	//delay(1000);

}


void getSerialData() {
  while (Serial1.available()) {
    // get the new byte:
    char inChar = (char)Serial1.read();
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {

    	int subStringStart = inputString.lastIndexOf(':') + 3;
    	int subStringEnd = inputString.length() - 5;
    	inputString = inputString.substring(subStringStart, subStringEnd);

    	stringComplete = true;
    }
  }
}

void loop()
{
	getSerialData();

	if (stringComplete) {

		Serial.println(inputString);

		float newWeight = inputString.toFloat();
	    inputString = "";
	    stringComplete = false;

	    Phant phant(phantHost, publicKey, privateKey);

	    if(firstRun){
			phant.add("old_weight", oldWeight);
			phant.add("new_weight", newWeight);
			phant.add("alarm_state", "IDLE");

			webClient->postData(phant);

			firstRun = false;
		}
	    else if(abs(newWeight - oldWeight) > WEIGHT_DIFF_THRESHOLD){

	    	phant.add("old_weight", oldWeight);
	    	phant.add("new_weight", newWeight);

	    	if(newWeight < oldWeight){
	    		phant.add("alarm_state", "ALARM");

	    	}else{
	    		phant.add("alarm_state", "ARMED");
	    	}
	    	webClient->postData(phant);

	    	oldWeight = newWeight;
	    }
	 }
}
