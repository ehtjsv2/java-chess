package chess.dao;

import chess.domain.chessBoard.Space;
import java.util.List;

public interface SpaceDao {

    int countAll();

    List<Space> findAll();
}
