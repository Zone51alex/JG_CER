package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class cer_semiautomated extends BaseHullMod {

    public static float MAX_CR_PENALTY = 0.0F;

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMinCrewMod().modifyMult(id, 0.0F);
    }
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ship.setInvalidTransferCommandTarget(true);
    }
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        float opad = 10.0F;

            tooltip.addPara("However, this ship was automated in a fashion that does not require special expertise to maintain. Some of the techniques used are poorly understood, likely dating to an earlier period.",
                    opad, Misc.getHighlightColor(), new String[] {
                            Math.round(MAX_CR_PENALTY * 100.0F) + "%"
                    });
        }
    }

