package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeConvertorTest {

    @Test
    @DisplayName("피스타입 스트링이 입력되면 PieceType으로 변환한다.")
    void should_convert_to_piece_type() {
        String pieceType = "pawn";

        assertThat(PieceTypeConvertor.toPieceType(pieceType)).isEqualTo(PieceType.PAWN);
    }
}
