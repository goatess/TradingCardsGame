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

    int damage = 0;
    int playerI = 0;

    String manageRound() {
        Player opponent = player[1];
        boolean turnPassed = false;
        int opponentHealth = 0;
        int opponentNewHealth = 0;
        for (playerI = 0; playerI < numberOfPlayers; playerI++) {
            message = "Player's " + playerI + " turn. ";
            System.out.println(message);

            opponent = player[Math.abs(playerI - 1)];
            damage = player[playerI].playerTurn();
            turnPassed = player[playerI].getTurnPassed();

            if (turnPassed) {
                message = "Player " + playerI + " passes the turn to player " + (Math.abs(playerI - 1));
            } else {

                opponentNewHealth = opponent.getHealth() - damage;
                opponent.setHealth(opponentNewHealth);

                if (opponentNewHealth < 1) {
                    opponentNewHealth = 0;
                    gameContinues = false;
                    message = "Player " + (Math.abs(playerI - 1)) + " health drops to 0! Player " + playerI
                            + " WINS!!!";
                    winner = playerI;
                    System.out.println(message);
                    break;
                } else {
                    message = "Player " + (Math.abs(playerI - 1)) + " gets damaged with " + damage
                            + " points: Health drops from "
                            + opponentHealth + " to " + opponentNewHealth;
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
