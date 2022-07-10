package TradingCardGame;

public class Card {
    final int HEALING_CARD_VALUE = 5;
    public int manaCost = -1;
    public int damage = manaCost;
    final int[] MANACOST_CATALOG = { 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8, 0, 0, 1, 1, 2, 2, 2, 3,
            3, 3, 3,
            4, 4, 4, 5, 5, 6, 6, 7, 8 };
    final int[] DAMAGE_CATALOG = { 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10, 10,
            11, 12, 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12 };
    int[] selected = new int[40];

    public Card() { // Default Constructor - calls to the random card picker
        selectRandomCard();
    }

    public Card(int manaCost, int damage) { // manual card picker - Constructor for testing purposes
        this.manaCost = manaCost;
        this.damage = damage;
    }

    private int selectRandomCard() { // random card picker
        int randomIndex = (int) Math.floor(Math.random() * (MANACOST_CATALOG.length));
        if (MANACOST_CATALOG[randomIndex] != -1) {
            manaCost = MANACOST_CATALOG[randomIndex];
            damage = DAMAGE_CATALOG[randomIndex];
            MANACOST_CATALOG[randomIndex] = -1;
        } else
            selectRandomCard();
        return randomIndex;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public int getDamage() {
        return damage;
    }

    public int getHEALING_CARD_VALUE() {
        return HEALING_CARD_VALUE;
    }
}
