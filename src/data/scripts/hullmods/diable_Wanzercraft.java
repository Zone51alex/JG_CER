package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.AdvanceableListener;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class diable_Wanzercraft extends BaseHullMod {
    //Basic Striekcraft huillmod systems
            public static float REGEN_RATE = 3.0F;

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ship.addListener(new diable_WanzercraftListener$(ship));
        //handover to Arma Strikecraft hullmod
        boolean haveArma = Global.getSettings().getModManager().isModEnabled("armaa");
        if (haveArma)
            ship.getVariant().addPermaMod("strikeCraft");
    }



    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "" + ((Float) REGEN_RATE).intValue();
        return null;
    }
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        Color warning = new Color(199, 78, 78, 155);

        tooltip.addSpacer(10.0F);
        tooltip.addSectionHeading("Details", Alignment.MID, 0.0F);
        tooltip.addSpacer(10.0F);

        tooltip.addPara("This Ship Has a Built-in Backup Hull Regenerator recovering 3 units of Hull every Second.", 0.0F,
                Misc.getTextColor(), Misc.getHighlightColor());

        tooltip.addSpacer(10.0F);

        tooltip.addSectionHeading("Warning", Misc.getHighlightColor(), warning, Alignment.MID, 0.0F);
        tooltip.addSpacer(10.0F);

        tooltip.addPara("The Recovery systems can only be active during Zero Flux Boost.", 0.2F, Misc.getNegativeHighlightColor(), new String[] { "zero-flux boost" });
        tooltip.addImageWithText(0.0F);
    }

    public static class diable_WanzercraftListener$ implements AdvanceableListener {
        ShipAPI ship;
        Float regenPerSecond = Float.valueOf(0.0F);
        Float regenedgoing = Float.valueOf(0.0F);

        Float delay = Float.valueOf(0.0F);

        public diable_WanzercraftListener$(ShipAPI ship) {
            this.ship = ship;
        }
        public void advance(float amount) {
            this.regenPerSecond = diable_Wanzercraft.REGEN_RATE;

            float level = (this.regenedgoing.floatValue() - 0.0F) / (this.ship.getMaxHitpoints());
            int remaining = (int) ((1.0F - level) * 100.0F);

            if (!this.ship.getFluxTracker().isEngineBoostActive()) {
                this.delay = Float.valueOf(0.0F);
            }
            this.delay = Float.valueOf(this.delay.floatValue() + 1.0F * amount);
            boolean halted = (this.delay.floatValue() <= 3.0F);

            String path = "graphics/icons/hullsys/damper_field.png";

            String title = "Hull Regen";
            if (halted) title = title + " (Halted)";

            if (this.ship == Global.getCombatEngine().getPlayerShip()) {
                Global.getCombatEngine().maintainStatusForPlayerShip("", path, title, "Regen Remaining: " + remaining + "%", false);
            }

            if (halted)
                return;  if (this.regenedgoing.floatValue() > this.ship.getMaxHitpoints())
                return;  if (this.ship.getHitpoints() >= this.ship.getMaxHitpoints())
                return;
            float recover = this.regenPerSecond.floatValue() * amount;
            this.ship.setHitpoints(this.ship.getHitpoints() + recover);
            if (this.ship.getHitpoints() > this.ship.getMaxHitpoints()) {
                float over = this.ship.getHitpoints() - this.ship.getMaxHitpoints();
                this.ship.setHitpoints(this.ship.getHitpoints() - over);

                recover -= over;
            }

            this.regenedgoing = Float.valueOf(this.regenedgoing.floatValue() + recover);
        }
    }
}