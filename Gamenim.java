import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class Gamenim extends Game {
	
	int marks[] = {1, 2, 3}; //'O' for computer, 'X' for human
	int current=13;
    int winningLines = 0;
    
    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;    
    
    public Gamenim() {
    	currentState = new StateTicTacToe();
    }
    
    public boolean isWinState(State state)
    {
        //StateTicTacToe tstate = (StateTicTacToe) state;
        //player who did the last move
        // int previous_player = (state.player==0 ? 1 : 0);  
        // int mark = marks[previous_player];
        
        
        if (current < 1) {
            
            return true;
        }
        
        return false;
    }
    
    public boolean isStuckState(State state) {
        
        return false;
    }
	
	
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state) || isStuckState(state))
			return null;
		
		Set<State> successors = new HashSet<State>();
        StateTicTacToe tstate = (StateTicTacToe) state;
        
        StateTicTacToe successor_state;
        
        
        
        
        successor_state = new StateTicTacToe(tstate);
        successor_state.board = tstate.board;
        successor_state.player = (state.player==0 ? 1 : 0); 
        
        successors.add(successor_state);
        
        
    
        return successors;
    }
    
    
    
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);
    	
	    	if (previous_player==0) //computer wins
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}

        return NeutralScore;
    }
    
    
    public static void main(String[] args) throws Exception {
        
        Game game = new Gamenim(); 
        Search search = new Search(game);
        
        int current=13;
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //System.out.println("");
        int pos=1;
        while (true) {
        	
        	StateTicTacToe 	nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human
                  
            	  //get human's move
                  System.out.println("\nThere are "+current+" coins left");
                  System.out.print("Take 1, 2, or 3 coins> ");
                  pos = Integer.parseInt( in.readLine() );
            	  current-=pos;
                  nextState = new StateTicTacToe((StateTicTacToe)game.currentState);
                  nextState.player = 1;
                  nextState.board = current;
                  System.out.println("You: take "+pos+" coins");
                  if ( current<1)  {
            
                   
                    System.out.println("Computer wins!");
                    
                    
                    return;
                }
                  break;
                  
              case 0: //Computer
            	  
                  System.out.println("\nThere are "+current+" coins left");
            	  nextState = (StateTicTacToe)search.bestSuccessorState(pos, current);
                  //System.out.println(nextState);
            	  nextState.player = 0;
                  //System.out.println(pos);
                  int next=4-pos;
            	  System.out.println("Robot: takes "+next+" coins");
                  current-=next;
                  if ( game.isWinState(game.currentState) ) {
            
                    System.out.println("You win!");
                    
                    return;
                }
                  break;
            }
                        
            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            
            //Who wins?
            if ( game.isWinState(game.currentState) ) {
            
            	if (game.currentState.player == 1) //i.e. last move was by the computer
            		System.out.println("Computer wins!");
            	else
            		System.out.println("You win!");
            	
            	break;
            }
        }
    }
}