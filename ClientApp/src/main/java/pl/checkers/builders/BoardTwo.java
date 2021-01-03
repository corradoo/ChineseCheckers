package pl.checkers.builders;

public class BoardTwo extends ConcreteBoard{

    public BoardTwo() {
        drawBoard();

        drawBase(8,1,4,false,1);
        drawBase(4,10,4,false,0);
        drawBase(4+9,10,4,false,0);

        drawBase(4+9,8,4,true,0);
        drawBase(4,8,4,true,0);
        drawBase(8,17,4,true,2);
    }
}
