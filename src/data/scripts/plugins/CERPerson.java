package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;

import com.fs.starfarer.api.characters.FullName.Gender;
import data.campaign.ids.cer_ids;

public class CERPerson {


    public static PersonAPI Y11Person;

    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }

    public static void create() {
        createCERCharacters();
    }

    public static PersonAPI createCERCharacters() {

        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        /**Cyan Core*/
        if (getPerson(cer_ids.cercyancore) == null) {
            PersonAPI person = Global.getSector().getFaction("da_cer").createRandomPerson();
            person.setId(cer_ids.cercyancore);
            person.setGender(Gender.MALE);
            person.setRankId(Ranks.SPACE_COMMANDER); // Operation Manager
            person.setPostId(Ranks.POST_GUARD_LEADER); // Scientist
            //person.setVoice(Voices.OFFICIAL);
            person.setPersonality(Personalities.STEADY);
            person.getStats().setLevel(5);
            person.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
            person.getStats().setSkillLevel(Skills.FIELD_MODULATION, 2);
            person.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            person.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
            person.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 2);
            person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cyancore"));
            person.getMemoryWithoutUpdate().set("$chatterChar", "robotic");
            ip.addPerson(person);
        }
        /**Ouno Capital*/
        MarketAPI market1 = Global.getSector().getEconomy().getMarket("OT_a");
        if (market1 != null) {
            /**Faction Leader*/
            if ((getPerson(cer_ids.cerlead) == null)) {
                PersonAPI dankshirePerson = Global.getFactory().createPerson();
                dankshirePerson.setId(cer_ids.cerlead);
                dankshirePerson.setFaction("da_cer");
                dankshirePerson.getName().setFirst("Mordrigg");
                dankshirePerson.getName().setLast("Dankshire V");
                dankshirePerson.setGender(Gender.MALE);
                dankshirePerson.setImportance(PersonImportance.VERY_HIGH);
                dankshirePerson.setRankId("cer_grandadmiral");
                dankshirePerson.setPostId(Ranks.POST_FACTION_LEADER);
                dankshirePerson.addTag("military");
                dankshirePerson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_grandadmiral"));
                if (Global.getSettings().getModManager().isModEnabled("IndEvo")) {
                    dankshirePerson.getStats().setSkillLevel("indevo_industrial_planning", 1.0F);
                    dankshirePerson.getStats().setSkillLevel("indevo_planetary_operations", 1.0F);
                    dankshirePerson.getStats().setSkillLevel("indevo_fleet_logistics", 1.0F);
                } else {
                    dankshirePerson.getStats().setSkillLevel("industrial_planning", 1.0F);
                }
                dankshirePerson.addTag("coff_nocapture");
                market1.addPerson(dankshirePerson);
                market1.getCommDirectory().addPerson(dankshirePerson, 0);
                market1.setAdmin(dankshirePerson);
                ip.addPerson(dankshirePerson);
            }
            /**Chief Engineer*/
            if ((getPerson(cer_ids.cerengie) == null)) {
                PersonAPI daxterPerson = Global.getFactory().createPerson();
                daxterPerson.setId(cer_ids.cerengie);
                daxterPerson.setFaction("da_cer");
                daxterPerson.getName().setFirst("Taio");
                daxterPerson.getName().setLast("Lunaria");
                daxterPerson.setGender(Gender.MALE);
                daxterPerson.setImportance(PersonImportance.VERY_HIGH);
                daxterPerson.setRankId("cer_chiefengineering");
                daxterPerson.setPostId("cer_chiefengineering");
                daxterPerson.addTag("trade");
                daxterPerson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_daxter"));
                daxterPerson.addTag("coff_nocapture");
                market1.addPerson(daxterPerson);
                market1.getCommDirectory().addPerson(daxterPerson, 1);
                ip.addPerson(daxterPerson);
            }

            /**Assistant Engineer*/
                if ((getPerson(cer_ids.cerassist) == null)) {
                    PersonAPI faunPerson = Global.getFactory().createPerson();
                    faunPerson.setId(cer_ids.cerassist);
                    faunPerson.setFaction("da_cer");
                    faunPerson.getName().setFirst("Suru");
                    faunPerson.getName().setLast("Solaris");
                    faunPerson.setGender(Gender.FEMALE);
                    faunPerson.setImportance(PersonImportance.VERY_HIGH);
                    faunPerson.setRankId("cer_assistengineering");
                    faunPerson.setPostId("cer_assistengineering");
                    faunPerson.addTag("military");
                    faunPerson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_faun"));
                    faunPerson.addTag("coff_nocapture");
                    market1.addPerson(faunPerson);
                    market1.getCommDirectory().addPerson(faunPerson, 2);
                    ip.addPerson(faunPerson);
                }
        }

        /*Sivie Capital**/
        //ImportantPeopleAPI ip = Global.getSector().getImportantPeople();

        MarketAPI market2 = Global.getSector().getEconomy().getMarket("diableavionics_prison");
        if (market2 != null) {
        /*2-3 custom Npcs**/
        /**Faction Leader*/

        if ((getPerson(cer_ids.dalead) == null)) {
            PersonAPI Person = Global.getFactory().createPerson();
            Person.setId(cer_ids.dalead);
            Person.setFaction("diableavionics");
            Person.getName().setFirst("Ake");
            Person.getName().setLast("Solaris");
            Person.setGender(Gender.FEMALE);
            Person.setRankId(Ranks.POST_ADMINISTRATOR);
            Person.setPostId(Ranks.POST_FACTION_LEADER);
            Person.setImportance(PersonImportance.VERY_HIGH);
            Person.addTag("military");
            Person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_Ake"));
            if (Global.getSettings().getModManager().isModEnabled("IndEvo")) {
                Person.getStats().setSkillLevel("indevo_industrial_planning", 1.0F);
                Person.getStats().setSkillLevel("indevo_planetary_operations", 1.0F);
                Person.getStats().setSkillLevel("indevo_fleet_logistics", 1.0F);
            } else {
                Person.getStats().setSkillLevel("industrial_planning", 1.0F);
            }
            Person.addTag("coff_nocapture");
            market2.addPerson(Person);
            market2.getCommDirectory().addPerson(Person, 0);
            market2.setAdmin(Person);
            ip.addPerson(Person);
        }

        }
        /*Belt Ops Station**/
        //ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        //MarketAPI market3 = Global.getSector().getEconomy().getMarket("diableavionics_ressource");
        //if (market3 != null) {
        /*1 custom Npc**/
        //}


        /*Eclispe Station**/
        //ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
       //MarketAPI market4 = Global.getSector().getEconomy().getMarket("diableavionics_eclipse");
        //if (market4 != null) {
        /*1 custom Npc**/
        //}


        /*Shadow Station FOB**/
        //ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        //MarketAPI market5 = Global.getSector().getEconomy().getMarket("diableavionics_shadow");
        //if (market5 != null) {
        /*1-2 custom Npcs**/
        //}
        /*Fleet Contacts*/
        /*
        if ((getPerson(cer_ids.ceradmiral) == null)) {
            PersonAPI Y11Person = Global.getFactory().createPerson();
            Y11Person.setId(cer_ids.ceradmiral);
            Y11Person.setFaction("da_cer");
            Y11Person.getName().setFirst("Admiral");
            Y11Person.getName().setLast("Y11");
            Y11Person.setGender(Gender.MALE);
            Y11Person.setRankId(Ranks.SPACE_ADMIRAL);
            Y11Person.setPostId(Ranks.POST_FLEET_COMMANDER);
            Y11Person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_faun"));
            Y11Person.setPersonality("steady");
            Y11Person.getStats().setSkipRefresh(true);
            Y11Person.getStats().setLevel(7);

            Y11Person.getStats().setSkillLevel("ballistic_mastery", 2.0F);
            Y11Person.getStats().setSkillLevel("gunnery_implants", 1.0F);
            Y11Person.getStats().setSkillLevel("impact_mitigation", 1.0F);
            Y11Person.getStats().setSkillLevel("target_analysis", 1.0F);
            Y11Person.getStats().setSkillLevel("helmsmanship", 1.0F);
            Y11Person.getStats().setSkillLevel("missile_specialization", 1.0F);
            Y11Person.getStats().setSkillLevel("polarized_armor", 1.0F);
            Y11Person.addTag("coff_nocapture");
            ip.addPerson(Y11Person);
        }
        */
        return null;
    }
    //Fleet AddPerson
    /*
    public static class Y11Person {
        private String id = cer_ids.ceradmiral;
        private final FullName.Gender gender = FullName.Gender.MALE;
        private final FactionAPI faction = Global.getSector().getFaction("da_cer");
        Y11Person() {
            PersonAPI Y11Person = this.faction.createRandomPerson();
            Y11Person.setId(this.id);
            Y11Person.getName().setFirst("Admiral");
            Y11Person.getName().setLast("Y11");
            Y11Person.getName().setGender(this.gender);
            Y11Person.setRankId(Ranks.SPACE_ADMIRAL);
            Y11Person.setPostId(Ranks.POST_FLEET_COMMANDER);
            Y11Person.setPortraitSprite("cer_2ndFleetCaptain");
            Y11Person.setPersonality("steady");
            Y11Person.getStats().setSkipRefresh(true);
            Y11Person.getStats().setLevel(7);

            Y11Person.getStats().setSkillLevel("ballistic_mastery", 2.0F);
            Y11Person.getStats().setSkillLevel("gunnery_implants", 1.0F);
            Y11Person.getStats().setSkillLevel("impact_mitigation", 1.0F);
            Y11Person.getStats().setSkillLevel("target_analysis", 1.0F);
            Y11Person.getStats().setSkillLevel("helmsmanship", 1.0F);
            Y11Person.getStats().setSkillLevel("missile_specialization", 1.0F);
            Y11Person.getStats().setSkillLevel("polarized_armor", 1.0F);
            Y11Person.addTag("coff_nocapture");

            ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
            ip.addPerson(Y11Person);
        }
    }
     */
}
