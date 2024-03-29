package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpaceDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean isExistGame() throws SQLException {
        Connection connection = getConnection();
        String query = "select count(*) from space;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return isNextExist(resultSet);
        }
        return false;
    }

    private boolean isNextExist(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(1) > 0;
    }
}
