package data.scripts.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import data.scripts.util.Diableavionics_stringsManager;

public class Diableavionics_transit3Stats extends BaseShipSystemScript {
    private final Integer SPEED_BONUS = Integer.valueOf(140);

    private final Integer ACCEL_BONUS = Integer.valueOf(200);

    private final float DECEL_MALUS = 0.8F;

    private final float TURN_MALUS = 0.4F;

    private final float DAMAGE_REDUCTION = 0.75F;

    public void apply(MutableShipStatsAPI stats, String id, ShipSystemStatsScript.State state, float effectLevel) {
        if (state == ShipSystemStatsScript.State.OUT) {
            stats.getMaxSpeed().unmodify(id);
        } else {
            stats.getMaxSpeed().modifyFlat(id, this.SPEED_BONUS.intValue() * effectLevel);
            stats.getAcceleration().modifyPercent(id, this.ACCEL_BONUS.intValue() * effectLevel);
            stats.getDeceleration().modifyMult(id, 1.0F - 0.9F * effectLevel);
            stats.getTurnAcceleration().modifyMult(id, 1.0F - 0.5F * effectLevel);
            stats.getMaxTurnRate().modifyMult(id, 1.0F - 0.5F * effectLevel);
            stats.getArmorDamageTakenMult().modifyMult(id, 1.0F - 0.5F * effectLevel);
            stats.getHullDamageTakenMult().modifyMult(id, 1.0F - 0.5F * effectLevel);
        }
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getArmorDamageTakenMult().unmodify(id);
        stats.getHullDamageTakenMult().unmodify(id);
    }

    private final String TXT1 = Diableavionics_stringsManager.txt("transit2");

    private final String TXT2 = Diableavionics_stringsManager.txt("transit3");

   /*private final String TXT3 = Diableavionics_stringsManager.txt("%");*/

    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State state, float effectLevel) {
        int speed = Math.round(this.SPEED_BONUS.intValue() * effectLevel);
        int dmg = Math.round(50.0F * effectLevel);
        if (index == 0)
            return new ShipSystemStatsScript.StatusData(this.TXT1 + this.TXT1, false);
        if (index == 1)
            return new ShipSystemStatsScript.StatusData(this.TXT2 + this.TXT2 + dmg, false);
        return null;
    }
}