package chess.domain.chessBoard;

import java.util.List;

public class fixedChessBoardReader implements SpaceGenerator {

    private final List<Space> fixedSpace;

    public fixedChessBoardReader(List<Space> fixedSpace) {
        this.fixedSpace = fixedSpace;
    }

    @Override
    public List<Space> generateSpaces() {
        return fixedSpace;
    }
}
