/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.dataflow.examples;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.DateTime;


public class WindowedWordCount2 {
	
	private static int contador;

   public WindowedWordCount2()
   {
	   //new Sensor();
	   //new Data();
   }
	
   
	
  public Sensor parseJsonToObject(String entry)
  { 
	  //Sensor sensor=new Sensor();
	  Sensor sensor=null;
	  
	  try
	  {			  
		  ObjectMapper mapper=new ObjectMapper();
		  
		  sensor=mapper.readValue(entry, Sensor.class);
		  
		  return sensor;
	  }
	  catch(IOException e)
	  {
		  System.out.println("Denro del catch:  "+e.getMessage());		  
	  }
	  return sensor;	  
  }
  


  public static class Data {

  private String temperature;
  private String pressure;

  public String getTemperature() {
  return temperature;
  }

  public void setTemperature(String temperature) {
  this.temperature = temperature;
  }

  public String getPressure() {
  return pressure;
  }

  public void setPressure(String pressure) {
  this.pressure = pressure;
  }

  }
  

  public static class Sensor {

  private String deviceId;
  private String channel;
  private Long timestamp;
  private Data data;
  
  public String getDeviceId() {
  return deviceId;
  }

  public void setDeviceId(String deviceId) {
  this.deviceId = deviceId;
  }

  public String getChannel() {
  return channel;
  }

  public void setChannel(String channel) {
  this.channel = channel;
  }

  public Long getTimestamp() {
  return timestamp;
  }

  public void setTimestamp(Long timestamp) {
  this.timestamp = timestamp;
  }

  public Data getData() {
  return data;
  }

  public void setData(Data data) {
  this.data = data;
  }

  }  
  
  
  

  public static void main(String[] args) throws IOException {
	  String json="{\"deviceId\":\"rpi3\",\"channel\":\"pubsub\",\"timestamp\":1506028914249,\"data\":{\"temperature\":\"40.35922\",\"pressure\":\"941.9607\"}}";	
	  WindowedWordCount2 obj=new WindowedWordCount2();
	  Sensor  sensor= sensor= obj.parseJsonToObject(json);
	  System.out.println("DeviceId:"+ sensor.getDeviceId());
	  System.out.println("Channel:"+sensor.getChannel());
	  System.out.println("Timestamp STRING:"+new DateTime(sensor.getTimestamp()).toString());
	  System.out.println(sensor.getData().getTemperature());
	  System.out.println(sensor.getData().getPressure());
	  
	  System.out.println("---------------");
	  
	  json="{\"deviceId\":\"rpi4\",\"channel\":\"pubsub2\",\"timestamp\":1506028914249,\"data\":{\"temperature\":\"47.35922\",\"pressure\":\"999.9607\"}}";		  
	  sensor= sensor= obj.parseJsonToObject(json);
	  System.out.println("DeviceId:"+ sensor.getDeviceId());
	  System.out.println("Chann	el:"+sensor.getChannel());
	  System.out.println("Timestamp STRIN:"+ new DateTime(sensor.getTimestamp()).toStringRfc3339());
	  System.out.println("Timestamp:"+new Date(sensor.getTimestamp()));
	  System.out.println(sensor.getData().getTemperature());
	  System.out.println(sensor.getData().getPressure());
	  	  	  
  }
}
