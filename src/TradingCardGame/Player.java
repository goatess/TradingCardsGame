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
        turnDamage = turnLoop();
        return turnDamage;
    }

    Card getLowestCostCard() {
        sortHandAscending(hand);
        return hand.get(0);
    }

    Card getHighestCostCard() {
        sortHandDescending(hand);
        return hand.get(0);
    }

    Card getHealingCard() {
        sortHandByHealing(hand);
        return hand.get(0);
    }

    Card sortHand(boolean gotHealing) {
        if (health < 26 && gotHealing) {
            getHealingCard();
        } else {
            if (hand.size() >= 3) {
                getLowestCostCard();
            }
            if (hand.size() < 3) {
                getHighestCostCard();
            }
        }
        return hand.get(0);
    }

    void discardPlayedCards() {
        for (int i = 0; i < handTemp.size(); i++) {
            if (hand.contains(handTemp.get(i))) {
                hand.remove(handTemp.get(i));
            }
        }
        handTemp.clear();
    }

    Card pickRandomCard(List<Card> cards) {
        int randomIndex = (int) Math.floor(Math.random() * (cards.size()));
        Card card = cards.get(randomIndex);
        cards.remove(randomIndex);
        return card;
    }

    void drawCard() { // picks a card from the deck and puts it to cards in hand
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
        message = deck.size() + " cards in deck, " + hand.size() + " cards in hand.";
        System.out.println(message);
    }

    int playCard(Card cardInUse) { // plays one card from the hand
        int manaCost = cardInUse.getManaCost();
        int cardDamage = 0;
        if (mana >= manaCost) {
            mana -= manaCost;
            if (manaCost == 1) {
                health += cardInUse.HEALING_CARD_VALUE;
                if (health > 30) {
                    health = 30;
                    cardDamage = 0;
                }
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

    int turnLoop() {
        int turnDamage = 0;
        int cardDamage = 0;
        int size = hand.size();
        if (mana == 0 || turnPassed || size == 0) {
            if (turnPassed == true) {
                message += " PASS";
                System.out.println(message);
            }
            turnPassed = true;
        } else {
            for (int i = 0; i < size; i++) {
                sortHand(true);
                Card cardInUse = hand.get(i);
                cardDamage = playCard(cardInUse);
                turnDamage += cardDamage;
            }
        }
        discardPlayedCards();  
        return turnDamage;
    }

    void sortHandAscending(List<Card> hand) {
        int manaCost = 0;
        int nextManaCost = 0;
        for (int card = 0; card < hand.size(); card++) {
            manaCost = hand.get(card).getManaCost();
            for (int card2 = card + 1; card < hand.size() - card2; card2++) {
                nextManaCost = hand.get(card2).getManaCost();
                // int minimum = Integer.min(manaCost, nextManaCost);
                if (manaCost > nextManaCost) {
                    Card cardTemp = hand.get(card2);
                    hand.set(card2, hand.get(card));
                    hand.set(card, cardTemp);
                }
            }
        }
    }

    void sortHandDescending(List<Card> hand) {
        int manaCost = 0;
        int nextManaCost = 0;
        for (int card = 0; card < hand.size(); card++) {
            manaCost = hand.get(card).getManaCost();
            for (int card2 = card + 1; card < hand.size() - card2; card2++) {
                nextManaCost = hand.get(card2).getManaCost();
                if (manaCost < nextManaCost) {
                    Card cardTemp = hand.get(card2);
                    hand.set(card2, hand.get(card));
                    hand.set(card, cardTemp);
                }
            }
        }
    }

     void sortHandByHealing(List<Card> hand) {
        for (int card = 0; card < hand.size(); card++) {
            if (hand.get(card).getManaCost() == 1) {
                Card cardTemp = hand.get(0);
                hand.set(0, hand.get(card));
                hand.set(card, cardTemp);
                break;
            }
            sortHand(false);
        }
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

    public int getHandCardIndex(Card card) {
        int cardIndex = hand.indexOf(card);
        return cardIndex;
    }
}