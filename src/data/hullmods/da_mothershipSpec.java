package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatFleetManagerAPI;
import com.fs.starfarer.api.combat.DeployedFleetMemberAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;

public class da_mothershipSpec extends BaseHullMod {

    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getDynamic().getMod("deployment_points_mod").modifyMult("deployment_points_mod",0.5F);
        stats.getDynamic().getMod("coord_maneuvers_flat").modifyFlat(id, 5.0F);
        stats.getDynamic().getMod("ground_support").modifyFlat(id, 300.0F);
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0) return "5%";
        if (index == 1) return "300";
        if (index == 2) return "300%";
        if (index == 3) return "50%";
        return null;
        }

        public void advanceInCombat(ShipAPI ship, float amount) {
            CombatEngineAPI engine = Global.getCombatEngine();
            if (engine == null)
                return;
            CombatFleetManagerAPI manager = engine.getFleetManager(ship.getOriginalOwner());
            if (manager == null)
                return;
            DeployedFleetMemberAPI member = manager.getDeployedFleetMember(ship);
            if (member == null)
                return;
            boolean apply = (ship == engine.getPlayerShip());
            PersonAPI commander = null;
            if (member.getMember() != null) {
                commander = member.getMember().getFleetCommander();
                if (member.getMember().getFleetCommanderForStats() != null) {
                    commander = member.getMember().getFleetCommanderForStats();
                }
            }
            apply |= commander != null && ship.getCaptain() == commander;
            if (apply) {
                ship.getMutableStats().getDynamic().getMod("command_point_rate_flat").modifyFlat("operations_center_mod", 3.0F);

            } else {
                ship.getMutableStats().getDynamic().getMod("command_point_rate_flat").unmodify("operations_center_mod");
            }
        }
}