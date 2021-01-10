package pl.client.tests;

import javafx.scene.paint.Color;
import org.junit.Test;
import pl.client.Board;

import static org.junit.Assert.assertEquals;

public class ClientUITest {
    @Test
    public void testHints() {
        Board testBoard = new Board(6);
        testBoard.setCurrentFieldNr(87);
        testBoard.showMoves();
        assertEquals(testBoard.circles.get(55).getStroke() , Color.ORANGERED);
    }
}
