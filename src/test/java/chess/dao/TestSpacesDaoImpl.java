package chess.dao;

import chess.domain.chessBoard.Space;
import chess.domain.position.File;
import chess.domain.position.Rank;
import java.util.ArrayList;
import java.util.List;

public class TestSpacesDaoImpl implements SpacesDao {

    protected List<Space> spaces = new ArrayList<>();

    @Override
    public int countAll() {
        return spaces.size();
    }

    @Override
    public List<Space> findAll() {
        return spaces;
    }

    @Override
    public void updateBoard(List<Space> updateSpaces) {
        int nonUpdatedIndex = 0;
        for (int i = 0; i < this.spaces.size(); i++) {
            Space originSpace = spaces.get(i);
            File originFile = originSpace.getFile();
            Rank originRank = originSpace.getRank();
            for (int j = nonUpdatedIndex; j < updateSpaces.size(); j++) {
                Space updateSpace = updateSpaces.get(j);
                File updateFile = updateSpace.getFile();
                Rank updateRank = updateSpace.getRank();
                if (originFile == updateFile && originRank == updateRank) {
                    spaces.set(i, updateSpace);
                    nonUpdatedIndex++;
                }
            }
        }
    }

    @Override
    public void insertAll(List<Space> spaces) {
        this.spaces.addAll(spaces);
    }

    @Override
    public void deleteAll() {
        spaces = new ArrayList<>();
    }
}
