package chess.dao;

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

    public List<Space> loadSpaces() {
        return spacesDao.findAll();
    }

    public void saveChessBoard(List<Space> spaces) {
        if (isExistGame()) {
            spacesDao.updateBoard(spaces);
            return;
        }
        spacesDao.insertAll(spaces);
    }

    public void deleteAll() {
        spacesDao.deleteAll();
    }
}
