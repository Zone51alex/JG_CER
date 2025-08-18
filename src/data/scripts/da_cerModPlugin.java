package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.InstallableIndustryItemPlugin.InstallableItemDescriptionMode;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.ShipAIConfig;
import com.fs.starfarer.api.combat.ShipAIPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseInstallableItemEffect;
import com.fs.starfarer.api.impl.campaign.econ.impl.ItemEffectsRepo;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.launcher.ModManager;
import data.campaign.CyanCoreOfficerGen;
import data.campaign.ids.cer_ids;
import data.scripts.campaign.plugins.CerCampaignPluginImpl;
import data.scripts.plugins.CERPerson;
import data.scripts.world.MarketHelpers;
import data.scripts.world.cerGen;
import exerelin.campaign.SectorManager;

public class da_cerModPlugin extends BaseModPlugin {
    //variables
    public static final String MEMKEY_VERSION_CER = "$dacer_version";
    public static final String MEMKEY_INTIALIZED_CER = "$dacer_initialized";
    public static final String MEMKEY_MAIN_FLEETS_INITIALIZED_CER = "$dacer_Special";
    public static final boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");


    // sync scripts
    public void syncCERScripts() {
        addScriptsIfNeeded();
    }
    protected void addScriptsIfNeeded() {
        SectorAPI sector = Global.getSector();
        sector.registerPlugin(new CerCampaignPluginImpl());
    }

    public void onApplicationLoad() {
        ModManager manager = ModManager.getInstance();
        if (manager.isModEnabled("da_Junkers_Guild"))
            throw new RuntimeException("Corvid Engineering & Recovery Subsidiary is the updated version of DA's Junkers Guild why do you have it both on ?");
    }

    public void onGameLoad(boolean newGame) {
        //Load Characters
        MarketAPI market1 = Global.getSector().getEconomy().getMarket("OT_a");
        if (market1 != null) CERPerson.create();
        //CyanCoreOfficerGen.create();
        //if (!MagicVariables.getIBB()) cerGen.spawngrandfleet();
        syncCERScripts();
        //addNPCs
        if (!haveNexerelin || SectorManager.getManager().isCorvusMode()) {
            if (!Global.getSector().getMemoryWithoutUpdate().contains(MEMKEY_INTIALIZED_CER)) {
                addToOngoingGame();
                Global.getSector().getMemoryWithoutUpdate().set(MEMKEY_INTIALIZED_CER, true);
            }
        }

        if(newGame) {
            boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
            if (!haveNexerelin || SectorManager.getCorvusMode()) {

                (new cerGen()).generate(Global.getSector());
            } else {
                (new cerGen()).generate(Global.getSector());
            }
        }
        //addfleets
        if (!Global.getSector().getMemoryWithoutUpdate().contains(MEMKEY_MAIN_FLEETS_INITIALIZED_CER)) {
            addFleetsToOngoingGame();
            Global.getSector().getMemoryWithoutUpdate().set(MEMKEY_MAIN_FLEETS_INITIALIZED_CER, true);
        }
        //Load the only Colony Item used
        final float CER_NANOFORGE_ITEM_QUALITY_BONUS = 0.4f;
        ItemEffectsRepo.ITEM_EFFECTS.put(cer_ids.CER_NANOFORGE_ITEM, new BaseInstallableItemEffect(cer_ids.CER_NANOFORGE_ITEM) {
            @Override
            public void apply(Industry industry) {
                industry.getMarket().getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD)
                        .modifyFlat("nanoforge", CER_NANOFORGE_ITEM_QUALITY_BONUS, Misc.ucFirst(spec.getName().toLowerCase()));

            }

            @Override
            public void unapply(Industry industry) {
                industry.getSupplyBonus().modifyFlat(spec.getId(), 0, Misc.ucFirst(spec.getName().toLowerCase()));
                industry.getMarket().getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD).unmodifyFlat("nanoforge");
            }

            @Override
            protected void addItemDescriptionImpl(Industry industry, TooltipMakerAPI text, SpecialItemData data,
                                                  InstallableItemDescriptionMode mode, String pre, float pad) {

                text.addPara(pre + "Increases ship and weapon production quality by %s.",
                        pad, Misc.getHighlightColor(),
                        Math.round(CER_NANOFORGE_ITEM_QUALITY_BONUS * 100f) + "%");
            }
        });
       if (CyanCoreOfficerGen.getPerson(cer_ids.cercyancore) != null) {
            CyanCoreOfficerGen.setInstanceChipDescription(cer_ids.CYANCORE_CHIP, CyanCoreOfficerGen.getPerson(cer_ids.cercyancore));
        }
    }

    public void onNewGame() {
        boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");

        //Global.getSector().addTransientScript(new OnuoMarketScript());
        if (!haveNexerelin || SectorManager.getManager().isCorvusMode()) {
            new cerGen().generate(Global.getSector());
        }

        Global.getSector().getMemoryWithoutUpdate().set(MEMKEY_INTIALIZED_CER, true);
    }

   protected void addFleetsToOngoingGame() {


    }

    protected void addToOngoingGame() {
        if (!haveNexerelin || SectorManager.getManager().isCorvusMode()) {
            new cerGen().generate(Global.getSector());
            //MarketHelpers.generateMarketsFromEconJson("diable_OnuoCapital");

        }
    }

    public void onNewGameAfterEconomyLoad() {
        cerGen.spawn1stfleet();
        cerGen.spawn2ndfleet();
        cerGen.spawn3rdfleet();
        cerGen.spawn4thfleet();
        Global.getSector().getMemoryWithoutUpdate().set(MEMKEY_MAIN_FLEETS_INITIALIZED_CER, true);
        Global.getSector().getMemoryWithoutUpdate().set(MEMKEY_VERSION_CER, 0.1 );
    }
/*
    @Override
    public PluginPick<ShipAIPlugin> pickShipAI(FleetMemberAPI member, ShipAPI ship) {
        if (ship.isFighter()) return null;

        ModManager manager = ModManager.getInstance();
        if (!manager.isModEnabled("secretsofthefrontier")) {
            if (ship.getCaptain().getFaction().getId().equals("da_cer") || ship.getVariant().getHullMods().contains("EX_streamcaps") || ship.getCaptain().getMemoryWithoutUpdate().contains(cer_ids.OFFICER_NOT_FEARLESS)) {
                return new PluginPick<ShipAIPlugin>(Global.getSettings().createDefaultShipAI(ship, new ShipAIConfig()), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            }
            return null;
        }
        return null;
    }
 */

}