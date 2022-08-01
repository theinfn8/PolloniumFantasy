package com.chaostek.polloniumfantasy;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * @author chaosburn
 */

public class skill {
    public UUID id;
    public String name;
    public int basePercent;
    public int perLevel;
    public int currentLevel;
    public ObservableList<subSkill> subSkills;
    public String bonuses;
    public String book;
    public String text;
    public List<UUID> preReqs;
    private List<bonus> skillBonuses;
    private boolean BonusesSet;
    private boolean isCat;
    
    public skill()
    {
        preReqs = new ArrayList<>();
        skillBonuses = new ArrayList();
        subSkills = FXCollections.observableArrayList();
        isCat = false;
        currentLevel = 1;
        
    }

    public skill(String newID)
    {
        id = UUID.fromString(newID);
        preReqs = new ArrayList<>();
        skillBonuses = new ArrayList();
        subSkills = FXCollections.observableArrayList();
        isCat = false;
        currentLevel = 1;
        
    }
    
    public skill(String newID, String newName)
    {
        id = UUID.fromString(newID);
        name = newName;
        preReqs = new ArrayList<>();
        skillBonuses = new ArrayList();
        subSkills = FXCollections.observableArrayList();
        isCat = false;
        currentLevel = 1;
    }
    
    public skill(String newID, String newName, Boolean newCat)
    {
        id = UUID.fromString(newID);
        name = newName;
        isCat = true;
        skillBonuses = new ArrayList();
        subSkills = FXCollections.observableArrayList();
        currentLevel = 1;
        
    }
        
    public skill(String newID, String newName, String newBase, String newPerLevel, String newBonus, String newBook, String newText)
    {
        id = UUID.fromString(newID);
        name = newName;
        basePercent = Integer.parseInt(newBase);
        perLevel = Integer.parseInt(newPerLevel);
        book = newBook;
        text = newText;
        preReqs = new ArrayList<>();
        skillBonuses = new ArrayList();
        subSkills = FXCollections.observableArrayList();
        setBonuses(newBonus);
        isCat = false;
        currentLevel = 1;
    }
    
    public void generateUUID()
    {
        id = UUID.randomUUID();
        
    }
    
    public void setName(String newName){name = newName;}
    
    public void setBasePercent(String newBase){basePercent = Integer.parseInt(newBase);}
    
    public void setBasePercent(int newBase){basePercent = newBase;}
    
    public void setPerLevel(String newPerLevel){perLevel = Integer.parseInt(newPerLevel);}
    
    public void setPerLevel(int newPerLevel){perLevel = newPerLevel;}
    
    public void setLevel(int newLevel) { currentLevel = newLevel; }
    
    public void setBonuses(String newBonus)
    {
        if (newBonus.equalsIgnoreCase("none"))
        {
            skillBonuses.clear();
            BonusesSet = false;
            
        } else
        {
            skillBonuses.clear();
            String[] tokenStrings = newBonus.split(";");
            for(String aToken : tokenStrings)
            {
                skillBonuses.add(new bonus(aToken));
                
            }
            BonusesSet = true;
                
        }
    }
    
    public int getLevel() { return currentLevel; }
    
    public int getLeveledPercent()
    {
        if (currentLevel > 1)
        {
            return basePercent + ((currentLevel - 1) * perLevel);
        }
        else
        {
            return basePercent;
        }
    }
    
    public boolean areBonuses()
    {
        return BonusesSet;
        
    }
    
    public String getBonuses()
    {
        String fullString = "";
        if (BonusesSet)
        {
            for (bonus tempBonus : skillBonuses)
            {
                fullString = (fullString + tempBonus.getBonusString() + ";");

            }
        }
        else
        {
            fullString = "NONE";
            
        }
        
        return fullString;
    }
    
    public void setBook(String newBook){book = newBook;}
    
    public void setText(String newText)
    {
        text = newText;
        
    }
    
    public void setIsCat(boolean blnIsCat)
    {
        isCat = blnIsCat;
        
    }
    
    public String getIDString()
    {
        return id.toString();
    }
    
    public UUID getID()
    {
        return id;
        
    }
    
    public void addSubSkill(subSkill newSubSkill)
    {
        subSkills.add(newSubSkill);
        
    }
    
    public void addSubSkill(String name, int base, int perlevel)
    {
        subSkills.add(new subSkill(name, base, perlevel));
        
    }
    
    public void removeSubSkill(subSkill s)
    {
        subSkills.remove(s);
        
    }
    
    public void updateSubSkill(String seekName, String name, int base, int perlevel)
    {
        subSkills.forEach(search -> {
            if (search.getName().matches(seekName))
                {
                    search.setAll(name, base, perlevel);
                }
        });
    }
    
    public void updateSubName()
    {
        
    }
    
    public void setSubSkills(ObservableList list)
    {
        subSkills.clear();
        subSkills.addAll(list);
    }
    
    public int getSubSkillQuantity()
    {
        return subSkills.size();
        
    }
    
    public boolean isSubSkillEmpty()
    {
        return subSkills.isEmpty();
    }
    
    public subSkill getSubSkill(int subSkillNumber)
    {
        return subSkills.get(subSkillNumber);
        
    }
    
    public List getSubSkills()
    {
        return subSkills;
        
    }
    
    public boolean getIsCat()
    {
        return isCat;
        
    }
    
    @Override
    public String toString()
    {
        if(isCat)
        {
            return name;
            
        } else
        {
            String returnString = name+" ("+Integer.toString(getLeveledPercent())+"%, +"+Integer.toString(perLevel)+"%/lvl)";
            return returnString;
            
        }
    }
}
