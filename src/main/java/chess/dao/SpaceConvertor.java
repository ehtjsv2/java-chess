package chess.dao;

import chess.domain.chessBoard.Space;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;

public class SpaceConvertor {

    public static Space toSpace(String pieceType, String color, int file, int rank) {
        Piece piece = PieceConvertor.toPiece(pieceType, color);
        Position position = new Position(File.of(file), Rank.of(rank));
        return new Space(piece, position);
    }
}
