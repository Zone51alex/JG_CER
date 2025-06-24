package data.hullmods.versant;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.util.Diableavionics_stringsManager;
public class DAVersantBurst extends BaseHullMod {
    public int getDisplaySortOrder() { return 2000; }

    public int getDisplayCategoryIndex() { return 3; }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
            return Diableavionics_stringsManager.txt("hm_selector_1");
        if (index == 1)
            return Diableavionics_stringsManager.txt("hm_selector");
        return null;
    }
}
