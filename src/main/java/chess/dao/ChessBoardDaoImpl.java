package chess.dao;

import chess.domain.chessBoard.Space;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardDaoImpl implements ChessBoardDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디\
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

    @Override
    public int countAll() {
        Connection connection = getConnection();
        String query = "select count(*) from ChessBoard;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Space> findAll() {
        Connection connection = getConnection();
        String query = "select * from ChessBoard;";
        List<Space> spaces = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String dbPieceType = resultSet.getString("piece_type");
                String dbColor = resultSet.getString("color");
                int fileNumber = resultSet.getInt("file_number");
                int rankNumber = resultSet.getInt("rank_number");

                Space space = SpaceConvertor.toSpace(dbPieceType, dbColor, fileNumber, rankNumber);

                spaces.add(space);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return spaces;
    }

    @Override
    public void updateBoard(List<Space> spaces) {
        try {
            Connection connection = getConnection();
            String query = "update ChessBoard set piece_type = ?, color = ? where file_number = ? and rank_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Space space : spaces) {
                preparedStatement.setString(1, PieceTypeConvertor.convertToString(space.getPieceType()));
                preparedStatement.setString(2, space.getColor().getValue());
                preparedStatement.setInt(3, space.getFile().getValue());
                preparedStatement.setInt(4, space.getRank().getValue());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertAll(List<Space> spaces) {
        try {
            Connection connection = getConnection();
            //final var query = "INSERT INTO user VALUES(?, ?)";
            String query = "insert into ChessBoard values(?, ?, ?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Space space : spaces) {
                preparedStatement.setString(1, PieceTypeConvertor.convertToString(space.getPieceType()));
                preparedStatement.setString(2, space.getColor().getValue());
                preparedStatement.setInt(3, space.getFile().getValue());
                preparedStatement.setInt(4, space.getRank().getValue());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
