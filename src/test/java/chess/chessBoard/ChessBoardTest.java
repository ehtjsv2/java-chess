package chess.chessBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.InitialPieceGenerator;
import chess.domain.chessBoard.OriginalChessSpaceGenerator;
import chess.domain.chessBoard.Space;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @Test
    @DisplayName("이동경로에 피스가 있으면 움직일 수 없다")
    void should_not_move_when_route_has_piece() {
        Position from = new Position(File.a, Rank.ONE);
        Position to = new Position(File.a, Rank.THREE);
        ChessBoard chessBoard = new ChessBoard(new OriginalChessSpaceGenerator(new InitialPieceGenerator()));

        assertThatThrownBy(() -> chessBoard.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("루트에 피스가 있습니다.");
    }

    //초기 상태에 대하여 폰 8(1*8) 특수기물 30(9+5*2+3*2+2.5*2) 총 38
    @Test
    @DisplayName("체스판에 남아있는 기물에 따라 원하는 진영의 점수를 계산한다.")
    void should_calculate_score() {
        ChessBoard chessBoard = new ChessBoard(new OriginalChessSpaceGenerator(new InitialPieceGenerator()));

        assertThat(chessBoard.calculateScore(Color.BLACK).getScore()).isEqualTo(38);
    }

    //한줄에 Pawn 두개만 두고 1점이 나오는 지 테스트
    @Test
    @DisplayName("같은 File에 같은 색의 폰이 있다면 폰은 0.5점으로 계산된다")
    void should_downGrade_pawn_score_when_file_has_same_color_pawn() {
        // given
        Piece pawn1 = new Pawn(Color.WHITE);
        Position position1 = new Position(File.a, Rank.ONE);

        Piece pawn2 = new Pawn(Color.WHITE);
        Position position2 = new Position(File.a, Rank.TWO);

        List<Space> spaces = List.of(new Space(pawn1, position1), new Space(pawn2, position2));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.calculateScore(Color.WHITE).getScore()).isEqualTo(1);
    }

    @Test
    @DisplayName("왕이 모두 살아있는 지 확인할 수 있다.")
    void should_true_when_all_king_alive() {
        // given
        Piece king1 = new King(Color.WHITE);
        Position position1 = new Position(File.a, Rank.ONE);

        Piece king2 = new King(Color.WHITE);
        Position position2 = new Position(File.a, Rank.TWO);

        List<Space> spaces = List.of(new Space(king1, position1), new Space(king2, position2));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.isAllKingAlive()).isTrue();
    }

    @Test
    @DisplayName("왕이 하나 죽었는 지 확인할 수 있다.")
    void should_false_when_one_king_alive() {
        // given
        Piece king = new King(Color.WHITE);
        Position position1 = new Position(File.a, Rank.ONE);

        List<Space> spaces = List.of(new Space(king, position1));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.isAllKingAlive()).isFalse();
    }

    @Test
    @DisplayName("어떤 진영이 이겼는 지 알 수 있다(흰색 승)")
    void should_white_win_when_white_more_score() {
        // given
        Piece pawn = new Pawn(Color.WHITE);
        Piece king1 = new King(Color.WHITE);
        Piece king2 = new King(Color.BLACK);
        Position position1 = new Position(File.a, Rank.ONE);
        Position position2 = new Position(File.b, Rank.ONE);
        Position position3 = new Position(File.c, Rank.ONE);

        List<Space> spaces = List.of(new Space(pawn, position1), new Space(king1, position2),
                new Space(king2, position3));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.getWinner()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("어떤 진영이 이겼는 지 알 수 있다(검정 승)")
    void should_black_win_when_black_more_score() {
        // given
        Piece pawn = new Pawn(Color.BLACK);
        Piece king1 = new King(Color.WHITE);
        Piece king2 = new King(Color.BLACK);
        Position position1 = new Position(File.a, Rank.ONE);
        Position position2 = new Position(File.b, Rank.ONE);
        Position position3 = new Position(File.c, Rank.ONE);

        List<Space> spaces = List.of(new Space(pawn, position1), new Space(king1, position2),
                new Space(king2, position3));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.getWinner()).isEqualTo(Color.BLACK);
    }

    @Test
    @DisplayName("흰색 왕이 죽으면, 검정 왕이 이긴다")
    void should_black_win_when_white_king_die() {
        // given
        Piece king = new King(Color.BLACK);
        Position position1 = new Position(File.a, Rank.ONE);

        List<Space> spaces = List.of(new Space(king, position1));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.getWinner()).isEqualTo(Color.BLACK);
    }

    @Test
    @DisplayName("검정 왕이 죽으면, 흰색 왕이 이긴다")
    void should_white_win_when_white_black_die() {
        // given
        Piece king = new King(Color.WHITE);
        Position position1 = new Position(File.a, Rank.ONE);

        List<Space> spaces = List.of(new Space(king, position1));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.getWinner()).isEqualTo(Color.WHITE);
    }

    @Test
    @DisplayName("점수가 같으면, 무승부이다.")
    void should_draw_when_same_score() {
        // given
        Piece pawn1 = new Pawn(Color.WHITE);
        Position position1 = new Position(File.a, Rank.ONE);
        Piece pawn2 = new Pawn(Color.BLACK);
        Position position2 = new Position(File.b, Rank.ONE);
        Piece king1 = new King(Color.WHITE);
        Piece king2 = new King(Color.BLACK);
        Position position3 = new Position(File.c, Rank.ONE);
        Position position4 = new Position(File.d, Rank.ONE);

        List<Space> spaces = List.of(new Space(pawn1, position1), new Space(pawn2, position2)
                , new Space(king1, position3), new Space(king2, position4));
        ChessBoard chessBoard = new ChessBoard(new TestCustomChessSpaceGenerator(spaces));

        // when

        // then
        assertThat(chessBoard.getWinner()).isEqualTo(Color.EMPTY);
    }
}
