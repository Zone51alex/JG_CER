package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.InstallableIndustryItemPlugin.InstallableItemDescriptionMode;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseInstallableItemEffect;
import com.fs.starfarer.api.impl.campaign.econ.impl.ItemEffectsRepo;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.launcher.ModManager;
import data.campaign.ids.cer_ids;
import data.scripts.campaign.CERIndustrySetup;
import data.scripts.campaign.CorvidSubsidiaryScript;
import data.scripts.campaign.plugins.CerCampaignPluginImpl;
import data.scripts.plugins.CERPerson;
import data.scripts.world.cerGen;
import data.scripts.world.systems.cer_OnuoMarketScript;
import exerelin.campaign.SectorManager;
import lunalib.lunaSettings.LunaSettings;

@SuppressWarnings("unused")  // IntelliJ warning suppression
public class da_cerModPlugin extends BaseModPlugin {

    public static boolean hasNexerelin() {
        return Global.getSettings().getModManager().isModEnabled("nexerelin");
    }
    public static final String MEMKEY_VERSION_CER = "$dacer_version";
    public static final String MEMKEY_INTIALIZED_CER = "$dacer_initialized";
    public static final String MEMKEY_MAIN_FLEETS_INITIALIZED_CER = "$dacer_Special";

    @Override
    public void onApplicationLoad() {
        ModManager manager = ModManager.getInstance();
        if (manager.isModEnabled("da_Junkers_Guild")) {
            throw new RuntimeException(
                    "Corvid Engineering & Recovery Subsidiary is the updated version of DA's Junkers Guild. Remove the old mod."
            );
        }
    }

    @Override
    public void onNewGame() {
        // Sync campaign scripts
        registerColonyItem();
        // Generate markets and fleets
    }

    @Override
    public void onNewGameAfterEconomyLoad() {

        addToOngoingGame();

    }

    @Override
    public void onGameLoad(boolean newGame) {
        // Sync campaign scripts
        registerColonyItem();
        syncCERScripts();
        //CERSaveMigration();
        //Save fix code
        /*
        SectorAPI sector = Global.getSector();
        MemoryAPI memory = sector.getMemoryWithoutUpdate();
        if (!memory.contains("$dacer_SaveMixer_done")) {
            cerGen.spawn1stfleet();
            cerGen.spawn2ndfleet();
            cerGen.spawn3rdfleet();
            cerGen.spawn4thfleet();

            PlanetAPI onuo = (PlanetAPI) sector.getEntityById("OT_a");
            MarketAPI market = onuo.getMarket();
            market.setName("Crow's Nest");
            new CERIndustrySetup();
            CERPerson.create();
            memory.set("$dacer_SaveMixer_done", true);
        }
        */
    }

    protected void syncCERScripts() {
        SectorAPI sector = Global.getSector();

        // Campaign plugin (safe to re-register)
        sector.registerPlugin(new CerCampaignPluginImpl());

        // Production script
        if (!Global.getSector().hasScript(CorvidSubsidiaryScript.class)) {
            Global.getSector().addScript(new CorvidSubsidiaryScript());
        }
    }

    protected void CERSaveMigration() {
        MemoryAPI memory = Global.getSector().getMemoryWithoutUpdate();
        float version = memory.getFloat(MEMKEY_VERSION_CER);

        // only migrate old saves
        if (version < 0.1f) {
            // Safe to add missing fleets, markets, industries, etc.
            //addToOngoingGame();

            // Update version so we don't rerun this migration
            memory.set(MEMKEY_VERSION_CER, 0.1f);
        }
    }

    protected void addToOngoingGame() {

        SectorAPI sector = Global.getSector();
        MemoryAPI memory = sector.getMemoryWithoutUpdate();

        // Add custom Onuo market if Luna settings allow
        if (!memory.contains("$dacer_marketspawn_done")) {
            if (hasNexerelin()
                    && Global.getSettings().getModManager().isModEnabled("lunalib")
                    && Boolean.TRUE.equals(LunaSettings.getBoolean("da_CER", "newcapitalspawn"))) {
                Global.getSector().addTransientScript(new cer_OnuoMarketScript());
                sector.getMemoryWithoutUpdate().set("$cer_onuo_initialized", true);
            }
            memory.set("$dacer_marketspawn_done", true);
        }

        // World generation (Corvus only)
        if (!memory.contains("$dacer_cerGen_done")) {
            if (!hasNexerelin() || SectorManager.getManager().isCorvusMode()) {
                new cerGen().generate(Global.getSector());
                memory.set("$dacer_cerGen_done", true);
            }
        }

        // Run cer_main logic (Subsidary Logic)
        if (!memory.contains("$dacer_alliance_done")) {
            cerGen.cer_main(sector);
            memory.set("$dacer_alliance_done", true);
        }

        // Add fleets if missing
        if (!memory.contains(MEMKEY_MAIN_FLEETS_INITIALIZED_CER)) {
            cerGen.spawn1stfleet();
            cerGen.spawn2ndfleet();
            cerGen.spawn3rdfleet();
            cerGen.spawn4thfleet();
            memory.set(MEMKEY_MAIN_FLEETS_INITIALIZED_CER, true);
        }

        // Set name to OT_a
        if (sector.getMemoryWithoutUpdate().getBoolean("$cer_onuo_renamed2")) return;

        if (!memory.getBoolean("$cer_onuo_renamed")) {
            PlanetAPI onuo = (PlanetAPI) sector.getEntityById("OT_a");
            if (onuo != null) {
                onuo.setName("Crow's Nest");
                MarketAPI market = onuo.getMarket();
                if (market != null) {
                    market.setName("Crow's Nest");
                    memory.set("$cer_onuo_renamed", true);
                }
            }
        }

        // Industries + NPCs
        if (!memory.contains("$dacer_industries_done")) {
            new CERIndustrySetup();
            CERPerson.create();
            memory.set("$dacer_industries_done", true);
        }

        float version = memory.getFloat(MEMKEY_VERSION_CER);
        if (version < 0.1f) {
            // run migration logic
            memory.set(MEMKEY_VERSION_CER, 0.1);
        }
    }

    // Load the colony item
    public static void registerColonyItem() {
        final float CER_NANOFORGE_ITEM_QUALITY_BONUS = 0.4f;

        if (!ItemEffectsRepo.ITEM_EFFECTS.containsKey(cer_ids.CER_NANOFORGE_ITEM)) {
            ItemEffectsRepo.ITEM_EFFECTS.put(cer_ids.CER_NANOFORGE_ITEM, new BaseInstallableItemEffect(cer_ids.CER_NANOFORGE_ITEM) {
                @Override
                public void apply(Industry industry) {
                    industry.getMarket().getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD)
                            .modifyFlat("nanoforge", CER_NANOFORGE_ITEM_QUALITY_BONUS, Misc.ucFirst(spec.getName().toLowerCase()));
                }

                @Override
                public void unapply(Industry industry) {
                    industry.getMarket().getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD)
                            .unmodifyFlat("nanoforge");
                    // remove the wrong supplyBonus line — it was never modified in apply()
                }

                @Override
                protected void addItemDescriptionImpl(Industry industry, TooltipMakerAPI text, SpecialItemData data,
                                                      InstallableItemDescriptionMode mode, String pre, float pad) {
                    text.addPara(pre + "Increases ship and weapon production quality by %s.",
                            pad, Misc.getHighlightColor(),
                            Math.round(CER_NANOFORGE_ITEM_QUALITY_BONUS * 100f) + "%");
                }
            });
        }
    }
}