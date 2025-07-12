package data.hullmods.versant;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import java.util.HashMap;
import java.util.Map;
import org.lazywizard.lazylib.MathUtils;
import static data.scripts.util.Diableavionics_stringsManager.txt;
import org.magiclib.util.MagicIncompatibleHullmods;
import java.util.HashSet;
import java.util.Set;

public class DATuningEX extends BaseHullMod {
//    private static float debuff=0;

//    private static final Map<String,Float> HULLMOD_DEBUFF = new HashMap<>();
//    static{
//        HULLMOD_DEBUFF.put("safetyoverrides",0.2f);
////        HULLMOD_DEBUFF.put("unstable_injector",0.15f);
////        HULLMOD_DEBUFF.put("auxiliarythrusters",0.15f);
////        HULLMOD_DEBUFF.put("SCY_lightArmor",0.15f);
//    }
    private  final Set<String> BLOCKED_HULLMODS = new HashSet<>();
    {
        // These hullmods will automatically be removed
        // This prevents unexplained hullmod blocking
        BLOCKED_HULLMODS.add("safetyoverrides");
    }

    private final Map<Integer,String> LEFT_SELECTOR = new HashMap<>();
    {
        LEFT_SELECTOR.put(0, "diableavionics_versant_EX_harvest_LEFT");
        LEFT_SELECTOR.put(1, "diableavionics_versant_EX_harvestB_LEFT");
        LEFT_SELECTOR.put(2, "diableavionics_versant_EX_harvestC_LEFT");
    }

    private final Map<Integer,String> RIGHT_SELECTOR = new HashMap<>();
    {
        RIGHT_SELECTOR.put(0, "diableavionics_versant_EX_harvest_RIGHT");
        RIGHT_SELECTOR.put(1, "diableavionics_versant_EX_harvestB_RIGHT");
        RIGHT_SELECTOR.put(2, "diableavionics_versant_EX_harvestC_RIGHT");
    }

    private final Map<String, Integer> SWITCH_TO = new HashMap<>();
    {
        SWITCH_TO.put("diableavionics_versant_EX_harvest_LEFT",1);
        SWITCH_TO.put("diableavionics_versant_EX_harvestB_LEFT",2);
        SWITCH_TO.put("diableavionics_versant_EX_harvestC_LEFT",0);
    }

    private final Map<Integer,String> SWITCH = new HashMap<>();
    {
        SWITCH.put(0,"diableavionics_selector_auto_EX");
        SWITCH.put(1,"diableavionics_selector_burst_EX");
        SWITCH.put(2,"diableavionics_selector_semi_EX");
    }

    private final String leftslotID = "GUN_LEFT";
    private final String rightslotID = "GUN_RIGHT";

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
//        debuff=0;
//        for(String h : stats.getVariant().getHullMods()){
//            if(HULLMOD_DEBUFF.containsKey(h)){
//                debuff+=HULLMOD_DEBUFF.get(h);
//            }
//        }
//        stats.getPeakCRDuration().modifyMult(id,1-debuff);

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
            if(stats.getVariant().getWeaponSpec(leftslotID)!=null){
                selected=SWITCH_TO.get(stats.getVariant().getWeaponSpec(leftslotID).getWeaponId());

            } else {
                selected=MathUtils.getRandomNumberInRange(0, SWITCH_TO.size()-1);
                random=true;
            }

            //add the proper hullmod
            stats.getVariant().addMod(SWITCH.get(selected));

            //clear the weapons to replace
            stats.getVariant().clearSlot(leftslotID);
            stats.getVariant().clearSlot(rightslotID);
            //select and place the proper weapon
            String toInstallLeft=LEFT_SELECTOR.get(selected);
            String toInstallRight=RIGHT_SELECTOR.get(selected);

            stats.getVariant().addWeapon(leftslotID, toInstallLeft);
            stats.getVariant().addWeapon(rightslotID, toInstallRight);

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
                MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "diableavionics_tuning");
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
                                LEFT_SELECTOR.containsValue(s.getWeaponSpecIfWeapon().getWeaponId()) ||
                                RIGHT_SELECTOR.containsValue(s.getWeaponSpecIfWeapon().getWeaponId())
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
        return ( ship.getHullSpec().getHullId().startsWith("diableavionics_"));
    }
/*
    private final Set<String> BLOCKED_HULLMODS;

    private final Map<Integer, String> LEFT_SELECTOR;

    private final Map<Integer, String> RIGHT_SELECTOR;

    private final Map<String, Integer> SWITCH_TO;

    private final Map<Integer, String> SWITCH;

    private String leftslotID = "GUN_LEFT";

    private String rightslotID = "GUN_RIGHT";

    public DATuningEX() {
        this.BLOCKED_HULLMODS = new HashSet<>();
        this.BLOCKED_HULLMODS.add("safetyoverrides");
        this.LEFT_SELECTOR = new HashMap<>();
        this.LEFT_SELECTOR.put(Integer.valueOf(0), "diableavionics_versant_EX_harvest_LEFT");
        this.LEFT_SELECTOR.put(Integer.valueOf(1), "diableavionics_versant_EX_harvestB_LEFT");
        this.LEFT_SELECTOR.put(Integer.valueOf(2), "diableavionics_versant_EX_harvestC_LEFT");
        this.RIGHT_SELECTOR = new HashMap<>();
        this.RIGHT_SELECTOR.put(Integer.valueOf(0), "diableavionics_versant_EX_harvest_RIGHT");
        this.RIGHT_SELECTOR.put(Integer.valueOf(1), "diableavionics_versant_EX_harvestB_RIGHT");
        this.RIGHT_SELECTOR.put(Integer.valueOf(2), "diableavionics_versant_EX_harvestC_RIGHT");
        this.SWITCH_TO = new HashMap<>();
        this.SWITCH_TO.put("diableavionics_versant_EX_harvest_LEFT", Integer.valueOf(1));
        this.SWITCH_TO.put("diableavionics_versant_EX_harvestB_LEFT", Integer.valueOf(2));
        this.SWITCH_TO.put("diableavionics_versant_EX_harvestC_LEFT", Integer.valueOf(0));
        this.SWITCH = new HashMap<>();
        this.SWITCH.put(Integer.valueOf(0), "diableavionics_selector_auto_EX");
        this.SWITCH.put(Integer.valueOf(1), "diableavionics_selector_burst_EX");
        this.SWITCH.put(Integer.valueOf(2), "diableavionics_selector_semi_EX");
        this.leftslotID = "GUN_LEFT";
        this.rightslotID = "GUN_RIGHT";
    }

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        boolean toSwitch = true;
        for (int i = 0; i < this.SWITCH.size(); i++) {
            if (stats.getVariant().getHullMods().contains(this.SWITCH.get(Integer.valueOf(i))))
                toSwitch = false;
        }
        if (toSwitch) {
            int selected;
            boolean random = false;
            if (stats.getVariant().getWeaponSpec("GUN_LEFT") != null) {
                selected = ((Integer) this.SWITCH_TO.get(stats.getVariant().getWeaponSpec("GUN_LEFT").getWeaponId())).intValue();
            } else {
                selected = MathUtils.getRandomNumberInRange(0, this.SWITCH_TO.size() - 1);
                random = true;
            }
            stats.getVariant().addMod(this.SWITCH.get(Integer.valueOf(selected)));
            stats.getVariant().clearSlot("GUN_LEFT");
            stats.getVariant().clearSlot("GUN_RIGHT");
            String toInstallLeft = this.LEFT_SELECTOR.get(Integer.valueOf(selected));
            String toInstallRight = this.RIGHT_SELECTOR.get(Integer.valueOf(selected));
            stats.getVariant().addWeapon("GUN_LEFT", toInstallLeft);
            stats.getVariant().addWeapon("GUN_RIGHT", toInstallRight);
            if (random)
                stats.getVariant().autoGenerateWeaponGroups();
        }
    }

    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        for (String tmp : this.BLOCKED_HULLMODS) {
            if (ship.getVariant().getHullMods().contains(tmp))
                MagicIncompatibleHullmods.removeHullmodWithWarning(ship.getVariant(), tmp, "diableavionics_tuning_EX");
        }
        if (ship.getOriginalOwner() < 0)
            if (Global.getSector() != null &&
                    Global.getSector().getPlayerFleet() != null &&
                    Global.getSector().getPlayerFleet().getCargo() != null &&
                    Global.getSector().getPlayerFleet().getCargo().getStacksCopy() != null &&
                    !Global.getSector().getPlayerFleet().getCargo().getStacksCopy().isEmpty())
                for (CargoStackAPI s : Global.getSector().getPlayerFleet().getCargo().getStacksCopy()) {
                    if (s
                            .isWeaponStack() && (this.LEFT_SELECTOR
                            .containsValue(s.getWeaponSpecIfWeapon().getWeaponId()) || this.RIGHT_SELECTOR
                            .containsValue(s.getWeaponSpecIfWeapon().getWeaponId())))
                        Global.getSector().getPlayerFleet().getCargo().removeStack(s);
                }
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
            return Diableavionics_stringsManager.txt("hm_warning");
        if (index == 1)
            return Global.getSettings().getHullModSpec("safetyoverrides").getDisplayName();
        return null;
    }

    public boolean isApplicableToShip(ShipAPI ship) {
        return ship.getHullSpec().getHullId().startsWith("diableavionics_");
    }
*/
}