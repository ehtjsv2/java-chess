package chess.dao;

import chess.domain.piece.PieceType;

public enum PieceTypeConvertor {

    PAWN("pawn", PieceType.PAWN);

    private final String dbPieceType;
    private final PieceType domainPieceType;

    PieceTypeConvertor(String dbPieceType, PieceType domainPieceType) {
        this.dbPieceType = dbPieceType;
        this.domainPieceType = domainPieceType;
    }

    public static PieceType toPieceType(String pieceType) {
        for (PieceTypeConvertor value : values()) {
            if (value.dbPieceType.equals(pieceType)) {
                return value.domainPieceType;
            }
        }
        throw new IllegalArgumentException("변환 할 수 없는 PieceType입니다.");
    }
}
