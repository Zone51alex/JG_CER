package data.scripts.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import com.fs.starfarer.api.util.IntervalUtil;
import java.awt.*;
import static data.scripts.util.Diableavionics_stringsManager.txt;

public class diable_SuperBurnDriveStats extends BaseShipSystemScript {

    private final Float DAMAGE_RESISTANCE = 0.5F;
    public static float MAXSPEED_BONUS = 300f;
    public static float ACCEL_BONUS = 200f;

    //private final IntervalUtil effectInterval = new IntervalUtil(0.1f, 0.1f);


    public void apply(MutableShipStatsAPI stats, String id, ShipSystemStatsScript.State state, float effectLevel) {

        stats.getArmorDamageTakenMult().modifyMult(id, DAMAGE_RESISTANCE);
        stats.getHullDamageTakenMult().modifyMult(id, DAMAGE_RESISTANCE);
        stats.getEmpDamageTakenMult().modifyMult(id, DAMAGE_RESISTANCE);
        if (state == ShipSystemStatsScript.State.OUT) {
            stats.getMaxSpeed().unmodify(id); // to slow down ship to its regular top speed while powering drive down
        } else {
            stats.getMaxSpeed().modifyFlat(id, MAXSPEED_BONUS * effectLevel);
            stats.getAcceleration().modifyFlat(id, ACCEL_BONUS * effectLevel);
        }
        /*
        ShipAPI ship = (ShipAPI)stats.getEntity();
        if (ship != null) {

            effectInterval.advance(Global.getCombatEngine().getElapsedInLastFrame());
            if (effectInterval.intervalElapsed()) {
                ship.addAfterimage(new Color(100, 255, 100, 80), 0.0F, 0.0F, -(ship.getVelocity()).x, -(ship.getVelocity()).y, 0.0F, 0.0F, 0.5F, 0.0F, false, false, false);
            }
        }
        */
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getArmorDamageTakenMult().unmodify(id);
        stats.getHullDamageTakenMult().unmodify(id);
        stats.getEmpDamageTakenMult().unmodify(id);

    }

    @Override
    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State state, float effectLevel) {

        if (index == 0) {
            return new StatusData(txt("damper1") + Math.round((1 - DAMAGE_RESISTANCE) * 100 * effectLevel) + txt("%"), false);
        }
        return null;
    }
}
