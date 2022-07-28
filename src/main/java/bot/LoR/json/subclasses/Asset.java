package bot.LoR.json.subclasses;

import java.util.Objects;

public class Asset {

    String gameAbsolutePath;
    String fullAbsolutePath;

    public Asset(String gameAbsolutePath, String fullAbsolutePath) {
        this.gameAbsolutePath = gameAbsolutePath;
        this.fullAbsolutePath = fullAbsolutePath;
    }

    public String getGameAbsolutePath() {
        return gameAbsolutePath;
    }

    public String getFullAbsolutePath() {
        return fullAbsolutePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(gameAbsolutePath, asset.gameAbsolutePath) && Objects.equals(fullAbsolutePath, asset.fullAbsolutePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameAbsolutePath, fullAbsolutePath);
    }
}
