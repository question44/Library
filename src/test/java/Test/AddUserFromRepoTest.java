/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.limanowa.library.model.Account.Person;
import com.limanowa.library.model.Database.DBRepo;
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
public class AddUserFromRepoTest {
    Person person;
    DBRepo repo;
    boolean actual = true;
    
    public AddUserFromRepoTest() {
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
        person = new Person();
        
        person.setAccountType(0);
        person.setBirthDate("01-01-1993");
        person.setEmail("a@a.pl");
        person.setFirstName("TestUnit");
        person.setLastName("TestUnit");
        person.setLogin("TestUnit");
        person.setPassword("TestUnit");
        person.setUserId(0);
        
        actual = repo.AddUser(person);
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
        assertTrue(actual);
    }
}
