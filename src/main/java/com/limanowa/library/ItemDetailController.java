/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.other.Item;
import com.limanowa.library.model.other.LoggedInfo;
import com.limanowa.library.model.other.OrderInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class ItemDetailController implements Initializable {
    private Item item = null;
    private User user = null;
    private LoggedInfo loggedInfo = null;
    /**
     * Initializes the controller class.
     */
    @FXML
    FXMLLoader loader = new FXMLLoader();
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnOrder;
    
    @FXML
    private Label lblTitle;
    
    @FXML
    private Label lblCategory;
    
    @FXML
    private Label lblSubcategory;
    
    @FXML
    private Label lblUser;
    
    @FXML
    private Label lblAuthor;
    
    @FXML 
    private TextArea txtDescription;
    
    @FXML
    private Label lblMsg;
    
    @FXML
    private Label lblAval;
    
    @FXML 
    private void btnBackAction(ActionEvent event) throws Exception{
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/Catalog.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Katalog");
            
            CatalogController controller = loader.<CatalogController>getController();
            controller.setLoggedInfo(loggedInfo);
            controller.setUser(user);
            stage.show();    
    }
    
    @FXML 
    private void btnOrderAction(ActionEvent event) throws Exception{
        OrderInfo order = new OrderInfo();
        order.setItemId(item.getId());
        order.setUserId(loggedInfo.getUserId());
        order.setOwnerId(item.getUserId());
        
        if(item.setOrder(order)){
           item.setAvalibility(item.getId(), 0);
        }
        
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/OrderConfirm.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Potwierdzenie zamówienia");
            OrderConfirmController controller = loader.<OrderConfirmController>getController();
            controller.setLoggedInfo(loggedInfo);
            controller.setUser(user);
            controller.setItem(item);
            
            stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setItem(Item item){
        this.item = item;
        this.lblTitle.setText(item.getTitle());
        this.lblAuthor.setText(lblAuthor.getText()+": "+item.getAdditionalInfo());
        this.lblCategory.setText(lblCategory.getText()+": "+item.getCategory());
        this.lblSubcategory.setText(lblSubcategory.getText()+": "+item.getSubcategory());
        this.lblUser.setText(lblUser.getText()+": "+item.getUser());
        this.txtDescription.setText(item.getDescription());
        System.out.println(item.isAvalibility());
        
        
        if(loggedInfo.getUserId()!=0 && item.getUserId()!=0){
            if(item.isAvalibility() == 1 && item.getUserId() == loggedInfo.getUserId()){
                this.btnOrder.setVisible(false);
                this.lblMsg.setVisible(true);
                this.lblAval.setVisible(false);
            }  
            if(item.isAvalibility() == 0 && item.getUserId() == loggedInfo.getUserId()){
                this.btnOrder.setVisible(false);
                this.lblMsg.setVisible(true);
                this.lblAval.setVisible(false);
            }
            if(item.isAvalibility() == 0 && item.getUserId() != loggedInfo.getUserId()){
                this.btnOrder.setVisible(false);
                this.lblAval.setVisible(true);
                this.lblMsg.setVisible(false);
            }
        }
        else{
            this.btnOrder.setVisible(false);
            this.lblAval.setVisible(false);
            this.lblMsg.setVisible(false);
        }
        
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
        if(this.loggedInfo.isLogged()){
            btnOrder.setVisible(true);
        }
        else{
            btnOrder.setVisible(false);
        }
    }
}
