package com.chaostek.polloniumfantasy;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author chaosburn
 */
public class bookList extends DefaultHandler {
    private File bookFile;
    private String tmpValue;
    private book bookTmp;
    
    private ObservableList<book> ObservableBooks;
    private ArrayList<book> Books;
    
    //private DefaultComboBoxModel<book> cboModel;
    
    
    public bookList()
    {
        Books = new ArrayList();
        //cboModel = new DefaultComboBoxModel(Books);
        
    }
    
    public ObservableList<book> getBooks()
    {
        //return cboModel;
        return ObservableBooks;
        
    }
    
    public book getBookByISBN(String searchISBN)
    {
        for (book iBook:Books)
        {
            if (iBook.getISBN().equalsIgnoreCase(searchISBN))
                return iBook;
            
        }
        return null;
        
    }
    
    public book getBookByTitle(String searchTitle)
    {
        for (book iBook:Books)
        {
            if (iBook.getName().equalsIgnoreCase(searchTitle))
                return iBook;
            
        }
        return null;
    }
    
    public void openXML(File file)
    {
        bookFile = file;
        parseDocument();
        ObservableBooks = FXCollections.observableArrayList(Books);
        
    }
    
    public void openXML()
    {
        bookFile = new File("lib/books.xml");
        parseDocument();
        ObservableBooks = FXCollections.observableArrayList(Books);

    }
    
    public ArrayList<book> retrieveBooks()
    {
        return Books;
        
    }
    
        private void parseDocument()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(bookFile, this);
            
        } catch (ParserConfigurationException e){
            System.out.println("ParserConfig error");
            
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error loading Book List");
            
        }
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException
    {
        if (elementName.equalsIgnoreCase("book"))
        {
            bookTmp = new book(attributes.getValue("isbn"), attributes.getValue("name"), attributes.getValue("game"));
            
        }
        
    }
    
    @Override
    public void endElement(String s, String s1, String element) throws SAXException
    {
        if (element.equalsIgnoreCase("book"))
        {
            Books.add(bookTmp);
            
        }

    }
    
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException
    {
        tmpValue = new String(ac, i, j);
        
    }
}
