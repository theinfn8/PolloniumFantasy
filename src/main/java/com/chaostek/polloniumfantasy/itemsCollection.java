package com.chaostek.polloniumfantasy;

import java.io.IOException;
import java.nio.file.*;
import javafx.collections.ObservableList;

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
import javax.xml.transform.OutputKeys;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author chaosburn
 */
public class itemsCollection extends DefaultHandler
{
    String itemFile;
    String tmpValue;
    item categoryTmp;
    item itemTmp;
    
    TreeItem<item> root;
    TreeItem<item> catNode;
    TreeItem<item> itemNode;
    
    public itemsCollection(String file)
    {
        itemFile = file;
        root = new TreeItem<>(new item());
        parseDocument();
        
    }
    
    public itemsCollection()
    {
        itemFile = null;
        root = new TreeItem<>(new item());
        
    }
    
    public TreeItem<item> findCat(String searchCat)
    {
        ObservableList<TreeItem<item>> categories;
        //TreeItem<item> searchItem;
        
        categories = root.getChildren();
        for(TreeItem<item> cat : categories)
        {
            if (cat.getValue().getName().equals(searchCat)) { return cat; }
        }
        // If we get here, we didn't find the category
        // So add the category and return it
        item newCat = new item();
        newCat.setName(searchCat);
        newCat.generateUUID();
        newCat.setCategory(searchCat);
        newCat.setIsCat(true);
        TreeItem<item> treeCat = new TreeItem(newCat);
        root.getChildren().add(treeCat);
        
        return treeCat;
        
    }
    
    public void openXML(String file)
    {
        itemFile = file;
        //new File checkFile = File(itemFile);
        Path tempPath = Paths.get(file);
        
        if (!root.getChildren().isEmpty()) { root = new TreeItem<>(new item()); }
        if (Files.notExists(tempPath))
        {
            return;
            
        }
        
        parseDocument();
        
    }

    public TreeItem<item> getRoot()
    {
        return root;
        
    }
    
    private void parseDocument()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(itemFile, this);
            
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
        /*if (elementName.equalsIgnoreCase("category"))
        {
            categoryTmp = new item(attributes.getValue("id"), attributes.getValue("name"), true);
            catNode = new TreeItem(categoryTmp);
            root.getChildren().add(catNode);
            
        }*/
        
        if (elementName.equalsIgnoreCase("item"))
        {
            itemTmp = new item(attributes.getValue("id"), attributes.getValue("name"));
            
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException
    {
        /*if (element.equalsIgnoreCase("category"))
        {
            root.getChildren().add(catNode);
            
        }*/
        
        if (element.equalsIgnoreCase("price"))
        {
            itemTmp.setPrice(tmpValue);
        }
        if (element.equalsIgnoreCase("description"))
        {
            itemTmp.setDescription(tmpValue);
        }
        if (element.equalsIgnoreCase("category"))
        {
            itemTmp.setCategory(tmpValue);
        }
        if (element.equalsIgnoreCase("weight"))
        {
            itemTmp.setCategory(tmpValue);
        }
        if (element.equalsIgnoreCase("item"))
        {
            //itemTmp.setIsLevel(true);
            TreeItem<item> addCat = findCat(catNode.getValue().getName());
            //catNode.getChildren().add(addLevel);
            addCat.getChildren().add(new TreeItem<>(itemTmp));
            
        }
    }
    
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException
    {
        tmpValue = new String(ac, i, j);
        
    }
    
    public void saveAs(String fileName)
    {
        itemFile = fileName;
        
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
            
            Element rootElement = doc.createElement("items");
            doc.appendChild(rootElement);
            
            for (TreeItem<item> categoryNode : root.getChildren())
            {
                /*categoryTmp = categoryNode.getValue();
                
                Element categoryElement = doc.createElement("category");
                attrTmp = doc.createAttribute("name");
                attrTmp.setValue(categoryTmp.getName());
                categoryElement.setAttributeNode(attrTmp);
                attrTmp = doc.createAttribute("id");
                attrTmp.setValue(categoryTmp.getID());
                categoryElement.setAttributeNode(attrTmp);
                rootElement.appendChild(categoryElement);
                */
                
                if (!categoryNode.getChildren().isEmpty())
                {
                    for (TreeItem<item> itemNode : categoryNode.getChildren())
                    {
                        rootElement.appendChild(buildItemNode(doc, itemNode.getValue()));

                    }
                }
                
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(itemFile);
            System.out.println("For error checking: ");
            System.out.println(result.toString());
            System.out.println(itemFile);
            transformer.transform(source, result);
                
        } catch (Exception e)
        {
            
        }   
    }
    
    private Element buildItemNode(Document doc, item addItem)
    {
        // Skill element creation
        Element itemElement = doc.createElement("item");
        itemElement.setAttribute("id", addItem.getID());
        itemElement.setAttribute("name", addItem.getName());

        Element xmlItem = doc.createElement("category");
        xmlItem.appendChild(doc.createTextNode(addItem.getCategory()));
        itemElement.appendChild(xmlItem);
        xmlItem = doc.createElement("price");
        xmlItem.appendChild(doc.createTextNode(addItem.getPrice()));
        itemElement.appendChild(xmlItem);
        xmlItem = doc.createElement("weight");
        xmlItem.appendChild(doc.createTextNode(addItem.getWeight()));
        itemElement.appendChild(xmlItem);
        xmlItem = doc.createElement("description");
        xmlItem.appendChild(doc.createTextNode(addItem.getDescription()));
        itemElement.appendChild(xmlItem);
        
        return itemElement;
        
    }

}
