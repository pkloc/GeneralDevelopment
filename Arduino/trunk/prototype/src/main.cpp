// SPI and the pair of SFE_CC3000 include statements are required
// for using the CC300 shield as a client device.
//#include "Arduino.h"
#include <SPI.h>
#include <SFE_CC3000.h>
#include <SFE_CC3000_Client.h>

////////////////////////////////////
// CC3000 Shield Pins & Variables //
////////////////////////////////////
// Don't change these unless you're using a breakout board.
#define CC3000_INT      2   // Needs to be an interrupt pin (D2/D3)
#define CC3000_EN       7   // Can be any digital pin
#define CC3000_CS       10  // Preferred is pin 10 on Uno
#define IP_ADDR_LEN     4   // Length of IP address in bytes

////////////////////
// WiFi Constants //
////////////////////
char ap_ssid[] = "attic";                // SSID of network
char ap_password[] = "dupasalata";        // Password of network
unsigned int ap_security = WLAN_SEC_WPA2; // Security of network
// ap_security can be any of: WLAN_SEC_UNSEC, WLAN_SEC_WEP,
//  WLAN_SEC_WPA, or WLAN_SEC_WPA2

unsigned int timeout = 30000;             // Milliseconds
char server[] = "data.sparkfun.com";      // Remote host site

// Initialize the CC3000 objects (shield and client):
SFE_CC3000 wifi = SFE_CC3000(CC3000_INT, CC3000_EN, CC3000_CS);
SFE_CC3000_Client client = SFE_CC3000_Client(wifi);

/////////////////
// Phant Stuff //
/////////////////
const String publicKey = "7JOaE6DMLwt0m6nAndKx";
const String privateKey = "mzZ2N5dbJXiy5EkDkaAm";
//////////////////

const float WEIGHT_DIFF_THRESHOLD = 5.0;
float lastWeight = 0.0;

String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete
boolean firstRun = true;

enum AlarmState {
	IDLE, ARMED, ALARM
};

AlarmState alarmState = IDLE;

void setupWiFi()
{
  ConnectionInfo connection_info;
  byte i;

  // Initialize CC3000 (configure SPI communications)
  wifi.init();

  // Connect using DHCP
  Serial.print(F("Connecting to: "));
  Serial.println(ap_ssid);
  if(!wifi.connect(ap_ssid, ap_security, ap_password, timeout))
  {
    // Error: 1 - Could not connect to AP
    Serial.println(F("Error: Failed to connect to AP"));
  }

  // Gather connection details and print IP address
  if ( !wifi.getConnectionInfo(connection_info) )
  {
    // Error: 2 - Could not obtain connection details
    Serial.println(F("Error: Could not obtain IP"));
  }
  else
  {
    Serial.print(F("My IP: "));
    for (i = 0; i < IP_ADDR_LEN; i++)
    {
      Serial.print(connection_info.ip_address[i]);
      if ( i < IP_ADDR_LEN - 1 )
      {
        Serial.print(".");
      }
    }
    Serial.println();
  }
  Serial.println(F("Wifi setup complete."));
}

void setup()
{
	inputString.reserve(75);

	Serial.begin(9600);

	delay(5000);

	// Set Up WiFi:
	setupWiFi();
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
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

void postData(float oldWeight, float newWeight, AlarmState alarmState)
{
	byte NUM_FIELDS = 3;
	String fieldNames[] = {"old_weight", "new_weight", "alarm_state"};

	String alarmText = "";
	if( alarmState == IDLE ){
		alarmText = "IDLE";
	}else if( alarmState == ARMED ){
		alarmText = "ARMED";
	}else if( alarmState == ALARM ){
		alarmText = "ALARM";
	}

	String fieldData[] = { String(oldWeight), String(newWeight), alarmText };


  // Make a TCP connection to remote host
  if ( !client.connect(server, 80) )
  {
    // Error: 4 - Could not make a TCP connection
    //Serial.println(F("Error: 4"));
  }

  // Post the data! Request should look a little something like:
  // GET /input/publicKey?private_key=privateKey&light=1024&switch=0&time=5201 HTTP/1.1\n
  // Host: data.sparkfun.com\n
  // Connection: close\n
  // \n
  client.print("GET /input/");
  client.print(publicKey);
  client.print("?private_key=");
  client.print(privateKey);
  for (int i=0; i<NUM_FIELDS; i++)
  {
    client.print("&");
    client.print(fieldNames[i]);
    client.print("=");
    client.print(fieldData[i]);
  }
  client.println(" HTTP/1.1");
  client.print("Host: ");
  client.println(server);
  client.println("Connection: close");
  client.println();

  while (client.connected())
  {
    if ( client.available() )
    {
      char c = client.read();
      Serial.print(c);
    }
  }
  Serial.println();

  delay(5000);
}

void loop()
{
	if (stringComplete) {

		Serial.println(inputString);

		float weight = inputString.toFloat();
	    inputString = "";
	    stringComplete = false;

	    if(abs(weight - lastWeight) > WEIGHT_DIFF_THRESHOLD){

	    	if(weight < lastWeight){
	    		alarmState = ALARM;
	    		postData(lastWeight, weight, alarmState);

	    		//runCaptureTextToSpeechPrompt();	   // This would be a call to Temboo.

	    	}else{
	    		alarmState = ARMED;
	    		postData(lastWeight, weight, alarmState);
	    	}

	    	lastWeight = weight;
	    }

	    if(firstRun){
	    	alarmState = IDLE;
	    	postData(lastWeight, weight, alarmState);
	    	firstRun = false;
	    }
	 }
}
