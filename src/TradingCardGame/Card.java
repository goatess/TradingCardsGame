package TradingCardGame;

public class Card {
    public int value = -1;
    final int[] catalog = { 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8, 0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3,
            4, 4, 4, 5, 5, 6, 6, 7, 8 };
    final int HEALING_CARD_VALUE = 5;
    public int damage = value;

    // Damage independent from Mana cost
    // int[] cardsDamage = { 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10, 10,
    // 11, 12, 0, 0, 0, 1, 2, 2, 3, 3, 4, 4,
    // 5, 6, 6, 7, 8, 9, 10, 10, 11, 12 };

    Card() {
        int randomIndex = (int) Math.floor(Math.random() * (catalog.length));
        value = catalog[randomIndex];
    }

    Card(int value) {
        this.value = value;
    }

    void setValue() {
        int randomIndex = (int) Math.floor(Math.random() * (catalog.length));
        this.value = catalog[randomIndex];   
        }
    
    int getValue() {
        return this.value;
    }
}
