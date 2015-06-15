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
import com.limanowa.library.model.other.MovieItem;
import com.limanowa.library.model.other.OrderInfo;
import com.limanowa.library.model.other.SetOrderInfo;
import com.limanowa.library.model.other.SubCategory;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Patryk
 */
public interface DBRepoInterface {
    public void ConnectDB();
    public boolean AddUser(Person person);
    public int CheckUser(Person person);
    public Person getData(Person person);
    public ArrayList<String> getCategories();
    public List<SubCategory> getSubCategories();
    public ObservableList<String> getItemDependOnCategory(String category);
    public ObservableList<String> getAllBooks();
    public ObservableList<String> getAllAlbums();
    public ObservableList<String> getAllMovies();
    public ObservableList<String> getBooks(String subcategory);
    public ObservableList<String> getAlbums(String subcategory);
    public ObservableList<String> getMovies(String subcategory);
    public ObservableList<String> getItemDependOnCategoryAndSubcategory(String category,String subcategory);
    public BookItem getInformationForBook(String name);
    public AlbumItem getInformationForAlbum(String name);
    public MovieItem getInformationForMovie(String name);
    public boolean addOrder(OrderInfo order);
    public void setAvalibity(int category,int item, int val);
    public ObservableList<ApprovalInfo> getAllOrders(int userId);
    public boolean setsetOrder(SetOrderInfo info);
    public void RemoveFromOrders(int orderId);
    public boolean checkOrder(ApprovalInfo info);
    public ObservableList<SetOrderInfo> getAllSetOrders(String username);
    public void sendRefuseMessage(ApprovalInfo info,String user);
    public void sendReturnMessage(ApprovalInfo info,String user);
    public void deleteOrder(int orderId);
    public ObservableList<String> getMessages(String username);
    public int getItemsCategory(int orderId);
    public void RemoveFromSetOrders(int orderId);
    public boolean CheckMessages(String username);
    public void UpdateReadedMessage(String username);
    public ObservableList<String>getSubCategoriesViaCategoryName(String name);
    public ObservableList<String>getAllTags();
    public void addTag(String name);
    public int getSubCategoryIdDependsOnName(String name);
    public void AddBook(BookItem item);
    public void AddMovie(MovieItem item);
    public void AddAlbum(AlbumItem item);
}
