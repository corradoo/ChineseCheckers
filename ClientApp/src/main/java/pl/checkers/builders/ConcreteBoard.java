package pl.checkers.builders;

import pl.checkers.Field;

import java.util.ArrayList;
/**
 * Klasa tworząca pola dla standarowego rozmiaru planszy
 * */
public abstract class ConcreteBoard implements BoardBuilder {

    protected ArrayList<Field> fields = new ArrayList<>();
    protected static final int gapX = 20,gapY = 17;
    protected static final int oddRowX = 10;

    /**
     * Funkcja tworząca promienie na planszy
     * dla podstawowego rozmiaru planszy
     */
    public void drawBase( int beginX,int beginY,int h, boolean invert, int player, int base) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Field f;

                    if (i % 2 == 0) {
                        f = new Field(j * gapX, i * gapY, player, base);
                    } else {
                        f = new Field(j * gapX + oddRowX, i * gapY,player,base);
                    }

                    fields.add(f);
                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for(int j = beginX; j<=endX;j++) {
                    Field f;

                    if (i % 2 == 0) {
                        f = new Field(j * gapX, i * gapY,player,base);
                    } else {
                        f = new Field(j * gapX + oddRowX, i * gapY,player,base);
                    }
                    fields.add(f);
                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    /**
     * Implementacja metody tworząca środkową część planszy bez baz
     * dla podstawowego rozmiaru planszy
     */
    public void drawBoard() {

        int beginX = 17 / 2 - 2, endX = beginX + 5;
        int beginY = 5, endY = 14, middleY = 9;
        for (int i = beginY; i < endY; i++) {
            for (int j = beginX; j < endX; j++) {
                Field f;
                if (i % 2 == 0) {
                    f = new Field(j * gapX, i * gapY,0,0);
                } else {
                    f = new Field(j * gapX + oddRowX, i * gapY,0,0);
                }
                fields.add(f);
            }

            if (i >= middleY) {
                if (i % 2 == 1) beginX++;
                else endX--;
            } else {
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        }
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
}
