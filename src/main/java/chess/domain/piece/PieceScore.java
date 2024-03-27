package chess.domain.piece;

import java.util.Arrays;

public enum PieceScore {
    PAWN(PieceType.PAWN, 1),
    ROOK(PieceType.ROOK, 5),
    BISHOP(PieceType.BISHOP, 3),
    KNIGHT(PieceType.KNIGHT, 2.5),
    QUEEN(PieceType.QUEEN, 9),
    KING(PieceType.KING, 0),
    EMPTY(PieceType.EMPTY, 0),
    ;

    private static final double DOWNGRADE_PAWN_SCORE = 0.5;

    private final PieceType pieceType;
    private final double score;

    PieceScore(PieceType pieceType, double score) {
        this.pieceType = pieceType;
        this.score = score;
    }

    public static PieceScore of(PieceType pieceType) {
        return Arrays.stream(values())
                .filter(pieceScore -> pieceScore.pieceType == pieceType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피스 종류입니다."));
    }

    public static double getDowngradePawnScore() {
        return DOWNGRADE_PAWN_SCORE;
    }

    public double getScore() {
        return score;
    }
}
