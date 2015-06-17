/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.limanowa.library.model.other.BookItem;
import com.limanowa.library.model.other.MovieItem;
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
public class VirtualFunctionFromItemClassTest {
    BookItem book;
    MovieItem movie;
    
    public VirtualFunctionFromItemClassTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        book = new BookItem();
        movie = new MovieItem();
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
        book.setAuthor("Autor");
        movie.setDirector("Rezyser");
        
        Assert.assertEquals("Autor", book.getAdditionalInfo());
        Assert.assertEquals("Rezyser", movie.getAdditionalInfo());
    }
}
