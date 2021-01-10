package pl.builders;
/**
 * Interfejs "budowniczego" planszy
 * */
public interface BoardBuilder {

    /**
     * Metoda tworząca bazy
     * @param beginX współrzędna X wierzchołka bazy
     * @param beginY współrzędna Y wierzchołka bazy
     * @param h rozmiar bazy
     * @param invert orientacja bazy
     * @param player pionki gracza
     * @param base  docelowa baza dla gracza po przeciwnej stronie tablicy
     */
    void drawBase( int beginX,int beginY,int h, boolean invert, int player,int base);

    /**
     * Metoda tworząca środkową część planszy bez baz
     */
    void drawBoard();
}
