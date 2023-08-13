package Controller;


/**This is the controller class for the main inventory page. */

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.example.demo2.*;
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

import static javafx.collections.FXCollections.observableArrayList;


public class InventoryManagementController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Displays the data for the parts in inventory (sample data) and those added as well.
     * connects table to the part observable list.
     */
    @FXML
    private TableView<Part> imsPartTableView;
    /**
     * Displays the data for the products in inventory (sample data) and those added as well.
     * connects the table to the product observable list.
     */

    @FXML
    private TableView<Product> imsProductTableView;
    /**
     * the search box that will search for parts in the table based on name and ID.
     */
    @FXML
    private TextField imsPartSearch;

    /**
     * the search box that will search for products in the table based on name and ID.
     */
    @FXML
    private TextField imsProductSearch;

    /**
     * this will direct user to the Add part form and allow the user to add a new part to the table.
     */
    @FXML
    private Button partAddBtn;
    /**
     * displays the ID for the part in the part table.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    /**
     * displays the inventory for the part in the part table.
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    /**
     * displays the name for the part in the part table.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;
    /**
     * displays the price for the part in the part table.
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    /**
     * displays the ID for the product in the product table.
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    /**
     * displays the inventory for the product in the product table.
     */
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    /**
     * displays the name for the product in the product table.
     */
    @FXML
    private TableColumn<Product, String> productNameCol;
    /**
     * displays the price for the product in the product table.
     */
    @FXML
    private TableColumn<Product, Double> productPriceCol;


    /**
     * this will direct user to the add part form, so user can add new part information.
     * @param event
     * @throws IOException
     */
    @FXML
    void addPartBtn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * this will direct user to the add product form, so they can add new product information.
     * @param event
     * @throws IOException
     */
    @FXML
    public void addProductBtn(ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * this will delete the part from the table.
     * error will show to confirm the deletion so a part is not deleted accidentally.
     * @param event
     * @throws IOException
     */
    @FXML
    void deletePartBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete this part, do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedItems = imsPartTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedItems);

        }

    }

    /**
     * this will delete the product from the table.
     * error will show to confirm deletion to make sure product is not deleted by accident.
     * @param event
     * @throws IOException
     */
    @FXML
    void deleteProductBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete this part, do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product selectedItems = imsProductTableView.getSelectionModel().getSelectedItem();
            if(selectedItems.getallassociatedparts().isEmpty()) {
                Inventory.deleteProduct(selectedItems);
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error Dialog");
                alert1.setContentText("Please remove all associated parts to delete this product");
                alert1.showAndWait();
            }
        }

    }

    /**
     * this will exit the program.
     * @param event
     */
    @FXML
    void imsExitBtn(ActionEvent event) {

        System.exit(0);

    }

    /**
     * this will direct user to modify part form.
     * error to make sure a part is selected, the new page will not populate without a part being selected.
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyPartBtn(ActionEvent event) throws IOException {

        if(imsPartTableView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPart.fxml"));
            loader.load();

            ModifypartController MPController = loader.getController();
            MPController.setPart(imsPartTableView.getSelectionModel().getSelectedIndex(), imsPartTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        }

    }
    /**
     * this will direct user to modify product form.
     * error to make sure a product is selected, the new page will not populate without a product being selected.
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyProductBtn(ActionEvent event) throws IOException {

        if(imsProductTableView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
            loader.load();

            ModifyproductController MPController = loader.getController();
            MPController.setProduct(imsProductTableView.getSelectionModel().getSelectedIndex(), imsProductTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * this will search for parts in the table by name or ID.
     * @param event
     */
    @FXML
    void searchParts(ActionEvent event) {
        String q = imsPartSearch.getText();
        if(q.isBlank()){
            imsPartTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Part part = Inventory.lookupPart(Integer.parseInt(q));
            if(part != null){
                imsPartTableView.getSelectionModel().select(part);
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
                imsPartTableView.setItems(parts);
            }

        }

    }

    /**
     * this will search product in the table based on name and ID.
     * @param event
     */
    @FXML
    void searchProducts(ActionEvent event) {
        String q = imsProductSearch.getText();
        if(q.isBlank()){
            imsProductTableView.setItems(Inventory.getAllProducts());
            return;
        }
        try{
            Product product = Inventory.lookupProduct(Integer.parseInt(q));
            if(product != null){
                imsProductTableView.getSelectionModel().select(product);
            }
            else
            {
                throw new NumberFormatException(); //not found try name
            }
        } catch (NumberFormatException e){
            ObservableList<Product> products = Inventory.lookupProduct(q);
            if(products.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Product Not Found");
                alert.showAndWait();

            }
            else
                imsProductTableView.setItems(products);

        }


    }

    /**
     * this will initialize the page with the inputted sample data.
     * Sets the table information to the part and product information from the main page.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imsPartTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        imsProductTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        System.out.println("......." + Inventory.getAllProducts().get(0).getallassociatedparts().size());

    }

}







