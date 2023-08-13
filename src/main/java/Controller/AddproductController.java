package Controller;

/**this is the controller class for the adding product form. */


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.demo2.*;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
//import static Model.DataProvider.allParts;


public class AddproductController implements Initializable {
    /**
     * this test product is used for the add associated parts observable array list, adds data that will not be used.
     */
    Product testProduct = new Product(1,"happy", 4.99, 4, 1, 6);


    /**
     * this is the parts Table View, which will hold the same part information as the main inventory page.
     */
    @FXML
    private TableView<Part> PartsTableView;
    /**
     * this text field allows user to search for a specific part.
     */
    @FXML
    private TextField partSearchField;
    /**
     * this is the colum in the part table view that displays the cost of the part.
     */
    @FXML
    private TableColumn<Part, Double> partCostCol;
    /**
     * this is the part ID column that displays the part ID.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**
     * this is the part inventory column that displays the inventory of the part.
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    /**
     * this is the part name column that displays the name of the part.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * this is the part cost column that has been added to the product. Displays the part cost.
     */
    @FXML
    private TableColumn<Part, String> productPartCostCol;
    /**
     * this is the part id column for parts added to the product. Displays the part ID.
     */
    @FXML
    private TableColumn<Part, Integer> productPartIdCol;
    /**
     * this is the part inventory column for part added to the product. Displays the part inventory.
     */
    @FXML
    private TableColumn<Part, Integer> productPartInvCol;
    /**
     * this is the part name column for parts added to the product. Displays part name.
     */
    @FXML
    private TableColumn<Part, String> productPartNameCol;
    /**
     * this is the parts that are added to the product table view.
     */
    @FXML
    private TableView<Part> productPartsTableView;
    /**
     * product ID text fields. Allows user to add the ID of a product.
     */
    @FXML
    private TextField productIdTxt;
    /**
     * Product inventory text field. Allows user to add the inventory of the product.
     */
    @FXML
    private TextField productInvTxt;
    /**
     * product max text field. Allows user to input the maximum inventory for the product.
     */
    @FXML
    private TextField productMaxTxt;
    /**
     * product min text field. Allows user to input the minimum inventory for the product.
     */
    @FXML
    private TextField productMinTxt;
    /**
     * product name text field. Allows user to input a name for the product.
     */
    @FXML
    private TextField productNameTxt;
    /**
     * product price text field. Allows user to input a price for the product.
     */
    @FXML
    private TextField productPriceTxt;

    /**
     * This is for the search bar. Allows user to search the part information based on name or ID.
     * sets error is the part is unable to be found.
     * @param event
     */
    @FXML
    void searchParts(ActionEvent event) {

        String q = partSearchField.getText();
        if(q.isBlank()){
            PartsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Part part = Inventory.lookupPart(Integer.parseInt(q));
            if(part != null){
                PartsTableView.getSelectionModel().select(part);
            }
            else
            {
                throw new NumberFormatException(); //not found try by name
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> parts = Inventory.lookupPart(q);
            if(parts.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Part Not Found");
                alert.showAndWait();

            }
            else{
                PartsTableView.setItems(parts);
            }

        }

    }

    /**
     * Adds the part to the current product's part list.
     * @param event
     */
    @FXML
    void addPartToProduct(ActionEvent event)  {


        Part selectedItems = PartsTableView.getSelectionModel().getSelectedItem();
        testProduct.addAssociatedPart(selectedItems);
        productPartsTableView.setItems(testProduct.getallassociatedparts());
    }


    /**
     * selecting this will direct the user to the main page.
     * Shows error, since going to the main page will delete product information that has not been saved.
     * @param event
     * @throws IOException
     */
    @FXML
    void displayMainMenu(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            Parent root = FXMLLoader.load(getClass().getResource("/Controller/InventoryManagement.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * this button will remove the associated part from the specific product.
     * has confirmation button to make sure the part is not deleted accidentally.
     * @param event
     */
    @FXML
    void removePartFromProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete this part, do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedItems = productPartsTableView.getSelectionModel().getSelectedItem();
            testProduct.deleteAssociatedPart(selectedItems);
        }
    }

    /**
     * this will save the product to the main inventory page.
     * user will be automatically directed to the inventory management page.
     * if stock is higher than max or lower than minimum there will be an error pop-up.
     * @param event
     * @throws IOException
     */
    @FXML
    void saveProduct(ActionEvent event) throws IOException{

        try {
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());

            if(stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Stock must be higher than min and lower than max.");
                alert.showAndWait();
                return;
            }


            Inventory.addProduct(new Product(Inventory.getProductId(), name, price, stock, min, max, testProduct.getallassociatedparts()));

            Parent root = FXMLLoader.load(getClass().getResource("/Controller/InventoryManagement.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }
    }


    /**
     * this displays the parts table as well as the parts associated list table.
     * the parts table automatically updates as parts are added to the inventory management list.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        PartsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * disables the product ID text field and makes it where it cannot be edited.
         * this is an auto generated ID and does not need to be inputted.
         */

        productIdTxt.setEditable(false);
        productIdTxt.setDisable(true);

    }
}

