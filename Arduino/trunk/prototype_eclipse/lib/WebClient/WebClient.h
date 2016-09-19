/*
 * WebClient.hpp
 *
 *  Created on: Sep 15, 2016
 *      Author: phil
 */

#ifndef SRC_WEBCLIENT_HPP_
#define SRC_WEBCLIENT_HPP_

#include <Phant.h>
#include <stddef.h>
#include <stdint.h>
#include <WString.h>
#include <SFE_CC3000.h>
#include <SFE_CC3000_Client.h>

////////////////////////////////////
// CC3000 Shield Pins & Variables //
////////////////////////////////////
// Don't change these unless you're using a breakout board.
#define INTERRUPT_PIN		2   // Needs to be an interrupt pin
#define ENABLE_PIN			7   // Enable pin (can be any digital pin)
#define CHIP_SELECT_PIN		10  // Chip select pin

#define IP_ADDRESS_LENGTH	4

class WebClient{
public:
	WebClient(const char *dataHost, uint16_t port, const char *publicKey, const char *privateKey);
	~WebClient();

	/**
	  * @brief Connects to an Access Point(AP)
	  *
	  * @param[in] ssid SSID of the AP
	  * @param[in] security AP security (WLAN_SEC_UNSEC, WLAN_SEC_WEP, WLAN_SEC_WPA, or WLAN_SEC_WPA2)
	  * @param[in] password optional ASCII password if connecting to a secured AP
	  * @param[in] timeout optional time (ms) to wait before stopping. 0 = no timeout
	  * @return True if connected to wireless network. False otherwise.
	  */
	bool connectToWifi(char *ssid, uint8_t security, char *password = NULL, uint16_t timeout = 0);

	/**
	  * @brief Grabs the IP address
	  *
	  * @param[out] ipAddress string that will contain the IP address
	  * @return True if IP address could be read. False otherwise.
	  */
	bool getIpAddress(String &ipAddress);


	void postData(Phant &phant);

private:

	SFE_CC3000 *_wifi;
	SFE_CC3000_Client *_webClient;

	const char *_dataHost;
	uint16_t _port;
	const char *_publicKey;
	const char *_privateKey;
};



#endif /* SRC_WEBCLIENT_HPP_ */
