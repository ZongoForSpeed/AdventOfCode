package com.adventofcode.point;

public enum Direction {
    UP() {
        @Override
        public Direction reverse() {
            return DOWN;
        }

        @Override
        public Direction left() {
            return LEFT;
        }

        @Override
        public Direction right() {
            return RIGHT;
        }
    },
    DOWN() {
        @Override
        public Direction reverse() {
            return UP;
        }

        @Override
        public Direction left() {
            return RIGHT;
        }

        @Override
        public Direction right() {
            return LEFT;
        }
    },
    LEFT() {
        @Override
        public Direction reverse() {
            return RIGHT;
        }

        @Override
        public Direction left() {
            return DOWN;
        }

        @Override
        public Direction right() {
            return UP;
        }
    },
    RIGHT() {
        @Override
        public Direction reverse() {
            return LEFT;
        }

        @Override
        public Direction left() {
            return UP;
        }

        @Override
        public Direction right() {
            return DOWN;
        }
    };

    public abstract Direction reverse();

    public abstract Direction left();

    public abstract Direction right();
}
