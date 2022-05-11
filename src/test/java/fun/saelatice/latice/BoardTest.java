package fun.saelatice.latice;

import fun.saelatice.latice.model.Board;
import fun.saelatice.latice.model.Position;
import fun.saelatice.latice.model.square.Square;
import fun.saelatice.latice.model.square.SquareType;
import fun.saelatice.latice.model.tile.Tile;
import fun.saelatice.latice.model.tile.TileColor;
import fun.saelatice.latice.model.tile.TileShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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
    void Should_Init_Board_With_1_Moon() {
        int moons = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.MOON) {
                moons += 1;
            }
        }
        Assertions.assertEquals(1, moons);
    }

    @Test
    void Should_Init_Board_With_64_Normal() {
        int normal = 0;
        for (Square square : this.board.getSquares().values()) {
            if (square.getType() == SquareType.NORMAL) {
                normal += 1;
            }
        }
        Assertions.assertEquals(64, normal);
    }

    @Test
    void Should_Be_Latice_Points_When_4_Tiles_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(4, 4), new Tile(TileColor.ORANGE, TileShape.MARIO));
        this.board.setTile(new Position(6, 4), new Tile(TileColor.BLUE, TileShape.BOWSER));
        this.board.setTile(new Position(5, 3), new Tile(TileColor.GREEN, TileShape.MARIO));

        Assertions.assertEquals(Board.LATICE_POINTS, this.board.getPointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_Trefoil_Points_When_3_Tiles_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(4, 4), new Tile(TileColor.ORANGE, TileShape.MARIO));
        this.board.setTile(new Position(5, 3), new Tile(TileColor.GREEN, TileShape.MARIO));

        Assertions.assertEquals(Board.TREFOIL_POINTS, this.board.getPointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_Double_Points_When_2_Tiles_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(4, 4), new Tile(TileColor.ORANGE, TileShape.MARIO));

        Assertions.assertEquals(Board.DOUBLE_POINTS, this.board.getPointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_0_Point_When_1_Tile_Around() {
        this.board.setTile(new Position(5, 5), new Tile(TileColor.BLUE, TileShape.MARIO));

        Assertions.assertEquals(0, this.board.getPointsAt(new Position(5, 4)));
    }

    @Test
    void Should_Be_0_Point_When_0_Tile_Around() {
        Assertions.assertEquals(0, this.board.getPointsAt(new Position(5, 5)));
    }

    @Test
    void Should_Be_Star_Points_When_Star_Square() {
        Assertions.assertEquals(Board.STAR_POINTS, this.board.getPointsAt(new Position(2, 2)));
    }

    @Test
    void Should_Be_Latice_Plus_Star_Points_When_4_Tiles_Around_And_Star_Square() {
        this.board.setTile(new Position(1, 2), new Tile(TileColor.BLUE, TileShape.MARIO));
        this.board.setTile(new Position(2, 1), new Tile(TileColor.ORANGE, TileShape.MARIO));
        this.board.setTile(new Position(2, 3), new Tile(TileColor.PINK, TileShape.MARIO));
        this.board.setTile(new Position(3, 2), new Tile(TileColor.GREEN, TileShape.MARIO));

        Assertions.assertEquals(Board.LATICE_POINTS + Board.STAR_POINTS, this.board.getPointsAt(new Position(2, 2)));
    }

    @Test
    void Should_Be_2_Tiles_Around_When_Top_Left_Corner() {
        Tile blueMario = new Tile(TileColor.BLUE, TileShape.MARIO);
        Tile bluePeach = new Tile(TileColor.BLUE, TileShape.PEACH);

        this.board.setTile(new Position(1, 2), blueMario);
        this.board.setTile(new Position(2, 1), bluePeach);

        Set<Tile> aroundTiles = new HashSet<>();
        aroundTiles.add(bluePeach);
        aroundTiles.add(blueMario);

        Assertions.assertEquals(aroundTiles, this.board.getAroundTiles(1, 1));
    }

    @Test
    void Should_Be_3_Tiles_Around_When_Left_Border() {
        Tile blueMario = new Tile(TileColor.BLUE, TileShape.MARIO);
        Tile bluePeach = new Tile(TileColor.BLUE, TileShape.PEACH);
        Tile blueBowser = new Tile(TileColor.BLUE, TileShape.BOWSER);

        this.board.setTile(new Position(3, 1), blueMario);
        this.board.setTile(new Position(4, 2), bluePeach);
        this.board.setTile(new Position(5, 1), blueBowser);

        Set<Tile> aroundTiles = new HashSet<>();
        aroundTiles.add(bluePeach);
        aroundTiles.add(blueMario);
        aroundTiles.add(blueBowser);

        Assertions.assertEquals(aroundTiles, this.board.getAroundTiles(4, 1));
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

        Set<Tile> aroundTiles = new HashSet<>();
        aroundTiles.add(bluePeach);
        aroundTiles.add(blueMario);
        aroundTiles.add(blueBowser);
        aroundTiles.add(blueYoshi);

        Assertions.assertEquals(aroundTiles, this.board.getAroundTiles(5, 4));
    }

    @Test
    void Should_Be_2_Tiles_Around_When_No_Border() {
        Tile greenToad = new Tile(TileColor.GREEN, TileShape.TOAD);
        Tile yellowToad = new Tile(TileColor.YELLOW, TileShape.TOAD);

        this.board.setTile(new Position(5, 3), greenToad);
        this.board.setTile(new Position(6, 4), yellowToad);

        Set<Tile> aroundTiles = new HashSet<>();
        aroundTiles.add(greenToad);
        aroundTiles.add(yellowToad);

        Assertions.assertEquals(aroundTiles, this.board.getAroundTiles(5, 4));
    }
}
