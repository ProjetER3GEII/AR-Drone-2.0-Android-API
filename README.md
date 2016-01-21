# AR-Drone-2.0-Android-API

## Abstract
The goal of this API is to allow anyone to use Parrot's AR Drone 2.0 with their Android applications.

This interface will provide the user with Java routines that can be easily integrated into an Android project. Thus it must be possible with this API to write an AR Drone application without having to understand the drone’s specifications. 

## Services
The API will provide five main services: 
*	Managing the connection and data transmission between the drone and the smartphone. The UDP protocol will be used in order to set up a link between the devices.
*	Generating the commands that will be sent to the drone. These are AT commands that require a specific syntax.
*	Setting up the drone before take-off (such as setting the maximum altitude, video frame rate…).
*	Receiving and interpreting the navigation data coming from the drone in order to display useful information such as altitude and speed of the aircraft.
*	Receiving the video streamed by the drone in order to display it in real time. It will also be possible to record the video stream.
*	
Thus this interface will allow the user to set up and pilot the drone. It will also allow him to display and record the camera stream, which can be used for further processing.

## What's included
You will find in this repository all the Java classes, as well as an Android project that can be used as an example.

## Authors

*Jules Simon 
*Alexis Artigues
*Mathieu Collomb
*Antoine Crosetti
*Jean-Christophe Chudziak
