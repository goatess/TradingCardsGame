package TradingCardGame;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest {

    @Test
    public void each_player_starts_with_30_health() {

        // arrange
        final int INITIAL_HEALTH = 30;
        int actualHealth = 0;
        Player player = new Player();

        // act
        actualHealth = player.getHealth();

        // assert
        assertEquals(INITIAL_HEALTH, actualHealth);
    }

    @Test
    public void each_player_starts_with_0_manaSlots() {

        // arrange
        final int INITIAL_MANASLOTS = 0;
        int actualManaSlots = 0;
        Player player = new Player();

        // act
        actualManaSlots = player.getManaSlots();

        // assert
        assertEquals(INITIAL_MANASLOTS, actualManaSlots);
    }

    @Test
    public void player_receives_3_cards_from_deck_initially() {

        // arrange
        final int INITIAL_CARDS_IN_HAND = 3;
        int actualCardsInHand = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        actualCardsInHand = game.player[0].hand.size();

        // assert
        assertEquals(INITIAL_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void each_player_receives_3_cards_from_deck_() {

        // arrange
        final boolean BOTH_PLAYERS_GOT_3_CARDS = true;
        final int CARDS_IN_HAND_PLAYER1 = 3;
        final int CARDS_IN_HAND_PLAYER2 = 3;
        boolean actuallyWhereSame = false;
        int actualCardsPlayer1 = 3;
        int actualCardsPlayer2 = 3;
        Game game = new Game();

        // act
        game.createPlayers();
        actualCardsPlayer1 = game.player[0].hand.size();
        actualCardsPlayer2 = game.player[1].hand.size();
        actuallyWhereSame = actualCardsPlayer1 == actualCardsPlayer2;

        // assert
        assertEquals(CARDS_IN_HAND_PLAYER1, actualCardsPlayer1);
        assertEquals(CARDS_IN_HAND_PLAYER2, actualCardsPlayer2);
        assertEquals(BOTH_PLAYERS_GOT_3_CARDS, actuallyWhereSame);
    }

    @Test
    public void active_player_draws_a_card_from_the_deck_to_his_hand() {
        // arrange
        final int CARDS_IN_HAND = 4;
        int actualCardsInHand = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].drawCard();
        actualCardsInHand = game.player[0].hand.size();

        // assert
        assertEquals(CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void active_player_gets_1_more_mana_slot() {
        // arrange
        final int MANASLOTS_AFTER_1_ROUND = 1;
        int actualManaSlots = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.roundLoop();
        actualManaSlots = game.player[0].getManaSlots();

        // assert
        assertEquals(MANASLOTS_AFTER_1_ROUND, actualManaSlots);
    }

    @Test
    public void active_player_mana_slots_are_refilled() {
        // arrange
        final int MANA_REFILLED = 2;
        int actualManaRefilled = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.roundLoop();
        game.player[0].prepareActivePlayer();
        actualManaRefilled = game.player[0].getMana();

        // assert
        assertEquals(MANA_REFILLED, actualManaRefilled);
    }

    @Test
    public void active_player_can_play_as_many_cards_as_he_can_afford() {
        // arrange
        final int CARDS_LEFT = 0;
        int actualCardsLeft = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].setMana(6);
        game.player[0].hand.clear();
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].turnLoop();
        actualCardsLeft = game.player[0].hand.size();

        // assert
        assertEquals(CARDS_LEFT, actualCardsLeft);
    }

    @Test
    public void any_played_card_empties_mana_slots() { // if manacost value is not 0
        // arrange
        final int MANA_LEFT = 0;
        Game game = new Game();
        int actualManaLeft = 0;

        // act
        game.player[0] = new Player();
        game.player[0].hand.clear();
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].hand.add(new Card(1, 0));
        game.player[0].setMana(3);
        game.player[0].turnLoop();
        actualManaLeft = game.player[0].getMana();

        // assert
        assertEquals(MANA_LEFT, actualManaLeft);

    }

    @Test
    public void any_played_card_deals_damage_to_the_opponent() {  // if damage value not 0
        // arrange
        final int TOTAL_DAMAGE = 4;
        int actualDamage = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].setManaSlots(4);
        game.player[1].setTurnPassed(true);
        game.roundLoop();
        actualDamage = game.player[0].getDamage();

        // assert
        assertEquals(TOTAL_DAMAGE, actualDamage);

    }

    @Test
    public void damage_dealt_and_mana_consumed_are_equal() {
        // arrange
        final int MANA_COST = 2;
        final int DAMAGE_DEALT = 2;
        final boolean SAME_VALUES = true;
        Game game = new Game();
        int manaBeforePlayingCards = 0;
        int actualManaCost = 0;
        int actualDamage = 0;
        boolean actuallySame = false;

        // act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].setManaSlots(2);
        game.player[0].setMana(2);
        manaBeforePlayingCards = game.player[0].getMana();
        game.player[0].hand.add(new Card(2, 2));
        game.roundLoop();
        actualDamage = game.player[0].getDamage();
        actualManaCost = (game.player[0].getMana() - 1) + manaBeforePlayingCards;
        actuallySame = (actualManaCost == actualDamage);

        // assert
        assertEquals(MANA_COST, actualManaCost);
        assertEquals(DAMAGE_DEALT, actualDamage);
        assertEquals(SAME_VALUES, actuallySame);

    }

    @Test
    public void active_player_wins_if_opponent_health_reaches_zero() {
        // arrange
        final int WINNER = 0;
        int actualWinner = 0;
        Game game = new Game();

        // act
        game.createPlayers();
       // game.player[1].clearCards();
        game.player[1].setHealth(0);
        game.roundLoop();
        actualWinner = game.getWinner();

        // assert
        assertEquals(WINNER, actualWinner);
    }

    @Test
    public void opponent_player_becomes_active_if_active_player_passes_turn() {
        // arrange
        final Game game = new Game();
        int lastPlayer = game.getNumberOfPlayers() - 1;
        final String PASS_TURN_MESSAGE = "Player " + lastPlayer + " passes the turn to player " + (lastPlayer - 1);
        String actualMessage = "";

        // act
        game.createPlayers();
        game.player[lastPlayer].setTurnPassed(true);
        actualMessage = game.roundLoop();

        // assert
        assertEquals(PASS_TURN_MESSAGE, actualMessage);

    }

    @Test
    public void active_player_passes_turn_if_has_no_cards_left_in_hand() {
        // arrange
        final boolean TURN_PASSED = true;
        boolean actualTurnPassed = false;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].clearCards();
        game.roundLoop();
        actualTurnPassed = game.player[0].getTurnPassed();

        // assert
        assertEquals(TURN_PASSED, actualTurnPassed);
    }

    @Test
    public void active_player_passes_turn_if_has_no_mana_to_pay() {
        // arrange
        final boolean TURN_PASSED = true;
        boolean actualTurnPassed = false;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].setMana(0);
        game.player[0].setManaSlots(0);
        game.player[0].turnLoop();
        actualTurnPassed = game.player[0].getTurnPassed();

        // assert
        assertEquals(TURN_PASSED, actualTurnPassed);
    }

    @Test
    public void active_can_manually_pass_the_turn() {
        // arrange
        final boolean TURN_PASSED = true;
        boolean actualTurnPassed = false;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].setTurnPassed(true);
        game.roundLoop();
        actualTurnPassed = game.player[0].getTurnPassed();

        // assert
        assertEquals(TURN_PASSED, actualTurnPassed);

    }

    @Test
    public void actual_player_receives_1_damage_if_deck_is_empty_when_drawing_a_card() {
        // arrange
        final int HEALTH_LEFT = 29;
        int actualHealthLeft = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].deck.clear();
        game.player[0].drawCard();
        actualHealthLeft = game.player[0].getHealth();

        // assert
        assertEquals(HEALTH_LEFT, actualHealthLeft);
    }

    @Test
    public void card_is_discarded_if_hand_size_becomes_more_than_5() {
        // arrange
        final int CARDS_IN_HAND = 5;
        int actualCardsInHand = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].hand.add(new Card(2, 2));
        game.player[0].drawCard();
        actualCardsInHand = game.player[0].hand.size();

        // assert
        assertEquals(CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void healing_cards_add_5_health_when_played() {
        // arrange
        final int HEALTH_AFTER_HEALING = 30;
        int actualHealthAfterHealing = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].setHealth(25);
        game.player[0].hand.clear();
        game.player[0].hand.add(new Card(1, 0));
        game.roundLoop();
        actualHealthAfterHealing = game.player[0].getHealth();

        // assert
        assertEquals(HEALTH_AFTER_HEALING, actualHealthAfterHealing);
    }

    @Test
    public void builds_an_individual_random_deck_of_20_from_40_cards() {
        // arrange
        final int DECK = 17;
        int actualDeck = 0;
        final Game game = new Game();

        // act
        game.createPlayers();
        actualDeck = game.player[0].deck.size();

        // assert
        assertEquals(DECK, actualDeck);
    }

    @Test
    public void can_be_obtained_an_entire_gameplay_with_computer_players() {
        // arrange
        final String FINISH_MESSAGE = "PC vs PC game has succsessfully finished\n";
        String actualFinishMessage = "";
        Game game = new Game();

        // act
        actualFinishMessage = game.startPCvsPCGame();

        // assert
        assertEquals(FINISH_MESSAGE, actualFinishMessage);
    }

    @Test
    public void PC_player_sorts_hand_cards_by_lowest_manaCost(){
        
        //arrange
        final int LOWEST_COST = 0;
        int actualLowestCost = 10;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].hand.add(new Card (8,0));
        game.player[0].hand.add(new Card (3,0));
        game.player[0].hand.add(new Card (6,0));
        game.player[0].hand.add(new Card (0,0));
        Card selectedCard = game.player[0].getLowestCostCard();
        actualLowestCost = selectedCard.getManaCost();

        //assert
        assertEquals(LOWEST_COST, actualLowestCost);
    }

    @Test
    public void PC_player_sorts_hand_cards_by_highest_manaCost(){
        
        //arrange
        final int HIGHEST_COST = 8;
        int actualHighestCost = 0;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].hand.add(new Card (8,0));
        game.player[0].hand.add(new Card (3,0));
        game.player[0].hand.add(new Card (6,0));
        game.player[0].hand.add(new Card (0,0));
        Card selectedCard = game.player[0].getHighestCostCard();
        actualHighestCost = selectedCard.getManaCost();

        //assert
        assertEquals(HIGHEST_COST, actualHighestCost);
    }

    @Test
    public void PC_Player_selects_healing_card(){
        
        //arrange
        final int HEALING_CARD_COST = 1;
        int actualCostOfSelectedCard = 0;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].hand.add(new Card (8,0));
        game.player[0].hand.add(new Card (1,0));
        game.player[0].hand.add(new Card (6,0));
        game.player[0].hand.add(new Card (0,0));
        Card selectedCard = game.player[0].getHealingCard();
        actualCostOfSelectedCard = selectedCard.getManaCost();

        //assert
        assertEquals(HEALING_CARD_COST, actualCostOfSelectedCard);
    }

    @Test
    public void simpleAI_selects_healing_card_when_health_below_26(){
         //arrange
        final int HEALING_CARD_COST = 1;
        int actualCostOfSelectedCard = 0;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].setHealth(25);
        game.player[0].hand.add(new Card (1,0));
        game.player[0].hand.add(new Card (0,0));
        Card selectedCard = game.player[0].sortHand(true);
        actualCostOfSelectedCard = selectedCard.getManaCost();

        //assert
        assertEquals(HEALING_CARD_COST, actualCostOfSelectedCard);
    }

    @Test
    public void simpleAI_selects_lowest_cost_card_when_hand__is_equal_or_above_3_cards(){
         //arrange
        final int LOWEST_COST = 0;
        int actualLowestCost = 10;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].hand.add(new Card (8,0));
        game.player[0].hand.add(new Card (1,0));
        game.player[0].hand.add(new Card (0,0));
        Card selectedCard = game.player[0].sortHand(true);
        actualLowestCost = selectedCard.getManaCost();

        //assert
        assertEquals(LOWEST_COST, actualLowestCost);
    }

    @Test
    public void simpleAI_selects_lowest_cost_card_when_hand__is_below_3_cards(){
         //arrange
        final int HIGHEST_COST = 8;
        int actualHighestCost = 0;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearCards();
        game.player[0].hand.add(new Card (8,0));
        game.player[0].hand.add(new Card (0,0));
        Card selectedCard = game.player[0].sortHand(true);
        actualHighestCost = selectedCard.getManaCost();

        //assert
        assertEquals(HIGHEST_COST, actualHighestCost);
    }

}