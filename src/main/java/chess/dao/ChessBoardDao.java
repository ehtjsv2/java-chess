package chess.dao;

import chess.domain.chessBoard.Space;
import java.util.List;

public interface ChessBoardDao {

    int countAll();

    List<Space> findAll();

    void updateBoard(List<Space> spaces);

    void insertAll(List<Space> spaces);
}
