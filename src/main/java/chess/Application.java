package chess;

import chess.dao.ChessBoardDaoImpl;
import chess.dao.ChessBoardService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {

    public static void main(String[] args) {
        ChessMachine chessMachine = new ChessMachine(new OutputView(), new InputView(),
                new ChessBoardService(new ChessBoardDaoImpl()));
        chessMachine.run();
    }
}
