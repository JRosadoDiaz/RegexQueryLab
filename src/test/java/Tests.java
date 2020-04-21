import edu.neumont.csc180.rosado.jose.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    ConsoleUI cs;
    @BeforeEach
    void setup(){
        cs = new ConsoleUI();
    }

    @Test
    void input_command_can_match_create_statement(){
        //arrange
        String command = "CREATE TABLE 'poopMaster' (name, age, poop_size) : line format /(insert) (regex) (here)/ file 'file path goes here'";

        //act
        boolean isSuccessful = cs.processCommand(command);

        //assert
        assertTrue(isSuccessful);
    }

    @Test
    void input_command_can_match_simple_select_statement(){
        //arrange
        String command = "SELECT name, age, poop_size FROM poopMaster";

        //act
        boolean isSuccessful = cs.processCommand(command);

        //assert
        assertTrue(isSuccessful);
    }
}
