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
public class spellsCollection extends DefaultHandler
{
    String spellFile;
    String tmpValue;
    spell categoryTmp;
    spell spellTmp;
    
    TreeItem<spell> root;
    TreeItem<spell> catNode;
    TreeItem<spell> spellNode;
    
    public spellsCollection(String file)
    {
        spellFile = file;
        root = new TreeItem<>(new spell());
        parseDocument();
        
    }
    
    public spellsCollection()
    {
        spellFile = null;
        root = new TreeItem<>(new spell());
        
    }
    
    public TreeItem<spell> findLevelByCat(String searchCat, String searchLevel)
    {
        ObservableList<TreeItem<spell>> categories;
        TreeItem<spell> result;
        //TreeItem<spell> searchSpell;
        
        categories = root.getChildren();
        for(TreeItem<spell> cat : categories)
        {
            if (cat.getValue().getName().equals(searchCat))
            {
                ObservableList<TreeItem<spell>> levels;
                levels = cat.getChildren();
                for (TreeItem<spell> ilevel : levels)
                {
                    if (ilevel.getValue().getName().equals(searchLevel))
                    {
                        // Found our cat and level, return it
                        return ilevel;
                    }
                }
                // If we get here, we didn't find the level
                // so we add it, and return that node!
                spell newLevel = new spell();
                newLevel.setName(searchLevel);
                newLevel.generateUUID();
                //newLevel.setCat(true);
                newLevel.setIsLevel(true);
                result = new TreeItem(newLevel);
                cat.getChildren().add(result);
                return result;
            }
        }
        // If we get here, we didn't find the category (which shouldn't happen)
        // So add the category, then the level, and return the level
        spell newCat = new spell();
        newCat.setName(searchCat);
        newCat.generateUUID();
        newCat.setCat(true);
        TreeItem<spell> treeCat = new TreeItem(newCat);
        root.getChildren().add(treeCat);
        
        spell newLevel = new spell();
        newLevel.setName(searchLevel);
        newLevel.generateUUID();
        //newLevel.setCat(true);
        newLevel.setIsLevel(true);
        result = new TreeItem(newLevel);
        treeCat.getChildren().add(result);
        return result;
        
    }
    
    public void openXML(String file)
    {
        spellFile = file;
        //new File checkFile = File(spellFile);
        Path tempPath = Paths.get(file);
        
        if (!root.getChildren().isEmpty()) { root = new TreeItem<>(new spell()); }
        if (Files.notExists(tempPath))
        {
            return;
            
        }
        
        parseDocument();
        
    }

    public TreeItem<spell> getRoot()
    {
        return root;
        
    }
    
    private void parseDocument()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(spellFile, this);
            
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
            categoryTmp = new spell(attributes.getValue("id"), attributes.getValue("name"), true);
            catNode = new TreeItem(categoryTmp);
            root.getChildren().add(catNode);
            
        }
        
        if (elementName.equalsIgnoreCase("spell"))
        {
            spellTmp = new spell(attributes.getValue("id"), attributes.getValue("name"));
            
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException
    {
        /*if (element.equalsIgnoreCase("category"))
        {
            root.getChildren().add(catNode);
            
        }*/
        
        if (element.equalsIgnoreCase("range"))
        {
            spellTmp.setRange(tmpValue);
        }
        if (element.equalsIgnoreCase("duration"))
        {
            spellTmp.setDuration(tmpValue);
        }
        if (element.equalsIgnoreCase("savingthrow"))
        {
            spellTmp.setSavingThrow(tmpValue);
        }
        if (element.equalsIgnoreCase("ppe"))
        {
            spellTmp.setPpe(tmpValue);
        }
        if (element.equalsIgnoreCase("level"))
        {
            spellTmp.setLevel(tmpValue);
        }
        if (element.equalsIgnoreCase("listName"))
        {
            spellTmp.setListName(tmpValue);
        }
        if (element.equalsIgnoreCase("text"))
        {
            spellTmp.setText(tmpValue);
        }
        if (element.equalsIgnoreCase("spell"))
        {
            //spellTmp.setIsLevel(true);
            TreeItem<spell> addLevel = findLevelByCat(catNode.getValue().getName(), spellTmp.getLevel());
            //catNode.getChildren().add(addLevel);
            addLevel.getChildren().add(new TreeItem<>(spellTmp));
            
        }
    }
    
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException
    {
        tmpValue = new String(ac, i, j);
        
    }
    
    public void saveAs(String fileName)
    {
        spellFile = fileName;
        
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
            
            Element rootElement = doc.createElement("spells");
            doc.appendChild(rootElement);
            
            for (TreeItem<spell> categoryNode : root.getChildren())
            {
                categoryTmp = categoryNode.getValue();
                
                Element categoryElement = doc.createElement("category");
                attrTmp = doc.createAttribute("name");
                attrTmp.setValue(categoryTmp.getName());
                categoryElement.setAttributeNode(attrTmp);
                attrTmp = doc.createAttribute("id");
                attrTmp.setValue(categoryTmp.getID());
                categoryElement.setAttributeNode(attrTmp);
                rootElement.appendChild(categoryElement);
                
                if (!categoryNode.getChildren().isEmpty())
                {
                    for (TreeItem<spell> newLevel : categoryNode.getChildren())
                    {
                        for (TreeItem<spell> spellNode : newLevel.getChildren())
                        {
                            categoryElement.appendChild(buildSpellNode(doc, spellNode.getValue()));

                        }
                    }
                }
                
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(spellFile);
            System.out.println("For error checking: ");
            System.out.println(result.toString());
            System.out.println(spellFile);
            transformer.transform(source, result);
                
        } catch (Exception e)
        {
            
        }   
    }
    
    private Element buildSpellNode(Document doc, spell addSpell)
    {
        // Skill element creation
        Element spellElement = doc.createElement("spell");
        spellElement.setAttribute("id", addSpell.getID());
        spellElement.setAttribute("name", addSpell.getName());

        Element xmlSpell = doc.createElement("range");
        xmlSpell.appendChild(doc.createTextNode(addSpell.getRange()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("duration");
        xmlSpell.appendChild(doc.createTextNode(addSpell.getDuration()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("savingthrow");
        xmlSpell.appendChild(doc.createTextNode(addSpell.getSavingThrow()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("level");
        xmlSpell.appendChild(doc.createTextNode(addSpell.getLevel()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("ppe");
        xmlSpell.appendChild(doc.createTextNode(addSpell.getPpe()));
        spellElement.appendChild(xmlSpell);
        xmlSpell = doc.createElement("text");
        xmlSpell.appendChild(doc.createTextNode(addSpell.getText()));
        spellElement.appendChild(xmlSpell);
        
        return spellElement;
        
    }

}
