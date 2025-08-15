package data.scripts.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;

public class diableavionics_KaijuDriveStats extends BaseShipSystemScript {

    public static float SHIELD_DAMAGEREDUCTION = 0.1f;
    public static float MAXSPEED_BONUS = 300f;
    public static float ACCEL_BONUS = 200f;
    public static float SHIELD_UPKEEP = 1.0f;
    public static float SHIELD_BONUS_TURN = 2.0f;

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        if (state == State.OUT) {
            stats.getMaxSpeed().unmodify(id); // to slow down ship to its regular top speed while powering drive down
        } else {
            stats.getShieldDamageTakenMult().modifyMult(id, SHIELD_DAMAGEREDUCTION * effectLevel);
            stats.getMaxSpeed().modifyFlat(id, MAXSPEED_BONUS * effectLevel);
            stats.getAcceleration().modifyFlat(id, ACCEL_BONUS * effectLevel);
            //stats.getAcceleration().modifyPercent(id, 200f * effectLevel);
            stats.getShieldUpkeepMult().modifyMult(id, SHIELD_UPKEEP);
            stats.getShieldTurnRateMult().modifyMult(id, SHIELD_BONUS_TURN);
        }
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getShieldDamageTakenMult().unmodify(id);
        stats.getShieldUpkeepMult().unmodify(id);
        stats.getShieldTurnRateMult().unmodify(id);
    }
    //private final String TXT1 = Diableavionics_stringsManager.txt("shield");

    //private final String TXT2 = Diableavionics_stringsManager.txt("%");

    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State  state, float effectLevel) {
        if (index == 0) {
            return new StatusData("increased engine power, Engine Turn Rate and Shield invulnerability Active.", false);
        }
        return null;
    }
}