package chess;

public class Knight extends ChessPiece {

	public Knight(Player player) {
		super(player);
	}

	public String type() {
		return "Knight";
	}

	public boolean isValidMove(Move move, IChessPiece[][] board){
		//calls the chesspiece isvalidmove method to check for generic validations
		if(!super.isValidMove(move, board))
			return false;
		boolean valid = true;

		//checks if the knight moved 2 rows up or down
		if(Math.abs(move.fromRow-move.toRow) == 2) {
			//if it did then check if it moved one space to right or left, return false if not
			if (Math.abs(move.fromRow - move.toRow) != 2 || Math.abs(move.fromColumn - move.toColumn) != 1) {
				valid = false;
			}
		}
		//if the knight did not move 2 rows, then check if it moved 1 row and 2 columns, return false if not
		else if(Math.abs(move.fromRow-move.toRow) != 1 || Math.abs(move.fromColumn-move.toColumn) != 2){
			valid = false;
		}
		return valid;

	}

}
