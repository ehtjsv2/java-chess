package chess.dao;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.PreFixSpaceGenerator;
import chess.domain.chessBoard.Space;
import java.util.List;

public class ChessBoardService {

    private final ChessBoardDao chessBoardDao;

    public ChessBoardService(ChessBoardDao chessBoardDao) {
        this.chessBoardDao = chessBoardDao;
    }

    public boolean isExistGame() {
        return chessBoardDao.countAll() > 0;
    }

    public ChessBoard loadChessBoard() {
        List<Space> spaces = chessBoardDao.findAll();
        return new ChessBoard(new PreFixSpaceGenerator(spaces));
    }

    public void saveChessBoard(ChessBoard chessBoard) {
        if (isExistGame()) {
            chessBoardDao.updateBoard(chessBoard.getSpaces());
            return;
        }
        chessBoardDao.insertAll(chessBoard.getSpaces());
    }

    public void deleteAll() {
        chessBoardDao.deleteAll();
    }
}
