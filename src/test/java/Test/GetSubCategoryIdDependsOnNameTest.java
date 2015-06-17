/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.limanowa.library.model.Account.Person;
import com.limanowa.library.model.Database.DBRepo;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patryk
 */
public class GetSubCategoryIdDependsOnNameTest {
    Person person;
    DBRepo repo;
    int actual;
    
    public GetSubCategoryIdDependsOnNameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        repo = new DBRepo();
        repo.ConnectDB();
        actual = repo.getSubCategoryIdDependsOnName("Wiersze");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test 
    public void testMe(){
        Assert.assertEquals(10, actual);
    } 
}
