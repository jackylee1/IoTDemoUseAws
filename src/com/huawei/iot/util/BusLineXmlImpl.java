package com.huawei.iot.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.huawei.iot.model.BusLine;
import com.huawei.iot.model.Station;

public class BusLineXmlImpl {

	private static Document document;

	private static void init() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
	}

	public static List<BusLine> parserXml(InputStream is) {
        try {
        	init();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);
            
            //return datas
            List<BusLine> bls = new ArrayList<BusLine>();
            
            NodeList nl = document.getElementsByTagName(XmlConstants.TAG_BUSLINE);
            for(int i=0;i<nl.getLength();i++){
            	//1.lines
            	BusLine bl = new BusLine();
            	String lineNum = document.getElementsByTagName(XmlConstants.TAG_BUSLINE_NUM).item(i).getFirstChild().getNodeValue().trim();
            	String startTime = document.getElementsByTagName(XmlConstants.TAG_BUSLINE_STARTTIME).item(i).getFirstChild().getNodeValue().trim();
            	String endTime = document.getElementsByTagName(XmlConstants.TAG_BUSLINE_ENDTIME).item(i).getFirstChild().getNodeValue().trim();
            	String price = document.getElementsByTagName(XmlConstants.TAG_BUSLINE_PRICE).item(i).getFirstChild().getNodeValue().trim();
            	String isFav = document.getElementsByTagName(XmlConstants.TAG_BUSLINE_ISFAVOR).item(i).getFirstChild().getNodeValue().trim();
            	String favStation = document.getElementsByTagName(XmlConstants.TAG_BUSLINE_FAVORSTATION).item(i).getFirstChild().getNodeValue().trim();
            	bl.setLineNum(lineNum);
            	bl.setStartTime(startTime);
            	bl.setEndTime(endTime);
            	bl.setPrice(price);
            	bl.setIsFavor(isFav);
            	bl.setFavorStationId(favStation);
            	//2.stations
            	Node node = nl.item(i);
				Element element = (Element)node;
				NodeList stationList = element.getElementsByTagName(XmlConstants.TAG_STATION);
            	List<Station> stations = new ArrayList<Station>();
            	for(int j=0;j<stationList.getLength();j++){
            		Station station = new Station();
            		String id = document.getElementsByTagName(XmlConstants.TAG_STATION_ID).item(j).getFirstChild().getNodeValue().trim();
            		String lon = document.getElementsByTagName(XmlConstants.TAG_STATION_LONGITUDE).item(j).getFirstChild().getNodeValue().trim();
            		String lat = document.getElementsByTagName(XmlConstants.TAG_STATION_LATITUDE).item(j).getFirstChild().getNodeValue().trim();
            		String alt = document.getElementsByTagName(XmlConstants.TAG_STATION_ALTITUDE).item(j).getFirstChild().getNodeValue().trim();
            		String spe = document.getElementsByTagName(XmlConstants.TAG_STATION_SPEED).item(j).getFirstChild().getNodeValue().trim();
            		String name = document.getElementsByTagName(XmlConstants.TAG_STATION_NAME).item(j).getFirstChild().getNodeValue().trim();
            		String time = document.getElementsByTagName(XmlConstants.TAG_STATION_REMAINTIME).item(j).getFirstChild().getNodeValue().trim();
            		station.setId(id);
            		station.setName(name);
            		station.setLongitude(lon);
            		station.setLatitude(lat);
            		station.setAltitude(alt);
            		station.setSpeed(spe);
            		station.setRemainTime(time);
            		stations.add(station);
            	}
            	bl.setStations(stations);
            	bls.add(bl);
            }
			
            System.out.println("bus line :"+ bls);
            return bls;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
