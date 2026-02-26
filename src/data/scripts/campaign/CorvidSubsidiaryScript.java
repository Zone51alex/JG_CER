package data.scripts.campaign;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.campaign.FleetAssignment;
import data.scripts.world.cerGen;
import org.magiclib.util.MagicCampaign;
import com.fs.starfarer.api.campaign.SectorAPI;


public class CorvidSubsidiaryScript implements EveryFrameScript {

    private float daysSinceLastCheck = 0f;

    @Override
    public boolean isDone() {
        return false; // runs for the whole campaign
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        daysSinceLastCheck += amount / 86400f; // seconds → in-game days
        if (daysSinceLastCheck < 1f) return; // only run once per in-game day
        daysSinceLastCheck = 0f;

        SectorAPI sector = Global.getSector();

        // Spawn or respawn fleets
        checkAndRespawnCorvidFleets();

        MarketAPI corvidMarket = Global.getSector().getEconomy().getMarket("OT_a");
        if (corvidMarket == null) {
            removeBonusFromDiable(sector);
            return;
        }
        if (!"da_cer".equals(corvidMarket.getFactionId())) {
            removeBonusFromDiable(sector);
            triggerDiableHostilityAndInvasion(corvidMarket);
            return;
        }

        // Corvid exists → add industries safely
        addIndustries(corvidMarket);

        // Apply production bonus to Diable markets
        applyBonusToDiable(sector);
    }

    private void checkAndRespawnCorvidFleets() {
        // Each fleet has its own memkey and spawn method
        checkAndRespawnFleet("$dacer_1stfleet", cerGen::spawn1stfleet);
        checkAndRespawnFleet("$dacer_2ndfleet", cerGen::spawn2ndfleet);
        checkAndRespawnFleet("$dacer_3rdfleet", cerGen::spawn3rdfleet);
        checkAndRespawnFleet("$dacer_4thfleet", cerGen::spawn4thfleet);
    }

    private void checkAndRespawnFleet(String memKey, Runnable spawnMethod) {
        MemoryAPI memory = Global.getSector().getMemoryWithoutUpdate();

        // If memkey missing or false, spawn the fleet
        if (!memory.getBoolean(memKey)) {
            spawnMethod.run(); // This method must set memKey to true inside itself
        }
    }

    /** Adds industries to Corvid market only if they exist and are not already added */
    private void addIndustries(MarketAPI market) {
        // Drydock (IndEvo)
        if (Global.getSettings().getModManager().isModEnabled("IndEvo")) {
            addIndustrySafe(market, "IndEvo_dryDock");
        }
        addIndustrySafe(market, "orbitalworks");    // Orbital Works

        market.setFactionId("da_cer");
    }

    /** Adds a single industry if it exists and is not already present */
    private void addIndustrySafe(MarketAPI market, String industryId) {
        if (market.getIndustry(industryId) != null) return;
        if (Global.getSettings().getIndustrySpec(industryId) != null) {
            market.addIndustry(industryId);
        }
    }

    /** Applies +20% ship & weapon production bonus to all Diable markets */
    private void applyBonusToDiable(SectorAPI sector) {
        for (MarketAPI m : sector.getEconomy().getMarketsCopy()) {
            if ("diableavionics".equals(m.getFactionId())) {
                m.getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD)
                        .modifyFlat("cer_supply_bonus", 0.2f, "Corvid subsidiary bonus");
            }
        }
    }

    /** Removes bonus from Diable markets if Corvid is gone */
    private void removeBonusFromDiable(SectorAPI sector) {
        for (MarketAPI m : sector.getEconomy().getMarketsCopy()) {
            if ("diableavionics".equals(m.getFactionId())) {
                m.getStats().getDynamic().getMod(Stats.PRODUCTION_QUALITY_MOD)
                        .unmodify("cer_supply_bonus");
            }
        }
    }

    private void triggerDiableHostilityAndInvasion(MarketAPI lostMarket) {
        SectorAPI sector = Global.getSector();
        if (sector.getMemoryWithoutUpdate().getBoolean("$dacer_invasion_triggered")) return;

        FactionAPI newOwner = lostMarket.getFaction();
        FactionAPI diable = sector.getFaction("diableavionics");
        if (diable != null && newOwner != null && !"da_cer".equals(newOwner.getId())) {
            diable.setRelationship(newOwner.getId(), RepLevel.VENGEFUL);
        }

        CampaignFleetAPI firstFleet = (CampaignFleetAPI) sector.getMemoryWithoutUpdate().get("$dacer_1stfleet");
        if (firstFleet != null && !firstFleet.isDespawning()) {
            firstFleet.clearAssignments();
            firstFleet.addAssignment(FleetAssignment.ATTACK_LOCATION, lostMarket.getPrimaryEntity(), 999f, "Retaking Onuo");
        } else {
            // Fallback spawn
            PersonAPI captain = MagicCampaign.createCaptainBuilder("da_cer")
                    // ... set name/portrait/etc as before
                    .create();

            CampaignFleetAPI invasionFleet = MagicCampaign.createFleetBuilder()
                    .setFleetFaction("da_cer")
                    .setFleetName("Corvid Retaliation Fleet")
                    .setFleetType(FleetTypes.TASK_FORCE)  // or RAID_FLEET if exists
                    .setFlagshipName("Retribution")
                    .setFlagshipVariant("diable_pandemonium_EX_Flagship")
                    .setFlagshipAutofit(false)
                    .setCaptain(captain)
                    .setMinFP((int) 800f)
                    .setQualityOverride(2f)
                    .setAssignment(FleetAssignment.ATTACK_LOCATION)
                    .setAssignmentTarget(lostMarket.getPrimaryEntity())
                    .setSpawnLocation(lostMarket.getContainingLocation().createToken(lostMarket.getLocationInHyperspace()))
                    .create();

            invasionFleet.addTag(Tags.NEUTRINO);  // after import com.fs.starfarer.api.impl.campaign.ids.Tags;
            invasionFleet.setDiscoverable(false);
        }

        sector.getMemoryWithoutUpdate().set("$dacer_invasion_triggered", true);
    }
}