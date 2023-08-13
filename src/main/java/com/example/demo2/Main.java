/**
 * RUNTIME ERROR: Originally when modifying the part I did not correctly set it up.
 * Instead of just updating the part, I was adding an additional part with different information.
 * After some help with the CI I was able to correctly add update part to the inventory class and fix that error.
 * FUTURE ENHANCEMENT: Already add associated parts to the sample data products.
 * JavaDocs is located: C:\Users\CHEWI\IdeaProjects\JHInventory\src\main\java\Controller
 */
package com.example.demo2;
/**This class will display an inventory managment program. */

import com.example.demo2.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
/**This is the main method. This is the first method that gets called when you run for java program.*/
public class Main extends Application  {

    /**
     * sets the start page as the inventory management page.
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 699, 400));
        primaryStage.show();

    }

    /**
     * this is the test data for the part and product tables in the main page.
     * located here to make sure the information is only added once when the program launches.
     * @param args
     */
    public static void main(String[] args) {

        InHouse inHouse1 = new InHouse(Inventory.getPartId(), "tire", 9.99, 10, 2, 20, 2);
        InHouse inHouse2 = new InHouse(Inventory.getPartId(),"chain", 4.99, 3, 1, 6, 3);
        OutSourced outSourced1 = new OutSourced((Inventory.getPartId()),"Screw", 1.09, 30,1,50,"Bob's Part Shack");

        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(outSourced1);

        Product product1 = new Product(Inventory.getProductId(), "Sally Bike", 24.99, 5, 1, 7);
        Product product2 = new Product(Inventory.getProductId(), "Mike Bike", 24.99, 5, 1, 7);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);


/**
 * launches the application.
 */
        launch(args);

    }
}