package com.huawei.iot.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.GetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.huawei.iot.model.BusLine;
import com.huawei.iot.model.Station;

public class DBOperator {

	private final static ProvisionedThroughput THRUPUT = new ProvisionedThroughput(2L, 1L);
	protected static DynamoDB dynamo;
	protected static AmazonDynamoDBClient client;

	private static void init() {
		client = new AmazonDynamoDBClient();
		// 使用Singapore服务器
		Region tokyo = Region.getRegion(Regions.AP_SOUTHEAST_1);
		client.setRegion(tokyo);
		dynamo = new DynamoDB(client);
	}

	public static void createTable(String tblName, String hashKey) throws InterruptedException {
		init();
		// Table doesn't exist. Let's create it.
		Table table = dynamo.createTable(newCreateTableRequest(tblName, hashKey));
		// Wait for the table to become active
		TableDescription desc = table.waitForActiveOrDelete();
		if (desc != null) {
			System.out.println("Skip creating table which already exists and ready for use: " + desc);
			return;
		}
		desc = table.waitForActive();
		System.out.println("Table is ready for use! " + desc);
	}

	private static CreateTableRequest newCreateTableRequest(String tableName, String hashKey) {
		// primary keys

		CreateTableRequest req = new CreateTableRequest().withTableName(tableName)
				.withAttributeDefinitions(new AttributeDefinition(hashKey, ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement(hashKey, KeyType.HASH)).withProvisionedThroughput(THRUPUT);
		return req;
	}

	public static void putItem(String tblName, List<BusLine> busLines) {
		init();
		Table table = dynamo.getTable(tblName);
		List<Item> items = null;
		if (DBConstants.TBL_BUSLINE.equals(tblName)) {
			items = newBusLineItems(busLines);
		} else if (DBConstants.TBL_STATION.equals(tblName)) {
			items = newStationItems(busLines);
		}
		for (Item item : items) {
			table.putItem(item);
		}
	}
	
	public static PutItemOutcome putMessage(String tblName, String content){
		init();
		Table table = dynamo.getTable(tblName);
		//1. get the list of items
		ScanRequest scanRequest = new ScanRequest(tblName);
		List<Map<String, AttributeValue>> msgList = client.scan(scanRequest).getItems();
		//2. get the biggest num
		int maxId = 0;
		for(Map<String, AttributeValue> map: msgList){
			String currentId = map.get(DBConstants.HASH_KEY_NAME).getN();
			if(maxId < Integer.parseInt(currentId)){
				maxId = Integer.parseInt(currentId);
			}
		}
		//3. input the item to DB
		Item item = new Item().withNumber(DBConstants.HASH_KEY_NAME, maxId+1)
				.withString(DBConstants.MESSAGE_CONTENT, content);
		return table.putItem(item);
	}
	
	public static List getMessages(String tblName, String id){
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition = new Condition()
            .withComparisonOperator(ComparisonOperator.GT.toString())
            .withAttributeValueList(new AttributeValue().withN(id));
        scanFilter.put(DBConstants.HASH_KEY_NAME, condition);
        ScanRequest scanRequest = new ScanRequest(tblName).withScanFilter(scanFilter);
		List<Map<String, AttributeValue>> scanList = client.scan(scanRequest).getItems();
		List resultList = new ArrayList();
		
		for(Map<String, AttributeValue> map: scanList){
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put(map.get(DBConstants.HASH_KEY_NAME).getN(), map.get(DBConstants.MESSAGE_CONTENT).getS());
			resultList.add(resultMap);
		}
		return resultList;
	}

	public static PutItemOutcome updateItem(String tblName, Map<String, String> map) {
		init();
		Table table = dynamo.getTable(tblName);
		Item item = null;
		if (DBConstants.TBL_DEVICE_INSTANCE.equals(tblName)) {
			item = newInstanceItems(map);
		}
		return table.putItem(item);

	}

	private static Item newInstanceItems(Map<String, String> map) {
		// TODO Auto-generated method stub
		Item item = new Item().withString(DBConstants.HASH_KEY_NAME, map.get(DBConstants.DEVICE_ID))
				.withString(DBConstants.POSITION_ID, map.get(DBConstants.POSITION_ID));

		return null;
	}

	private static List<Item> newStationItems(List<BusLine> busLines) {
		// TODO Auto-generated method stub
		List<Item> list = new ArrayList<Item>();
		int stationHashID = 0;
		for (BusLine bl : busLines) {
			for (Station station : bl.getStations()) {
				Item item = new Item().withString(DBConstants.HASH_KEY_NAME, stationHashID + "")
						.withString(DBConstants.STATION_ID, station.getId())
						.withString(DBConstants.STATION_NAME, station.getName())
						.withString(DBConstants.STATION_LON, station.getLongitude())
						.withString(DBConstants.STATION_LAT, station.getLatitude())
						.withString(DBConstants.STATION_ALT, station.getAltitude())
						.withString(DBConstants.STATION_SPE, station.getSpeed())
						.withString(DBConstants.STATION_REMAIN_TIME, station.getRemainTime())
						.withString(DBConstants.STATION_LINE_ID, bl.getLineNum());
				stationHashID++;
				list.add(item);
			}
		}

		return list;
	}

	private static List<Item> newBusLineItems(List<BusLine> busLines) {
		// TODO Auto-generated method stub
		List<Item> list = new ArrayList<Item>();
		for (BusLine bl : busLines) {
			Item item = new Item().withString(DBConstants.HASH_KEY_NAME, bl.getLineNum())
					.withString(DBConstants.LINE_START_TIME, bl.getStartTime())
					.withString(DBConstants.LINE_END_TIME, bl.getEndTime())
					.withString(DBConstants.LINE_PRICE, bl.getPrice())
					.withString(DBConstants.LINE_STATUS, DBConstants.STATUS_ENABLE);
			list.add(item);
		}

		return list;
	}

	public static GetItemOutcome getItem(String tblName, String hashKey) {
		init();
		Table table = dynamo.getTable(tblName);
		GetItemOutcome outcome = table
				.getItemOutcome(new GetItemSpec().withPrimaryKey(DBConstants.HASH_KEY_NAME, hashKey));

		return outcome;
	}

	public static ScanResult getAllItem(String tblName) {
		init();
		ScanRequest scanRequest = new ScanRequest(tblName);
		return client.scan(scanRequest);
	}

	public static void delItems(String tblName) {
		init();
		for(int i=1; i<5;i++){
			HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
	        Condition condition = new Condition()
	            .withComparisonOperator(ComparisonOperator.CONTAINS.toString())
	            .withAttributeValueList(new AttributeValue().withS(DIConstants.DATA_STREAM_ID+"_"+i));
	        scanFilter.put(DIConstants.DATA_STREAM_ID, condition);
	        ScanRequest scanRequest = new ScanRequest(tblName).withScanFilter(scanFilter);
	        
			List<Map<String, AttributeValue>> scanList = client.scan(scanRequest).getItems();
			for (Map<String, AttributeValue> attr : scanList) {
				String id = attr.get(DIConstants.DATA_STREAM_ID).getS();
				Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
				key.put("DataStreamID", new AttributeValue().withS(id));

				DeleteItemRequest deleteItemRequest = new DeleteItemRequest(tblName, key);
				DeleteItemResult deleteItemResult = client.deleteItem(deleteItemRequest);
			}
		}
		
	}
	/*
	 * private static Item newItem() { Item item = new
	 * Item().withString(HASH_KEY_NAME, "foo") .withBinary("binary", new byte[]
	 * { 1, 2, 3, 4 }) .withBinarySet("binarySet", new byte[] { 5, 6 }, new
	 * byte[] { 7, 8 }) .withBoolean("booleanTrue", true)
	 * .withBoolean("booleanFalse", false).withInt("intAttr",
	 * 1234).withList("listAtr", "abc", "123") .withMap("mapAttr", new
	 * ValueMap().withString("key1", "value1").withInt("key2", 999))
	 * .withNull("nullAttr").withNumber("numberAttr",
	 * 999.1234).withString("stringAttr", "bla") .withStringSet("stringSetAttr",
	 * "da", "di", "foo", "bar", "bazz"); return item; } public static String
	 * simpleQuery() { Table table = dynamo.getTable(TABLE_NAME);
	 * ItemCollection<?> col = table.query(HASH_KEY_NAME, "foo"); int count = 0;
	 * for (Item item: col) { System.out.println(item); count++; } return
	 * col.toString(); }
	 */
}
