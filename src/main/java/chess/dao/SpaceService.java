package chess.dao;

public class SpaceService {

    private final SpaceDao spaceDao;

    SpaceService(SpaceDao spaceDao) {
        this.spaceDao = spaceDao;
    }

    public boolean isExistGame() {
        return spaceDao.countAll() > 0;
    }
}
