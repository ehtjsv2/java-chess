package chess.domain.chessBoard;

import java.util.List;

public class DbChessBoardReader implements SpaceGenerator {

    private final List<Space> fixedSpace;

    public DbChessBoardReader(List<Space> fixedSpace) {
        this.fixedSpace = fixedSpace;
    }

    @Override
    public List<Space> generateSpaces() {
        return fixedSpace;
    }
}
