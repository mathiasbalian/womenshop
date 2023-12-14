package com.mathias.womenstore;

import com.mathias.womenstore.dao.ProductDao;
import com.mathias.womenstore.dao.ShopDao;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import com.mathias.womenstore.model.Shop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

    @FXML
    private Button btnDeleteProduct;

    private MainMenuController mainMenuController;
    private Product product;
    private Shop shop;


    @FXML
    private void onClickDelete() {
        ProductDao.deleteProduct(this.product.getId());
        mainMenuController.refreshProducts();
    }

    @FXML
    private void onClickSell() {
        showBuyOrSellWindow(false);
    }

    @FXML
    private void onClickBuy() {
        showBuyOrSellWindow(true);
    }

    public void onClickEdit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_product_page.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();

            EditProductController editProductController = loader.getController();

            editProductController.initializeController(product, mainMenuController);


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
            txtProductPrice.setText("Size " + ((Shoe) product).getShoeSize() + " - " + String.format("%.0f", product.getCurrentPrice()) + "€");
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("shoes.jpg")));
            ivProduct.setImage(image);
        } else if (product instanceof Clothes) {
            txtProductPrice.setText("Size " + ((Clothes) product).getSize() + " - " + String.format("%.0f", product.getCurrentPrice()) + "€");
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("clothes.png")));
            ivProduct.setImage(image);
        } else {
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("accessories.jpg")));
            ivProduct.setImage(image);
            txtProductPrice.setText(String.format("%.0f", product.getCurrentPrice()) + "€");
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

    private boolean checkSell(int quantity) {
        return product.getNbItems() >= quantity;
    }

    private boolean checkBuy(double price) {
        return shop.getCapital() >= price;
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

    private void buyItem(int quantity, double price) {
        if (checkBuy(price)) {
            double currentCapital = shop.getCapital();
            double currentCost = shop.getCost();

            ShopDao.setCapital(currentCapital - quantity * price);
            ShopDao.setCost(currentCost + quantity * price);
            this.shop.setCapital(currentCapital - quantity * price);
            this.shop.setCost(currentCost + quantity * price);
            this.mainMenuController.setShopDetails();

            ProductDao.updateProductStock(product, true, quantity);
            this.product.setNbItems(product.getNbItems() + quantity);
            this.txtProductStock.setText("In stock: " + product.getNbItems());
        } else {
            showBuyImpossible();
        }
    }

    private void sellItem(int quantity, double price) {
        if (checkSell(quantity)) {
            double currentCapital = shop.getCapital();
            double currentIncome = shop.getIncome();

            ShopDao.setCapital(currentCapital + quantity * price);
            ShopDao.setIncome(currentIncome + quantity * price);
            this.shop.setCapital(currentCapital + quantity * price);
            this.shop.setIncome(currentIncome + quantity * price);
            this.mainMenuController.setShopDetails();

            ProductDao.updateProductStock(product, false, quantity);
            this.product.setNbItems(product.getNbItems() - quantity);
            this.txtProductStock.setText("In stock: " + product.getNbItems());
        } else {
            showSellImpossible();
        }
    }


    private void showBuyOrSellWindow(boolean buying) {
        Stage popupStage = new Stage();
        popupStage.setTitle(buying ? "Buy a product" : "Sell a product");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label priceLabel = new Label(buying ? "Product buy price:" : "Product sell price");
        TextField priceField = new TextField();
        priceField.setText(String.format("%.0f", product.getCurrentPrice()));
        Label quantityLabel = new Label(buying ? "Number of products to buy:" : "Number of products to sell");
        TextField quantityField = new TextField();

        gridPane.add(priceLabel, 0, 0);
        gridPane.add(priceField, 1, 0);
        gridPane.add(quantityLabel, 0, 1);
        gridPane.add(quantityField, 1, 1);

        Button validateButton = new Button(buying ? "Buy" : "Sell");
        validateButton.setOnAction(e -> {
            if (checkInputs(priceField, quantityField)) {
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                if (buying) {
                    buyItem(quantity, price);
                } else {
                    sellItem(quantity, price);
                }
                popupStage.close();
            }

        });

        gridPane.add(validateButton, 1, 2);

        Scene scene = new Scene(gridPane, 370, 150);
        popupStage.setScene(scene);

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    private boolean checkInputs(TextField priceField, TextField quantityField) {
        if (priceField.getText().isBlank() || quantityField.getText().isBlank()) {
            return false;
        }

        try {
            if (Double.parseDouble(priceField.getText()) < 0) return false;
        } catch (NumberFormatException e) {
            return false;  // Handle the case when the input is not a valid double
        }

        try {
            if (Integer.parseInt(quantityField.getText()) < 0) return false;
        } catch (NumberFormatException e) {
            return false;  // Handle the case when the input is not a valid integer
        }

        return true;
    }
}
