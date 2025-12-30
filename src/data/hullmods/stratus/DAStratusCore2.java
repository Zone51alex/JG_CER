package data.hullmods.stratus;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import org.lazywizard.lazylib.MathUtils;
import org.magiclib.util.MagicIncompatibleHullmods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static data.scripts.util.Diableavionics_stringsManager.txt;

public class DAStratusCore2 extends BaseHullMod {
    private final Map<Integer,String> MAIN_SELECTOR = populateMainSelector();
    private final Map<String, Integer> SWITCH_TO = populateSwitchTo();
    private final Map<Integer,String> SWITCH = populateSwitch();
    private final Set<String> BLOCKED_HULLMODS = new HashSet<>();
    {
        // These hullmods will automatically be removed
        // This prevents unexplained hullmod blocking
        BLOCKED_HULLMODS.add("safetyoverrides");
    }

    private HashMap<Integer, String> populateMainSelector() {
        HashMap<Integer, String> retVal = new HashMap();
        retVal.put(0, "diable_microbuiltin");
        retVal.put(1, "diable_magicboxbuiltin");
        retVal.put(2, "diable_reioibuiltin");
        return retVal;
    }

    private HashMap<String, Integer> populateSwitchTo() {
        HashMap<String, Integer> retVal = new HashMap();
        retVal.put("diable_microbuiltin",1);
        retVal.put("diable_magicboxbuiltin",2);
        retVal.put("diable_reioibuiltin",0);
        return retVal;
    }

    private HashMap<Integer, String> populateSwitch() {
        HashMap<Integer, String> retVal = new HashMap();
        retVal.put(0, "diable_selector_Micro");
        retVal.put(1, "diable_selector_Magicbox");
        retVal.put(2, "diable_selector_Reioi");
        return retVal;
    }

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

        //trigger a weapon switch if none of the selector hullmods are present
        boolean toSwitch=true;
        for(int i=0; i<SWITCH.size(); i++){
            if(stats.getVariant().getHullMods().contains(SWITCH.get(i))){
                toSwitch=false;
            }
        }

        //remove the weapons to change and swap the hullmod for the next fire mode
        if(toSwitch){
            //select new fire mode
            int selected;
            boolean random=false;
            String topleftslotID = "BUILTIN_A";
            String toprightslotID = "BUILTIN_B";
            String bottomleftslotID = "BUILTIN_C";
            String bottomrightslotID = "BUILTIN_D";
            if(stats.getVariant().getWeaponSpec(topleftslotID)!=null){
                selected=SWITCH_TO.get(stats.getVariant().getWeaponSpec(topleftslotID).getWeaponId());
            } else {
                selected=MathUtils.getRandomNumberInRange(0, SWITCH_TO.size()-1);
                random=true;
            }

            //add the proper hullmod
            stats.getVariant().addMod(SWITCH.get(selected));

            //clear the weapons to replace
            stats.getVariant().clearSlot(topleftslotID);
            stats.getVariant().clearSlot(toprightslotID);
            stats.getVariant().clearSlot(bottomleftslotID);
            stats.getVariant().clearSlot(bottomrightslotID);

            //String toInstallLeft = MAIN_SELECTOR.get(selected);
            String toInstallRight=MAIN_SELECTOR.get(selected);

            stats.getVariant().addWeapon(topleftslotID, toInstallRight);
            stats.getVariant().addWeapon(toprightslotID, toInstallRight);
            stats.getVariant().addWeapon(bottomleftslotID, toInstallRight);
            stats.getVariant().addWeapon(bottomrightslotID, toInstallRight);

            if(random){
                stats.getVariant().autoGenerateWeaponGroups();
            }
        }
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id){

        //blocked hullmods
        for (String tmp : BLOCKED_HULLMODS) {
            if (ship.getVariant().getHullMods().contains(tmp)) {
                MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "diable_stratus_core");
            }
        }

        //only check for undo in refit to avoid issues
        if(ship.getOriginalOwner()<0){
            //undo fix for harvests put in cargo
            if(
                    Global.getSector()!=null &&
                    Global.getSector().getPlayerFleet()!=null &&
                    Global.getSector().getPlayerFleet().getCargo()!=null &&
                    Global.getSector().getPlayerFleet().getCargo().getStacksCopy()!=null &&
                    !Global.getSector().getPlayerFleet().getCargo().getStacksCopy().isEmpty()
                    ){
                for (CargoStackAPI s : Global.getSector().getPlayerFleet().getCargo().getStacksCopy()){
                    if(
                            s.isWeaponStack() && (
                                MAIN_SELECTOR.containsValue(s.getWeaponSpecIfWeapon().getWeaponId())
                                )
                            ){
                        Global.getSector().getPlayerFleet().getCargo().removeStack(s);
                    }
                }
            }
        }
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return txt("hm_warning");
        if (index == 1) return Global.getSettings().getHullModSpec("safetyoverrides").getDisplayName();
        return null;
    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        // Allows any ship with a diableavionics hull id
        return ( ship.getHullSpec().getHullId().startsWith("diable_"));
    }
}