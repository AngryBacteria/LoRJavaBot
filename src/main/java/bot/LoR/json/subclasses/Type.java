package bot.LoR.json.subclasses;
import com.google.gson.annotations.SerializedName;

public enum Type {

    @SerializedName("Unit") UNIT,
    @SerializedName("Landmark") LANDMARK,
    @SerializedName("Spell") SPELL,
    @SerializedName("Ability") ABILITY,
    @SerializedName("Trap") TRAP,
    @SerializedName("Equipment") EQUIPMENT

}
