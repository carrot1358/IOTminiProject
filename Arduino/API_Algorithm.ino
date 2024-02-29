#include <String.h>
#include <ArduinoJson.h>

const String Update_apiUrl = "http://192.168.235.51:8080/api/sensor/UpdateValueSensor";
const String Save_apiUrl = "http://192.168.255.51:8080/device/save";
const String FindByName_apiUrl = "http://192.168.255.51:8080/device/findByName";
const String FindAll_apiUrl = "http://192.168.255.51:8080/device/findAll";

void updateDevice(String name,double value){
  if ((WiFi.status() == WL_CONNECTED)) {

    WiFiClient client;
    HTTPClient http;

    String requestBody = "{\"name\":\"" + name + "\",\"value\":\"" + String(value) + "\"}";

    Serial.print("[HTTP] begin...");
    http.begin(client, Update_apiUrl);  
    http.addHeader("Content-Type", "application/json");
    Serial.print("[HTTP] POST...");

    int httpCode = http.POST(requestBody);
    Serial.print(http.errorToString(httpCode).c_str());

    if (httpCode > 0) {
      if (httpCode == HTTP_CODE_OK) {
        const String& payload = http.getString();

        // Parse the JSON response
        JsonDocument jsonDoc;
        DeserializationError error = deserializeJson(jsonDoc, payload);
        if (error) {
            Serial.print("[JSON] Parsing failed: ");
            Serial.println(error.c_str());
            return; 
        }

        // Extract the "status" field from the response
        String updateStatus = jsonDoc["status"];
        if (updateStatus == "Success") {
            // Extract the "dataValue" object from the response
            JsonObject responseData = jsonDoc["dataValue"];

            // Extract values from the "dataValue" object
            String updatedId = responseData["id"];
            String updatedName = responseData["name"];
            String updatedPin = responseData["pin"];
            String updatedValue = responseData["value"];
            String updatedDeviceID = responseData["deviceId"];

            String responseText = "[HTTP] Update => ID: " + updatedId + " name: " + updatedName + " pin: " + updatedPin + " value: " + updatedValue + " Device_ID: " + updatedDeviceID;
            Serial.println(responseText);
        } else if (updateStatus == "Failed") {
            String failedMessage = jsonDoc["message"];
            String responseText = "[HTTP] Update => Failed by " + failedMessage;
            Serial.println(responseText);
        } else {
            String responseText = "[HTTP] Update => Failed by invalid response";
            Serial.println(responseText);
        }
        
      } else {
        Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }
    } else {
      Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }
}

String findByName(String name,String returnItem){
  if ((WiFi.status() == WL_CONNECTED)) {

    WiFiClient client;
    HTTPClient http;

    String requestBody = name;

    Serial.print("[HTTP] begin...\n");
    http.begin(client, FindByName_apiUrl);  
    http.addHeader("Content-Type", "application/json");

    Serial.print("[HTTP] POST...\n");
    // start connection and send HTTP header and body
    int httpCode = http.POST(requestBody);

    // httpCode will be negative on error
    if (httpCode > 0) {
      if (httpCode == HTTP_CODE_OK) {
        const String& payload = http.getString();
        Serial.println(payload);
        // Parse the JSON response
        JsonDocument jsonDoc;
        DeserializationError error = deserializeJson(jsonDoc, payload);
        if (error) {
          Serial.print("[JSON] Parsing failed: ");
          Serial.println(error.c_str());
          return ""; // Exit if parsing fails
        }

        // Extract the "status" value
        String message = jsonDoc["message"];
        String name = jsonDoc["data"][0]["name"];
        String status = jsonDoc["data"][0]["device_status"];
        String value = jsonDoc["data"][0]["value"];
        String time = jsonDoc["data"][0]["time"];


        // Print the extracted status
        Serial.println("**********************");
        Serial.print("[HTTP] message: ");
        Serial.println(message);
        Serial.print("[HTTP] name: ");
        Serial.println(name);
        Serial.print("[HTTP] status: ");
        Serial.println(status);
        Serial.print("[HTTP] value: ");
        Serial.println(value);
        Serial.print("[HTTP] time: ");
        Serial.println(time);
        Serial.print("**********************");

        if(returnItem == "status"){
          return status;
        }else if(returnItem == "value"){
          return value;
        }
        return "";
        
      } else {
        Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }
    } else {
      Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
    return "";
  }
  return "";
}

void findAll(){
  if ((WiFi.status() == WL_CONNECTED)) {

    WiFiClient client;
    HTTPClient http;

    Serial.print("[HTTP] begin...\n");
    http.begin(client, FindAll_apiUrl);  
    http.addHeader("Content-Type", "application/json");

    String requestBody = "";
    Serial.print("[HTTP] POST...\n");
    // start connection and send HTTP header and body
    int httpCode = http.POST(requestBody);

    // httpCode will be negative on error
    if (httpCode > 0) {
      if (httpCode == HTTP_CODE_OK) {
        const String& payload = http.getString();

        // Parse the JSON response
        JsonDocument jsonDoc;
        DeserializationError error = deserializeJson(jsonDoc, payload);
        if (error) {
          Serial.print("[JSON] Parsing failed: ");
          Serial.println(error.c_str());
          return; // Exit if parsing fails
        }

        // Extract the "status" value
        String name = jsonDoc["name"];
        bool updateStatus = jsonDoc["Device_status"];
        double updatevalue = jsonDoc["value"];
        String time = jsonDoc["time"];


        // Print the extracted status
        Serial.println("**********************");
        Serial.print("[HTTP] name: ");
        Serial.println(name);
        Serial.print("[HTTP] status: ");
        Serial.println(updateStatus);
        Serial.print("[HTTP] value: ");
        Serial.println(updatevalue);
        Serial.print("[HTTP] time: ");
        Serial.println(time);
        Serial.print("**********************");

        
      } else {
        Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }
    } else {
      Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    http.end();
  }
}