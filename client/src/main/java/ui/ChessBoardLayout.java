package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;


import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.*;
import static ui.EscapeSequences.*;

public class ChessBoardLayout {

    private final ChessGame.TeamColor team;
    private final ChessPiece[][] board;

    private final Map<ChessPiece, String> mappings = Map.ofEntries(
            Map.entry(new ChessPiece(WHITE, PAWN), WHITE_PAWN),
            Map.entry(new ChessPiece(WHITE, ROOK), WHITE_ROOK),
            Map.entry(new ChessPiece(WHITE, KNIGHT), WHITE_KNIGHT),
            Map.entry(new ChessPiece(WHITE, BISHOP), WHITE_BISHOP),
            Map.entry(new ChessPiece(WHITE, QUEEN), WHITE_QUEEN),
            Map.entry(new ChessPiece(WHITE, KING), WHITE_KING),

            Map.entry(new ChessPiece(BLACK, PAWN), BLACK_PAWN),
            Map.entry(new ChessPiece(BLACK, ROOK), BLACK_ROOK),
            Map.entry(new ChessPiece(BLACK, KNIGHT), BLACK_KNIGHT),
            Map.entry(new ChessPiece(BLACK, BISHOP), BLACK_BISHOP),
            Map.entry(new ChessPiece(BLACK, QUEEN), BLACK_QUEEN),
            Map.entry(new ChessPiece(BLACK, KING), BLACK_KING)
    );

    public ChessBoardLayout(ChessPiece[][] board, ChessGame.TeamColor team) {
        this.board = board;
        this.team = team;
    }

    public ArrayList<String> getStringList(ChessPiece[] chessPieceList) {
        var list = new ArrayList<String>();
        for (ChessPiece p : chessPieceList) {
            list.add(p == null ? EMPTY : mappings.get(p));
        }
        return list;
    }

    public ArrayList<ArrayList<String>> getStringArray(ChessPiece[][] chessPieceArray) {
        var array = new ArrayList<ArrayList<String>>();
        for (ChessPiece[] p : chessPieceArray) {
            array.add(getStringList(p));
        }
        return array;
    }

    public void printHighlightedBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        var printableBoard = getStringArray(board);

        //print file labels above and below the board

        setGrey(out);
        drawFileLabels(out);
        drawChessBoard(out, printableBoard);
        drawFileLabels(out);
        setWhite(out);
    }

    public void printBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        var printableBoard = getStringArray(board);

        //print file labels above and below the board

        setGrey(out);
        drawFileLabels(out);
        drawChessBoard(out, printableBoard);
        drawFileLabels(out);
        setWhite(out);
    }

    private void drawFileLabels(PrintStream out) {
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        var labels = team == WHITE
                ? "     A     B     C    D     E     F     G    H     "
                : "     H     G     F    E     D     C     B    A     ";
        System.out.print(labels);
        System.out.println(RESET_BG_COLOR);
    }

    private void drawChessBoard(PrintStream out, ArrayList<ArrayList<String>> printableBoard) {

        if (team == WHITE) {
            for (int i = printableBoard.size() - 1; i >= 0; i--) {
                var backgroundStartColor = (i % 2 == 0) ? SET_BG_COLOR_MAGENTA : SET_BG_COLOR_WHITE;
                drawRow(out, i + 1, printableBoard.get(i), backgroundStartColor);
            }
        } else {
            for (int i = 0; i < printableBoard.size(); i++) {
                var backgroundStartColor = (i % 2 == 0) ? SET_BG_COLOR_MAGENTA : SET_BG_COLOR_WHITE;
                drawRow(out, i + 1, printableBoard.get(i), backgroundStartColor);
            }
        }

    }

    private void drawRow(PrintStream out, int rowNumber, ArrayList<String> row, String backgroundStartColor) {
        setGrey(out);
        System.out.printf(" %d ", rowNumber);

        String bg = backgroundStartColor;

        if (team == WHITE) {
            for (String square : row) {
                out.print(bg);
                out.print(" " + square + " ");
                bg = (bg.equals(SET_BG_COLOR_MAGENTA)) ? SET_BG_COLOR_WHITE : SET_BG_COLOR_MAGENTA;
            }
        } else {
            for (int i = row.size() - 1; i >= 0; i--) {
                out.print(bg);
                out.print(" " + row.get(i) + " ");
                bg = (bg.equals(SET_BG_COLOR_MAGENTA)) ? SET_BG_COLOR_WHITE : SET_BG_COLOR_MAGENTA;
            }
        }


        setGrey(out);
        System.out.printf(" %d ", rowNumber);
        setBlack(out);
        System.out.println();
    }


    private void setGrey(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }

    private void setBlack(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private void setWhite(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE);
    }


}
