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


}
