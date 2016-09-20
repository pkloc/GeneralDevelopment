/*
 * WeightSensor.cpp
 *
 *  Created on: Sep 19, 2016
 *      Author: phil
 */

#include <Arduino.h>
#include <WeightSensor.h>

WeightSensor::WeightSensor(){

	_incomingData.reserve(75);

	Serial1.begin(9600);
	delay(1000);
}

float
WeightSensor::getWeight(){

	float retVal = _incomingData.toFloat();

	_incomingData = "";
	_dataComplete = false;

	return retVal;
}

bool
WeightSensor::weightAvailable(){

	getSerialData();

	return _dataComplete;
}

void
WeightSensor::getSerialData(){
	while (Serial1.available()) {

		char inChar = (char)Serial1.read();

		_incomingData += inChar;

		if (inChar == '\n') {
			int subStringStart = _incomingData.lastIndexOf(':') + 3;
			int subStringEnd = _incomingData.length() - 5;
			_incomingData = _incomingData.substring(subStringStart, subStringEnd);

			_dataComplete = true;
		}
	}
}
