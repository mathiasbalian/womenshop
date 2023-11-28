package com.mathias.womenstore;

import com.mathias.womenstore.dao.ShopDao;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import com.mathias.womenstore.model.Shop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ProductCardController {

    private Product product;

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

    private final ShopDao shopDao = new ShopDao();

    @FXML
    private void onClickSell() {
        double priceProduct = product.getPrice();
        Shop shop = shopDao.getShop();
        double currentCapital = shop.getCapital();
        double currentIncome = shop.getIncome();
        shopDao.setCapital(currentCapital + priceProduct);
        shopDao.setIncome(currentIncome + priceProduct);
        this.mainMenuController.setShopDetails();
    }

    @FXML
    private void onClickBuy() {
        double priceProduct = product.getPrice();
        Shop shop = shopDao.getShop();
        double currentCapital = shop.getCapital();
        double currentCost = shop.getCost();
        shopDao.setCapital(currentCapital - priceProduct);
        shopDao.setCost(currentCost + priceProduct);
        this.mainMenuController.setShopDetails();
    }


    public void initializeController(Product product, MainMenuController controller) {
        this.mainMenuController = controller;
        if (product instanceof Shoe) {
            txtProductPrice.setText("Size " + ((Shoe) product).getShoeSize() + " - " + product.getPrice() + "€");
        } else if (product instanceof Clothes) {
            txtProductPrice.setText("Size " + ((Clothes) product).getSize() + " - " + product.getPrice() + "€");
        } else {
            txtProductPrice.setText(product.getPrice() + "€");
        }
        txtProductName.setText(product.getName());
        txtProductStock.setText("In stock: " + product.getNbItems());
        this.product = product;
    }
}
