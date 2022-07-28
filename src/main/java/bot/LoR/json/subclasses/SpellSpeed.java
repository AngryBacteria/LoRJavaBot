package bot.LoR.json.subclasses;

import com.google.gson.annotations.SerializedName;

public enum SpellSpeed {

    @SerializedName("Slow")SLOW("Slow", "Can be played outside of combat when no spells or skills are pending. Happens after your opponent has a chance to react."),
    @SerializedName("Burst")BURST("Burst", "Can be played whenever you may act. Happens instantly and allows you to continue to play other cards."),
    @SerializedName("Fast")FAST("Fast", "Can be played whenever you may act. Happens after your opponent has a chance to react."),
    @SerializedName("Focus")FOCUS("Focus", "Can be played outside combat or when no other spells or skills are pending. Happens instantly and allows you to continue to play other cards."),
    NONE("", "For card create");

    private final String nameRef;
    private final String description;

    SpellSpeed(String nameRef, String description) {
        this.nameRef = nameRef;
        this.description = description;
    }

    public String getNameRef() {
        return nameRef;
    }

    public String getDescription() {
        return description;
    }
}
