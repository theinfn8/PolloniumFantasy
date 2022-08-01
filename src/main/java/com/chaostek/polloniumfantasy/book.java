package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */
public class book {
    private String name;
    private String isbn;
    private String game;
    
    public book()
    {
        
    }
    
    public book(String newISBN)
    {
        isbn = newISBN;
        
    }
    
    public book(String newISBN, String newName)
    {
        isbn = newISBN;
        name = newName;
        
    }
    
    public book(String newISBN, String newName, String newGame)
    {
        isbn = newISBN;
        name = newName;
        game = newGame;
        
    }
    
    public String getName() { return name;}
    public String getISBN() { return isbn;}
    public String getGame() { return game;}
    
    public void setName(String newName) { name = newName;}
    public void setISBN(String newISBN) { isbn = newISBN;}
    public void setGame(String newGame) { game = newGame;}
    
    @Override
    public String toString()
    {
        return name;
        
    }
}
