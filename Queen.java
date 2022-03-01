package chess;

public class Queen extends ChessPiece {

	public Queen(Player player) {
		super(player);

	}

	public String type() {
		return "Queen";

	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//calls the chesspiece isvalidmove method to check for generic validations
		if(!super.isValidMove(move, board))
			return false;

		//creates a bishop and a rook, whose movements combined equal the queen's movement
		Bishop move1 = new Bishop(board[move.fromRow][move.fromColumn].player());
		Rook move2 = new Rook(board[move.fromRow][move.fromColumn].player());
		//invokes the bishop and rook isvalidmove methods to determine if the queen's move is valid
		return (move1.isValidMove(move, board) || move2.isValidMove(move, board));
	}
}