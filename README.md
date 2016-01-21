# AR-Drone-2.0-Android-API

## Abstract
We are Industrial Programming students and this is the result of our project.
The goal of this project was to code an API to allow anyone to use Parrot's AR Drone 2.0 with their Android applications.

This interface will provide the user with Java routines that can be easily integrated into an Android project. Thus it must be possible with this API to write an AR Drone application without having to understand the drone’s specifications. 

## Services
The API will provide five main services: 
*	Managing the connection and data transmission between the drone and the smartphone. The UDP protocol will be used in order to set up a link between the devices.
*	Generating the commands that will be sent to the drone. These are AT commands that require a specific syntax.
*	Setting up the drone before take-off (such as setting the maximum altitude, video frame rate…).
*	Receiving and interpreting the navigation data coming from the drone in order to display useful information such as altitude and speed of the aircraft.
*	Receiving the video streamed by the drone in order to display it in real time. It will also be possible to record the video stream.

Thus this interface will allow the user to set up and pilot the drone. It will also allow him to display and record the camera stream, which can be used for further processing.

## How to install

1. Download and unzip "AR-Drone-2.0.zip"
2. Open the project in Android Studio
3. Compile the project into your smartphone
4. Power up the drone and connect your smartphone to its WiFi hotspot
5. You're good to go

## Libraries dependencies

* [Vitamio] (https://vitamio.org/) is used to display the drone's video feed in real time. 

## What's included
You will find in this repository all the Java classes, as well as an Android project that can be used as an example.

## Authors
We are [Electrical Engineering and Industrial Programming Students] (https://www.iut-acy.univ-smb.fr/) at the [University of Savoie Mont-Blanc] (https://www.univ-smb.fr/).
* [Jules Simon] (https://github.com/JulesSimonGEII)
* Alexis Artigues
* Mathieu Collomb
* Antoine Crosetti
* Jean-Christophe Chudziak

## Mentor

* Bernard Caron

