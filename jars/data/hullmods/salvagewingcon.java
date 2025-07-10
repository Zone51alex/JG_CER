package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;

public class salvagewingcon extends BaseHullMod {

    private static float REDUCED_RANGE = 0.0F;
    //set fighter engagement range to 0
    private static float LOSSES = 0.75F;
    //reduce crew losses by 25%

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        //Fighter losses Code
        stats.getDynamic().getStat("fighter_crew_loss_mult").modifyMult(id, LOSSES);
        //Fighter Range Reduction Code
        stats.getFighterWingRange().modifyMult(id, REDUCED_RANGE);
    }
    //Description Code
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
            return (int)((1.0F - REDUCED_RANGE) * 100.0F) + "%";
        if (index == 1)
            return (int)((1.0F - LOSSES) * 100.0F) + "%";
        return null;
    }
    //Applicable to ship code
    public boolean isApplicableToShip(ShipAPI ship) {
        if (ship.getVariant().hasHullMod("automated"))
            return false;
        int bays = (int)ship.getMutableStats().getNumFighterBays().getModifiedValue();
        return (ship != null && bays > 0);
    }
    public String getUnapplicableReason(ShipAPI ship) {
        if (ship != null && ship.getVariant().hasHullMod("automated"))
            return "Can not be installed on automated ships";
        return "Ship does not have fighter bays";
    }

}