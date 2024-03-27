package chess.domain.piece;

import chess.domain.position.Position;

public abstract class Piece {

    private final Color color;
    private final PieceType pieceType;

    public Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public abstract boolean canMove(Position from, Position to);

    public abstract boolean canCatch(Position from, Position to);

    public boolean isSameColor(Piece piece) {
        return color == piece.color;
    }

    public boolean isSameColor(Color otherColor) {
        return color == otherColor;
    }

    public boolean isOppositeColor(Piece piece) {
        if (color == Color.WHITE) {
            return piece.color == Color.BLACK;
        }
        return piece.color == Color.WHITE;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public double getScore() {
        if (pieceType == PieceType.QUEEN) {
            return 9;
        }
        if (pieceType == PieceType.ROOK) {
            return 5;
        }
        if (pieceType == PieceType.BISHOP) {
            return 3;
        }
        if (pieceType == PieceType.KNIGHT) {
            return 2.5;
        }
        if (pieceType == PieceType.PAWN) {
            return 1;
        }
        return 0;
    }
}
