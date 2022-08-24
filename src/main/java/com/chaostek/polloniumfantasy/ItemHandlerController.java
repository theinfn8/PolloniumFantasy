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
public class ItemHandlerController implements Initializable
{
    Stage itemStage;
    itemsCollection gameItemsList;
    item selectedItem;
    editMode handlerMode;
    //final String regex = "^[a-zA-Z0-9,+\\-\\s\\.\\(\\)\\/\\:\\;\\%\\']+$";
    
    @FXML TreeView itemTree;
    @FXML TextField txtName, txtPrice, txtCategory, txtWeight;
    @FXML TextArea txtText;
    
    @FXML Button cmdSave, cmdOpen, cmdExit, cmdCommit;
    @FXML Button cmdAdd, cmdUpdate, cmdRemove, cmdNewCat, cmdCancel, cmdClear;
    
    private boolean areEntriesBad()
    {
        if (txtName.getText().isEmpty() || !txtName.getText().matches(App.REGEX))
        { 
            //System.out.println("Name error");
            notification.generateErrorMessage("Input Error", "Name is empty or contains an illegal character");
            return true;
        }
        
        if (txtPrice.getText().isEmpty())
        {
            txtPrice.setText("0");
            
        }
        
        if (!txtPrice.getText().matches(App.REGEX))
        {
            System.out.println("Price Error");
        }
        
        if (txtCategory.getText().isEmpty())
        {
            if (gameItemsList.root.getChildren().isEmpty())
            {
                System.out.println("No category with an empty list");
                return true;
            }
            
            if (selectedItem.isCat())
            {
                txtCategory.setText(selectedItem.getName());
            }
            else
            {
                txtCategory.setText(selectedItem.getCategory());
            }
        }
        if (!txtCategory.getText().matches(App.REGEX))
        {
            System.out.println("Category error");
            return true;
        }
        
        if (txtWeight.getText().isEmpty())
        {
            txtWeight.setText("0");
        }
        
        if (!txtWeight.getText().matches(App.REGEX))
        {
            System.out.println("Weight Error");
            return true;
        }
        
        if (txtText.getText() == null) txtText.setText(txtName.getText());
        if (txtText.getText().isEmpty()) txtText.setText(txtName.getText());
        
        return false;
    }
    
    private void clearTextFields()
    {
        txtName.setText("");
        txtPrice.setText("");
        txtCategory.setText("");
        txtWeight.setText("");
        txtText.setText("");
        
    }
    
    private void cycleTextFields()
    {
        txtName.setDisable(!txtName.isDisable());
        txtPrice.setDisable(!txtPrice.isDisable());
        txtCategory.setDisable(!txtCategory.isDisable());
        txtWeight.setDisable(!txtWeight.isDisable());
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
        gameItemsList.saveToXML();
        
    }
    
    @FXML
    private void cmdSaveAsAction(ActionEvent event)
    {
        File returnVal = notification.getSaveAsFile(itemStage, "Select an Item XML File", "XML file", "*.xml");
        
        if (returnVal != null)
        {
            gameItemsList.saveAs(returnVal.getPath());
            gameItemsList.saveToXML();
            
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
        App.setRoot("dataeditor");
        
    }
    
    @FXML
    private void cmdOpenAction() throws IOException
    {
        File returnVal = notification.getFile(itemStage, "Select an Item XML File", "XML file", "*.xml");
        
        if (returnVal != null)
        {
            gameItemsList.openXML(returnVal.getPath());
            itemTree.setRoot(gameItemsList.getRoot());
            itemTree.refresh();
            
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
            item newCat = new item();
            newCat.setName(result.get());
            newCat.setCategory(result.get());
            itemTree.getRoot().getChildren().add(new TreeItem(newCat));
            itemTree.refresh();
        }
    }
    
    @FXML
    private void cmdCancelAction() throws IOException
    {
        actionButtonsOn();
        itemTree.setDisable(false);
        handlerMode = editMode.READ;
        cycleTextFields();
        
    }
    
    @FXML
    private void cmdUpdateAction() throws IOException
    {
        if (gameItemsList.root.getChildren().isEmpty())
        {
            System.out.println("Nothing to update");
            return;
        }
        
        itemTree.setDisable(true);
        actionButtonsOff();

        handlerMode = editMode.UPDATE;
        //clearTextFields();
        cycleTextFields();
        
    }
    
    @FXML
    private void cmdCommitAction() throws IOException
    {
        TreeItem<item> currentItem = itemTree.getTreeItem(itemTree.getSelectionModel().getSelectedIndex());
        
        switch (handlerMode)
        {
            case READ:
                return;
                
            case CREATE:
                // Add new item
                if (gameItemsList.root.getChildren().isEmpty() && (txtCategory.getText().isBlank() || txtCategory.getText().isEmpty()))
                {
                    System.out.println("Can't create when empty and with no category");
                    return;
                }
                
                if (areEntriesBad())
                {
                    System.out.println("Bad entries?");
                    return;

                }

                item newItem = new item();
                newItem.generateUUID();
                newItem.setName(txtName.getText());
                newItem.setPrice(txtPrice.getText());
                newItem.setCategory(txtCategory.getText());
                newItem.setWeight(txtWeight.getText());
                newItem.setDescription(txtText.getText());

                TreeItem<item> foundCat = gameItemsList.findCat(newItem.getCategory());
                foundCat.getChildren().add(new TreeItem(newItem));
                
                itemTree.refresh();
                clearTextFields();
                break;
                
            case UPDATE:
                // Update item
                
                if (areEntriesBad())
                {
                    System.out.println("Text entries are incorrect");
                    return;
                }
                
                if (currentItem.getValue().getCategory().matches(txtCategory.getText()))
                {
                    selectedItem = currentItem.getValue();

                    selectedItem.setName(txtName.getText());
                    selectedItem.setPrice(txtPrice.getText());
                    selectedItem.setCategory(txtCategory.getText());
                    selectedItem.setWeight(txtWeight.getText());
                    selectedItem.setDescription(txtText.getText());
                    
                } else
                {
                    TreeItem<item> newCategory = gameItemsList.findCat(currentItem.getValue().getCategory());
                    item updateItem = new item();

                    updateItem.setId(currentItem.getValue().getId());
                    updateItem.setName(txtName.getText());
                    updateItem.setPrice(txtPrice.getText());
                    updateItem.setCategory(txtCategory.getText());
                    updateItem.setWeight(txtWeight.getText());
                    updateItem.setDescription(txtText.getText());

                    newCategory.getChildren().add(new TreeItem(updateItem));
                    currentItem.getParent().getChildren().remove(currentItem);

                }

                itemTree.refresh();
                clearTextFields();
                cycleTextFields();
                actionButtonsOn();
                itemTree.setDisable(false);
                handlerMode = editMode.READ;
                break;
                
            case DELETE:
                // Delete selected item
                currentItem.getParent().getChildren().remove(currentItem);
                itemTree.refresh();
                clearTextFields();
                actionButtonsOn();
                itemTree.setDisable(false);
                handlerMode = editMode.READ;
                break;
        }
    }
    
    @FXML
    private void cmdAddAction() throws IOException
    {
        actionButtonsOff();
        cycleTextFields();
        clearTextFields();
        itemTree.setDisable(true);
        handlerMode = editMode.CREATE;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectedItem = new item();
        
        gameItemsList = new itemsCollection();
        itemTree.setShowRoot(false);
        itemTree.setRoot(gameItemsList.getRoot());
        
        gameItemsList.openXML("lib/items.xml");
        itemTree.setRoot(gameItemsList.getRoot());
        itemTree.refresh();
        handlerMode = editMode.READ;
        cycleTextFields(); // Should shut them all off to input
        cmdCancel.setDisable(true);
        cmdCommit.setDisable(true);
        
        itemTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<item>>()
        {
            public void changed(ObservableValue<? extends TreeItem<item>> changed, TreeItem<item> oldVal, TreeItem<item> newVal)
            {
                if (newVal != null)
                {
                    TreeItem<item> currentItem = itemTree.getTreeItem(itemTree.getSelectionModel().getSelectedIndex());
                    selectedItem = currentItem.getValue();

                    txtName.setText(selectedItem.getName());
                    txtPrice.setText(selectedItem.getPrice());
                    txtCategory.setText(selectedItem.getCategory());
                    txtWeight.setText(selectedItem.getWeight());
                    txtText.setText(selectedItem.getDescription());
                    
                }
            }
        });

    }    
    
}
