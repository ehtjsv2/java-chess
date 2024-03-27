package chess.chessBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.OriginalChessSpaceGenerator;
import chess.domain.chessBoard.PieceGenerator;
import chess.domain.piece.Color;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @Test
    @DisplayName("이동경로에 피스가 있으면 움직일 수 없다")
    void should_not_move_when_route_has_piece() {
        Position from = new Position(File.a, Rank.ONE);
        Position to = new Position(File.a, Rank.THREE);
        ChessBoard chessBoard = new ChessBoard(new OriginalChessSpaceGenerator(new PieceGenerator()));

        assertThatThrownBy(() -> chessBoard.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("루트에 피스가 있습니다.");
    }

    //초기 상태에 대하여 폰 8(1*8) 특수기물 30(9+5*2+3*2+2.5*2) 총 38
    @Test
    @DisplayName("체스판에 남아있는 기물에 따라 원하는 진영의 점수를 계산한다.")
    void should_calculate_score() {
        ChessBoard chessBoard = new ChessBoard(new OriginalChessSpaceGenerator(new PieceGenerator()));

        assertThat(chessBoard.calculateScore(Color.BLACK)).isEqualTo(38);
    }
}
