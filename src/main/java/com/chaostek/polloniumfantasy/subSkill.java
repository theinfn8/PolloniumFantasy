package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */
public class subSkill
{
    private String name;
    private int base;
    private int perLevel;
    
    subSkill()
    {
        name = "";
        base = 0;
        perLevel = 0;
        
    }
    
    subSkill(String newName, int newBase, int newPerLevel)
    {
        name = newName;
        base = newBase;
        perLevel = newPerLevel;
        
    }
    
    public String getName() { return name; }
    public int getBase() { return base; }
    public int getPerLevel() { return perLevel; }
    
    public void setName(String newName) { name = newName; }
    public void setBase(int newBase) { base = newBase; }
    public void setPerLevel(int newPerLevel) { perLevel = newPerLevel; }
    
    public void setAll(String name, int base, int perLevel)
    {
        this.name = name;
        this.base = base;
        this.perLevel = perLevel;
        
    }
    
}
