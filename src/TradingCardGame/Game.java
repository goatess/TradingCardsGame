package TradingCardGame;

public class Game {
    int numberOfPlayers = 2;
    int winner = -1;
    boolean gameContinues = true;
    String message = "";

    Player[] player = new Player[numberOfPlayers];

    String startPCvsPCGame() {
        createPlayers();
        gameLoop();
        String message = "PC vs PC game has succsessfully finished\n";
        System.out.println(message);
        return message;
    }

    public void gameLoop() {  // as in a tennis match: turn < round < game
        while (gameContinues) {
            roundLoop();
        }
    }

    void createPlayers() {
        for (int index = 0; index < numberOfPlayers; index++) {
            player[index] = new Player();
        }
    }

    String roundLoop() {
        int turnDamage = 0;
        boolean turnPassed = false;
        int oppHealth = 30;
        int oppId = 1;
        
        System.out.println("\nROUND STARTS");
        for (int id = 0; id < numberOfPlayers; id++){
            System.out.println("\nPlayer's " + id + " turn. ");

            Player opponent = player[oppId];
            Player activePlayer = player[id];
            oppId = Math.abs(id-1);
            
            turnDamage = activePlayer.playerTurn();
            turnPassed = activePlayer.getTurnPassed();
            oppHealth = (opponent.getHealth() - turnDamage);
          
            if (turnPassed) {
                message = "Player " + id + " passes the turn to player " + oppId;
            } else {
                if (oppHealth < 1) {  
                    opponent.setHealth(oppHealth = 0);
                    gameContinues = false;
                    winner = id;
                    System.out.println("Player " + oppId + " health drops to 0! Player " + id + " WINS!!!\n");
                    break;
                } else if (turnDamage > 0){      
                    opponent.setHealth(oppHealth);
                    message = "Player " + oppId + " gets damaged with " + turnDamage + " points: Health drops to " + oppHealth;
                }
            }
            System.out.println(message);
        }
        return message;
    }

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