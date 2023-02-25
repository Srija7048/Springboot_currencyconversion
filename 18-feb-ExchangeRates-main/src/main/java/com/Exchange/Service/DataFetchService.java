package com.Exchange.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.Exchange.Entity.Conversiontable;
import com.Exchange.Entity.Apidata;
import com.Exchange.Excel.ExcelCreation;
import com.Exchange.Repository.Datarepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFetchService {
	@Autowired
	private Datarepository repo;
	String currencies[]= {"AED","CAD","EUR","INR","JPY"};
	public void getApiData(String date) {
		FutureTask<Apidata>[] futuretasks = new FutureTask[6];
		Conversiontable[] table=new Conversiontable[6];
		try {
			
			for(int i=0;i< currencies.length;i++) {
				String st="https://api.apilayer.com/exchangerates_data/"+date+"?symbols=USD&base="+currencies[i];

				Callable<Apidata> clble = new DataFetchApi(st);
				futuretasks[i] = new FutureTask<Apidata>(clble);

				Conversiontable info=new Conversiontable();
				info.setRequest(st);
				info.setCreate_ts(new Timestamp(new Date().getTime()));
				info.setStatus("SENT_THE_REQ");
				table[i]=repo.save(info);

			    Thread th = new Thread(futuretasks[i]);
			    th.start();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int j = 0; j < currencies.length; j++)
	    {

			try {
				Conversiontable info=table[j];
				info.setReponse(futuretasks[j].get().toString());
				info.setUpdate_ts(futuretasks[j].get().getTimestamp());
				info.setStatus("RECEIVED");
				new ExcelCreation(futuretasks[j].get());
				repo.save(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }  
	}
	public List<Conversiontable> getAllData()
	{
		return this.repo.findAll();
	}
	
}
