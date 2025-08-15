package data.missions.cer_EX1Gust;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {
    @Override
	public void defineMission(MissionDefinitionAPI api) {

	
		// Set up the fleets so we can add ships and fighter wings to them.
		// In this scenario, the fleets are attacking each other, but
		// in other scenarios, a fleet may be defending or trying to escape
		api.initFleet(FleetSide.PLAYER, "CESN", FleetGoal.ATTACK, true);
		api.initFleet(FleetSide.ENEMY, "HSS", FleetGoal.ATTACK, true);

		// Set a small blurb for each fleet that shows up on the mission detail and
		// mission results screens to identify each side.
		api.setFleetTagline(FleetSide.PLAYER, "Defense Fleet Gamma with Grand Engineer Taio");
		api.setFleetTagline(FleetSide.ENEMY, "Hegemony fleet under Commodore Jensulte");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("The CESN Marauder Must Survive.");
		api.addBriefingItem("The CESN Callaban Will Command the Fleet For you, Focus on piloting.");
		
		// Set up the player's fleet.  Variant names come from the
		// files in data/variants and data/variants/fighters
		
				api.addToFleet(FleetSide.PLAYER, "diable_gust_EX_Flagship", FleetMemberType.SHIP,"CESN Marauder", true);

				api.addToFleet(FleetSide.PLAYER, "diableavionics_daze_AntiFighter", FleetMemberType.SHIP,"CESN Callaban", false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_rime_m_Standard1b", FleetMemberType.SHIP,"CESN Marco", false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_rime_m_Standard1b", FleetMemberType.SHIP,"CESN Polo", false);
				//api.addToFleet(FleetSide.PLAYER, "diable_fractalus_Assault", FleetMemberType.SHIP, false);
				//api.addToFleet(FleetSide.PLAYER, "diable_fractalus_Assault", FleetMemberType.SHIP, false);
				//api.addToFleet(FleetSide.PLAYER, "diable_burst_Standard", FleetMemberType.SHIP, false);
				//api.addToFleet(FleetSide.PLAYER, "diable_burst_Standard", FleetMemberType.SHIP, false);
			    api.addToFleet(FleetSide.PLAYER, "diableavionics_miniGust_Close_S.(CER)", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_miniGust_Close_S.(CER)", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_fractus_support", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_fractus_support", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_vapor_Brawler(CER)", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.PLAYER, "diableavionics_vapor_Attack(CER)", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.PLAYER, "diableavionics_vapor_Attack(CER)", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_sleet_Escort(CER)", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diableavionics_sleet_Escort(CER)", FleetMemberType.SHIP, false);
				
				
		// Mark both ships as essential - losing either one results
		// in mission failure. Could also be set on an enemy ship,
		// in which case destroying it would result in a win.
		api.defeatOnShipLoss("CESN Marauder");
		
				// Set up the enemy fleet
		//api.addToFleet(FleetSide.ENEMY, "onslaught_Standard", FleetMemberType.SHIP, "HSS Naga", true);
		//api.addToFleet(FleetSide.ENEMY, "onslaught_Outdated", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "onslaught_Outdated", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "dominator_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "dominator_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "mora_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "mora_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "mora_Strike", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "mora_Support", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "dominator_Support", FleetMemberType.SHIP, false);
		//api.addToFleet(FleetSide.ENEMY, "dominator_Support", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "condor_Support", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "condor_Support", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "enforcer_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "enforcer_Assault", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "enforcer_CS", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "enforcer_CS", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "hound_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "hound_Standard", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);
		api.addToFleet(FleetSide.ENEMY, "lasher_CS", FleetMemberType.SHIP, false);			
                

		// Set up the map.
		float width = 18000f;
		float height = 24000f;
		
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		float minX = -width/2;
		float minY = -height/2;
		
		for (int i = 0; i < 15; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 900f; 
			api.addNebula(x, y, radius);
		}
		
		api.addNebula(minX + width * 0.8f, minY + height * 0.4f, 2000);
		api.addNebula(minX + width * 0.8f, minY + height * 0.5f, 2000);
		api.addNebula(minX + width * 0.8f, minY + height * 0.6f, 2000);
		
		api.addObjective(minX + width * 0.8f, minY + height * 0.4f, "sensor_array");
		api.addObjective(minX + width * 0.8f, minY + height * 0.6f, "nav_buoy");
		api.addObjective(minX + width * 0.3f, minY + height * 0.3f, "nav_buoy");
		api.addObjective(minX + width * 0.3f, minY + height * 0.7f, "sensor_array");
		api.addObjective(minX + width * 0.2f, minY + height * 0.5f, "comm_relay");

		api.addAsteroidField(minX + width * 0.5f, minY + height, 270, width,
								20f, 70f, 50);
		
		api.addPlanet(0, 0, 200f, "barren", 0f, true);
		
	}

}
