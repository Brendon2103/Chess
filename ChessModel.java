package chess;

public class ChessModel implements IChessModel {	 
    private IChessPiece[][] board;
	private Player player;

	// declare other instance variables as needed

	public ChessModel() {
		board = new IChessPiece[8][8];
		player = Player.WHITE;

		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight (Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight (Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(Player.WHITE);
		}
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}
	}

	public boolean isComplete(Player p) {
		boolean checkmate = true;
		if(inCheck(p)){
			for(int i = 0; i < 8; i++){
				for(int i2 = 0; i2 < 8; i2++){
					if(board[i][i2] != null && board[i][i2].player() == p){
						for(int i3 = 0; i3 < 8; i3++){
							for (int i4 = 0; i4 < 8; i4++){
								Move move = new Move(i,i2,i3,i4);
								if(board[i][i2]!= null && board[i][i2].isValidMove(move,board)){
									var temp = board[i3][i4];
									board[i3][i4] = board[i][i2];
									board[i][i2] = null;
									if(!inCheck(p)){
										checkmate = false;
									}
									board[i][i2] = board[i3][i4];
									board[i3][i4] = temp;
								}
							}
						}
					}
				}
			}
		}
		return checkmate;
	}

	public boolean isValidMove(Move move) {
		boolean valid = false;

		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board) )
				return true;

		return valid;
	}

	public void move(Move move) {
		board[move.toRow][move.toColumn] =  board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	//incheck
//find other player king first
//scan board for not null spaces and if they are of your color
//check for all  your pieces if it is a valid move for them to move to king space.
//create move to go to king space and check if it is valid
//then display in check popup

	//checkmate
//go through each piece of team in check and try to input every possible move then check if still in check
	public boolean inCheck(Player p) {
		int x=0;
		int y=0;
		boolean valid = false;
		//locate this players king
		for (int i = 0; i <  8; i++) {
			for(int i2 = 0; i2 < 8; i2++) {
				if (board[i][i2] != null && board[i][i2].type() == "King" && board[i][i2].player() == p) {
					x = i2;
					y = i;
				}
			}
		}
		//go through all pieces on the board, if they are of your color
		for(int i = 0; i < 8; i++){
			for(int i2 = 0; i2 < 8 ; i2++){
				Move move = new Move(i,i2,y,x);
				if(board[i][i2] != null && board[i][i2].player() != p && board[i][i2].isValidMove(move,board)){
					valid = true;
				}
			}
		}

		return valid;
	}

	public Player currentPlayer() {

		return player;
	}

	public int numRows() {
		return 8;
	}

	public int numColumns() {
		return 8;
	}

	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	public void setNextPlayer() {
		player = player.next();
	}

	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	public void AI() {
		/*
		 * Write a simple AI set of rules in the following order.
		 * a. Check to see if you are in check.
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check
		 *
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 *c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 *d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

	}

}