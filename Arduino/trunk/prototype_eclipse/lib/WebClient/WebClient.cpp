/*
 * WebClient.cpp
 *
 *  Created on: Sep 15, 2016
 *      Author: phil
 */
#include "WebClient.h"

WebClient::WebClient(const char *dataHost, uint16_t port, const char *publicKey, const char *privateKey) :
	_dataHost(dataHost),
	_port(port),
	_publicKey(publicKey),
	_privateKey(privateKey)
{
	_wifi = new SFE_CC3000(INTERRUPT_PIN, ENABLE_PIN, CHIP_SELECT_PIN);

	_webClient = new SFE_CC3000_Client(*_wifi);
}

bool
WebClient::connectToWifi(char *ssid, uint8_t security, char *password, uint16_t timeout){

	_wifi->init();
	return _wifi->connect(ssid, security, password, timeout);
}

bool
WebClient::getIpAddress(String &ipAddress){

	ConnectionInfo connection_info;

	String myString;

	if (!_wifi->getConnectionInfo(connection_info)) {
		return false;
	} else {
		for (int i = 0; i < IP_ADDRESS_LENGTH; i++) {
			ipAddress.concat(connection_info.ip_address[i]);
			if (i < IP_ADDRESS_LENGTH - 1) {
				ipAddress.concat(".");
			}
		}
	}
	return true;
}

void
WebClient::postData(Phant &phant){

	if ( !_webClient->connect(_dataHost, _port) )
	{
		Serial.println(F("Error: TCP connect failed"));
	}

	_webClient->print("GET /input/");
	_webClient->print(_publicKey);
	_webClient->print("?private_key=");
	_webClient->print(_privateKey);
	_webClient->print(phant.queryString());
	_webClient->println(" HTTP/1.1");
	_webClient->print("Host: ");
	_webClient->println(_dataHost);
	_webClient->println("Connection: close");
	_webClient->println();

	while(_webClient->connected()){

		if ( _webClient->available() )
		{
		  char c = _webClient->read();
		  Serial.print(c);
		}
	}
}


