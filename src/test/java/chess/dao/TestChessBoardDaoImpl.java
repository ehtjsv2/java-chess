package chess.dao;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.Space;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestChessBoardDaoImpl implements ChessBoardDao {

    private final int LOAD_GAME_NUMBER = 1;

    protected Map<Integer, ChessBoard> chessBoardDb = new HashMap<>();

    @Override
    public int countAll() {
        ChessBoard chessBoard = chessBoardDb.get(LOAD_GAME_NUMBER);
        return chessBoard.getSpaces().size();
    }

    @Override
    public List<Space> findAll() {
        ChessBoard chessBoard = chessBoardDb.get(LOAD_GAME_NUMBER);
        return chessBoard.getSpaces();
    }

    @Override
    public void updateBoard(List<Space> spaces) {
        //TODO: 프로덕트가 불변객체로 되어있어 변경할 방법이 없다
    }

    @Override
    public void insertAll(List<Space> spaces) {
        //TODO: 프로덕트가 불변객체로 되어있어 변경할 방법이 없다
    }
}
