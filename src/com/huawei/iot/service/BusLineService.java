package com.huawei.iot.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.huawei.iot.db.DBConstants;
import com.huawei.iot.db.DBOperator;
import com.huawei.iot.db.DIConstants;
import com.huawei.iot.model.BusLine;
import com.huawei.iot.util.BusLineXmlImpl;
import com.huawei.iot.util.TransferDI2Result;

public class BusLineService {
	private static final String bucketName = "devicetype-bucket";
	private static final String key = "buslines_xian.xml";

	public static void initBusLines() {
		List<BusLine> busLines = getBusLinesFromS3();
		DBOperator.putItem(DBConstants.TBL_BUSLINE, busLines);
		DBOperator.putItem(DBConstants.TBL_STATION, busLines);
	}

	private static List<BusLine> getBusLinesFromS3() {

		return BusLineXmlImpl.parserXml(getObjectFromS3());
	}

	private static InputStream getObjectFromS3() {

		AmazonS3 s3 = new AmazonS3Client();
		Region usWest2 = Region.getRegion(Regions.AP_NORTHEAST_1);
		s3.setRegion(usWest2);
		S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
		return object.getObjectContent();
	}
	
	private static BusLine getCurrentLine(String lineNum){
		List<BusLine> busLines = getBusLinesFromS3();
		for(BusLine busLine : busLines){
			if(lineNum.equals(busLine.getLineNum())){
				return busLine;
			}
		}
		return null;
	}

	public static List getAllLines() {
		// read busline from s3
		List<BusLine> busLines = getBusLinesFromS3();
		List resultList = new ArrayList();
		// read instance from db
		List<Map<String, Object>> buses = getAllBuses();

		for (BusLine busLine : busLines) {
			Map<String, Object> lineMap = new HashMap<String,Object>();
			lineMap.put(busLine.getLineNum(), busLine);
			Map<String, Object> busesMap = new HashMap<String, Object>();
			for (Map<String, Object> bus : buses) {
				if (busLine.getLineNum().equals(bus.get(DBConstants.BUSLINE_NUM))) {
					busesMap.put((String) bus.get(DIConstants.DEVICE_ID), bus);
				}
			}
			lineMap.put("buses", busesMap);
			resultList.add(lineMap);
		}
		return resultList;
	}
	
	public static List getTheLine(String lineNum, String positionId){
		List resultList = new ArrayList();
		//1.get the current bus line
		BusLine currentLine = getCurrentLine(lineNum);
		if(null != currentLine){
			resultList.add(currentLine);
			//2.get all buses info of current line
			List<Map<String, Object>> buses = getAllBusesOfLine(lineNum);
			//3.construct the datas of buses to display
			List<Map<String, String>> latestBusList = new ArrayList<Map<String, String>>();
			for(Map<String, Object> busMap : buses){
				Map<String, String> latestBus = new HashMap<String, String>();
				latestBus.put(DBConstants.DEVICE_NAME, (String) busMap.get(DIConstants.DEVICE_NAME));
				String currentPositionId = (String) busMap.get(DIConstants.POSITION_ID);
				latestBus.put(DBConstants.DEVICE_POSITION_ID, currentPositionId);
				String position = getCurrentPosition(currentPositionId);
				String remainStops = (20 - Integer.parseInt(position)) + "";
				latestBus.put(DBConstants.DEVICE_REMAIN_STOPS, remainStops);
				latestBus.put(DBConstants.DEVICE_REMAIN_TIMES, (String) busMap.get(DIConstants.REMAIN_TIME));
				latestBusList.add(latestBus);
			}
			List sortedBusList = getSortedBusList(latestBusList);
			resultList.add(sortedBusList);
		}
		
		return resultList;
	}
	
	public static List getFavorLines(){
		List resultList = new ArrayList();
		//1. get favor lines from s3
		List<BusLine> busLines = getBusLinesFromS3();
		//2. read instance from db
		List<Map<String, Object>> buses = getAllBuses();
		for (BusLine busLine : busLines) {
			Map<String, Object> lineMap = new HashMap<String,Object>();
			lineMap.put("lineInfor", busLine);
			//3. construct the datas of buses to display
			List<Map<String, String>> busList = new ArrayList<Map<String, String>>();
			for (Map<String, Object> busMap : buses) {
				if (busLine.getLineNum().equals(busMap.get(DBConstants.BUSLINE_NUM))) {
					Map<String, String> latestBus = new HashMap<String, String>();
					latestBus.put(DBConstants.DEVICE_NAME, (String) busMap.get(DIConstants.DEVICE_NAME));
					String currentPositionId = (String) busMap.get(DIConstants.POSITION_ID);
					latestBus.put(DBConstants.DEVICE_POSITION_ID, currentPositionId);
					String position = getCurrentPosition(currentPositionId);
					String remainStops = (20 - Integer.parseInt(position)) + "";
					latestBus.put(DBConstants.DEVICE_REMAIN_STOPS, remainStops);
					latestBus.put(DBConstants.DEVICE_REMAIN_TIMES, (String) busMap.get(DIConstants.REMAIN_TIME));
					
					busList.add(latestBus);
				}
			}
			//sort the buses and get top 3
			List sortedBusList = getSortedBusList(busList);
			lineMap.put("buses", sortedBusList);
			resultList.add(lineMap);
		}
		return resultList;
	}
	
	private static List getSortedBusList(List<Map<String, String>> busList) {
		// TODO Auto-generated method stub
		List resultList = new ArrayList();
		Map<String, Object> timeMap = new TreeMap<String, Object>();
		for(Map<String, String> busMap : busList){
			timeMap.put(busMap.get(DBConstants.DEVICE_REMAIN_TIMES), busMap);
		}
		int index = 0;
		for(Object newBus : timeMap.values() ){
			if(index < 3){
				resultList.add(newBus);
				index ++;
			}
		}
		return resultList;
	}

	private static String getCurrentPosition(String currentPositionId) {
		// TODO Auto-generated method stub
		String position = "";
		//1. currentPositionId contained "_"
		if(currentPositionId.contains("_")){
			position = currentPositionId.split("_")[0];
		}else{
			//2. int
			position = currentPositionId;
		}
		return position;
	}

	private static List<Map<String, Object>> getAllBuses(){
		ScanResult allInstance = DBOperator.getAllItem(DBConstants.TBL_DEVICE_INSTANCE);
		List<Map<String, AttributeValue>> listItems = allInstance.getItems();
		List<Map<String, Object>> buses = new ArrayList<Map<String, Object>>();
		for (Map<String, AttributeValue> m : listItems) {
			buses.add(TransferDI2Result.converFromDeviceInstance(m));
		}
		return buses;
	}
	
	private static List<Map<String, Object>> getAllBusesOfLine(String currentLineNum){
		ScanResult allInstance = DBOperator.getAllItem(DBConstants.TBL_DEVICE_INSTANCE);
		List<Map<String, AttributeValue>> listItems = allInstance.getItems();
		List<Map<String, Object>> buses = new ArrayList<Map<String, Object>>();
		for (Map<String, AttributeValue> m : listItems) {
			if(null != m.get(DIConstants.BUSLINE_NUM)
					&& currentLineNum.equals(m.get(DIConstants.BUSLINE_NUM).getS())){
				buses.add(TransferDI2Result.converFromDeviceInstance(m));
			}
		}
		return buses;
	}
}
