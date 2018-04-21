package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class AiPlayer {

    private static final float MIN_MOVE_TIME = 0.2f;
    private static final float MAX_MOVE_TIME = 3.0f;
    float moveTimer;
    private String aiOwner;
    int aiLevel;

    AiPlayer(String aiOwner, int aiLevel) {
        moveTimer = 0;
        this.aiOwner = aiOwner;
        this.aiLevel = aiLevel;
    }

    public void update(ChessBrawler context) {
        moveTimer = moveTimer - Gdx.graphics.getDeltaTime();
        if (moveTimer < 0) {
            moveTimer = MathUtils.random(MIN_MOVE_TIME, MAX_MOVE_TIME);
            List<Piece> aiPieces = new ArrayList<>();
            for ( Piece piece : context.pieceManager.pieces) {
                if (piece.owner.equals(aiOwner)) {
                    aiPieces.add(piece);
                }
            }
            boolean takenAction = false;
            while (!takenAction) {
                Piece randomPiece = aiPieces.get(MathUtils.random(0,aiPieces.size() - 1));
                List<Move> moves = context.pieceManager.generateMoves(randomPiece);
                if (!moves.isEmpty()) {
                    takenAction = true;
                    if (aiLevel == 0) {
                        Move move = moves.get(MathUtils.random(0, moves.size() - 1));
                        context.pieceManager.movePiece(randomPiece, move);
                    }
                    if (aiLevel == 1) {
                        Move move;
                        if (MathUtils.random(100) > 50) {
                            move = getTakingMove(moves, context);
                        } else {
                            move = moves.get(MathUtils.random(0, moves.size() - 1));
                        }
                        context.pieceManager.movePiece(randomPiece, move);
                    }
                    if (aiLevel == 2) {
                        Move move;
                        if (MathUtils.random(100) > 10) {
                            move = getTakingMove(moves, context);
                        } else {
                            move = moves.get(MathUtils.random(0, moves.size() - 1));
                        }
                        context.pieceManager.movePiece(randomPiece, move);
                    }
                }
            }
        }
    }

    private Move getTakingMove(List<Move> moves, ChessBrawler context) {
        for (Move move : moves) {
            Piece other = context.pieceManager.getTakingPieceAt(move.x, move.y);
            if (other != null) {
                return move;
            }
        }
        return moves.get(0);
    }

}
