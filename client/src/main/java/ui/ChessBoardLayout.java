package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


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
        setWhite(out);
    }

    private void drawFileLabels(PrintStream out, String color) {
        System.out.print(color);
        var labels = team == WHITE
                ? "    A   B  C   D  E   F   G  H     "
                : "    H   G  F   E  D   C   B  A     ";
        System.out.print(labels);
        System.out.println(RESET_BG_COLOR);
    }

    private void drawChessBoard(PrintStream out) {

        if (team == BLACK) {
            setGreen(out);
            drawTopRow(out, new String[]{WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING,
                    WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}, 1);
            drawTopPawns(out, WHITE_PAWN, 2);
            drawMiddle(out);
            drawBottomPawns(out, BLACK_PAWN, 7);
            drawBottomRow(out, new String[]{BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING,
                    BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK}, 8);
        } else {
            setBlue(out);
            drawTopRow(out, new String[]{BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN,
                    BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK}, 8);
            drawTopPawns(out, BLACK_PAWN, 7);
            drawMiddle(out);
            drawBottomPawns(out, WHITE_PAWN, 2);
            drawBottomRow(out, new String[]{WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN,
                    WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}, 1);
        }
    }


    private void drawMiddle(PrintStream out) {
        String[] nums = team == WHITE ? new String[]{" 6 ", " 5 ", " 4 ", " 3 "} : new String[]{" 3 ", " 4 ", " 5 ", " 6 "};

        setBlack(out);
        System.out.print(nums[0]);
        drawMiddle(out, "\u001b[107m", "\u001b[100m");
        setBlack(out);
        System.out.print(nums[0]);
        System.out.println(RESET_BG_COLOR);
        setBlack(out);
        System.out.print(nums[1]);
        drawMiddle(out, "\u001b[100m", "\u001b[107m");
        setBlack(out);
        System.out.print(nums[1]);
        System.out.println(RESET_BG_COLOR);
        setBlack(out);
        System.out.print(nums[2]);
        drawMiddle(out, "\u001b[107m", "\u001b[100m");
        setBlack(out);
        System.out.print(nums[2]);
        System.out.println(RESET_BG_COLOR);
        setBlack(out);
        System.out.print(nums[3]);
        drawMiddle(out, "\u001b[100m", "\u001b[107m");
        setBlack(out);
        System.out.print(nums[3]);
        System.out.println(RESET_BG_COLOR);

    }


    private void drawMiddle(PrintStream out, String startColor, String endColor) {
        for (int j = 0; j < 4; j++) {
            System.out.print(startColor);
            System.out.print(EMPTY);
            System.out.print(endColor);
            System.out.print(EMPTY);
        }
    }

    private void drawBottomPawns(PrintStream out, String piece, int row) {
        setBlack(out);
        System.out.printf(" %d ", row);
        setGreen(out);
        if (team == WHITE) {
            setBlue(out);
        }
        for (int i = 0; i < 4; i++) {
            System.out.print("\u001b[107m" + piece);
            System.out.print("\u001b[100m" + piece);
        }
        setBlack(out);
        System.out.printf(" %d ", row);
        System.out.println(RESET_BG_COLOR);
    }

    private void drawTopPawns(PrintStream out, String piece, int row) {
        setBlack(out);
        System.out.printf(" %d ", row);
        setBlue(out);
        if (team == WHITE) {
            setGreen(out);
        }
        for (int i = 0; i < 4; i++) {
            System.out.print("\u001b[100m" + piece);
            System.out.print("\u001b[107m" + piece);
        }
        setBlack(out);
        System.out.printf(" %d ", row);
        System.out.println(RESET_BG_COLOR);
    }

    private void drawBottomRow(PrintStream out, String[] pieces, int row) {
        setBlack(out);
        System.out.printf(" %d ", row);
        setGreen(out);
        if (team == WHITE) {
            setBlue(out);
        }
        System.out.print("\u001b[100m" + pieces[0]);
        System.out.print("\u001b[107m" + pieces[1]);
        System.out.print("\u001b[100m" + pieces[2]);
        System.out.print("\u001b[107m" + pieces[3]);
        System.out.print("\u001b[100m" + pieces[4]);
        System.out.print("\u001b[107m" + pieces[5]);
        System.out.print("\u001b[100m" + pieces[6]);
        System.out.print("\u001b[107m" + pieces[7]);
        setBlack(out);
        System.out.printf(" %d ", row);
        System.out.println(RESET_BG_COLOR);
    }


    private void drawTopRow(PrintStream out, String[] pieces, int row) {
        setBlack(out);
        System.out.printf(" %d ", row);
        setBlue(out);
        if (team == WHITE) {
            setGreen(out);
        }
        System.out.print("\u001b[107m" + pieces[0]);
        System.out.print("\u001b[100m" + pieces[1]);
        System.out.print("\u001b[107m" + pieces[2]);
        System.out.print("\u001b[100m" + pieces[3]);
        System.out.print("\u001b[107m" + pieces[4]);
        System.out.print("\u001b[100m" + pieces[5]);
        System.out.print("\u001b[107m" + pieces[6]);
        System.out.print("\u001b[100m" + pieces[7]);
        setBlack(out);
        System.out.printf(" %d ", row);
        System.out.println(RESET_BG_COLOR);
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

    private void setWhite(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE);
    }


}
