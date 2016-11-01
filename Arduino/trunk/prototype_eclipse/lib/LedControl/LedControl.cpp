/*
 * LedControl.cpp
 *
 *  Created on: Oct 7, 2016
 *      Author: phil
 */

#include <Arduino.h>
#include "LedControl.h"

LedControl::LedControl(uint8_t ledPin) :
		ledPin(ledPin), ledOn(false) {

	pinMode(ledPin, OUTPUT);
}

void LedControl::turnOn() {
	ledOn = true;
	changeBrightness(255);
}

void LedControl::turnOff() {
	changeBrightness(0);
	ledOn = false;
}

void LedControl::changeBrightness(uint8_t brightness) {
	if (ledOn) {
		analogWrite(ledPin, brightness);
		delay(10);
	}
}

void LedControl::toggle() {
	if (ledOn) {
		turnOff();
	} else {
		turnOn();
	}
}
