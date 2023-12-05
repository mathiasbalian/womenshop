package com.mathias.womenstore;

import com.mathias.womenstore.dao.ProductDao;
import com.mathias.womenstore.model.Accessory;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    @FXML
    private Button btnValidateAdd;

    @FXML
    private ComboBox<String> cbCategories;

    @FXML
    private FlowPane fpAddProductMenu;

    @FXML
    private FlowPane fpProductSize;

    @FXML
    private TextField txtFieldPrice;

    @FXML
    private TextField txtFieldProductName;

    @FXML
    private TextField txtFieldSize;

    private MainMenuController mainMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fpProductSize.setVisible(false);
        cbCategories.setItems(FXCollections.observableArrayList("Clothes", "Shoes", "Accessories"));
    }

    void setMainMenuController(MainMenuController controller) {
        this.mainMenuController = controller;
    }

    @FXML
    void onCategoryChange(ActionEvent event) {
        switch (cbCategories.getValue()) {
            case "Clothes", "Shoes":
                fpProductSize.setVisible(true);
                break;
            case "Accessories":
                fpProductSize.setVisible(false);
        }
    }

    @FXML
    void onAddProductClick() {
        Product product;
        if (!validateAddProduct()) {
            return;
        }

        if (cbCategories.getValue().equals("Clothes")) {
            product = new Clothes(ProductDao.getNextId(),
                    txtFieldProductName.getText(),
                    Double.parseDouble(txtFieldPrice.getText()),
                    Double.parseDouble(txtFieldPrice.getText()),
                    0,
                    Integer.parseInt(txtFieldSize.getText()));
        } else if (cbCategories.getValue().equals("Shoes")) {
            product = new Shoe(ProductDao.getNextId(),
                    txtFieldProductName.getText(),
                    Double.parseDouble(txtFieldPrice.getText()),
                    Double.parseDouble(txtFieldPrice.getText()),
                    0,
                    Integer.parseInt(txtFieldSize.getText()));
        } else {
            product = new Accessory(ProductDao.getNextId(),
                    txtFieldProductName.getText(),
                    Double.parseDouble(txtFieldPrice.getText()),
                    Double.parseDouble(txtFieldPrice.getText()),
                    0);
        }

        ProductDao.addProduct(product);
        mainMenuController.refreshProducts();
        Stage stage = (Stage) btnValidateAdd.getScene().getWindow();
        stage.close();
    }

    private boolean validateAddProduct() {
        if (cbCategories.getValue() == null) {
            showImpossiblePopup("Please select a category!");
            return false;
        }

        if (!fieldsNotEmpty()) {
            showImpossiblePopup("Please fill all the product fields!");
            return false;
        }

        try {
            System.out.println(txtFieldPrice.getText());
            double price = Double.parseDouble(txtFieldPrice.getText());
            if (price < 0) {
                showImpossiblePopup("Please enter a positive price!");
                return false;
            }

            if ((cbCategories.getValue().equals("Clothes") || cbCategories.getValue().equals("Shoes")) &&
                    Integer.parseInt(txtFieldSize.getText()) < 0) {

                showImpossiblePopup("Please enter a positive size!");
                return false;
            }
        } catch (NumberFormatException e) {
            showImpossiblePopup("Please enter a valid price / size!");
            return false;
        }

        return true;
    }

    private boolean fieldsNotEmpty() {
        switch (cbCategories.getValue()) {
            case "Clothes", "Shoes":
                return !txtFieldPrice.getText().isBlank() &&
                        !txtFieldProductName.getText().isBlank() &&
                        !txtFieldSize.getText().isBlank();
            case "Accessories":
                return !txtFieldPrice.getText().isBlank() &&
                        !txtFieldProductName.getText().isBlank();
            default:
                return false;
        }
    }

    private void showImpossiblePopup(String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Alert");

        Label label = new Label(message);
        StackPane popupLayout = new StackPane();
        popupLayout.getChildren().add(label);

        Scene popupScene = new Scene(popupLayout, 250, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

}
