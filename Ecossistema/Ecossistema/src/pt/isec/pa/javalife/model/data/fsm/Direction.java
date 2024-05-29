package pt.isec.pa.javalife.model.data.fsm;

import java.util.Random;

public enum Direction {

    LEFT, RIGHT,UP,DOWN;

    public static Direction direcaoAleatoria() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public Direction oposta() {
        return switch (this) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP -> DOWN;
            case DOWN -> UP;
        };
    }



}
