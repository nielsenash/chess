package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class ChessBoardLayout {

    private final ChessGame.TeamColor team;

    public ChessBoardLayout(ChessGame.TeamColor team) {
        this.team = team;
    }


    public void printBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        //print file labels above and below the board

        setBlack(out);
        drawFileLabels(out, SET_BG_COLOR_LIGHT_GREY);
        drawChessBoard(out);
        setBlack(out);
        drawFileLabels(out, SET_BG_COLOR_LIGHT_GREY);
    }

    private void drawFileLabels(PrintStream out, String color) {
        System.out.print(color);
        var labels = team == WHITE
                ? " A   B   C   D   E   F   G   H "
                : " H   G   F   E   D   C   B   A ";
        System.out.println(labels);
    }

    private void drawChessBoard(PrintStream out) {

        if (team == BLACK) {
            setGreen(out);
            drawRow1(out, new String[]{WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING,
                    WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK});
            drawRow2(out, WHITE_PAWN);
            drawMiddle(out);
            setBlue(out);
            drawRow7(out, BLACK_PAWN);
            drawRow8(out, new String[]{BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING,
                    BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK});
        } else {
            setBlue(out);
            drawRow8(out, new String[]{BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN,
                    BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK});
            drawRow7(out, BLACK_PAWN);
            drawMiddle(out);
            drawRow2(out, WHITE_PAWN);
            drawRow1(out, new String[]{WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN,
                    WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK});
        }
    }


    private void drawMiddle(PrintStream out) {
        String[] nums = team == WHITE ? new String[]{" 6 ", " 5 ", " 4 ", " 3 "} : new String[]{" 3 ", " 4 ", " 5 ", " 6 "};

        setBlack(out);
        System.out.print(nums[0]);
        drawMiddle(out, "\u001b[107m", "\u001b[100m");
        System.out.print(nums[0]);
        out.println();
        System.out.print(nums[1]);
        drawMiddle(out, "\u001b[100m", "\u001b[107m");
        System.out.print(nums[1]);
        out.println();
        System.out.print(nums[2]);
        drawMiddle(out, "\u001b[107m", "\u001b[100m");
        System.out.print(nums[2]);
        out.println();
        System.out.print(nums[3]);
        drawMiddle(out, "\u001b[100m", "\u001b[107m");
        System.out.print(nums[3]);
        out.println();

    }


    private void drawMiddle(PrintStream out, String startColor, String endColor) {
        for (int j = 0; j < 4; j++) {
            System.out.print(startColor);
            System.out.print(EMPTY);
            System.out.print(endColor);
            System.out.print(EMPTY);
        }
    }

    private void drawRow2(PrintStream out, String piece) {
        System.out.print(" 2 ");
        setGreen(out);
        for (int i = 0; i < 4; i++) {
            System.out.print("\u001b[107m" + piece);
            System.out.print("\u001b[100m" + piece);
        }
        setBlack(out);
        System.out.print(" 2 ");
        out.println();
    }

    private void drawRow7(PrintStream out, String piece) {
        System.out.print(" 7 ");
        setBlue(out);
        for (int i = 0; i < 4; i++) {
            System.out.print("\u001b[100m" + piece);
            System.out.print("\u001b[107m" + piece);
        }
        setBlack(out);
        System.out.print(" 7 ");
        out.println();
    }

    private void drawRow1(PrintStream out, String[] pieces) {
        setBlack(out);
        System.out.print(" 1 ");
        setGreen(out);
        System.out.print("\u001b[100m" + pieces[0]);
        System.out.print("\u001b[107m" + pieces[1]);
        System.out.print("\u001b[100m" + pieces[2]);
        System.out.print("\u001b[107m" + pieces[3]);
        System.out.print("\u001b[100m" + pieces[4]);
        System.out.print("\u001b[107m" + pieces[5]);
        System.out.print("\u001b[100m" + pieces[6]);
        System.out.print("\u001b[107m" + pieces[7]);
        setBlack(out);
        System.out.print(" 1 ");
        out.println();
    }


    private void drawRow8(PrintStream out, String[] pieces) {
        setBlack(out);
        System.out.print(" 8 ");
        setBlue(out);
        System.out.print("\u001b[107m" + pieces[0]);
        System.out.print("\u001b[100m" + pieces[1]);
        System.out.print("\u001b[107m" + pieces[2]);
        System.out.print("\u001b[100m" + pieces[3]);
        System.out.print("\u001b[107m" + pieces[4]);
        System.out.print("\u001b[100m" + pieces[5]);
        System.out.print("\u001b[107m" + pieces[6]);
        System.out.print("\u001b[100m" + pieces[7]);
        setBlack(out);
        System.out.print(" 8 ");
        out.println();
    }

    private void setBlack(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }

    private void setBlue(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private void setGreen(PrintStream out) {
        out.print(SET_TEXT_COLOR_GREEN);
    }


}
