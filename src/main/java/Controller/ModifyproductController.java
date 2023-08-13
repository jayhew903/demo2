package Controller;
/**This is the controller class for the modifying products*/

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.demo2.*;

import static javafx.collections.FXCollections.observableArrayList;

public class ModifyproductController implements Initializable {
    static int selectRow;

    /**
     * test data for the observable list. this date is not displayed
     */
    private Product prodToMod;
    // Product testProduct = new Product(1, "happy", 4.99, 4, 1, 6);
    ObservableList<Part> temp = observableArrayList();
    /**
     * table view for the current parts
     * will update if new parts are saved to the main page
     */
    @FXML
    private TableView<Part> partsTableView;
    /**
     * table view for parts in the product
     * these parts have to be added
     * will automatically display previously added parts
     */
    @FXML
    private TableView<Part> productPartsTableView;
    /**
     * search box for the part table
     */
    @FXML
    private TextField partSeachField;
    /**
     * the ID column for the parts. Displays the part ID the same from the main table
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**
     * the inventory column for parts. Displays the same inventory from the main table
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    /**
     * the name column for parts. Displays the same name from the main table
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * the price column for parts. Displays the same prices from the main table
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    /**
     * part ID column for parts added to the product
     */
    @FXML
    private TableColumn<Part, Integer> productPartIdCol;
    /**
     * parts inventory column for parts added to the product
     */
    @FXML
    private TableColumn<Part, Integer> productPartInvCol;
    /**
     * parts name column for parts added to the product
     */
    @FXML
    private TableColumn<Part, String> productPartNameCol;
    /**
     * the text field that will allow user to update the product ID
     */
    @FXML
    private TextField productIdTxtField;
    /**
     * the text field that will allow user to update the inventory level
     */
    @FXML
    private TextField productInvTxtField;
    /**
     * the text field that will allow user to update the max inventory level
     */
    @FXML
    private TextField productMaxTxtField;
    /**
     * the text field that will allow user to update the min inventory level
     */
    @FXML
    private TextField productMinTxtField;
    /**
     * text field that will allow user to update the name of the product
     */
    @FXML
    private TextField productNameTxField;
    /**
     * part cost column for parts added to the product.
     */
    @FXML
    private TableColumn<Part, Double> productPartCostCol;
    /**
     * text field that will allow user to update the product price
     */
    @FXML
    private TextField productPriceTxtField;
    /**
     * allows user to be able to search part based on part ID or name
     * @param event
     */
    @FXML
    void searchPart(ActionEvent event) {
        String q = partSeachField.getText();
        if (q.isBlank()) {
            partsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Part part = Inventory.lookupPart(Integer.parseInt(q));
            if (part != null) {
                partsTableView.getSelectionModel().select(part);
            } else {
                throw new NumberFormatException(); //not found try by name
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> parts = Inventory.lookupPart(q);
            if (parts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Part Not Found");
                alert.showAndWait();

            } else {
                partsTableView.setItems(parts);
            }

        }
    }

    /**
     * sets the product information from the information gathered from the main product table
     * @param index
     * @param product
     */
    public void setProduct(int index, Product product) {
        selectRow = index;
        prodToMod = product;
        productIdTxtField.setText(String.valueOf(product.getId()));
        productNameTxField.setText(product.getName());
        productInvTxtField.setText(String.valueOf(product.getStock()));
        productPriceTxtField.setText(String.valueOf(product.getPrice()));
        productMaxTxtField.setText(String.valueOf(product.getMax()));
        productMinTxtField.setText(String.valueOf(product.getMin()));

        // testProduct = product;
        // System.out.println(prodToMod.getName() + "**");
        // System.out.println(testProduct.getAssociatedParts().size());

        temp = observableArrayList(product.getallassociatedparts());
        productPartsTableView.setItems(temp);
        productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * this will add the parts from the part table to the parts product table
     * @param event
     */
    @FXML
    void addPartToProductBtn(ActionEvent event) {

        Part selectedItems = partsTableView.getSelectionModel().getSelectedItem();
        temp.add(selectedItems);
        //productPartsTableView.setItems(temp.getAssociatedParts());

    }

    /**
     * this will direct user to the main menu.
     * error because information will not automatically save
     * @param event
     * @throws IOException
     */
    @FXML
    void displayMainMenuBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/Controller/InventoryManagement.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * this will remove the part from the parts in product table
     * @param event
     */
    @FXML
    void removePartFromProductBtn(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete this part, do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedItems = productPartsTableView.getSelectionModel().getSelectedItem();
            temp.remove(selectedItems);

        }
    }

    /**
     *  this will save the updated product information
     *  will direct user to the main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void saveProductBtn(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(productIdTxtField.getText());
            String name = productNameTxField.getText();
            double price = Double.parseDouble(productPriceTxtField.getText());
            int stock = Integer.parseInt(productInvTxtField.getText());
            int max = Integer.parseInt(productMaxTxtField.getText());
            int min = Integer.parseInt(productMinTxtField.getText());
            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Stock must be higher than min and lower than max.");
                alert.showAndWait();
                return;
            }


            Inventory.updateProduct(selectRow, new Product(id, name, price, stock, min, max, temp));

            Parent root = FXMLLoader.load(getClass().getResource("/Controller/InventoryManagement.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }

    }

    /**
     * this allows the data from the main page to be automatically updated to the correct tables.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        productIdTxtField.setEditable(false);
        productIdTxtField.setDisable(true);


    }
}
