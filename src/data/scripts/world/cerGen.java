package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.campaign.ids.Diableavionics_ids;
import data.campaign.ids.cer_ids;
import exerelin.campaign.AllianceManager;
import exerelin.campaign.DiplomacyManager;
import exerelin.campaign.PlayerFactionStore;
import exerelin.campaign.alliances.Alliance;
import exerelin.utilities.NexConfig;
import exerelin.utilities.NexFactionConfig;
import org.magiclib.util.MagicCampaign;
import data.scripts.util.Diableavionics_stringsManager;
import data.scripts.plugins.CERPerson;

import java.util.Map;

import static data.scripts.util.Diableavionics_stringsManager.txt;

public class cerGen implements SectorGeneratorPlugin {

    public void generate(SectorAPI sector) {
        //relations
        FactionAPI da_cer = sector.getFaction("da_cer");
        FactionAPI player = sector.getFaction("player");
        FactionAPI hegemony = sector.getFaction("hegemony");
        FactionAPI tritachyon = sector.getFaction("tritachyon");
        FactionAPI pirates = sector.getFaction("pirates");
        FactionAPI independent = sector.getFaction("independent");
        FactionAPI church = sector.getFaction("luddic_church");
        FactionAPI path = sector.getFaction("luddic_path");
        FactionAPI diktat = sector.getFaction("sindrian_diktat");
        FactionAPI kol = sector.getFaction("knights_of_ludd");
        FactionAPI persean = sector.getFaction("persean");
        FactionAPI guard = sector.getFaction("lions_guard");
        FactionAPI remnant = sector.getFaction("remnant");
        FactionAPI derelict = sector.getFaction("derelict");
        da_cer.setRelationship(guard.getId(), RepLevel.SUSPICIOUS);
        da_cer.setRelationship(diktat.getId(), RepLevel.SUSPICIOUS);
        da_cer.setRelationship(player.getId(), RepLevel.FAVORABLE);
        da_cer.setRelationship(independent.getId(), RepLevel.FRIENDLY);
        da_cer.setRelationship(tritachyon.getId(), RepLevel.INHOSPITABLE);
        da_cer.setRelationship(pirates.getId(), RepLevel.VENGEFUL);
        da_cer.setRelationship(persean.getId(), RepLevel.FRIENDLY);
        da_cer.setRelationship(kol.getId(), RepLevel.INHOSPITABLE);
        da_cer.setRelationship(hegemony.getId(), RepLevel.SUSPICIOUS);
        da_cer.setRelationship(path.getId(), RepLevel.VENGEFUL);
        da_cer.setRelationship(church.getId(), RepLevel.SUSPICIOUS);
        da_cer.setRelationship(remnant.getId(), RepLevel.HOSTILE);
        da_cer.setRelationship(derelict.getId(), RepLevel.FRIENDLY);
        da_cer.setRelationship("diableavionics", RepLevel.COOPERATIVE);
        da_cer.setRelationship("cabal", RepLevel.VENGEFUL);
        da_cer.setRelationship("sun_ici", RepLevel.FAVORABLE);
        da_cer.setRelationship("crystanite", RepLevel.NEUTRAL);
        da_cer.setRelationship("mayorate", RepLevel.NEUTRAL);
        da_cer.setRelationship("pirateAnar", RepLevel.VENGEFUL);
        da_cer.setRelationship("exipirated", RepLevel.NEUTRAL);
        da_cer.setRelationship("exigency", RepLevel.SUSPICIOUS);
        da_cer.setRelationship("syndicate_asp", RepLevel.SUSPICIOUS);
        da_cer.setRelationship("tiandong", RepLevel.SUSPICIOUS);
        da_cer.setRelationship("SCY", RepLevel.FRIENDLY);
        da_cer.setRelationship("neutrinocorp", RepLevel.FRIENDLY);
        da_cer.setRelationship("6eme_bureau", RepLevel.INHOSPITABLE);
        da_cer.setRelationship("dassault_mikoyan", RepLevel.INHOSPITABLE);
        da_cer.setRelationship("pack", RepLevel.INHOSPITABLE);
        da_cer.setRelationship("blackrock_driveyards", RepLevel.INHOSPITABLE);
        da_cer.setRelationship("citadeldefenders", RepLevel.NEUTRAL);
        da_cer.setRelationship("pn_colony", RepLevel.NEUTRAL);
        da_cer.setRelationship("junk_pirates", RepLevel.VENGEFUL);
        da_cer.setRelationship("sun_ice", RepLevel.INHOSPITABLE);
        da_cer.setRelationship("shadow_industry", RepLevel.HOSTILE);
        da_cer.setRelationship("ORA", RepLevel.FRIENDLY);
        da_cer.setRelationship("interstellarimperium", RepLevel.HOSTILE);
        da_cer.setRelationship("blade_breakers", RepLevel.HOSTILE);
        da_cer.setRelationship("new_galactic_order", RepLevel.VENGEFUL);
        da_cer.setRelationship("explorer_society", RepLevel.VENGEFUL);
        da_cer.setRelationship("scalartech", RepLevel.COOPERATIVE);
        da_cer.setRelationship("Coalition", -0.2F);
        da_cer.setRelationship("metelson", -0.2F);
        da_cer.setRelationship("the_deserter", 0.35F);
        da_cer.setRelationship("noir", 0.0F);
        da_cer.setRelationship("Lte", 0.0F);
        da_cer.setRelationship("GKSec", 0.1F);
        da_cer.setRelationship("gmda", -0.1F);
        da_cer.setRelationship("oculus", -0.25F);
        da_cer.setRelationship("nomads", -0.25F);
        da_cer.setRelationship("thulelegacy", -0.25F);
        da_cer.setRelationship("infected", -0.99F);
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("da_cer");
    }
       public static void cer_main (SectorAPI sector) {
           FactionAPI cer_main = sector.getFaction("da_cer");
           cer_main.setRelationship("diableavionics", 1.0F);
           if (Global.getSettings().getBoolean("DiableCorp")) {
               Alliance ceralli = AllianceManager.createAlliance("da_cer", "diableavionics", AllianceManager.getBestAlignment("da_cer", "diableavionics"));
               ceralli.setName(Global.getSettings().getString("diableavionics", "cer_DiableCorp"));
               ceralli.addPermaMember("da_cer");
               ceralli.addPermaMember("diableavionics");
           }
           cer_main.setRelationship("player", Global.getSector().getPlayerFaction().getRelationship("diableavionics"));
           if (Global.getSettings().getModManager().isModEnabled("nexerelin")) {
               NexFactionConfig factionConfig = NexConfig.getFactionConfig("da_cer");
               if (!DiplomacyManager.isRandomFactionRelationships()) {
                   factionConfig.minRelationships.clear();
                   factionConfig.minRelationships.put("diableavionics", 0.251F);
               }
               for (Map.Entry<String, Float> entry : factionConfig.startRelationships.entrySet()) {
                   if ("da_cer".equals(entry.getKey()))
                       continue;
                   cer_main.setRelationship(entry.getKey(), entry.getValue());
               }
               if ("da_cer".equals(PlayerFactionStore.getPlayerFactionIdNGC()) || "diableavionics".equals(PlayerFactionStore.getPlayerFactionIdNGC()))
                   cer_main.setRelationship("diableavionics", 0.75F);
           }
       }
    //cerGen.spawn1stfleet();
    //cerGen.spawn2ndfleet();
    //cerGen.spawn3rdfleet();
    //cerGen.spawn4thfleet();
    //1st Grand Defense Fleet - Pina Colada Fleet
    public static void spawn1stfleet() {
        //settle for the largest military market
        SectorEntityToken target = null;

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.getFaction().getId().equals("da_cer")) {
                if (target == null
                        || (m.hasSubmarket(Submarkets.GENERIC_MILITARY) && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY)
                        || m.getSize() > target.getMarket().getSize()))) {
                    target = m.getPrimaryEntity();
                }
            }
        }

        if (target != null) {
            //magiccaptain

            PersonAPI FleetCaptain = MagicCampaign.createCaptainBuilder("da_cer")
                    .setFirstName(Diableavionics_stringsManager.txt("1stFleetFN"))
                    .setLastName(Diableavionics_stringsManager.txt("1stFleetLN"))
                    .setPortraitId("cer_1stFleetCaptain")
                    .setGender(FullName.Gender.MALE)
                    .setRankId(Ranks.SPACE_ADMIRAL)
                    .setPostId(Ranks.POST_FLEET_COMMANDER)
                    .setPersonality(Personalities.STEADY)
                    .setLevel(8)
                    .setEliteSkillsOverride(3)
                    .setSkillPreference(OfficerManagerEvent.SkillPickPreference.NO_ENERGY_YES_BALLISTIC_YES_MISSILE_YES_DEFENSE)
                    .create();

            //Fleet creation code
            CampaignFleetAPI dacer1stfleet = MagicCampaign.createFleetBuilder()
                    .setFleetFaction("da_cer")
                    .setFleetName(Diableavionics_stringsManager.txt("1stFleet"))
                    .setFleetType(FleetTypes.TASK_FORCE)
                    .setFlagshipName(Diableavionics_stringsManager.txt("1stFlagship"))
                    .setFlagshipAlwaysRecoverable(false)
                    .setFlagshipVariant("diable_pandemonium_EX_Flagship")
                    .setFlagshipAutofit(false)
                    .setCaptain(FleetCaptain)
                    .setMinFP(300)
                    .setReinforcementFaction("da_cer")
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.DEFEND_LOCATION)
                    .setAssignmentTarget(target)
                    .setSpawnLocation(target)
                    .setIsImportant(false)
                    .setTransponderOn(true)
                    .create();

            dacer1stfleet.setDiscoverable(false);
            dacer1stfleet.addTag(Tags.NEUTRINO);
            //gulf.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer1stfleet.getMemoryWithoutUpdate().set("$dacer_1stfleet", true);
        }
    }
    //2nd Counter Attack Fleet -
    public static void spawn2ndfleet() {
        //settle for the largest military market
        SectorEntityToken target = null;

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.getFaction().getId().equals("da_cer")) {
                if (target == null
                        || (m.hasSubmarket(Submarkets.GENERIC_MILITARY) && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY)
                        || m.getSize() > target.getMarket().getSize()))) {
                    target = m.getPrimaryEntity();
                }
            }
        }

        if (target != null) {
            //magiccaptain

            PersonAPI FleetCaptain = MagicCampaign.createCaptainBuilder("da_cer")
                    .setFirstName(Diableavionics_stringsManager.txt("2ndFleetFN"))
                    .setLastName(Diableavionics_stringsManager.txt("2ndFleetLN"))
                    .setPortraitId("cer_2ndFleetCaptain")
                    .setGender(FullName.Gender.MALE)
                    .setRankId(Ranks.SPACE_ADMIRAL)
                    .setPostId(Ranks.POST_FLEET_COMMANDER)
                    .setPersonality(Personalities.STEADY)
                    .setLevel(8)
                    .setEliteSkillsOverride(0)
                    .setSkillPreference(OfficerManagerEvent.SkillPickPreference.NO_ENERGY_YES_BALLISTIC_YES_MISSILE_YES_DEFENSE)
                    .create();

            //Fleet creation code
            CampaignFleetAPI dacer2ndfleet = MagicCampaign.createFleetBuilder()
                    .setFleetFaction("da_cer")
                    .setFleetName(txt("2ndFleet"))
                    .setFleetType(FleetTypes.TASK_FORCE)
                    .setFlagshipName(txt("2ndFlagship"))
                    .setFlagshipAlwaysRecoverable(false)
                    .setFlagshipVariant("diable_maelstrom_Boss")
                    .setFlagshipAutofit(false)
                    .setCaptain(FleetCaptain)
                    .setMinFP(300)
                    .setReinforcementFaction("da_cer")
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.PATROL_SYSTEM)
                    .setAssignmentTarget(target)
                    .setSpawnLocation(target)
                    .setIsImportant(false)
                    .setTransponderOn(true)
                    .create();

            dacer2ndfleet.setDiscoverable(false);
            dacer2ndfleet.addTag(Tags.NEUTRINO);
            //gulf.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer2ndfleet.getMemoryWithoutUpdate().set("$dacer_2ndfleet", true);
        }
    }
    //3rd Fast Attack Fleet -
    public static void spawn3rdfleet() {
        //settle for the largest military market
        SectorEntityToken target = null;

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.getFaction().getId().equals("da_cer")) {
                if (target == null
                        || (m.hasSubmarket(Submarkets.GENERIC_MILITARY) && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY)
                        || m.getSize() > target.getMarket().getSize()))) {
                    target = m.getPrimaryEntity();
                }
            }
        }

        if (target != null) {
            //magiccaptain

            PersonAPI FleetCaptain = MagicCampaign.createCaptainBuilder("da_cer")
                    .setFirstName(Diableavionics_stringsManager.txt("3rdFleetFN"))
                    .setLastName(Diableavionics_stringsManager.txt("3rdFleetLN"))
                    .setPortraitId("cer_3rdFleetCaptain")
                    .setGender(FullName.Gender.MALE)
                    .setRankId(Ranks.SPACE_ADMIRAL)
                    .setPostId(Ranks.POST_FLEET_COMMANDER)
                    .setPersonality(Personalities.STEADY)
                    .setLevel(8)
                    .setEliteSkillsOverride(3)
                    .setSkillPreference(OfficerManagerEvent.SkillPickPreference.NO_ENERGY_YES_BALLISTIC_YES_MISSILE_YES_DEFENSE)
                    .create();

            //Fleet creation code
            CampaignFleetAPI dacer3rdfleet = MagicCampaign.createFleetBuilder()
                    .setFleetFaction("da_cer")
                    .setFleetName(txt("3rdFleet"))
                    .setFleetType(FleetTypes.PATROL_MEDIUM)
                    .setFlagshipName(txt("3rdFlagship"))
                    .setFlagshipAlwaysRecoverable(false)
                    .setFlagshipVariant("diableavionics_gust_Frontline")
                    .setFlagshipAutofit(false)
                    .setCaptain(FleetCaptain)
                    .setMinFP(150)
                    .setReinforcementFaction("da_cer")
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.PATROL_SYSTEM)
                    .setAssignmentTarget(target)
                    .setSpawnLocation(target)
                    .setIsImportant(false)
                    .setTransponderOn(true)
                    .create();

            dacer3rdfleet.setDiscoverable(false);
            dacer3rdfleet.addTag(Tags.NEUTRINO);
            //gulf.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer3rdfleet.getMemoryWithoutUpdate().set("$dacer_3rdfleet", true);
        }
    }
    //4th Spec Ops Fleet -
    public static void spawn4thfleet() {
        //settle for the largest military market
        SectorEntityToken target = null;

        for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
            if (m.getFaction().getId().equals("da_cer")) {
                if (target == null
                        || (m.hasSubmarket(Submarkets.GENERIC_MILITARY) && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY)
                        || m.getSize() > target.getMarket().getSize()))) {
                    target = m.getPrimaryEntity();
                }
            }
        }

        if (target != null) {
            //magiccaptain

            PersonAPI FleetCaptain = MagicCampaign.createCaptainBuilder("da_cer")
                    .setFirstName(Diableavionics_stringsManager.txt("4thFleetFN"))
                    .setLastName(Diableavionics_stringsManager.txt("4thFleetLN"))
                    .setPortraitId("cer_4thFleetCaptain")
                    .setGender(FullName.Gender.MALE)
                    .setRankId(Ranks.SPACE_ADMIRAL)
                    .setPostId(Ranks.POST_FLEET_COMMANDER)
                    .setPersonality(Personalities.STEADY)
                    .setLevel(8)
                    .setEliteSkillsOverride(3)
                    .setSkillPreference(OfficerManagerEvent.SkillPickPreference.NO_ENERGY_YES_BALLISTIC_YES_MISSILE_YES_DEFENSE)
                    .create();

            //Fleet creation code
            CampaignFleetAPI dacer4thfleet = MagicCampaign.createFleetBuilder()
                    .setFleetFaction("da_cer")
                    .setFleetName(txt("4thFleet"))
                    .setFleetType(FleetTypes.PATROL_SMALL)
                    .setFlagshipName(txt("4thFlagship"))
                    .setFlagshipAlwaysRecoverable(false)
                    .setFlagshipVariant("diableavionics_calm_Mixed_cer")
                    .setFlagshipAutofit(false)
                    .setCaptain(FleetCaptain)
                    .setMinFP(100)
                    .setReinforcementFaction("da_cer")
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.PATROL_SYSTEM)
                    .setAssignmentTarget(target)
                    .setSpawnLocation(target)
                    .setIsImportant(false)
                    .setTransponderOn(true)
                    .create();

            dacer4thfleet.setDiscoverable(false);
            dacer4thfleet.addTag(Tags.NEUTRINO);
            //gulf.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer4thfleet.getMemoryWithoutUpdate().set("$dacer_4thfleet", true);
        }
    }

}