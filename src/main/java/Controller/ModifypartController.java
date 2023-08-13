package Controller;
/**This is the controller class for the modifying parts*/

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.demo2.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModifypartController implements Initializable {
    static int selectRow;
    /**
     * toggle group to make sure only one radio button can be selected
     */
    @FXML
    private ToggleGroup modPartTB;
    /**
     * outsources radio button to determine the part is an outsourced part
     */
    @FXML
    private RadioButton outsourcedRB;
    /**
     * in house radio button to determine the part is an in house part
     */
    @FXML
    private RadioButton inHouseRB;
    /**
     * text field for the part cost.
     * allows user to update the part cost
     */
    @FXML
    private TextField partCostTxt;
    /**
     * part ID text field
     * allows user to update the part ID
     */
    @FXML
    private TextField partIdTxt;
    /**
     * part inventory text field
     * allows user to update the inventory for the part
     */
    @FXML
    private TextField partInvTxt;
    /**
     * part max text field
     * allows user to update the max inventory for the part
     */
    @FXML
    private TextField partMaxTxt;
    /**
     * part min text field
     * allows user to update the min inventory for the part
     */
    @FXML
    private TextField partMinTxt;
    /**
     * part name text field
     * allows user to update the part name
     */
    @FXML
    private TextField partNameTxt;
    /**
     * special label changes to Machine ID or Company name based on RB
     */
    @FXML
    private Label partSpecialLbl;
    /**
     * part special text allows user to update the machine ID or company name based on RB
     */
    @FXML
    private TextField partSpecialTxt;

    /**
     * changes the special label to Machine ID if the in house RB is selected
     * @param event
     */
    @FXML
    void partHouseRB(ActionEvent event) {

        partSpecialLbl.setText("Machine ID");

    }

    /**
     * changes the special label to Company name if the outsourced RB is selected
     * @param event
     */
    @FXML
    void partOutRB(ActionEvent event) {

        partSpecialLbl.setText("Company Name");

    }

    /**
     * this displays the inventory management page
     * error since going back will not automatically save part information
     * @param event
     * @throws IOException
     */
    @FXML
    void DisplayMainMenu(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * this collects the data from the selected part to be imported to the modify part information
     * all data will automatically transfer to the user
     * @param index
     * @param part
     */
    public void setPart(int index, Part part) {
        selectRow = index;
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partCostTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        if (part instanceof InHouse) {
            partSpecialTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            partSpecialLbl.setText("Machine ID");
            inHouseRB.setSelected(true);

        }
        if (part instanceof OutSourced) {
            partSpecialTxt.setText(String.valueOf(((OutSourced) part).getCompanyName()));
            partSpecialLbl.setText("Company Name");
            outsourcedRB.setSelected(true);
        }
    }
    /**
     * this will save the update to the inventory management page
     * error in case the text fields are empty
     * user will be directed to the main page after the save
     * @param event
     * @throws IOException
     */
    @FXML
    void modPartSave(ActionEvent event) throws IOException {

        try {
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partCostTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Stock must be higher than min and lower than max.");
                alert.showAndWait();
                return;
            }
            if (outsourcedRB.isSelected()) {
                String companyName = partSpecialTxt.getText();
                Inventory.updatePart(selectRow, new OutSourced(Inventory.getPartId(), name, price, stock, min, max, companyName));

            } else {
                int machineId = Integer.parseInt(partSpecialTxt.getText());
                Inventory.updatePart(selectRow, new InHouse(Inventory.getPartId(), name, price, stock, min, max, machineId));

            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid fields");
            alert.showAndWait();
        }
        Parent root = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * sets the part ID to uneditable and disabled since part ID are unique and cannot be updated
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdTxt.setEditable(false);
        partIdTxt.setDisable(true);


    }

}

