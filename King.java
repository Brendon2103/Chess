package chess;

public class King extends ChessPiece {

	public King(Player player) {
		super(player);
	}

	public String type() {
		return "King";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//calls the chesspiece isvalidmove method to check for generic validations
		if(!super.isValidMove(move, board)) {
			return false;
		}
		boolean valid = true;

		//Checks if the king moved one space in any direction
		if(Math.abs(move.fromRow-move.toRow) > 1 || Math.abs(move.fromColumn-move.toColumn) > 1){
			return false;
		}
		return valid;

	}
}
