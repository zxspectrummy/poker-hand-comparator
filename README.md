# Description

A simple Java implementation of naive poker hand evaluation

## Prerequisites

* Java 17

## Installation

* build

```
git clone https://github.com/zxspectrummy/poker-hand-comparator.git
cd poker-hand-comparator
./gradlew publishToMavenLocal
```

* add the dependency to your project

```
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

## Known limitations

* hands like A2345 are not recognized as straight
* there is no validation if the hand is a valid poker hand
