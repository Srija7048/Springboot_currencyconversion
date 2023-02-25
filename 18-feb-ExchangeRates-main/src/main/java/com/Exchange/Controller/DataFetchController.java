package com.Exchange.Controller;

import com.Exchange.Entity.Conversiontable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exchange.Service.DataFetchService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class DataFetchController {
	@Autowired
	private DataFetchService fetchService;
	
	@GetMapping("/fetchrate")
	public void FetchApiData(@RequestParam Optional<String> date) {
		if(date.isPresent()){
			fetchService.getApiData(date.get());
		}else{
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			fetchService.getApiData(simpleDateFormat.format(new Date()));
		}

	}
	@GetMapping("/getalldata")
	public ResponseEntity<List<Conversiontable>> getAllData(){
		List<Conversiontable> conversiontable=this.fetchService.getAllData();
		return new ResponseEntity<>(conversiontable, HttpStatus.OK);
	}
}