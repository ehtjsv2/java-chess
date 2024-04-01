package chess.dao;

import static chess.dao.DbConnector.getConnection;

import chess.domain.chessBoard.Space;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpacesDaoImpl implements SpacesDao {


    @Override
    public int countAll() {
        Connection connection = getConnection();
        String query = "select count(*) from spaces;";
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
        String query = "select * from spaces;";
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
            String query = "update spaces set piece_type = ?, color = ? where file_number = ? and rank_number = ?";
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
            String query = "insert into spaces values(?, ?, ?, ?) ";
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
    public void deleteAll() {
        try {
            Connection connection = getConnection();
            String query = "delete from spaces";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
