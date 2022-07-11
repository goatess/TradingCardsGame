package TradingCardGame;

import java.util.ArrayList;
import java.util.List;

public class PlayerAI {
    final int MAX_CARDS_IN_HAND = 5;
    int health = 30;
    int mana = 0;
    int manaSlots = 0;
    int maxDeckCards = 20;
    int turnDamage = 0;
 //   String message = "";
    boolean turnPassed = false;

    List<Card> deck = new ArrayList<>();
    List<Card> hand = new ArrayList<>();
    List<Card> handTemp = new ArrayList<>();

    PlayerAI() {
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

    void heal(Card cardInUse) {
        health += cardInUse.getHEALING_CARD_VALUE();
        if (health > 30) {
            health = 30;
        }
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
        sortHandByHealingCards(hand);
        return hand.get(0);
    }

    Card sortHandAI(boolean gotHealing) {
        if (health < 26 && gotHealing) {
            getHealingCard();
        } else {
            if (hand.size() >= 4) {
                getLowestCostCard();
            } else {
                getHighestCostCard();
                if (hand.get(0).getManaCost() == 1) {
                    turnPassed = true;
                }
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
            bleedMessage();
        } else {
            Card cardDraw = pickRandomCard(deck);
            value = cardDraw.getDamage();
            if (hand.size() < MAX_CARDS_IN_HAND) {
                hand.add(cardDraw);
                drawMessage(value);
            } else {
                overloadMessage(value);
            }
        }
        deckMessage();
    }

    int playCard(Card cardInUse) { // plays one card from the hand
        int manaCost = cardInUse.getManaCost();
        int cardDamage = 0;
        if (mana >= manaCost) {
            mana -= manaCost;
            if (manaCost == 1) {
                heal(cardInUse);
                playHealingCardMessage();
            } else {
                cardDamage = cardInUse.getDamage();
                playCardMessage(cardDamage);
            }
            handTemp.add(cardInUse);
        }
        return cardDamage;
    }

    int turnLoop() {
        if (mana == 0 || turnPassed || hand.size() == 0) {
            passTurnMessage();
            turnPassed = true;
        } else {
            playCardsLoop();
        }
        discardPlayedCards();
        return turnDamage;
    }

    void playCardsLoop() {
        int cardDamage = 0;
        for (int i = 0; i < hand.size(); i++) {
            sortHandAI(true);
            Card cardInUse = hand.get(i);
            cardDamage = playCard(cardInUse);
            turnDamage += cardDamage;
        }
    }

    void sortHandAscending(List<Card> hand) {
        int manaCost = 0;
        int nextManaCost = 0;
        for (int card = 0; card < hand.size(); card++) {
            manaCost = hand.get(card).getManaCost();
            for (int nextCard = card + 1; card < hand.size() - nextCard; nextCard++) {
                nextManaCost = hand.get(nextCard).getManaCost();
                if (manaCost > nextManaCost || manaCost == 1) {
                    Card cardTemp = hand.get(nextCard);
                    hand.set(nextCard, hand.get(card));
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
            for (int nextCard = card + 1; card < hand.size() - nextCard; nextCard++) {
                nextManaCost = hand.get(nextCard).getManaCost();
                if (manaCost < nextManaCost || manaCost == 1) {
                    Card cardTemp = hand.get(nextCard);
                    hand.set(nextCard, hand.get(card));
                    hand.set(card, cardTemp);
                }
            }
        }
    }

    void sortHandByHealingCards(List<Card> hand) {
        for (int card = 0; card < hand.size(); card++) {
            if (hand.get(card).getManaCost() == 1) {
                Card cardTemp = hand.get(0);
                hand.set(0, hand.get(card));
                hand.set(card, cardTemp);
                break;
            }
            sortHandAI(false);
        }
    }

    void passTurnMessage() {
            String message = " PASS";
            System.out.println(message);
    }
    void drawMessage(int value) {
        String message = "A card with a value of " + value + " drawn. ";
         System.out.println(message);
    }

    void overloadMessage(int value) {
        String message = "A card with a value of " + value + " drawn. OVERLOAD! Card discarded. ";
     System.out.println(message);
    }

    void deckMessage() {
        String message = deck.size() + " cards in deck, " + hand.size() + " cards in hand.";
         System.out.println(message);
    }

    void bleedMessage() {
        String message = "No more cards in deck. BLEEDING OUT! HP-1! ";
         System.out.println(message);
    }

    void playHealingCardMessage(){
        String message = " Plays a HEALING CARD!";
         System.out.println(message);
    }
    
    void playCardMessage(int damage){
        String message = "Plays a card with a value of " + damage;
         System.out.println(message);
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