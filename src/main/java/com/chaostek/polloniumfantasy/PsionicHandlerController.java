package com.chaostek.polloniumfantasy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author chaosburn
 */
public class PsionicHandlerController implements Initializable
{
    Stage psionicStage;
    psionicsCollection gamePsionicsList;
    psionic selectedPsionic;
    editMode handlerMode;
    
    @FXML TreeView psionicTree;
    @FXML TextField txtName, txtRange, txtDuration;
    @FXML TextField txtISP, txtSavingThrow, txtTrance;
    @FXML TextArea txtText;
    
    @FXML Button cmdSave, cmdSaveAs, cmdOpen, cmdExit, cmdCommit;
    @FXML Button cmdAdd, cmdUpdate, cmdRemove, cmdNewCat, cmdCancel, cmdClear;
    
    private boolean areEntriesBad()
    {
        if (txtName.getText().isEmpty() || !txtName.getText().matches(App.REGEX))
        { 
            System.out.println("Name error");
            return true;
        }
        if (txtRange.getText().isEmpty() || !txtRange.getText().matches(App.REGEX))
        {
            System.out.println("Range error");
            return true;
        }
        if (txtDuration.getText().isEmpty())
        {
            System.out.println("Duration error");
            return true;
        }
        
        if (txtTrance.getText().isEmpty()) txtTrance.setText("Instant");
        
        if (txtISP.getText().isEmpty()) txtISP.setText("0");
        if (!isNumeric(txtISP.getText()))
        {
            System.out.println("ISP error");
            return true;
        }
        
        //if (!txtText.getText().matches(App.REGEX)) return true;
        if (txtText.getText() == null) txtText.setText(txtName.getText());
        if (txtText.getText().isEmpty()) txtText.setText(txtName.getText());
        
        return false;
    }
    
    private boolean isNumeric(String strNum)
    {
        return strNum.matches("\\d+");
        
    }
    
    private void clearTextFields()
    {
        txtName.setText("");
        txtISP.setText("");
        txtDuration.setText("");
        txtTrance.setText("");
        txtRange.setText("");
        txtSavingThrow.setText("");
        txtText.setText("");
        
    }
    
    private void cycleTextFields()
    {
        txtName.setDisable(!txtName.isDisable());
        txtISP.setDisable(!txtISP.isDisable());
        txtDuration.setDisable(!txtDuration.isDisable());
        txtTrance.setDisable(!txtTrance.isDisable());
        txtRange.setDisable(!txtRange.isDisable());
        txtSavingThrow.setDisable(!txtSavingThrow.isDisable());
        txtText.setDisable(!txtText.isDisable());
        
    }

    private void actionButtonsOff()
    {
            cmdAdd.setDisable(true);
            cmdUpdate.setDisable(true);
            cmdNewCat.setDisable(true);
            cmdRemove.setDisable(true);
            //cmdClear.setDisable(true);
            cmdCommit.setDisable(false);
            cmdCancel.setDisable(false);

    }
    
    private void actionButtonsOn()
    {
            cmdAdd.setDisable(false);
            cmdUpdate.setDisable(false);
            cmdNewCat.setDisable(false);
            cmdRemove.setDisable(false);
            //cmdClear.setDisable(true);
            cmdCommit.setDisable(true);
            cmdCancel.setDisable(true);

    }
    
    @FXML
    private void cmdSaveAction() throws IOException
    {
        gamePsionicsList.saveToXML();
        
    }
    
    @FXML
    private void cmdExitAction() throws IOException
    {
        App.setRoot("dataeditor");
        
    }
    
    @FXML
    private void cmdOpenAction() throws IOException
    {
        File returnVal = notification.getFile(psionicStage, "Select an Item XML File", "XML file", "*.xml");
        
        if (returnVal != null)
        {
            gamePsionicsList.openXML(returnVal.getPath());
            psionicTree.setRoot(gamePsionicsList.getRoot());
            psionicTree.refresh();
            
        }
        else
        {
            System.out.println("Cancel selected.");
        }
        
        cmdSave.setDisable(false);
        
    }
    
    @FXML
    private void cmdSaveAsAction(ActionEvent event)
    {
        File returnVal = notification.getSaveAsFile(psionicStage, "Select an Item XML File", "XML file", "*.xml");

        
        if (returnVal != null)
        {
            gamePsionicsList.saveAs(returnVal.getPath());
            gamePsionicsList.saveToXML();
            
        }
        else
        {
            System.out.println("Cancel selected.");
            
        }
        
        cmdSave.setDisable(false);
        
    }
    
    @FXML
    private void cmdClearAction() throws IOException
    {
        clearTextFields();
        
    }
    
    @FXML
    private void cmdRemoveAction() throws IOException
    {
        actionButtonsOff();
        
        handlerMode = editMode.DELETE;
            
    }
    
    @FXML
    private void cmdNewCatAction() throws IOException
    {
        Optional<String> result = notification.getStringInfo("Category Name", "Enter the name of the new category:");
        if (result.isPresent() && result.get().matches(App.REGEX))
        {
            psionic newCat = new psionic();
            newCat.generateUUID();
            newCat.setName(result.get());
            newCat.setCat(true);
            psionicTree.getRoot().getChildren().add(new TreeItem(newCat));
            psionicTree.refresh();
        }
    }
    
    @FXML
    private void cmdCancelAction() throws IOException
    {
        actionButtonsOn();
        psionicTree.setDisable(false);
        handlerMode = editMode.READ;
        cycleTextFields();
        
    }
    
    @FXML
    private void cmdUpdateAction() throws IOException
    {
        psionicTree.setDisable(true);
        actionButtonsOff();

        handlerMode = editMode.UPDATE;
        //clearTextFields();
        cycleTextFields();
        
    }
    
    @FXML
    private void cmdCommitAction() throws IOException
    {
        TreeItem<psionic> currentPsionic = psionicTree.getTreeItem(psionicTree.getSelectionModel().getSelectedIndex());
        
        switch (handlerMode)
        {
            case READ:
                return;
                
            case CREATE:
                // Add new psionic
                System.out.println("Made it to create");
                if (areEntriesBad())
                {
                    System.out.println("Bad entries?");
                    return;

                }

                psionic newPsionic = new psionic();
                newPsionic.generateUUID();
                newPsionic.setName(txtName.getText());
                newPsionic.setIsp(Integer.parseInt(txtISP.getText()));
                newPsionic.setDuration(txtDuration.getText());
                newPsionic.setRange(txtRange.getText());
                newPsionic.setSavingThrow(txtSavingThrow.getText());
                newPsionic.setLengthOfTrance(txtTrance.getText());
                newPsionic.setText(txtText.getText());

                if (currentPsionic.getValue().isCat())
                {
                    currentPsionic.getChildren().add(new TreeItem(newPsionic));
                }
                else
                {
                    currentPsionic.getParent().getChildren().add(new TreeItem(newPsionic));
                }

                psionicTree.refresh();
                clearTextFields();
                /*cycleTextFields();
                selectedPsionic = new psionic();
                actionButtonsOn();
                psionicTree.setDisable(false);
                handlerMode = editMode.READ;*/
                break;
                
            case UPDATE:
                // Update psionic
                if (currentPsionic.getValue().isCat())
                {
                    // TODO Add better error handling communication'
                    //System.out.println("Cannot have a Category selected");

                    selectedPsionic = currentPsionic.getValue();
                    selectedPsionic.setName(txtName.getText());

                }
                else if (areEntriesBad())
                {
                    System.out.println("Text entries are incorrect");

                }
                else
                {
                    selectedPsionic = currentPsionic.getValue();

                    selectedPsionic.setName(txtName.getText());
                    selectedPsionic.setIsp(Integer.parseInt(txtISP.getText()));
                    selectedPsionic.setDuration(txtDuration.getText());
                    selectedPsionic.setRange(txtRange.getText());
                    selectedPsionic.setSavingThrow(txtSavingThrow.getText());
                    selectedPsionic.setLengthOfTrance(txtTrance.getText());
                    selectedPsionic.setText(txtText.getText());

                }

                psionicTree.refresh();
                clearTextFields();
                cycleTextFields();
                actionButtonsOn();
                psionicTree.setDisable(false);
                handlerMode = editMode.READ;
                break;
                
            case DELETE:
                // Delete selected psionic
                currentPsionic.getParent().getChildren().remove(currentPsionic);
                psionicTree.refresh();
                clearTextFields();
                actionButtonsOn();
                psionicTree.setDisable(false);
                handlerMode = editMode.READ;
                break;
        }
        
        cmdSave.setDisable(false);
        
    }
    
    @FXML
    private void cmdAddAction() throws IOException
    {
        actionButtonsOff();
        cycleTextFields();
        psionicTree.setDisable(true);
        handlerMode = editMode.CREATE;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectedPsionic = new psionic();
        
        gamePsionicsList = new psionicsCollection();
        psionicTree.setShowRoot(false);
        psionicTree.setRoot(gamePsionicsList.getRoot());
        
        gamePsionicsList.openXML("lib/psionics.xml");
        psionicTree.setRoot(gamePsionicsList.getRoot());
        psionicTree.refresh();
        handlerMode = editMode.READ;
        cycleTextFields(); // Should shut them all off to input
        cmdCancel.setDisable(true);
        cmdCommit.setDisable(true);
        cmdSave.setDisable(true);
        
        psionicTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<psionic>>()
        {
            public void changed(ObservableValue<? extends TreeItem<psionic>> changed, TreeItem<psionic> oldVal, TreeItem<psionic> newVal)
            {
                if (newVal != null)
                {
                    TreeItem<psionic> currentPsionic = psionicTree.getTreeItem(psionicTree.getSelectionModel().getSelectedIndex());
                    selectedPsionic = currentPsionic.getValue();

                    txtName.setText(selectedPsionic.getName());
                    txtISP.setText(String.valueOf(selectedPsionic.getIsp()));
                    txtDuration.setText(selectedPsionic.getDuration());
                    txtTrance.setText(selectedPsionic.getLengthOfTrance());
                    txtText.setText(selectedPsionic.getText());
                    txtRange.setText(selectedPsionic.getRange());
                    txtSavingThrow.setText(selectedPsionic.getSavingThrow());
                    
                }
            }
        });

    }    

}
