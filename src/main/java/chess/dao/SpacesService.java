package chess.dao;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.PreFixSpaceGenerator;
import chess.domain.chessBoard.Space;
import java.util.List;

public class SpacesService {

    private final SpacesDao spacesDao;

    public SpacesService(SpacesDao spacesDao) {
        this.spacesDao = spacesDao;
    }

    public boolean isExistGame() {
        return spacesDao.countAll() > 0;
    }

    public ChessBoard loadChessBoard() {
        List<Space> spaces = spacesDao.findAll();
        return new ChessBoard(new PreFixSpaceGenerator(spaces));
    }

    public void saveChessBoard(ChessBoard chessBoard) {
        if (isExistGame()) {
            spacesDao.updateBoard(chessBoard.getSpaces());
            return;
        }
        spacesDao.insertAll(chessBoard.getSpaces());
    }

    public void deleteAll() {
        spacesDao.deleteAll();
    }
}
