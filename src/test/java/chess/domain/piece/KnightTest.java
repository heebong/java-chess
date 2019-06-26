package chess.domain.piece;

import chess.domain.board.*;
import chess.domain.path.PathFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class KnightTest {
    private Piece knight;

    @BeforeEach
    void setUp() {
        knight = Knight.whiteCreate();
    }

    @Test
    void 나이트_이동_사방() {
        Vectors movableVectors = knight.movableArea(new Square(new XPosition("d"), new YPosition("4")));
        Vectors expectedVectors = new Vectors(new HashSet<>());
        expectedVectors.add(new Vector(new Square(new XPosition("e"), new YPosition("6")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("f"), new YPosition("5")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("f"), new YPosition("3")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("e"), new YPosition("2")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("c"), new YPosition("2")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("b"), new YPosition("3")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("b"), new YPosition("5")), Direction.NONE));
        expectedVectors.add(new Vector(new Square(new XPosition("c"), new YPosition("6")), Direction.NONE));

        assertThat(movableVectors).isEqualTo(expectedVectors);
    }

    @Test
    void 나이트_이동_막힌곳() {
        Vectors movableSquares = knight.movableArea(new Square(new XPosition("b"), new YPosition("1")));
        Vectors expectedSquares = new Vectors(new HashSet<>());
        expectedSquares.add(new Vector(new Square(new XPosition("a"), new YPosition("3")), Direction.NONE));
        expectedSquares.add(new Vector(new Square(new XPosition("c"), new YPosition("3")), Direction.NONE));
        expectedSquares.add(new Vector(new Square(new XPosition("d"), new YPosition("2")), Direction.NONE));

        assertThat(movableSquares).isEqualTo(expectedSquares);
    }
}
