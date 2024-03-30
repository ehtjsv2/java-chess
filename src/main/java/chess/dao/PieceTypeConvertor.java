package chess.dao;

import chess.domain.piece.PieceType;
import java.util.Arrays;

public enum PieceTypeConvertor {

    PAWN("pawn", PieceType.PAWN),
    ROOK("rook", PieceType.ROOK),
    BISHOP("bishop", PieceType.BISHOP),
    KNIGHT("knight", PieceType.KNIGHT),
    QUEEN("queen", PieceType.QUEEN),
    KING("king", PieceType.KING),
    EMPTY("empty", PieceType.EMPTY);


    private final String dbPieceType;
    private final PieceType domainPieceType;

    PieceTypeConvertor(String dbPieceType, PieceType domainPieceType) {
        this.dbPieceType = dbPieceType;
        this.domainPieceType = domainPieceType;
    }

    public static PieceType toPieceType(String pieceType) {
        return Arrays.stream(values())
                .filter(value -> value.dbPieceType.equals(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("변환 할 수 없는 PieceType입니다."))
                .domainPieceType;
    }

    public static String convertToString(PieceType pieceType) {
        return Arrays.stream(values())
                .filter(value -> value.domainPieceType == pieceType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("변환 할 수 없는 PieceType입니다."))
                .dbPieceType;
    }
}
