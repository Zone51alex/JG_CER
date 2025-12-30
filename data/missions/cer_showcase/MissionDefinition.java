package data.missions.cer_showcase;

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
		api.initFleet(FleetSide.ENEMY, "DSF", FleetGoal.ATTACK, true);

		// Set a small blurb for each fleet that shows up on the mission detail and
		// mission results screens to identify each side.
		api.setFleetTagline(FleetSide.PLAYER, "Main Showcase of Corvid Ships");
		api.setFleetTagline(FleetSide.ENEMY, "Romanced DSF Fleet");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Enjoy the Crazy Battle man.");
		
		// Set up the player's fleet.  Variant names come from the
		// files in data/variants and data/variants/fighters
				//EX Ships
				if(Global.getSettings().isDevMode()){
                api.addToFleet(FleetSide.PLAYER, "diable_pandemonium_EX_Flagship", FleetMemberType.SHIP,"CESN Flying Dutchman", false);
				api.addToFleet(FleetSide.PLAYER, "diable_pandemonium_EX2_Flagship", FleetMemberType.SHIP,"IDSS Starbound", false);
                api.addToFleet(FleetSide.PLAYER, "diable_chieftain_EX_Flagship", FleetMemberType.SHIP,"CESN Black Pearl", false);
                api.addToFleet(FleetSide.PLAYER, "diable_storm_EX_Flagship", FleetMemberType.SHIP,"CESN Gold Ship", false);
				api.addToFleet(FleetSide.PLAYER, "diable_daze_EX_Flagship", FleetMemberType.SHIP,"CESN Merkava", false);
				api.addToFleet(FleetSide.PLAYER, "diable_gust_EX_Flagship", FleetMemberType.SHIP,"CESN Marauder", true);				
                api.addToFleet(FleetSide.PLAYER, "diable_gust_EX2_Flagship", FleetMemberType.SHIP,"CESN Khrizantema", false);	            
                api.addToFleet(FleetSide.PLAYER, "diable_riptide_EX_Flagship", FleetMemberType.SHIP,"DSF Star Platinum", false);	           
                api.addToFleet(FleetSide.PLAYER, "diable_calm_EX_Flagship", FleetMemberType.SHIP,"CESN Railjack", false);				
				api.addToFleet(FleetSide.PLAYER, "diable_vapor_EX_Flagship", FleetMemberType.SHIP,"CESN Arise", false);
				api.addToFleet(FleetSide.PLAYER, "diable_versant_EX_Flagship", FleetMemberType.SHIP,"CESN Starlight", false);
				api.addToFleet(FleetSide.PLAYER, "diable_goblin_EX_Flagship", FleetMemberType.SHIP,"CESN Goblin", false);
				}
				//Standard Ships
				api.addToFleet(FleetSide.PLAYER, "diable_chieftain_cer_Standard", FleetMemberType.SHIP,"CESN Valkyrie", true);
				api.addToFleet(FleetSide.PLAYER, "diable_chieftain_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_gust_cer_Assault", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_daze_cer_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_haze_cer_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_storm_cer_Standard", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.PLAYER, "diable_fractalus_Assault", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_rime_m_cer_Advanced", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_miniGust_cer_Support", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.PLAYER, "diable_fractus_cer_Bomber", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.PLAYER, "diable_burst_Standard", FleetMemberType.SHIP,"CESN Burst", false);
				api.addToFleet(FleetSide.PLAYER, "diable_stratus_cer_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_calm_cer_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_versant_cer_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_vapor_cer_Close_Quarter", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_eagle_C_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_raven_C_Standard", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.PLAYER, "diable_valiant_C_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_zephyr_C_Standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_brisk_cer_Energy", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.PLAYER, "diable_coanda_cer_Energy", FleetMemberType.SHIP, false);
				
		// Mark both ships as essential - losing either one results
		// in mission failure. Could also be set on an enemy ship,
		// in which case destroying it would result in a win.
		//api.defeatOnShipLoss("DSF Black Pearl");
		
		// Set up the enemy fleet.
		//api.addToFleet(FleetSide.ENEMY, "eagle_Assault", FleetMemberType.SHIP, false);		
		//api.addToFleet(FleetSide.ENEMY, "onslaught_Outdated", FleetMemberType.SHIP, false);	
				
				api.addToFleet(FleetSide.ENEMY, "diableavionics_versant_standard", FleetMemberType.SHIP, true);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_pandemonium_willBreaker", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_pandemonium_willBreaker", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_maelstrom_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_maelstrom_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_maelstrom_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_maelstrom_standard", FleetMemberType.SHIP, false);				
                api.addToFleet(FleetSide.ENEMY, "diableavionics_storm_standard", FleetMemberType.SHIP, false);	   
				api.addToFleet(FleetSide.ENEMY, "diableavionics_storm_standard", FleetMemberType.SHIP, false);				
                api.addToFleet(FleetSide.ENEMY, "diableavionics_gust_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_gust_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_gust_standard", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_haze_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_haze_standard", FleetMemberType.SHIP, false);				
                api.addToFleet(FleetSide.ENEMY, "diableavionics_daze_combat", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_daze_combat", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_rime_m_standard", FleetMemberType.SHIP, false); 
				api.addToFleet(FleetSide.ENEMY, "diableavionics_rime_m_standard", FleetMemberType.SHIP, false); 
				api.addToFleet(FleetSide.ENEMY, "diableavionics_rime_m_standard", FleetMemberType.SHIP, false); 
				api.addToFleet(FleetSide.ENEMY, "diableavionics_rime_m_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_fractus_support", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_fractus_support", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_fractus_support", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_fractus_support", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_miniGust_assault", FleetMemberType.SHIP, false);	             
                api.addToFleet(FleetSide.ENEMY, "diableavionics_calm_standard", FleetMemberType.SHIP, false);                     
                api.addToFleet(FleetSide.ENEMY, "diableavionics_coanda_standard", FleetMemberType.SHIP, false);              
                api.addToFleet(FleetSide.ENEMY, "diableavionics_derecho_standard", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_hayle_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_hayle_standard", FleetMemberType.SHIP, false);				
                api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_draft_standard", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_vapor_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_vapor_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_vapor_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_vapor_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_vapor_standard", FleetMemberType.SHIP, false);
                api.addToFleet(FleetSide.ENEMY, "diableavionics_sleet_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_sleet_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_sleet_standard", FleetMemberType.SHIP, false);
				api.addToFleet(FleetSide.ENEMY, "diableavionics_sleet_standard", FleetMemberType.SHIP, false);
				
			if(Global.getSettings().isDevMode()){    
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_IBBgulf_boss", FleetMemberType.SHIP, false);               
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_virtuous_grenadier", FleetMemberType.SHIP, false);       
                    
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_chinook_standard", FleetMemberType.SHIP, false);	                
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_cirrus_standard", FleetMemberType.SHIP, false);	               
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_stratus_standard", FleetMemberType.SHIP, false);               
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_stratus_p_combat", FleetMemberType.SHIP, false);              
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_rime_standard", FleetMemberType.SHIP, false);                 
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_rime_p_support", FleetMemberType.SHIP, false);   
                                    
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_laminar_miner", FleetMemberType.SHIP, false);                  
                    api.addToFleet(FleetSide.ENEMY, "diableavionics_shear_standard", FleetMemberType.SHIP, false);  

                }
		
		// Set up the map.
		float width = 24000f;
		float height = 18000f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		float minX = -width/2;
		float minY = -height/2;
		
		for (int i = 0; i < 15; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 900f; 
			api.addNebula(x, y, radius);
		}
		
		api.addNebula(minX + width * 0.8f - 1000, minY + height * 0.4f, 2000);
		api.addNebula(minX + width * 0.8f - 1000, minY + height * 0.5f, 2000);
		api.addNebula(minX + width * 0.8f - 1000, minY + height * 0.6f, 2000);
		
		api.addObjective(minX + width * 0.8f - 1000, minY + height * 0.4f, "nav_buoy");
		api.addObjective(minX + width * 0.8f - 1000, minY + height * 0.6f, "nav_buoy");
		api.addObjective(minX + width * 0.3f + 1000, minY + height * 0.3f, "comm_relay");
		api.addObjective(minX + width * 0.3f + 1000, minY + height * 0.7f, "comm_relay");
		api.addObjective(minX + width * 0.5f, minY + height * 0.5f, "sensor_array");
		api.addObjective(minX + width * 0.2f + 1000, minY + height * 0.5f, "sensor_array");
		
		// Add an asteroid field
		api.addAsteroidField(minX + width * 0.3f, minY, 90, 3000f,
								20f, 70f, 50);
		
		// Add some planets.  These are defined in data/config/planets.json.
		api.addPlanet(0, 0, 200f, "barren", 350f, true);
		
	}
}