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
import data.scripts.campaign.CorvidSubsidiaryScript;
import exerelin.campaign.AllianceManager;
import exerelin.campaign.alliances.Alliance;
import org.magiclib.util.MagicCampaign;
import data.scripts.util.Diableavionics_stringsManager;

import java.util.HashMap;
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

    public static void cer_main(SectorAPI sector) {

        if (Global.getSettings().getBoolean("DiableCorp")) {

            Alliance alliance = AllianceManager.getFactionAlliance("da_cer");

            if (alliance == null || !alliance.getMembersCopy().contains("diableavionics")) {
                alliance = AllianceManager.createAlliance(
                        "da_cer",
                        "diableavionics",
                        AllianceManager.getBestAlignment("da_cer", "diableavionics")
                );
            }

            // ✅ Only operate if alliance is non-null
            if (alliance != null) {
                alliance.setName(Global.getSettings().getString("diableavionics", "cer_DiableCorp"));
                alliance.addPermaMember("da_cer");
                alliance.addPermaMember("diableavionics");
            } else {
                // Optional: log a warning if creation failed
                Global.getLogger(CorvidSubsidiaryScript.class).warn(
                        "Failed to create alliance between da_cer and diableavionics"
                );
            }

            // Forced relationship
            sector.getFaction("da_cer").setRelationship("diableavionics", 1.0f);
        }
    }

    //1st Grand Defense Fleet - Pina Colada Fleet
    public static void spawn1stfleet() {
        //Spawn Location
        SectorEntityToken target = null;
        if (Global.getSector().getEntityById("OT_a") != null && Global.getSector().getEntityById("OT_a").getFaction() == Global.getSector().getFaction("da_cer")) {
            target = Global.getSector().getEntityById("OT_a");
        } else {
            //settle for the largest military market
            for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
                if (m.getFaction().getId().equals("da_cer")) {
                    if (target == null || (m.hasSubmarket(Submarkets.GENERIC_MILITARY)
                            && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY) || m.getSize() > target.getMarket().getSize()))) {
                        target = m.getPrimaryEntity();
                    }
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
                    .setPersonality(Personalities.AGGRESSIVE)
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
                    .setMinFP(400)
                    .setReinforcementFaction("da_cer")
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.ORBIT_AGGRESSIVE)
                    .setAssignmentTarget(target)
                    .setSpawnLocation(target)
                    .setIsImportant(false)
                    .setTransponderOn(true)
                    .create();

            dacer1stfleet.setDiscoverable(false);
            dacer1stfleet.addTag(Tags.NEUTRINO);
            dacer1stfleet.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer1stfleet.getMemoryWithoutUpdate().set("$dacer_1stfleet", true);
            Global.getSector().getMemoryWithoutUpdate().set("$dacer_1stfleet", dacer1stfleet);
        }
    }
    //2nd Counter Attack Fleet -
    public static void spawn2ndfleet() {
        //Spawn Location
        SectorEntityToken target = null;
        if (Global.getSector().getEntityById("OT_a") != null && Global.getSector().getEntityById("OT_a").getFaction() == Global.getSector().getFaction("da_cer")) {
            target = Global.getSector().getEntityById("OT_a");
        } else {
            //settle for the largest military market
            for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
                if (m.getFaction().getId().equals("da_cer")) {
                    if (target == null || (m.hasSubmarket(Submarkets.GENERIC_MILITARY)
                            && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY) || m.getSize() > target.getMarket().getSize()))) {
                        target = m.getPrimaryEntity();
                    }
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
                    .setPersonality(Personalities.AGGRESSIVE)
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
                    .setFlagshipVariant("diable_riptide_EX_Standard")
                    .setFlagshipAutofit(false)
                    .setCaptain(FleetCaptain)
                    .setMinFP(400)
                    .setReinforcementFaction("da_cer")
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.DEFEND_LOCATION)
                    .setAssignmentTarget(target)
                    .setSpawnLocation(target)
                    .setIsImportant(false)
                    .setTransponderOn(true)
                    .create();

            dacer2ndfleet.setDiscoverable(false);
            dacer2ndfleet.addTag(Tags.NEUTRINO);
            dacer2ndfleet.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer2ndfleet.getMemoryWithoutUpdate().set("$dacer_2ndfleet", true);
            Global.getSector().getMemoryWithoutUpdate().set("$dacer_2ndfleet", dacer2ndfleet);
        }
    }
    //3rd Fast Attack Fleet -
    public static void spawn3rdfleet() {
        //Spawn Location
        SectorEntityToken target = null;
        if (Global.getSector().getEntityById("OT_a") != null && Global.getSector().getEntityById("OT_a").getFaction() == Global.getSector().getFaction("da_cer")) {
            target = Global.getSector().getEntityById("OT_a");
        } else {
            //settle for the largest military market
            for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
                if (m.getFaction().getId().equals("da_cer")) {
                    if (target == null || (m.hasSubmarket(Submarkets.GENERIC_MILITARY)
                            && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY) || m.getSize() > target.getMarket().getSize()))) {
                        target = m.getPrimaryEntity();
                    }
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
                    .setPersonality(Personalities.AGGRESSIVE)
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
                    .setFlagshipVariant("diable_haze_cer_Standard")
                    .setFlagshipAutofit(false)
                    .setCaptain(FleetCaptain)
                    .setMinFP(200)
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
            dacer3rdfleet.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer3rdfleet.getMemoryWithoutUpdate().set("$dacer_3rdfleet", true);
            Global.getSector().getMemoryWithoutUpdate().set("$dacer_3rdfleet", dacer3rdfleet);
        }
    }
    //4th Spec Ops Fleet -
    public static void spawn4thfleet() {
        //Spawn Location
        SectorEntityToken target = null;
        if (Global.getSector().getEntityById("OT_a") != null && Global.getSector().getEntityById("OT_a").getFaction() == Global.getSector().getFaction("da_cer")) {
            target = Global.getSector().getEntityById("OT_a");
        } else {
            //settle for the largest military market
            for (MarketAPI m : Global.getSector().getEconomy().getMarketsCopy()) {
                if (m.getFaction().getId().equals("da_cer")) {
                    if (target == null || (m.hasSubmarket(Submarkets.GENERIC_MILITARY)
                            && (!target.getMarket().hasSubmarket(Submarkets.GENERIC_MILITARY) || m.getSize() > target.getMarket().getSize()))) {
                        target = m.getPrimaryEntity();
                    }
                }
            }
        }

        if (target != null) {
            //magiccaptain
            Map<String, Integer> skills = new HashMap<>();
            skills.put(Skills.HELMSMANSHIP, 2);
            skills.put(Skills.FIELD_MODULATION, 2);
            skills.put(Skills.COMBAT_ENDURANCE, 2);
            skills.put(Skills.ENERGY_WEAPON_MASTERY, 2);
            skills.put(Skills.TARGET_ANALYSIS, 2);

            PersonAPI FleetCaptain = MagicCampaign.createCaptainBuilder("da_cer")
                    .setIsAI(true)
                    .setAICoreType(cer_ids.CYANCORE_CHIP) // was setAICoreId
                    .setFirstName("Cyan")
                    .setLastName("Sentinel-Warden")
                    .setPortraitId("cyancore")
                    .setGender(FullName.Gender.MALE)
                    .setFactionId("da_cer")
                    .setRankId(Ranks.SPACE_ADMIRAL)
                    .setPostId(Ranks.POST_GUARD_LEADER)
                    .setPersonality(Personalities.AGGRESSIVE)
                    .setLevel(5)
                    .setSkillLevels(skills)
                    .create();

            //Fleet creation code
            org.magiclib.campaign.MagicFleetBuilder fleetBuilder = MagicCampaign.createFleetBuilder();
            fleetBuilder.setFleetFaction("da_cer");
            fleetBuilder.setFleetName(txt("4thFleet"));
            fleetBuilder.setFleetType(FleetTypes.PATROL_SMALL);
            fleetBuilder.setFlagshipName(txt("4thFlagship"));
            fleetBuilder.setFlagshipAlwaysRecoverable(false);
            fleetBuilder.setFlagshipVariant("diable_calm_EX_Flagship");
            fleetBuilder.setFlagshipAutofit(false);
            fleetBuilder.setCaptain(FleetCaptain);
            fleetBuilder.setMinFP(150);
            fleetBuilder.setReinforcementFaction("da_cer");
            fleetBuilder.setQualityOverride(2f);
            fleetBuilder.setAssignment(FleetAssignment.PATROL_SYSTEM);
            fleetBuilder.setAssignmentTarget(target);
            fleetBuilder.setSpawnLocation(target);
            fleetBuilder.setIsImportant(false);
            fleetBuilder.setTransponderOn(false);
            CampaignFleetAPI dacer4thfleet = fleetBuilder
                    .create();

            dacer4thfleet.setDiscoverable(false);
            dacer4thfleet.addTag(Tags.NEUTRINO);
            dacer4thfleet.getFlagship().getStats().getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(Diableavionics_ids.UNIQUE, -2000f);
            dacer4thfleet.getMemoryWithoutUpdate().set("$dacer_4thfleet", true);
            Global.getSector().getMemoryWithoutUpdate().set("$dacer_4thfleet", dacer4thfleet);
        }
    }
}