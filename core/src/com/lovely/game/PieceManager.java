package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.lovely.game.Constants.*;
import static com.lovely.game.PieceType.CASTLE;
import static com.lovely.game.PieceType.PAWN;

public class PieceManager {

    List<Piece> pieces;
    Piece selectedPiece;
    private List<Move> legalMoves;
    private Vector2 offset;

    PieceManager() {
        pieces = new ArrayList<>();
        offset = new Vector2(HALF_TILE_SIZE, HALF_TILE_SIZE);
    }

    void update(ChessBrawler context) {
        if (context.inputManager.justClicked) {
            if (selectedPiece != null) {
                for (Move move : legalMoves) {
                    Rectangle rectangle = new Rectangle(move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (rectangle.contains(context.inputManager.mousePos.cpy().add(offset))) {
                        movePiece(selectedPiece, move);
                        selectedPiece = null;
                        legalMoves = null;
                    }
                }
            }
            selectedPiece = null;
            legalMoves = Collections.emptyList();
            for (Piece piece : pieces) {
                Rectangle rectangle = new Rectangle(piece.pos.x, piece.pos.y, TILE_SIZE, TILE_SIZE);
                if (canSelectPiece(piece) && rectangle.contains(context.inputManager.mousePos.cpy().add(offset))) {
                    selectedPiece = piece;
                    legalMoves = generateMoves(piece, context);
                    break;
                }
            }
        }
        for (Piece piece : pieces) {
            if (piece.moveTimer > 0) {
                piece.moveTimer = piece.moveTimer - Gdx.graphics.getDeltaTime();
                if (piece.moveTimer < 0) {
                    piece.mov = new Vector2();
                    piece.pos = new Vector2(MathUtils.round(piece.pos.x / TILE_SIZE) * TILE_SIZE, MathUtils.round(piece.pos.y / TILE_SIZE) * TILE_SIZE);
                }
                piece.pos.add(piece.mov);
            }
        }
        pieces.removeIf(p -> p.state == Piece.State.DEAD);
    }

    private boolean canSelectPiece(Piece piece) {
        return piece.moveTimer <= 0;
    }

    private void movePiece(Piece selectedPiece, Move move) {
        Vector2 target = new Vector2(move.x, move.y).scl(TILE_SIZE);
        Vector2 mov = target.cpy().sub(selectedPiece.pos);
        selectedPiece.mov = mov.cpy().nor().scl(GAME_SPEED);
        selectedPiece.moveTimer = (mov.len() / TILE_SIZE);
    }

    public boolean isOccupied(int xpos, int ypos) {
        for (Piece piece : pieces) {
            if (MathUtils.round(piece.pos.x / TILE_SIZE) == xpos && MathUtils.round(piece.pos.y / TILE_SIZE) == ypos) {
                return true;
            }
        }
        return false;
    }

    List<Move> generateMoves(Piece piece, ChessBrawler context) {
        if (!piece.owner.equals(context.playerOwner) && !context.isTesting) {
            return Collections.emptyList();
        }
        List<Move> moves = new ArrayList<>();
        int xpos = MathUtils.round(piece.pos.x / TILE_SIZE);
        int ypos = MathUtils.round(piece.pos.y / TILE_SIZE);
        int ydir = piece.owner.equals(RED) ? 1 : -1;
        if (piece.type == PAWN) {
            moves.add(new Move(xpos, ypos + ydir));
        }
        if (piece.type == CASTLE) {
            for (int i = xpos + 1; i < 8; i++) {
                if (!isOccupied(i, ypos)) {
                    moves.add(new Move(i, ypos));
                } else {
                    break;
                }
            }
            for (int i = xpos - 1; i > -1; i--) {
                if (!isOccupied(i, ypos)) {
                    moves.add(new Move(i, ypos));
                } else {
                    break;
                }
            }
            for (int i = ypos + 1; i < 8; i++) {
                if (!isOccupied(xpos, i)) {
                    moves.add(new Move(xpos, i));
                } else {
                    break;
                }
            }
            for (int i = ypos - 1; i > -1; i--) {
                if (!isOccupied(xpos, i)) {
                    moves.add(new Move(xpos, i));
                } else {
                    break;
                }
            }
        }
        return moves;
    }

    public boolean isLegalMove(int x, int y) {
        if (legalMoves == null || legalMoves.isEmpty()) {
            return false;
        }
        for (Move move : legalMoves) {
            if (move.x == x && move.y == y) {
                return true;
            }
        }
        return false;
    }
}
