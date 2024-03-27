package chess.chessBoard;

import chess.domain.chessBoard.Space;
import chess.domain.chessBoard.SpaceGenerator;
import java.util.List;

public class TestCustomChessSpaceGenerator implements SpaceGenerator {

    private final List<Space> prefixSpace;

    public TestCustomChessSpaceGenerator(List<Space> prefixSpace) {
        this.prefixSpace = prefixSpace;
    }

    @Override
    public List<Space> generateSpaces() {
        return prefixSpace;
    }
}
