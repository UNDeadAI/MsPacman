package MsPacmanExample.pacman.controllers.examples;

import java.util.Random;
import MsPacmanExample.pacman.game.Game;
import MsPacmanExample.pacman.game.Constants.MOVE;
import MsPacmanExample.pacman.controllers.Controller;

/*
 * The Class RandomPacMan.
 */
public final class RandomPacMan extends Controller<MOVE>
{
	private Random rnd=new Random();
	private MOVE[] allMoves=MOVE.values();
	
	/* (non-Javadoc)
	 * @see MsPacmanExample.pacman.controllers.Controller#getMove(MsPacmanExample.pacman.game.Game, long)
	 */
	public MOVE getMove(Game game,long timeDue)
	{
		return allMoves[rnd.nextInt(allMoves.length)];
	}
}