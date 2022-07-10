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
        String message = "PC vs PC game has succsessfully finished";
        System.out.println(message);
        return message;
    }

    public void gameLoop() {
        while (gameContinues) {
            manageRound();
        }
    }

    void createPlayers() {
        for (int index = 0; index < numberOfPlayers; index++) {
            player[index] = new Player();
        }
    }

    String manageRound() {
        int turnDamage = 0;
        int playerI = 0;
        Player opponent = player[1];
        boolean turnPassed = false;
        int oppOldHealth = 0;
        int oppHealth = 0;
        for (playerI = 0; playerI < numberOfPlayers; playerI++) {
            message = "\nPlayer's " + playerI + " turn. ";
            System.out.println(message);

            opponent = player[Math.abs(playerI - 1)];
            int opponentI = Math.abs(playerI - 1);
            turnDamage = player[playerI].playerTurn();
            turnPassed = player[playerI].getTurnPassed();

            if (turnPassed) {
                message = "Player " + playerI + " passes the turn to player " + opponentI;
            } else {
                oppOldHealth = opponent.getHealth();
                oppHealth = oppOldHealth - turnDamage;
                opponent.setHealth(oppHealth);
                if (oppHealth < 1) {
                    oppHealth = 0;
                    gameContinues = false;
                    message = "Player " + opponentI + " health drops to 0! Player " + playerI
                            + " WINS!!!\n";
                    winner = playerI;
                    System.out.println(message);
                    break;
                } else {
                    message = "Player " + opponentI + " gets damaged with " + turnDamage
                            + " points: Health drops from "
                            + oppOldHealth + " to " + oppHealth;
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
}
