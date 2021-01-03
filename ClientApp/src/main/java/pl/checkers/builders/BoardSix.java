package pl.checkers.builders;

public class BoardSix extends ConcreteBoard {

    public BoardSix() {
        drawBoard();

        drawBase(8,1,4,false,1);
        drawBase(4,10,4,false,5);
        drawBase(4+9,10,4,false,3);

        drawBase(4+9,8,4,true,2);
        drawBase(4,8,4,true,6);
        drawBase(8,17,4,true,4);
    }
}
