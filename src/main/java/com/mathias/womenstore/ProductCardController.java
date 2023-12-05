package com.mathias.womenstore;

import com.mathias.womenstore.dao.ProductDao;
import com.mathias.womenstore.dao.ShopDao;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import com.mathias.womenstore.model.Shop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductCardController {

    @FXML
    private Button btnBuyProduct;

    @FXML
    private Button btnEditProduct;

    @FXML
    private Button btnSellProduct;

    @FXML
    private FlowPane fpProductActions;

    @FXML
    private FlowPane fpProductCard;

    @FXML
    private FlowPane fpProductPrice;

    @FXML
    private FlowPane fpProductStock;

    @FXML
    private ImageView ivProduct;

    @FXML
    private Text txtProductName;

    @FXML
    private Text txtProductPrice;

    @FXML
    private Text txtProductStock;

    private MainMenuController mainMenuController;
    private Product product;
    private Shop shop;

    @FXML
    private void onClickSell() {
        if (checkSell()) {
            double priceProduct = product.getCurrentPrice();
            double currentCapital = shop.getCapital();
            double currentIncome = shop.getIncome();

            ShopDao.setCapital(currentCapital + priceProduct);
            ShopDao.setIncome(currentIncome + priceProduct);
            this.shop.setCapital(currentCapital + priceProduct);
            this.shop.setIncome(currentIncome + priceProduct);
            this.mainMenuController.setShopDetails();

            ProductDao.updateProductStock(product, false);
            this.product.setNbItems(product.getNbItems() - 1);
            this.txtProductStock.setText("In stock: " + product.getNbItems());
        } else {
            showSellImpossible();
        }
    }

    @FXML
    private void onClickBuy() {
        if (checkBuy()) {
            double priceProduct = product.getRealPrice();
            double currentCapital = shop.getCapital();
            double currentCost = shop.getCost();

            ShopDao.setCapital(currentCapital - priceProduct);
            ShopDao.setCost(currentCost + priceProduct);
            this.shop.setCapital(currentCapital - priceProduct);
            this.shop.setCost(currentCost + priceProduct);
            this.mainMenuController.setShopDetails();

            ProductDao.updateProductStock(product, true);
            this.product.setNbItems(product.getNbItems() + 1);
            this.txtProductStock.setText("In stock: " + product.getNbItems());
        } else {
            showBuyImpossible();
        }
    }

    public void onClickEdit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_product_page.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.setScene(scene);

            stage.setTitle("My Window");

            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public void initializeController(Product product, MainMenuController controller) {
        this.mainMenuController = controller;
        if (product instanceof Shoe) {
            txtProductPrice.setText("Size " + ((Shoe) product).getShoeSize() + " - " + product.getCurrentPrice() + "€");
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("shoes.jpg")));
            ivProduct.setImage(image);
        } else if (product instanceof Clothes) {
            txtProductPrice.setText("Size " + ((Clothes) product).getSize() + " - " + product.getCurrentPrice() + "€");
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("clothes.png")));
            ivProduct.setImage(image);
        } else {
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("accessories.jpg")));
            ivProduct.setImage(image);
            txtProductPrice.setText(product.getCurrentPrice() + "€");
        }
        ivProduct.setFitWidth(175);
        ivProduct.setFitHeight(175);
        ivProduct.setPreserveRatio(true);
        ivProduct.setSmooth(true);
        txtProductName.setText(product.getName());
        txtProductStock.setText("In stock: " + product.getNbItems());

        this.product = product;
        this.shop = ShopDao.getShop();
    }

    private boolean checkSell() {
        return product.getNbItems() > 0;
    }

    private boolean checkBuy() {
        return shop.getCapital() >= product.getRealPrice();
    }

    public void showSellImpossible() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Alert");

        Label label = new Label("Not enough stock to sell this item.");
        StackPane popupLayout = new StackPane();
        popupLayout.getChildren().add(label);

        Scene popupScene = new Scene(popupLayout, 250, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    public void showBuyImpossible() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Alert");

        Label label = new Label("You don't have enough capital to buy this product.");
        StackPane popupLayout = new StackPane();
        popupLayout.getChildren().add(label);

        Scene popupScene = new Scene(popupLayout, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
