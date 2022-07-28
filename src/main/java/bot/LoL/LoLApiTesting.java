package bot.LoL;

import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

public class LoLApiTesting {

    public static void main(String[] args) {

        APICredentials creds = new APICredentials("RGAPI-1d6a7f00-dc2a-4528-9d88-20dcfd00c954");
        R4J r4J = new R4J(creds);

        Summoner summoner = r4J.getLoLAPI().getSummonerAPI().getSummonerByName(LeagueShard.EUW1, "AngryBacteria");
        System.out.println(summoner.getChampionMasteries().get(0).getChampionId());
        System.out.println(summoner.getSummonerLevel());
        System.out.println(summoner.getPUUID());


        System.out.println(r4J.getDDragonAPI().getChampion(555).getImage());
    }

}
