package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Voices;

public class CERPerson {

    //public static String cerlead = "cerlead";
    //public static String cerengie = "cerengie";
    //public static String cerassist = "cerassist";
    //public static String cershadow = "cershadow";

    public static void create() {
        createCERCharacters();
    }
    /**Ouno Capital*/

    public static PersonAPI createCERCharacters() {

        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        MarketAPI market1 = Global.getSector().getEconomy().getMarket("OT_a");
        if (market1 != null) {
            /**Faction Leader*/


                PersonAPI dankshirePerson = Global.getFactory().createPerson();
                dankshirePerson.setId("cerlead");
                dankshirePerson.setFaction("da_cer");
                dankshirePerson.getName().setFirst("Mordrigg");
                dankshirePerson.getName().setLast("Dankshire V");
                dankshirePerson.setGender(FullName.Gender.MALE);
                dankshirePerson.setRankId("cer_grandadmiral");
                dankshirePerson.setPostId("cer_grandadmiral");
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

            /**Chief Engineer*/


                PersonAPI daxterPerson = Global.getFactory().createPerson();
                daxterPerson.setId("cerengie");
                daxterPerson.setFaction("da_cer");
                daxterPerson.getName().setFirst("Daxter");
                daxterPerson.getName().setLast("Taxmen");
                daxterPerson.setGender(FullName.Gender.MALE);
                daxterPerson.setRankId("cer_chiefengineering");
                daxterPerson.setPostId("cer_chiefengineering");
                daxterPerson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_daxter"));
                daxterPerson.addTag("coff_nocapture");
                market1.addPerson(daxterPerson);
                market1.getCommDirectory().addPerson(daxterPerson, 1);
                ip.addPerson(daxterPerson);

            /**Assistant Engineer*/

                PersonAPI faunPerson = Global.getFactory().createPerson();
                faunPerson.setId("cerassist");
                faunPerson.setFaction("da_cer");
                faunPerson.getName().setFirst("Lola");
                faunPerson.getName().setLast("Faun");
                faunPerson.setGender(FullName.Gender.FEMALE);
                faunPerson.setRankId("cer_assistengineering");
                faunPerson.setPostId("cer_assistengineering");
                faunPerson.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cer_faun"));
                faunPerson.addTag("coff_nocapture");
                market1.addPerson(faunPerson);
                market1.getCommDirectory().addPerson(faunPerson, 2);
                ip.addPerson(faunPerson);

        }

        /*Sivie Capital**/
        //ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        //MarketAPI market2 = Global.getSector().getEconomy().getMarket("diableavionics_prison");
        //if (market2 != null) {
        /*2-3 custom Npcs**/
        //}


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
        return null;
    }

}