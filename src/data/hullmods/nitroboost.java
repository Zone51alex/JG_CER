package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import data.scripts.util.Diableavionics_stringsManager;
import org.magiclib.util.MagicIncompatibleHullmods;
import java.util.HashSet;
import java.util.Set;

public class nitroboost extends BaseHullMod {
    //
    public static float SPEED_BONUS = 100f;
    //
    private final Set<String> BLOCKED_HULLMODS;

    //
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getZeroFluxSpeedBoost().modifyFlat(id, SPEED_BONUS);
    }

    //apply blocked hullmod
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        for (String tmp : this.BLOCKED_HULLMODS) {
            if (ship.getVariant().getHullMods().contains(tmp))
                MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "EX_nitroboost");
        }
    }

    //
    public nitroboost() {
        this.BLOCKED_HULLMODS = new HashSet<>();
        this.BLOCKED_HULLMODS.add("auxiliarythrusters");
        this.BLOCKED_HULLMODS.add("unstable_injector");
    }

    //Hullmod Description.
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) SPEED_BONUS + "";
        if (index == 1)
            return Diableavionics_stringsManager.txt("EX_nitro_1");
        if (index == 2)
            return Diableavionics_stringsManager.txt("EX_nitro_2");
        return null;
    }

    //
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship.getHullSpec().getHullId().startsWith("diableavionics_");
        //End of Code
    }
}