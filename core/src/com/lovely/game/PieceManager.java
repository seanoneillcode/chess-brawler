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
        legalMoves = new ArrayList<>();
    }

    void update(ChessBrawler context) {
        if (context.inputManager.justClicked) {
            if (selectedPiece != null) {
                for (Move move : legalMoves) {
                    Rectangle rectangle = new Rectangle(move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (rectangle.contains(context.inputManager.mousePosOnBoard.cpy().add(offset))) {
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
                if (canSelectPiece(piece) && rectangle.contains(context.inputManager.mousePosOnBoard.cpy().add(offset))) {
                    selectedPiece = piece;
                    break;
                }
            }
        }
        if (selectedPiece != null) {
            legalMoves = generateMoves(selectedPiece, context);
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
            for (Piece other : pieces) {
                if (piece != other && isSameTile(piece, other) && piece.state != Piece.State.DEAD) {
                    other.state = Piece.State.DEAD;
                }
            }
        }
        pieces.removeIf(p -> p.state == Piece.State.DEAD);
    }

    private boolean canSelectPiece(Piece piece) {
        return piece.moveTimer <= 0 && !piece.isLocked;
    }

    private boolean isEnemy(Piece piece, Piece other) {
        return !piece.owner.equals(other.owner);
    }

    private void movePiece(Piece selectedPiece, Move move) {
        Piece targetPiece = getPieceAt(move.x, move.y);
        if (targetPiece != null) {
            targetPiece.isLocked = true;
        }
        Vector2 target = new Vector2(move.x, move.y).scl(TILE_SIZE);
        Vector2 mov = target.cpy().sub(selectedPiece.pos);
        selectedPiece.mov = mov.cpy().nor().scl(GAME_SPEED);
        selectedPiece.moveTimer = (mov.len() / TILE_SIZE);
    }

    boolean isSameTile(Piece piece, Piece other) {
        return MathUtils.round(piece.pos.x / TILE_SIZE) == MathUtils.round(other.pos.x / TILE_SIZE)
                && MathUtils.round(piece.pos.y / TILE_SIZE) == MathUtils.round(other.pos.y / TILE_SIZE);
    }

    boolean isOccupied(int xpos, int ypos) {
        for (Piece piece : pieces) {
            if (MathUtils.round(piece.pos.x / TILE_SIZE) == xpos && MathUtils.round(piece.pos.y / TILE_SIZE) == ypos) {
                return true;
            }
        }
        return false;
    }

    Piece getPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            if (MathUtils.round(piece.pos.x / TILE_SIZE) == x && MathUtils.round(piece.pos.y / TILE_SIZE) == y) {
                return piece;
            }
        }
        return null;
    }

    Piece getTakingPieceAt(int x, int y) {
        Piece piece = getPieceAt(x, y);
        return piece != null && piece.moveTimer <= 0 ? piece : null;
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
            if (!isOccupied(xpos, ypos + ydir)) {
                moves.add(new Move(xpos, ypos + ydir));
            }
            Piece leftAtt = getTakingPieceAt(xpos - 1, ypos + ydir);
            if (leftAtt != null && isEnemy(piece, leftAtt)) {
                moves.add(new Move(xpos - 1, ypos + ydir, true));
            }
            Piece rightAtt = getTakingPieceAt(xpos + 1, ypos + ydir);
            if (rightAtt != null && isEnemy(piece, rightAtt)) {
                moves.add(new Move(xpos + 1, ypos + ydir, true));
            }
        }
        if (piece.type == CASTLE) {
            for (int i = xpos + 1; i < 8; i++) {
                if (!isOccupied(i, ypos)) {
                    moves.add(new Move(i, ypos));
                } else {
                    Piece attPiece = getTakingPieceAt(i, ypos);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(i, ypos, true));
                    }
                    break;
                }
            }
            for (int i = xpos - 1; i > -1; i--) {
                if (!isOccupied(i, ypos)) {
                    moves.add(new Move(i, ypos));
                } else {
                    Piece attPiece = getTakingPieceAt(i, ypos);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(i, ypos, true));
                    }
                    break;
                }
            }
            for (int i = ypos + 1; i < 8; i++) {
                if (!isOccupied(xpos, i)) {
                    moves.add(new Move(xpos, i));
                } else {
                    Piece attPiece = getTakingPieceAt(xpos, i);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(xpos, i, true));
                    }
                    break;
                }
            }
            for (int i = ypos - 1; i > -1; i--) {
                if (!isOccupied(xpos, i)) {
                    moves.add(new Move(xpos, i));
                } else {
                    Piece attPiece = getTakingPieceAt(xpos, i);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(xpos, i, true));
                    }
                    break;
                }
            }
        }
        return moves;
    }

    Move getLegalMove(int x, int y) {
        for (Move move : legalMoves) {
            if (move.x == x && move.y == y) {
                return move;
            }
        }
        return null;
    }
}
