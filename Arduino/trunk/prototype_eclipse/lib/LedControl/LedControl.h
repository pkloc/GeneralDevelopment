/*
 * LedControl.h
 *
 *  Created on: Oct 7, 2016
 *      Author: phil
 */

#ifndef LIB_LEDCONTROL_LEDCONTROL_H_
#define LIB_LEDCONTROL_LEDCONTROL_H_

class LedControl {
public:
	LedControl(uint8_t ledPin);
	~LedControl();

	void turnOn();

	void turnOff();

	void changeBrightness(uint8_t brightness);

	void toggle();

private:

	uint8_t ledPin;
	boolean ledOn;
};



#endif /* LIB_LEDCONTROL_LEDCONTROL_H_ */
