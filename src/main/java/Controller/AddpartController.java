package Controller;

/**This class is the controller for the add part form */

import com.example.demo2.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.lang.String;
import java.net.URL;
import java.util.Optional;


import java.io.IOException;
import java.util.ResourceBundle;

public class AddpartController implements Initializable {
    /**
     *
     * this is the button to be able to switch between an In-House or an Outsourced part.
     */
    @FXML
    private RadioButton inHouseRB;
    /**
     * this is the button to be able to switch between an In-House or an OutSourced part.
     */
    @FXML
    private RadioButton outsourcedRB;
    /**
     * this is the label that displays "Price" on the table.
     */
    @FXML
    private Label partCostLbl;
    /**
     * text field that allows user to add the cost of the part.
     */
    @FXML
    private TextField partCostTxt;
    /**
     * this is outline for the add part form.
     */
    @FXML
    private GridPane partGridPane;
    /**
     * the label that displays "ID" on the form.
     */
    @FXML
    private Label partIdLbl;
    /**
     * text field that allows user to input the part ID.
     */
    @FXML
    private TextField partIdTxt;
    /**
     * label that displays "Inv" on form.
     */
    @FXML
    private Label partInvLbl;
    /**
     * text field that allows user to input the inventory for the part.
     */
    @FXML
    private TextField partInvTxt;
    /**
     *label that displays "max" on form.
     */
    @FXML
    private Label partMaxLbl;
    /**
     * text field that allows user to input the max inventory for the part.
     */
    @FXML
    private TextField partMaxTxt;
    /**
     * label that displays "min" on the form.
     */
    @FXML
    private Label partMinLbl;
    /**
     * text field that allows user to input the minimum inventory for the part.
     */
    @FXML
    private TextField partMinTxt;
    /**
     * label that displays "Name" on form.
     */
    @FXML
    private Label partNameLbl;
    /**
     * text field that allows user to input the part name.
     */
    @FXML
    private TextField partNameTxt;
    /**
     * this label will display "Machine ID" or "Company Name".
     */
    @FXML
    private Label partSpecialLbl;
    /**
     * text field that will allow user to input the machine id or the company name.
     */
    @FXML
    private TextField partSpecialTxt;
    /**
     * toggle group prevents allowing both in house and outsourced being selected at the same time.
     */
    @FXML
    private ToggleGroup partTG;

    /**
     * changes the Special label to Company name when the Outsourced radio button is selected.
     * @param event
     */
    @FXML
    void setLabelToCompanyName(ActionEvent event) {
        partSpecialLbl.setText("Company Name");


    }

    /**
     * this changes the special label to Machine Id when the In-House button is selected.
     * @param event
     */
    @FXML
    void setLabelToMachineID(ActionEvent event) {
        partSpecialLbl.setText("Machine ID");

    }

    /**
     * this button will direct user back to the inventory management page.
     * Error since going to the main screen will not automatically save the part.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

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
     * This will save the part to the inventory management table.
     * error to make sure all the textfields have correct information.
     * error to prevent current inventory from being more than the max and less than the min.

     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
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
                Inventory.addPart(new OutSourced(Inventory.getPartId(), name, price, stock, min, max, companyName));

            } else {
                int machineId = Integer.parseInt(partSpecialTxt.getText());
                Inventory.addPart(new InHouse(Inventory.getPartId(), name, price, stock, min, max, machineId));

            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid fields");
            alert.showAndWait();
            return;
        }


        Parent root = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this has the text field for part ID disable when this page is initiaalized.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

        partIdTxt.setEditable(false);
        partIdTxt.setDisable(true);


    }

}