package data.scripts.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class cer_OuterTerminus implements SectorGeneratorPlugin {

    public void generate(SectorAPI sector) {
/*
        // Onuo: Barren homeworld

        if (Global.getSettings().getModManager().isModEnabled("IndEvo")) {

            MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                    "da_cer",
                    "OT_a",
                    null,
                    "Crow's Nest",
                    6,
                    new ArrayList(Arrays.asList((Object[])new String[] { "population_6","ore_abundant","rare_ore_abundant","no_atmosphere","regional_capital","hot","low_gravity","barren_marginal" } )),
                    new ArrayList(Arrays.asList((Object[])new String[] { "population","megaport","refining","mining","orbitalworks","heavybatteries","highcommand","diableavionics_starfortress_lock","IndEvo_dryDock"  } )),
                    new ArrayList(Arrays.asList((Object[])new String[] { "open_market", "generic_military", "black_market", "storage" } )),
                    0.5F,
                    false
                    );
            /*
            MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                    "da_cer",
                    "OT_a",
                    null,
                    "Crow's Nest",
                    6,
                    Arrays.asList(
                            Conditions.POPULATION_6,
                            Conditions.ORE_ABUNDANT,
                            Conditions.RARE_ORE_ABUNDANT,
                            Conditions.NO_ATMOSPHERE,
                            Conditions.REGIONAL_CAPITAL,
                            Conditions.HOT,
                            Conditions.LOW_GRAVITY,
                            Conditions.ARID
                    ),
                    Arrays.asList(
                            Submarkets.GENERIC_MILITARY,
                            Submarkets.SUBMARKET_OPEN,
                            Submarkets.SUBMARKET_STORAGE,
                            Submarkets.SUBMARKET_BLACK
                    ),
                    new ArrayList(Arrays.asList((Object[])new String[] {
                            "battlestation_high",
                            "patrolhq",
                            "refining",
                            "spaceport",
                            "heavyindustry",
                            "fuelprod",
                            "population"
                    } ))
                    0.18f,
                    false)

            da_CER_market.addIndustry(Industries.MINING, Collections.singletonList(Items.MANTLE_BORE)); //couldn't find another way to add w/ forge!
            da_CER_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList("cer_nanoforge")); //couldn't find another way to add w/ forge!
            da_CER_market.getIndustry("IndEvo_Drydock").setAICoreId(Commodities.GAMMA_CORE);
        } else {
            MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                    "da_cer",
                    "OT_a",
                    null,
                    "Crow's Nest",
                    6,
                    new ArrayList(Arrays.asList((Object[])new String[] { "population_6","ore_abundant","rare_ore_abundant","no_atmosphere","regional_capital","hot","low_gravity","barren_marginal" } )),
                    new ArrayList(Arrays.asList((Object[])new String[] { "population","megaport","refining","mining","orbitalworks","heavybatteries","highcommand","diableavionics_starfortress_lock" } )),
                    new ArrayList(Arrays.asList((Object[])new String[] { "open_market", "generic_military", "black_market", "storage" } )),
                    0.5F,
                    false);


            /*
            MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                    "da_cer",
                    "OT_a",
                    null,
                    "Crow's Nest",
                    6,
                    Arrays.asList(
                            Conditions.POPULATION_6,
                            Conditions.ORE_ABUNDANT,
                            Conditions.RARE_ORE_ABUNDANT,
                            Conditions.NO_ATMOSPHERE,
                            Conditions.REGIONAL_CAPITAL,
                            Conditions.HOT,
                            Conditions.LOW_GRAVITY,
                            Conditions.ARID
                    ),
                    Arrays.asList(
                            Submarkets.GENERIC_MILITARY,
                            Submarkets.SUBMARKET_OPEN,
                            Submarkets.SUBMARKET_STORAGE,
                            Submarkets.SUBMARKET_BLACK
                    ),
                    Arrays.asList(
                            Industries.POPULATION,
                            Industries.MEGAPORT,
                            Industries.MINING,
                            Industries.REFINING,
                            Industries.ORBITALWORKS,
                            Industries."diableavionics_starfortress_lock",
                            Industries.HEAVYBATTERIES,
                            Industries.HIGHCOMMAND
                            ),
                    true,
                    false,
                    true);


            da_CER_market.addIndustry(Industries.MINING, Collections.singletonList(Items.MANTLE_BORE)); //couldn't find another way to add w/ forge!
            da_CER_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList("cer_nanoforge")); //couldn't find another way to add w/ forge!
        }









        /*
        StarSystemAPI system = sector.getStarSystem(Diableavionics_stringsManager.txt("star_C"));
        system.getOptionalUniqueId();

        PlanetAPI star = system.getStar();

        PlanetAPI OT1 = system.getPlanets().set("OT_a",Diableavionics_stringsManager.txt("cer_capital");
        */

    }

}