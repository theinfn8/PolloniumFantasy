package com.chaostek.polloniumfantasy;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author niteseer
 */
public class notification {
    static void generateErrorMessage(String title, String message)
    {
        Dialog<String> errorDialog = new Dialog<>();
        errorDialog.setHeaderText(title);
        errorDialog.setContentText(message);
        
        ButtonType dialogButtons = new ButtonType("OK", ButtonData.OK_DONE);
        errorDialog.getDialogPane().getButtonTypes().add(dialogButtons);
        
        errorDialog.showAndWait();
        
    }
    
    static Optional<String> getStringInfo(String title, String text)
    {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setContentText(text);
        inputDialog.setHeaderText(title);
        return inputDialog.showAndWait();
        
    }
    
    static File getFile(Stage openStage, String title, String fileType, String fileExtension)
    {
        return getFile(openStage, title, fileType, fileExtension, "lib");
        
    }
    
    static File getFile(Stage openStage, String title, String fileType, String fileExtension, String initDir)
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.setInitialDirectory(new File(initDir));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileType, fileExtension));
        
        return fc.showOpenDialog(openStage);
        
    }

    static File getSaveAsFile(Stage openStage, String title, String fileType, String fileExtension)
    {
        return getSaveAsFile(openStage, title, fileType, fileExtension, "lib");

    }
    
    static File getSaveAsFile(Stage openStage, String title, String fileType, String fileExtension, String initDir)
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.setInitialDirectory(new File(initDir));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileType, fileExtension));
        
        return fc.showSaveDialog(openStage);
        
    }
}
