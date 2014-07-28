package com.kiwimuffin.bigtwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains methods to evaluate poker hands.
 *
 * @author plin
 */
public class HandCompareUtils {

    public static void main(String[] args) {
//        Hand straight = CardGenerator.getStraight(3, 7);
        Hand straight2 = CardGenerator.getStraight(5, 9);
//        System.out.println(straight.toString());
//        System.out.println(isStraight(straight));
//
//        Hand flush = CardGenerator.getFlush(Card.Suit.SPADE);
//        System.out.println(flush.toString());
//        System.out.println(isFlush(flush));

//
//        Hand random = CardGenerator.getFiveRandom();
//        System.out.println(random.toString());
//        System.out.println(isFlush(random));

        Hand fullHouse = CardGenerator.getFullHouse(14, 3);
//        System.out.println(fullHouse.toString());
//        System.out.println(isFullHouse(fullHouse));
//        System.out.println(isFourOfAKind(fullHouse));

        Hand royalFlush = CardGenerator.getRoyalFlush(Card.Suit.HEART);
//        System.out.println(royalFlush.toString());
//        System.out.println(isRoyalFlush(royalFlush));

//        Hand hand = new Hand();
//        hand.addCard(new Card(2, Card.Suit.SPADE));
//        hand.addCard(new Card(2, Card.Suit.HEART));
//        hand.addCard(new Card(4, Card.Suit.SPADE));
//        hand.addCard(new Card(5, Card.Suit.CLUB));
//        hand.addCard(new Card(7, Card.Suit.DIAMOND));
//        System.out.println(isPair(hand));

//        Hand hand = CardGenerator.getFullHouse(3, 2);
//        System.out.println(isTwoPairs(hand));

        System.out.println(compareHands(royalFlush, straight2));
    }

    public static int compareHands(Hand hand1, Hand hand2) {
        List<Card> hand1Cards = hand1.getCards();
        List<Card> hand2Cards = hand2.getCards();

        if(hand1Cards.size() != hand2Cards.size()) {
            throw new RuntimeException("Cannot compare hands: different number of cards");
        }

        determineHandRank(hand1);
        determineHandRank(hand2);

        if(hand1.getRank().getRank() > hand2.getRank().getRank()) {
            return 1;
        } else if(hand1.getRank().getRank() < hand2.getRank().getRank()) {
            return -1;
        }

        // Hands are the same type
        switch(hand1.getRank()) {
            // Use the highest ranking card to determine these types
            case HIGH_CARD:
            case PAIR:
            case TWO_PAIR:
            case TRIPLE:
            case STRAIGHT:
            case FLUSH:
            case STRAIGHT_FLUSH:
            case ROYAL_FLUSH:
            {
                Collections.sort(hand1Cards, Collections.reverseOrder());
                Collections.sort(hand2Cards, Collections.reverseOrder());

                Card hand1HighCard = hand1Cards.get(0);
                Card hand2HighCard = hand2Cards.get(0);

                return hand1HighCard.compareTo(hand2HighCard);
            }

            // To determine rank, look at the value for the three of a kind for a full house
            case FULL_HOUSE:
            case FOUR_OF_A_KIND:
            {
                List<Card> cards1 = getHighestRankingCards(hand1);
                List<Card> cards2 = getHighestRankingCards(hand2);

                Card hand1HighCard = cards1.get(0);
                Card hand2HighCard = cards2.get(0);

                return hand1HighCard.compareTo(hand2HighCard);
            }
        }

        return 0;
    }

    /**
     * Determines the rank of the hand and sets it.
     *
     * @param hand
     */
    public static void determineHandRank(Hand hand) {
        if(hand.getCards().size() == 2) {
            if(isOnePair(hand)) {
                hand.setRank(HandRank.PAIR);
                return;
            }
        }

        if(hand.getCards().size() == 3) {
            if(isThreeOfAKind(hand)) {
                hand.setRank(HandRank.TRIPLE);
                return;
            }
        }

        if(hand.getCards().size() == 5) {
            if(isStraight(hand)) {
                hand.setRank(HandRank.STRAIGHT);
            } else if(isFlush(hand)) {
                hand.setRank(HandRank.FLUSH);
            } else if(isFullHouse(hand)) {
                hand.setRank(HandRank.FULL_HOUSE);
            } else if(isFourOfAKind(hand)) {
                hand.setRank(HandRank.FOUR_OF_A_KIND);
            } else if(isStraightFlush(hand)) {
                hand.setRank(HandRank.STRAIGHT_FLUSH);
            } else if(isRoyalFlush(hand)) {
                hand.setRank(HandRank.ROYAL_FLUSH);
            }
            return;
        }

        hand.setRank(HandRank.HIGH_CARD);
    }

    /**
     * Determines if a hand is a straight.
     *
     * @param hand
     * @return true if hand is a straight
     */
    public static boolean isStraight(Hand hand) {
        List<Card> cards = hand.getCards();

        if (cards.size() < 5) {
            return false;
        }

        Collections.sort(cards);
        Card previousCard = null;

        for (Card card : cards) {
            if (previousCard == null) {
                previousCard = card;
                continue;
            }
            // check difference between current and previous is not 1, it is not a straight
            if (card.getValue() - previousCard.getValue() != 1) {
                return false;
            }

            previousCard = card;
        }

        return true;
    }

    /**
     * Determines if a hand is a flush.
     *
     * @param hand
     * @return true if hand is a flush
     */
    public static boolean isFlush(Hand hand) {
        List<Card> cards = hand.getCards();

        if (cards.size() < 5) {
            return false;
        }

        Card.Suit suit = null;

        for (Card card : cards) {
            if (suit == null) {
                suit = card.getSuit();
                continue;
            }
            if (suit != card.getSuit()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if a hand is a full house.
     *
     * @param hand
     * @return true if hand is a full house
     */
    public static boolean isFullHouse(Hand hand) {
        Map<Integer, List<Card>> cardMap = new HashMap<>();
        List<Card> cards = hand.getCards();

        if (cards.size() < 5) {
            return false;
        }

        for (Card card : cards) {
            List<Card> list = cardMap.get(card.getValue());

            // if list is null, then create a new one and add it to the map
            if (list == null) {
                list = new ArrayList<>();
                cardMap.put(card.getValue(), list);
            }

            list.add(card);
        }

        // if there are more or less than 2 distinct card values, it is not a full house
        if (cardMap.size() != 2) {
            return false;
        }

        int previousSet = 0;
        for (Map.Entry<Integer, List<Card>> entry : cardMap.entrySet()) {
            if (previousSet == 0) {
                previousSet = entry.getValue().size();
            } else {
                if (previousSet == 2) {
                    return entry.getValue().size() == 3;
                }
            }

        }

        return false;
    }

    /**
     * Determines if a hand is a straight flush.
     *
     * @param hand
     * @return true if a hand is a straight flush
     */
    public static boolean isStraightFlush(Hand hand) {
        return isStraight(hand) && isFlush(hand);
    }

    /**
     * Determines if a hand is a royal flush.
     *
     * @param hand
     * @return true if a hand is a royal flush
     */
    public static boolean isRoyalFlush(Hand hand) {
        List<Card> cards = hand.getCards();

        if (cards.size() < 5) {
            return false;
        }

        if (!isFlush(hand)) {
            return false;
        }

        Collections.sort(cards);
        Card previousCard = null;

        for (Card card : cards) {
            if (previousCard == null) {
                // if the first card isn't 10, then it cannot be a royal flush, so return false
                if (card.getValue() != 10) {
                    return false;
                }
                previousCard = card;
                continue;
            }

            if (card.getValue() - previousCard.getValue() != 1) {
                return false;
            }

            previousCard = card;
        }

        return true;
    }

    /**
     * Determines if a hand contains only two cards with the same value.
     *
     * Returns false if hand contains more than two cards.
     * Returns false if a hand contains more than one set of pairs.
     * Returns false if a hand contains two cards of different values.
     *
     * @param hand
     * @return true if hand contains exactly 1 pair and no other cards
     */
    public static boolean isOnePair(Hand hand) {
        return hand.getCards().size() == 2 && countPairs(hand) == 1;
    }

    /**
     * Determines if a hand contains exactly 1 pair.
     *
     * Returns false if hand contains more than 1 pair.
     * Returns true if hand contains 1 pair and other cards.
     *
     * @param hand
     * @return true if hand contains 1 pair
     */
    public static boolean isPair(Hand hand) {
        return countPairs(hand) == 1;
    }

    /**
     * Determines if a hand contains two pairs.
     *
     * @param hand
     * @return true if a hand contains two pairs
     */
    public static boolean isTwoPairs(Hand hand) {
        return countPairs(hand) == 2;
    }

    /**
     * Determines if a hand contains three cards of the same value.
     *
     * Returns false if the hand contains more than three cards
     *
     * @param hand
     * @return true if hand is a three of a kind
     */
    public static boolean isThreeOfAKind(Hand hand) {
        return hand.getCards().size() == 3 && sameCards(hand) == 3;
    }

    public static boolean isFourOfAKind(Hand hand) {
        return hand.getCards().size() == 5 && sameCards(hand) == 4;
    }

    /**
     * Determines the maximum number of cards with the same value in the given hand.
     * <p/>
     * For a full house, 3 is returned because it is greater than 2 (the pairs).
     *
     * @param hand
     * @return the count of the highest number of identical cards
     */
    public static int sameCards(Hand hand) {
        List<Card> cards = hand.getCards();
        Map<Integer, List<Card>> cardMap = new HashMap<>();

        for (Card card : cards) {
            List<Card> list = cardMap.get(card.getValue());

            // if list is null, then create a new one and add it to the map
            if (list == null) {
                list = new ArrayList<>();
                cardMap.put(card.getValue(), list);
            }

            list.add(card);
        }

        int count = 0;
        for (Map.Entry<Integer, List<Card>> entry : cardMap.entrySet()) {
            if (entry.getValue().size() > count) {
                count = entry.getValue().size();
            }
        }

        return count;
    }

    /**
     * Returns the number of pairs in the hand.
     *
     * @param hand
     * @return integer representing the number of pairs in the hand
     */
    public static int countPairs(Hand hand) {
        List<Card> cards = hand.getCards();
        Map<Integer, List<Card>> cardMap = new HashMap<>();

        for (Card card : cards) {
            List<Card> list = cardMap.get(card.getValue());

            // if list is null, then create a new one and add it to the map
            if (list == null) {
                list = new ArrayList<>();
                cardMap.put(card.getValue(), list);
            }

            list.add(card);
        }

        int count = 0;
        for(Map.Entry<Integer, List<Card>> entry : cardMap.entrySet()) {
            if(entry.getValue().size() == 2) {
                count++;
            }
        }

        return count;
    }


    /**
     * Returns a list of the highest ranking cards in the hand (usually used to determine hand strength).
     *
     * A full house would return the three of a kind.
     * A four of a kind would return the four identical cards (and not the single card).
     *
     * @param hand
     * @return list of highest cards
     */
    public static List<Card> getHighestRankingCards(Hand hand) {
        List<Card> highCards = new ArrayList<>();

        // Put the cards into a map
        Map<Integer, List<Card>> cardMap = new HashMap<>();
        for(Card card : hand.getCards()) {
            List<Card> list = cardMap.get(card.getValue());

            // if list is null, then create a new one and add it to the map
            if (list == null) {
                list = new ArrayList<>();
                cardMap.put(card.getValue(), list);
            }

            list.add(card);
        }

        // Look for the three of a kind
        if(isFullHouse(hand)) {
            for (Map.Entry<Integer, List<Card>> entry : cardMap.entrySet()) {
                if (entry.getValue().size() == 3) {
                    highCards.addAll(entry.getValue());
                    break;
                }
            }
        }

        // Look for the four of a kind
        if(isFourOfAKind(hand)) {
            for (Map.Entry<Integer, List<Card>> entry : cardMap.entrySet()) {
                if (entry.getValue().size() == 4) {
                    highCards.addAll(entry.getValue());
                    break;
                }
            }
        }

        return highCards;
    }
}
