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


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.bigquery.model.JsonObject;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.dataflow.examples.WindowedWordCount2.Data;
import com.google.cloud.dataflow.examples.WindowedWordCount2.Sensor;
import com.google.cloud.dataflow.examples.WordCount.ExtractWordsFn;
import com.google.cloud.dataflow.examples.common.DataflowExampleOptions;
import com.google.cloud.dataflow.examples.common.DataflowExampleUtils;
import com.google.cloud.dataflow.examples.common.ExampleBigQueryTableOptions;
import com.google.cloud.dataflow.examples.common.ExamplePubsubTopicOptions;
import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.PipelineResult;
import com.google.cloud.dataflow.sdk.io.BigQueryIO;
import com.google.cloud.dataflow.sdk.io.PubsubIO;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.Default;
import com.google.cloud.dataflow.sdk.options.Description;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.transforms.Aggregator;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.transforms.PTransform;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import com.google.cloud.dataflow.sdk.transforms.Sum;
import com.google.cloud.dataflow.sdk.transforms.DoFn.ProcessContext;
import com.google.cloud.dataflow.sdk.transforms.windowing.FixedWindows;
import com.google.cloud.dataflow.sdk.transforms.windowing.Window;
import com.google.cloud.dataflow.sdk.values.KV;
import com.google.cloud.dataflow.sdk.values.PCollection;


import org.joda.time.Duration;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


import java.util.List;

public class WindowedWordCount {
    private static final Logger LOG = LoggerFactory.getLogger(WindowedWordCount.class);
    static final int WINDOW_SIZE = 0;  // Default window duration in minutes
    
    static class FormatAsTableRowFn extends DoFn<KV<String, Sensor>, TableRow> {
	    @Override
	    public void processElement(ProcessContext c) {
	      TableRow row = new TableRow()
	    		  .set("word", c.getClass())
	    		  .set("count", c.element().getValue())
	    		  // include a field for the window timestamp
	    		  .set("window_timestamp", c.timestamp().toString());
	      c.output(row);
	    }
	  }  
  
  private static TableSchema getSchema() {
	    List<TableFieldSchema> fields = new ArrayList<>();
	    fields.add(new TableFieldSchema().setName("deviceid").setType("STRING"));
	    fields.add(new TableFieldSchema().setName("channel").setType("STRING"));
	    fields.add(new TableFieldSchema().setName("timestamp").setType("STRING"));	    
	    fields.add(new TableFieldSchema().setName("temperature").setType("STRING"));
	    fields.add(new TableFieldSchema().setName("pressure").setType("STRING"));	    
	    //fields.add(new TableFieldSchema().setName("datetime").setType("DATETIME"));
	    fields.add(new TableFieldSchema().setName("datetime").setType("STRING"));
	    TableSchema schema = new TableSchema().setFields(fields);
	    return schema;
	  }
  
  private static TableReference getTableReference(Options options) {
    TableReference tableRef = new TableReference();
    tableRef.setProjectId(options.getProject());
    tableRef.setDatasetId(options.getBigQueryDataset());
    tableRef.setTableId(options.getBigQueryTable());
    return tableRef;
  }
  
   public  interface Options extends WordCount.WordCountOptions, DataflowExampleOptions, ExamplePubsubTopicOptions, ExampleBigQueryTableOptions {
    
	@Description("Fixed window duration, in minutes")
    @Default.Integer(WINDOW_SIZE)
    Integer getWindowSize();
    
	void setWindowSize(Integer value);

    @Description("Whether to run the pipeline with unbounded input")
    boolean isUnbounded();
    
    void setUnbounded(boolean value);
  }
     
  static class ExtractWordsFn extends DoFn<String, TableRow> {	    
	    @Override
	    public void processElement(ProcessContext c) {	    	
	    	Sensor sensor=parseJsonToObject2(c.element());	    		   
	    	
	    	TableRow row = new TableRow()
		    		  .set("deviceId", sensor.getDeviceId())
		    		  .set("channel", sensor.getChannel())
		    		  .set("timestamp", sensor.getTimestamp())
		    		  .set("temperature", sensor.getData().getTemperature())
		    		  .set("pressure", sensor.getData().getPressure())
		    		  .set("datetime", new DateTime(sensor.getTimestamp()).toString());	    		        
	    	c.output(row);
	    }  
  }
    
  static Sensor parseJsonToObject2(String entry)
  { 
	  Sensor sensor=null;
	  
	  try
	  {			  
		  ObjectMapper mapper=new ObjectMapper();		
		  sensor=mapper.readValue(entry, Sensor.class);
		  
		  return sensor;
	  }
	  catch(IOException e)
	  {
		  LOG.info("------>Ocurrio un error al intentar parsear el objeto json al tipo objeto sensor");		  
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
    Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
    options.setBigQuerySchema(getSchema());
       
    DataflowExampleUtils exampleDataflowUtils = new DataflowExampleUtils(options, options.isUnbounded());

    Pipeline pipeline = Pipeline.create(options);
    
    PCollection<String> input;
    if (options.isUnbounded()) {
      input = pipeline.apply(PubsubIO.Read.topic(options.getPubsubTopic()));
    } else {
      input = pipeline.apply(PubsubIO.Read.topic(options.getPubsubTopic()));
    }

    //el metodo ExtractWordsFn permite procesar los objetos json y convertior en un datarow
    input.apply(ParDo.of(new ExtractWordsFn()))
        .apply(BigQueryIO.Write
          .to(getTableReference(options))
          .withSchema(getSchema())
          .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
          .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));
    
    
    PipelineResult result = pipeline.run();

    ////////////exampleDataflowUtils.mockUnboundedSource(options.getInputFile(), result);
  }
}
