package data.scripts.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import com.fs.starfarer.api.util.IntervalUtil;
import data.scripts.util.Diableavionics_stringsManager;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

@SuppressWarnings("unused")  // IntelliJ warning suppression
public class Diableavionics_transitEXStats extends BaseShipSystemScript {

    private final IntervalUtil effectInterval = new IntervalUtil(0.1f, 0.1f);
    private final Integer SPEED_BONUS = Integer.valueOf(140);
    private final Integer ACCEL_BONUS = Integer.valueOf(200);
    //private final float DECEL_MALUS = 0.9F;
    //private final float TURN_MALUS = 0.5F;
    //private final float DAMAGE_REDUCTION = 0.5F;
    private final String TXT1 = Diableavionics_stringsManager.txt("transit2");
    private final String TXT2 = Diableavionics_stringsManager.txt("transit3");
    //private final String TXT3 = Diableavionics_stringsManager.txt("%");

    public void apply(MutableShipStatsAPI stats, String id, ShipSystemStatsScript.State state, float effectLevel) {

        ShipAPI ship = (ShipAPI)stats.getEntity();
        if (ship != null) {

            effectInterval.advance(Global.getCombatEngine().getElapsedInLastFrame());
            if (effectInterval.intervalElapsed()) {
                // Add field
                Vector2f fixedVel = null;

// In apply(), inside effectInterval.intervalElapsed():
                if (fixedVel == null) {
                    fixedVel = new Vector2f(ship.getVelocity());
                }

                ship.addAfterimage(
                        new Color(100, 255, 100, 80),
                        0f, 0f,
                        -fixedVel.x, -fixedVel.y,
                        0f, 0f, 0.5f, 0f,
                        false, false, false
                );
            }
        }

        if (state == State.OUT) {
            stats.getMaxSpeed().unmodify(id);
        } else {
            stats.getMaxSpeed().modifyFlat(id, (float)this.SPEED_BONUS.intValue() * effectLevel);
            stats.getAcceleration().modifyPercent(id, (float)this.ACCEL_BONUS.intValue() * effectLevel);
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

    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State state, float effectLevel) {
        //int speed = Math.round(this.SPEED_BONUS.intValue() * effectLevel);
        int dmg = Math.round(50.0F * effectLevel);
        if (index == 0)
            return new ShipSystemStatsScript.StatusData(this.TXT1 + this.TXT1, false);
        if (index == 1)
            return new ShipSystemStatsScript.StatusData(this.TXT2 + this.TXT2 + dmg, false);
        return null;
    }
}