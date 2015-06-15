/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library;

import com.google.inject.Injector;
import com.limanowa.library.model.Account.User;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.InjectorInstance;
import com.limanowa.library.model.other.ApprovalInfo;
import com.limanowa.library.model.other.LoggedInfo;
import java.io.IOException;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javax.imageio.IIOException;

/**
 * FXML Controller class
 *
 * @author Patryk
 */
public class MessagesController implements Initializable {
    User user = null;
    LoggedInfo loggedInfo  = null;
    private Injector injector = null;
    private ApprovalInfo toApprove = null;
    private DBRepoInterface repo = null;
    @FXML
    FXMLLoader loader = new FXMLLoader();
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnBackLog;
    
    @FXML
    private ListView listView;
    
    @FXML
    private void btnBackAction(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/fxml/MainPrimaryWindow.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Aplikacja domowej biblioteki");
            stage.show();
    }
    
    @FXML
    private void btnBackLogAction(ActionEvent event) throws IOException{
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
    
    public MessagesController(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private void setListView(){
        listView.setItems(repo.getMessages(user.getLogin()));
    }
    
    public void setUser(User user){
        this.user = user;
        setListView();
    }
    
    public void setLoggedInfo(LoggedInfo info){
        this.loggedInfo = info;
        if(this.loggedInfo.isLogged()){
            this.btnBack.setVisible(false);
            this.btnBackLog.setVisible(true);
        }
    }
}
