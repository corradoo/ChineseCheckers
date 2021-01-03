package pl.checkers.builders;

public class BoardFour extends ConcreteBoard{

    public BoardFour() {
        drawBoard();

        drawBase(8,1,4,false,0);
        drawBase(4,10,4,false,4);
        drawBase(4+9,10,4,false,3);

        drawBase(4+9,8,4,true,2);
        drawBase(4,8,4,true,1);
        drawBase(8,17,4,true,0);
    }
}
