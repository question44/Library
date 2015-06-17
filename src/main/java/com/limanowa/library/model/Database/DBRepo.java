/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.Database;
import com.limanowa.library.model.Account.Person;
import com.limanowa.library.model.other.AlbumItem;
import com.limanowa.library.model.other.ApprovalInfo;
import com.limanowa.library.model.other.BookItem;
import com.limanowa.library.model.other.Item;
import com.limanowa.library.model.other.MovieItem;
import com.limanowa.library.model.other.OrderInfo;
import com.limanowa.library.model.other.SetOrderInfo;
import com.limanowa.library.model.other.SubCategory;
import java.sql.*;
/**
 *
 * @author Patryk
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

public class DBRepo implements DBRepoInterface{
    public static Connection connection = null;
    
    @Override
    public void ConnectDB(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:Library.sqlite");
            System.out.println("dziala");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<String> getCategories(){
        Statement stat = null;
        ArrayList<String> list = new ArrayList<String>();
        try{
            stat= connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT name FROM Categories");
            while(result.next()){
                list.add(result.getString("name"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    @Override
    public boolean AddUser(Person person){
        Statement stat = null;
        System.out.println(person.getLogin());
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO USERS(accountId, firstname, lastname, email, birthdate, username, password)"
                            + "VALUES('"+person.getAccountType()+"','"+person.getFirstName()+"','"+person.getLastName()+"'"
                            + ",'"+person.getEmail()+"','"+person.getBirthDate()+"','"+person.getLogin()+"','"+person.getPassword()+"')");
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public int CheckUser(Person person){
        Statement stat = null;
        int type = 0;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT accountId FROM USERS WHERE username = '"+person.getLogin()+"'");
            if(result.next()){
                type = result.getInt("accountId");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            type = -1;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return type;
    }
    
    @Override
    public Person getData(Person person){
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM USERS WHERE username = '"+person.getLogin()+"'"
                    + "AND password = '"+person.getPassword()+"'");
            if(result.next())
            {
                person.setAccountType(result.getInt("accountId"));
                person.setBirthDate(result.getString("birthdate"));
                person.setEmail(result.getString("email"));
                person.setFirstName(result.getString("firstname"));
                person.setLastName(result.getString("lastname"));
                person.setUserId(result.getInt("userId"));
                person.setLogin(result.getString("username"));
                person.setPassword(result.getString("password"));
                return person;
            }
            else{
                return null;
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<SubCategory> getSubCategories() {
        Statement stat = null;
        List<SubCategory> list =  new ArrayList<SubCategory>();
        try{
            stat= connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT Categories.name as 'cat', SubCategories.name as 'subcat' FROM SubCategories JOIN Categories ON Categories.categoryId = SubCategories.categoryId");
            while(result.next()){
                list.add(new SubCategory(result.getString("subcat"), result.getString("cat")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public ObservableList<String> getAllBooks() {
        return getItemDependOnCategory("Books");
    }

    @Override
    public ObservableList<String> getAllAlbums() {
        return getItemDependOnCategory("Albums");
    }

    @Override
    public ObservableList<String> getAllMovies() {
        return getItemDependOnCategory("Movies");
    }
    
    public ObservableList<String> getBooks(String subcategory){
        return getItemDependOnCategoryAndSubcategory("Books", subcategory);
    }
    
    public ObservableList<String> getAlbums(String subcategory){
        return getItemDependOnCategoryAndSubcategory("Albums", subcategory);
    }
    
    public ObservableList<String> getMovies(String subcategory){
        return getItemDependOnCategoryAndSubcategory("Movies", subcategory);
    }

    @Override
    public ObservableList<String> getItemDependOnCategory(String category) {
        Statement stat = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT title FROM "+category);
            while(result.next()){
                list.add(result.getString("title"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    @Override
    public ObservableList<String> getItemDependOnCategoryAndSubcategory(String category,String subcategory) {
        Statement stat = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT title FROM "+category+" JOIN SubCategories "
                    + "ON "+category+".subcategoryId = SubCategories.subcategoryId WHERE Subcategories.name = '"+subcategory+"'");
            while(result.next()){
                list.add(result.getString("title"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public BookItem getInformationForBook(String name) {
        Statement stat = null;
        BookItem b = new BookItem();
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT bookId, name, description, author, title, username, Users.userId, avalible FROM Books JOIN SubCategories ON Books.subcategoryId = SubCategories.subcategoryId JOIN Users ON Users.userId = Books.userId WHERE title = '"+name+"'");
            while(result.next()){
                b.setId(result.getInt("bookId"));
                b.setSubcategory(result.getString("name"));
                b.setDescription(result.getString("description"));
                b.setAuthor(result.getString("author"));
                b.setTitle(result.getString("title"));
                b.setUser(result.getString("username"));
                b.setUserId(result.getInt("userId"));
                b.setAvalibility(result.getInt("avalible"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return b;
    }

    @Override
    public AlbumItem getInformationForAlbum(String name) {
        Statement stat = null;
        AlbumItem a = new AlbumItem();
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT albumId, name, description, band, title, username, Users.userId, avalible FROM Albums JOIN SubCategories ON Albums.subcategoryId = SubCategories.subcategoryId JOIN Users ON Users.userId = Albums.userId WHERE title = '"+name+"'");
            while(result.next()){
                a.setId(result.getInt("albumId"));
                a.setSubcategory(result.getString("name"));
                a.setDescription(result.getString("description"));
                a.setBand(result.getString("band"));
                a.setTitle(result.getString("title"));
                a.setUser(result.getString("username"));
                a.setUserId(result.getInt("userId"));
                a.setAvalibility(result.getInt("avalible"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return a;
    }

    @Override
    public MovieItem getInformationForMovie(String name) {
        Statement stat = null;
        MovieItem m = new MovieItem();
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT moviesId, name, description, director, title, username, Users.userId, avalible FROM Movies JOIN SubCategories ON Movies.subcategoryId = SubCategories.subcategoryId JOIN Users ON Users.userId = Movies.userId WHERE title = '"+name+"'");
            while(result.next()){
                m.setId(result.getInt("moviesId"));
                m.setSubcategory(result.getString("name"));
                m.setDescription(result.getString("description"));
                m.setDirector(result.getString("director"));
                m.setTitle(result.getString("title"));
                m.setUser(result.getString("username"));
                m.setUserId(result.getInt("userId"));
                m.setAvalibility(result.getInt("avalible"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return m;
    }

    @Override
    public boolean addOrder(OrderInfo order) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Orders(categoryId, itemId, userId, ownerId)"
                            + "VALUES('"+order.getCategoryId()+"','"+order.getItemId()+"','"+order.getUserId()+"','"+order.getOwnerId()+"')");
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void setAvalibity(int category, int item, int val) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            if(category == 1){
                stat.executeUpdate("UPDATE Books SET avalible='"+val+"' WHERE bookId = "+item);
            }
            if(category == 2){
                stat.executeUpdate("UPDATE Movies SET avalible='"+val+"' WHERE moviesId = "+item);
            }
            else{
                stat.executeUpdate("UPDATE Albums SET avalible='"+val+"' WHERE albumId = "+item);
                System.out.println("czesc");
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public ObservableList<ApprovalInfo> getAllOrders(int userId){
        ObservableList<ApprovalInfo> list = FXCollections.observableArrayList();
        try {
            list.addAll(getBooksOrders(userId));
        } catch (SQLException ex) {
            Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list.addAll(getMoviesOrders(userId));
        } catch (SQLException ex) {
            Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list.addAll(getAlbumsOrders(userId));
        } catch (SQLException ex) {
            Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    private ObservableList<ApprovalInfo> getBooksOrders(int userId) throws SQLException {
        ObservableList<ApprovalInfo> list = FXCollections.observableArrayList();
        list = getOrders("Books","bookId",1, userId);
        return list;
    }

    private ObservableList<ApprovalInfo> getAlbumsOrders(int userId) throws SQLException {
        ObservableList<ApprovalInfo> list = FXCollections.observableArrayList();
        list = getOrders("Albums","albumId",3, userId);
        return list;
    }

    private ObservableList<ApprovalInfo> getMoviesOrders(int userId) throws SQLException {
        ObservableList<ApprovalInfo> list = FXCollections.observableArrayList();
        list = getOrders("Movies","moviesId",2, userId);
        return list;
    }

    private ObservableList<ApprovalInfo> getOrders(String table,String key, int catId, int userId) throws SQLException{
        Statement stat = null;
        ObservableList<ApprovalInfo> list = FXCollections.observableArrayList();
        try{
            stat = connection.createStatement();

            ResultSet result = stat.executeQuery("SELECT orderId, itemId, username, Orders.ownerId, Categories.name as 'cat', title FROM Orders "
                    + "JOIN "+table+" ON Orders.itemId = "+table+"."+key+" "
                    + "JOIN Users ON Orders.userId = Users.userId "
                    + "JOIN Categories ON Orders.categoryId = Categories.categoryId "
                    + "WHERE Orders.categoryId = "+catId+" AND Orders.ownerId = "+userId+"");
            while(result.next()){
                list.add(new ApprovalInfo(
                        result.getInt("orderId"),
                        result.getInt("itemId"),
                        result.getInt("ownerId"),
                        result.getString("username"),
                        result.getString("cat"),
                        result.getString("title")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            stat.close();
        }
        return list;
    }

    @Override
    public boolean setsetOrder(SetOrderInfo info) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO SetOrders(categoryId, itemId, username, orderId, ownerId)"
                    + "VALUES("+info.getCategoryId()+","+info.getItemId()+",'"+info.getUsername()+"','"+info.getOrderId()+"','"+info.getOwnerId()+"')");
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public void RemoveFromOrders(int orderId){
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("DELETE FROM Orders WHERE orderId = "+orderId+"");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean checkOrder(ApprovalInfo info) {
        Statement stat = null;
        int catId = 0;
        System.out.println("kategoria " +info.getCategory());
        switch(info.getCategory()){
            case "Ksiazki":
                catId = 1;
                break;
            case "Filmy":
                catId = 2;
                break;
            case "Muzyka":
                catId = 3;
                break;
        }
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT setorderId FROM SetOrders where categoryId = "+catId+" AND itemId = "+info.getItemId()+" ");
            if(result.next()){
                return true;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public ObservableList<SetOrderInfo> getAllSetOrders(String username) {
        ObservableList<SetOrderInfo> list = FXCollections.observableArrayList();
        try {
            list.addAll(getBooksSetOrders(username));
        } catch (SQLException ex) {
            Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list.addAll(getMoviesSetOrders(username));
        } catch (SQLException ex) {
            Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list.addAll(getAlbumsSetOrders(username));
        } catch (SQLException ex) {
            Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private ObservableList<SetOrderInfo> getBooksSetOrders(String username) throws SQLException {
        ObservableList<SetOrderInfo> list = FXCollections.observableArrayList();
        list = getSetOrders("Books","bookId",1, username);
        return list;
    }

    private ObservableList<SetOrderInfo> getAlbumsSetOrders(String username) throws SQLException {
        ObservableList<SetOrderInfo> list = FXCollections.observableArrayList();
        list = getSetOrders("Movies","moviesId",2, username);
        return list;
    }

    private ObservableList<SetOrderInfo> getMoviesSetOrders(String username) throws SQLException {
        ObservableList<SetOrderInfo> list = FXCollections.observableArrayList();
        list = getSetOrders("Albums","albumId",3, username);
        return list;
    }

    private ObservableList<SetOrderInfo> getSetOrders(String table,String key, int catId, String username) throws SQLException{
        Statement stat = null;
        ObservableList<SetOrderInfo> list = FXCollections.observableArrayList();
        try{
            stat = connection.createStatement();
            
            ResultSet result = stat.executeQuery("SELECT setorderId, SetOrders.categoryId, SetOrders.itemId,"
                    + "Users.username, title, Categories.name as 'catname', orderId, ownerId  FROM SetOrders "
                    + "JOIN "+table+" ON SetOrders.itemId = "+table+"."+key+" "
                    + "JOIN Users ON SetOrders.ownerId = Users.userId "
                    + "JOIN Categories ON SetOrders.categoryId = Categories.categoryId "
                    + "WHERE SetOrders.categoryId = "+catId+" AND SetOrders.username = '"+username+"'");
            
            while(result.next()){
                list.add(new SetOrderInfo(
                        result.getInt("setorderId"),
                        result.getInt("categoryId"),
                        result.getInt("itemId"),
                        result.getString("username"),
                        result.getString("title"),
                        result.getString("catname"),
                        result.getInt("orderId"),
                        result.getInt("ownerId")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            stat.close();
        }
        return list;
    }

    @Override
    public void sendRefuseMessage(ApprovalInfo info, String user) {
        Statement stat = null;
        String text="Uzytkownik "+user+" odmowil pozyczenia rzeczy o tytule "+info.getTitle()+"!";
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Messages(orderId,itemId,username, ownerId, text, readed)"
                    + "VALUES("+info.getOrderId()+","+info.getItemId()+",'"+info.getUsername()+"',"+info.getOwnerId()+",'"+text+"',0)");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        Statement stat = null;
        
        try{
            stat = connection.createStatement();
            stat.executeUpdate("DELETE FROM Orders WHERE orderId = "+orderId);
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ObservableList<String> getMessages(String username) {
        ObservableList<String> list = FXCollections.observableArrayList();
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT text FROM Messages WHERE username = '"+username+"'");
            while(result.next()){
                list.add(result.getString("text"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public int getItemsCategory(int orderId) {
        Statement stat = null;
        int cat = 0;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT categoryId FROM Orders WHERE orderId = "+orderId);
            while(result.next()){
                cat = result.getInt("categoryId");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cat;
    }

    @Override
    public void sendReturnMessage(ApprovalInfo info, String user) {
        Statement stat = null;
        String text="Uzytkownik "+user+" prosi o oddanie rzeczy o tytule "+info.getTitle()+"!";
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Messages(orderId,itemId,username, ownerId, text, readed)"
                    + "VALUES("+info.getOrderId()+","+info.getItemId()+",'"+info.getUsername()+"',"+info.getOwnerId()+",'"+text+"',0)");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void RemoveFromSetOrders(int orderId) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("DELETE FROM SetOrders WHERE orderId = "+orderId+"");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean CheckMessages(String username) {
        Statement stat = null;
        boolean b = false;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT messagesId FROM Messages Where username = '"+username+"' AND readed = 0");
            if(result.next()){
                b = true;
            }
            else{
                b = false;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return b;
    }

    @Override
    public void UpdateReadedMessage(String username) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("UPDATE Messages Set readed = 1 WHERE username = '"+username+"'");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ObservableList<String> getSubCategoriesViaCategoryName(String name) {
        ObservableList<String> list = FXCollections.observableArrayList();
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT SubCategories.name as 'n' FROM SubCategories "
                    + "JOIN Categories ON SubCategories.categoryId = Categories.categoryId WHERE Categories.name = '"+name+"'");
            while(result.next()){
                list.add(result.getString("n"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public ObservableList<String> getAllTags() {
        ObservableList<String> list = FXCollections.observableArrayList();
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT name as 'n' FROM Tags");
            while(result.next()){
                list.add(result.getString("n"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public void addTag(String name) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Tags(name) VALUES('"+name+"')");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getSubCategoryIdDependsOnName(String name) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT subcategoryId FROM SubCategories WHERE name ='"+name+"'");
            if(result.next()){
                return result.getInt("subcategoryId");
            }
            else{
                return -1;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return -1;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void AddBook(BookItem item) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Books(subcategoryId, title, author, description, userId, avalible)"
                    + "VALUES("+item.getSubcategoryId()+",'"+item.getTitle()+"','"+item.getAuthor()+"','"+item.getDescription()+"', "+item.getUserId()+","+item.isAvalibility()+")");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void AddMovie(MovieItem item) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Movies(subcategoryId, title, director, description, userId, avalible)"
                    + "VALUES("+item.getSubcategoryId()+",'"+item.getTitle()+"','"+item.getDirector()+"','"+item.getDescription()+"', "+item.getUserId()+","+item.isAvalibility()+")");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void AddAlbum(AlbumItem item) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO Albums(subcategoryId, title, band, description, userId, avalible)"
                    + "VALUES("+item.getSubcategoryId()+",'"+item.getTitle()+"','"+item.getBand()+"','"+item.getDescription()+"', "+item.getUserId()+","+item.isAvalibility()+")");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getLastId(String table, String col) {
        int id = -1;
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT max("+col+") as 'max' FROM "+table);
            if(result.next()){
                id = result.getInt("max");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return -1;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    @Override
    public void addAndBindTag(int idTag, int idItem, int idCategory) {
        Statement stat = null;
        try{
            stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO TagsItem(itemId, tagId, categoryId) VALUES("+idItem+","+idTag+","+idCategory+")");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getTagIdDependsOnName(String name) {
        int id = -1;
        Statement stat = null;
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT tagId FROM Tags WHERE name = '"+name+"'");
            if(result.next()){
                id = result.getInt("tagId");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return -1;
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    @Override
    public ObservableList<String> getTags(String category, int id) {
        ObservableList<String> list = FXCollections.observableArrayList();
        Statement stat = null;
        int cat = 0;
        System.out.println(category);
        if(category.equals("Ksiazki")){
            cat = 1;
        }
        if(category.equals("Muzyka")){
            cat = 3;
        }
        if(category.equals("Film")){
            cat = 2;
        }
        
        try{
            stat = connection.createStatement();
            ResultSet result = stat.executeQuery("SELECT name FROM TagsItem JOIN Tags ON TagsItem.tagId = Tags.tagId"
                    + " WHERE categoryId = "+cat+" AND itemId = "+id);
            while(result.next()){
                list.add(result.getString("name"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally{
            try {
                stat.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBRepo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    @Override
    public ObservableList<String> getItemForTags(String name) {
        ObservableList<String> list = FXCollections.observableArrayList();
        //osobno z ksiazek, albumow i filmow i zlaczyc.
        return list;
    }
   
}
