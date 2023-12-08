package com.mathias.womenstore;

import com.mathias.womenstore.dao.ProductDao;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class EditProductController {

    @FXML
    private Button btnValidateEdit;

    @FXML
    private FlowPane fpProductCard;

    @FXML
    private FlowPane fpProductSize;

    @FXML
    private ImageView ivProduct;

    @FXML
    private TextField txtFieldEditName;

    @FXML
    private TextField txtFieldEditPrice;

    @FXML
    private TextField txtFieldEditSize;

    @FXML
    private Text txtEditSize;

    private MainMenuController mainMenuController;
    private Product product;


    // Getter methods

    public Button getBtnValidateEdit() {
        return btnValidateEdit;
    }

    public FlowPane getFpProductCard() {
        return fpProductCard;
    }

    public FlowPane getFpProductSize() {
        return fpProductSize;
    }

    public ImageView getIvProduct() {
        return ivProduct;
    }

    public TextField getTxtFieldEditName() {
        return txtFieldEditName;
    }

    public TextField getTxtFieldEditPrice() {
        return txtFieldEditPrice;
    }

    public TextField getTxtFieldEditSize() {
        return txtFieldEditSize;
    }

    public Text getTxtEditSize() { return txtEditSize; }
    public void onClickValidate() {
        product.setName(txtFieldEditName.getText());
        double discount = product.getCurrentPrice() / product.getRealPrice();
        product.setRealPrice(Double.parseDouble(txtFieldEditPrice.getText()));
        product.applyDiscount(discount);

        if (product instanceof Shoe) {
            ((Shoe) product).setShoeSize(Integer.parseInt(txtFieldEditSize.getText()));
        } else if (product instanceof Clothes) {
            ((Clothes) product).setSize(Integer.parseInt(txtFieldEditSize.getText()));
        }
        ProductDao.editProduct(product);
        mainMenuController.refreshProducts();
        Stage stage = (Stage) btnValidateEdit.getScene().getWindow();
        stage.close();
    }

    public void initializeController(Product product, MainMenuController controller) {
        this.mainMenuController = controller;
        this.product = product;

        getTxtFieldEditName().setText(product.getName());
        getTxtFieldEditPrice().setText(String.valueOf(product.getRealPrice()));

        if (product instanceof Shoe) {
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("shoes.jpg")));
            getIvProduct().setImage(image);
            getTxtFieldEditSize().setText(String.valueOf(((Shoe) product).getShoeSize()));
        } else if (product instanceof Clothes) {
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("clothes.png")));
            getIvProduct().setImage(image);
            getTxtFieldEditSize().setText(String.valueOf(((Clothes) product).getSize()));
        } else {
            Image image = new Image(String.valueOf(ProductCardController.class.getResource("accessories.jpg")));
            getIvProduct().setImage(image);
            getFpProductSize().setVisible(false);
        }
    }
}
