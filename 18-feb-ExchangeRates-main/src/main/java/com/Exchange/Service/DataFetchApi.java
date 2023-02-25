package com.Exchange.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.Exchange.Entity.Apidata;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


public class DataFetchApi implements Callable<Apidata>{
	String st;

	public DataFetchApi(String st) {
		this.st=st;
	}

	 public Apidata FetchApi() throws IOException {


		 OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(50000, TimeUnit.MILLISECONDS).build();
		 Request request = new Request.Builder()
				 .url(st)
				 .addHeader("apikey", "sYkqMMrsuWe6SG4C3OxRNywSHvLQhy0p").get().build();
		 ResponseBody responseBody = client.newCall(request).execute().body();
		 String response = responseBody.string();
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		 objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		 Apidata ratesObj = objectMapper.readValue(response, Apidata.class);
		 ratesObj.setTimestamp(new Timestamp(new Date().getTime()));
		 return ratesObj;
	 }
		 @Override
	public Apidata call() {
		try {
			return FetchApi();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

}
