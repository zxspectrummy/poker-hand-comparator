package zx.spectrummy.pokerhandcomparator;

import java.util.Objects;

import static java.util.Comparator.comparingInt;

public class Card implements Comparable<Card> {

    private final Suit suit;
    private final int rank;

    public Card(int rank, Suit suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public static Card fromString(String stringValue) {
        return new Card(parseRank(stringValue.charAt(0)), parseSuit(stringValue.charAt(1)));
    }

    private static Suit parseSuit(char suit) {
        return switch (suit) {
            case 'h', 'H' -> Suit.HEARTS;
            case 'd', 'D' -> Suit.DIAMONDS;
            case 'c', 'C' -> Suit.CLUBS;
            case 's', 'S' -> Suit.SPADES;
            default -> throw new IllegalArgumentException("Cannot parse suit");
        };
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
        char rankAsChar = switch (rank) {
            case 10 -> 'T';
            case 11 -> 'J';
            case 12 -> 'Q';
            case 13 -> 'K';
            case 14 -> 'A';
            default -> Character.forDigit(rank, 10);
        };
        char suitAsChar = switch (suit) {
            case CLUBS -> 'c';
            case HEARTS -> 'h';
            case DIAMONDS -> 'd';
            case SPADES -> 's';
        };
        return new StringBuilder().append(rankAsChar).append(suitAsChar).toString();
    }

    private static int parseRank(char rank) {
        return switch (rank) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            case '2', '3', '4', '5', '6', '7', '8', '9' -> Character.getNumericValue(rank);
            default -> throw new IllegalArgumentException("Cannot parse card rank");
        };
    }

    @Override
    public int compareTo(Card anotherCard) {
        return comparingInt((Card c) -> c.rank).compare(this, anotherCard);
    }
}
