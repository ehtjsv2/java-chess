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

    public ChessBoard(SpaceGenerator spaceGenerator) {
        this.spaces = spaceGenerator.generateSpaces();
    }

    public void move(Position from, Position to) {
        Space fromSpace = findSpace(from);
        Space toSpace = findSpace(to);

        List<Position> routeToTarget = fromSpace.findRouteToTarget(toSpace);
        validateClearRoute(routeToTarget);

        fromSpace.movePiece(toSpace);
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

    public boolean isSameColor(Position position, Color color) {
        Space space = findSpace(position);
        return space.isSameColor(color);
    }

    public List<Space> getSpaces() {
        return spaces;
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
}
