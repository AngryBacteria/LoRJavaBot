package bot.LoR.json.subclasses;

import com.google.gson.annotations.SerializedName;

public enum Set {
    @SerializedName("Set1") SET1(1, "Foundations", "http://dd.b.pvp.net/3_8_0/core/en_us/img/sets/set3_crispmip.png"),
    @SerializedName("Set2") SET2(2, "Rising Tides", "http://dd.b.pvp.net/3_8_0/core/en_us/img/sets/set2_crispmip.png"),
    @SerializedName("Set3") SET3(3, "Call of the Mountain", "http://dd.b.pvp.net/3_8_0/core/en_us/img/sets/set3_crispmip.png"),
    @SerializedName("Set4") SET4(4, "Empires of the Ascended", "http://dd.b.pvp.net/3_8_0/core/en_us/img/sets/set4_crispmip.png"),
    @SerializedName("Set5") SET5(5, "Beyond the Bandlewood", "http://dd.b.pvp.net/3_8_0/core/en_us/img/sets/set5_crispmip.png"),
    @SerializedName("Set6") SET6(6, "", ""),
    CUSTOM(7, "Custom", "");

    private final int number;
    private final String nameFull;
    private final String iconURL;

    Set(int number, String nameFull, String iconURL) {
        this.number = number;
        this.nameFull = nameFull;
        this.iconURL = iconURL;
    }

    public int getNumber() {
        return number;
    }

    public String getNameFull() {
        return nameFull;
    }

    public String getIconURL() {
        return iconURL;
    }
}
