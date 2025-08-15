package data.missions.cer_EX10Goblin;

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
		api.setFleetTagline(FleetSide.PLAYER, "Just Ace Pilot Captain Sideways");
		api.setFleetTagline(FleetSide.ENEMY, "Hedgemony Fast Reponse Fleet");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("The CESN Goblin Must Survive.");
		api.addBriefingItem("Cull the Herd and Avoid the Bigger Ships.");
		api.addBriefingItem("DO not Chase, you'll get Sorrunded and put down.");
		api.addBriefingItem("use your Kinetic Gatling and Artassults to Destroy Missiles.");
		
		// Set up the player's fleet.  Variant names come from the
		// files in data/variants and data/variants/fighters
		
				//Standard Ships
				api.addToFleet(FleetSide.PLAYER, "diable_goblin_EX_Flagship", FleetMemberType.SHIP,"CESN Goblin", true);
				
		// Mark both ships as essential - losing either one results
		// in mission failure. Could also be set on an enemy ship,
		// in which case destroying it would result in a win.
		api.defeatOnShipLoss("CESN Goblin");
		
		// Set up the enemy fleet.
		//api.addToFleet(FleetSide.ENEMY, "eagle_Assault", FleetMemberType.SHIP, false);		
		//api.addToFleet(FleetSide.ENEMY, "onslaught_Outdated", FleetMemberType.SHIP, false);
		
                api.addToFleet(FleetSide.ENEMY, "enforcer_XIV_Elite", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "enforcer_XIV_Elite", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "lasher_Standard", FleetMemberType.SHIP, false);	
                api.addToFleet(FleetSide.ENEMY, "lasher_Standard", FleetMemberType.SHIP, false);	            
                api.addToFleet(FleetSide.ENEMY, "lasher_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "hound_hegemony_Standard", FleetMemberType.SHIP, false);	
                api.addToFleet(FleetSide.ENEMY, "hound_hegemony_Standard", FleetMemberType.SHIP, false);	            
                api.addToFleet(FleetSide.ENEMY, "hound_hegemony_Standard", FleetMemberType.SHIP, false);				
                

		// Set up the map.
		float width = 24000f;
		float height = 14000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		float minX = -width/2;
		float minY = -height/2;
		
		// All the addXXX methods take a pair of coordinates followed by data for
		// whatever object is being added.
		
		// Add two big nebula clouds
		api.addNebula(minX + width * 0.75f, minY + height * 0.5f, 2000);
		api.addNebula(minX + width * 0.25f, minY + height * 0.5f, 1000);
		
		// And a few random ones to spice up the playing field.
		// A similar approach can be used to randomize everything
		// else, including fleet composition.
		for (int i = 0; i < 5; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 400f; 
			api.addNebula(x, y, radius);
		}
		
		// Add objectives. These can be captured by each side
		// and provide stat bonuses and extra command points to
		// bring in reinforcements.
		// Reinforcements only matter for large fleets - in this
		// case, assuming a 100 command point battle size,
		// both fleets will be able to deploy fully right away.
		api.addObjective(minX + width * 0.25f + 3000, minY + height * 0.5f, 
						 "nav_buoy");
		api.addObjective(minX + width * 0.75f - 3000, minY + height * 0.5f, 
						 "sensor_array");
	}

}
