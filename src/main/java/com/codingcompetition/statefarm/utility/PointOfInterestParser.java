package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            PointHandler handler = new PointHandler();
            saxParser.parse(new File("/Users/pankaj/employees.xml"), handler);
            return handler.getPoints();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Handler to parse data with SAX
    private class PointHandler extends DefaultHandler{
        private List<PointOfInterest> pointsOfInterest;
        private PointOfInterest currentPoint;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            pointsOfInterest =  new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentPoint = new PointOfInterest();
            if(qName.toLowerCase().equals("node")){
                for (int i = 0; i < attributes.getLength(); i++) {
                    currentPoint.addDescriptor(attributes.getQName(i),attributes.getValue(i));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.toLowerCase().equals("node")){
                pointsOfInterest.add(currentPoint);
            }
        }

        public List<PointOfInterest> getPoints(){
            return pointsOfInterest;
        }
    }
}
