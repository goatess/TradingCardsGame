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
    String message = "";
    boolean turnPassed = false;
    List<Card> deck = new ArrayList<>();
    List<Card> hand = new ArrayList<>();
    List<Card> handTemp = new ArrayList<>();

    Player() {
        fillDeck();
        fillHand();
    }

    void fillDeck() {
        for (int i = 0; i < maxDeckCards; i++) {
            deck.add(new Card());
        }
    }

    void sortHand(List<Card> hand) {

    }

    void clearCards() {
        deck.clear();
        hand.clear();
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

    Card getLowestCostCard() {
        sortHand(hand);
        return hand.get(0);
    }

    void discardPlayedCards() {
        for (int i = 0; i < handTemp.size(); i++) {
            if (hand.contains(handTemp.get(i))) {
                hand.remove(handTemp.get(i));
            }
        }
    }

    Card pickRandomCard(List<Card> cards) {
        int randomIndex = (int) Math.floor(Math.random() * (cards.size()));
        Card card = cards.get(randomIndex);
        cards.remove(randomIndex);
        return card;
    }

    void drawCard() { // put one card from deck to hand
        int value = 0;
        if (deck.size() <= 0) {
            health--;
            message = "No more cards in deck. BLEEDING OUT! HP-1! ";
        } else {
            Card cardDraw = pickRandomCard(deck);
            value = cardDraw.getManaCost();
            if (hand.size() < MAX_CARDS_IN_HAND) {
                hand.add(cardDraw);
                message += "A card with a value of " + value + " drawn. ";
            } else {
                message += "A card with a value of " + value + " drawn. OVERLOAD! Card discarded. ";
            }
        }
        message = deck.size() + " cards in deck. ";
        message += hand.size() + " cards in hand.";
        System.out.println(message);
    }

    int playCard(Card cardInUse) {
        int manaCost = cardInUse.getManaCost();
        int cardDamage = 0;
        if (mana >= manaCost) {
            mana -= manaCost;
            if (manaCost == 1) {
                health += cardInUse.HEALING_CARD_VALUE;
                message = " Plays a HEALING CARD!";
                
            } else {
                cardDamage = cardInUse.getDamage();
                message = "Plays a card with a value of " + cardDamage;
            }
            handTemp.add(cardInUse);
            System.out.println(message);
        }
        return cardDamage;
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
            for (int i = 0; i < hand.size(); i++) {
                Card cardInUse = hand.get(i);
                //getLowestCostCard();
                cardDamage = playCard(cardInUse);
                turnDamage += cardDamage;
            }
        }
        discardPlayedCards();
        handTemp.clear();
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

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
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
