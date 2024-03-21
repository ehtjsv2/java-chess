package chess.piece;

import chess.Rule;
import chess.position.FileDifference;
import chess.position.Position;
import chess.position.PositionDifference;
import chess.position.RankDifference;

public class Queen extends Piece {

    private final Rule moveRule;

    public Queen(Color color) {
        super(color);
        moveRule = (fileDifference, rankDifference) ->
                ((!fileDifference.equals(new FileDifference(0)) && rankDifference.equals(new RankDifference(0))) ||
                        (fileDifference.equals(new FileDifference(0)) && !rankDifference.equals(new RankDifference(0))))
                        || fileDifference.hasSameDistance(rankDifference);
    }

    @Override
    public boolean isMovable(Position from, Position to) {
        PositionDifference positionDifference = from.calculateDifferenceTo(to);
        return positionDifference.isObeyRule(moveRule);
    }

    @Override
    public boolean isCatchable(Position from, Position to) {
        return isMovable(from, to);
    }
}
