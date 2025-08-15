package data.scripts.plugins;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import com.fs.starfarer.launcher.ModManager;
import data.scripts.world.systems.CER_AddMarketplace;

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
        ModManager manager = ModManager.getInstance();
            //sector gen call goes here
            if (manager.isModEnabled("IndEvo")) {
                //
                PlanetAPI onuo = (PlanetAPI) Global.getSector().getEntityById("OT_a");
                if (Global.getSector().getEntityById("OT_a") != null) {
                //ArrayList<String> industries = );
                MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                    "da_cer",
                    onuo,
                    null,
                    "Crow's Nest",
                    6,
                    new ArrayList<>(Arrays.asList("population_6", "ore_abundant", "rare_ore_abundant", "no_atmosphere", "regional_capital", "hot", "low_gravity", "barren_marginal")),
                        new ArrayList<>(Arrays.asList("population", "megaport", "refining", "mining", "orbitalworks", "heavybatteries", "highcommand", "diableavionics_starfortress_lock","IndEvo_dryDock")),
                    new ArrayList<>(Arrays.asList("open_market", "generic_military", "black_market", "storage")),
                    0.5F
            );
            da_CER_market.addIndustry(Industries.MINING, Collections.singletonList(Items.MANTLE_BORE));
            da_CER_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList("cer_nanoforge"));
            //da_CER_market.getIndustry("IndEvo_Drydock").setAICoreId(Commodities.GAMMA_CORE); this one requires to detect the industry ID of indevo_drydock
            }
            //MarketAPI market1 = Global.getSector().getEconomy().getMarket("OT_a");
            //if (market1 != null)
            done = true;
        }
            else
            {
                //
                if (Global.getSector().getEntityById("OT_a") != null) {
                    PlanetAPI onuo = (PlanetAPI) Global.getSector().getEntityById("OT_a");
                    //ArrayList<String> industries = );
                    MarketAPI da_CER_market = CER_AddMarketplace.addMarketplace(
                            "da_cer",
                            onuo,
                            null,
                            "Crow's Nest",
                            6,
                            new ArrayList<>(Arrays.asList("population_6", "ore_abundant", "rare_ore_abundant", "no_atmosphere", "regional_capital", "hot", "low_gravity", "barren_marginal")),
                            new ArrayList<>(Arrays.asList("population", "megaport", "refining", "mining", "orbitalworks", "heavybatteries", "highcommand", "diableavionics_starfortress_lock")),
                            new ArrayList<>(Arrays.asList("open_market", "generic_military", "black_market", "storage")),
                            0.5F
                    );
                    da_CER_market.addIndustry(Industries.MINING, Collections.singletonList(Items.MANTLE_BORE));
                    da_CER_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList("cer_nanoforge"));
                    //da_CER_market.getIndustry("IndEvo_Drydock").setAICoreId(Commodities.GAMMA_CORE);
                }
                //MarketAPI market1 = Global.getSector().getEconomy().getMarket("OT_a");
                //if (market1 != null)
                done = true;
            }

        }
    }

