package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import data.scripts.util.Diableavionics_stringsManager;
import org.magiclib.util.MagicIncompatibleHullmods;
import java.util.HashSet;
import java.util.Set;

public class ImprovedThrusters extends BaseHullMod {
    //mod description value
    public static float MANEUVER_BONUS = 50f;
    //Blocked Hullmods value
    private final Set<String> BLOCKED_HULLMODS;
    //Stats value setup
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
        stats.getDeceleration().modifyPercent(id, MANEUVER_BONUS);
        stats.getTurnAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
        stats.getMaxTurnRate().modifyPercent(id, MANEUVER_BONUS);
    }
    //improvedthrusters variables
    public ImprovedThrusters() {
        this.BLOCKED_HULLMODS = new HashSet<>();
        this.BLOCKED_HULLMODS.add("auxiliarythrusters");
    }
    //apply blocked hullmod
        public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
            for (String tmp : this.BLOCKED_HULLMODS) {
                if (ship.getVariant().getHullMods().contains(tmp))
                    MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "EX_Impthrusters");
            }
        }

    //Hullmod Description.
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) MANEUVER_BONUS + Diableavionics_stringsManager.txt("%");
        if (index == 1) return Diableavionics_stringsManager.txt("EX_impthrusters");
        return null;
    }
    //
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship.getHullSpec().getHullId().startsWith("diableavionics_");
    }
//End of Code
}