/**
   PostHTTPClient.ino

    Created on: 21.11.2016

*/
#include <BH1750.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <time.h>
#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
#include "DHT.h"


//DHT Define
#define DHTPIN 2  
#define DHTTYPE    DHT22  
DHT dht(DHTPIN, DHTTYPE);

//OLED Define
#define SCREEN_WIDTH 128 // pixel ความกว้าง
#define SCREEN_HEIGHT 64 // pixel ความสูง 
#define OLED_RESET     -1 
Adafruit_SSD1306 OLED(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

//BH1750
BH1750 lightMeter;

const String deviceName = "led1"; // Name of the device to update
const int led1 = D0, SW = D3,DHT = D4; // LED pin
bool status = false;
int value;

#ifndef STASSID
#define STASSID "Test"
#define STAPSK "0945756665"
#endif

void setup() {

  Serial.begin(115200);
  //Init Oled
  if (!OLED.begin(SSD1306_SWITCHCAPVCC, 0x3C)) { 
    Serial.println("SSD1306 allocation failed");
  } else {
    Serial.println("OLED Start Work !!!");
  }

  //Init DHT
  dht.begin();

  //Init Wire
  Wire.begin();
  lightMeter.begin();

  pinMode(SW,INPUT_PULLUP);
  pinMode(led1,OUTPUT);

  Serial.println();
  Serial.println();
  Serial.println();

  WiFi.begin(STASSID, STAPSK);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected! IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {

  if(digitalRead(SW)==0){
    while(digitalRead(SW)==0);
    delay(100);
    Serial.print("Click SWITCH !!");
    
  }

//DHT
  double h = dht.readHumidity();
  double t = dht.readTemperature();
  updateDevice("Temp", t);
  updateDevice("Hum", h);


//BH1750
  if (lightMeter.measurementReady()) {
    double lux = lightMeter.readLightLevel();
    updateDevice("Light1", lux);
  }
  
  delay(1000);
}


