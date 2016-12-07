package com.example.dav.mobilecwapp;

//This is responsible for parsing the data from the rss xml, getData method is called from the asynctask class

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSParser {


    private List<Weather> weatherArrayList;

    public List<Weather> getData(String _url) { // method used collected all title and description tags within rss xml
        try {

            weatherArrayList = new ArrayList<Weather>();
            URL url = new URL(_url);
            URLConnection con = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String inputLine;
            String fullStr = "";
            while ((inputLine = reader.readLine()) != null)
                fullStr = fullStr.concat(inputLine + "\n");

            InputStream istream = url.openStream();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder(); // parsing object


            Document doc = builder.parse(istream);

            doc.getDocumentElement().normalize();

            NodeList List = doc.getElementsByTagName("item"); //starting tag location

            for (int entry = 0; entry < List.getLength(); entry++) {

                Node tag = List.item(entry);
                if (tag.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) tag;

                    Weather weather = new Weather(); // new weather object which has access to getters and setters collect the necessary tags

                    weather.setTitle(getTagValue("title", eElement));
                    weather.setDescription(getTagValue("description", eElement));

                    weatherArrayList.add(weather);// at the end of each increment the tags are stored within the weatherArrayList
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherArrayList;
    }

    private String getTagValue(String sTag, Element eElement) { // method called the getData method
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();

        Node value = (Node) nlList.item(0);

        return value.getNodeValue();

    }

    private String removeBr(String str1){ // this was originally used to edit the data which had been parsed
        return str1.replaceAll("Minimum Temperature:","\n \n Minimum Temperature:");
    }
}
