from tkinter import *
from serial import *
from builtins import print

serial_port = 'COM3'
baud_rate = 9600
ser = Serial(serial_port, baud_rate, timeout=0)

rootWindow = Tk()
rootWindow.title("Phil's App")
rootWindow.geometry("300x300")

label = Label(rootWindow, text="Hello Earthling!")
label.pack();

def sayHello():
	print("Hello Earthling")

button = Button(rootWindow, text = "Do it", bg = "yellow", command = sayHello)
button.pack();

# make a scrollbar
scrollbar = Scrollbar(rootWindow)
scrollbar.pack(side = RIGHT, fill = Y)

# make a text box to put the serial output
log = Text(rootWindow, width=30, height=30, takefocus=0)
log.pack()

# attach text box to scrollbar
log.config(yscrollcommand=scrollbar.set)
scrollbar.config(command = log.yview)

serInput = ''

def readSerial():
    try:
        global ser
        global serInput
        
        if ser.is_open:
            serInput = serInput + ser.readline().decode('utf-8')

            #self.serial.read(self.serial.in_waiting or 1)

            #print(serInput) if serInput != '' else None
            if serInput.endswith('\n'):
                log.insert('0.0', serInput)
                serInput = ''

        else:
            ser = serial.Serial('COM3', 9600, timeout=0)
    except:
        print('No data')

        if ser.is_open:
            ser.close()

    rootWindow.after(100, readSerial)

rootWindow.after(100, readSerial)

rootWindow.mainloop()
