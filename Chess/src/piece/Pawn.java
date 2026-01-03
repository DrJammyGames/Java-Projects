package piece;
import game.GamePanel;
public class Pawn extends Piece{
    public Pawn(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("images/w-pawn.png");
        }
        else {
            image = getImage("images/b-pawn.png");
        }
    }
}
