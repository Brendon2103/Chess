package chess;

import org.junit.Assert;
import org.junit.Test;

public class ChessModelTestExample {
    @Test
    public void currentPlayerTest() {
        ChessModel model = new ChessModel();
        Assert.assertEquals(model.currentPlayer(), Player.WHITE);
        model.setNextPlayer();
        Assert.assertEquals(model.currentPlayer(), Player.BLACK);
    }


    @Test
    public void pieceAtTest() {
        ChessModel model = new ChessModel();
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);
        Assert.assertEquals(model.pieceAt(0, 4), king);
    }

    private ChessModel initClearModel() {
        ChessModel model = new ChessModel();
        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++) {
                model.setPiece(r, c, null);
            }
        }
        return model;
    }

    @Test
    public void blackPawnIsValidDownStraightMoveTest() {
        final int INITIAL_ROW = 1;
        final int INITIAL_COL = 0;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4] - piece-1
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);

        // Add a Black pawn at [1][0] - piece-2
        Pawn bPawn = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |            K         |
         *   |P                     |
         *   |  wP                  |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *    -  -  -  -  -  -  -  -
         * */

        // Move straight 1 pos - no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));

        // Move straight 2 pos initially -  no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL)));

        //  Move straight 2 pos after initial move -  no piece where player wants to move
        Pawn bPawn4 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL, bPawn4);  // piece-3
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW + 1, INITIAL_COL, INITIAL_ROW + 3, INITIAL_COL)));

        // Move straight 1 pos - piece found where player wants to move
        Pawn wPawn1 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL, wPawn1);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));

        // Move straight 2 pos initially - piece found where player wants to move
        Pawn wPawn2 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL, wPawn2);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL)));

        // No moving backwards one pos
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));

        // No moving several rows at the time
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 4, INITIAL_COL)));

        // No moving outside of borders
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL - 1)));
    }

    @Test
    public void blackPawnIsValidDiagonalMoveTest() {
        final int INITIAL_ROW = 1;
        final int INITIAL_COL = 0;
        final int FINAL_ROW = 7;
        final int FINAL_COL = 7;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4]
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);

        // Add a Black pawn at [1][0]
        Pawn bPawn = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        // Diagonal moves (down - right)

        // Move diagonal where other player's piece available
        Pawn wPawn = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL + 1, wPawn);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 1)));

        // Move diagonal where piece from same player available
        Pawn bPawn1 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL + 1, bPawn1);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 1)));

        // Move diagonal where no piece available
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL + 1, null);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 1)));

        // Move diagonal - not diagonal
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 3, INITIAL_COL + 1)));

        // Diagonal moves (down - left)

        // Move diagonal where other player's piece available
        Pawn bPawn4 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW, FINAL_COL, bPawn4);

        Pawn wPawn5 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, FINAL_COL - 1, wPawn5);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, FINAL_COL, INITIAL_ROW + 1, FINAL_COL - 1)));

        // Move diagonal where piece from same player available
        Pawn bPawn5 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW, FINAL_COL, bPawn5);
        Pawn bPawn6 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 1, FINAL_COL - 1, bPawn6);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, FINAL_COL, INITIAL_ROW + 1, FINAL_COL - 1)));

        // Move diagonal where no piece available
        model.setPiece(INITIAL_ROW + 1, FINAL_COL - 1, null);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, FINAL_COL, INITIAL_ROW + 1, FINAL_COL - 1)));

        // No moving backwards diagonally
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, FINAL_COL - 1)));

        // No moving outside of borders
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 8, INITIAL_COL - 1)));
    }

    @Test
    public void whitePawnIsValidDownStraightMoveTest() {
        final int INITIAL_ROW = 6;
        final int INITIAL_COL = 0;

        ChessModel model = initClearModel();

        // Add the White King in [7][4]
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);

        // Add a White pawn at [6][0]
        Pawn pawn = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW, INITIAL_COL, pawn);
        model.setNextPlayer();
        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |   p                  |
         *   |wp                    |
         *   |            k         |
         *    -  -  -  -  -  -  -  -
         * */

        // Move straight 1 pos - no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));

        // Move straight 2 pos initially -  no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL)));

        //  Move straight 2 pos after initial move -  no piece where player wants to move
        Pawn bPawn4 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL, bPawn4);  // piece-3
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW - 1, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL)));

        // Move straight 1 pos - piece found where player wants to move
        Pawn bPawn1 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL, bPawn1);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));

        // Move straight 2 pos initially - piece found where player wants to move
        Pawn wPawn2 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL, wPawn2);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL)));

        // No moving backwards one pos
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));

        // No moving several rows at the time
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 4, INITIAL_COL)));

        // No moving outside of borders
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL - 1)));
    }

    @Test
    public void whitePawnIsValidDiagonalMoveTest() {
        final int INITIAL_ROW = 6;
        final int INITIAL_COL = 0;
        final int FINAL_ROW = 0;
        final int FINAL_COL = 7;

        ChessModel model = initClearModel();

        // Add the White King in [7][4]
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);

        // Add a White pawn at [6][0]
        Pawn bPawn = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        // Diagonal moves (down - right)

        // Move diagonal where other player's piece available
        Pawn wPawn = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL + 1, wPawn);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        // Move diagonal where piece from same player available
        Pawn bPawn1 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL + 1, bPawn1);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        // Move diagonal where no piece available
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL + 1, null);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        // Move diagonal - not diagonal
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL + 1)));

        // Diagonal moves (down - left)

        // Move diagonal where other player's piece available
        Pawn bPawn4 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW, FINAL_COL, bPawn4);

        Pawn wPawn5 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, FINAL_COL - 1, wPawn5);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, FINAL_COL, INITIAL_ROW - 1, FINAL_COL - 1)));

        // Move diagonal where piece from same player available
        Pawn bPawn5 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW, FINAL_COL, bPawn5);
        Pawn bPawn6 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 1, FINAL_COL - 1, bPawn6);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, FINAL_COL, INITIAL_ROW - 1, FINAL_COL - 1)));

        // Move diagonal where no piece available
        model.setPiece(INITIAL_ROW - 1, FINAL_COL - 1, null);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, FINAL_COL, INITIAL_ROW - 1, FINAL_COL - 1)));

        // No moving backwards diagonally
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, FINAL_COL - 1)));

        // No moving outside of borders
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 8, INITIAL_COL - 1)));
    }

    @Test
    public void BlackRookIsValidtMoveTest() {
        int INITIAL_COL = 0;
        int INITIAL_ROW = 0;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4] - piece-1
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);


        // Add a Black rook at [0][0] - piece-2
        Rook bRook = new Rook(Player.BLACK);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bRook);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   | R           K        |
         *   | wp                   |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *    -  -  -  -  -  -  -  -
         * */

        // Move straight 1 pos - no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));


        // Move straight 2 pos initially -  no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL)));

        // Move straight 1 pos - piece found where player wants to move
        Pawn wPawn1 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL, wPawn1);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));

        // moving several rows at the time
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 4, INITIAL_COL)));

        // No moving outside of borders
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL - 1)));

        // can't jump over a piece
        Pawn bPawn1 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL, wPawn1);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 3, INITIAL_COL)));

        // can't move diagonal
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 1)));

        //move horizontal
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 1)));

        //move horizontal multiple spaces
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 2)));

        //cannot move through king
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 5)));
    }

    @Test
    public void WhiteRookIsValidtMoveTest() {
        int INITIAL_COL = 0;
        int INITIAL_ROW = 7;

        ChessModel model = initClearModel();

        // Add the White King in [7][4] - piece-1
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);


        // Add a White rook at [7][0] - piece-2
        Rook bRook = new Rook(Player.WHITE);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bRook);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   | wp                   |
         *   | r        k           |
         *    -  -  -  -  -  -  -  -
         * */

        // Move straight 1 pos - no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));


        // Move straight 2 pos initially -  no piece where player wants to move
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL)));

        // Move straight 1 pos - piece found where player wants to move
        Pawn wPawn1 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL, wPawn1);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));

        // moving several rows at the time
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 4, INITIAL_COL)));

        // No moving outside of borders
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 3, INITIAL_COL - 1)));

        // can't jump over a piece
        Pawn bPawn1 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL, wPawn1);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL)));

        // can't move diagonal
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        //move horizontal
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 1)));

        //move horizontal multiple spaces
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 2)));

        //cannot move through king
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 5)));
    }

    @Test
    public void BlackKnightIsValidtMoveTest() {
        int INITIAL_COL = 1;
        int INITIAL_ROW = 0;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4] - piece-1
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);


        // Add a Black Knight at [0][1] - piece-2
        Knight bKnight = new Knight(Player.BLACK);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bKnight);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2, piece4, and piece-3
         *    -  -  -  -  -  -  -  -
         *   |   kn       k         |
         *   |   wp                 |
         *   |      bp              |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *    -  -  -  -  -  -  -  -
         * */

        //cannot jump in one direction (Vertical)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL)));

        //cannot jump in one direction (Horizontal)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 1)));

        // can jump over black and white pieces
        Pawn wPawn1 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL, wPawn1);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 1)));

        // can jump over black and white pieces
        Pawn wPawn2 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL, wPawn1);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 1)));

//        // Allowed to move backwards
//        model.setPiece(INITIAL_ROW +2 , INITIAL_COL + 1, bKnight);
//        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW , INITIAL_COL , INITIAL_ROW, INITIAL_COL)));
        //Check out of bounds
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL - 1)));

        // cannot capture own piece
        Pawn bPawn2 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL + 1, bPawn2);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 1)));

        //can capture opposing piece
        Pawn wPawn3 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL + 1, wPawn3);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 1)));
    }

    @Test
    public void BlackKnightIsValidtMoveBackwardsTest() {
        ChessModel model = initClearModel();
        // Add a Black Knight at [2][2]
        Knight bKnight = new Knight(Player.BLACK);
        model.setPiece(2, 2, bKnight);
        model.setNextPlayer();

        //jump backwards
        Assert.assertTrue((model.isValidMove(new Move(2, 2, 0, 1))));

        //move backwards with both color pieces in the path
        Pawn wPawn3 = new Pawn(Player.WHITE);
        model.setPiece(1, 1, wPawn3);
        Pawn wPawn4 = new Pawn(Player.BLACK);
        model.setPiece(2, 1, wPawn4);
        Assert.assertTrue((model.isValidMove(new Move(2, 2, 0, 1))));
    }

    @Test
    public void WhiteKnightIsValidtMoveTest() {
        int INITIAL_COL = 1;
        int INITIAL_ROW = 7;

        ChessModel model = initClearModel();

        // Add the White King in [7][4] - piece-1
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);


        // Add a White Knight at [7][1] - piece-2
        Knight bKnight = new Knight(Player.WHITE);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bKnight);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2, piece4, and piece-3
         *    -  -  -  -  -  -  -  -
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |      bp              |
         *   |  wp                  |
         *   |  kn       k          |
         *    -  -  -  -  -  -  -  -
         * */

        //cannot jump in one direction (Vertical)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL)));

        //cannot jump in one direction (Horizontal)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 1)));

        // can jump over black and white pieces
        Pawn wPawn1 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL, wPawn1);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 1)));

        // can jump over black and white pieces
        Pawn wPawn2 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL, wPawn1);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 1)));

        //Check out of bounds
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL - 1)));

        // cannot capture own piece
        Pawn bPawn2 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL + 1, bPawn2);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 1)));

        //can capture opposing piece
        Pawn wPawn3 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL + 1, wPawn3);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 1)));
    }

    @Test
    public void WhiteKnightIsValidMoveBackwardsTest() {
        ChessModel model = initClearModel();
        // Add a White Knight at [5][2]
        Knight bKnight = new Knight(Player.WHITE);
        model.setPiece(5, 2, bKnight);
        model.setNextPlayer();

        //jump backwards
        Assert.assertTrue((model.isValidMove(new Move(5, 2, 7, 1))));

        //move backwards with both color pieces in the path
        Pawn wPawn3 = new Pawn(Player.WHITE);
        model.setPiece(6, 1, wPawn3);
        Pawn wPawn4 = new Pawn(Player.BLACK);
        model.setPiece(5, 1, wPawn4);
        Assert.assertTrue((model.isValidMove(new Move(5, 2, 7, 1))));
    }

    @Test
    public void blackBishopIsValidMoveTest() {
        final int INITIAL_ROW = 0;
        final int INITIAL_COL = 2;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4] - piece-1
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);

        // Add a Black bishop at [0][2] - piece-2
        Bishop bPawn = new Bishop(Player.BLACK);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |     b      K         |
         *   |       p              |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *    -  -  -  -  -  -  -  -
         * */

        // can move diagonal 2 spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 2)));

        // can move diagonal 1 spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 1)));

        // can move diagonal multiple spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 4, INITIAL_COL + 4)));

        // cannot move in one direction (horizontal)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));

        //cannot move in one direction (vertical)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL)));

        //cannot jump over pieces
        Pawn bPawn2 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL + 1, bPawn2);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 2)));

        Pawn bPawn3 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL + 1, bPawn3);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 2)));

        // take opposite color piece
        Pawn wPawn5 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL - 1, wPawn5);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL - 1)));

        // Try to take same color piece
        Pawn bPawn5 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL - 1, bPawn5);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL - 1)));

        // move out of bounds
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 6, INITIAL_COL + 6)));

        // backwards
    }

    @Test
    public void blackBishopIsValidMoveBackwardsTest() {
        ChessModel model = initClearModel();
        // Add a black bishop at [2][4]
        Bishop bBishop = new Bishop(Player.BLACK);
        model.setPiece(2, 4, bBishop);
        model.setNextPlayer();

        //jump backwards
        Assert.assertTrue((model.isValidMove(new Move(2, 4, 0, 2))));

        //move backwards with  color piece in the path
        Pawn wPawn3 = new Pawn(Player.WHITE);
        model.setPiece(1, 3, wPawn3);
        Assert.assertFalse((model.isValidMove(new Move(2, 5, 0, 2))));

    }

    @Test
    public void whiteBishopIsValidMoveTest() {
        final int INITIAL_ROW = 7;
        final int INITIAL_COL = 2;

        ChessModel model = initClearModel();

        // Add the White King in [7][4] - piece-1
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);

        // Add a White bishop at [7][2] - piece-2
        Bishop bPawn = new Bishop(Player.WHITE);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |       p              |
         *   |     b      k         |
         *    -  -  -  -  -  -  -  -
         * */

        // can move diagonal 2 spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 2)));

        // can move diagonal 1 spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        // can move diagonal multiple spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 4, INITIAL_COL + 4)));

        // cannot move in one direction (horizontal)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));

        //cannot move in one direction (vertical)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL)));

        //cannot jump over pieces
        Pawn bPawn2 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL + 1, bPawn2);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 2)));

        Pawn bPawn3 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL + 1, bPawn3);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 2)));

        // take opposite color piece
        Pawn wPawn5 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL + 1, wPawn5);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        // Try to take same color piece
        Pawn bPawn5 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL - 1, bPawn5);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL - 1)));

        // move out of bounds
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL - 1)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 6, INITIAL_COL - 6)));

    }

    @Test
    public void whiteBishopIsValidMoveBackwardsTest() {
        ChessModel model = initClearModel();
        // Add a white bishop at [5][4]
        Bishop bBishop = new Bishop(Player.WHITE);
        model.setPiece(5, 4, bBishop);
        model.setNextPlayer();

        //jump backwards
        Assert.assertTrue((model.isValidMove(new Move(5, 4, 7, 2))));

        //move backwards with  color piece in the path
        Pawn wPawn3 = new Pawn(Player.WHITE);
        model.setPiece(6, 3, wPawn3);
        Assert.assertFalse((model.isValidMove(new Move(5, 4, 7, 2))));

    }

    @Test
    public void blackQueenIsValidMoveTest() {
        final int INITIAL_ROW = 0;
        final int INITIAL_COL = 3;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4] - piece-1
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);

        // Add a Black Queen at [0][3] - piece-2
        Queen bPawn = new Queen(Player.BLACK);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |         Q K          |
         *   |         wp           |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *    -  -  -  -  -  -  -  -
         * */

        //move forward one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));

        // move forward multiple spaces
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 4, INITIAL_COL)));

        //move to the left one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));

        //move to the left multiple spaces
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 2)));

        // try and move to the right (king in the way should be false)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 1)));

        // Try and capture an apposing piece in front of queen
        Pawn wPawn5 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 1, INITIAL_COL, wPawn5);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));

        // try and jump over the king
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 3)));

        //try and move in a spot that can't happen
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 3, INITIAL_COL + 2)));

        //Move outside the border
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 5)));

        //Diagonal testing

        //diagonal move one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 1)));

        //diagonal move multiple spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 3, INITIAL_COL + 3)));

        //capture opposite piece diagonal
        Pawn wPawn6 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL + 2, wPawn6);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 2)));

        //capture same piece
        Pawn wPawn7 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW + 2, INITIAL_COL + 2, wPawn7);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 2, INITIAL_COL + 2)));
    }

    @Test
    public void blackQueenIsValidMoveBackwardsTest() {
        ChessModel model = initClearModel();
        // Add a black Queen at [3][1]
        Queen bBishop = new Queen(Player.BLACK);
        model.setPiece(3, 0, bBishop);
        model.setNextPlayer();

        //move back wards on a diagonal
        Assert.assertTrue((model.isValidMove(new Move(3, 0, 0, 3))));

        //move diagonal with a piece in the way
        Pawn wPawn3 = new Pawn(Player.WHITE);
        model.setPiece(2, 1, wPawn3);
        Assert.assertFalse((model.isValidMove(new Move(3, 0, 0, 3))));

        //move diagonal backwards and capture the piece
        Pawn wPawn4 = new Pawn(Player.WHITE);
        model.setPiece(2, 1, wPawn4);
        Assert.assertTrue((model.isValidMove(new Move(3, 0, 2, 1))));

    }

    @Test
    public void whiteQueenIsValidMoveTest() {
        final int INITIAL_ROW = 7;
        final int INITIAL_COL = 3;

        ChessModel model = initClearModel();

        // Add the White King in [7][4] - piece-1
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);

        // Add a White Queen at [7][3] - piece-2
        Queen bPawn = new Queen(Player.WHITE);
        model.setPiece(INITIAL_ROW, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |         WP           |
         *   |         Q K          |
         *    -  -  -  -  -  -  -  -
         * */

        //move forward one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));

        // move forward multiple spaces
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 4, INITIAL_COL)));

        //move to the left one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 1)));

        //move to the left multiple spaces
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL - 2)));

        // try and move to the right (king in the way should be false)
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 1)));

        // Try and capture an apposing piece in front of queen
        Pawn wPawn5 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 1, INITIAL_COL, wPawn5);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL)));

        // try and jump over the king
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW, INITIAL_COL + 3)));

        //try and move in a spot that can't happen
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL + 2)));

        //Move outside the border
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL)));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 5)));

        //Diagonal testing

        //diagonal move one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 1)));

        //diagonal move multiple spots
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 3, INITIAL_COL + 3)));

        //capture opposite piece diagonal
        Pawn wPawn6 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL + 2, wPawn6);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 2)));

        //capture same piece
        Pawn wPawn7 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW - 2, INITIAL_COL + 2, wPawn7);
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 2, INITIAL_COL + 2)));
    }

    @Test
    public void whiteQueenIsValidMoveBackwardsTest() {
        ChessModel model = initClearModel();
        // Add a White Queen at [4][0]
        Queen bBishop = new Queen(Player.WHITE);
        model.setPiece(4, 0, bBishop);
        model.setNextPlayer();

        //move back wards on a diagonal
        Assert.assertTrue((model.isValidMove(new Move(4, 0, 7, 3))));

        //move diagonal with a piece in the way
        Pawn wPawn3 = new Pawn(Player.BLACK);
        model.setPiece(5, 1, wPawn3);
        Assert.assertFalse((model.isValidMove(new Move(4, 0, 7, 3))));
    }

    @Test
    public void blackKingiIsValidMoveTest() {
        final int INITIAL_ROW = 0;
        final int INITIAL_COL = 4;

        ChessModel model = initClearModel();

        // Add the Black King in [0][4] - piece-1
        King king = new King(Player.BLACK);
        model.setPiece(0, 4, king);

        // Add a Black Pawn at [1][4] - piece-2
        Pawn bPawn = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW+1, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |          K          |
         *   |          p           |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *    -  -  -  -  -  -  -  -
         * */

        //Move forward one spot
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL )));

        //move left one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW  , INITIAL_COL-1 )));

        //move right one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW , INITIAL_COL +1)));

        // move diagonal one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL +1 )));

        //move multiple spots( cannot do )
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW -3, INITIAL_COL )));

        //out of bounds
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW -1, INITIAL_COL )));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL + 5)));

        // capture opposite piece
        Pawn wPawn6 = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW , INITIAL_COL + 1, wPawn6);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW , INITIAL_COL + 1)));
    }

    @Test
    public void WhiteKingiIsValidMoveTest() {
        final int INITIAL_ROW = 7;
        final int INITIAL_COL = 4;

        ChessModel model = initClearModel();

        // Add the Black King in [7][4] - piece-1
        King king = new King(Player.WHITE);
        model.setPiece(7, 4, king);

        // Add a Black Pawn at [6][4] - piece-2
        Pawn bPawn = new Pawn(Player.WHITE);
        model.setPiece(INITIAL_ROW-1, INITIAL_COL, bPawn);
        model.setNextPlayer();

        /*
         *   Board status from piece-1, piece2 and piece-3
         *    -  -  -  -  -  -  -  -
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |                      |
         *   |          P           |
         *   |          K           |
         *    -  -  -  -  -  -  -  -
         * */

        //Move forward one spot
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL )));

        //move left one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW  , INITIAL_COL-1 )));

        //move right one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW , INITIAL_COL +1)));

        // move diagonal one spot
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW - 1, INITIAL_COL +1 )));

        //move multiple spots( cannot do )
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW +3, INITIAL_COL )));

        //out of bounds
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW +1, INITIAL_COL )));
        Assert.assertFalse(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW + 1, INITIAL_COL + 5)));

        // capture opposite piece
        Pawn wPawn6 = new Pawn(Player.BLACK);
        model.setPiece(INITIAL_ROW , INITIAL_COL - 1, wPawn6);
        Assert.assertTrue(model.isValidMove(new Move(INITIAL_ROW, INITIAL_COL, INITIAL_ROW , INITIAL_COL - 1)));
    }
}
