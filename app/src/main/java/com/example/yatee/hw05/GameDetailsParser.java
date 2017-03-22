package com.example.yatee.hw05;
/*
InClass 06
GameDetailsActivity
Yateen Kedare
 */
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by yatee on 2/19/2017.
 */
public class GameDetailsParser {
    static public class GameDetailsSAXParser extends DefaultHandler {
        private static GameDetails gameDetails;
        StringBuilder sb;
        boolean similar = false,images = false;
        static public GameDetails parseGames(InputStream in) throws IOException, SAXException {
            GameDetailsSAXParser  parser=new GameDetailsSAXParser ();
            Xml.parse(in,Xml.Encoding.UTF_8,parser);
            return parser.getGame();
        }

        @Override
        public void startDocument() throws SAXException {
            gameDetails=new GameDetails();
            sb=new StringBuilder();
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("Similar")){
                similar = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            switch (localName) {
                case "baseImgUrl":
                    gameDetails.setBaseURL(sb.toString().trim());
                    break;
                case "GameTitle":
                    gameDetails.setTitle(sb.toString().trim());
                    break;
                case "Platform":
                    gameDetails.setPlatform(sb.toString().trim());
                    break;
                case "ReleaseDate":
                    gameDetails.setReleaseDate(sb.toString().trim());
                    break;
                case "Overview":
                    gameDetails.setOverview(sb.toString().trim());
                    break;
                case "genre":
                    gameDetails.setGenre(sb.toString().trim());
                    break;
                case "original":
                    if(gameDetails.getImageURL()!=null)
                        gameDetails.setImageURL(sb.toString().trim());
                    break;
                case "boxart":
                    if(gameDetails.getImageURL()!=null)
                        gameDetails.setImageURL(sb.toString().trim());
                    break;
                case "Publisher":
                    gameDetails.setPublisher(sb.toString().trim());
                    break;
                case  "Youtube":
                    gameDetails.setVideoURL(sb.toString().trim());
                    break;

                case "id":
                    if(similar)
                        gameDetails.setSimilarGames(sb.toString().trim());
                    break;
            }
            sb.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            sb.append(ch, start, length);
        }

        static public GameDetails getGame() {
            return gameDetails;
        }
    }
}
