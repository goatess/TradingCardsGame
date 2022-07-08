import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.addPlayers();
        game.turn();
    }
}

class Player {
    int health = 30;
    int mana = 0;
    int manaSlots = 0;
    int[] cards = { 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8, 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8 };
    int[] cardsDamage = { 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12 };
    List<Integer> cardsDeck = new ArrayList<Integer>();
    List<Integer> cardsHand = new ArrayList<Integer>();

    void buildDeck() {
        int max = 39;
        for (int index = 0; index < 20; index++) {
            int randomIndex = (int) Math.floor(Math.random() * (max - MIN + 1) + MIN);
            cardsDeck.add(cards[randomIndex]);
            max--;
        }
        
    }

    final int MIN = 0;
    
    int max = 19;
    String drawACard() {
        String message = "";
        if (cardsDeck.size() < 1) {
            health--;
            message = "No more cards in deck. BLEEDING OUT! HP-1! ";
        } else {
            int randomIndex = (int) Math.floor(Math.random() * (max - MIN + 1) + MIN);
            int cardDraw = cardsDeck.get(randomIndex);
            cardsDeck.remove(randomIndex);
            message = cardsDeck.size() + " cards in deck.";
            max--;
            if (cardsHand.size() < 5) {
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

    void initialSetupPlayer() {
        buildDeck();
        for (int i = 0; i < 3; i++) {
            drawACard();
        }
    }

    void setupActivePlayer() {
        if (manaSlots < 10) {
            manaSlots += 1;
        }
        mana = manaSlots;
        drawACard();
    }

    int playCards() {
        int totalDamage = 0;
        String message = "";
        for (int index = 0; index < cardsHand.size(); index++) {
            int cardValue = cardsHand.get(index);
            if (mana >= cardValue) {
                totalDamage += cardValue;
                mana -= cardValue;
                cardsHand.remove(index);
                message = "Plays a card with a value of " + cardValue;
                if (cardValue == 1){
                    health += 5;
                    message += " HEALING CARD!";
                }
            } else {
                message = "PASS TURN";
                break;
            }
            System.out.println(message);
        }
        return totalDamage;
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

    public int getCardsInHand_size() {
        int size = cardsHand.size();
        return size;
    }

    public int getMana() {
        return mana;
    }

}

class Game {
    Player[] player = new Player[2];
    boolean gameContinues = true;
    int damage = 0;

    void addPlayers() {
        for (int index = 0; index < 2; index++) {
            player[index] = new Player();
        }

        for (int index = 0; index < 2; index++) {
            player[index].initialSetupPlayer();
        }
    }

    String manageTurn() {
        int damage = 0;
        int opponent = 1;
        int opponentHP = 0;
        int opponentNewHP = 0;
        String message = "";

        for (int active = 0; active < 2; active++) {
            message = "Player " + active + " turn. ";
            System.out.println(message);
            player[active].setupActivePlayer();
            if (active == 1) {
                opponent = 0;
            } else
                opponent = 1;
            damage = player[active].playCards();
            opponentHP = player[opponent].getHealth();
            opponentNewHP = opponentHP - damage;
            if (opponentNewHP < 1) {
                opponentNewHP = 0;
                gameContinues = false;
                message = "Player " + opponent + " health drops to 0! " + active + " WINS!!!";
                System.out.println(message);
                break;
            } else {
                player[opponent].setHealth(opponentNewHP);
                message = "Player " + opponent + " gets damaged with " + damage + " points: Health drops from "
                        + opponentHP + " to " + opponentNewHP;
            }
            System.out.println(message);
        }
        return message;
    }

    void turn() {
        while (gameContinues) {
            manageTurn();
        }

    }

    void setDamage(int damage) {
        this.damage = damage;
    }
}
