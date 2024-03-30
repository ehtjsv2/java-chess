package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.chessBoard.Space;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpaceServiceTest {

    @Test
    @DisplayName("진행중인 게임이 있는 지 확인가능하다.")
    void should_know_game_exist() {
        // given
        TestSpacesDaoImpl testSpaceDao = new TestSpacesDaoImpl();
        testSpaceDao.spaces = List.of(new Space(new Pawn(Color.WHITE), new Position(File.a, Rank.ONE)));
        SpacesService spacesService = new SpacesService(testSpaceDao);

        // when
        boolean isExist = spacesService.isExistGame();

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("진행중인 게임이 없는 지 확인가능하다.")
    void should_know_game_not_exist() {
        // given
        TestSpacesDaoImpl testSpaceDao = new TestSpacesDaoImpl();
        testSpaceDao.spaces = List.of();
        SpacesService spacesService = new SpacesService(testSpaceDao);

        // when
        boolean isExist = spacesService.isExistGame();

        // then
        assertThat(isExist).isFalse();
    }

    @Test
    @DisplayName("진행 중인 체스보드를 가져옵니다")
    void should_load_chess_board() {
        // given
        TestSpacesDaoImpl testSpaceDao = new TestSpacesDaoImpl();
        Space space1 = new Space(new King(Color.WHITE), new Position(File.a, Rank.ONE));
        Space space2 = new Space(new King(Color.BLACK), new Position(File.b, Rank.TWO));
        testSpaceDao.spaces = List.of(space1, space2);
        SpacesService spacesService = new SpacesService(testSpaceDao);

        // when
        List<Space> spaces = spacesService.loadSpaces();

        // given
        assertThat(spaces).hasSize(2);
    }

    @Test
    @DisplayName("진행된 게임이 있을 경우 체스 보드를 저장할 수 있다.")
    void save_chess_board_when_ongoing_game() {
        // given
        TestSpacesDaoImpl testSpaceDao = new TestSpacesDaoImpl();
        Space space1 = new Space(new King(Color.WHITE), new Position(File.a, Rank.ONE));
        Space space2 = new Space(new King(Color.BLACK), new Position(File.b, Rank.TWO));
        testSpaceDao.spaces = new ArrayList<>(Arrays.asList(space1, space2));

        Space space3 = new Space(new Pawn(Color.BLACK), new Position(File.a, Rank.ONE));
        List<Space> spaces = List.of(space3);

        SpacesService spacesService = new SpacesService(testSpaceDao);

        // when
        spacesService.saveChessBoard(spaces);

        // then
        assertThat(testSpaceDao.spaces.get(0)).isEqualTo(space3);
    }

    @Test
    @DisplayName("진행된 게임이 없을 경우 체스 보드를 저장할 수 있다.")
    void save_chess_board_when_not_exist_game() {
        // given
        TestSpacesDaoImpl testSpaceDao = new TestSpacesDaoImpl();
        testSpaceDao.spaces = new ArrayList<>(Arrays.asList());

        Space space = new Space(new Pawn(Color.BLACK), new Position(File.a, Rank.ONE));
        List<Space> updatedSpaces = List.of(space);

        SpacesService spacesService = new SpacesService(testSpaceDao);

        // when
        spacesService.saveChessBoard(updatedSpaces);

        // then
        assertThat(testSpaceDao.spaces).hasSize(1);
    }

    @Test
    @DisplayName("스페이스를 모두 삭제할 수 있다")
    void should_delete_all_space() {
        // given
        TestSpacesDaoImpl testSpaceDao = new TestSpacesDaoImpl();
        Space space = new Space(new Pawn(Color.BLACK), new Position(File.a, Rank.ONE));
        testSpaceDao.spaces = new ArrayList<>(Arrays.asList(space));

        SpacesService spacesService = new SpacesService(testSpaceDao);

        // when
        spacesService.deleteAll();

        // then
        assertThat(testSpaceDao.spaces).hasSize(0);
    }
}
