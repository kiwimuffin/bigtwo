package com.kiwimuffin.bigtwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a standard deck of 52 playing cards.
 *
 * @author plin
 */
public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {
        buildDeck();
    }

    private void buildDeck() {
        for(int i=Card.MIN_VALUE; i <= Card.MAX_VALUE; i++) {
            cards.add(new Card(i, Card.Suit.CLUB));
            cards.add(new Card(i, Card.Suit.DIAMOND));
            cards.add(new Card(i, Card.Suit.HEART));
            cards.add(new Card(i, Card.Suit.SPADE));
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Deck{ ");
        for(Card card : cards) {
            stringBuilder.append(card.getDisplayValue()).append(card.getSuit().getUnicode()).append(" ");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
