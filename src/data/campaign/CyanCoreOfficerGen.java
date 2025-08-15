package data.campaign;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.loading.Description;
import data.campaign.ids.cer_ids;

import static com.fs.starfarer.api.campaign.AICoreOfficerPlugin.AUTOMATED_POINTS_MULT;
import static com.fs.starfarer.api.impl.campaign.AICoreOfficerPluginImpl.*;

public class CyanCoreOfficerGen {

    public static void create() {
        createCharacters();
    }
    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }

    public static void createCharacters() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        if (getPerson(cer_ids.cercyancore) == null) {
            PersonAPI person = genCyan();
            ip.addPerson(person);
            setInstanceChipDescription(cer_ids.CYANCORE_CHIP, person);
        }

    }
    //cercyancore
    public static PersonAPI genCyan() {
        PersonAPI person = Global.getFactory().createPerson();
        person.setId(cer_ids.cercyancore);
        person.setAICoreId(cer_ids.CYANCORE_CHIP);
        person.setFaction("da_cer");
        person.setGender(Gender.MALE);
        person.setRankId(Ranks.SPACE_CAPTAIN);
        person.setPostId(Ranks.POST_OFFICER);
        person.getName().setFirst("AI Construct");
        person.getName().setLast(" Cyan");

        person.setPersonality(Personalities.STEADY);
        //
        person.getStats().setLevel(7);
        person.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
        person.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 2);
        person.getStats().setSkillLevel(Skills.FIELD_MODULATION, 2);
        person.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
        //person.getStats().setSkillLevel(Skills.SYSTEMS_EXPERTISE, 2);
        person.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
        person.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 2);
        person.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 2);

        person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cyancore"));
        person.getMemoryWithoutUpdate().set("$chatterChar", "warframe_cephaloncy");
        person.getMemoryWithoutUpdate().set(cer_ids.OFFICER_NOT_FEARLESS, true);
        person.getMemoryWithoutUpdate().set(AUTOMATED_POINTS_MULT, Math.max(0, BETA_MULT - 0.25f)); // less AS points than Beta
        return person;
    }

    public static void setInstanceChipDescription(String commodityId, PersonAPI person) {
        Global.getSettings().getCommoditySpec(commodityId).setName("Cyan's AI Core");
        Global.getSettings().getDescription(commodityId, Description.Type.RESOURCE).setText1(
                "a Custom AI core Imprinted with a Personality designed for Combat Only."
        );
    }
}