package chess.domain.piece;

import java.util.List;

public enum Color {
    BLACK("black"), WHITE("white"), EMPTY("empty");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public static Color of(String wantedColor) {
        List<Color> colors = List.of(values());
        return colors.stream()
                .filter((colorEntity) -> colorEntity.color.equals(wantedColor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 컬러를 조회했습니다."));
    }

    public String getValue() {
        return color;
    }
}
