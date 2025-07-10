package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;

public class StreamlinedCapacitors extends BaseHullMod {
    //set the variable
    public static float FLUX_DECREASE = -10f;
    //add the effect
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getEnergyWeaponFluxCostMod().modifyPercent(id, FLUX_DECREASE);
    }

    //Hullmod Description.
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
            return (int) FLUX_DECREASE + "%";
            return null;
    }
    //End of Code
}