package pl.client.tests;

import org.junit.Test;
import pl.server.ServerBoard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerLogicTest {

    @Test
    public void testNextJumpPossibleF() {
        ServerBoard sb = new ServerBoard(2);
        sb.movingIndex = 61;
        assertFalse(sb.nextJumpPossible());
    }

    @Test
    public void testNextJumpPossibleT() {
        ServerBoard sb = new ServerBoard(2);
        sb.movingIndex = 66;
        assertTrue(sb.nextJumpPossible());
    }

    @Test
    public void testValidateCorrectJump() {
        ServerBoard sb = new ServerBoard(2);
        assertTrue(sb.validateJump("2 66"));
    }

    @Test
    public void testValidateIncorrectJump() {
        ServerBoard sb = new ServerBoard(2);
        assertFalse(sb.validateJump("2 67"));
    }

    @Test
    public void testCorrectJumpable() {
        ServerBoard sb = new ServerBoard(2);
        assertTrue(sb.jumpable(66,2));
    }

}