package TradingCardGame;

import java.util.ArrayList;
import java.util.List;

public class Player {
    final int MAX_CARDS_IN_HAND = 5;
    int health = 30;
    int mana = 0;
    int manaSlots = 0;
    int maxDeckCards = 20;
    int turnDamage = 0;
    List<Card> deck = new ArrayList<>();
    List<Card> hand = new ArrayList<>();
    String message = "";
    boolean turnPassed = false;

    void fillDeck() {

        for (int i = 0; i < maxDeckCards; i++) {
            deck.add(new Card());
        }
    }

    Player() {
        fillDeck();
        fillHand();
    }

    void sortHand(List<Card> hand) {
    }

    void clearDeck() {
        deck.clear();
    }

    void clearHand() {
        hand.clear();
    }

    Card takeRandomCard(List<Card> cards) {
        int randomIndex = (int) Math.floor(Math.random() * (cards.size()));
        Card card = cards.get(randomIndex);
        cards.remove(randomIndex);
        return card;
    }

    void drawCard() {// put one card from deck to hand
        String message = "";

        if (deck.size() <= 0) {
            health--;
            message = "No more cards in deck. BLEEDING OUT! HP-1! ";
        } else {
            Card cardDraw = takeRandomCard(deck);

            if (hand.size() < MAX_CARDS_IN_HAND) {
                hand.add(cardDraw);
                message += "A card with a value of " + cardDraw.value + " drawn. ";
            } else {
                message += "A card with a value of " + cardDraw.value + " drawn. OVERLOAD! Card discarded. ";
            }

        }
        message = deck.size() + " cards in deck. ";
        message += hand.size() + " cards in hand.";

        System.out.println(message);
    }

    void fillHand() {
        for (int i = 0; i < 3; i++) {
            drawCard();
        }
    }

    void increaseManaSlots() {
        if (manaSlots < 10) {
            manaSlots += 1;
        }
    }

    void refillMana() {
        mana = manaSlots;
    }

    void prepareActivePlayer() {
        increaseManaSlots();
        refillMana();
        drawCard();
    }

    int playerTurn() {
        prepareActivePlayer();
        turnDamage = playCardsLoop();
        return turnDamage;
    }

    int playCardsLoop() {
        int turnDamage = 0;
        int cardDamage = 0;
        if (mana == 0 || turnPassed || hand.size() == 0) {
            if (turnPassed == true) {
                message += " PASS";
                System.out.println(message);
            }
            turnPassed = true;
        } else {
            for (int index = 0; index < hand.size(); index++) {
                Card cardInUse = getLowestCostCard();
                cardDamage = playCard(cardInUse);
                turnDamage += cardDamage;
            }
        }
        return turnDamage;
    }

    Card getLowestCostCard() {
        sortHand(hand);
        return hand.get(0);
    }

    int playCard(Card cardInUse) {
        int cardDamage = 0;
        if (mana >= cardInUse.value) {
            message = "Plays a card with a value of " + cardInUse.value;
            mana -= cardInUse.value;

            if (cardInUse.value == 1) {
                health += cardInUse.HEALING_CARD_VALUE;
                message += " HEALING CARD!";
            } else {
                cardDamage = cardInUse.value;
            }
            hand.remove(cardInUse);
            System.out.println(message);
        }
        return cardDamage;
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

    public String getCardsinHandValues() {
        String values = "";
        for (int index = 0; index < hand.size(); index++) {
            values += String.valueOf(hand.get(index)) + ", ";
        }
        return values;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getPlayCardMessage() {
        return message;
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

    public int getDamage() {
        return turnDamage;
    }
}
