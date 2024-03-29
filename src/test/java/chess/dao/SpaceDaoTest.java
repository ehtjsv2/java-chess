package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.chessBoard.TestCustomChessSpaceGenerator;
import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.Space;
import chess.domain.piece.Color;
import chess.domain.piece.Pawn;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpaceDaoTest {

    @Test
    @DisplayName("진행중인 게임이 있는 지 확인가능하다.")
    void should_know_game_exist() {
        // given
        TestSpaceDaoImpl testSpaceDao = new TestSpaceDaoImpl();
        ChessBoard chessBoard = createPreFixChessBoard(
                List.of(new Space(new Pawn(Color.WHITE), new Position(File.a, Rank.ONE))));
        testSpaceDao.chessBoardDb = Map.of(1, chessBoard);
        SpaceService spaceService = new SpaceService(testSpaceDao);

        // when
        boolean isExist = spaceService.isExistGame();

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("진행중인 게임이 없는 지 확인가능하다.")
    void should_know_game_not_exist() {
        // given
        TestSpaceDaoImpl testSpaceDao = new TestSpaceDaoImpl();
        testSpaceDao.chessBoardDb = Map.of();
        SpaceService spaceService = new SpaceService(testSpaceDao);

        // when
        boolean isExist = spaceService.isExistGame();

        // then
        assertThat(isExist).isFalse();
    }

    private ChessBoard createPreFixChessBoard(List<Space> spaces) {
        return new ChessBoard(new TestCustomChessSpaceGenerator(spaces));
    }
}
