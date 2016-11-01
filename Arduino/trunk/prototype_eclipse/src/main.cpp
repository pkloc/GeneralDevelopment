// SPI and the pair of SFE_CC3000 include statements are required
// for using the CC300 shield as a client device.
//#include "Arduino.h"
#include <Arduino.h>
#include <Phant.h>
#include <SPI.h>
#include <WebClient.h>
#include <WeightSensor.h>
#include <LedControl.h>

////////////////////
// WiFi Constants //
////////////////////
char ap_ssid[] = "attic";                // SSID of network
char ap_password[] = "dupasalata";        // Password of network
uint8_t ap_security = WLAN_SEC_WPA2; // Security of network
uint16_t timeout = 30000;             // Milliseconds

/////////////////////
// Phant Constants //
/////////////////////
const char phantHost[] = "data.sparkfun.com";      // Remote host site
const uint16_t hostPort = 80;
const char publicKey[] = "7JOaE6DMLwt0m6nAndKx";
const char privateKey[] = "mzZ2N5dbJXiy5EkDkaAm";

WebClient *webClient = NULL;
WeightSensor *weightSensor = NULL;
LedControl *ledControl;

//////////////////
const float WEIGHT_DIFF_THRESHOLD = 5.0;
float oldWeight = 0.0;
boolean firstRun = true;

void setupWiFi()
{
 	webClient = new WebClient(phantHost, hostPort, publicKey, privateKey);

	Serial.print("Connecting to: ");
	Serial.println(ap_ssid);

	if(webClient->connectToWifi(ap_ssid, ap_security, ap_password, timeout)){
		String ipAddress = "";
		if(webClient->getIpAddress(ipAddress)){
			Serial.print("Got IP: ");
			Serial.println(ipAddress);
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
	Serial.begin(9600);
	delay(1000);

	// Set Up WiFi:
	setupWiFi();

	// Set up the Weight Sensor reader
	weightSensor = new WeightSensor();

	ledControl = new LedControl(5);

	Serial2.begin(115200);
}

void handleWeightReading(float newWeight) {

	Serial.println(newWeight);

	Phant phant(phantHost, publicKey, privateKey);

	if (firstRun) {
		phant.add("old_weight", oldWeight);
		phant.add("new_weight", newWeight);
		phant.add("alarm_state", "IDLE");

		webClient->postData(phant);

		firstRun = false;
	} else if (abs(newWeight - oldWeight) > WEIGHT_DIFF_THRESHOLD) {

		phant.add("old_weight", oldWeight);
		phant.add("new_weight", newWeight);

		if (newWeight < oldWeight) {
			phant.add("alarm_state", "ALARM");

		} else {
			phant.add("alarm_state", "ARMED");
		}
		webClient->postData(phant);

		oldWeight = newWeight;
	}

}

void handleCommandsFromBluetooth() {
	String bluetoothMessage = "";
	char data;

	while (Serial2.available() > 0) {
		data = (byte) Serial2.read();

		if (data == ':') {
			break;
		} else {
			bluetoothMessage += data;
		}
		delay(1);
	}

	if (bluetoothMessage.length() != 0) {

		if (bluetoothMessage == "TO") {
			Serial.println("Turning On");
			ledControl->turnOn();
		}

		else if (bluetoothMessage == "TF") {
			Serial.println("Turning Off");
			ledControl->turnOff();
		}

		else if ((bluetoothMessage.toInt() >= 0)
				&& (bluetoothMessage.toInt() <= 255)) {
			Serial.println(bluetoothMessage.toInt());
			ledControl->changeBrightness(bluetoothMessage.toInt());
		}
	}

}

void loop() {
	if (weightSensor->weightAvailable()) {
		float newWeight = weightSensor->getWeight();
		//handleWeightReading(newWeight);
		Serial.println(newWeight);
	}

	handleCommandsFromBluetooth();

}
