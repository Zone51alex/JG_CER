package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.AICoreOfficerPlugin;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.BaseAICoreOfficerPluginImpl;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.campaign.ids.cer_ids;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

public class CyanCoreOfficerPluginImpl extends BaseAICoreOfficerPluginImpl implements AICoreOfficerPlugin {

    @Override
    public PersonAPI createPerson(String aiCoreid, String factionId, Random random) {
        PersonAPI person = CERPerson.getPerson(cer_ids.cercyancore);

        if (person == null) {
            // Person doesn’t exist yet — create and register immediately
            person = Global.getSector().getFaction(factionId).createRandomPerson();
            person.setId(cer_ids.cercyancore);
            person.setGender(FullName.Gender.MALE);
            person.setRankId("space_commander");
            person.setPostId("post_guard_leader");
            person.setPersonality(Personalities.AGGRESSIVE);
            person.getName().setFirst("Cyan");
            person.getName().setLast("Sentinel-Warden");
            person.getStats().setLevel(5);
            person.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
            person.getStats().setSkillLevel(Skills.FIELD_MODULATION, 2);
            person.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            person.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
            person.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 2);
            person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "cyancore"));
            person.getMemoryWithoutUpdate().set("$chatterChar", "cer_cyanai_chatter");
            person.setAICoreId(cer_ids.CYANCORE_CHIP);
            person.getMemoryWithoutUpdate().set("$isAICore", true);
            person.getMemoryWithoutUpdate().set("$coreType","warden");

            Global.getSector().getImportantPeople().addPerson(person);
        }
        // Reset admiral skills
        for (MutableCharacterStatsAPI.SkillLevelAPI skillLevel : person.getStats().getSkillsCopy()) {
            if (skillLevel.getSkill().isAdmiralSkill()) {
                person.getStats().setSkillLevel(skillLevel.getSkill().getId(), 0);
            }
        }
        return person;
    }

    @Override
    public void createPersonalitySection(PersonAPI person, TooltipMakerAPI tooltip) {
        float opad = 10f;
        Color text = person.getFaction().getBaseUIColor();
        Color bg = person.getFaction().getDarkUIColor();

        float autoMult = person.getMemoryWithoutUpdate().getFloat(AICoreOfficerPlugin.AUTOMATED_POINTS_MULT);
        String autoMultString = new DecimalFormat("#.##").format(autoMult);

        tooltip.addPara("Automated ship points multiplier: " + autoMultString + "x", opad, Misc.getHighlightColor(), autoMultString + "x");
        String aiName = person.getName().getFirst();
        tooltip.addSectionHeading(aiName + " - EX-Warden core, now active in ship command", text, bg, Alignment.MID, 20);

        switch (person.getPersonalityAPI().getId()) {
            case Personalities.RECKLESS:
                tooltip.addPara("Displays relentless determination. Will suppress threats at any cost, regardless of risk.", opad);
                break;
            case Personalities.AGGRESSIVE:
                tooltip.addPara("Engages targets proactively. Implements containment and lethal measures swiftly.", opad);
                break;
            case Personalities.STEADY:
                tooltip.addPara("Balances tactical decisions with crew safety. Maintains order under pressure.", opad);
                break;
            case Personalities.CAUTIOUS:
                tooltip.addPara("Prefers to secure areas before engagement. Minimizes unnecessary damage.", opad);
                break;
            case Personalities.TIMID:
                tooltip.addPara("Avoids reckless confrontations. Will retreat and regroup if situation escalates.", opad);
                break;
        }
    }
/*
    public PersonAPI createPerson(String aiCoreid, String factionId, Random random) {
        PersonAPI person = null;

        if (aiCoreid.equals(cer_ids.CYANCORE_CHIP)) {
            person = CERPerson.getPerson(cer_ids.cercyancore);
        }
        for (MutableCharacterStatsAPI.SkillLevelAPI skillLevel : person.getStats().getSkillsCopy()) {
            if (skillLevel.getSkill().isAdmiralSkill()) {
                person.getStats().setSkillLevel(skillLevel.getSkill().getId(), 0);
            }
        }
        if (Misc.isUnremovable(person)) {
            Misc.setUnremovable(person, false);
            person.getStats().setLevel(person.getStats().getLevel() - 1);
            for (MutableCharacterStatsAPI.SkillLevelAPI skillLevel : person.getStats().getSkillsCopy()) {
                if (skillLevel.getSkill().isCombatOfficerSkill() && !skillLevel.getSkill().hasTag(Skills.TAG_NPC_ONLY)) {
                    person.getStats().setSkillLevel(skillLevel.getSkill().getId(), 0);
                    break;
                }
            }
        }
        return person;
    }
    @Override
    public void createPersonalitySection(PersonAPI person, TooltipMakerAPI tooltip) {
        float opad = 10f;
        Color text = person.getFaction().getBaseUIColor();
        Color bg = person.getFaction().getDarkUIColor();
        CommoditySpecAPI spec = Global.getSettings().getCommoditySpec(person.getAICoreId());

        float autoMult = person.getMemoryWithoutUpdate().getFloat(AICoreOfficerPlugin.AUTOMATED_POINTS_MULT);
        String autoMultString = new DecimalFormat("#.##").format(person.getMemoryWithoutUpdate().getFloat(AICoreOfficerPlugin.AUTOMATED_POINTS_MULT));
        String dConString = "";
        String dConString2 = "";
        String dConString3 = "";

        tooltip.addPara("Automated ship points multiplier: "
                + autoMultString + "x" + dConString + dConString2 + dConString3, opad, Misc.getHighlightColor(),autoMultString + "x", dConString2);

        tooltip.addSectionHeading("Personality: " + Misc.getPersonalityName(person), text, bg, Alignment.MID, 20);
        switch (person.getPersonalityAPI().getId()) {
            //
            case Personalities.RECKLESS:
                tooltip.addPara("In combat, this AI Core is single-minded and determined. " +
                        "In a human captain, their traits might be considered reckless. In a machine, they're terrifying.", opad);
                break;
            case Personalities.AGGRESSIVE:
                tooltip.addPara("In combat, this AI Core will prefer to engage at a range that allows the use of " +
                        "all of their ship's weapons and will employ any fighters under their command aggressively.", opad);
                break;
            case Personalities.STEADY:
                tooltip.addPara("In combat, this AI Core will favor a balanced approach with " +
                        "tactics matching the current situation.", opad);
                break;
            //
            case Personalities.CAUTIOUS:
                tooltip.addPara("In combat, this AI Core will prefer to stay out of enemy range, " +
                        "only occasionally moving in if out-ranged by the enemy.", opad);
                break;
            //
            case Personalities.TIMID:
                tooltip.addPara("In combat, this AI Core will attempt to avoid direct engagements if at all " +
                        "possible, even if commanding a combat vessel.", opad);
                break;
        }
    }
    */
}
