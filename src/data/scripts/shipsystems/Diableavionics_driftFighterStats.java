package data.scripts.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import data.scripts.util.Diableavionics_stringsManager;
import data.scripts.util.MagicAnim;

import java.awt.*;

public class Diableavionics_driftFighterStats extends BaseShipSystemScript {
    private final Integer TURN_ACC_BUFF = Integer.valueOf(1000);

    private final Integer TURN_RATE_BUFF = Integer.valueOf(500);

    private final Integer ACCEL_BUFF = Integer.valueOf(500);

    private final Integer DECCEL_BUFF = Integer.valueOf(300);

    private final Integer SPEED_BUFF = Integer.valueOf(200);

    private final Integer TIME_BUFF = Integer.valueOf(1000);

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        float effect = Math.min(1.0F, Math.max(0.0F, MagicAnim.smoothReturnNormalizeRange(effectLevel, 0.0F, 1.0F) / 2.0F + MagicAnim.smoothReturnNormalizeRange(effectLevel * 1.5F, 0.0F, 1.0F) / 2.0F + MagicAnim.smoothReturnNormalizeRange(effectLevel * 2.0F, 0.0F, 1.0F) / 2.0F));
        ShipAPI ship = (ShipAPI)stats.getEntity();
        if (ship != null) {
            ship.setJitterUnder(ship, Color.CYAN, 0.5F * effect, 5, 5.0F + 5.0F * effect, 5.0F + 10.0F * effect);
            if (Math.random() > 0.8999999761581421D)
                ship.addAfterimage(new Color(0, 200, 255, 64), 0.0F, 0.0F, -(ship.getVelocity()).x, -(ship.getVelocity()).y, 5.0F + 50.0F * effect, 0.0F, 0.0F, 2.0F * effect, false, false, false);
            if (!stats.getTimeMult().getPercentMods().containsKey(id)) {
                Global.getSoundPlayer().playSound("diableavionics_drift", 1.0F, 1.66F, ship.getLocation(), ship.getVelocity());
                ship.setPhased(true);
            } else if (ship.isPhased()) {
                ship.setPhased(false);
            }
        }
        stats.getTurnAcceleration().modifyPercent(id, this.TURN_ACC_BUFF.intValue() * effect);
        stats.getMaxTurnRate().modifyPercent(id, this.TURN_RATE_BUFF.intValue() * effect);
        stats.getMaxSpeed().modifyPercent(id, this.SPEED_BUFF.intValue() * effect);
        stats.getAcceleration().modifyPercent(id, this.ACCEL_BUFF.intValue());
        stats.getDeceleration().modifyPercent(id, this.DECCEL_BUFF.intValue());
        stats.getTimeMult().modifyPercent(id, this.TIME_BUFF.intValue() * effect);
    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getTimeMult().unmodify(id);
    }

    private final String TXT = Diableavionics_stringsManager.txt("drift");

    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0)
            return new StatusData(this.TXT, false);
        return null;
    }
}
