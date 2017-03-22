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


public class GameVersionParser {
    static public class GameVersionsSAXParser extends DefaultHandler {
        static ArrayList<Game> gamesList;
        Game game;
        StringBuilder sb;

        static public ArrayList<Game> parseGamesList(InputStream in) throws IOException, SAXException {
            GameVersionsSAXParser parser=new GameVersionsSAXParser();
            Xml.parse(in,Xml.Encoding.UTF_8,parser);
            return parser.getGames();
        }

        @Override
        public void startDocument() throws SAXException {
            gamesList=new ArrayList<Game>();
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
            if(localName.equals("Game")){
                game=new Game();
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            switch (localName) {
                case "Game":
                    gamesList.add(game);
                    break;
                case "id":
                    game.setId(sb.toString().trim());
                    break;
                case "GameTitle":
                    game.setTitle(sb.toString().trim());
                    break;
                case "ReleaseDate":
                    game.setReleaseDate(sb.toString().trim());
                    break;
                case "Platform":
                    game.setPlatform(sb.toString().trim());
                    break;
            }

            sb.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            sb.append(ch, start, length);
        }

        static public ArrayList<Game> getGames() {
            return gamesList;
        }
    }
}
