package chess;

public abstract class ChessPiece implements IChessPiece {

	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid = true;
		//checks if the piece moved anywhere, returns invalid if not
		if (move.fromRow == move.toRow && move.fromColumn == move.toColumn)
			return false;
		//checks if the move is within the boundaries of the board
		if (move.toRow < 0 || move.toRow > 7 || move.toColumn < 0 || move.toColumn  > 7 || move.fromRow < 0 ||move.fromRow > 7 || move.fromColumn < 0 || move.fromColumn > 7 )
			return false;

		//checks if the piece is moving to a place occupied by a piece of the same color
		if (board[move.toRow][move.toColumn] != null){
			if(board[move.toRow][move.toColumn].player() == this.owner){
				return false;
			}
		}
//		//verifies that this piece is at the intended space, returns false if not
//		if (board[move.fromRow][move.fromColumn] != this){
//			return false;
//		}
		//checks if the move is within the boundaries of the board
		if(move.fromRow < 0 || move.fromRow > 7 || move.fromColumn < 0 || move.fromColumn > 7){
			return false;
		}

		return valid;
	}
}
