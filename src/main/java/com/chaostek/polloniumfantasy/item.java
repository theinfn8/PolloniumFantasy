package com.chaostek.polloniumfantasy;

import java.util.UUID;

/**
 *
 * @author chaosburn
 */
public class item
{
    private UUID id;
    private String name, price, description, category, weight;
    private boolean isCat;

    item()
    {
        
    }
    
    item(String id)
    {
        this.id = UUID.fromString(id);
        
    }
    
    item(String id, String name)
    {
        this.id = UUID.fromString(id);
        this.name = name;
        
    }
    
    public boolean isCat() { return isCat; }
    public void setIsCat(boolean isCat) { this.isCat = isCat; }
    
    public UUID getId() { return id; }
    public String getID() { return id.toString(); }
    
    public void setId(UUID id) { this.id = id; }
    public void setID(String id) { this.id = UUID.fromString(id); }
    public void generateUUID() { id = UUID.randomUUID(); }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    @Override
    public String toString()
    {
        return name;
    }
}
