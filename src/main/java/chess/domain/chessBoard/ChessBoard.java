package chess.domain.chessBoard;

import chess.domain.piece.Color;
import chess.domain.piece.PieceScore;
import chess.domain.position.File;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    private static final int DOWNGRADE_PAWN_COUNT_THRESHOLD_PER_FILE = 2;

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
        }
        if (turnColor == Color.BLACK) {
            turnColor = Color.WHITE;
        }
        throw new IllegalArgumentException("흰색 또는 검정색 누구의 턴도 아닌 상태입니다.");
    }

    public double calculateScore(Color color) {
        return calculateTotalScore(color) - getTotalDownGradedPawnScore(color);
    }

    private double calculateTotalScore(Color color) {
        return spaces.stream()
                .filter(space -> space.isSameColor(color))
                .mapToDouble(space -> space.findPieceScore().getScore())
                .sum();
    }

    private double getTotalDownGradedPawnScore(Color color) {
        return findDowngradePawnCount(color) * PieceScore.getDowngradePawnScore();
    }

    private int findDowngradePawnCount(Color color) {
        int totalDwongradePawnCount = 0;
        for (File file : File.values()) {
            List<Space> fileSpaces = getSameFileSpaces(file);
            totalDwongradePawnCount += getDowngradePawnCountFromFile(fileSpaces, color);
        }
        return totalDwongradePawnCount;
    }

    private List<Space> getSameFileSpaces(File file) {
        List<Space> fileSpaces = new ArrayList<>();
        spaces.stream()
                .filter(space -> space.isSameFilePosition(file))
                .forEach(fileSpaces::add);
        return fileSpaces;
    }

    private int getDowngradePawnCountFromFile(List<Space> fileSpaces, Color color) {
        int oneFilePawnCount = (int) fileSpaces.stream()
                .filter(space -> space.isSameColor(color))
                .filter(Space::hasPawn)
                .count();
        if (oneFilePawnCount < DOWNGRADE_PAWN_COUNT_THRESHOLD_PER_FILE) {
            oneFilePawnCount = 0;
        }
        return oneFilePawnCount;
    }

    public boolean isAllKingAlive() {
        long aliveKingCount = spaces.stream()
                .filter(Space::isKing)
                .count();
        return aliveKingCount == 2;
    }

    public Color getWinner() {
        double whiteScore = calculateScore(Color.WHITE);
        double blackScore = calculateScore(Color.BLACK);
        if (whiteScore > blackScore) {
            return Color.WHITE;
        }
        if (whiteScore < blackScore) {
            return Color.BLACK;
        }
        return Color.EMPTY;
    }

    public boolean isSameColor(Position position, Color color) {
        Space space = findSpace(position);
        return space.isSameColor(color);
    }

    public List<Space> getSpaces() {
        return spaces;
    }
}
