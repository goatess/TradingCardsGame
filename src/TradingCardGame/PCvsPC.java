package TradingCardGame;

public class PCvsPC {
    public static void main(String[] args) throws Exception {
    }
    
    public PCvsPC() {
        Game game = new Game();
        game.startPCvsPCGame();
    }

    public void playerVSpc(){
        Game game = new Game();
        game.startPlayervsPCGame();
    }
}