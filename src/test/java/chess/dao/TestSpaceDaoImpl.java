package chess.dao;

import chess.domain.chessBoard.ChessBoard;
import java.util.HashMap;
import java.util.Map;

public class TestSpaceDaoImpl implements SpaceDao {

    protected Map<Integer, ChessBoard> chessBoardDb = new HashMap<>();

    @Override
    public int countAll() {
        return chessBoardDb.values().size();
    }
}
