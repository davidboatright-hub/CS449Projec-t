import com.mycompany.finalsprint0.Minus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MinusTest tests the Minus class. testGoodMinus should pass, testBadMinus 
 * should fail
 * @author David Boatright
 */
public class MinusTest {
    Minus goodMinus = new Minus(4, 3);
    int correctGoodMinus = 4 - 3;
    Minus badMinus = new Minus(4, -3);
    int correctBadMinus = 4 - (-3);
    
    @Test
    public void testGoodMinus(){
        assertEquals(correctGoodMinus, goodMinus.getValue());
    }
    
    @Test
    public void testBadMinus(){
        assertEquals(correctBadMinus, badMinus.getValue());
    }
}
