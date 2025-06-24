package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipEngineControllerAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lazywizard.lazylib.MathUtils;
import org.magiclib.util.MagicAnim;

public class Diableavionics_versantEXEffect implements EveryFrameWeaponEffectPlugin {
    private final Map<Integer, String> LEFT_SELECTOR;

    private final Map<Integer, String> RIGHT_SELECTOR;

    private WeaponAPI rgun;

    private WeaponAPI lgun;

    private WeaponAPI rdoor;

    private WeaponAPI ldoor;

    private WeaponAPI rbarrel;

    private WeaponAPI lbarrel;

    private WeaponAPI rbit;

    private WeaponAPI lbit;

    private WeaponAPI rpanel;

    private WeaponAPI lpanel;

    private WeaponAPI rshield;

    private WeaponAPI lshield;

    private ShipAPI ship;

    private ShipSystemAPI system;

    private ShipEngineControllerAPI engines;

    public String leftshieldID = "ENGINE_PANEL_LEFT";

    public String rightshieldID = "ENGINE_PANEL_RIGHT";

    public String leftslotID = "GUN_LEFT";

    public String rightslotID = "GUN_RIGHT";

    public String leftbarrelID = "BARREL_LEFT";

    public String rightbarrelID = "BARREL_RIGHT";

    public String leftdoorID = "SHIELD_LEFT";

    public String rightdoorID = "SHIELD_RIGHT";

    public String leftbitID = "COVER_LEFT";

    public String rightbitID = "COVER_RIGHT";

    public String leftpanelID = "TOP_LEFT";

    public String rightpanelID = "TOP_RIGHT";

    private boolean runOnce;

    private boolean soundIN;

    private boolean soundOUT;

    private float doorWidth;

    private float doorHeight;

    private float barrelWidth;

    private float barrelHeight;

    private float bitWidth;

    private float bitHeight;

    private float panelWidth;

    private float panelHeight;

    private float shieldWidth;

    private float shieldHeight;

    private float rate;

    private boolean travelDrive;

    private float rotateOffset = 5.0F;

    private float doorOffsetX = -10.0F;

    private float doorOffsetY = -10.5F;

    private float barrelOffsetX = -5.0F;

    private float barrelOffsetY = 8.0F;

    private float barrelRecoil = 5.0F;

    private float lrecoil;

    private float rrecoil;

    private float bitOffsetX = -8.0F;

    private float bitOffsetY = -3.0F;

    private float panelOffsetX = 5.0F;

    private float panelOffsetY = 5.0F;

    private float currentRotateL;

    private float currentRotateR;

    private float maxRotate = 22.5F;

    private float shieldOffsetX = -4.0F;

    private float shieldOffsetY = -2.0F;

    public  Diableavionics_versantEXEffect() {
        this.LEFT_SELECTOR = new HashMap<>();
        this.LEFT_SELECTOR.put(Integer.valueOf(0), "diableavionics_versant_EX_harvest_LEFT");
        this.LEFT_SELECTOR.put(Integer.valueOf(1), "diableavionics_versant_EX_harvestB_LEFT");
        this.LEFT_SELECTOR.put(Integer.valueOf(2), "diableavionics_versant_harvestC_LEFT");
        this.RIGHT_SELECTOR = new HashMap<>();
        this.RIGHT_SELECTOR.put(Integer.valueOf(0), "diableavionics_versant_EX_harvest_RIGHT");
        this.RIGHT_SELECTOR.put(Integer.valueOf(1), "diableavionics_versant_EX_harvestB_RIGHT");
        this.RIGHT_SELECTOR.put(Integer.valueOf(2), "diableavionics_versant_EX_harvestC_RIGHT");
        this.leftshieldID = "ENGINE_PANEL_LEFT";
        this.rightshieldID = "ENGINE_PANEL_RIGHT";
        this.leftslotID = "GUN_LEFT";
        this.rightslotID = "GUN_RIGHT";
        this.leftbarrelID = "BARREL_LEFT";
        this.rightbarrelID = "BARREL_RIGHT";
        this.leftdoorID = "SHIELD_LEFT";
        this.rightdoorID = "SHIELD_RIGHT";
        this.leftbitID = "COVER_LEFT";
        this.rightbitID = "COVER_RIGHT";
        this.leftpanelID = "TOP_LEFT";
        this.rightpanelID = "TOP_RIGHT";
        this.runOnce = false;
        this.soundIN = true;
        this.soundOUT = true;
        this.rate = 1.0F;
        this.travelDrive = false;
        this.rotateOffset = 5.0F;
        this.doorOffsetX = -10.0F;
        this.doorOffsetY = -10.5F;
        this.barrelOffsetX = -5.0F;
        this.barrelOffsetY = 8.0F;
        this.barrelRecoil = 5.0F;
        this.lrecoil = 0.0F;
        this.rrecoil = 0.0F;
        this.bitOffsetX = -8.0F;
        this.bitOffsetY = -3.0F;
        this.panelOffsetX = 5.0F;
        this.panelOffsetY = 5.0F;
        this.currentRotateL = 0.0F;
        this.currentRotateR = 0.0F;
        this.maxRotate = 22.5F;
        this.shieldOffsetX = -4.0F;
        this.shieldOffsetY = -2.0F;
    }

    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if (Global.getCombatEngine().isPaused())
            return;
        if (!this.runOnce || this.ship == null || this.system == null) {
            this.ship = weapon.getShip();
            this.system = this.ship.getSystem();
            this.engines = this.ship.getEngineController();
            List<WeaponAPI> weapons = this.ship.getAllWeapons();
            for (WeaponAPI w : weapons) {
                switch (w.getSlot().getId()) {
                    case "GUN_LEFT":
                        this.lgun = w;
                    case "GUN_RIGHT":
                        this.rgun = w;
                    case "SHIELD_LEFT":
                        this.ldoor = w;
                        this.doorHeight = w.getSprite().getHeight();
                        this.doorWidth = w.getSprite().getWidth();
                    case "SHIELD_RIGHT":
                        this.rdoor = w;
                    case "BARREL_LEFT":
                        this.lbarrel = w;
                        this.barrelHeight = w.getSprite().getHeight();
                        this.barrelWidth = w.getSprite().getWidth();
                    case "BARREL_RIGHT":
                        this.rbarrel = w;
                    case "COVER_LEFT":
                        this.lbit = w;
                        this.bitHeight = w.getSprite().getHeight();
                        this.bitWidth = w.getSprite().getWidth();
                    case "COVER_RIGHT":
                        this.rbit = w;
                    case "TOP_LEFT":
                        this.lpanel = w;
                        this.panelHeight = w.getSprite().getHeight();
                        this.panelWidth = w.getSprite().getWidth();
                    case "TOP_RIGHT":
                        this.rpanel = w;
                    case "ENGINE_PANEL_LEFT":
                        this.lshield = w;
                        this.shieldHeight = w.getSprite().getHeight();
                        this.shieldWidth = w.getSprite().getWidth();
                    case "ENGINE_PANEL_RIGHT":
                        this.rshield = w;
                }
            }
            if (this.lgun == null || this.rgun == null)
                return;
            this.runOnce = true;
            return;
        }
        float ltarget = 0.0F;
        float rtarget = 0.0F;
        if (this.engines.isAccelerating()) {
            ltarget -= 11.25F;
            rtarget += 11.25F;
        } else if (this.engines.isDecelerating() || this.engines.isAcceleratingBackwards()) {
            ltarget += 22.5F;
            rtarget -= 22.5F;
        }
        if (this.engines.isStrafingLeft()) {
            ltarget += 7.5F;
            rtarget += 15.0F;
        } else if (this.engines.isStrafingRight()) {
            ltarget -= 15.0F;
            rtarget -= 7.5F;
        }
        if (this.engines.isTurningLeft()) {
            ltarget -= 11.25F;
            rtarget -= 11.25F;
        } else if (this.engines.isTurningRight()) {
            ltarget += 11.25F;
            rtarget += 11.25F;
        }
        float rtl = MathUtils.getShortestRotation(this.currentRotateL, ltarget);
        if (Math.abs(rtl) < 0.5F) {
            this.currentRotateL = ltarget;
        } else if (rtl > 0.0F) {
            this.currentRotateL += 0.5F;
        } else {
            this.currentRotateL -= 0.5F;
        }
        float rtr = MathUtils.getShortestRotation(this.currentRotateR, rtarget);
        if (Math.abs(rtr) < 0.5F) {
            this.currentRotateR = rtarget;
        } else if (rtr > 0.0F) {
            this.currentRotateR += 0.5F;
        } else {
            this.currentRotateR -= 0.5F;
        }
        float FACING = this.ship.getFacing();
        float LGUN = this.lgun.getCurrAngle();
        float RGUN = this.rgun.getCurrAngle();
        if (this.lgun.getChargeLevel() == 1.0F) {
            this.lrecoil = Math.min(1.0F, this.lrecoil + 0.33F);
        } else {
            this.lrecoil = Math.max(0.0F, this.lrecoil - 0.5F * amount);
        }
        if (this.rgun.getChargeLevel() == 1.0F) {
            this.rrecoil = Math.min(1.0F, this.rrecoil + 0.33F);
        } else {
            this.rrecoil = Math.max(0.0F, this.rrecoil - 0.5F * amount);
        }
        if (this.ship.getTravelDrive().isActive() || this.ship.getFluxTracker().isVenting()) {
            this.rate = Math.min(1.0F, this.rate + amount);
            this.travelDrive = true;
        } else if (this.travelDrive) {
            this.rate = Math.max(0.0F, this.rate - amount);
            if (this.rate == 0.0F)
                this.travelDrive = false;
        } else {
            this.rate = this.system.getEffectLevel();
        }
        if (this.rate == 0.0F) {
            this.soundIN = false;
        } else if (this.rate == 1.0F) {
            this.soundOUT = false;
        }
        if (this.system.isActive() || this.rate > 0.0F) {
            if (this.rate > 0.0F && !this.soundIN) {
                this.soundIN = true;
                Global.getSoundPlayer().playSound("diableavionics_transform_in", 1.0F, 1.0F, this.ship.getLocation(), this.ship.getVelocity());
            } else if (this.rate < 1.0F && !this.soundOUT) {
                this.soundOUT = true;
                Global.getSoundPlayer().playSound("diableavionics_transform_out", 1.0F, 1.0F, this.ship.getLocation(), this.ship.getVelocity());
            }
            this.lgun.setRemainingCooldownTo(1.0F);
            this.rgun.setRemainingCooldownTo(1.0F);
            float rotateDoors = MagicAnim.smoothNormalizeRange(this.rate, 0.25F, 0.75F);
            float slideDoors = MagicAnim.smoothNormalizeRange(this.rate, 0.0F, 0.5F);
            float recessDoors = MagicAnim.smoothNormalizeRange(this.rate, 0.5F, 1.0F);
            float clipDoors = MagicAnim.smoothReturnNormalizeRange(this.rate, 0.5F, 1.0F);
            this.lbarrel.setCurrAngle(this.lgun.getCurrAngle() + slideDoors * MathUtils.getShortestRotation(LGUN, FACING));
            this.rbarrel.setCurrAngle(this.rgun.getCurrAngle() + slideDoors * MathUtils.getShortestRotation(RGUN, FACING));
            float lbX = this.barrelWidth / 2.0F + -5.0F * rotateDoors;
            float rbX = this.barrelWidth / 2.0F - -5.0F * rotateDoors;
            float lbY = this.barrelHeight / 2.0F + 8.0F * slideDoors + 5.0F * this.lrecoil;
            float rbY = this.barrelHeight / 2.0F + 8.0F * slideDoors + 5.0F * this.rrecoil;
            this.lbarrel.getSprite().setCenter(lbX, lbY);
            this.rbarrel.getSprite().setCenter(rbX, rbY);
            this.ldoor.setCurrAngle(this.lgun.getCurrAngle() + rotateDoors * MathUtils.getShortestRotation(LGUN, FACING) + clipDoors * 5.0F);
            this.rdoor.setCurrAngle(this.rgun.getCurrAngle() + rotateDoors * MathUtils.getShortestRotation(RGUN, FACING) - clipDoors * 5.0F);
            float ldX = this.doorWidth / 2.0F + -10.0F * recessDoors;
            float rdX = this.doorWidth / 2.0F - -10.0F * recessDoors;
            float dY = this.doorHeight / 2.0F + -10.5F * slideDoors;
            this.ldoor.getSprite().setCenter(ldX, dY);
            this.rdoor.getSprite().setCenter(rdX, dY);
            this.lbit.setCurrAngle(this.lgun.getCurrAngle() + rotateDoors * MathUtils.getShortestRotation(LGUN, FACING) + clipDoors * 5.0F);
            this.rbit.setCurrAngle(this.rgun.getCurrAngle() + rotateDoors * MathUtils.getShortestRotation(RGUN, FACING) - clipDoors * 5.0F);
            float lbtX = this.bitWidth / 2.0F + -8.0F * recessDoors;
            float rbtX = this.bitWidth / 2.0F - -8.0F * recessDoors;
            float btY = this.bitHeight / 2.0F + -3.0F * slideDoors;
            this.lbit.getSprite().setCenter(lbtX, btY);
            this.rbit.getSprite().setCenter(rbtX, btY);
            float lpX = this.panelWidth / 2.0F + 5.0F * recessDoors;
            float rpX = this.panelWidth / 2.0F - 5.0F * recessDoors;
            float pY = this.panelHeight / 2.0F + 5.0F * rotateDoors;
            this.lpanel.getSprite().setCenter(lpX, pY);
            this.rpanel.getSprite().setCenter(rpX, pY);
            this.lshield.setCurrAngle(FACING + this.currentRotateL * (1.0F - slideDoors));
            this.rshield.setCurrAngle(FACING + this.currentRotateR * (1.0F - slideDoors));
            float lsX = this.shieldWidth / 2.0F + -4.0F * slideDoors;
            float rsX = this.shieldWidth / 2.0F - -4.0F * slideDoors;
            float sY = this.shieldHeight / 2.0F + -2.0F * recessDoors;
            this.lshield.getSprite().setCenter(lsX, sY);
            this.rshield.getSprite().setCenter(rsX, sY);
        } else {
            this.lbarrel.getSprite().setCenter(this.barrelWidth / 2.0F, this.barrelHeight / 2.0F + 5.0F * this.lrecoil);
            this.rbarrel.getSprite().setCenter(this.barrelWidth / 2.0F, this.barrelHeight / 2.0F + 5.0F * this.rrecoil);
            this.lbarrel.setCurrAngle(LGUN);
            this.ldoor.setCurrAngle(LGUN);
            this.lbit.setCurrAngle(LGUN);
            this.rbarrel.setCurrAngle(RGUN);
            this.rdoor.setCurrAngle(RGUN);
            this.rbit.setCurrAngle(RGUN);
            this.lshield.setCurrAngle(FACING + this.currentRotateL);
            this.rshield.setCurrAngle(FACING + this.currentRotateR);
        }
    }
}