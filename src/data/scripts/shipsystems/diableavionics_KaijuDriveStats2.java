package data.scripts.shipsystems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.util.IntervalUtil;
import data.scripts.util.Diableavionics_stringsManager;
import data.scripts.util.MagicAnim;

import java.awt.*;

import static data.scripts.util.Diableavionics_stringsManager.txt;

public class diableavionics_KaijuDriveStats2 extends BaseShipSystemScript {

    public static float SHIELD_DAMAGEREDUCTION = 0.1f;
    public static float MAXSPEED_BONUS = 300f;
    public static float ACCEL_BONUS = 200f;
    public static float SHIELD_UPKEEP = 1.0f;
    public static float SHIELD_BONUS_TURN = 2.0f;
    public static float MAX_AMMO = 2.0f;
    public static float MAX_AMMO_REGEN = 8.0f;
    private static float ROF_BONUS_PERCENT = 100, BEAM_BONUS_PERCENT = 100, FLUX_REDUCTION = 0.8f;

    private final IntervalUtil effectInterval = new IntervalUtil(0.1f, 0.1f);

    //private final Integer TURN_ACC_BUFF = Integer.valueOf(1000);
    //private final Integer TURN_RATE_BUFF = Integer.valueOf(200);
    //private final Integer ACCEL_BUFF = Integer.valueOf(500);
    //private final Integer DECCEL_BUFF = Integer.valueOf(300);
    //private final Integer SPEED_BUFF = Integer.valueOf(300);

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        ShipAPI ship = (ShipAPI)stats.getEntity();
        if (ship != null) {

            effectInterval.advance(Global.getCombatEngine().getElapsedInLastFrame());
            if (effectInterval.intervalElapsed()) {
                ship.addAfterimage(new Color(100, 255, 100, 80), 0.0F, 0.0F, -(ship.getVelocity()).x, -(ship.getVelocity()).y, 0.0F, 0.0F, 0.5F, 0.0F, false, false, false);
            }
        }

        float beamPercent = BEAM_BONUS_PERCENT * effectLevel;
        stats.getBeamWeaponDamageMult().modifyPercent(id, beamPercent);

        float rofPercent = ROF_BONUS_PERCENT * effectLevel;
        stats.getBallisticRoFMult().modifyPercent(id, rofPercent);
        stats.getEnergyRoFMult().modifyPercent(id, rofPercent);

        float fluxMult = 1-(FLUX_REDUCTION* effectLevel);
        stats.getBallisticWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getEnergyWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getBeamWeaponFluxCostMult().modifyMult(id,fluxMult);

        //stats.getTurnAcceleration().modifyPercent(id, this.TURN_ACC_BUFF.intValue());
        //stats.getMaxTurnRate().modifyPercent(id, this.TURN_RATE_BUFF.intValue());
        //stats.getMaxSpeed().modifyPercent(id, this.SPEED_BUFF.intValue());
        //stats.getAcceleration().modifyPercent(id, this.ACCEL_BUFF.intValue());
        //stats.getDeceleration().modifyPercent(id, this.DECCEL_BUFF.intValue());

        if (state == State.OUT) {
            stats.getMaxSpeed().unmodify(id); // to slow down ship to its regular top speed while powering drive down
        } else {
            stats.getShieldDamageTakenMult().modifyMult(id, SHIELD_DAMAGEREDUCTION * effectLevel);
            stats.getMaxSpeed().modifyFlat(id, MAXSPEED_BONUS * effectLevel);
            stats.getAcceleration().modifyFlat(id, ACCEL_BONUS * effectLevel);
            //stats.getAcceleration().modifyPercent(id, 200f * effectLevel);
            stats.getShieldUpkeepMult().modifyMult(id, SHIELD_UPKEEP);
            stats.getShieldTurnRateMult().modifyMult(id, SHIELD_BONUS_TURN);
            stats.getBallisticAmmoBonus().modifyMult(id, MAX_AMMO);
            stats.getBallisticAmmoRegenMult().modifyMult(id, MAX_AMMO_REGEN);
        }

    }

    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getBallisticRoFMult().unmodify(id);
        stats.getBallisticWeaponFluxCostMod().unmodify(id);
        stats.getEnergyRoFMult().unmodify(id);
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
        stats.getBeamWeaponDamageMult().unmodify(id);
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getShieldDamageTakenMult().unmodify(id);
        stats.getShieldUpkeepMult().unmodify(id);
        stats.getShieldTurnRateMult().unmodify(id);
        stats.getBallisticAmmoBonus().unmodifyMult(id);
        stats.getBallisticAmmoRegenMult().unmodifyMult(id);
        /*
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getShieldDamageTakenMult().unmodify(id);
        stats.getShieldUpkeepMult().unmodify(id);
        stats.getShieldTurnRateMult().unmodify(id);
        */
    }
    //private final String TXT1 = Diableavionics_stringsManager.txt("shield");

    //private final String TXT2 = Diableavionics_stringsManager.txt("%");
/*
    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State  state, float effectLevel) {
        if (index == 0) {
            return new StatusData("increased engine power, Engine Turn Rate and Shield invulnerability Active.", false);
        }
        return null;
    }
    */
    private final String TXT1 = txt("redirection1");
    private final String TXT2 = txt("%");
    private final String TXT3 = txt("redirection2");
    private final String TXT4 = Diableavionics_stringsManager.txt("drift");
    //    private final String TXT4 = txt("redirection3");
//    private final String TXT5 = txt("redirection4");
    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        int mult = Math.round(ROF_BONUS_PERCENT * effectLevel);
//        int acc = Math.round(ACCURACY_LOSS * effectLevel * 2);
        int flux = Math.round(FLUX_REDUCTION*100 * effectLevel);
        if (index == 0) {
            return new StatusData(TXT1 + mult + TXT2, false);
        }
        if (index == 1) {
            return new StatusData(TXT3 + flux + TXT2, false);
        }
        if (index == 2) {
            return new StatusData(this.TXT4, false);
        }
        return null;
    }
}