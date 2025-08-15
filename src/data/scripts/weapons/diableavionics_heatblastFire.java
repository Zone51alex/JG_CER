package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;
import com.fs.starfarer.api.combat.OnFireEffectPlugin;

import java.awt.*;

public class diableavionics_heatblastFire implements EveryFrameWeaponEffectPlugin, OnFireEffectPlugin {

    @Override
    public void advance (float amount, CombatEngineAPI engine, WeaponAPI weapon) {
    }

    @Override
    public void onFire(DamagingProjectileAPI projectile, WeaponAPI weapon, CombatEngineAPI engine) {

        for(int i=0; i<10; i++){
            Vector2f drift = MathUtils.getPoint(weapon.getShip().getVelocity(), MathUtils.getRandomNumberInRange(-50f, 50f), weapon.getCurrAngle());
            DamagingProjectileAPI p = (DamagingProjectileAPI) engine.spawnProjectile(weapon.getShip(), weapon, weapon.getId(), projectile.getLocation(), weapon.getCurrAngle()+MathUtils.getRandomNumberInRange(-2.5f, 2.5f), drift);
            p.setDamageAmount(projectile.getBaseDamageAmount()/10);
        }
        projectile.setDamageAmount(projectile.getBaseDamageAmount()/10);
        engine.addSmoothParticle(
                projectile.getLocation(),
                weapon.getShip().getVelocity(),
                MathUtils.getRandomNumberInRange(90, 120),
                2,
                0.1f,
                new Color(100, 255, 100, 255)
                );

        for(int i=0; i<5; i++){
            engine.addNebulaParticle(
                    projectile.getLocation(),
                    MathUtils.getPoint(weapon.getShip().getVelocity(), MathUtils.getRandomNumberInRange(15*i, 30*i), weapon.getCurrAngle()),
                    MathUtils.getRandomNumberInRange(20, 40),
                    MathUtils.getRandomNumberInRange(1.5f, 2f),
                    0.1f,
                    0.3f,
                    MathUtils.getRandomNumberInRange(0.3f, 0.6f),
                    new Color(169,169,169, 255)
            );
        }

        for(int i=0; i<15; i++){
            engine.addHitParticle(
                    projectile.getLocation(),
                    MathUtils.getRandomPointInCone(weapon.getShip().getVelocity(), MathUtils.getRandomNumberInRange(100, 400), weapon.getCurrAngle()-25, weapon.getCurrAngle()+25),
                    MathUtils.getRandomNumberInRange(2, 6),
                    5f,
                    MathUtils.getRandomNumberInRange(0.1f, 0.3f),
                    new Color(100, 255, 100, 255)
            );
        }
    }
}
