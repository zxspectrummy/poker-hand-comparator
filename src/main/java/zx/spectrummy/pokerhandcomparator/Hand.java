package zx.spectrummy.pokerhandcomparator;


import java.util.*;

public class Hand implements Comparable<Hand> {
    private final List<Card> cards;
    private final Set<Suit> suits;
    private final TreeMap<Integer, Integer> ranks;

    public static Hand fromString(String stringValue) {
        int pos = 0;
        List<Card>cards = new ArrayList<>();
        while (pos<stringValue.length()-1) {
            cards.add(Card.fromString(stringValue.substring(pos,pos+2)));
            pos +=2;
        }
        return new Hand(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Hand(List<Card>cards) {
        this.cards = cards;
        this.suits = new HashSet<>();
        this.ranks = new TreeMap<>();
        for(Card card: cards) {
            this.suits.add(card.suit);
            this.ranks.put(card.rank, this.ranks.getOrDefault(card.rank, 0) + 1);
        }
    }

    public HandRank getRank() {
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
        return this.ranks.containsValue(2);
    }

    private boolean isTwoPairs() {
        return this.ranks.containsValue(2) && this.ranks.size()==3;
    }

    private boolean isThreeOfKind() {
        return this.ranks.containsValue(3);
    }

    private boolean isFourOfKind() {
        return this.ranks.containsValue(4);
    }

    private boolean isFullHouse() {
        return this.ranks.containsValue(2) && this.ranks.containsValue(3);
    }

    private boolean isStraightFlush() {
        return this.isFlush() && this.isStraight();
    }

    private boolean isFlush() {
        return this.suits.size() == 1;
    }

    private boolean isStraight() {
        return this.ranks.lastKey() - this.ranks.firstKey() == 4;
    }

    @Override
    public int compareTo(Hand hand) {
        return 0;
    }
}
