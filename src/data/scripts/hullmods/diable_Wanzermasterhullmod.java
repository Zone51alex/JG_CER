package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
public class diable_Wanzermasterhullmod extends BaseHullMod {
    //Basic Striekcraft huillmod systems

    //handover to Arma Strikecraft hullmod
        public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
            boolean haveArma = Global.getSettings().getModManager().isModEnabled("strikeCraft");
            if (haveArma)
                ship.getVariant().addPermaMod("strikeCraft");
        }
}