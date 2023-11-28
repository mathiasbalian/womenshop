package com.mathias.womenstore;

import com.mathias.womenstore.dao.AccessoriesDao;
import com.mathias.womenstore.dao.ClothesDao;
import com.mathias.womenstore.dao.ShoesDao;
import com.mathias.womenstore.model.Accessory;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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

    private final ShoesDao shoesDao = new ShoesDao();
    private final ClothesDao clothesDao = new ClothesDao();
    private final AccessoriesDao accessoriesDao = new AccessoriesDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCategories.setItems(FXCollections.observableArrayList("Clothes", "Shoes", "Accessories"));
        cbCategories.getSelectionModel().selectFirst();
        onCategoryChange();
    }

    @FXML
    private void onCategoryChange() {
        hBoxProductList.getChildren().clear();
        List<? extends Product> products;
        switch (cbCategories.getValue()) {
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
                productCardController.setProduct(product);
                hBoxProductList.getChildren().add(fp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Clothes> getClothes() {
        return clothesDao.getClothes();
    }

    private List<Shoe> getShoes() {
        return shoesDao.getShoes();
    }

    private List<Accessory> getAccessories() {
        return accessoriesDao.getAccessories();
    }
}