package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpaceDaoTest {

    private final SpaceDao spaceDao = new SpaceDao();
    private Connection connection;

    @BeforeEach
    void setUp() {
        try {
            connection = spaceDao.getConnection();
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("진행중인 게임이 있는 지 확인가능하다.")
    void should_know_game_exist() throws SQLException {
        // given
        Statement statement = connection.createStatement();
        statement.execute("INSERT into space (space_id, piece_type, color, rank_number, file_number) values"
                + "(\"testId100\",\"queen\",\"white\",\"1\",\"1\")");

        // when
        boolean isExist = spaceDao.isExistGame();

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("진행중인 게임이 없는 지 확인가능하다.")
    void should_know_game_not_exist() throws SQLException {
        // given
        Statement statement = connection.createStatement();

        // when
        boolean isExist = spaceDao.isExistGame();

        // then
        assertThat(isExist).isFalse();
    }

    @AfterEach
    void setDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

}
