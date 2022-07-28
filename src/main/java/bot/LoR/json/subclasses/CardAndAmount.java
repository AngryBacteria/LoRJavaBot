package bot.LoR.json.subclasses;

import bot.LoR.json.Card;

import java.util.ArrayList;
import java.util.List;

public class CardAndAmount {

    Card card;
    int amount;

    public CardAndAmount(Card card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public Card getCard() {
        return card;
    }

    public int getAmount() {
        return amount;
    }

    public String getRegionBars(){
        return this.card.getRegions().get(0).getEmojiBarDiscord().trim();
    }

    @Override
    public String toString() {
        return "`" + getAmount() + "` x " + getRegionBars() + getCard().getName().trim();
    }
}
