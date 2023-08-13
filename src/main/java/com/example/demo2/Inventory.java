package com.example.demo2;


/**
 * inventory class
 * this class holds most of the observable lists
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {
    /**
     * this sets the initial part and product id as 1
     */
    private static int partId = 1;
    private static int productId = 100;
    /**
     * the observable lists for parts and products
     * this is where all the data is loaded
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * this will provide the ID for the part
     * automatically increases the part ID by 1
     * @return
     */
    public static int getPartId() {
        return partId++;
    }

    /**
     * this will provide the ID for product
     * automatically increases the product ID by 1
     * @return
     */
    public static int getProductId() {
        return productId++;
    }

    /**
     * add part, by calling this will add the part information to the part observable lisy
     * @param part
     */
    public static void addPart(Part part)
    {
        allParts.add(part);
    }

    /**
     * add product, by calling this will add the product information to the product observable list
     * @param product
     */
    public static void addProduct(Product product)
    {
        allProducts.add(product);
    }

    /**
     * this gets all the parts that are in the parts observable list
     * @return
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * this gets all the products in the product observable list
     * @return
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    /**
     * this will update the selected part with new information provided
     * @param index
     * @param selectPart
     */
    public static void updatePart(int index, Part selectPart)
    {
        allParts.set(index, selectPart);
    }

    /**
     * this will delete the selected part from the list
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }

    /**
     * this will update the selected product with the new information
     * @param index
     * @param selectProduct
     */
    public static void updateProduct(int index, Product selectProduct)
    {
        allProducts.set(index, selectProduct);
    }

    /**
     * this will delete the product from the list
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }

    /**
     * this allows us to call to look up the product based on the ID
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId)
    {
        for(int i = 0; i < allProducts.size(); i++){
            Product product = allProducts.get(i);

            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     * this allows us to call to look up the part based on the ID
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId)
    {
        for(int i = 0; i < allParts.size(); i++){
            Part part = allParts.get(i);

            if(part.getId() == partId){
                return part;
            }
        }
        return null;

    }

    /**
     * this allows us to call to look up the product based on a partial name match and display that new list
     * @param partialProductName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String partialProductName)
    {
        ObservableList<Product> nameProduct = FXCollections.observableArrayList();

        for (Product product: allProducts){
            if(product.getName().toLowerCase().contains(partialProductName.toLowerCase())){
                nameProduct.add(product);
            }
        }
        return nameProduct;
    }

    /**
     * this allows us to look up a part based on a partial name match and display that new list
     * @param partialPartName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partialPartName)
    {
        ObservableList<Part> namePart = FXCollections.observableArrayList();

        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(partialPartName.toLowerCase())) {
                namePart.add(part);
            }

        }

        return namePart;
    }

}

