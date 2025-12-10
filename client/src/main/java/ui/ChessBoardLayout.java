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
            list.add(mappings.get(p));
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


    public void printBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        var printableBoard = getStringArray(board);

        //print file labels above and below the board

        setBlack(out);
        drawFileLabels(out, SET_BG_COLOR_LIGHT_GREY);
        drawChessBoard(out, printableBoard);
        drawFileLabels(out, SET_BG_COLOR_LIGHT_GREY);
    }

    private void drawFileLabels(PrintStream out, String color) {
        System.out.print(color);
        var labels = team == WHITE
                ? "    A   B  C   D  E   F   G  H     "
                : "    H   G  F   E  D   C   B  A     ";
        System.out.print(labels);
        System.out.println(RESET_BG_COLOR);
    }

    private void drawChessBoard(PrintStream out, ArrayList<ArrayList<String>> printableBoard) {

        if (team == WHITE) {
            for (ArrayList<String> row : printableBoard) {
                drawRow(row);
            }
        }
    }

    private void drawRow(ArrayList<String> row) {
        for (String square : row) {
            System.out.println(square);
        }
        System.out.println();
    }


    private void setBlack(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }


}
