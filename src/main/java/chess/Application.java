package chess;

import chess.dao.SpacesDaoImpl;
import chess.dao.SpacesService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {

    public static void main(String[] args) {
        ChessMachine chessMachine = new ChessMachine(new OutputView(), new InputView(),
                new SpacesService(new SpacesDaoImpl()));
        chessMachine.run();
    }
}
