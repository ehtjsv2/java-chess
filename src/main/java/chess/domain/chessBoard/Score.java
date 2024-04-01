package chess.domain.chessBoard;

public class Score {

    private final double score;

    public Score(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public boolean isHigherThan(Score score) {
        return this.score > score.score;
    }
}
