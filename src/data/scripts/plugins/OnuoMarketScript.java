package data.scripts.plugins;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import data.scripts.world.systems.CER_AddMarketplace;
import data.scripts.plugins.CERPerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class OnuoMarketScript implements EveryFrameScript {

    boolean done = false;

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        if (Global.getSector().getEntityById("OT_a") != null) {
            //sector gen call goes here
            boolean indevo = Global.getSettings().getModManager().isModEnabled("IndEvo");
            PlanetAPI onuo = (PlanetAPI) Global.getSector().getEntityById("OT_a");
            ArrayList<String> industries = new ArrayList<>(Arrays.asList("population", "megaport", "refining", "mining", "orbitalworks", "heavybatteries", "highcommand", "diableavionics_starfortress_lock"));
            if (indevo) {
                industries.add("IndEvo_dryDock");
            }
            MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                    "da_cer",
                    onuo,
                    null,
                    "Crow's Nest",
                    6,
                    new ArrayList<>(Arrays.asList("population_6", "ore_abundant", "rare_ore_abundant", "no_atmosphere", "regional_capital", "hot", "low_gravity", "barren_marginal")),
                    industries,
                    new ArrayList<>(Arrays.asList("open_market", "generic_military", "black_market", "storage")),
                    0.5F
            );
            da_CER_market.addIndustry(Industries.MINING, Collections.singletonList(Items.MANTLE_BORE));
            da_CER_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList("cer_nanoforge"));
            if (indevo) {
                da_CER_market.getIndustry("IndEvo_Drydock").setAICoreId(Commodities.GAMMA_CORE);
            }
            CERPerson.create();
            //MarketAPI market1 = Global.getSector().getEconomy().getMarket("OT_a");
            //if (market1 != null)
            done = true;
        }
    }
}
