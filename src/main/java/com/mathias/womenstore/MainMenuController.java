package com.mathias.womenstore;

import com.mathias.womenstore.dao.*;
import com.mathias.womenstore.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private FlowPane fpCapital;

    @FXML
    private FlowPane fpCost;

    @FXML
    private FlowPane fpFilters;

    @FXML
    private FlowPane fpHeader;

    @FXML
    private FlowPane fpIncome;

    @FXML
    private FlowPane fpMainPage;

    @FXML
    private FlowPane fpOurProducts;

    @FXML
    private FlowPane fpStats;

    @FXML
    private FlowPane fpTitle;

    @FXML
    private HBox hBoxProductList;

    @FXML
    private Text txtCapital;

    @FXML
    private Text txtCost;

    @FXML
    private Text txtDiscover;

    @FXML
    private Text txtIncome;

    @FXML
    private Text txtOurProducts;

    @FXML
    private Text txtTitle;

    @FXML
    private Text txtFilterCategory;

    @FXML
    private ComboBox<String> cbCategories;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setShopDetails();
        cbCategories.setItems(FXCollections.observableArrayList("All Products", "Clothes", "Shoes", "Accessories"));
        cbCategories.getSelectionModel().selectFirst();
        onCategoryChange();
    }

    @FXML
    private void onCategoryChange() {
        refreshProducts();
    }

    public void refreshProducts() {
        hBoxProductList.getChildren().clear();
        List<? extends Product> products;
        switch (cbCategories.getValue()) {
            case "All Products":
                products = getProducts();
                break;
            case "Shoes":
                products = getShoes();
                break;
            case "Accessories":
                products = getAccessories();
                break;
            default:
                products = getClothes();
                break;
        }
        loadProducts(products);
    }

    private void loadProducts(List<? extends Product> products) {
        for (Product product : products) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainMenuController.class.getResource("product_card.fxml"));
            try {
                FlowPane fp = loader.load();
                ProductCardController productCardController = loader.getController();
                productCardController.initializeController(product, this);
                hBoxProductList.getChildren().add(fp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Product> getProducts() {
        return ProductDao.getProducts();
    }

    private List<Clothes> getClothes() {
        return ClothesDao.getClothes();
    }

    private List<Shoe> getShoes() {
        return ShoesDao.getShoes();
    }

    private List<Accessory> getAccessories() {
        return AccessoriesDao.getAccessories();
    }

    public void setShopDetails() {
        Shop shop = ShopDao.getShop();
        txtCapital.setText("Capital: " + shop.getCapital() + "€");
        txtCost.setText("Cost: " + shop.getCost() + "€");
        txtIncome.setText("Income: " + shop.getIncome() + "€");
    }

    @FXML
    private void onAddProductClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-product.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            AddProductController addProductController = loader.getController();
            addProductController.setMainMenuController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("My Window");
        stage.show();
    }
}