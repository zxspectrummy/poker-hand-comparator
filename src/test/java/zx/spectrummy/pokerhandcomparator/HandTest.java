package zx.spectrummy.pokerhandcomparator;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandTest {

    @Test
    public void testHandContainsFlush() {
        assertEquals(Hand.fromString("Jh2h7h8h5h").getRank(), HandRank.FLUSH);
    }

    @Test
    public void testHandContainsStraight() {
        assertEquals(Hand.fromString("5h6c7d8d9c").getRank(), HandRank.STRAIGHT);
    }

    @Test
    public void testHandContainsStraightFlush() {
        assertEquals(Hand.fromString("5d6d7d8d9d").getRank(), HandRank.STRAIGHTFLUSH);
    }

    @Test
    public void testHandContainsFourOfKind() {
        assertEquals(Hand.fromString("5s5h5d5c9d").getRank(), HandRank.FOUROFAKIND);
    }

    @Test
    public void testHandContainsThreeOfKind() {
        assertEquals(Hand.fromString("5s5h5dAdQc").getRank(), HandRank.THREEOFAKIND);
    }

    @Test
    public void testHandContainsFullHouse() {
        assertEquals(Hand.fromString("5s5h5dAdAc").getRank(), HandRank.FULLHOUSE);
    }

    @Test
    public void testHandContainsTwoPairs() {
        assertEquals(Hand.fromString("5s5h6dAdAc").getRank(), HandRank.TWOPAIRS);
    }

    @Test
    public void testHandContainsOnePair() {
        assertEquals(Hand.fromString("5s5h6dAdJc").getRank(), HandRank.ONEPAIR);
    }

    @Test
    public void testHandContainsHighCard() {
        assertEquals(Hand.fromString("5s6h7dAdJc").getRank(), HandRank.HIGHCARD);
    }

    @Test
    public void testCompareHighCard() {
        assertEquals(0, Hand.fromString("5s6h7dAdJc").compareTo(Hand.fromString("Jc6h5s7dAd")));
        assertEquals(1, Hand.fromString("5s6h7dAdJc").compareTo(Hand.fromString("Jc6h5s7dQd")));
        assertEquals(-1, Hand.fromString("5s6h7dQdTc").compareTo(Hand.fromString("Jc6h5s7dQd")));
    }

    @Test
    public void testCompareCardsWithDifferentRanks() {
        assertThat(Hand.fromString("5h6h7hAhQh"), lessThan(Hand.fromString("AcKcQcJcTc")));
    }

    @Test
    public void testCompareTwoPairs() {
        assertThat(Hand.fromString("6h6h5d5cAh"), lessThan(Hand.fromString("6d6h7c7dQd")));
        assertThat(Hand.fromString("8h8h5d5cAh").compareTo(Hand.fromString("5d5h8c8dAd")), equalTo(0));
        assertThat(Hand.fromString("QdQhAcAdKd"), greaterThan(Hand.fromString("AhAh5d5cKh")));
    }

    @Test
    public void compareOnePairs() {
        assertThat(Hand.fromString("6h6h2c8hJc"), lessThan(Hand.fromString("6d6h5c2dAd")));
        assertThat(Hand.fromString("8h5h5d3cQh").compareTo(Hand.fromString("5d5h8c3dQd")), equalTo(0));
        assertThat(Hand.fromString("QdQhAcAdKd"), greaterThan(Hand.fromString("AhAh5d5cKh")));
    }

    @Test
    public void compareFullHouses() {
        assertThat(Hand.fromString("6h6s6cJhJc"), lessThan(Hand.fromString("JhJdJs6d6c")));
    }

    @Test
    public void compareThreeOfKinds() {
        assertThat(Hand.fromString("6h6s6cJhTc"), lessThan(Hand.fromString("AhQh6h6s6c")));
        assertThat(Hand.fromString("5h5s5cJhTc"), lessThan(Hand.fromString("JhTc6h6s6c")));
    }

    @Test
    public void compareStraights() {
        assertThat(Hand.fromString("2h4s6c5h3c"), lessThan(Hand.fromString("8hTd6s7d9c")));
    }
}