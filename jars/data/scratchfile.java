/*
package data.scripts.plugins;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.MissileAIPlugin;
import com.fs.starfarer.api.combat.MissileAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.uaf_specialItemEffectsRepo;
import com.fs.starfarer.api.impl.campaign.ids.Voices;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.util.Misc;
import data.econ.listeners.RuinRestorationOptionProvider;
import data.econ.listeners.SophiaAdminAvailbilityListener;
import data.scripts.weapons.ai.uaf_ChorusAIPlugin;
import data.scripts.weapons.ai.uaf_HyperbrightFlareAI;
import data.scripts.weapons.ai.uaf_LionRoarAI;
import data.scripts.weapons.ai.uaf_MinirestAIPlugin;
import data.scripts.weapons.ai.uaf_NukeChorusAIPlugin;
import data.scripts.weapons.ai.uaf_awpBombAI;
import data.scripts.weapons.ai.uaf_phaseMineAI;
import data.scripts.world.Auroria;
import data.scripts.world.Caeli;
import data.scripts.world.Stjarna;
import data.utility.logging.logging;
import exerelin.campaign.PlayerFactionStore;
import exerelin.campaign.SectorManager;
import java.io.IOException;
import java.util.ArrayList;
import kaysaar.uaf.keycardshop.DummyCounter;
import kaysaar.uaf.keycardshop.scripts.KeycardManager;
import kaysaar.uaf.keycardshop.scripts.KeycardManagerAdvance;
import org.json.JSONArray;
import org.json.JSONObject;

public class uaf_baseModPlugin extends BaseModPlugin {
    public static final String hyperbright_flare = "uaf_hyperbright_flare";

    public static final String hyperbright_flare_g = "uaf_hyperbright_flare_green";

    public static final String hyperbright_flare_b = "uaf_hyperbright_flare_blue";

    public static final String chorus_s = "uaf_missile_chorus_s";

    public static final String chorus_nuke = "uaf_missile_chorus_nt";

    public static final String chorus_m = "uaf_missile_chorus_m";

    public static final String chorus_l = "uaf_missile_chorus_l";

    public static final String awp_bomb = "uaf_bomb_awp_1";

    public static final String keycardLady = "uaf_keycard_lady";

    public static final String uafKeycardCoints = "$uaf_keycard_coints";

    public static final String minirest = "uaf_minirest_asm";

    public static final String minirest_second = "uaf_minirest_second";

    public static final String phase_mine = "uaf_anti_phase_bomb";

    public static final String lion_roar = "uaf_lion_roar_proj2";

    public static UAFExplosionGFXData nukegfx_data;

    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();
    }

    public PluginPick<MissileAIPlugin> pickMissileAI(MissileAPI missile, ShipAPI launchingShip) {
        switch (missile.getProjectileSpecId()) {
            case "uaf_hyperbright_flare_green":
            case "uaf_hyperbright_flare_blue":
            case "uaf_hyperbright_flare":
                return new PluginPick(new uaf_HyperbrightFlareAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case "uaf_lion_roar_proj2":
                return new PluginPick(new uaf_LionRoarAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case "uaf_anti_phase_bomb":
                return new PluginPick(new uaf_phaseMineAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case "uaf_bomb_awp_1":
                return new PluginPick(new uaf_awpBombAI(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case "uaf_minirest_asm":
                return new PluginPick(new uaf_MinirestAIPlugin(missile, launchingShip), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case "uaf_missile_chorus_s":
                return new PluginPick(new uaf_ChorusAIPlugin(missile, launchingShip, "small"), CampaignPlugin.PickPriority.MOD_SPECIFIC);
            case "uaf_missile_chorus_nt":
                return new PluginPick(new uaf_NukeChorusAIPlugin(missile, launchingShip, "nuke"), CampaignPlugin.PickPriority.MOD_SPECIFIC);
        }
        return null;
    }

    public void initiateCaptains() {
        (new CERPerson()).create();
        try {
            JSONObject jsonData = Global.getSettings().loadJSON("data/config/uaf_escort_fleet.json", "uaf");
            JSONArray captainData = jsonData.getJSONArray("availableCaptains");
            for (int n = 0; n < captainData.length(); n++) {
                JSONObject captData = captainData.getJSONObject(n);
                PersonAPI captain = Global.getFactory().createPerson();
                captain.setFaction("uaf");
                captain.setId(captData.getString("captainId"));
                captain.getName().setFirst(captData.getString("captainNameFirst"));
                captain.getName().setLast(captData.getString("captainNameLast"));
                String gender = captData.getString("captainGender");
                if (gender.equals("Female")) {
                    captain.getName().setGender(FullName.Gender.FEMALE);
                } else {
                    captain.getName().setGender(FullName.Gender.MALE);
                }
                captain.setRankId(captData.getString("captainRankId"));
                captain.setPostId(captData.getString("captainPostId"));
                captain.setPortraitSprite(captData.getString("captainPhotoSprite"));
                captain.setPersonality(captData.getString("captainPersonalities"));
                captain.getStats().setSkipRefresh(true);
                JSONArray captainSkillData = captData.getJSONArray("captainSkills");
                int level = 0;
                for (int j = 0; j < captainSkillData.length(); j++) {
                    JSONObject skill = captainSkillData.getJSONObject(j);
                    captain.getStats().setSkillLevel(skill.getString("skillId"), skill.getInt("skillLevel"));
                    level += skill.getInt("skillLevel");
                }
                if (level != 0) {
                    captain.getStats().setLevel(level);
                } else {
                    captain.getStats().setLevel(1);
                }
                JSONArray captainTags = captData.getJSONArray("captainTags");
                for (int k = 0; k < captainTags.length(); k++) {
                    String tag = captainTags.getString(k);
                    captain.addTag(tag);
                }
                ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
                ip.addPerson(captain);
            }
        } catch (Exception e) {
            logging.log.info(e);
        }
    }

    public void initializeUAF() {
        initiateCaptains();
        boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
        boolean hasCSV = false;
        JSONArray temp = null;
        SectorAPI sector = Global.getSector();
        (new Auroria()).generate(sector);
        (new Stjarna()).generate(sector);
        (new Caeli()).generate(sector);
        FactionAPI uaf = sector.getFaction("uaf");
        try {
            temp = Global.getSettings().getMergedSpreadsheetDataForMod("factionId", "data/config/starting_relationships.csv", "uaf");
            hasCSV = true;
        } catch (Exception ex) {
            logging.log.info("cant get starting relationship");
            logging.log.info(ex.toString());
        }
        if (hasCSV)
            for (int i = 0; i < temp.length(); i++) {
                try {
                    JSONObject row = temp.getJSONObject(i);
                    String factionid = row.getString("factionId");
                    String startingRelation = row.getString("relationshipStart");
                    uaf.setRelationship(factionid, Float.parseFloat(startingRelation));
                } catch (Exception ex) {
                    logging.log.info(ex);
                }
            }
    }

    public void insertMayu() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        MarketAPI market = null;
        market = Global.getSector().getEconomy().getMarket("uaf_favonius_station_market");
        if (market != null) {
            PersonAPI mayu = Global.getFactory().createPerson();
            mayu.setId("uaf_mayu");
            mayu.setFaction("uaf");
            mayu.setGender(FullName.Gender.FEMALE);
            mayu.setRankId("uaf_researcher");
            mayu.setPostId("uaf_researcher");
            mayu.setImportance(PersonImportance.LOW);
            mayu.getName().setFirst("Mei");
            mayu.getName().setLast("Yu");
            mayu.setPortraitSprite("graphics/portraits/uaf_sup_mayu_128.png");
            mayu.getStats().setLevel(7);
            mayu.getStats().setSkillLevel("uaf_silverfoxWit", 2.0F);
            mayu.getStats().setSkillLevel("ballistic_mastery", 2.0F);
            mayu.getStats().setSkillLevel("energy_weapon_mastery", 2.0F);
            mayu.getStats().setSkillLevel("target_analysis", 2.0F);
            mayu.getStats().setSkillLevel("field_modulation", 1.0F);
            mayu.getStats().setSkillLevel("systems_expertise", 1.0F);
            mayu.getStats().setSkillLevel("helmsmanship", 1.0F);
            market.getCommDirectory().addPerson(mayu, 5);
            market.getCommDirectory().getEntryForPerson(mayu).setHidden(true);
            market.addPerson(mayu);
            ip.addPerson(mayu);
        }
    }

    public void insertNarumi() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        PersonAPI sophiaAshley = Global.getFactory().createPerson();
        sophiaAshley.setId("uaf_keycard_lady");
        sophiaAshley.setFaction("uaf");
        sophiaAshley.setGender(FullName.Gender.FEMALE);
        sophiaAshley.setImportance(PersonImportance.HIGH);
        sophiaAshley.setVoice(Voices.SCIENTIST);
        sophiaAshley.getName().setFirst("NRU-M1");
        sophiaAshley.setRankId("uaf_keycard_shop_title");
        sophiaAshley.setPostId("uaf_keycard_shop_title");
        sophiaAshley.setPortraitSprite(Global.getSettings().getSpriteName("uaf_person", "uaf_narumi"));
        if (!ip.containsPerson(sophiaAshley))
            ip.addPerson(sophiaAshley);
        for (MarketAPI uaf : Misc.getFactionMarkets("uaf")) {
            if (uaf.getId().equals("uaf_favonius_station_market")) {
                uaf.getCommDirectory().addPerson(sophiaAshley);
                break;
            }
        }
    }

    public void initializeListener() {
        ListenerManagerAPI l = Global.getSector().getListenerManager();
        if (!l.hasListenerOfClass(UAFEventManager.class))
            l.addListener(new UAFEventManager(true), true);
        if (!l.hasListenerOfClass(uaf_keycardLootGen.class))
            l.addListener(new uaf_keycardLootGen(), true);
        if (!l.hasListenerOfClass(uaf_baseEvent.class))
            Global.getSector().addTransientListener((CampaignEventListener)new uaf_baseEvent(false));
        if (!l.hasListenerOfClass(RuinRestorationOptionProvider.class))
            l.addListener(new RuinRestorationOptionProvider());
        if (!l.hasListenerOfClass(SophiaAdminAvailbilityListener.class))
            l.addListener(new SophiaAdminAvailbilityListener());
    }

    public void initializeBar() {}

    public void onNewGame() {
        boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
        if (!haveNexerelin || SectorManager.getManager().isCorvusMode())
            initializeUAF();
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("uaf");
        uaf_specialItemEffectsRepo.addItemEffectsToVanillaRepo();
    }

    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);
        initializeListener();
        initializeBar();
        String filename = "graphics/uaf_fonts/";
        ArrayList<Integer> fontSizes = new ArrayList<>();
        fontSizes.add(Integer.valueOf(12));
        fontSizes.add(Integer.valueOf(15));
        fontSizes.add(Integer.valueOf(18));
        fontSizes.add(Integer.valueOf(20));
        fontSizes.add(Integer.valueOf(24));
        fontSizes.add(Integer.valueOf(35));
        for (Integer fontSize : fontSizes) {
            try {
                Global.getSettings().loadFont(filename + "ACES07_Regular_" + fontSize + ".fnt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        uaf_specialItemEffectsRepo.addItemEffectsToVanillaRepo();
        ListenerManagerAPI l = Global.getSector().getListenerManager();
        if (Global.getSector().getPersistentData().get("$uaf_keycard_coints") == null) {
            Global.getSector().getPersistentData().put("$uaf_keycard_coints", new DummyCounter());
            insertNarumi();
        }
        KeycardManager.getIntance().updateManager();
        if (!Global.getSector().hasScript(KeycardManagerAdvance.class))
            Global.getSector().addScript((EveryFrameScript)new KeycardManagerAdvance());
        if (newGame &&
                "uaf".equals(PlayerFactionStore.getPlayerFactionIdNGC()))
            Global.getSector().getPlayerFleet().getCargo().addSpecial(new SpecialItemData("uaf_keycard_violet", (String)null), 1.0F);
    }

    public void onNewGameAfterTimePass() {
        uaf_specialItemEffectsRepo.addItemEffectsToVanillaRepo();
        Global.getSector().addScript(new uaf_manipulator());
        insertMayu();
    }
}
*/