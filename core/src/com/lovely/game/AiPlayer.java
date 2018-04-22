package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lovely.game.Constants.PLAYING_GAME;
import static com.lovely.game.Constants.TILE_SIZE;

public class AiPlayer {

    private static final float MIN_MOVE_TIME = 0.2f;
    private static final float MAX_MOVE_TIME = 12.0f;
    private static final float COOLDOWN = 0.5f;
    float moveTimer;
    private String aiOwner;
    int aiLevel;
    Piece king;

    AiPlayer(String aiOwner, int aiLevel) {
        moveTimer = 0;
        this.aiOwner = aiOwner;
        this.aiLevel = aiLevel;
        king = null;
    }

    public void update(ChessBrawler context) {
        if (!context.screen.equals(PLAYING_GAME)) {
            return;
        }
        if (king == null) {
            for ( Piece piece : context.pieceManager.pieces) {
                if (piece.owner.equals(aiOwner) && piece.type == PieceType.KING) {
                    king = piece;
                    break;
                }
            }
        }
        moveTimer = moveTimer - Gdx.graphics.getDeltaTime();
        if (moveTimer < 0) {
            List<Piece> aiPieces = new ArrayList<>();
            boolean kingInDanger = false;
            for ( Piece piece : context.pieceManager.pieces) {
                if (piece.owner.equals(aiOwner) && !piece.isLocked && piece.moveTimer < COOLDOWN) {
                    aiPieces.add(piece);
                }
                if (!piece.owner.equals(aiOwner) && (Math.abs(piece.pos.x - king.pos.x) < (2 * TILE_SIZE) && Math.abs(piece.pos.y - king.pos.y) < (TILE_SIZE * 2))) {
                    kingInDanger = true;
                }
            }
            moveTimer = MathUtils.random(MIN_MOVE_TIME, MAX_MOVE_TIME / (aiPieces.size() + 1));
            if (aiPieces.isEmpty()) {
                return;
            }
            boolean takenAction = false;
            int maxTries = 10;
            while (!takenAction) {
                maxTries = maxTries - 1;
                if (maxTries < 0) {
                    takenAction = true;
                }
                if (aiPieces.contains(king) && kingInDanger && MathUtils.random(100) > 50) {
                    List<Move> moves = context.pieceManager.generateMoves(king);
                    if (!moves.isEmpty()) {
                        Move move = moves.get(MathUtils.random(0, moves.size() - 1));
                        context.pieceManager.movePiece(king, move, context);
                    }
                }
                Piece randomPiece = aiPieces.get(MathUtils.random(0, aiPieces.size() - 1));
                if (randomPiece.type == PieceType.KING) {
                    randomPiece = aiPieces.get(MathUtils.random(0, aiPieces.size() - 1));
                }
                List<Move> moves = context.pieceManager.generateMoves(randomPiece);
                if (!moves.isEmpty()) {
                    takenAction = true;
                    if (aiLevel == 0) {
                        Move move = moves.get(MathUtils.random(0, moves.size() - 1));
                        context.pieceManager.movePiece(randomPiece, move, context);
                    }
                    if (aiLevel == 1) {
                        List<Move> takingMoves = getTakingMoves(moves, context);
                        Move move;
                        if (!takingMoves.isEmpty()) {
                            move = takingMoves.get(MathUtils.random(0, takingMoves.size() - 1));
                        } else {
                            move = moves.get(MathUtils.random(0, moves.size() - 1));
                        }
                        context.pieceManager.movePiece(randomPiece, move, context);
                    }
                    if (aiLevel == 2) {
                        List<Move> takingMoves = getTakingMoves(moves, context);
                        takingMoves.sort((o1, o2) -> o2.value - o1.value);
                        Move move;
                        if (!takingMoves.isEmpty()) {
                            move = takingMoves.get(MathUtils.random(0, takingMoves.size() - 1));
                        } else {
                            move = moves.get(MathUtils.random(0, moves.size() - 1));
                        }
                        context.pieceManager.movePiece(randomPiece, move, context);
                    }
                }
            }
        }
    }

    private List<Move> getTakingMoves(List<Move> moves, ChessBrawler context) {
        List<Move> takingMoves = new ArrayList<>();
        for (Move move : moves) {
            Piece other = context.pieceManager.getTakingPieceAt(move.x, move.y);
            if (other != null) {
                takingMoves.add(move);
            }
        }
        return takingMoves;
    }

}
