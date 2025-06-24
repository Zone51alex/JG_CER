package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.util.Diableavionics_stringsManager;
public class jg_brownnote extends BaseHullMod {

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
            return "" + "100%";
        if (index == 1)
            return Diableavionics_stringsManager.txt("jg_persudophase");
        return null;
    }

}
