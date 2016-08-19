package MsPacmanExample.pacman.controllers.examples;

import java.util.Random;
import MsPacmanExample.pacman.controllers.Controller;
import MsPacmanExample.pacman.game.Game;

import static MsPacmanExample.pacman.game.Constants.*;

/*
 * The Class RandomNonRevPacMan.
 */
public final class RandomNonRevPacMan extends Controller<MOVE>
{	
	Random rnd=new Random();
	
	/* (non-Javadoc)
	 * @see MsPacmanExample.pacman.controllers.Controller#getMove(MsPacmanExample.pacman.game.Game, long)
	 */
	public MOVE getMove(Game game,long timeDue)
	{			
		MOVE[] possibleMoves=game.getPossibleMoves(game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade());		//set flag as false to prevent reversals	
		
		return possibleMoves[rnd.nextInt(possibleMoves.length)];
	}
}