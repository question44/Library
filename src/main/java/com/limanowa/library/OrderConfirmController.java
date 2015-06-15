/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.other.Item;
import com.limanowa.library.model.other.LoggedInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class OrderConfirmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private User user = null;
    private LoggedInfo loggedInfo = null;
    private Item item = null;
    
    @FXML 
    FXMLLoader loader = new FXMLLoader();
    
    @FXML
    private Label lblUser;
    
    @FXML 
    private Label lblTitle;
    
    @FXML
    private void btnBackAction(ActionEvent event) throws Exception{
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/MainExtraWindow.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Aplikacja domowej biblioteki");
            MainExtraWindowController controller = loader.<MainExtraWindowController>getController();
            controller.setLoggedInfo(loggedInfo);
            controller.setUser(user);
            stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
    }
    
    public void setItem(Item item){
        this.item = item;
        this.lblTitle.setText(this.lblTitle.getText()+" "+this.item.getTitle());
    }
    
    public void setUser(User user){
        this.user = user;
        this.lblUser.setText(this.lblUser.getText()+" "+this.user.getLogin());
    }
    
}
