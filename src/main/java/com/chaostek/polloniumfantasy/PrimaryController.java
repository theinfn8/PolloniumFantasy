package com.chaostek.polloniumfantasy;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSkillHandler() throws IOException {
        
        App.setRoot("SkillHandler");
        
    }
    
    @FXML
    private void switchToPsionicHandler() throws IOException
    {
        App.setRoot("PsionicHandler");
        
    }
}
