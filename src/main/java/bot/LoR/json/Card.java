package bot.LoR.json;

import bot.LoR.json.subclasses.*;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Card {

    //Strings
    private String descriptionRaw;
    private String levelupDescriptionRaw;
    private String flavorText;
    private String artistName;
    private String name;
    private String cardCode;

    //Lists
    private ArrayList<Asset> assets;
    private ArrayList<Keyword> keywords;
    private ArrayList<String> associatedCardRefs;

    //Numbers
    private int attack;
    private int cost;
    private int health;

    //Booleans and Enums
    private boolean collectible;
    private Rarity rarity;
    private Set set;
    private ArrayList<Region> regions;
    private Type type;
    private SuperType supertype;

    //Spell specific
    private SpellSpeed spellSpeed;

    //Static count
    private static int customCardCount;
    private int customCardID;

    public Card(String descriptionRaw, String levelupDescriptionRaw, String flavorText, String artistName, String name, String cardCode, ArrayList<String> associatedCardRefs, ArrayList<Asset> assets, ArrayList<Keyword> keywords, int attack, int cost, int health, boolean collectible, Rarity rarity, Set set, ArrayList<Region> regions, Type type, SuperType supertype, SpellSpeed spellSpeed) {
        this.descriptionRaw = descriptionRaw;
        this.levelupDescriptionRaw = levelupDescriptionRaw;
        this.flavorText = flavorText;
        this.artistName = artistName;
        this.name = name;
        this.cardCode = cardCode;
        this.associatedCardRefs = associatedCardRefs;
        this.assets = assets;
        this.keywords = keywords;
        this.attack = attack;
        this.cost = cost;
        this.health = health;
        this.collectible = collectible;
        this.rarity = rarity;
        this.set = set;
        this.regions = regions;
        this.type = type;
        this.supertype = supertype;
        this.spellSpeed = spellSpeed;

        if (set.equals(Set.CUSTOM)){
            customCardID = ++customCardCount;
            this.cardCode = String.format("0%d%s0%d", set.getNumber(), regions.get(0).getAbbreviation(), customCardID);
        }
    }

    public Card(String decriptionRaw, String name, String pictureURL, int attack, int cost, int health, Rarity rarity, Region region, Type type, SpellSpeed spellSpeed) {
        this.descriptionRaw = decriptionRaw;
        this.levelupDescriptionRaw = "";
        this.flavorText = "";
        this.artistName = "";
        this.name = name;
        this.associatedCardRefs = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.attack = attack;
        this.cost = cost;
        this.health = health;
        this.collectible = true;
        this.rarity = rarity;
        this.set = Set.CUSTOM;
        this.regions = new ArrayList<>();
        this.type = type;
        this.spellSpeed = spellSpeed;
        this.supertype = SuperType.NONE;

        this.regions.add(region);
        this.assets.add(new Asset(pictureURL, pictureURL));
        customCardID = ++customCardCount;
        this.cardCode = String.format("0%d%s0%d", set.getNumber(), regions.get(0).getAbbreviation(), customCardID);
    }

    public String getDescriptionRaw() {
        return descriptionRaw;
    }

    public String getLevelupDescriptionRaw() {
        return levelupDescriptionRaw;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getName() {
        return name;
    }

    public String getCardCode() {
        return cardCode;
    }

    public Set getSet() {
        return set;
    }

    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public int getAttack() {
        return attack;
    }

    public int getCost() {
        return cost;
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public Asset getAssets() {
        return assets.get(0);
    }

    public boolean isCollectible() {
        return collectible;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Type getType() {
        return type;
    }

    public SuperType getSupertype() {
        return supertype;
    }

    public ArrayList<String> getAssociatedCardRefs() {
        return associatedCardRefs;
    }

    public SpellSpeed getSpellSpeed() {
        return spellSpeed;
    }

    public boolean isChampion(){
        if (this.getType().equals(Type.UNIT) && this.supertype.equals(SuperType.CHAMPION))
            return true;
        return false;
    }

    public boolean hasKeywordSearch(String keywordSearch){

        for (Keyword keyword : getKeywords()){
            if (keyword.name().replace("_", " ").equalsIgnoreCase(keywordSearch) ||
                    keyword.getNameRef().equalsIgnoreCase(keywordSearch)){
                return true;
            }
        }
        return false;
    }

    public boolean hasRegionSearch(String regionSearch){

        for (Region region : getRegions()){
            if (region.name().equalsIgnoreCase(regionSearch) ||
                    region.getRegionFull().equalsIgnoreCase(regionSearch) ||
                    region.getAbbreviation().equalsIgnoreCase(regionSearch)){
                return true;
            }
        }
        return false;
    }

    public Boolean containsDescription(String descriptionSearch){
        return (getName().equalsIgnoreCase(descriptionSearch) ||
                        getCardCode().equalsIgnoreCase(descriptionSearch) ||
                        getDescriptionRaw().toLowerCase(Locale.ROOT).contains(descriptionSearch) ||
                        getLevelupDescriptionRaw().toLowerCase(Locale.ROOT).contains(descriptionSearch) ||
                        hasKeywordSearch(descriptionSearch) ||
                        hasRegionSearch(descriptionSearch));
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", cardCode='" + cardCode + '\'' +
                ", set='" + set + '\'' +
                ", rarity='" + rarity + '\'' +
                ", attack=" + attack +
                ", cost=" + cost +
                ", health=" + health +
                ", Type=" + type +
                ", Supertype=" + supertype +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return attack == card.attack && cost == card.cost && health == card.health && collectible == card.collectible && Objects.equals(descriptionRaw, card.descriptionRaw) && Objects.equals(levelupDescriptionRaw, card.levelupDescriptionRaw) && Objects.equals(flavorText, card.flavorText) && Objects.equals(artistName, card.artistName) && Objects.equals(name, card.name) && Objects.equals(cardCode, card.cardCode) && Objects.equals(assets, card.assets) && Objects.equals(keywords, card.keywords) && Objects.equals(associatedCardRefs, card.associatedCardRefs) && rarity == card.rarity && set == card.set && Objects.equals(regions, card.regions) && type == card.type && supertype == card.supertype && spellSpeed == card.spellSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionRaw, levelupDescriptionRaw, flavorText, artistName, name, cardCode, assets, keywords, associatedCardRefs, attack, cost, health, collectible, rarity, set, regions, type, supertype, spellSpeed);
    }
}
