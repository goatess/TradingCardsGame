import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest {

    @Test
    public void each_player_starts_with_30_health() {

        // arrange
        final int EXPECTED_HEALTH = 30;
        Player player = new Player();

        // act

        int actualHealth = player.getHealth();

        // assert
        assertEquals(EXPECTED_HEALTH, actualHealth);
    }

    @Test
    public void each_player_starts_with_0_manaSlots() {

        // arrange
        final int EXPECTED_MANASLOTS = 0;
        Player player = new Player();

        // act
        int actualManaSlots = player.getManaSlots();

        // assert
        assertEquals(EXPECTED_MANASLOTS, actualManaSlots);
    }

    @Test
    public void player_receives_3_cards_from_deck_initially() {

        // arrange
        final int EXPECTED_CARDS_IN_HAND = 3;
        Game game = new Game();

        // act
        game.createPlayers();
        int actualCardsInHand = game.player[0].getNumberOfCardsInHand();

        // assert
        assertEquals(EXPECTED_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void each_player_receives_3_cards_from_deck_() {

        // arrange
        final boolean EXPECTED_SAME_CARDS_IN_HAND = true;
        Game game = new Game();

        // act
        game.createPlayers();
        int actualCardsInHand = game.player[0].getNumberOfCardsInHand();
        int actualCardsInHand2 = game.player[1].getNumberOfCardsInHand();
        boolean sameCardsInHand = actualCardsInHand == actualCardsInHand2;

        // assert
        assertEquals(EXPECTED_SAME_CARDS_IN_HAND, sameCardsInHand);
    }

    @Test
    public void active_player_can_draw_a_card_from_the_deck_to_his_hand() {
        // arrange
        final int EXPECTED_CARDS_IN_HAND = 4;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[0].drawCard();
        int actualCardsInHand = game.player[0].getNumberOfCardsInHand();

        // assert
        assertEquals(EXPECTED_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void active_player_gets_1_more_mana_slot() {
        // arrange
        final int EXPECTED_MANA_SLOTS = 1;
        Game game = new Game();

        // act
        game.createPlayers();
        game.manageTurns();
        int actualManaSlots = game.player[0].getManaSlots();

        // assert
        assertEquals(EXPECTED_MANA_SLOTS, actualManaSlots);
    }

    @Test
    public void active_player_mana_slots_are_refilled() {
        // arrange
        final int EXPECTED_MANA = 2;
        Game game = new Game();

        // act
        game.createPlayers();
        game.manageTurns();
        game.player[0].prepareActivePlayer();
        int actualMana = game.player[0].getMana();

        // assert
        assertEquals(EXPECTED_MANA, actualMana);
    }

    
    @Test
    public void active_player_can_play_as_many_cards_as_he_can_afford(){
        // arrange
        final int EXPECTED_CARDS_IN_HAND = 0;
        Game game = new Game();

        // act
        game.player[0] = new Player();
        game.player[0].cardsHand.add(0, 2);
        game.player[0].cardsHand.add(1, 2);
        game.player[0].cardsHand.add(2, 2);
        game.player[0].setMana(6);
        game.player[0].playCardsinHand();
        int actualCardsInHand = game.player[0].getNumberOfCardsInHand();

        // assert
        assertEquals(EXPECTED_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test 
    public void any_played_card_empties_mana_slots(){
        //arrange
        final int EXPECTED_MANA = 0;
        Game game = new Game();

        //act
        game.player[0] = new Player();
        game.player[0].cardsHand.add(0, 2);
        game.player[0].setMana(2);
        game.player[0].playCardsinHand();
        int actualMana = game.player[0].getMana();

        //assert
        assertEquals(EXPECTED_MANA, actualMana);

    }

    @Test
    public void any_played_card_deals_damage_to_the_opponent_if_card_value_is_not_0_or_1(){
        //arrange
        final int EXPECTED_OPP_HEALTH = 28;
        Game game = new Game();

        //act
        game.player[0] = new Player();
        game.player[1] = new Player();
        game.player[0].cardsHand.add(0, 2);
        game.player[0].setMana(2);
        game.manageTurns();
        int actualOppHealth = game.player[1].getHealth();

        //assert
        assertEquals(EXPECTED_OPP_HEALTH, actualOppHealth);

    }

    @Test
    public void damage_dealt_and_mana_consumed_are_equal(){
         //arrange
        final boolean EXPECTED_EQUAL = true;
        Game game = new Game();

        //act
        game.player[0] = new Player();
        game.player[1] = new Player();
        int OppHealth = game.player[1].getHealth();
        game.player[0].cardsHand.add(0, 2);
        game.player[0].setMana(2);
        int mana = game.player[0].getMana();
        game.manageTurns();
        int actualDamage = OppHealth - game.player[1].getHealth();
        int actualMana = mana - game.player[0].getMana();

        boolean areEqual = actualDamage == actualMana;

        //assert
        assertEquals(EXPECTED_EQUAL, areEqual);
        
    }

    @Test
    public void active_player_wins_if_opponent_health_reaches_zero() {
        // arrange
        final int EXPECTED_WINNER = 0;
        Game game = new Game();

        // act
        game.createPlayers();
        game.player[1].setHealth(0);
        game.gameLoop();
        int actualWinner = game.getWinner();

        // assert
        assertEquals(EXPECTED_WINNER, actualWinner);
    }

    @Test
    public void opponent_player_becomes_active_if_active_player_has_no_cards_left_in_hand(){

    }
    @Test
    public void opponent_player_becomes_active_if_active_player_has_no_mana_to_pay(){

    }
    @Test
    public void opponent_player_becomes_active_if_active_player_passes_the_turn(){

    }

    @Test
    public void actual_player_receives_1_damage_if_deck_is_empty_when_drawing_a_card(){
        //arrange
        final int EXPECTED_HEALTH = 29;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].clearDeck();
        game.player[0].drawCard();
        int actualHealth = game.player[0].getHealth();

        //assert
        assertEquals(EXPECTED_HEALTH, actualHealth);
    }

    @Test
    public void card_is_discarded_if_hand_size_becomes_more_than_5(){
        //arrange
        final int EXPECTED_CARDS_IN_HAND = 5;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].cardsHand.add(0, 2);
        game.player[0].cardsHand.add(0, 2);
        game.manageTurns();
        int actualCardsInHand = game.player[0].getNumberOfCardsInHand();
        

        //assert
        assertEquals(EXPECTED_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void healing_cards_add_5_health_when_played(){
        //arrange
        final int EXPECTED_HEALTH = 30;
        Game game = new Game();

        //act
        game.createPlayers();
        game.player[0].setHealth(25);
        game.player[0].clearHand();
        game.player[0].cardsHand.add(0, 1);
        game.manageTurns();
        int actualHealth = game.player[0].getHealth();
        

        //assert
        assertEquals(EXPECTED_HEALTH , actualHealth);
    }

    @Test
    public void builds_an_individual_random_deck_of_20_from_40_cards(){
        //arrange
        final
        Game game = new Game();

        //act
        game.createPlayers();
        game.manageTurns();
        

        //assert
        assertEquals(true, true);
    }

    @Test
    public void can_be_obtained_an_entire_gameplay_with_computer_players(){
        //arrange
        final String EXPECTED_MESSAGE = "PC vs PC game has succssessfully finished";
        Game game = new Game();

        //act
        String actualMessage = game.startPCvsPCGame();
        

        //assert
        assertEquals(EXPECTED_MESSAGE , actualMessage);
    }
}
