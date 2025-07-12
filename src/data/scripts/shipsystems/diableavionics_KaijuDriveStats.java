package data.scripts.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import data.scripts.util.Diableavionics_stringsManager;

public class diableavionics_KaijuDriveStats extends BaseShipSystemScript {

    public void apply(MutableShipStatsAPI stats, String id, ShipSystemStatsScript.State state, float effectLevel) {

        if (state == ShipSystemStatsScript.State.OUT) {
            stats.getMaxSpeed().unmodify(id); // to slow down ship to its regular top speed while powering drive down
        } else {
            stats.getShieldTurnRateMult().modifyMult(id, 2.0F);
            stats.getShieldDamageTakenMult().modifyMult(id, 1.0F - 0.9F * effectLevel);
            stats.getShieldUpkeepMult().modifyMult(id, 0.0F);
            stats.getMaxSpeed().modifyFlat(id, 200f * effectLevel);
            stats.getAcceleration().modifyFlat(id, 200f * effectLevel);
            //stats.getAcceleration().modifyPercent(id, 200f * effectLevel);
            stats.getShieldArcBonus().modifyMult(id, 300f * effectLevel);
            stats.getShieldUnfoldRateMult().modifyMult(id, 300f * effectLevel);
        }
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getShieldDamageTakenMult().unmodify(id);
        stats.getShieldTurnRateMult().unmodify(id);
        stats.getShieldUpkeepMult().unmodify(id);
        stats.getShieldArcBonus().unmodify(id);
        stats.getShieldUnfoldRateMult().unmodify(id);
    }
    private final String TXT1 = Diableavionics_stringsManager.txt("shield");

    //private final String TXT2 = Diableavionics_stringsManager.txt("%");

    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State  state, float effectLevel) {
        if (index == 0) {
            return new StatusData("increased engine power", false);
        }
        if (index == 1) {
            return new ShipSystemStatsScript.StatusData(this.TXT1 + Math.round(1000.0F * effectLevel), false);
        }
        if (index == 2) {
            return new ShipSystemStatsScript.StatusData("Engine Turn Rate and Shield System Override Active.", false);
        }
        return null;
    }
}