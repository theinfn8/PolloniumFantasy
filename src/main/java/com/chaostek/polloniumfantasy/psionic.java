package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */

import java.util.UUID;

public class psionic
{
    private UUID id;
    private String name, text, range, duration, savingThrow;
    private String lengthOfTrance;
    private boolean isCat;
    private int isp;

    public psionic()
    {
        isCat = false;
        
    }
    
    psionic(String id, String name)
    {
        isCat = false;
        this.id = UUID.fromString(id);
        this.name = name;
        
    }
    
    psionic(String id, String name, boolean isCat)
    {
        this.isCat = isCat;
        this.id = UUID.fromString(id);
        this.name = name;
        
    }
    
    public UUID getId() { return id; }
    public String getIdString() { return id.toString(); }
    public void setId(UUID id) { this.id = id; }
    public void setID(String id) { this.id = UUID.fromString(id); }
    public void generateUUID() { id = UUID.randomUUID(); }

    public boolean isCat() { return isCat; }
    public void setCat(boolean isCat) { this.isCat = isCat; }
    
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

    public String getLengthOfTrance() { return lengthOfTrance; }
    public void setLengthOfTrance(String lengthOfTrance) { this.lengthOfTrance = lengthOfTrance; }

    public int getIsp() { return isp; }
    public void setIsp(int isp) { this.isp = isp; }
    
    @Override
    public String toString()
    {
        if (isCat)
        { return name; }
        else
        { return name + " (ISP: " + Integer.toString(isp) + ")"; }
        
    }
}
