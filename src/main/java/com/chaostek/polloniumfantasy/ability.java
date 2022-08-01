package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */

import java.util.UUID;
import java.util.ArrayList;

public class ability
{
    private UUID id;
    String name, text;
    int percentage, perLevel, currentLevel;
    int ppe, isp;

    private ArrayList<bonus> abilityBonuses;
    
    public void ability()
    {
        ppe = 0;
        isp = 0;
        percentage = 0;
        perLevel = 0;
        currentLevel = 1;
    }

    public void generateUUID() { id = UUID.randomUUID(); }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getPercentage() { return percentage; }
    public void setPercentage(int percentage) { this.percentage = percentage; }

    public int getPerLevel() { return perLevel; }
    public void setPerLevel(int perLevel) { this.perLevel = perLevel; }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public int getPpe() { return ppe; }
    public void setPpe(int ppe) { this.ppe = ppe; }

    public int getIsp() { return isp; }
    public void setIsp(int isp) { this.isp = isp; }
    
    
    public int getLeveledPercent()
    {
        if (currentLevel > 1)
        { return percentage + ((currentLevel-1) * perLevel); }
        else
        { return percentage; }
    }
    
    @Override
    public String toString()
    {
        String addon;
        
        addon = "";
        
        if (percentage > 0)
        {
            addon = addon + Integer.toString(getLeveledPercent());
        }
        if (ppe > 0)
        {
            if (addon.length() > 0) { addon = addon + "; " + "PPE: " + ppe; }
            else { addon = "PPE: " + ppe; }
        }
        if (isp > 0)
        {
            if (addon.length() > 0) { addon = addon + "; " + "ISP: " + isp; }
            else { addon = "ISP: " + isp; }
        }
        
        if (addon.length() > 0)
        {
            return name + " (" + addon + ")";
        }
        else
        {
            return name;
        }
    }
}
