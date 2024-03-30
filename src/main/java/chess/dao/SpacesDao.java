package chess.dao;

import chess.domain.chessBoard.Space;
import java.util.List;

public interface SpacesDao {

    int countAll();

    List<Space> findAll();

    void updateBoard(List<Space> spaces);

    void insertAll(List<Space> spaces);

    void deleteAll();
}
