package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static data.scripts.util.Diableavionics_stringsManager.txt;

public class ArbitratorGantry extends BaseHullMod {
    private static final String GANTRY_ID = "diableavionics_compactdecksExtra";
    private final Color HL = Global.getSettings().getColor("hColor");
    private final String ID = "arbitrator_gantry", TAG = "arbitrator";

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return txt("hm_gantry_101");
        }
        return null;
    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        //title
        tooltip.addSectionHeading(txt("hm_gantry_102"), Alignment.MID, 15);

        if (ship != null && ship.getVariant() != null) {
            if (ship.getVariant().getFittedWings().isEmpty()) {
                //no wing fitted
                tooltip.addPara(
                        txt("hm_gantry_103")
                        , 10
                        , HL
                );
            } else if (noAbitrator(ship.getVariant())) {
                //no arbitrator wings installed
                tooltip.addPara(
                        txt("hm_gantry_104")
                        , 10
                        , HL
                );
            } else {
                //effect applied
                List<String> arbitrators = allArbitrators(ship.getVariant());

                if (!arbitrators.isEmpty()) {
                    tooltip.addPara(
                            txt("hm_gantry_105")
                            , 10
                            , HL
                            , txt("hm_gantry_105hl")
                    );

                    tooltip.setBulletedListMode("    - ");

                    for (String w : arbitrators) {
                        tooltip.addPara(
                                w
                                , 3
                        );
                    }
                    tooltip.setBulletedListMode(null);
                }
            }
        }
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {




        if (ship.getOriginalOwner() == -1) {
            return; //suppress in refit
        }

        boolean allDeployed = true, ranOnce = false;

        for (FighterLaunchBayAPI bay : ship.getLaunchBaysCopy()) {
            if (bay.getWing() != null) {
                ranOnce = true;
                if (bay.getWing().getSpec().hasTag(TAG)) {

                    FighterWingSpecAPI wingSpec = bay.getWing().getSpec();
                    int deployed = bay.getWing().getWingMembers().size();
                    int maxTotal = wingSpec.getNumFighters() + 1;
                    int actualAdd = maxTotal - deployed;

                    if (actualAdd > 0) {
                        bay.setExtraDeployments(actualAdd);
                        bay.setExtraDeploymentLimit(maxTotal);
                        bay.setExtraDuration(9999999);
                        allDeployed = false;
                    } else {
                        bay.setExtraDeployments(0);
                        bay.setExtraDeploymentLimit(0);
                        bay.setFastReplacements(0);
                    }

                    if (ship.getMutableStats().getFighterRefitTimeMult().getPercentStatMod(ID) == null && actualAdd != 0) {
                        //instantly add all the required fighters upon deployment
                        bay.setFastReplacements(actualAdd);
                    }


                    //debug
//                    Global.getCombatEngine().addFloatingText(
//                            bay.getWeaponSlot().computePosition(ship),
//                            "add= "+bay.getExtraDeployments()+" max= "+bay.getExtraDeploymentLimit()+" fast= "+bay.getFastReplacements(),
//                            10, Color.ORANGE, ship, 1, 1);
                }
            }
        }

        if (ship.getMutableStats().getFighterRefitTimeMult().getPercentStatMod(ID) == null && allDeployed && ranOnce) {
            //used as a check to add all the extra fighters upon deployment
            ship.getMutableStats().getFighterRefitTimeMult().modifyPercent(ID, 1);
        }
    }

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        //reset the "check" mutable stat so that it is applied next deployment
        stats.getFighterRefitTimeMult().unmodify(ID);
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        //the extra crafts do not deplete the fighter replacement gauge when destroyed, this makes the rate deplete faster from those that do to compensate.
        Integer crafts = 0, extraCrafts = 0;
        for (String w : ship.getVariant().getFittedWings()) {
            if (Global.getSettings().getFighterWingSpec(w).hasTag(TAG)) {
                crafts += Global.getSettings().getFighterWingSpec(w).getNumFighters();
                extraCrafts++;
            }
        }
        if (extraCrafts > 0) {
            ship.getMutableStats().getDynamic().getMod(Stats.REPLACEMENT_RATE_DECREASE_MULT).modifyMult(id, (crafts + extraCrafts) / crafts);
        }
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        return txt("hm_noBays");
    }

    private boolean noAbitrator(ShipVariantAPI variant) {
        boolean noAbitrator = true;
        for (String w : variant.getFittedWings()) {
            if (Global.getSettings().getFighterWingSpec(w).hasTag(TAG)) {
                noAbitrator = false;
                break;
            }
        }
        return noAbitrator;
    }

    private List<String> allArbitrators(ShipVariantAPI variant) {
        List<String> allArbitrators = new ArrayList<>();
        for (String w : variant.getFittedWings()) {
            FighterWingSpecAPI f = Global.getSettings().getFighterWingSpec(w);
            if (f.hasTag(TAG)) {
                allArbitrators.add(f.getWingName() + " " + f.getRoleDesc());
            }
        }
        return allArbitrators;
    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        if (ship == null) return false;
        return ship.getVariant().hasHullMod(GANTRY_ID);
    }

    @Override
    public boolean showInRefitScreenModPickerFor(ShipAPI ship) {
        return ship.getVariant().hasHullMod(GANTRY_ID);
    }

}
