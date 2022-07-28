package bot.LoR.json;

import bot.LoR.json.subclasses.*;
import bot.LoR.json.subclasses.Set;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class CardService {

    private ArrayList<Card> cardArray;
    private Map<String, Card> cardMap;

    public CardService() {
        this.cardArray = CardIndexing.getCardArrayFromArrays();
        this.cardMap = cardArray.stream().collect(Collectors.toMap(Card::getCardCode, item -> item));
    }

    //Queries
    public List<Card> getCardsByName(String name){
        return this.cardArray.stream()
                .filter(card -> card.getName().equalsIgnoreCase(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByNameContains(String name){
        return this.cardArray.stream()
                .filter(card -> card.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByDescriptionContains(String description){
        return this.cardArray.stream()
                .filter(card -> card.getDescriptionRaw().toLowerCase(Locale.ROOT).contains(description.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<Card> getCardByCode(String code){
        return Optional.of(cardMap.get(code));
    }

    public List<Card> getCardByNameRegex(String regex){
        return this.cardArray.stream()
                .filter(card -> card.getName().matches(regex))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByAttack(int attack){
        return this.cardArray.stream()
                .filter(card -> card.getAttack() == attack)
                .sorted(Comparator.comparingInt(Card::getAttack))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByHealth(int health){
        return this.cardArray.stream()
                .filter(card -> card.getHealth() == health)
                .sorted(Comparator.comparingInt(Card::getAttack))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByCost(int cost){
        return this.cardArray.stream()
                .filter(card -> card.getCost() == cost)
                .sorted(Comparator.comparingInt(Card::getAttack))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsByType(Type type){
        return this.cardArray.stream()
                .filter(card -> card.getType().equals(type))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsBySuperType(SuperType superType){
        return this.cardArray.stream()
                .filter(card -> card.getSupertype() == superType)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getCardsBySet(Set set){
        return this.cardArray.stream()
                .filter(card -> card.getSet().equals(set))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Card> getAssociatedCards(Card card){

        List<Card> output = new ArrayList<>();
        for (String cardCode : card.getAssociatedCardRefs()){
            output.add(this.cardMap.get(cardCode));
        }
        return output;
    }

    public List<Card> getAssociatedCards(String code){

        Optional<Card> card = getCardByCode(code);
        List<Card> output = new ArrayList<>();
        if (card.isPresent()){
            for (String cardCode : card.get().getAssociatedCardRefs()){
                output.add(this.cardMap.get(cardCode));
            }
        }
        return output;
    }

    //Helper Methods
    public Optional<Region> getRegionByName(String name){
        name = name.trim();
        for (Region currentRegion : Region.values()){
            if(currentRegion.getRegionFull().equalsIgnoreCase(name) ||
                    currentRegion.getAbbreviation().equalsIgnoreCase(name) ||
                    currentRegion.name().equalsIgnoreCase(name))
                return Optional.of(currentRegion);
        }
        return Optional.empty();
    }

    public Optional<Keyword> getKeyWordByName(String name){
        name = name.trim();
        for (Keyword currentKeyword : Keyword.values()){
            if (currentKeyword.getNameRef().equalsIgnoreCase(name) || currentKeyword.name().equalsIgnoreCase(name))
                return Optional.of(currentKeyword);
        }
        return Optional.empty();
    }

    public ArrayList<Card> getCardArray() {
        return cardArray;
    }

    public Map<String, Card> getCardMap() {
        return cardMap;
    }

    //Card Methods
    public void addCard(Card card){
        this.cardArray.add(card);
        this.cardMap.put(card.getCardCode(), card);
    }
}
