package pl.client;

/**Klasa reprezentująca pole*/
public class Field {

    private final int x;
    private final int y;
    private int player;
    private final int base;

    public Field(int x, int y, int player,int base) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.base = base;
    }

    /**
     * Ustawia gracza na danym polu
     * @param player idgracza
     */
    public void setPlayer(int player) {
        this.player = player;
    }

    /**
     * Pobiera czyją bazę zawiera pole
     * @return id bazy
     */
    public int getBase(){
        return base;
    }

    /**
     * Pobiera id gracza na danym polu
     * @return id gracza
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Zwraca współrzędną X pola
     * @return współrzędna X pola
     */
    public int getX() {
        return x;
    }

    /**
     * Zwraca współrzędną Y pola
     * @return współrzędna Y pola
     */
    public int getY() {
        return y;
    }
}
