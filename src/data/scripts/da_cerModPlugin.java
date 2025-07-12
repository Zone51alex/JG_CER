package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.InstallableIndustryItemPlugin.InstallableItemDescriptionMode;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseInstallableItemEffect;
import com.fs.starfarer.api.impl.campaign.econ.impl.ItemEffectsRepo;
import com.fs.starfarer.launcher.ModManager;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.campaign.ids.cer_items;
import data.scripts.plugins.CERPerson;
import data.scripts.plugins.OnuoMarketScript;
import data.scripts.world.cerGen;

import exerelin.campaign.SectorManager;

public class da_cerModPlugin extends BaseModPlugin {


    public void onApplicationLoad() {
        ModManager manager = ModManager.getInstance();
        if (manager.isModEnabled("da_Junkers_Guild"))
            throw new RuntimeException("Corvid Engineering & Recover Subsidiary is the updated version of DA's Junkers Guild why do you have it both on ?");
    }


    public void onGameLoad(boolean newGame) {
        //cerGen
        if(newGame) {
            boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
            if (!haveNexerelin || SectorManager.getCorvusMode()) {

                (new cerGen()).generate(Global.getSector());
            } else {
                (new cerGen()).generate(Global.getSector());
            }
        }

        final float CER_NANOFORGE_ITEM_QUALITY_BONUS = 0.4f;
        ItemEffectsRepo.ITEM_EFFECTS.put(cer_items.CER_NANOFORGE_ITEM, new BaseInstallableItemEffect(cer_items.CER_NANOFORGE_ITEM) {
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
    }

    public void onNewGame() {
        boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");

        //Global.getSector().addTransientScript(new OnuoMarketScript());
        CERPerson.create();
        if (!haveNexerelin || SectorManager.getCorvusMode()) {

            (new cerGen()).generate(Global.getSector());
        }
        else
        {

            (new cerGen()).generate(Global.getSector());
        }
    }
   /*
   protected void addFleetsToOngoingGame() {
        cerGen.spawngrandfleet();
    }
    */
    public void onNewGameAfterEconomyLoad() {
        cerGen.spawngrandfleet();

    }
}