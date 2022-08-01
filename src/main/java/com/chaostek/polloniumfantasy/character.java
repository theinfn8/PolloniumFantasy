package com.chaostek.polloniumfantasy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author chaosburn
 */
public class character
{
    private String name, trueName;
    private String race;
    private String alignment;
    private int age, lifespan;
    private int height, weight;
    private String landOfOrigin, environment, background, racialHostility, disposition;
    private ArrayList<String> insanity;

    private int level, experiencePoints;
    private int hp, sdc;
    private int ppe, isp, chi;
    private int iq, me, ma;
    private int ps, pp, pe, pb, spd;

    private int iqSkillBonus;
    private int trustIntimidate, charmImpress;

    private HashMap<String, Integer> savingThrows;

    private ArrayList<ability> specialAbilities;
    private ArrayList<skill> occSkills, otherSkills, secondarySkills;
    private ArrayList<spell> spells;
    private ArrayList<psionic> psionics;
    private ArrayList<weapon> weapons;
    private ArrayList<item> equipment;

    private ArrayList<bonus> bonusLog;

    private int numberOfAttacks, initiative, damage, strike, parry, dodge, roll;
    private skill martialArt;

    
    public void character()
    {
        savingThrows = new HashMap<>();
        savingThrows.put("Spell", 12);
        savingThrows.put("Ward", 14);
        savingThrows.put("Fume", 14);
        savingThrows.put("Ritual", 16);
        savingThrows.put("Circle", 16);
        savingThrows.put("Faerie", 16);
        savingThrows.put("Psionics", 0);
        savingThrows.put("Toxins", 0);
        savingThrows.put("Poisons", 14);
        savingThrows.put("Drugs", 0);
        savingThrows.put("Insanity", 0);
        savingThrows.put("Possession", 0);
        savingThrows.put("Horror Factor", 0);
        savingThrows.put("Coma/Death", 0);
        
        
    }

    public void addAbility(ability newAbility)
    {
        specialAbilities.add(newAbility);
            
    }
    
    public ability getAbility(int index)
    {
        return specialAbilities.get(index);
        
    }
    
    public void removeAbility(int index)
    {
        specialAbilities.remove(index);
        
    }
    public void clearAbilities()
    {
        specialAbilities.clear();
        
    }
    
    // OCC Skills
    public void addOccSkill(skill newSkill)
    {
        occSkills.add(newSkill);
            
    }
    
    public skill getOccSkill(int index)
    {
        return occSkills.get(index);
        
    }
    
    public void removeOccSkill(int index)
    {
        occSkills.remove(index);
        
    }
    public void clearOccSkills()
    {
        occSkills.clear();
        
    }

    // Other Skills
    public void addOtherSkill(skill newSkill)
    {
        otherSkills.add(newSkill);
            
    }
    
    public skill getOtherSkill(int index)
    {
        return otherSkills.get(index);
        
    }
    
    public void removeOtherSkill(int index)
    {
        otherSkills.remove(index);
        
    }
    public void clearOtherSkills()
    {
        otherSkills.clear();
        
    }
    
    // Secondary Skills
    public void addSecondarySkill(skill newSkill)
    {
        secondarySkills.add(newSkill);
            
    }
    
    public skill getSecondarySkill(int index)
    {
        return secondarySkills.get(index);
        
    }
    
    public void removeSecondarySkill(int index)
    {
        secondarySkills.remove(index);
        
    }
    public void clearSecondarySkills()
    {
        secondarySkills.clear();
        
    }

    // Spells
    public void addSpell(spell newSpell)
    {
        spells.add(newSpell);
            
    }
    
    public spell getSpell(int index)
    {
        return spells.get(index);
        
    }
    
    public void removeSpell(int index)
    {
        spells.remove(index);
        
    }
    public void clearSpells()
    {
        spells.clear();
        
    }
    
    // Psionics
    public void addPsionic(psionic newPsionic)
    {
        psionics.add(newPsionic);
            
    }
    
    public psionic getPsionic(int index)
    {
        return psionics.get(index);
        
    }
    
    public void removePsionic(int index)
    {
        psionics.remove(index);
        
    }
    public void clearPsionics()
    {
        psionics.clear();
        
    }
    
    // Weapons
    public void addOccSkill(weapon newWeapon)
    {
        weapons.add(newWeapon);
            
    }
    
    public weapon getWeapon(int index)
    {
        return weapons.get(index);
        
    }
    
    public void removeWeapon(int index)
    {
        weapons.remove(index);
        
    }
    public void clearWeapons()
    {
        weapons.clear();
        
    }
    
    // Equipment (item)
    public void addItem(item newItem)
    {
        equipment.add(newItem);
            
    }
    
    public item getItem(int index)
    {
        return equipment.get(index);
        
    }
    
    public void removeItem(int index)
    {
        equipment.remove(index);
        
    }
    
    public void clearEquipment()
    {
        equipment.clear();
        
    }
    
    public void clearItems()
    {
        equipment.clear();
        
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTrueName() { return trueName; }
    public void setTrueName(String trueName) { this.trueName = trueName; }
    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }
    public String getAlignment() { return alignment; }
    public void setAlignment(String alignment) { this.alignment = alignment; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public int getLifespan() { return lifespan; }
    public void setLifespan(int lifespan) { this.lifespan = lifespan; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public String getLandOfOrigin() { return landOfOrigin; }
    public void setLandOfOrigin(String landOfOrigin) { this.landOfOrigin = landOfOrigin; }
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }
    public String getRacialHostility() { return racialHostility; }
    public void setRacialHostility(String racialHostility) { this.racialHostility = racialHostility; }
    public String getDisposition() { return disposition; }
    public void setDisposition(String disposition) { this.disposition = disposition; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getExperiencePoints() { return experiencePoints; }
    public void setExperiencePoints(int experiencePoints) { this.experiencePoints = experiencePoints; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getSdc() { return sdc; }
    public void setSdc(int sdc) { this.sdc = sdc; }
    public int getPpe() { return ppe; }
    public void setPpe(int ppe) { this.ppe = ppe; }
    public int getIsp() { return isp; }
    public void setIsp(int isp) { this.isp = isp; }
    public int getChi() { return chi; }
    public void setChi(int chi) { this.chi = chi; }
    public int getIq() { return iq; }
    public void setIq(int iq) { this.iq = iq; }
    public int getMe() { return me; }
    public void setMe(int me) { this.me = me; }
    public int getMa() { return ma; }
    public void setMa(int ma) { this.ma = ma; }
    public int getPs() { return ps; }
    public void setPs(int ps) { this.ps = ps; }
    public int getPp() { return pp; }
    public void setPp(int pp) { this.pp = pp; }
    public int getPe() { return pe; }
    public void setPe(int pe) { this.pe = pe; }
    public int getPb() { return pb; }
    public void setPb(int pb) { this.pb = pb; }
    public int getSpd() { return spd; }
    public void setSpd(int spd) { this.spd = spd; }
    public int getIqSkillBonus() { return iqSkillBonus; }
    public void setIqSkillBonus(int iqSkillBonus) { this.iqSkillBonus = iqSkillBonus; }
    public int getTrustIntimidate() { return trustIntimidate; }
    public void setTrustIntimidate(int trustIntimidate) { this.trustIntimidate = trustIntimidate; }
    public int getCharmImpress() { return charmImpress; }
    public void setCharmImpress(int charmImpress) { this.charmImpress = charmImpress; }
    public int getNumberOfAttacks() { return numberOfAttacks; }
    public void setNumberOfAttacks(int numberOfAttacks) { this.numberOfAttacks = numberOfAttacks; }
    public int getInitiative() { return initiative; }
    public void setInitiative(int initiative) { this.initiative = initiative; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public int getStrike() { return strike; }
    public void setStrike(int strike) { this.strike = strike; }
    public int getParry() { return parry; }
    public void setParry(int parry) { this.parry = parry; }
    public int getDodge() { return dodge; }
    public void setDodge(int dodge) { this.dodge = dodge; }
    public int getRoll() { return roll; }
    public void setRoll(int roll) { this.roll = roll; }
    public skill getMartialArt() { return martialArt; }
    public void setMartialArt(skill martialArt) { this.martialArt = martialArt; }
    
}
