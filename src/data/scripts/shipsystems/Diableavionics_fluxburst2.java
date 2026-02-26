package data.scripts.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import data.scripts.util.Diableavionics_stringsManager;
import static data.scripts.util.Diableavionics_stringsManager.txt;

public class Diableavionics_fluxburst2 extends BaseShipSystemScript {

    private final float
            ROF_BONUS_PERCENT = 200,
            BEAM_BONUS_PERCENT = 200,
            FLUX_REDUCTION = 0.8f;

    private final Integer TURN_ACC_BUFF = Integer.valueOf(1000);
    private final Integer TURN_RATE_BUFF = Integer.valueOf(500);
    private final Integer ACCEL_BUFF = Integer.valueOf(500);
    private final Integer DECCEL_BUFF = Integer.valueOf(300);
    private final Integer SPEED_BUFF = Integer.valueOf(300);

    @Override
    public void apply(MutableShipStatsAPI stats, String id, ShipSystemStatsScript.State state, float effectLevel) {

        float beamPercent = BEAM_BONUS_PERCENT * effectLevel;
        stats.getBeamWeaponDamageMult().modifyPercent(id, beamPercent);

        float rofPercent = ROF_BONUS_PERCENT * effectLevel;
        stats.getBallisticRoFMult().modifyPercent(id, rofPercent);
        stats.getEnergyRoFMult().modifyPercent(id, rofPercent);

        float fluxMult = 1-(FLUX_REDUCTION* effectLevel);
        stats.getBallisticWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getEnergyWeaponFluxCostMod().modifyMult(id,fluxMult);
        stats.getBeamWeaponFluxCostMult().modifyMult(id,fluxMult);

        stats.getTurnAcceleration().modifyPercent(id, this.TURN_ACC_BUFF.intValue());
        stats.getMaxTurnRate().modifyPercent(id, this.TURN_RATE_BUFF.intValue());
        stats.getMaxSpeed().modifyPercent(id, this.SPEED_BUFF.intValue());
        stats.getAcceleration().modifyPercent(id, this.ACCEL_BUFF.intValue());
        stats.getDeceleration().modifyPercent(id, this.DECCEL_BUFF.intValue());


    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getBallisticRoFMult().unmodify(id);
        stats.getBallisticWeaponFluxCostMod().unmodify(id);
        stats.getEnergyRoFMult().unmodify(id);
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
        stats.getBeamWeaponDamageMult().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getTurnAcceleration().unmodify(id);
        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getTimeMult().unmodify(id);

    }
    private final String TXT1 = txt("redirection1");
    private final String TXT2 = txt("%");
    private final String TXT3 = txt("redirection2");
    private final String TXT4 = Diableavionics_stringsManager.txt("drift");

    @Override
    public ShipSystemStatsScript.StatusData getStatusData(int index, ShipSystemStatsScript.State state, float effectLevel) {
        int mult = Math.round(ROF_BONUS_PERCENT * effectLevel);
        int flux = Math.round(FLUX_REDUCTION*100 * effectLevel);
        if (index == 0) {
            return new ShipSystemStatsScript.StatusData(TXT1 + mult + TXT2, false);
        }
        if (index == 1) {
            return new ShipSystemStatsScript.StatusData(TXT3 + flux + TXT2, false);
        }
        if (index == 2) {
            return new ShipSystemStatsScript.StatusData(this.TXT4, false);
        }
        return null;
    }
}
