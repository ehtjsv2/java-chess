package chess.domain.chessBoard;

import java.util.List;

public class PreFixSpaceGenerator implements SpaceGenerator {

    private final List<Space> prefixSpace;

    public PreFixSpaceGenerator(List<Space> prefixSpace) {
        this.prefixSpace = prefixSpace;
    }

    @Override
    public List<Space> generateSpaces() {
        return prefixSpace;
    }
}
