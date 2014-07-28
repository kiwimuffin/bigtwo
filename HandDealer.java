package com.pslin.cards;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author plin
 */
public class HandDealer {

    public static void main(String[] args) {
        // Create a new deck and shuffle it
        Deck deck = new Deck();
        Collections.shuffle(deck.getCards());

        // Deal the cards
        List<Card> player1 = deck.getCards().subList(0, 13);
        List<Card> player2 = deck.getCards().subList(13, 26);
        List<Card> player3 = deck.getCards().subList(26, 39);
        List<Card> player4 = deck.getCards().subList(39, 52);

        // Sort hands
        Collections.sort(player1);
        Collections.sort(player2);
        Collections.sort(player3);
        Collections.sort(player4);

        // Print hands
        printHand(player1);
        printHand(player2);
        printHand(player3);
        printHand(player4);
    }

    private static void printHand(List<Card> hand) {
        Iterator<Card> i = hand.iterator();

        if(i.hasNext()) {
            Card card = i.next();
            System.out.print(card.getDisplayValue() + card.getSuit().getUnicode());
        }

        while(i.hasNext()) {
            Card card = i.next();
            System.out.print(" : " + card.getDisplayValue() + card.getSuit().getUnicode());
        }
        System.out.println();
    }
}
