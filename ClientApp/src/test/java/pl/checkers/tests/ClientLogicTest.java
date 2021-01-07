package pl.checkers.tests;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.Test;
import pl.checkers.Board;

import static org.junit.Assert.assertEquals;

public class ClientLogicTest {

    @Test
    public void testCalculateDist() {
        Board testBoard = new Board(4);
        Circle c1 = new Circle(0,0,1);
        Circle c2 = new Circle(1,1,1);
        assertEquals(testBoard.calculateDist(c1,c2),Math.sqrt(2),0);
    }

    @Test
    public void testMove() {
        Board testBoard = new Board(2);
        testBoard.move(1,3,70);
        assertEquals(testBoard.fields.get(3).getPlayer() , 1);
    }

}
