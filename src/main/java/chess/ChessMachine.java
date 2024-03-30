package chess;

import chess.dao.ChessBoardService;
import chess.domain.chessBoard.ChessBoard;
import chess.domain.chessBoard.InitialPieceGenerator;
import chess.domain.chessBoard.OriginalChessSpaceGenerator;
import chess.domain.piece.Color;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessMachine {

    private final OutputView outputView;
    private final InputView inputView;
    private final ChessBoardService chessBoardService;

    public ChessMachine(OutputView outputView, InputView inputView, ChessBoardService chessBoardService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.chessBoardService = chessBoardService;
    }

    public void run() {
        outputView.printStartGameMessage();
        outputView.printCommandGuideMessage();

        validateFirstCommand(inputView.getCommand());

        ChessBoard chessBoard = loadChessBoard();
        outputView.printChessBoard(chessBoard.getSpaces());

        if (chessBoard.isAllKingAlive()) {
            playChess(chessBoard);
        }

        outputView.printGameEndMessage();
        validateCommandIsStatus(inputView.getCommand());
        printGameResult(chessBoard);
        chessBoardService.deleteAll();
    }

    private ChessBoard loadChessBoard() {
        if (chessBoardService.isExistGame()) {
            return chessBoardService.loadChessBoard();
        }
        return new ChessBoard(new OriginalChessSpaceGenerator(new InitialPieceGenerator()));
    }

    private void printGameResult(ChessBoard chessBoard) {
        double whiteScore = chessBoard.calculateScore(Color.WHITE);
        double blackScore = chessBoard.calculateScore(Color.BLACK);
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
            validateCommandIsMove(command);
            tryMove(chessBoard);
            outputView.printChessBoard(chessBoard.getSpaces());
        }
    }

    private void tryMove(ChessBoard chessBoard) {
        Position from = inputView.getMovePosition();
        Position to = inputView.getMovePosition();
        chessBoard.move(from, to);
        chessBoardService.saveChessBoard(chessBoard);
    }

    private void validateCommandIsMove(Command command) {
        if (command != Command.MOVE) {
            throw new IllegalArgumentException("잘못된 명령어 입니다.");
        }
    }
}

