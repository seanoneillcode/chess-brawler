package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lovely.game.Constants.*;
import static com.lovely.game.LoadingManager.SOUND_ORDER_1;
import static com.lovely.game.LoadingManager.SOUND_ORDER_2;
import static com.lovely.game.Piece.State.DEAD;
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
        selectedPiece = null;
    }

    void update(ChessBrawler context) {
        if (!context.screen.equals(PLAYING_GAME)) {
            return;
        }
        if (context.inputManager.justClicked) {
            if (selectedPiece != null) {
                for (Move move : legalMoves) {
                    Rectangle rectangle = new Rectangle(move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (rectangle.contains(context.inputManager.mousePosOnBoard.cpy().add(offset))) {
                        movePiece(selectedPiece, move, context);
                        context.soundManager.playSound(SOUND_ORDER_2, context);
                        deselect();
                    }
                }
            }
            deselect();
            for (Piece piece : pieces) {
                Rectangle rectangle = new Rectangle(piece.pos.x, piece.pos.y, TILE_SIZE, TILE_SIZE);
                if (canSelectPiece(piece, context) && rectangle.contains(context.inputManager.mousePosOnBoard.cpy().add(offset))) {
                    selectedPiece = piece;
                    context.soundManager.playSound(SOUND_ORDER_1, context);
                    break;
                }
            }
        }
        if (selectedPiece != null) {
            legalMoves = generateMoves(selectedPiece);
            for (Move move : legalMoves) {
                Rectangle rectangle = new Rectangle(move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                if (rectangle.contains(context.inputManager.mousePosOnBoard.cpy().add(offset))) {
                    context.inputManager.cursorState = InputManager.CursorState.MOVE;
                    Piece targetPiece = getPieceAt(move.x, move.y);
                    if (targetPiece != null) {
                        context.inputManager.cursorState = InputManager.CursorState.ATTACK;
                    }
                }
            }
        }
        for (Piece piece : pieces) {
            boolean moving = false;

            if (piece.moveTimer > 0 && piece.state == Piece.State.ALIVE) {
                moving = true;
                piece.animState = Piece.AnimState.WALK;
            }
            piece.moveTimer = piece.moveTimer - Gdx.graphics.getDeltaTime();
            piece.animTimer = piece.animTimer + Gdx.graphics.getDeltaTime();
            if (moving) {
                if (piece.moveTimer < 0) {
                    piece.mov = new Vector2();
                    piece.pos = new Vector2(MathUtils.round(piece.pos.x / TILE_SIZE) * TILE_SIZE, MathUtils.round(piece.pos.y / TILE_SIZE) * TILE_SIZE);
                }
                piece.pos.add(piece.mov);
            }
            if (moving && piece.state == Piece.State.ALIVE) {
                for (Piece other : pieces) {
                    if (other.state == Piece.State.ALIVE && piece != other && !piece.owner.equals(other.owner) && isSameTile(piece, other)) {
                        other.state = Piece.State.DYING;
                        other.animTimer = 0;
                        other.dieTimer = DYING_TIMER;
                        context.soundManager.playClang(context);
                        context.effectsManager.addBlood(other.pos);
                    }
                }
            }
            if (piece.state == Piece.State.ALIVE && piece.moveTimer <= 0) {
                piece.animState = Piece.AnimState.IDLE;
            }
            if (piece.state == Piece.State.DYING) {
                piece.animState = Piece.AnimState.DIE;
                piece.dieTimer = piece.dieTimer - Gdx.graphics.getDeltaTime();
                if (piece.dieTimer < 0) {
                    context.soundManager.playScream(context);
                    piece.state = DEAD;
                    if (piece.type == KING) {
                        context.gameWinner = piece.owner.equals(RED) ? BLUE : RED;
                        context.changeScreen(GAME_WON);
                    }
                }
            }
        }
        //pieces.removeIf(p -> p.state == Piece.State.DEAD);
        pieces.sort((o1, o2) -> {
            if (o1.state == DEAD) {
                if (o2.state == DEAD) {
                    return 0;
                } else {
                    return -1;
                }
            }
            if (o2.state == DEAD) {
                return 1;
            }
            return (int)(o2.pos.y - o1.pos.y);
        });
    }

    private boolean canSelectPiece(Piece piece, ChessBrawler context) {
        if (!piece.owner.equals(context.playerOwner) && !context.isTesting && piece.state == Piece.State.ALIVE) {
            return false;
        }
        if (piece.state == DEAD) {
            return false;
        }
        return !piece.isLocked;
    }

    private boolean isEnemy(Piece piece, Piece other) {
        return !piece.owner.equals(other.owner);
    }

    public void movePiece(Piece selectedPiece, Move move, ChessBrawler context) {
        Piece targetPiece = getPieceAt(move.x, move.y);
        if (targetPiece != null) {
            targetPiece.isLocked = false;
            context.soundManager.playAttack(context);
        }
        Vector2 target = new Vector2(move.x, move.y).scl(TILE_SIZE);
        Vector2 mov = target.cpy().sub(selectedPiece.pos);
        selectedPiece.mov = mov.cpy().nor().scl(GAME_SPEED);
        selectedPiece.moveTimer = ((mov.len() * GAME_PIECE_TIME_SPEED) / TILE_SIZE );
        selectedPiece.animTimer = 0;
    }

    boolean isSameTile(Piece piece, Piece other) {
        if (Math.abs(piece.pos.x - other.pos.x) < HALF_TILE_SIZE && Math.abs(piece.pos.y - other.pos.y) < HALF_TILE_SIZE) {
            if (piece.state != DEAD) {
                return true;
            }
        }
        return MathUtils.round(piece.pos.x / TILE_SIZE) == MathUtils.round(other.pos.x / TILE_SIZE)
                && MathUtils.round(piece.pos.y / TILE_SIZE) == MathUtils.round(other.pos.y / TILE_SIZE);
    }

    boolean isOccupied(int xpos, int ypos) {
        for (Piece piece : pieces) {
            if (MathUtils.round(piece.pos.x / TILE_SIZE) == xpos && MathUtils.round(piece.pos.y / TILE_SIZE) == ypos) {
                if (piece.state != DEAD) {
                    return true;
                }
            }
        }
        return false;
    }

    Piece getPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            if (MathUtils.round(piece.pos.x / TILE_SIZE) == x && MathUtils.round(piece.pos.y / TILE_SIZE) == y) {
                if (piece.state != DEAD) {
                    return piece;
                }
            }
        }
        return null;
    }

    Piece getTakingPieceAt(int x, int y) {
        Piece piece = getPieceAt(x, y);
        return piece != null  ? piece : null; // && piece.moveTimer <= 0
    }

    List<Move> generateMoves(Piece piece) {
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
                moves.add(new Move(xpos - 1, ypos + ydir, true, leftAtt.type.value));
            }
            Piece rightAtt = getTakingPieceAt(xpos + 1, ypos + ydir);
            if (rightAtt != null && isEnemy(piece, rightAtt)) {
                moves.add(new Move(xpos + 1, ypos + ydir, true, rightAtt.type.value));
            }
        }
        if (piece.type == CASTLE || piece.type == QUEEN) {
            for (int i = xpos + 1; i < 8; i++) {
                if (!isOccupied(i, ypos)) {
                    moves.add(new Move(i, ypos));
                } else {
                    Piece attPiece = getTakingPieceAt(i, ypos);
                    if (attPiece != null && isEnemy(piece, attPiece)) {
                        moves.add(new Move(i, ypos, true, attPiece.type.value));
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
                        moves.add(new Move(i, ypos, true, attPiece.type.value));
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
                        moves.add(new Move(xpos, i, true, attPiece.type.value));
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
                        moves.add(new Move(xpos, i, true, attPiece.type.value));
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
                        moves.add(new Move(i, j, true, attPiece.type.value));
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
                        moves.add(new Move(i, j, true, attPiece.type.value));
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
                        moves.add(new Move(i, j, true, attPiece.type.value));
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
                        moves.add(new Move(j, i, true, attPiece.type.value));
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
        moves.removeIf(move -> move.y < 0 || move.y > 7);
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
                return new Move(tx, ty, true, attPiece.type.value);
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
