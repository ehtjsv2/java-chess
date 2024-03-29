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
        TestChessBoardDaoImpl testSpaceDao = new TestChessBoardDaoImpl();
        ChessBoard chessBoard = createPreFixChessBoard(
                List.of(new Space(new Pawn(Color.WHITE), new Position(File.a, Rank.ONE))));
        testSpaceDao.chessBoardDb = Map.of(1, chessBoard);
        ChessBoardService chessBoardService = new ChessBoardService(testSpaceDao);

        // when
        boolean isExist = chessBoardService.isExistGame();

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("진행중인 게임이 없는 지 확인가능하다.")
    void should_know_game_not_exist() {
        // given
        TestChessBoardDaoImpl testSpaceDao = new TestChessBoardDaoImpl();
        testSpaceDao.chessBoardDb = Map.of(1, createPreFixChessBoard(List.of()));
        ChessBoardService chessBoardService = new ChessBoardService(testSpaceDao);

        // when
        boolean isExist = chessBoardService.isExistGame();

        // then
        assertThat(isExist).isFalse();
    }

    @Test
    @DisplayName("진행 중인 체스보드를 가져옵니다")
    void should_load_chess_board() {
        // given
        TestChessBoardDaoImpl testSpaceDao = new TestChessBoardDaoImpl();
        Space space1 = new Space(new King(Color.WHITE), new Position(File.a, Rank.ONE));
        Space space2 = new Space(new King(Color.BLACK), new Position(File.b, Rank.TWO));
        testSpaceDao.chessBoardDb = Map.of(1, createPreFixChessBoard(List.of(space1, space2)));
        ChessBoardService chessBoardService = new ChessBoardService(testSpaceDao);

        // when
        ChessBoard chessBoard = chessBoardService.loadChessBoard();

        // given
        assertThat(chessBoard.getSpaces()).hasSize(2);
    }

    private ChessBoard createPreFixChessBoard(List<Space> spaces) {
        return new ChessBoard(new TestCustomChessSpaceGenerator(spaces));
    }
}
