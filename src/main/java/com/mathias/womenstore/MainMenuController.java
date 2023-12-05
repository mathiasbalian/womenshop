package com.mathias.womenstore;

import com.mathias.womenstore.dao.*;
import com.mathias.womenstore.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private Button btnApplyDiscount;

    @FXML
    private ComboBox<String> cbCategories;

    @FXML
    private ComboBox<String> cbCategoriesDiscount;

    @FXML
    private Slider sliderDiscount;

    private List<? extends Product> products = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setShopDetails();
        sliderDiscount.valueProperty().addListener((observableValue, oldValue, newValue) ->
                btnApplyDiscount.textProperty().setValue("Apply " + String.format("%.0f", newValue.floatValue()) + "%"));

        cbCategories.setItems(FXCollections.observableArrayList("All Products", "Clothes", "Shoes", "Accessories"));
        cbCategoriesDiscount.setItems(FXCollections.observableArrayList("All Products", "Clothes", "Shoes", "Accessories"));

        cbCategories.getSelectionModel().selectFirst();
        cbCategoriesDiscount.getSelectionModel().selectFirst();
        onCategoryChange();
    }

    @FXML
    private void onCategoryChange() {
        refreshProducts();
    }

    public void refreshProducts() {
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
        loadProducts();
    }

    private void loadProducts() {
        hBoxProductList.getChildren().clear();
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

    @FXML
    private void onApplyDiscountClick() {
        discount(false);
    }

    private void discount(boolean cancelDiscount) {
        Class<? extends Product> productsClass = switch (cbCategoriesDiscount.getValue()) {
            case "Shoes" -> Shoe.class;
            case "Clothes" -> Clothes.class;
            case "Accessories" -> Accessory.class;
            default -> Product.class;
        };
        for (Product product : ProductDao.getProducts()) {
            if (productsClass.isAssignableFrom(product.getClass())) {
                product.applyDiscount(cancelDiscount ? 1 : (1 - sliderDiscount.getValue() / 100));
                ProductDao.applyDiscount(product, cancelDiscount ? 1 : (1 - sliderDiscount.getValue() / 100));
            }
        }
        refreshProducts();
    }

    @FXML
    private void onCancelDiscountClick() {
        discount(true);
    }
}