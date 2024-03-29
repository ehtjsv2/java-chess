package chess.dao;

import chess.domain.chessBoard.Space;
import java.util.List;

public interface ChessBoardDao {

    int countAll();

    List<Space> findAll();
}
