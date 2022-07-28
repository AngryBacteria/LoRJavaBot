package bot.LoR.json.subclasses;

import com.google.gson.annotations.SerializedName;

public enum Rarity {

    @SerializedName("None")NONE(0, 0),
    @SerializedName("Champion")CHAMPION(3000, 300),
    EPIC(1200, 120),
    RARE(300, 30),
    COMMON(100, 10);

    private final int priceEssence;
    private final int priceRP;

    Rarity(int priceEssence, int priceRP) {
        this.priceEssence = priceEssence;
        this.priceRP = priceRP;
    }

    public int getPriceEssence() {
        return priceEssence;
    }

    public int getPriceRP() {
        return priceRP;
    }
}
