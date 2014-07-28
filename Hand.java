package com.kiwimuffin.bigtwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of cards. Usually contains five cards.
 *
 * @author plin
 */
public class Hand implements Comparable<Hand> {

    private List<Card> cards;

    private HandRank rank;

    public Hand() {
        cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public HandRank getRank() {
        return rank;
    }

    public void setRank(HandRank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Hand{ ");
        for(Card c : cards) {
            string.append(c.getDisplayValue()).append(c.getSuit().getUnicode()).append(" ");
        }
        string.append('}');
        return string.toString();
    }

    @Override
    public int compareTo(Hand otherHand) {
        if(this.rank.getRank() > otherHand.getRank().getRank()) {
            return 1;
        } else if(this.rank.getRank() < otherHand.getRank().getRank()) {
            return -1;
        }

        return 0;
    }
}
