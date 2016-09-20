/*
 * WeightSensor.h
 *
 *  Created on: Sep 19, 2016
 *      Author: phil
 */

#ifndef WEIGHTSENSOR_H_
#define WEIGHTSENSOR_H_

#include <WString.h>

class WeightSensor{
public:
	WeightSensor();
	~WeightSensor();

	float getWeight();

	bool weightAvailable();

private:

	void getSerialData();

	String _incomingData = "";
	bool _dataComplete = false;
};



#endif /* WEIGHTSENSOR_H_ */
