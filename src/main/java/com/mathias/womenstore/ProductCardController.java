package com.mathias.womenstore;

import com.mathias.womenstore.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCardController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setProduct(Product product) {
        txtProductName.setText(product.getName());
        txtProductPrice.setText(product.getPrice() + "€");
        txtProductStock.setText("In stock: " + product.getNbItems());
        this.product = product;
    }
}
