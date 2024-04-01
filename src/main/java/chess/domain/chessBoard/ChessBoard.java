package chess.domain.chessBoard;

import chess.domain.piece.Color;
import chess.domain.position.Position;
import java.util.List;

public class ChessBoard {

    private static final int PLAYER_COUNT = 2;

    private final List<Space> spaces;
    private Color turnColor;

    public ChessBoard(SpaceGenerator spaceGenerator) {
        this.spaces = spaceGenerator.generateSpaces();
        this.turnColor = Color.WHITE;
    }

    public void move(Position from, Position to) {
        Space fromSpace = findSpace(from);
        Space toSpace = findSpace(to);
        validateRightTurnMove(fromSpace);

        List<Position> routeToTarget = fromSpace.findRouteToTarget(toSpace);
        validateClearRoute(routeToTarget);

        fromSpace.movePiece(toSpace);
        changeTurn();
    }

    private void validateRightTurnMove(Space fromSpace) {
        if (!fromSpace.isSameColor(turnColor)) {
            throw new IllegalArgumentException("본인의 피스만 움직일 수 있습니다.");
        }
    }

    private void validateClearRoute(List<Position> routes) {
        for (Position route : routes) {
            validateRouteHasPiece(route);
        }
    }

    private void validateRouteHasPiece(Position route) {
        if (findSpace(route).hasPiece()) {
            throw new IllegalArgumentException("루트에 피스가 있습니다.");
        }
    }

    private Space findSpace(Position position) {
        return spaces.stream()
                .filter(space -> space.isSamePosition(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Space가 없습니다"));
    }

    private void changeTurn() {
        if (turnColor == Color.WHITE) {
            turnColor = Color.BLACK;
            return;
        }
        if (turnColor == Color.BLACK) {
            turnColor = Color.WHITE;
            return;
        }
        throw new IllegalArgumentException("흰색 또는 검정색 누구의 턴도 아닌 상태입니다.");
    }

    public Score calculateScore(Color color) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        return scoreCalculator.calculateScore(color, spaces);
    }

    public boolean isAllKingAlive() {
        long aliveKingCount = spaces.stream()
                .filter(Space::isKing)
                .count();
        return aliveKingCount == PLAYER_COUNT;
    }

    public Color getWinner() {
        Score whiteScore = calculateScore(Color.WHITE);
        Score blackScore = calculateScore(Color.BLACK);
        if (isAllKingAlive()) {
            if (whiteScore.isHigherThan(blackScore)) {
                return Color.WHITE;
            }
            if (blackScore.isHigherThan(whiteScore)) {
                return Color.BLACK;
            }
            return Color.EMPTY;
        }
        return findAliveKingColor();
    }

    private Color findAliveKingColor() {
        return spaces.stream()
                .filter(Space::isKing)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("살아남은 King이 없습니다."))
                .getColor();
    }

    public List<Space> getSpaces() {
        return spaces;
    }
}
