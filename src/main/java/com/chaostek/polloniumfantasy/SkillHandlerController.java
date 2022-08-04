package com.chaostek.polloniumfantasy;

import java.io.IOException;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author chaosburn
 */
public class SkillHandlerController implements Initializable {
    Stage skillStage;
    bookList gameBooks;
    skillsCollection gameSkillsList;
    skill selectedSkill;
    
    @FXML ToolBar barMain;
    @FXML Button cmdOpen, cmdSave, cmdSaveAs, cmdExit;
    
    @FXML Button cmdClear, cmdRemove, cmdNewCat, cmdLoad, cmdUpdate, cmdAdd, cmdAddSub, cmdRemoveSub, cmdModifySub;
    @FXML ComboBox<book> cboSourceBooks;
    
    @FXML TextField txtName, txtBase, txtPerLevel, txtBonuses;
    @FXML TextField txtSubName, txtSubBase, txtSubPerLevel;
    
    @FXML TextArea txtDescription;
    @FXML TreeView skillTree;
    
    @FXML TableView<subSkill> subSkillTable;
    @FXML TableColumn<subSkill, String> subSkillName;
    @FXML TableColumn<subSkill, Integer> subSkillBase, subSkillPerLevel;
    
    @FXML
    private void cmdExitAction(ActionEvent event) throws IOException
    {
        App.setRoot("primary");
        
    }
    
    @FXML
    private void cmdOpenAction(ActionEvent event)
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Select Skill XML File");
        //fc.setInitialDirectory(new File("./res"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        
        File returnVal = fc.showOpenDialog(skillStage);
        
        if (returnVal != null)
        {
            gameSkillsList.openXML(returnVal.getPath());
            skillTree.setRoot(gameSkillsList.getRoot());
            skillTree.refresh();
            
        }
        else
        {
            System.out.println("Cancel selected.");
        }
        
        cmdSave.setDisable(false);
        
    }
    
    @FXML
    private void cmdSaveAction(ActionEvent event)
    {
        gameSkillsList.saveToXML();
        
    }
    
    @FXML
    private void cmdSaveAsAction(ActionEvent event)
    {
        final FileChooser fc = new FileChooser();
        fc.setTitle("Select Skill XML File");
        //fc.setInitialDirectory(new File("./res"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        
        //File returnVal = fc.showOpenDialog(skillStage);
        File returnVal = fc.showSaveDialog(skillStage);
        
        if (returnVal != null)
        {
            gameSkillsList.saveAs(returnVal.getPath());
            gameSkillsList.saveToXML();
            
        }
        else
        {
            System.out.println("Cancel selected.");
            
        }
        
        cmdSave.setDisable(false);
        
    }
    
    @FXML
    private void cmdAddSubAction(ActionEvent event)
    {
        if (selectedSkill == null) { return; }
        subSkillTable.getItems().add(new subSkill(txtSubName.getText(), Integer.parseInt(txtSubBase.getText()), Integer.parseInt(txtSubPerLevel.getText())));
        selectedSkill.addSubSkill(txtSubName.getText(), Integer.parseInt(txtSubBase.getText()), Integer.parseInt(txtSubPerLevel.getText()));
        txtSubName.clear();
        txtSubBase.clear();
        txtSubPerLevel.clear();
        subSkillTable.refresh();
        
    }
    
    @FXML
    private void cmdRemoveSubAction(ActionEvent event)
    {
        if (subSkillTable.getSelectionModel().getSelectedIndex() > -1)
        {
            subSkill sub = subSkillTable.getSelectionModel().getSelectedItem();
            subSkillTable.getItems().remove(sub);
            selectedSkill.removeSubSkill(sub);
            
        }

    }
    
    @FXML
    private void cmdModifySubAction(ActionEvent event)
    {
        if (selectedSkill == null) { return; }
        
        if (subSkillTable.getSelectionModel().getSelectedIndex() > -1)
        {
            // Grab the current subskill so we can use it's name to find it
            subSkill sub = subSkillTable.getSelectionModel().getSelectedItem();
            // Update through the skill so we can verify that the subskill is in the skill
            // It should be, but who knows? 
            selectedSkill.updateSubSkill(sub.getName(), txtSubName.getText(), Integer.parseInt(txtSubBase.getText()), Integer.parseInt(txtSubPerLevel.getText()));
            //subSkillTable.getItems().remove(sub);
            //selectedSkill.removeSubSkill(sub);
            
        }
        
        txtSubName.clear();
        txtSubBase.clear();
        txtSubPerLevel.clear();
        subSkillTable.refresh();
        
    }
    
    @FXML
    private void cmdRemoveAction(ActionEvent event)
    {
        TreeItem<skill> currentSkill = skillTree.getTreeItem(skillTree.getSelectionModel().getSelectedIndex());
        currentSkill.getParent().getChildren().remove(currentSkill);
        skillTree.refresh();
            
    }
    
    @FXML
    private void cmdAddAction(ActionEvent event)
    {
        TreeItem<skill> currentSkill = skillTree.getTreeItem(skillTree.getSelectionModel().getSelectedIndex());
        
        if (checkRequiredTextIsBad())
        {
            return;
            
        }
        
        skill newSkill = new skill();
        newSkill.generateUUID();
        newSkill.setName(txtName.getText());
        newSkill.setBasePercent(txtBase.getText());
        newSkill.setPerLevel(txtPerLevel.getText());
        newSkill.setBonuses(txtBonuses.getText());
        
        book tempBook = cboSourceBooks.getValue();
        newSkill.setBook(tempBook.getISBN());
        newSkill.setText(txtDescription.getText());
        
        newSkill.setSubSkills(subSkillTable.getItems());
        
        if (currentSkill.getValue().getIsCat())
        {
            currentSkill.getChildren().add(new TreeItem(newSkill));
        }
        else
        {
            currentSkill.getParent().getChildren().add(new TreeItem(newSkill));
        }
        
        skillTree.refresh();
        
        clearTextFields();
        selectedSkill = new skill();
        
    }
    
    @FXML
    private void cmdLoadAction(ActionEvent event)
    {
        TreeItem<skill> currentSkill = skillTree.getTreeItem(skillTree.getSelectionModel().getSelectedIndex());
        selectedSkill = currentSkill.getValue();
        
        txtName.setText(selectedSkill.name);
        txtBase.setText(String.valueOf(selectedSkill.basePercent));
        txtPerLevel.setText(String.valueOf(selectedSkill.perLevel));
        txtBonuses.setText(selectedSkill.getBonuses());
        
        //cboSourceBooks.setSelectedItem(gameBooksList.getBookByISBN(selectedSkill.book));
        cboSourceBooks.getSelectionModel().select(gameBooks.getBookByISBN(selectedSkill.book));
        
        subSkillTable.getItems().setAll(selectedSkill.getSubSkills());
        subSkillTable.refresh();
        
        txtDescription.setText(selectedSkill.text);
        
        cmdAddSub.setDisable(false);
        cmdRemoveSub.setDisable(false);
        cmdModifySub.setDisable(false);
        
    }
    
    @FXML
    private void cmdUpdateAction(ActionEvent event)
    {
        TreeItem<skill> currentSkill = skillTree.getTreeItem(skillTree.getSelectionModel().getSelectedIndex());
        if (currentSkill.getValue().getIsCat())
        {
            System.out.println("Cannot have a Category selected");
            
            selectedSkill = currentSkill.getValue();
            selectedSkill.setName(txtName.getText());
            
        }
        else if (checkRequiredTextIsBad())
        {
            System.out.println("Text entries are incorrect");
            
        }
        else
        {
            selectedSkill = currentSkill.getValue();
            
            selectedSkill.setName(txtName.getText());
            selectedSkill.setBasePercent(txtBase.getText());
            selectedSkill.setPerLevel(txtPerLevel.getText());
            selectedSkill.setBonuses(txtBonuses.getText());
            
            book tempBook = (book) cboSourceBooks.getValue();
            selectedSkill.setBook(tempBook.getISBN());
            
            selectedSkill.setSubSkills(subSkillTable.getItems());

            selectedSkill.setText(txtDescription.getText());
        }
        
        skillTree.refresh();
        
    }
    
    @FXML
    private void cmdNewCatAction(ActionEvent event)
    {
        if (txtName.getText().isEmpty())
        {
            System.out.println("You need a name for the category");
            return;
            
        }
        
        skill newCat = new skill();
        newCat.generateUUID();
        newCat.setName(txtName.getText());
        newCat.setIsCat(true);
        skillTree.getRoot().getChildren().add(new TreeItem(newCat));
        skillTree.refresh();
        
    }
    
    @FXML
    private void cmdClearAction(ActionEvent event)
    {
        clearTextFields();
        
        cmdAddSub.setDisable(true);
        cmdRemoveSub.setDisable(true);
        cmdModifySub.setDisable(true);
        
    }
    
    public void setMyStage(Stage myStage)
    {
        skillStage = myStage;
        
    }
    
    private void clearTextFields()
    {
        txtName.setText("");
        txtBase.setText("");
        txtPerLevel.setText("");
        txtBonuses.setText("");
        cboSourceBooks.getSelectionModel().selectFirst();
        txtDescription.setText("");
        subSkillTable.getItems().clear();
        
    }
        
    private boolean checkRequiredTextIsBad()
    {
        if (txtName.getText().isEmpty())
        {
            //lblMessage.setText("Skills must have a name"); // Message box?
            return true;
                
        }
        
        if (txtBase.getText().isEmpty()) txtBase.setText("0");

        if (!isNumeric(txtBase.getText()))
        {
            return true;
        }
        
        if (txtPerLevel.getText().isEmpty()) txtPerLevel.setText("0");
        
        if (!isNumeric(txtPerLevel.getText()))
        {
            return true;
        }
        
        if (txtBonuses.getText().isEmpty()) txtBonuses.setText("NONE");
        
        if (txtDescription.getText().isEmpty()) txtDescription.setText(txtName.getText());
        
        return false;
    }
    
    private boolean isNumeric(String strNum)
    {
        try
        {
            int i = Integer.parseInt(txtBase.getText());
        }
        catch (NumberFormatException | NullPointerException nfe)
        {
            return false;
        }
        return true;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectedSkill = new skill();
        
        gameBooks = new bookList();
        gameBooks.openXML();
        
        gameSkillsList = new skillsCollection();
        skillTree.setShowRoot(false);
        skillTree.setRoot(gameSkillsList.getRoot());
        
        gameSkillsList.openXML("lib/PF-Core.xml");
        skillTree.setRoot(gameSkillsList.getRoot());
        skillTree.refresh();
            
        subSkill tmpSub = new subSkill("Test", 30, 5);
        
        subSkillName.setCellValueFactory(new PropertyValueFactory<>("name"));
        subSkillBase.setCellValueFactory(new PropertyValueFactory<>("base"));
        subSkillPerLevel.setCellValueFactory(new PropertyValueFactory<>("perLevel"));
        //subSkillTable.getItems().add(tmpSub);
        
        
        cboSourceBooks.setItems(gameBooks.getBooks());
        cboSourceBooks.getSelectionModel().selectFirst();
        
        cmdSave.setDisable(true);
        cmdAddSub.setDisable(false);
        cmdRemoveSub.setDisable(false);
        cmdModifySub.setDisable(false);
        
        skillTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<skill>>() {
            public void changed(ObservableValue<? extends TreeItem<skill>> changed, TreeItem<skill> oldVal, TreeItem<skill> newVal)
            {
                if (newVal != null)
                {
                    TreeItem<skill> currentSkill = skillTree.getTreeItem(skillTree.getSelectionModel().getSelectedIndex());
                    selectedSkill = currentSkill.getValue();

                    txtName.setText(selectedSkill.name);
                    txtBase.setText(String.valueOf(selectedSkill.basePercent));
                    txtPerLevel.setText(String.valueOf(selectedSkill.perLevel));
                    txtBonuses.setText(selectedSkill.getBonuses());

                    //cboSourceBooks.setSelectedItem(gameBooksList.getBookByISBN(selectedSkill.book));
                    cboSourceBooks.getSelectionModel().select(gameBooks.getBookByISBN(selectedSkill.book));

                    subSkillTable.getItems().setAll(selectedSkill.getSubSkills());
                    subSkillTable.refresh();

                    txtDescription.setText(selectedSkill.text);

                    cmdAddSub.setDisable(false);
                    cmdRemoveSub.setDisable(false);
                    cmdModifySub.setDisable(false);
                    
                }
            }
        });
        
    }    
    
}
