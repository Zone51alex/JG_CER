package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
public class overloadedsubsystems extends BaseHullMod {

    //Variables
    public static final float DEGRADE_INCREASE_PERCENT = 50.0F;
    public static final float PPT_PERCENT = 50.0F;
   //Applying stats
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getCRLossPerSecondPercent().modifyPercent(id, 50.0F);
        stats.getPeakCRDuration().modifyPercent(id, -50.0F);
    }
   //Description code
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "50%";
        if (index == 1) return "50%";
        return null;
    }
    //????
    public boolean isApplicableToShip(ShipAPI ship) {
        return (ship != null && (ship.getHullSpec().getNoCRLossTime() < 10000.0F || ship.getHullSpec().getCRLossPerSecond(ship.getMutableStats()) > 0.0F));
    }

}
