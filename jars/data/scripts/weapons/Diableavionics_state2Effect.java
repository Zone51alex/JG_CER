package data.scripts.weapons;

import com.fs.starfarer.api.combat.BeamAPI;
import com.fs.starfarer.api.combat.BeamEffectPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import org.lazywizard.lazylib.FastTrig;
import org.lazywizard.lazylib.MathUtils;

public class Diableavionics_state2Effect implements BeamEffectPlugin {
    private boolean runOnce = false;

    private float time = 0.0F;

    private float offsetA = 0.0F;

    WeaponSpecAPI specs;

    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam) {
        if (engine.isPaused() || beam.getWeapon().getShip().getOriginalOwner() == -1)
            return;
        if (!this.runOnce) {
            this.runOnce = true;
            beam.getWeapon().ensureClonedSpec();
            this.specs = beam.getWeapon().getSpec();
            this.offsetA = MathUtils.getRandomNumberInRange(0, 100);

        }
        this.time += amount * 2.0F;
        float A = (float)(FastTrig.sin(((this.time + this.offsetA) * 1.1F)) / 2.0D + FastTrig.sin((this.time + this.offsetA) * 2.9D) / 3.0D);

        this.specs.getHardpointAngleOffsets().set(0, Float.valueOf(A));

        this.specs.getTurretAngleOffsets().set(0, Float.valueOf(A));

        this.specs.getHiddenAngleOffsets().set(0, Float.valueOf(A));

    }
}
