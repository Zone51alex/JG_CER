package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.util.Diableavionics_stringsManager;
import org.magiclib.util.MagicIncompatibleHullmods;

import java.util.HashSet;
import java.util.Set;

public class kaijuengines extends BaseHullMod {

    private final Set<String> BLOCKED_HULLMODS;

    //public static float REPAIR_RATE_BONUS = 50f;
    //public static float CR_RECOVERY_BONUS = 50f;
    public static float REPAIR_BONUS = 75f;


    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {

        float bonus = REPAIR_BONUS;
        stats.getCombatEngineRepairTimeMult().modifyMult(id, 1f - REPAIR_BONUS * 0.01f);
        stats.getCombatWeaponRepairTimeMult().modifyMult(id, 1f - REPAIR_BONUS * 0.01f);

        //stats.getBaseCRRecoveryRatePercentPerDay().modifyPercent(id, CR_RECOVERY_BONUS);
        //stats.getRepairRatePercentPerDay().modifyPercent(id, REPAIR_RATE_BONUS);
        //stats.getSuppliesToRecover().modifyPercent(id, LOGISTICS_PENALTY);
        //stats.getSuppliesPerMonth().modifyPercent(id, LOGISTICS_PENALTY);
    }


    //apply blocked hull-mod
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        for (String tmp : this.BLOCKED_HULLMODS) {
            if (ship.getVariant().getHullMods().contains(tmp))
                MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "EX_nitroboost");
        }
    }

    //Add Hull-mods to the Blacklist
    public kaijuengines() {
        this.BLOCKED_HULLMODS = new HashSet<>();
        this.BLOCKED_HULLMODS.add("autorepair");
    }

    //Hull-mod Description.
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + (int) REPAIR_BONUS + "%";
        if (index == 1) return Diableavionics_stringsManager.txt("EX_kaijuengines_1");
        return null;
    }

    //
    public boolean isApplicableToShip(ShipAPI ship) {
        return ship.getHullSpec().getHullId().startsWith("diableavionics_");

    }
    /* End of Code */
}
