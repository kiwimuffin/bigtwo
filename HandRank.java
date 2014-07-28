package com.pslin.cards;

/**
 * @author plin
 */
public enum HandRank {

    HIGH_CARD(0),
    PAIR(1),
    TWO_PAIR(2),
    TRIPLE(3),
    STRAIGHT(4),
    FLUSH(5),
    FULL_HOUSE(6),
    FOUR_OF_A_KIND(7),
    STRAIGHT_FLUSH(8),
    ROYAL_FLUSH(9);

    private int rank;

    private HandRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
