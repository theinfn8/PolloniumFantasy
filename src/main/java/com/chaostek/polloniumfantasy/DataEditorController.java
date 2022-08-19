package com.chaostek.polloniumfantasy;

import java.io.IOException;
import javafx.fxml.FXML;

public class DataEditorController {
    @FXML
    private void switchToCharacterCreator() throws IOException {
        
    }

    @FXML
    private void switchToCharacterViewer() throws IOException {
        
    }
    
    @FXML
    private void switchToSkillHandler() throws IOException {
        
        App.setRoot("SkillHandler");
        
    }
    
    @FXML
    private void switchToPsionicHandler() throws IOException
    {
        App.setRoot("PsionicHandler");
        
    }

    @FXML
    private void switchToSpellHandler() throws IOException
    {
        App.setRoot("SpellHandler");
        
    }

    @FXML
    private void switchToItemHandler() throws IOException
    {
        App.setRoot("ItemHandler");
        
    }
    
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("primary");
    }
}