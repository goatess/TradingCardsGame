package TradingCardGame;

public class Message {
   
    String message = "";
    
    void drawMessage(int value){
        message += "A card with a value of " + value + " drawn. ";
    }
    void overloadMessage(int value){
        message += "A card with a value of " + value + " drawn. OVERLOAD! Card discarded. ";
    }
    void deck(){
        
    }
}
