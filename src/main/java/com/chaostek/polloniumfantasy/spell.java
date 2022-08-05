package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */

import java.util.UUID;

public class spell
{
    private UUID id;
    private String name, text;
    private String range, duration, savingThrow, listName;
    private String ppe, level;
    private boolean isCat;
    private boolean isLevel;

    public boolean isLevel()
    {
        return isLevel;
    }

    public void setIsLevel(boolean isLevel)
    {
        this.isLevel = isLevel;
    }
    
    spell()
    {
        isCat = false;
        
    }
    
    spell(String id, String name)
    {
        isCat = false;
        this.id = UUID.fromString(id);
        this.name = name;
        
    }
    
    spell(String id, String name, boolean isCat)
    {
        this.isCat = isCat;
        this.id = UUID.fromString(id);
        this.name = name;
        
    }
    
    public String getListName() { return listName; }
    public void setListName(String listName) { this.listName = listName; }

    public UUID getId() { return id; }
    public String getID() { return id.toString(); }
    
    public void setId(UUID id) { this.id = id; }
    public void setID(String id) { this.id = UUID.fromString(id); }
    public void generateUUID() { id = UUID.randomUUID(); }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getRange() { return range; }
    public void setRange(String range) { this.range = range; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getSavingThrow() { return savingThrow; }
    public void setSavingThrow(String savingThrow) { this.savingThrow = savingThrow; }

    public String getPpe() { return ppe; }
    public void setPpe(String ppe) { this.ppe = ppe; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public boolean isCat() { return isCat; }
    public void setCat(boolean isCat) { this.isCat = isCat; }

    @Override
    public String toString()
    {
        if (isCat || isLevel) return name;
        return name + " (PPE: " + ppe + ")";
        
    }
}
