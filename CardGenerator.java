package com.kiwimuffin.bigtwo;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Card generating utils
 *
 * @author plin
 */
public class CardGenerator {

    /**
     * Get a hand consisting of a straight with random suits.
     *
     * @param min - starting value
     * @param max - ending value (inclusive)
     * @return a hand with a straight
     */
    public static Hand getStraight(int min, int max) {

        if (max - min != 4) {
            return null;
        }
        Hand hand = new Hand();

        for (int i = min; i <= max; i++) {
            hand.addCard(new Card(i, getRandomSuit()));
        }

        return hand;
    }

    /**
     * Get a hand consisting of a flush.
     *
     * @param suit - the suit of the flush
     * @return a hand with a flush
     */
    public static Hand getFlush(Card.Suit suit) {
        int value = getRandomValue(Card.MIN_VALUE, Card.MAX_VALUE);
        Hand hand = new Hand();
        for (int i = 0; i < 5; i++) {
            Card card;
            do {
                card = new Card(value, suit);
            } while (hand.getCards().contains(card));

            hand.addCard(card);
            value = getRandomValue(Card.MIN_VALUE, Card.MAX_VALUE);
        }

        return hand;
    }

    /**
     * Get a full house consisting of the triple and pair values.
     *
     * @param tripleValue
     * @param pairValue
     * @return a hand with a full house
     */
    public static Hand getFullHouse(int tripleValue, int pairValue) {

        // the triple and pairs can't have the same value
        if(tripleValue == pairValue) {
            return null;
        }

        Deck deck = new Deck();
        Hand hand = new Hand();
        Collections.shuffle(deck.getCards());

        Iterator<Card> iterator = deck.getCards().iterator();
        int tripleCounter = 0;
        int pairCounter = 0;

        while(iterator.hasNext()) {
            Card card = iterator.next();
            if(tripleCounter < 3 && card.getValue() == tripleValue) {
                hand.addCard(card);
                iterator.remove();
                tripleCounter++;
            }
            if(pairCounter < 2 && card.getValue() == pairValue) {
                hand.addCard(card);
                iterator.remove();
                pairCounter++;
            }
            // once the hand consists of 5 cards, exit loop
            if(hand.getCards().size() == 5) {
                break;
            }
        }

        return hand;
    }

    public static Hand getRoyalFlush(Card.Suit suit) {
        Hand hand = new Hand();

        for(int i=10; i <= 14; i++) {
            hand.addCard(new Card(i, suit));
        }

        return hand;
    }

    /**
     * Get a hand consisting of five random cards.
     *
     * @return a random hand
     */
    public static Hand getFiveRandom() {
        int value = getRandomValue(Card.MIN_VALUE, Card.MAX_VALUE);
        Hand hand = new Hand();
        for (int i = 0; i < 5; i++) {
            Card card;
            do {
                card = new Card(value, getRandomSuit());
            } while (hand.getCards().contains(card));

            hand.addCard(card);
            value = getRandomValue(Card.MIN_VALUE, Card.MAX_VALUE);
        }

        return hand;
    }

    /**
     * Returns a random suit (club, diamond, heart, spade).
     *
     * @return a random suit
     */
    public static Card.Suit getRandomSuit() {
        Random random = new Random();
        int r = random.nextInt(Card.Suit.values().length);
        Card.Suit[] suits = Card.Suit.values();
        return suits[r];
    }

    /**
     * Generates a random integer (card value) between min and max, inclusive.
     *
     * @param min - minimum value (inclusive)
     * @param max - maximum value (inclusive)
     * @return - random integer
     */
    public static int getRandomValue(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
