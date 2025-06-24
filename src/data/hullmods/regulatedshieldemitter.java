package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import data.scripts.util.Diableavionics_stringsManager;
import org.magiclib.util.MagicIncompatibleHullmods;

import java.util.HashSet;
import java.util.Set;

public class regulatedshieldemitter extends BaseHullMod {

    public static float SHIELD_UPKEEP_BONUS = 50f;
    public static float SOFT_FLUX_CONVERSION = 0.5f;
    private final Set<String> BLOCKED_HULLMODS = new HashSet();

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getShieldUpkeepMult().modifyMult(id, 1f - SHIELD_UPKEEP_BONUS * 0.01f);
        stats.getShieldSoftFluxConversion().modifyFlat(id, SOFT_FLUX_CONVERSION);

    }
    //apply blocked hullmod
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        for (String tmp : this.BLOCKED_HULLMODS) {
            if (ship.getVariant().getHullMods().contains(tmp))
                MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "EX_regulateshieldemitter");
        }
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + (int) SHIELD_UPKEEP_BONUS + "%";
        if (index == 1) return "" + Math.round(SOFT_FLUX_CONVERSION * 100f) + "%";
        if (index == 2) return Diableavionics_stringsManager.txt("EX_nitro_1");
        return null;
    }
    public regulatedshieldemitter() {
        this.BLOCKED_HULLMODS.add("stabilizedshieldemitter");

    }
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship.getHullSpec().getHullId().startsWith("diableavionics_");
    }
}