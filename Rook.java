package chess;

public class Rook extends ChessPiece {

	public Rook(Player player) {

		super(player);

	}

	public String type() {

		return "Rook";

	}

	// determines if the move is valid for a rook piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//calls the chesspiece isvalidmove method to check for generic validations
		if(!super.isValidMove(move, board))
			return false;
		boolean valid = true;

		//checks if the rook's row and column both changed, invalid if true
		if((move.fromRow != move.toRow && move.fromColumn != move.toColumn)){
			return false;
		}

		//integer used to determine if the rook is moving up or down
		int diff = move.toRow - move.fromRow;

		//if statements for checking if the rook will pass through a piece, returning invalid if true
		//up
		if(diff < 0){
			for(int i = -1; i > diff; i--){
				if(board[move.fromRow+i][move.fromColumn] != null){
					return false;
				}
			}
		}
		//down
		if(diff > 0){
			for(int i = 1; i < diff; i++){
				if(board[move.fromRow+i][move.fromColumn] != null){
					return false;
				}
			}
		}
		//integer used to determine if rook is moving left or right

		//left
		int diffc = move.toColumn - move.fromColumn;
		if(diffc < 0){
			for(int i = -1; i > diffc; i--){
				if(board[move.fromRow][move.fromColumn+i] != null){
					return false;
				}
			}
		}
		//right
		if(diffc > 0){
			for(int i = 1; i < diffc; i++){
				if(board[move.fromRow][move.fromColumn+i] != null){
					return false;
				}
			}
		}
		return valid;

	}
}