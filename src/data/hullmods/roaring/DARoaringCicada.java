package data.hullmods.roaring;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.util.Diableavionics_stringsManager;
public class DARoaringCicada extends BaseHullMod {
    public int getDisplaySortOrder() {
        return 2000;
    }

    public int getDisplayCategoryIndex() {
        return 3;
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
            return Diableavionics_stringsManager.txt("rm_selector_2");
        if (index == 1)
            return Diableavionics_stringsManager.txt("rm_selector");
        return null;
    }
}