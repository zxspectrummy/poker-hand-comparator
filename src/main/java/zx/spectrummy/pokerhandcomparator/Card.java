package zx.spectrummy.pokerhandcomparator;

import java.util.Objects;

import static java.util.Comparator.comparingInt;

public class Card implements Comparable<Card> {
    Suit suit;
    int rank;

    public Card(int rank, Suit suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public static Card fromString(String stringValue) {
        return new Card(parseRank(stringValue.charAt(0)), parseSuit(stringValue.charAt(1)));
    }

    private static Suit parseSuit(char suit) {
        var result = switch (suit) {
            case 'h', 'H' -> Suit.HEARTS;
            case 'd', 'D' -> Suit.DIAMONDS;
            case 'c', 'C' -> Suit.CLUBS;
            case 's', 'S' -> Suit.SPADES;
            default -> throw new IllegalArgumentException("Cannot parse suit");
        };
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }

    private static int parseRank(char rank) {
        var value = switch (rank) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            case '2', '3', '4', '5', '6', '7', '8', '9' -> Character.getNumericValue(rank);
            default -> throw new IllegalArgumentException("Cannot parse card rank");
        };
        return value;
    }

    @Override
    public int compareTo(Card anotherCard) {
        return comparingInt((Card c) -> c.rank).compare(this, anotherCard);
    }
}
