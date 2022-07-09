import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.startPCvsPCGame();
    }
}

class Cards {
    final int EMPTY_DECK = 0;
    final int FULL_HAND = 5;
    final int MIN_INDEX = 0;
    int[] cards = { 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8, 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4,
            4, 5, 5, 6, 6, 7, 8 };
    int healingCardValue = 5;
    List<Integer> cardsDeck = new ArrayList<Integer>();
    List<Integer> cardsHand = new ArrayList<Integer>();
    int maxIndex = 19;
    // Damage independent from Mana cost
    int[] cardsDamage = { 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12, 0, 0, 0, 1, 2, 2, 3, 3, 4, 4,
            5, 6, 6, 7, 8, 9, 10, 10, 11, 12 };

    void buildDeck() {
        int max = 39;
        for (int index = 0; index < 20; index++) {
            int randomIndex = (int) Math.floor(Math.random() * (max - MIN_INDEX + 1) + MIN_INDEX);
            cardsDeck.add(cards[randomIndex]);
            max--;
        }
    }

    void clearDeck() {
        this.maxIndex = 0;
        cardsDeck.clear();
    }

    void clearHand() {
        cardsHand.clear();
    }
}

class Player {
    final int EMPTY_DECK = 0;
    final int FULL_HAND = 5;
    final int MIN_INDEX = 0;
    int health = 30;
    int mana = 0;
    int manaSlots = 0;
    int[] cards = { 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8, 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4,
            4, 5, 5, 6, 6, 7, 8 };
    int healingCardValue = 5;
    List<Integer> cardsDeck = new ArrayList<Integer>();
    List<Integer> cardsHand = new ArrayList<Integer>();

    int maxIndex = 19;

    // Damage independent from Mana cost
    int[] cardsDamage = { 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12, 0, 0, 0, 1, 2, 2, 3, 3, 4, 4,
            5, 6, 6, 7, 8, 9, 10, 10, 11, 12 };

    public void clearDeck() {
        this.maxIndex = 0;
        cardsDeck.clear();
    }

    public void clearHand() {
        cardsHand.clear();
    }

    void buildDeck() {
        int max = 39;
        for (int index = 0; index < 20; index++) {
            int randomIndex = (int) Math.floor(Math.random() * (max - MIN_INDEX + 1) + MIN_INDEX);
            cardsDeck.add(cards[randomIndex]);
            max--;
        }
    }

    String drawCard() {
        String message = "";
        if (cardsDeck.size() <= EMPTY_DECK) {
            health--;
            message = "No more cards in deck. BLEEDING OUT! HP-1! ";
        } else {
            int randomIndex = (int) Math.floor(Math.random() * (maxIndex - MIN_INDEX + 1) + MIN_INDEX);
            int cardDraw = cardsDeck.get(randomIndex);
            cardsDeck.remove(randomIndex);
            message = cardsDeck.size() + " cards in deck.";
            maxIndex--;
            if (cardsHand.size() < FULL_HAND) {
                cardsHand.add(cardDraw);
                message += "A card with a value of " + cardDraw + " drawn. ";
            } else {
                message += "A card with a value of " + cardDraw + " drawn. OVERLOAD! Card discarded. ";
            }
        }
        message += cardsHand.size() + " cards in hand.";
        System.out.println(message);
        return message;
    }

    void buildDeckANdHand() {
        buildDeck();
        for (int i = 0; i < 3; i++) {
            drawCard();
        }
    }

    void prepareActivePlayer() {
        if (manaSlots < 10) {
            manaSlots += 1;
        }
        mana = manaSlots;
        drawCard();
    }

    int cardsPlayed = 0;
    int cardsPassed = 0;
    int turnDamage = 0;
    boolean turnPassed = false;
 
    String message = "";

    int playCards() {
        message = "";
        while ((cardsPassed + cardsPlayed) < (cardsHand.size()) && turnPassed == false) {
            if(mana < 1){
                turnPassed = true;
                break;

            }
            for (int index = 0; index < cardsHand.size(); index++) {
                int cardValue = cardsHand.get(index);
                
                if (mana >= cardValue) {
                    cardsPlayed++;
                    cardsHand.remove(index);
                    message = "Plays a card with a value of " + cardValue;
                    mana -= cardValue;
                    turnDamage += cardValue;
                    if (cardValue == 1) {
                        turnDamage -= cardValue;
                        health += healingCardValue;
                        message += " HEALING CARD!";
                    }
                } else
                    cardsPassed++;

                System.out.println(message);
            }
        }
        if (turnPassed == true) {
            message += " PASS";
        }
        turnPassed = true;
        System.out.println(message);
        return turnDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getManaSlots() {
        return manaSlots;
    }

    public int getNumberOfCardsInHand() {
        int size = cardsHand.size();
        return size;
    }

    public String getCardsinHandValues() {
        String values = "";
        for (int index = 0; index < cardsHand.size(); index++) {
            values += String.valueOf(cardsHand.get(index)) + ", ";
        }
        return values;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getCardsPlayed() {
        return cardsPlayed;
    }

    public String getPlayCardMessage() {
        return message;
    }

    public int getTurnDamage() {
        return turnDamage;
    }

    public boolean getTurnPassed() {
        return turnPassed;
    }

    public void setTurnPassed(boolean turnPassed) {
        this.turnPassed = turnPassed;
    }

    public void setManaSlots(int manaSlots) {
        this.manaSlots = manaSlots;
    }
}

class Game {
    Player[] player = new Player[2];
    int numberOfPlayers = 2;
    boolean gameContinues = true;
    int winner = -1;

    String startPCvsPCGame() {
        createPlayers();
        while (gameContinues) {
            manageRound();
        }
        String message = "PC vs PC game has succssessfully finished";
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

        for (int index = 0; index < numberOfPlayers; index++) {
            player[index].buildDeckANdHand();
        }
    }

    String manageRound() {
        int opponent = -1;
        String message = "";

        for (int active = 0; active < numberOfPlayers; active++) {
            message = "Player " + active + " turn. ";
            System.out.println(message);
            player[active].prepareActivePlayer();
            if (active == 1) {
                opponent = 0;
            } else
                opponent = 1;
            int damage = player[active].playCards();
            boolean turnPassed = player[active].getTurnPassed();
            if (turnPassed) {
                
            }
            int opponentHealth = player[opponent].getHealth();
            int opponentNewHealth = opponentHealth - damage;
            if (opponentNewHealth < 1) {
                opponentNewHealth = 0;
                gameContinues = false;
                message = "Player " + opponent + " health drops to 0! Player " + active + " WINS!!!";
                winner = active;
                System.out.println(message);
                break;
            } else {
                player[opponent].setHealth(opponentNewHealth);
                message = "Player " + opponent + " gets damaged with " + damage + " points: Health drops from "
                        + opponentHealth + " to " + opponentNewHealth;
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
