package com.lovely.game;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

import static com.lovely.game.Constants.BLUE;
import static com.lovely.game.Constants.RED;
import static com.lovely.game.Constants.TILE_SIZE;
import static com.lovely.game.LoadingManager.*;

public class BoardManager {
    
    void createPieces(ChessBrawler context) {
        List<Piece> pieces = context.pieceManager.pieces;
//        pieces.add(new Piece(new Vector2(0, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
//        pieces.add(new Piece(new Vector2(TILE_SIZE, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE, RED));


        pieces.add(new Piece(new Vector2(0, 0), new Vector2(), PieceType.CASTLE, CASTLE_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE, 0), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, 0), new Vector2(), PieceType.BISHOP, BISHOP_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, 0), new Vector2(), PieceType.QUEEN, QUEEN_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, 0), new Vector2(), PieceType.KING, KING_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, 0), new Vector2(), PieceType.BISHOP, BISHOP_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, 0), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE, RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, 0), new Vector2(), PieceType.CASTLE, CASTLE_IDLE, RED));

        // BLUE
        pieces.add(new Piece(new Vector2(0, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
//        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE));


        pieces.add(new Piece(new Vector2(0 , TILE_SIZE * 7), new Vector2(), PieceType.CASTLE, CASTLE_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE, TILE_SIZE * 7), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, TILE_SIZE * 7), new Vector2(), PieceType.BISHOP, BISHOP_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, TILE_SIZE * 7), new Vector2(), PieceType.KING, KING_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, TILE_SIZE * 7), new Vector2(), PieceType.QUEEN, QUEEN_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, TILE_SIZE * 7), new Vector2(), PieceType.BISHOP, BISHOP_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, TILE_SIZE * 7), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE, BLUE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, TILE_SIZE * 7), new Vector2(), PieceType.CASTLE, CASTLE_IDLE, BLUE));
    }
}
