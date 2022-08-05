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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author chaosburn
 */
public class SpellHandlerController implements Initializable
{
    Stage spellStage;
    spellsCollection gameSpellsList;
    spell selectedSpell;
    editMode handlerMode;
    final String regex = "^[a-zA-Z0-9,+\\-\\s\\.\\(\\)\\:\\;\\%]+$";
    
    @FXML TreeView spellTree;
    @FXML TextField txtName, txtID, txtRange, txtDuration;
    @FXML TextField txtPPE, txtSavingThrow, txtLevel;
    @FXML TextArea txtText;
    
    @FXML Button cmdSave, cmdOpen, cmdExit, cmdCommit;
    @FXML Button cmdAdd, cmdUpdate, cmdRemove, cmdNewCat, cmdCancel, cmdClear;
    
    private boolean areEntriesBad()
    {
        if (txtName.getText().isEmpty() || !txtName.getText().matches(regex))
        { 
            System.out.println("Name error");
            return true;
        }
        if (txtRange.getText().isEmpty() || !txtRange.getText().matches(regex))
        {
            System.out.println("Range error");
            return true;
        }
        if (txtDuration.getText().isEmpty())
        {
            System.out.println("Duration error");
            return true;
        }
        
        if (txtLevel.getText().isEmpty())
        {
            System.out.println("Level Error");
            return true;
        }
        
        if (isNumeric(txtLevel.getText()))
        {
            txtLevel.setText("Level " + txtLevel.getText());
        }
        
        if (txtPPE.getText().isEmpty()) txtPPE.setText("0");
        
        if (!isNumeric(txtPPE.getText()))
        {
            System.out.println("PPE error");
            return true;
        }
        
        //if (!txtText.getText().matches(regex)) return true;
        if (txtText.getText() == null) txtText.setText(txtName.getText());
        if (txtText.getText().isEmpty()) txtText.setText(txtName.getText());
        
        return false;
    }
    
    private boolean isNumeric(String strNum)
    {
        return strNum.matches("\\d+");
        
        /*try
        {
            int i = Integer.parseInt(strNum);
        }
        catch (NumberFormatException | NullPointerException nfe)
        {
            return false;
        }
        return true;
        */
    }
    
    private void clearTextFields()
    {
        txtName.setText("");
        txtPPE.setText("");
        txtDuration.setText("");
        txtLevel.setText("");
        txtRange.setText("");
        txtSavingThrow.setText("");
        txtText.setText("");
        
    }
    
    private void cycleTextFields()
    {
        txtName.setDisable(!txtName.isDisable());
        txtPPE.setDisable(!txtPPE.isDisable());
        txtDuration.setDisable(!txtDuration.isDisable());
        txtLevel.setDisable(!txtLevel.isDisable());
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
        gameSpellsList.saveToXML();
        
    }
    
    @FXML
    private void cmdSaveAsAction(ActionEvent event)
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Select Psionic XML File");
        //fc.setInitialDirectory(new File("./res"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        
        //File returnVal = fc.showOpenDialog(skillStage);
        File returnVal = fc.showSaveDialog(spellStage);
        
        if (returnVal != null)
        {
            gameSpellsList.saveAs(returnVal.getPath());
            gameSpellsList.saveToXML();
            
        }
        else
        {
            System.out.println("Cancel selected.");
            
        }
        
        cmdSave.setDisable(false);
        
    }
    
    @FXML
    private void cmdExitAction() throws IOException
    {
        App.setRoot("primary");
        
    }
    
    @FXML
    private void cmdOpenAction() throws IOException
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Select Skill XML File");
        //fc.setInitialDirectory(new File("./res"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        
        File returnVal = fc.showOpenDialog(spellStage);
        
        if (returnVal != null)
        {
            gameSpellsList.openXML(returnVal.getPath());
            spellTree.setRoot(gameSpellsList.getRoot());
            spellTree.refresh();
            
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
        /*if (txtName.getText().isEmpty())
        {
            System.out.println("You need a name for the category");
            return;
            
        }
        */
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setContentText("Enter the name of the category:");
        inputDialog.setHeaderText("Name required");
        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent() && result.get().matches(regex))
        {
            spell newCat = new spell();
            newCat.generateUUID();
            newCat.setName(result.get());
            newCat.setCat(true);
            spellTree.getRoot().getChildren().add(new TreeItem(newCat));
            spellTree.refresh();
        }
    }
    
    @FXML
    private void cmdCancelAction() throws IOException
    {
        actionButtonsOn();
        spellTree.setDisable(false);
        handlerMode = editMode.READ;
        cycleTextFields();
        
    }
    
    @FXML
    private void cmdUpdateAction() throws IOException
    {
        spellTree.setDisable(true);
        actionButtonsOff();

        handlerMode = editMode.UPDATE;
        //clearTextFields();
        cycleTextFields();
        
    }
    
    @FXML
    private void cmdCommitAction() throws IOException
    {
        TreeItem<spell> currentSpell = spellTree.getTreeItem(spellTree.getSelectionModel().getSelectedIndex());
        
        switch (handlerMode)
        {
            case READ:
                return;
                
            case CREATE:
                // Add new spell
                System.out.println("Made it to create");
                if (areEntriesBad())
                {
                    System.out.println("Bad entries?");
                    return;

                }

                spell newSpell = new spell();
                newSpell.generateUUID();
                newSpell.setName(txtName.getText());
                newSpell.setPpe(txtPPE.getText());
                newSpell.setDuration(txtDuration.getText());
                newSpell.setRange(txtRange.getText());
                newSpell.setSavingThrow(txtSavingThrow.getText());
                newSpell.setLevel(txtLevel.getText());
                newSpell.setText(txtText.getText());

                
                if (currentSpell.getValue().isCat())
                {
                    TreeItem<spell> foundLevel = gameSpellsList.findLevelByCat(currentSpell.getValue().getName(), txtLevel.getText());
                    foundLevel.getChildren().add(new TreeItem(newSpell));
                }
                else if (currentSpell.getValue().isLevel())
                {
                    if (currentSpell.getValue().getName().matches(txtLevel.getText()))
                    {
                        currentSpell.getChildren().add(new TreeItem(newSpell));
                    } else
                    {
                        TreeItem<spell> foundLevel = gameSpellsList.findLevelByCat(currentSpell.getParent().getValue().getName(), txtLevel.getText());
                        foundLevel.getChildren().add(new TreeItem(newSpell));
                    }
                }
                else
                {
                    if (currentSpell.getParent().getValue().getName().matches(txtLevel.getText()))
                    {
                        currentSpell.getParent().getChildren().add(new TreeItem(newSpell));
                    } else
                    {
                        TreeItem<spell> foundLevel = gameSpellsList.findLevelByCat(currentSpell.getParent().getParent().getValue().getName(), txtLevel.getText());
                        foundLevel.getChildren().add(new TreeItem(newSpell));
                    }
                }

                spellTree.refresh();
                clearTextFields();
                /*cycleTextFields();
                selectedSpell = new spell();
                actionButtonsOn();
                spellTree.setDisable(false);
                handlerMode = editMode.READ;*/
                break;
                
            case UPDATE:
                // Update spell
                if (currentSpell.getValue().isCat())
                {
                    // TODO Add better error handling communication'
                    //System.out.println("Cannot have a Category selected");

                    selectedSpell = currentSpell.getValue();
                    selectedSpell.setName(txtName.getText());

                }
                else if (areEntriesBad())
                {
                    System.out.println("Text entries are incorrect");

                }
                else
                {
                    selectedSpell = currentSpell.getValue();

                    selectedSpell.setName(txtName.getText());
                    selectedSpell.setPpe(txtPPE.getText());
                    selectedSpell.setDuration(txtDuration.getText());
                    selectedSpell.setRange(txtRange.getText());
                    selectedSpell.setSavingThrow(txtSavingThrow.getText());
                    selectedSpell.setLevel(txtLevel.getText());
                    selectedSpell.setText(txtText.getText());

                }

                spellTree.refresh();
                clearTextFields();
                cycleTextFields();
                actionButtonsOn();
                spellTree.setDisable(false);
                handlerMode = editMode.READ;
                break;
                
            case DELETE:
                // Delete selected spell
                currentSpell.getParent().getChildren().remove(currentSpell);
                spellTree.refresh();
                clearTextFields();
                actionButtonsOn();
                spellTree.setDisable(false);
                handlerMode = editMode.READ;
                break;
        }
    }
    
    @FXML
    private void cmdAddAction() throws IOException
    {
        actionButtonsOff();
        cycleTextFields();
        spellTree.setDisable(true);
        handlerMode = editMode.CREATE;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectedSpell = new spell();
        
        gameSpellsList = new spellsCollection();
        spellTree.setShowRoot(false);
        spellTree.setRoot(gameSpellsList.getRoot());
        
        gameSpellsList.openXML("lib/spells.xml");
        spellTree.setRoot(gameSpellsList.getRoot());
        spellTree.refresh();
        handlerMode = editMode.READ;
        cycleTextFields(); // Should shut them all off to input
        cmdCancel.setDisable(true);
        cmdCommit.setDisable(true);
        
        spellTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<spell>>()
        {
            public void changed(ObservableValue<? extends TreeItem<spell>> changed, TreeItem<spell> oldVal, TreeItem<spell> newVal)
            {
                if (newVal != null)
                {
                    TreeItem<spell> currentSpell = spellTree.getTreeItem(spellTree.getSelectionModel().getSelectedIndex());
                    selectedSpell = currentSpell.getValue();

                    txtName.setText(selectedSpell.getName());
                    txtPPE.setText(selectedSpell.getPpe());
                    txtDuration.setText(selectedSpell.getDuration());
                    txtLevel.setText(selectedSpell.getLevel());
                    txtText.setText(selectedSpell.getText());
                    txtRange.setText(selectedSpell.getRange());
                    txtSavingThrow.setText(selectedSpell.getSavingThrow());
                    
                }
            }
        });

    }    
    
}
