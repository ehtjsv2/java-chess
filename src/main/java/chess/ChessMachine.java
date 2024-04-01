package chess;

import chess.dao.SpacesService;
import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.InitialPieceGenerator;
import chess.domain.chessBoard.OriginalChessSpaceGenerator;
import chess.domain.chessBoard.Score;
import chess.domain.chessBoard.fixedChessBoardReader;
import chess.domain.piece.Color;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessMachine {

    private final OutputView outputView;
    private final InputView inputView;
    private final SpacesService spacesService;

    public ChessMachine(OutputView outputView, InputView inputView, SpacesService spacesService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.spacesService = spacesService;
    }

    public void run() {
        outputView.printStartGameMessage();
        outputView.printCommandGuideMessage();

        validateFirstCommand(inputView.getCommand());

        ChessBoard chessBoard = createChessBoard();
        outputView.printChessBoard(chessBoard.getSpaces());

        playChess(chessBoard);

        outputView.printGameEndMessage();
        validateCommandIsStatus(inputView.getCommand());
        printGameResult(chessBoard);
    }

    private ChessBoard createChessBoard() {
        if (spacesService.isExistGame()) {
            return new ChessBoard(new fixedChessBoardReader(spacesService.loadSpaces()));
        }
        return new ChessBoard(new OriginalChessSpaceGenerator(new InitialPieceGenerator()));
    }

    private void printGameResult(ChessBoard chessBoard) {
        Score whiteScore = chessBoard.calculateScore(Color.WHITE);
        Score blackScore = chessBoard.calculateScore(Color.BLACK);
        outputView.printGameResultScore(whiteScore, blackScore);
        outputView.printWinner(chessBoard.getWinner());
    }

    private void validateFirstCommand(Command command) {
        if (command != Command.START) {
            throw new IllegalArgumentException("게임을 먼저 시작해야합니다.");
        }
    }

    private void validateCommandIsStatus(Command command) {
        if (command != Command.STATUS) {
            throw new IllegalArgumentException("올바르지 않은 명령어입니다.");
        }
    }

    private void playChess(ChessBoard chessBoard) {
        Command command;
        while (chessBoard.isAllKingAlive() && (command = inputView.getCommand()) != Command.END) {
            validateCommandIsMoveOrStatus(command);
            proceedCommand(command, chessBoard);
        }
        spacesService.deleteAll();
    }

    private void validateCommandIsMoveOrStatus(Command command) {
        if (command != Command.MOVE && command != Command.STATUS) {
            throw new IllegalArgumentException("게임 진행중일 때, 사용가능한 명령어가 아닙니다.");
        }
    }

    private void proceedCommand(Command command, ChessBoard chessBoard) {
        if (command == Command.MOVE) {
            tryMove(chessBoard);
            outputView.printChessBoard(chessBoard.getSpaces());
        }
        if (command == Command.STATUS) {
            printGameResult(chessBoard);
        }
    }

    private void tryMove(ChessBoard chessBoard) {
        Position from = inputView.getMovePosition();
        Position to = inputView.getMovePosition();
        chessBoard.move(from, to);
        spacesService.saveChessBoard(chessBoard.getSpaces());
    }
}

