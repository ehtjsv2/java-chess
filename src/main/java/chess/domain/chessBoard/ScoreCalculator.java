package chess.domain.chessBoard;

import chess.domain.piece.Color;
import chess.domain.piece.PieceScore;
import chess.domain.position.File;
import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator {

    private static final int DOWNGRADE_PAWN_COUNT_THRESHOLD_PER_FILE = 2;

    public Score calculateScore(Color color, List<Space> spaces) {
        double score = calculateTotalScore(color, spaces) - getTotalDownGradedPawnScore(color, spaces);
        return new Score(score);

    }

    private double calculateTotalScore(Color color, List<Space> spaces) {
        return spaces.stream()
                .filter(space -> space.isSameColor(color))
                .mapToDouble(space -> space.findPieceScore().getScore())
                .sum();
    }

    private double getTotalDownGradedPawnScore(Color color, List<Space> spaces) {
        return findDowngradePawnCount(color, spaces) * PieceScore.getDowngradePawnScore();
    }

    private int findDowngradePawnCount(Color color, List<Space> spaces) {
        int totalDwongradePawnCount = 0;
        for (File file : File.values()) {
            List<Space> fileSpaces = getSameFileSpaces(file, spaces);
            totalDwongradePawnCount += getDowngradePawnCountFromFile(fileSpaces, color);
        }
        return totalDwongradePawnCount;
    }

    private List<Space> getSameFileSpaces(File file, List<Space> spaces) {
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
