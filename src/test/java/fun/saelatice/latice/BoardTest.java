package fun.saelatice.latice;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Position;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.square.SquareType;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
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
        assertEquals(16, stars);
    }

    @Test
    void Should_Init_Board_With_1_Moon() {
        int moons = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.MOON) {
                moons += 1;
            }
        }
        assertEquals(1, moons);
    }

    @Test
    void Should_Init_Board_With_64_Normal() {
        int normal = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.NORMAL) {
                normal += 1;
            }
        }
        assertEquals(64, normal);
    }

    @Test
    void Should_Be_Latice_Points_When_4_Tiles_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(4, 4), new Tile(TileColor.ORANGE, TileShape.MARIO));
        this.board.setTile(new Position(6, 4), new Tile(TileColor.BLUE, TileShape.BOWSER));
        this.board.setTile(new Position(5, 3), new Tile(TileColor.GREEN, TileShape.MARIO));

        assertEquals(Board.LATICE_POINTS, this.board.pointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_Trefoil_Points_When_3_Tiles_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(4, 4), new Tile(TileColor.ORANGE, TileShape.MARIO));
        this.board.setTile(new Position(5, 3), new Tile(TileColor.GREEN, TileShape.MARIO));

        assertEquals(Board.TREFOIL_POINTS, this.board.pointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_Double_Points_When_2_Tiles_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(4, 4), new Tile(TileColor.ORANGE, TileShape.MARIO));

        assertEquals(Board.DOUBLE_POINTS, this.board.pointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_0_Point_When_1_Tile_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));

        assertEquals(0, this.board.pointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_0_Point_When_0_Tile_Around() {
        assertEquals(0, this.board.pointsAt(new Position(5, 5)));
    }

    @Test
    void Should_Be_Star_Points_When_Star_Square() {
        assertEquals(Board.STAR_POINTS, this.board.pointsAt(new Position(2, 2)));
    }

    @Test
    void Should_Be_Latice_Plus_Star_Points_When_4_Tiles_Around_And_Star_Square() {
        this.board.setTile(new Position(1, 2), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(2, 1), new Tile(TileColor.ORANGE, TileShape.MARIO));
        this.board.setTile(new Position(2, 3), new Tile(TileColor.PINK, TileShape.MARIO));
        this.board.setTile(new Position(3, 2), new Tile(TileColor.GREEN, TileShape.MARIO));

        assertEquals(Board.LATICE_POINTS + Board.STAR_POINTS, this.board.pointsAt(new Position(2, 2)));
    }

    @Test
    void Should_Be_2_Tiles_Around_When_Top_Left_Corner() {
        Tile blueMario = new Tile(TileColor.BLUE, TileShape.MARIO);
        Tile bluePeach = new Tile(TileColor.BLUE, TileShape.PEACH);
        this.board.setTile(new Position(0, 1), blueMario);
        this.board.setTile(new Position(1, 0), bluePeach);

        List<Tile> aroundTiles = this.board.aroundTiles(0, 0);

        assertThat(aroundTiles).containsExactlyInAnyOrder(blueMario, bluePeach);
    }

    @Test
    void Should_Be_2_Tiles_Around_When_Bottom_Right_Corner() {
        Tile blueMario = new Tile(TileColor.BLUE, TileShape.MARIO);
        Tile bluePeach = new Tile(TileColor.BLUE, TileShape.PEACH);
        this.board.setTile(new Position(8, 7), blueMario);
        this.board.setTile(new Position(7, 8), bluePeach);

        List<Tile> aroundTiles = this.board.aroundTiles(8, 8);

        assertThat(aroundTiles).containsExactlyInAnyOrder(blueMario, bluePeach);
    }

    @Test
    void Should_Be_3_Tiles_Around_When_Left_Border() {
        Tile blueMario = new Tile(TileColor.BLUE, TileShape.MARIO);
        Tile bluePeach = new Tile(TileColor.BLUE, TileShape.PEACH);
        Tile blueBowser = new Tile(TileColor.BLUE, TileShape.BOWSER);
        this.board.setTile(new Position(3, 0), blueMario);
        this.board.setTile(new Position(4, 1), bluePeach);
        this.board.setTile(new Position(5, 0), blueBowser);

        List<Tile> aroundTiles = this.board.aroundTiles(4, 0);

        assertThat(aroundTiles).containsExactlyInAnyOrder(blueMario, bluePeach, blueBowser);
    }

    @Test
    void Should_Be_4_Tiles_Around_When_No_Border() {
        Tile blueMario = new Tile(TileColor.BLUE, TileShape.MARIO);
        Tile bluePeach = new Tile(TileColor.BLUE, TileShape.PEACH);
        Tile blueBowser = new Tile(TileColor.BLUE, TileShape.BOWSER);
        Tile blueYoshi = new Tile(TileColor.BLUE, TileShape.YOSHI);
        this.board.setTile(new Position(5, 3), blueMario);
        this.board.setTile(new Position(6, 4), bluePeach);
        this.board.setTile(new Position(4, 4), blueBowser);
        this.board.setTile(new Position(5, 5), blueYoshi);

        List<Tile> aroundTiles = this.board.aroundTiles(5, 4);

        assertThat(aroundTiles).containsExactlyInAnyOrder(blueMario, bluePeach, blueBowser, blueYoshi);
    }

    @Test
    void Should_Be_2_Tiles_Around_When_No_Border() {
        Tile greenToad = new Tile(TileColor.GREEN, TileShape.TOAD);
        Tile yellowToad = new Tile(TileColor.YELLOW, TileShape.TOAD);
        this.board.setTile(new Position(5, 3), greenToad);
        this.board.setTile(new Position(6, 4), yellowToad);

        List<Tile> aroundTiles = this.board.aroundTiles(5, 4);

        assertThat(aroundTiles).containsExactlyInAnyOrder(greenToad, yellowToad);
    }

    @Test
    void Should_Be_Able_To_Play_On_Moon_Square_When_No_Other_Tile() {
        Tile greenYoshi = new Tile(TileColor.GREEN, TileShape.YOSHI);
        assertTrue(this.board.canPlayHere(new Position(4, 4), greenYoshi));
    }

    @Test
    void Should_Not_Be_Able_To_Play_When_No_Other_Tile() {
        Tile redYoshi = new Tile(TileColor.RED, TileShape.YOSHI);
        assertFalse(this.board.canPlayHere(new Position(2, 6), redYoshi));
    }

    @Test
    void Should_Be_Able_To_Play_When_Compatibles_Tiles_Around() {
        Tile yellowYoshi = new Tile(TileColor.YELLOW, TileShape.YOSHI);
        Tile blueYoshi = new Tile(TileColor.BLUE, TileShape.YOSHI);
        this.board.setTile(new Position(2, 2), yellowYoshi);
        this.board.setTile(new Position(3, 3), blueYoshi);

        Tile greenYoshi = new Tile(TileColor.GREEN, TileShape.YOSHI);
        assertTrue(this.board.canPlayHere(new Position(3, 2), greenYoshi));
    }

    @Test
    void Should_Not_Be_Able_To_Play_When_Tile_Placed_On_Another_Tile() {
        Tile blueYoshi = new Tile(TileColor.BLUE, TileShape.YOSHI);
        this.board.setTile(new Position(3, 3), blueYoshi);

        Tile redYoshi = new Tile(TileColor.RED, TileShape.YOSHI);
        assertFalse(this.board.canPlayHere(new Position(3, 3), redYoshi));
    }

    @Test
    void Should_Not_Be_Able_To_Play_When_No_Compatible_Tiles_Around() {
        Tile pinkPeach = new Tile(TileColor.PINK, TileShape.PEACH);
        this.board.setTile(new Position(3, 3), pinkPeach);
        Tile redYoshi = new Tile(TileColor.GREEN, TileShape.YOSHI);
        assertFalse(this.board.canPlayHere(new Position(3, 2), redYoshi));
    }
}
