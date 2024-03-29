package chess.dao;

import chess.domain.piece.Color;
import chess.domain.piece.EmptyPiece;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.function.Function;

public enum PieceConvertor {

    PAWN(PieceType.PAWN, Pawn::new);

    private final PieceType pieceType;

    private final Function<Color, Pawn> pieceConstructor;

    PieceConvertor(PieceType pieceType, Function<Color, Pawn> pieceConstructor) {
        this.pieceType = pieceType;
        this.pieceConstructor = pieceConstructor;
    }

    public static Piece toPiece(PieceType pieceType, Color color) {
        if (pieceType == PieceType.EMPTY) {
            return new EmptyPiece();
        }
        for (PieceConvertor value : values()) {
            if (value.pieceType == pieceType) {
                return value.pieceConstructor.apply(color);
            }
        }
        throw new IllegalArgumentException("변환 할 수 없는 피스 타입입니다.");
    }

    public static Piece toPiece(String originPieceType, String originColor) {
        PieceType pieceType = PieceTypeConvertor.toPieceType(originPieceType);
        Color color = Color.of(originColor);
        if (pieceType == PieceType.EMPTY) {
            return new EmptyPiece();
        }
        for (PieceConvertor value : values()) {
            if (value.pieceType == pieceType) {
                return value.pieceConstructor.apply(color);
            }
        }
        throw new IllegalArgumentException("변환 할 수 없는 피스 타입입니다.");
    }
}