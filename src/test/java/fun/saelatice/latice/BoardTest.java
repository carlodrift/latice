package fun.saelatice.latice;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.square.SquareType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @BeforeEach
    public void init() {
        this.board = new Board();
        this.board.init();
    }

    @Test
    void Should_Init_Board_With_16_Stars() {
        int stars = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.STAR) {
                stars += 1;
            }
        }
        Assertions.assertEquals(16, stars);
    }

    @Test
    void Should_init_Board_With_1_Moon() {
        int moons = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.MOON) {
                moons += 1;
            }
        }
        Assertions.assertEquals(1, moons);
    }

    @Test
    void Should_init_Board_With_64_Normal() {
        int normal = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.NORMAL) {
                normal += 1;
            }
        }
        Assertions.assertEquals(64, normal);
    }
}
