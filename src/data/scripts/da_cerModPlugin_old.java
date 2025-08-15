package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.InstallableIndustryItemPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseInstallableItemEffect;
import com.fs.starfarer.api.impl.campaign.econ.impl.ItemEffectsRepo;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.launcher.ModManager;
import data.scripts.world.cerGen;

public class da_cerModPlugin_old extends BaseModPlugin {
  public void onApplicationLoad() {
    ModManager manager = ModManager.getInstance();
    if (manager.isModEnabled("da_Junkers_Guild"))
      throw new RuntimeException("Corvid Engineering & Recover Subsidiary is the updated version of DA's Junkers Guild why do you have it both on ?"); 
  }
  
  public void onGameLoad(boolean newGame) {
    float CER_NANOFORGE_ITEM_QUALITY_BONUS = 0.4F;
    ItemEffectsRepo.ITEM_EFFECTS.put("cer_nanoforge", new BaseInstallableItemEffect("cer_nanoforge") {
          public void apply(Industry industry) {
            industry.getMarket().getStats().getDynamic().getMod("production_quality_mod").modifyFlat("nanoforge", 0.4F, Misc.ucFirst(this.spec.getName().toLowerCase()));
          }
          
          public void unapply(Industry industry) {
            industry.getSupplyBonus().modifyFlat(this.spec.getId(), 0.0F, Misc.ucFirst(this.spec.getName().toLowerCase()));
            industry.getMarket().getStats().getDynamic().getMod("production_quality_mod").unmodifyFlat("nanoforge");
          }
          
          protected void addItemDescriptionImpl(Industry industry, TooltipMakerAPI text, SpecialItemData data, InstallableIndustryItemPlugin.InstallableItemDescriptionMode mode, String pre, float pad) {
            text.addPara(pre + "Increases ship and weapon production quality by %s." + "On habitable worlds, causes pollution which becomes permanent.", pad, Misc.getHighlightColor(), new String[] { Math.round(40.0F) + "%" });
          }
        });
  }
  
  public void onNewGame() {
    boolean bool = Global.getSettings().getModManager().isModEnabled("nexerelin");
    (new cerGen()).generate(Global.getSector());
  }
  
  protected void addFleetsToOngoingGame() {
    //cerGen.spawngrandfleet();
  }
  
  public void onNewGameAfterEconomyLoad() {
    //cerGen.spawngrandfleet();
    MarketAPI market = Global.getSector().getEconomy().getMarket("OT_a");
    if (market != null) {
      if (!market.getAdmin().getName().getLast().equals("Dankshire")) {
        PersonAPI dank1 = Global.getFactory().createPerson();
        dank1.setFaction("da_cer");
        dank1.setGender(FullName.Gender.MALE);
        dank1.setRankId("cer_grandadmiral");
        dank1.setPostId("cer_grandadmiral");
        dank1.getName().setFirst("Mordrigg");
        dank1.getName().setLast("Dankshire V");
        dank1.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_grandadmiral"));
        if (Global.getSettings().getModManager().isModEnabled("IndEvo")) {
          dank1.getStats().setSkillLevel("indevo_industrial_planning", 1.0F);
          dank1.getStats().setSkillLevel("indevo_planetary_operations", 1.0F);
          dank1.getStats().setSkillLevel("indevo_fleet_logistics", 1.0F);
        } else {
          dank1.getStats().setSkillLevel("industrial_planning", 1.0F);
        } 
        market.getCommDirectory().addPerson(dank1, 0);
        market.addPerson(dank1);
        market.setAdmin(dank1);
      } 
      if (!market.getAdmin().getName().getLast().equals("Taxmen")) {
        PersonAPI daxter = Global.getFactory().createPerson();
        daxter.setFaction("da_cer");
        daxter.setGender(FullName.Gender.MALE);
        daxter.setRankId("cer_chiefengineering");
        daxter.setPostId("cer_chiefengineering");
        daxter.getName().setFirst("Daxter");
        daxter.getName().setLast("Taxmen");
        daxter.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_daxter"));
        market.getCommDirectory().addPerson(daxter, 1);
        market.addPerson(daxter);
      } 
      if (!market.getAdmin().getName().getLast().equals("Faun")) {
        PersonAPI faun = Global.getFactory().createPerson();
        faun.setFaction("da_cer");
        faun.setGender(FullName.Gender.MALE);
        faun.setRankId("cer_assistengineering");
        faun.setPostId("cer_assistengineering");
        faun.getName().setFirst("Lola");
        faun.getName().setLast("Faun");
        faun.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_faun"));
        market.getCommDirectory().addPerson(faun, 2);
        market.addPerson(faun);
      } 
    } 
    if (market != null);
  }
}
