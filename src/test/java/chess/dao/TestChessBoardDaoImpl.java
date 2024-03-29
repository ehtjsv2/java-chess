package chess.dao;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.Space;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestChessBoardDaoImpl implements ChessBoardDao {

    protected Map<Integer, ChessBoard> chessBoardDb = new HashMap<>();

    @Override
    public int countAll() {
        ChessBoard chessBoard = chessBoardDb.get(1);
        return chessBoard.getSpaces().size();
    }

    @Override
    public List<Space> findAll() {
        ChessBoard chessBoard = chessBoardDb.get(1);
        return chessBoard.getSpaces();
    }
}
