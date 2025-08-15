package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public class diable_armareplacer extends BaseHullMod {

    public diable_armareplacer() {
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        boolean haveArmaa = Global.getSettings().getModManager().isModEnabled("armaa");
        if (haveArmaa) ship.getVariant().addPermaMod("armaa_wingCommander");

    }
}