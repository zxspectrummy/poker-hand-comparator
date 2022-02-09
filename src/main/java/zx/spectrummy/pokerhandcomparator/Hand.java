package zx.spectrummy.pokerhandcomparator;

import java.util.*;
import java.util.stream.Collectors;


public class Hand implements Comparable<Hand> {
    private final List<Card> cards;
    private final Set<Suit> suits;
    private final LinkedHashMap<Integer, Integer> sortedFrequencyMap;
    private final HandRank rank;

    /**
     * Creates new Hand object from a string representation
     * where each card is defined as a two symbol string RS where
     * R - card rank ['2'-'9','A','K','Q','J','T']
     * S - card suit ['c','d','h','s']
     *
     * @param stringValue a string represenation of poker hand
     * @return Hand object
     */
    public static Hand fromString(String stringValue) {
        int pos = 0;
        List<Card> cards = new ArrayList<>();
        while (pos < stringValue.length() - 1) {
            cards.add(Card.fromString(stringValue.substring(pos, pos + 2)));
            pos += 2;
        }
        return new Hand(cards);
    }

    public Hand(List<Card> cards) {
        this.cards = cards;
        this.suits = new HashSet<>();
        Map<Integer, Integer> rankFrequencyMap = new HashMap<>();
        for (Card card : cards) {
            this.suits.add(card.getSuit());
            rankFrequencyMap.put(card.getRank(), rankFrequencyMap.getOrDefault(card.getRank(), 0) + 1);
        }
        // sortedFrequencyMap is used to compare hands with the same rank
        // for example, for the hands "8h 7s Td 7c Th" and "Jd Tc 7d 7h Ts"
        // frequencyMaps will be ((7->2),(8->1),(T->2)) and ((7->2),(10->2),(11->1))
        // to compare the hands ranks we need first to compare the ranks of the higher pair
        // if they are equal the lower pair should be compared, finally, if both pairs are the same
        // the kickers are compared. This can be easily achieved if the frequencyMap is
        // sorted in the descending order first by value and then by key, i.e.
        // ((10->2),(7->2),(8->1)) and ((10->2),(7->2),(11->1))
        // Once it is done we can just iterate over the key sets
        // and compare the corresponding elements.
        this.sortedFrequencyMap = rankFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        this.rank = calculateRank();
    }

    private HandRank calculateRank() {
        if (this.isStraightFlush()) {
            return HandRank.STRAIGHTFLUSH;
        } else if (this.isFourOfKind()) {
            return HandRank.FOUROFAKIND;
        } else if (this.isFullHouse()) {
            return HandRank.FULLHOUSE;
        } else if (this.isFlush()) {
            return HandRank.FLUSH;
        } else if (this.isStraight()) {
            return HandRank.STRAIGHT;
        } else if (this.isThreeOfKind()) {
            return HandRank.THREEOFAKIND;
        } else if (this.isTwoPairs()) {
            return HandRank.TWOPAIRS;
        } else if (this.isOnePair()) {
            return HandRank.ONEPAIR;
        }
        return HandRank.HIGHCARD;
    }

    private boolean isOnePair() {
        return this.sortedFrequencyMap.containsValue(2);
    }

    private boolean isTwoPairs() {
        return this.sortedFrequencyMap.containsValue(2) && this.sortedFrequencyMap.size() == 3;
    }

    private boolean isThreeOfKind() {
        return this.sortedFrequencyMap.containsValue(3);
    }

    private boolean isFourOfKind() {
        return this.sortedFrequencyMap.containsValue(4);
    }

    private boolean isFullHouse() {
        return this.sortedFrequencyMap.containsValue(2) && this.sortedFrequencyMap.containsValue(3);
    }

    private boolean isStraightFlush() {
        return this.isFlush() && this.isStraight();
    }

    private boolean isFlush() {
        return this.suits.size() == 1;
    }

    private boolean isStraight() {
        if (this.sortedFrequencyMap.size() == 5) {
            int firstKey = this.sortedFrequencyMap.keySet().stream().toList().get(0);
            int lastKey = this.sortedFrequencyMap.keySet().stream().reduce((prev, next) -> next).orElse(null);
            if (firstKey == 14) { // if hand contains an ace
                int secondKey = this.sortedFrequencyMap.keySet().stream().toList().get(1);
                if ((secondKey == 5) && (lastKey == 2)) { // and the rest cards are 2-5 then replace the default ace value (14) with (1)
                    this.sortedFrequencyMap.remove(14);
                    this.sortedFrequencyMap.put(1, 1);
                    return true;
                }
            }
            return firstKey - lastKey == 4;
        }
        return false;
    }

    /**
     * Compares two poker hands rankings.
     *
     * @param anotherHand – a hand to compare
     * @return the value 0 if this hand's rank equals the rank of the argument hand;
     * a value less than 0 if this cards rank is lower than the argument's;
     * and a value greater than 0 otherwise
     */
    @Override
    public int compareTo(Hand anotherHand) {
        int result = this.getRank().compareTo(anotherHand.getRank());
        if (result == 0) {
            return compareCardRanks(anotherHand);
        }
        return result;
    }

    private int compareCardRanks(Hand anotherHand) {
        int result = 0;
        List<Integer> ranks = this.sortedFrequencyMap.keySet().stream().toList();
        List<Integer> anotherCardRanks = anotherHand.sortedFrequencyMap.keySet().stream().toList();
        for (int i = 0; i < ranks.size(); i++) {
            result = Integer.compare(ranks.get(i), anotherCardRanks.get(i));
            if (result != 0)
                break;
        }
        return result;
    }

    public HandRank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
