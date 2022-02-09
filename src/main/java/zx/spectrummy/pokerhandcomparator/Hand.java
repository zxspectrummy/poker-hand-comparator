package zx.spectrummy.pokerhandcomparator;

import java.util.*;
import java.util.stream.Collectors;


public class Hand implements Comparable<Hand> {
    private final List<Card> cards;
    private final Set<Suit> suits;
    private final TreeMap<Integer, Integer> rankFrequencyMap;
    private final LinkedHashMap<Integer, Integer> sortedFrequencyMap;
    private final HandRank rank;

    public static Hand fromString(String stringValue) {
        int pos = 0;
        List<Card> cards = new ArrayList<>();
        while (pos < stringValue.length() - 1) {
            cards.add(Card.fromString(stringValue.substring(pos, pos + 2)));
            pos += 2;
        }
        return new Hand(cards);
    }

    public Hand(List<Card>cards) {
        this.cards = cards;
        this.suits = new HashSet<>();
        this.rankFrequencyMap = new TreeMap<>();
        for (Card card : cards) {
            this.suits.add(card.getSuit());
            this.rankFrequencyMap.put(card.getRank(), this.rankFrequencyMap.getOrDefault(card.getRank(), 0) + 1);
        }
        // sortedFrequencyMap is used to compare hands with the same rank
        // for example, for the hands "4h 7s Td 7c Th" and "Jd Tc 7d 7h Ts"
        // frequencyMaps will be ((4->1),(7->2),(T->2)) and ((7->2),(10->2),(11->1))
        // to compare the hands ranks we need first to compare the ranks of the higher pair
        // if they are equal the lower pair should be compared, finally, if both pairs are the same
        // the kickers are compared. This can be easily achieved if the frequencyMap is
        // sorted first by value and then by key in the descending order, i.e.
        // ((10->2),(7->2),(4->1)) and ((10->2),(7->2),(11->1))
        // Once it is done we can just iterate over the key sets
        // and compare the corresponding elements.
        this.sortedFrequencyMap = this.rankFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        this.rank = calculateRank(this);
    }

    private static HandRank calculateRank(Hand hand) {
        if (hand.isStraightFlush()) {
            return HandRank.STRAIGHTFLUSH;
        } else if (hand.isFourOfKind()) {
            return HandRank.FOUROFAKIND;
        } else if (hand.isFullHouse()) {
            return HandRank.FULLHOUSE;
        } else if (hand.isFlush()) {
            return HandRank.FLUSH;
        } else if (hand.isStraight()) {
            return HandRank.STRAIGHT;
        } else if (hand.isThreeOfKind()) {
            return HandRank.THREEOFAKIND;
        } else if (hand.isTwoPairs()) {
            return HandRank.TWOPAIRS;
        } else if (hand.isOnePair()) {
            return HandRank.ONEPAIR;
        }
        return HandRank.HIGHCARD;
    }

    private boolean isOnePair() {
        return this.rankFrequencyMap.containsValue(2);
    }

    private boolean isTwoPairs() {
        return this.rankFrequencyMap.containsValue(2) && this.rankFrequencyMap.size() == 3;
    }

    private boolean isThreeOfKind() {
        return this.rankFrequencyMap.containsValue(3);
    }

    private boolean isFourOfKind() {
        return this.rankFrequencyMap.containsValue(4);
    }

    private boolean isFullHouse() {
        return this.rankFrequencyMap.containsValue(2) && this.rankFrequencyMap.containsValue(3);
    }

    private boolean isStraightFlush() {
        return this.isFlush() && this.isStraight();
    }

    private boolean isFlush() {
        return this.suits.size() == 1;
    }

    private boolean isStraight() {
        return this.rankFrequencyMap.lastKey() - this.rankFrequencyMap.firstKey() == 4;
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
        var result = this.getRank().compareTo(anotherHand.getRank());
        if (result == 0) {
            return compareCardRanks(anotherHand);
        }
        return result;
    }

    private int compareCardRanks(Hand anotherHand) {
        int result = 0;
        var keys = this.sortedFrequencyMap.keySet().stream().toList();
        var anotherKeys = anotherHand.sortedFrequencyMap.keySet().stream().toList();
        for (int i = 0; i < keys.size(); i++) {
            result = Integer.compare(keys.get(i), anotherKeys.get(i));
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
