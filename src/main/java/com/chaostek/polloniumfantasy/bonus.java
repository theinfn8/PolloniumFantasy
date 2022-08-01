package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */

public class bonus {
    private String Stat;
    private int FlatBonus;
    private String StringBonus;
    
    public bonus()
    {
        
    }
    
    public bonus(String workingString)
    {
        String[] breakdown = workingString.split(":");
        Stat = breakdown[0];
        StringBonus = breakdown[1];
        
    }
    
    public void setStat(String newStat) { Stat = newStat;}
    public String getStat() { return Stat;}

    public void setFlatBonus(int newFlatBonus) { FlatBonus = newFlatBonus;}
    public int getFlatBonus() {return FlatBonus;}

    public void setStringBonus(String newStringBonus) { StringBonus = newStringBonus;}
    public String getStringBonus() { return StringBonus;}
    
    public String getBonusString()
    {
        return (Stat + ":" + StringBonus);
        
    }
    
}
