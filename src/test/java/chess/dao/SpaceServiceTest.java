package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.chessBoard.TestCustomChessSpaceGenerator;
import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.Space;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpaceServiceTest {

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
        testSpaceDao.chessBoardDb = Map.of(1, createPreFixChessBoard(List.of()));
        SpaceService spaceService = new SpaceService(testSpaceDao);

        // when
        boolean isExist = spaceService.isExistGame();

        // then
        assertThat(isExist).isFalse();
    }

    @Test
    @DisplayName("진행 중인 체스보드를 가져옵니다")
    void should_load_chess_board() {
        // given
        TestSpaceDaoImpl testSpaceDao = new TestSpaceDaoImpl();
        Space space1 = new Space(new King(Color.WHITE), new Position(File.a, Rank.ONE));
        Space space2 = new Space(new King(Color.BLACK), new Position(File.b, Rank.TWO));
        testSpaceDao.chessBoardDb = Map.of(1, createPreFixChessBoard(List.of(space1, space2)));
        SpaceService spaceService = new SpaceService(testSpaceDao);

        // when
        ChessBoard chessBoard = spaceService.loadChessBoard();

        // given
        assertThat(chessBoard.getSpaces()).hasSize(2);
    }

    private ChessBoard createPreFixChessBoard(List<Space> spaces) {
        return new ChessBoard(new TestCustomChessSpaceGenerator(spaces));
    }
}
