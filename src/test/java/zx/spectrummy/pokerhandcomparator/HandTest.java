package zx.spectrummy.pokerhandcomparator;


import org.junit.jupiter.api.Test;

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
}