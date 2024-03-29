package chess.dao;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.PreFixSpaceGenerator;
import chess.domain.chessBoard.Space;
import java.util.List;

public class ChessBoardService {

    private final ChessBoardDao spaceDao;

    ChessBoardService(ChessBoardDao spaceDao) {
        this.spaceDao = spaceDao;
    }

    public boolean isExistGame() {
        return spaceDao.countAll() > 0;
    }

    public ChessBoard loadChessBoard() {
        List<Space> spaces = spaceDao.findAll();
        return new ChessBoard(new PreFixSpaceGenerator(spaces));
    }
}