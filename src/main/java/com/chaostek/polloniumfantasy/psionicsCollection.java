package com.chaostek.polloniumfantasy;

import java.io.IOException;
import javafx.scene.control.TreeItem;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author chaosburn
 */

public class psionicsCollection extends DefaultHandler
{
    String psionicsFile;
    String tmpValue;
    psionic categoryTmp;
    psionic psionicTmp;
    
    TreeItem<psionic> root;
    TreeItem<psionic> catNode;
    TreeItem<psionic> psionicNode;
    
    public psionicsCollection(String file)
    {
        psionicsFile = file;
        root = new TreeItem<>(new psionic());
        parseDocument();
        
    }
    
    public psionicsCollection()
    {
        psionicsFile = null;
        root = new TreeItem<>(new psionic());
        
    }
    
    public void openXML(String file)
    {
        psionicsFile = file;
        if (!root.getChildren().isEmpty()) { root = new TreeItem<>(new psionic()); }
        parseDocument();
        
    }

    public TreeItem<psionic> getRoot()
    {
        return root;
        
    }
    
    private void parseDocument()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(psionicsFile, this);
            
        } catch (ParserConfigurationException e){
            System.out.println("ParserConfig error");
            
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
            
        } catch (IOException e) {
            System.out.println("IO error loading Skills");
            
        }
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException
    {
        if (elementName.equalsIgnoreCase("category"))
        {
            categoryTmp = new psionic(attributes.getValue("id"), attributes.getValue("name"), true);
            catNode = new TreeItem(categoryTmp);
            
        }
        
        if (elementName.equalsIgnoreCase("psionic"))
        {
            psionicTmp = new psionic(attributes.getValue("id"), attributes.getValue("name"));
            
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException
    {
        if (element.equalsIgnoreCase("category"))
        {
            root.getChildren().add(catNode);
            
        }
        
        if (element.equalsIgnoreCase("range"))
        {
            psionicTmp.setRange(tmpValue);
        }
        if (element.equalsIgnoreCase("duration"))
        {
            psionicTmp.setDuration(tmpValue);
        }
        if (element.equalsIgnoreCase("savingthrow"))
        {
            psionicTmp.setSavingThrow(tmpValue);
        }
        if (element.equalsIgnoreCase("trance"))
        {
            psionicTmp.setLengthOfTrance(tmpValue);
        }
        if (element.equalsIgnoreCase("isp"))
        {
            psionicTmp.setIsp(Integer.parseInt(tmpValue));
        }
        if (element.equalsIgnoreCase("text"))
        {
            psionicTmp.setText(tmpValue);
        }
        if (element.equalsIgnoreCase("psionic"))
        {
            catNode.getChildren().add(new TreeItem<>(psionicTmp));
            
        }
    }
    
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException
    {
        tmpValue = new String(ac, i, j);
        
    }
    
    public void saveAs(String fileName)
    {
        psionicsFile = fileName;
        
    }
    
    @SuppressWarnings("UseSpecificCatch")
    public void saveToXML()
    {
        //if (skillsFile == null) return;
        
        //if (categories == null) return;
        
        //if (categories.getChildCount() == 0) return;
        
        Attr attrTmp;
        
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element rootElement = doc.createElement("psionics");
            doc.appendChild(rootElement);
            
            for (int i = 0; i < root.getChildren().size(); i++)
            {
                catNode = root.getChildren().get(i);
                categoryTmp = catNode.getValue();
                
                
                Element categoryElement = doc.createElement("category");
                attrTmp = doc.createAttribute("name");
                attrTmp.setValue(categoryTmp.getName());
                categoryElement.setAttributeNode(attrTmp);
                attrTmp = doc.createAttribute("id");
                attrTmp.setValue(categoryTmp.getIdString());
                categoryElement.setAttributeNode(attrTmp);
                rootElement.appendChild(categoryElement);
                
                if (!catNode.getChildren().isEmpty())
                {
                    for (int j = 0; j < catNode.getChildren().size(); j++)
                    {
                        psionicNode = catNode.getChildren().get(j);
                        psionicTmp = psionicNode.getValue();
                        
                        categoryElement.appendChild(buildPsionicNode(doc));
                        
                    }
                }
                
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(psionicsFile);
            System.out.println("For error checking: ");
            System.out.println(result.toString());
            System.out.println(psionicsFile);
            transformer.transform(source, result);
                
        } catch (Exception e)
        {
            
        }   
    }
    
    private Element buildPsionicNode(Document doc)
    {
        // Skill element creation
        Element spellElement = doc.createElement("psionic");
        spellElement.setAttribute("id", psionicTmp.getIdString());
        spellElement.setAttribute("name", psionicTmp.getName());

        Element xmlSpell = doc.createElement("range");
        xmlSpell.appendChild(doc.createTextNode(psionicTmp.getRange()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("duration");
        xmlSpell.appendChild(doc.createTextNode(psionicTmp.getDuration()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("savingthrow");
        xmlSpell.appendChild(doc.createTextNode(psionicTmp.getSavingThrow()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("trance");
        xmlSpell.appendChild(doc.createTextNode(psionicTmp.getLengthOfTrance()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("isp");
        xmlSpell.appendChild(doc.createTextNode(Integer.toString(psionicTmp.getIsp())));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("text");
        xmlSpell.appendChild(doc.createTextNode(psionicTmp.getText()));
        spellElement.appendChild(xmlSpell);
        
        return spellElement;
        
    }
}
