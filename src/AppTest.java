import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest {

    

    @Test
    public void each_player_starts_with_30_health(){

        //arrange
        final int EXPECTED_HEALTH = 30;
        Player player = new Player();
        
        //act
        int actualHealth = player.getHealth();

        //assert
        assertEquals(EXPECTED_HEALTH, actualHealth);
    }

    @Test
    public void each_player_starts_with_30_healthB(){

        //arrange
        final int EXPECTED_HEALTH = 30;
        Game game = new Game();
        
        //act
        game.addPlayers();
        int actualHealth = game.player[0].getHealth();

        //assert
        assertEquals(EXPECTED_HEALTH, actualHealth);
    }

    @Test
    public void each_player_starts_with_0_manaSlots(){

        //arrange
        final int EXPECTED_MANASLOTS = 0;
        Player player = new Player();

        //act
        int actualManaSlots = player.getManaSlots();

        //assert
        assertEquals(EXPECTED_MANASLOTS, actualManaSlots);
    }


    @Test
    public void draw_a_card(){
        //arrange
        final int EXPECTED_CARDS_IN_HAND= 4;
        Game game = new Game();
        
        //act
        game.addPlayers();
        game.player[0].drawACard();
        int actualCardsInHand = game.player[0].getCardsInHand_size();
        
        //assert
        assertEquals(EXPECTED_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void  player_receives_3_cards_from_deck_initially(){
        
        //arrange
        final int EXPECTED_CARDS_IN_HAND = 3;        
        Game game = new Game();

        //act
        game.addPlayers();
        int actualCardsInHand = game.player[0].getCardsInHand_size();

        //assert
        assertEquals(EXPECTED_CARDS_IN_HAND, actualCardsInHand);
    }

    @Test
    public void each_player_receives_3_cards_from_deck_(){
        
        //arrange
        final boolean EXPECTED_SAME_CARDS_IN_HAND = true;        
        Game game = new Game();

        //act
        game.addPlayers();
        int actualCardsInHand = game.player[0].getCardsInHand_size();
        int actualCardsInHand2 = game.player[1].getCardsInHand_size();
        boolean sameCardsInHand = actualCardsInHand == actualCardsInHand2;

        //assert
        assertEquals(EXPECTED_SAME_CARDS_IN_HAND, sameCardsInHand);
    }

    @Test 
    public void active_player_gets_1_more_mana_slot(){
        //arrange
        final int EXPECTED_MANA_SLOTS = 1;
        Game game = new Game();

        //act
        game.addPlayers();
        game.manageTurn();
        int actualManaSlots = game.player[0].getManaSlots();
        
        //assert
        assertEquals(EXPECTED_MANA_SLOTS, actualManaSlots);
    }

    @Test 
    public void active_player_mana_slots_are_refilled(){
        //arrange
        final int EXPECTED_MANA = 2;
        Game game = new Game();

        //act
        game.addPlayers();
        game.manageTurn();
        game.player[0].setupActivePlayer();
        int actualMana = game.player[0].getMana();

        //assert
        assertEquals(EXPECTED_MANA, actualMana);
    }


}
