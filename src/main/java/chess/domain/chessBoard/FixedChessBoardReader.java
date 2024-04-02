package chess.domain.chessBoard;

import java.util.List;

public class FixedChessBoardReader implements SpaceGenerator {

    private final List<Space> fixedSpace;

    public FixedChessBoardReader(List<Space> fixedSpace) {
        this.fixedSpace = fixedSpace;
    }

    @Override
    public List<Space> generateSpaces() {
        return fixedSpace;
    }
}
