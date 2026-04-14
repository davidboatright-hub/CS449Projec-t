package com.mycompany.sprint3;

class RandomizeMove extends Move {
    long seed;

    RandomizeMove(long seed) {
        super(0, 0, 0, 0);
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "" + seed;
    }
}