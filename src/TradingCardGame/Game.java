package TradingCardGame;

public class Game {
    int numberOfPlayers = 2;
    Player[] player = new Player[numberOfPlayers];
    int winner = -1;
    boolean gameContinues = true;
    int roundNumber = 1; 
    
    String startPCvsPCGame() {
        createPlayers();
        gameLoop();
        String message = finishMessage();
        return message;
    }

    void createPlayers() {
        for (int index = 0; index < numberOfPlayers; index++) {
            player[index] = new Player();
        }
    }

    public void gameLoop() { // as in a tennis match: turn < round < game
        while (gameContinues) {
            roundLoop();
        }
    }

    String roundLoop() {
        int damage = 0;
        boolean turnPassed = false;
        int opponentHealth = 30;
        int opponent = 1;
        String message = "";

        roundMessage();
        for (int active = 0; active < numberOfPlayers; active++) {
            opponent = Math.abs(active - 1);
            Player opponentPlayer = player[opponent];
            Player activePlayer = player[active];
            
            playerTurnMessage(active);
            damage = activePlayer.playerTurn();
            turnPassed = activePlayer.getTurnPassed();
            opponentHealth = (opponentPlayer.getHealth() - damage);

            if (turnPassed) {
                message = passTurnMessage(active, opponent);
            } else {
                if (opponentHealth < 1) {
                    opponentPlayer.setHealth(opponentHealth = 0);
                    gameContinues = false;
                    winner = active;
                    message = winnerMessage(winner, opponent);
                    break;
                } else if (damage > 0) {
                    opponentPlayer.setHealth(opponentHealth);
                    message = turnDamageMessage(opponent, opponentHealth, damage);
                }
            }
        }
        return message;
    }

    // MESSAGES
    
    void roundMessage() {
        roundNumber++;
        System.out.println("\nROUND " + roundNumber);
    }

    void playerTurnMessage(int active) {
        System.out.println("\nPlayer's " + active + " turn. ");
    }

    String passTurnMessage(int active, int opponent) {
        String message = "Player " + active + " passes the turn to player " + opponent;
        System.out.println(message);
        return message;
    }

    String winnerMessage(int winner, int opponent) {
        String message = "Player " + opponent + " health drops to 0! Player " + winner + " WINS!!!\n";
        System.out.println(message);
        return message;
    }

    String turnDamageMessage(int opponent, int opponentHP, int damage) {
        String message = "Player " + opponent + " gets damaged with " + damage + " points: Health drops to "
                + opponentHP;
        System.out.println(message);
        return message;
    }

    String finishMessage(){
        String message = "PC vs PC game has succsessfully finished\n";
        System.out.println(message);
        return message;
    }

    // GETTERS AND SETTERS

    public int getWinner() {
        return winner;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}