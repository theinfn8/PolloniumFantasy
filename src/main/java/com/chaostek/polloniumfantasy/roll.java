package com.chaostek.polloniumfantasy;

/**
 *
 * @author chaosburn
 */

import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.HashMap;

public class roll
{
    static SecureRandom secRand;
    private static ArrayDeque<Integer> values;
    private static ArrayDeque<String> ops;
    
    static final HashMap<String, Integer> OPERATIONS = new HashMap<>()
    {
        {
            put("+", 1);
            put("-", 1);
            put("*", 2);
            put("/", 2);
        }
    };
    
    roll()
    {
        secRand = new SecureRandom();
        secRand.reseed();
        
        values = new ArrayDeque<>();
        ops = new ArrayDeque<>();
        
    }
    
    public static int d20()
    {
        return secRand.nextInt(19)+1;
        
    }
    
    public static int d12()
    {
        return secRand.nextInt(11)+1;
        
    }
    
    public static int d10()
    {
        return secRand.nextInt(9)+1;
        
    }
    
    public static int d8()
    {
        return secRand.nextInt(7)+1;
        
    }
    
    public static int d6()
    {
        return secRand.nextInt(5)+1;
        
    }
    
    public static int d4()
    {
        return secRand.nextInt(3)+1;
        
    }
    
    public int rollFormula(String rollString)
    {
        String token;
        String[] splitArray;
        
        rollString = rollString.toUpperCase();
        splitArray = rollString.split(" ");
        
        for (int i = 0; i <= splitArray.length; i++)
        {
            token = splitArray[i];
            if (OPERATIONS.containsKey(token))
            {
                int peekPrec, tokenPrec;
                peekPrec = OPERATIONS.get(ops.peek());
                tokenPrec = OPERATIONS.get(token);
                
                if (ops.isEmpty() || tokenPrec > peekPrec)
                {
                    ops.push(token);
                }
                else
                {
                    if (peekPrec < tokenPrec)
                    {
                        ops.push(token);
                    }
                    else if (peekPrec >= tokenPrec)
                    {
                        processOp();
                    }
                }
            }
            else if (token.matches("\\("))
            {
                ops.push(token);
                
            }
            else if (token.matches("\\)"))
            {
                String newOp;
                
                newOp = ops.peek();
                
                while (!newOp.matches("\\("))
                {

                    processOp();
                    newOp = ops.peek();
                    
                }
                ops.pop();
                
            }
            else if (token.contains("D"))
            {
                values.push(rollDice(token));
                
            }
            else
            {
                values.push(Integer.getInteger(token));
                
            }
        }
        
        while (!ops.isEmpty())
        {
            processOp();
            
        }
        
        return values.pop();
    }

    private int rollDice(String dieRoll)
    {
        String[] vals;
        int total, quantity, die;

        vals = dieRoll.split("D");
        quantity = Integer.getInteger(vals[0]);
        die = Integer.getInteger(vals[1]);
        total = 0;

        switch(die)
        {
            case 4:
                for (int j = 1; j <= quantity; j++)
                { total = total + d4(); }
                break;

            case 6:
                for (int j = 1; j <= quantity; j++)
                { total = total + d6(); }
                break;

            case 8:
                for (int j = 1; j <= quantity; j++)
                { total = total + d8(); }
                break;

            case 10:
                for (int j = 1; j <= quantity; j++)
                { total = total + d10(); }
                break;

            case 12:
                for (int j = 1; j <= quantity; j++)
                { total = total + d12(); }
                break;

            case 20:
                for (int j = 1; j <= quantity; j++)
                { total = total + d20(); }
                break;
        }
        return total;
        
    }
    
    private void processOp()
    {
        int val1, val2;
        String newOp;
        
        newOp = ops.pop();
        val1 = values.pop();
        val2 = values.pop();
        // Do + - * /
        switch(newOp)
        {
            case "+":
                values.push(val1 + val2);
                break;
            case "-":
                values.push(val1 - val2);
                break;
            case "*":
                values.push(val1 * val2);
                break;
            case "/":
                values.push(val1 / val2);
                break;

        }
    }
}
