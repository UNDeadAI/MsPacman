package MsPacmanExample.pacman.controllers.examples;

import java.util.EnumMap;
import java.util.Random;
import MsPacmanExample.pacman.controllers.Controller;
import MsPacmanExample.pacman.game.Game;
import MsPacmanExample.pacman.game.Constants.GHOST;
import MsPacmanExample.pacman.game.Constants.MOVE;

import static MsPacmanExample.pacman.game.Constants.*;

/*
 * The Class AggressiveGhosts.
 */
public final class AggressiveGhosts extends Controller<EnumMap<GHOST,MOVE>>
{	
	private final static float CONSISTENCY=1.0f;	//carry out intended move with this probability
	private Random rnd=new Random();
	private EnumMap<GHOST,MOVE> myMoves=new EnumMap<GHOST,MOVE>(GHOST.class);
	private MOVE[] moves=MOVE.values();
		
	/* (non-Javadoc)
	 * @see MsPacmanExample.pacman.controllers.Controller#getMove(MsPacmanExample.pacman.game.Game, long)
	 */
	public EnumMap<GHOST,MOVE> getMove(Game game,long timeDue)
	{		
		myMoves.clear();
		
		for(GHOST ghost : GHOST.values())				//for each ghost
			if(game.doesGhostRequireAction(ghost))		//if it requires an action
			{
				if(rnd.nextFloat()<CONSISTENCY)	//approach/retreat from the current node that Ms Pac-Man is at
					myMoves.put(ghost,game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
							game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost),DM.PATH));
				else									//else take a random action
					myMoves.put(ghost,moves[rnd.nextInt(moves.length)]);
			}

		return myMoves;
	}
}