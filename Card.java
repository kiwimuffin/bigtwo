package com.pslin.cards;

/**
 * Represents a single playing card.
 *
 * @author plin
 */
public class Card implements Comparable<Card> {

    public static final int MAX_VALUE = 14;

    public static final int MIN_VALUE = 2;

    private int value;

    private Suit suit;

    public enum Suit {
        CLUB("\u2663", 0),
        DIAMOND("\u2666", 1),
        HEART("\u2665", 2),
        SPADE("\u2660", 3);

        private final String unicode;

        private final int rank;

        Suit(String unicode, int rank) {
            this.unicode = unicode;
            this.rank = rank;
        }

        public String getUnicode() {
            return unicode;
        }

        public int getRank() {
            return rank;
        }
    }

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public String getDisplayValue() {
        if(value == MAX_VALUE) {
            return "A";
        }
        if(value == 11) {
            return "J";
        }
        if(value == 12) {
            return "Q";
        }
        if(value == 13) {
            return "K";
        }
        return String.valueOf(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public int compareTo(Card o) {
        if(value > o.getValue())
            return 1;
        if(value < o.getValue())
            return -1;

        // Number value is the same so look at suit rank
        if(suit.getRank() > o.getSuit().getRank())
            return 1;
        if(suit.getRank() < o.getSuit().getRank())
            return -1;

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (value != card.value) return false;
        if (suit != card.suit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + suit.hashCode();
        return result;
    }
}
