# LPsecureApp

## Software implementation in Sim Swap cyber attack

My Thesis Project was a software development with a front-end and a back-end Technology. I created an application in android devices that simulates e-banking apps using 
android studio and Laravel API. The importance of this application is to protect people being victims from sim swapping attacks. Apps like eurobank applications or nbg 
etc, uses a security system for authentication that is called otp, a six digit number that sends to the user in order to authenticate his ID. This OTP number based on 
sim card, constituting e-banking security vulnerable in Sim Swapping attacks. So i created an application that uses the DeviceId, a number that is unique to its physical 
device itself, sending the OTP via push notification. For this purpose needed two software applications such as a REST API to be responsible for the authentication of the
user and the communication bewteen the client-server, and the android application for the UI and services like registration, login and verification page with the OTP 
number. Moreover, both API and android app uses back-end and front-end technology itself. As far as the application's function is concerned, the first thing that it does
is to register the user with credentials and save them into database. With that, askes him if he agrees with the app, to retrieve the DeviceId from the device. Then we 
have the login page that matches the credentials of the user with the DeviceId and last we have the OTP verification that is limited. The connection between the android 
application and REST API is achieved with libraries that communicate with POST, GET, PUT etc requests that make it more secure to cyber attacks. For the demands of the 
application i use Laravel for the REST API and Android studio for the UI. The programming languages and scripts that needed was php, sql, json for the requests and java.
