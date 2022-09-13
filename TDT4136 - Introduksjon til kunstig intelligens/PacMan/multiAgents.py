# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        return successorGameState.getScore()

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def minmax(self, gameState):
        init_depth = 0
        return self.max(gameState, init_depth)[1] # return only move
        
    def max(self, gameState, depth):
        if self.terminalGameState(gameState, depth): # Check if gamestate is terminal
            return (self.evaluationFunction(gameState), None)
        pacmanLegelMoves = gameState.getLegalActions(self.index)
        bestMove = (float('-inf'), None) # Initial best move score starts at -inf and no best move
        for action in pacmanLegelMoves:
            nextGameState = gameState.generateSuccessor(self.index, action)
            nextGameStateValue = self.min(nextGameState, depth, 1)
            if nextGameStateValue[0] > bestMove[0]: # Chech if new score is higher
                bestMove = (nextGameStateValue[0],action)
        return bestMove
        
    def min(self, gameState, depth, ghostIndex):
            if self.terminalGameState(gameState, depth): # Check if gamestate is terminal
                return (self.evaluationFunction(gameState), None)
            bestMove = (float("inf"),None) # Initial best move score starts at inf and no best move
            numberOfGhosts = gameState.getNumAgents()
            actions = gameState.getLegalActions(ghostIndex)
            for action in gameState.getLegalActions(ghostIndex): # Iterate over all actions
                nextGameState = gameState.generateSuccessor(ghostIndex, action)
                isLastGhost = ghostIndex == numberOfGhosts-1
                if isLastGhost:
                    nextGameStateValue = self.max(nextGameState, depth+1) # pacman turn
                else:
                    nextGameStateValue = self.min(nextGameState, depth, ghostIndex+1) # ghost turn
                if nextGameStateValue[0] < bestMove[0]: # Chech if new score is lower
                    bestMove = (nextGameStateValue[0],action)
            return bestMove

    def terminalGameState(self, gameState, depth): # Check if depth reached, game is lost or won
        return depth == self.depth or gameState.isLose() or gameState.isWin()
    
    def getAction(self, gameState):
        return self.minmax(gameState)


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def alphabeta(self, gameState):
        init_depth = 0
        init_alpha = float('-inf')
        init_beta = float('inf')
        return self.max(gameState, init_depth, init_alpha, init_beta)[1] # return only move. alpha and beta is set to -inf, int
        
    def max(self, gameState, depth, alpha, beta):
        if self.terminalGameState(gameState, depth): # Check if gamestate is terminal
            return (self.evaluationFunction(gameState), None)
        bestMove = (float('-inf'), None) # Initial best move score starts at -inf and no best move
        
        pacmanLegelMoves = gameState.getLegalActions(self.index)
        for action in pacmanLegelMoves: # iterate over all legal actions for pacman
            nextGameState = gameState.generateSuccessor(self.index, action)
            nextGameStateValue = self.min(nextGameState, depth, 1, alpha, beta) # Get child nodes score
            if nextGameStateValue[0] > bestMove[0]: # Chech if new score is higher
                bestMove = (nextGameStateValue[0],action)
            
            alpha = max(alpha, nextGameStateValue[0])
            if alpha > beta: # check if to prune rest of tree
                return bestMove
        
        return bestMove
        
    def min(self, gameState, depth, ghostIndex, alpha, beta):
            if self.terminalGameState(gameState, depth): # Check if gamestate is terminal
                return (self.evaluationFunction(gameState), None)
            
            bestMove = (float("inf"),None) # Initial best move score starts at inf and no best move
            numberOfGhosts = gameState.getNumAgents()
            
            actions = gameState.getLegalActions(ghostIndex)
            for action in gameState.getLegalActions(ghostIndex): # Iterate over all actions for ghost
                nextGameState = gameState.generateSuccessor(ghostIndex, action)
                isLastGhost = ghostIndex == numberOfGhosts-1
                if isLastGhost: # Check if last ghost, run pacman, else run next ghost
                    nextGameStateValue = self.max(nextGameState, depth+1, alpha, beta) # pacman turn
                else:
                    nextGameStateValue = self.min(nextGameState, depth, ghostIndex+1, alpha, beta) # ghost turn
                
                if nextGameStateValue[0] < bestMove[0]:  # Chech if new score is lower
                    bestMove = (nextGameStateValue[0],action)
                
                beta = min(beta, nextGameStateValue[0])
                if beta < alpha: # check if to prune rest of tree
                    return bestMove
            
            return bestMove
        
    def terminalGameState(self, gameState, depth): # Check if depth reached, game is lost or won
        return depth == self.depth or gameState.isLose() or gameState.isWin()

    def getAction(self, gameState):
        return self.alphabeta(gameState)

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

# Abbreviation
better = betterEvaluationFunction
