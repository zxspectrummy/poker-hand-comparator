# Description

A simple Java implementation of naive poker hand evaluation

## Prerequisites

* Java 17

## Installation

* build and publish

```
git clone https://github.com/zxspectrummy/poker-hand-comparator.git
cd poker-hand-comparator
./gradlew publishToMavenLocal
```

* add the dependency to your project

```
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation 'zx.spectrummy:poker-hand-comparator:+'
}
```

## Usage

```
import zx.spectrummy.pokerhandcomparator.Hand;
...

Hand hand1 = Hand.fromString("AdJdTh2c2s");
Hand hand2 = Hand.fromString("QsTd9c8hJc");
System.out.println(hand1.compareTo(hand2));

```

For more usage examples see the
project's [unit tests](https://github.com/zxspectrummy/poker-hand-comparator/blob/main/src/test/java/zx/spectrummy/pokerhandcomparator/HandTest.java)

## Known limitations

* a hand like A2345 is not recognized as straight (which is kinda ok since the rules were defined as"the values are
  ordered as given above, with 2 being the lowest and ace the highest value.")
* there is no validation if the hand is a valid poker hand
