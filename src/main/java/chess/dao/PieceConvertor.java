package chess.dao;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.EmptyPiece;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceConvertor {

    PAWN(PieceType.PAWN, Pawn::new),
    ROOK(PieceType.ROOK, Rook::new),
    BISHOP(PieceType.BISHOP, Bishop::new),
    KNIGHT(PieceType.KNIGHT, Knight::new),
    QUEEN(PieceType.QUEEN, Queen::new),
    KING(PieceType.KING, King::new),
    ;


    private final PieceType pieceType;

    private final Function<Color, Piece> pieceConstructor;

    PieceConvertor(PieceType pieceType, Function<Color, Piece> pieceConstructor) {
        this.pieceType = pieceType;
        this.pieceConstructor = pieceConstructor;
    }

    public static Piece toPiece(String originPieceType, String originColor) {
        PieceType pieceType = PieceTypeConvertor.toPieceType(originPieceType);
        Color color = Color.of(originColor);
        if (pieceType == PieceType.EMPTY) {
            return new EmptyPiece();
        }
        Function<Color, Piece> pieceConstructor = Arrays.stream(values())
                .filter(value -> value.pieceType == pieceType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("변환 할 수 없는 피스 타입입니다."))
                .pieceConstructor;
        return pieceConstructor.apply(color);
    }
}
