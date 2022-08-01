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
public class skillsCollection extends DefaultHandler {
    String skillsFile;
    String tmpValue;
    skill categoryTmp;
    skill skillTmp;
    
    TreeItem<skill> root;
    TreeItem<skill> catNode;
    TreeItem<skill> skillNode;
    
    public skillsCollection(String file)
    {
        skillsFile = file;
        root = new TreeItem<>(new skill());
        parseDocument();
        
    }
    
    public skillsCollection()
    {
        skillsFile = null;
        root = new TreeItem<>(new skill());
        
    }
    
    public void openXML(String file)
    {
        skillsFile = file;
        if (!root.getChildren().isEmpty()) { root = new TreeItem<>(new skill()); }
        parseDocument();
        
    }
    
    public TreeItem<skill> getRoot()
    {
        return root;
        
    }
    
    private void parseDocument()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(skillsFile, this);
            
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
            categoryTmp = new skill(attributes.getValue("id"), attributes.getValue("name"), true);
            // Default to core book
            categoryTmp.setBook("0916211916");
            catNode = new TreeItem(categoryTmp);
            
        }
        
        if (elementName.equalsIgnoreCase("skill"))
        {
            skillTmp = new skill(attributes.getValue("id"), attributes.getValue("name"));
            
        }
        
        if (elementName.equalsIgnoreCase("subskill"))
        {
            skillTmp.addSubSkill(attributes.getValue("name"), Integer.parseInt(attributes.getValue("base")), Integer.parseInt(attributes.getValue("perlevel")));
            
        }
    }
    
    @Override
    public void endElement(String s, String s1, String element) throws SAXException
    {
        if (element.equalsIgnoreCase("category"))
        {
            root.getChildren().add(catNode);
            
        }
        
        if (element.equalsIgnoreCase("base"))
        {
            skillTmp.setBasePercent(tmpValue);
        }
        if (element.equalsIgnoreCase("perlevel"))
        {
            skillTmp.setPerLevel(tmpValue);
        }
        if (element.equalsIgnoreCase("bonuses"))
        {
            skillTmp.setBonuses(tmpValue);
        }
        if (element.equalsIgnoreCase("book"))
        {
            skillTmp.setBook(tmpValue);
        }
        if (element.equalsIgnoreCase("text"))
        {
            skillTmp.setText(tmpValue);
        }
        if (element.equalsIgnoreCase("skill"))
        {
            catNode.getChildren().add(new TreeItem<>(skillTmp));
            
        }
    }
    
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException
    {
        tmpValue = new String(ac, i, j);
        
    }
    
    public void saveAs(String fileName)
    {
        skillsFile = fileName;
        
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
            
            Element rootElement = doc.createElement("skills");
            doc.appendChild(rootElement);
            
            for (int i = 0; i < root.getChildren().size(); i++)
            {
                catNode = root.getChildren().get(i);
                categoryTmp = catNode.getValue();
                
                
                Element categoryElement = doc.createElement("category");
                attrTmp = doc.createAttribute("name");
                attrTmp.setValue(categoryTmp.name);
                categoryElement.setAttributeNode(attrTmp);
                attrTmp = doc.createAttribute("id");
                attrTmp.setValue(categoryTmp.getIDString());
                categoryElement.setAttributeNode(attrTmp);
                rootElement.appendChild(categoryElement);
                
                if (catNode.getChildren().size() > 0)
                {
                    for (int j = 0; j < catNode.getChildren().size(); j++)
                    {
                        skillNode = catNode.getChildren().get(j);
                        skillTmp = skillNode.getValue();
                        
                        categoryElement.appendChild(buildSkillNode(doc, skillTmp));
                        
                    }
                }
                
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(skillsFile);
            System.out.println("For error checking: ");
            System.out.println(result.toString());
            System.out.println(skillsFile);
            transformer.transform(source, result);
                
        } catch (Exception e)
        {
            
        }   
    }
    
    private Element buildSkillNode(Document doc, skill tmpSkill)
    {
        // Skill element creation
        Element skillElement = doc.createElement("skill");
        skillElement.setAttribute("id", skillTmp.getIDString());
        skillElement.setAttribute("name", skillTmp.name);

        Element xmlSkill = doc.createElement("base");
        xmlSkill.appendChild(doc.createTextNode(Integer.toString(skillTmp.basePercent)));
        skillElement.appendChild(xmlSkill);
        xmlSkill = doc.createElement("perlevel");
        xmlSkill.appendChild(doc.createTextNode(Integer.toString(skillTmp.perLevel)));
        skillElement.appendChild(xmlSkill);
        xmlSkill = doc.createElement("bonuses");
        xmlSkill.appendChild(doc.createTextNode(skillTmp.getBonuses()));
        skillElement.appendChild(xmlSkill);
        xmlSkill = doc.createElement("book");
        xmlSkill.appendChild(doc.createTextNode(skillTmp.book));
        skillElement.appendChild(xmlSkill);
        xmlSkill = doc.createElement("text");
        xmlSkill.appendChild(doc.createTextNode(skillTmp.text));
        skillElement.appendChild(xmlSkill);
        System.out.println("Quantity of sub skills: " + String.valueOf(skillTmp.getSubSkillQuantity()));
        if (!skillTmp.isSubSkillEmpty())
        {
            for (int j = 0; j < skillTmp.getSubSkillQuantity(); j++)
            {
                Element subElement = doc.createElement("subskill");
                subSkill sub = skillTmp.getSubSkill(j);
                
                System.out.println(sub.getName());
                subElement.setAttribute("name", sub.getName());
                subElement.setAttribute("base", String.valueOf(sub.getBase()));
                subElement.setAttribute("perlevel", String.valueOf(sub.getPerLevel()));
                skillElement.appendChild(subElement);

            }
        }
        
        return skillElement;
        
    }
}
