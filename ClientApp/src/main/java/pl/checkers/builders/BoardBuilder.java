package pl.checkers.builders;

public interface BoardBuilder {

    /**
     * Funkcja tworząca promienie na planszy
     */
    void drawBase( int beginX,int beginY,int h, boolean invert, int player,int base);

    /**
     * Tworzenie planszy bez baz
     */
    void drawBoard();
}
