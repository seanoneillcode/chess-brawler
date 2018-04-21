package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.lovely.game.Constants.*;
import static com.lovely.game.PieceType.*;

public class PieceManager {

    List<Piece> pieces;
    Piece selectedPiece;
    private List<Move> legalMoves;
    private Vector2 offset;


    PieceManager() {
        offset = new Vector2(HALF_TILE_SIZE, HALF_TILE_SIZE);
    }

    void start() {
        pieces = new ArrayList<>();
        legalMoves = new ArrayList<>();
    }

    void update(ChessBrawler context) {
        if (context.inputManager.justClicked) {
            if (selectedPiece != null) {
                for (Move move : legalMoves) {
                    Rectangle rectangle = new Rectangle(move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (rectangle.contains(context.inputManager.mousePosOnBoard.cpy().add(offset))) {
                        movePiece(selectedPiece, move);
                        deselect();
                    }
                }
            }
            deselect();
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
                if (piece != other && isSameTile(piece, other) && piece.state != Piece.State.DEAD && piece.moveTimer <= 0 && other.moveTimer <= 0) {
                    other.state = Piece.State.DEAD;
                    context.screenShaker.shake(SHAKE_AMOUNT);
                    if (other.type == KING) {
                        context.gameWinner = piece.owner;
                        context.changeScreen(GAME_WON);
                    }
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
        if (piece.type == CASTLE || piece.type == QUEEN) {
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
        if (piece.type == BISHOP || piece.type == QUEEN) {
            for (int i = xpos + 1, j = ypos + 1; i < 8 && j < 8; i++, j++) {
                if (!isOccupied(i, j)) {
                    moves.add(new Move(i, j));
                } else {
                    Piece attPiece = getTakingPieceAt(i, j);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(i, j, true));
                    }
                    break;
                }
            }
            for (int i = xpos - 1, j = ypos + 1; i > -1 && j < 8; i--, j++) {
                if (!isOccupied(i, j)) {
                    moves.add(new Move(i, j));
                } else {
                    Piece attPiece = getTakingPieceAt(i, j);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(i, j, true));
                    }
                    break;
                }
            }
            for (int i = xpos + 1, j = ypos - 1; i < 8 && j > -1; i++, j--) {
                if (!isOccupied(i, j)) {
                    moves.add(new Move(i, j));
                } else {
                    Piece attPiece = getTakingPieceAt(i, j);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(i, j, true));
                    }
                    break;
                }
            }
            for (int i = ypos - 1, j = xpos - 1; i > -1 &&  j > -1; i--, j--) {
                if (!isOccupied(j, i)) {
                    moves.add(new Move(j, i));
                } else {
                    Piece attPiece = getTakingPieceAt(j, i);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(j, i, true));
                    }
                    break;
                }
            }
        }
        if (piece.type == KING) {
            List<Move> kingMoves = new ArrayList<>();
            kingMoves.add(pieceCheck(xpos, ypos + ydir, piece));
            kingMoves.add(pieceCheck(xpos, ypos - ydir, piece));
            kingMoves.add(pieceCheck(xpos + 1, ypos + ydir, piece));
            kingMoves.add(pieceCheck(xpos + 1, ypos - ydir, piece));
            kingMoves.add(pieceCheck(xpos - 1, ypos + ydir, piece));
            kingMoves.add(pieceCheck(xpos - 1, ypos - ydir, piece));
            kingMoves.add(pieceCheck(xpos + 1, ypos, piece));
            kingMoves.add(pieceCheck(xpos - 1, ypos, piece));
            for (Move move : kingMoves) {
                if (move != null) {
                    moves.add(move);
                }
            }
        }
        if (piece.type == KNIGHT) {
            List<Move> knightMoves = new ArrayList<>();
            knightMoves.add(pieceCheck(xpos + 1, ypos + (ydir * 2), piece));
            knightMoves.add(pieceCheck(xpos + 1, ypos - (ydir * 2), piece));
            knightMoves.add(pieceCheck(xpos - 1, ypos + (ydir * 2), piece));
            knightMoves.add(pieceCheck(xpos - 1, ypos - (ydir * 2), piece));

            knightMoves.add(pieceCheck(xpos + 2, ypos + ydir, piece));
            knightMoves.add(pieceCheck(xpos + 2, ypos - ydir, piece));
            knightMoves.add(pieceCheck(xpos - 2, ypos + ydir, piece));
            knightMoves.add(pieceCheck(xpos - 2, ypos - ydir, piece));
            for (Move move : knightMoves) {
                if (move != null) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    Move pieceCheck(int tx, int ty, Piece piece) {
        if (!inBoard(tx, ty)) {
            return null;
        }
        if (!isOccupied(tx, ty)) {
            return new Move(tx, ty);
        } else {
            Piece attPiece = getTakingPieceAt(tx, ty);
            if (attPiece != null && isEnemy(piece, attPiece)) {
                return new Move(tx, ty, true);
            }
        }
        return null;
    }

    boolean inBoard(int x, int y) {
        return x > -1 && x  < 8 && y > -1 && y < 8;
    }

    Move getLegalMove(int x, int y) {
        for (Move move : legalMoves) {
            if (move.x == x && move.y == y) {
                return move;
            }
        }
        return null;
    }

    public void deselect() {
        selectedPiece = null;
        legalMoves = Collections.emptyList();
    }
}
