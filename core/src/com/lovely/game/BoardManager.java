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
        pieces.add(new Piece(new Vector2(0, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, TILE_SIZE), new Vector2(), PieceType.PAWN, PAWN_IDLE_RED, RED, PAWN_WALK_RED, PAWN_DIE_RED));


        pieces.add(new Piece(new Vector2(0, 0), new Vector2(), PieceType.CASTLE, CASTLE_IDLE_RED, RED, CASTLE_WALK_RED, CASTLE_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE, 0), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE_RED, RED, KNIGHT_WALK_RED, KNIGHT_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, 0), new Vector2(), PieceType.BISHOP, BISHOP_IDLE_RED, RED, BISHOP_WALK_RED, BISHOP_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, 0), new Vector2(), PieceType.QUEEN, QUEEN_IDLE_RED, RED, QUEEN_IDLE_RED, QUEEN_IDLE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, 0), new Vector2(), PieceType.KING, KING_IDLE_RED, RED, KING_WALK_RED, KING_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, 0), new Vector2(), PieceType.BISHOP, BISHOP_IDLE_RED, RED, BISHOP_WALK_RED, BISHOP_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, 0), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE_RED, RED, KNIGHT_WALK_RED, KNIGHT_DIE_RED));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, 0), new Vector2(), PieceType.CASTLE, CASTLE_IDLE_RED, RED, CASTLE_WALK_RED, CASTLE_DIE_RED));

        // BLUE
        pieces.add(new Piece(new Vector2(0, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, TILE_SIZE * 6), new Vector2(), PieceType.PAWN, PAWN_IDLE, BLUE, PAWN_WALK, PAWN_DIE));


        pieces.add(new Piece(new Vector2(0 , TILE_SIZE * 7), new Vector2(), PieceType.CASTLE, CASTLE_IDLE, BLUE, CASTLE_WALK, CASTLE_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE, TILE_SIZE * 7), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE, BLUE, KNIGHT_WALK, KNIGHT_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 2, TILE_SIZE * 7), new Vector2(), PieceType.BISHOP, BISHOP_IDLE, BLUE, BISHOP_WALK, BISHOP_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 3, TILE_SIZE * 7), new Vector2(), PieceType.KING, KING_IDLE, BLUE, KING_WALK, KING_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 4, TILE_SIZE * 7), new Vector2(), PieceType.QUEEN, QUEEN_IDLE, BLUE, QUEEN_IDLE, QUEEN_IDLE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 5, TILE_SIZE * 7), new Vector2(), PieceType.BISHOP, BISHOP_IDLE, BLUE, BISHOP_WALK, BISHOP_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 6, TILE_SIZE * 7), new Vector2(), PieceType.KNIGHT, KNIGHT_IDLE, BLUE, KNIGHT_WALK, KNIGHT_DIE));
        pieces.add(new Piece(new Vector2(TILE_SIZE * 7, TILE_SIZE * 7), new Vector2(), PieceType.CASTLE, CASTLE_IDLE, BLUE, CASTLE_WALK, CASTLE_DIE));
    }
}
