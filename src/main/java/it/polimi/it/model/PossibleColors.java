package it.polimi.it.model;

public enum PossibleColors {
    BLUE, CYAN, GREEN, YELLOW, WHITE, PINK, DEFAULT, XTILE;

    public enum getColor {
        CYAN {
            @Override
            public String toString() {
                return "CYAN";
            }
        },
        GREEN {
            @Override
            public String toString() {
                return "GREEN";
            }
        },
        BLUE {
            @Override
            public String toString() {
                return "BLUE";
            }
        },
        PINK {
            @Override
            public String toString() {
                return "PINK";
            }
        },
        YELLOW {
            @Override
            public String toString() {
                return "YELLOW";
            }
        },
        WHITE {
            @Override
            public String toString() {
                return "WHITE";
            }
        },
        DEFAULT {

            @Override
            public String toString() {
                return "DEFAULT";
            }
        },
        XTILE {

            @Override
            public String toString() {
                return "XTILE";
            }
        };
    }
}
