import serial
import time

ser = serial.Serial('COM3', 9600, timeout=0)

while 1:
	try:
		if ser.is_open:
			serInput = ser.readline().decode('utf-8')

			print(serInput) if serInput != '' else None

			time.sleep(0.3)
		else:
			ser = serial.Serial('COM3', 9600, timeout=0)

	except:
		print('No data')
		
		if ser.is_open:
			ser.close()
	
		time.sleep(0.3)

