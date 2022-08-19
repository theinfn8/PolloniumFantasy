package com.chaostek.polloniumfantasy;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToCharacterCreator() throws IOException {
        
    }

    @FXML
    private void switchToCharacterViewer() throws IOException {
        
    }

    @FXML
    private void switchToDataEditor() throws IOException {
        App.setRoot("DataEditor");
        
    }
}
