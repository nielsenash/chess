package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class PrintChessBoard {

    // Board dimensions.
    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 2;

    private static ChessGame.TeamColor team = BLACK;


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] topHeaders = (team == WHITE)
                ? new String[]{"A", "B", "C", "D", "E", "F", "G", "H"}
                : new String[]{"H", "G", "F", "E", "D", "C", "B", "A"};
        for (int boardCol = 0; boardCol < BOARD_SIZE; ++boardCol) {
            drawHeader(out, topHeaders[boardCol]);
        }
//        String[] sideHeaders = (team == WHITE) ? new String[]{"1", "2", "3", "4", "5", "6", "7", "8"} : new String[]{"8", "7", "6", "5", "4", "3", "2", "1"};
//        for (int boardRow = 0; boardRow < BOARD_SIZE; ++boardRow) {
//            drawHeader(out, topHeaders[boardRow]);
//        }


        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE / 2;
        int suffixLength = SQUARE_SIZE - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {
        drawRow1(out);
        drawRow2(out);
        drawMiddle(out, "\u001b[107m", "\u001b[35;100m");
        drawMiddle(out, "\u001b[35;100m", "\u001b[107m");
        drawMiddle(out, "\u001b[107m", "\u001b[35;100m");
        drawMiddle(out, "\u001b[35;100m", "\u001b[107m");

        drawRow7(out);
        drawRow8(out);
    }

    private static void drawMiddle(PrintStream out, String startColor, String endColor) {
        for (int j = 0; j < 4; j++) {
            System.out.print(startColor);
            System.out.print(EMPTY);
            System.out.print(endColor);
            System.out.print(EMPTY);
        }
        out.println();

    }

    private static void drawRow2(PrintStream out) {
        for (int i = 0; i < 4; i++) {
            System.out.print("\u001b[35;100m");
            System.out.print(BLACK_PAWN);
            System.out.print("\u001b[107m");
            System.out.print(BLACK_PAWN);
        }
        out.println();
    }

    private static void drawRow7(PrintStream out) {
        for (int i = 0; i < 4; i++) {
            System.out.print("\u001b[107m");
            System.out.print(WHITE_PAWN);
            System.out.print("\u001b[35;100m");
            System.out.print(WHITE_PAWN);
        }
        out.println();
    }

    private static void drawRow1(PrintStream out) {
        System.out.print("\u001b[107m");
        System.out.print(BLACK_ROOK);
        System.out.print("\u001b[35;100m");
        System.out.print(BLACK_KNIGHT);
        System.out.print("\u001b[107m");
        System.out.print(BLACK_BISHOP);
        System.out.print("\u001b[35;100m");
        System.out.print(BLACK_QUEEN);
        System.out.print("\u001b[107m");
        System.out.print(BLACK_KING);
        System.out.print("\u001b[35;100m");
        System.out.print(BLACK_BISHOP);
        System.out.print("\u001b[107m");
        System.out.print(BLACK_KNIGHT);
        System.out.print("\u001b[35;100m");
        System.out.print(BLACK_ROOK);

        out.println();
    }

    private static void drawRow8(PrintStream out) {
        System.out.print("\u001b[35;100m");
        System.out.print(WHITE_ROOK);
        System.out.print("\u001b[107m");
        System.out.print(WHITE_KNIGHT);
        System.out.print("\u001b[35;100m");
        System.out.print(WHITE_BISHOP);
        System.out.print("\u001b[107m");
        System.out.print(WHITE_QUEEN);
        System.out.print("\u001b[35;100m");
        System.out.print(WHITE_KING);
        System.out.print("\u001b[107m");
        System.out.print(WHITE_BISHOP);
        System.out.print("\u001b[35;100m");
        System.out.print(WHITE_KNIGHT);
        System.out.print("\u001b[107m");
        System.out.print(WHITE_ROOK);
        out.println();
    }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }
}
