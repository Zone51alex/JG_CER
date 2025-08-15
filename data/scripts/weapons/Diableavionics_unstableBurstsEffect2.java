package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;
//import data.scripts.util.Diableavionics_graphicLibEffects;
import org.magiclib.util.MagicRender;
import java.awt.Color;
import org.lazywizard.lazylib.FastTrig;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class Diableavionics_unstableBurstsEffect2 implements BeamEffectPlugin {


    private IntervalUtil fireInterval = new IntervalUtil(0.25F, 1.75F);

    private boolean wasZero = true;
    private boolean hasFired = false;
    private float random=1f;
    private final float WIDTH=35, PARTICLES=5;
    private final IntervalUtil timer = new IntervalUtil(0.2f,0.2f);

    private final String id="diableavionics_zephyr_firing";

    @Override
    public void advance(float amount, CombatEngineAPI engine, BeamAPI beam) {
        // Don't bother with any checks if the game is paused
        if (engine.isPaused()) {
            return;
        }

        if(beam.getBrightness()==1) {
            Vector2f start = beam.getFrom();
            Vector2f end = beam.getTo();

            if (MathUtils.getDistanceSquared(start, end)==0){
                return;
            }

            timer.advance(amount);
            if (timer.intervalElapsed()){
                hasFired=false;
                random = MathUtils.getRandomNumberInRange(0.5f, 1);

                //beam fluff
                if(MagicRender.screenCheck(0.1f, start)){
                    for(int i=0; i<PARTICLES; i++){
                        Vector2f point = MathUtils.getPointOnCircumference(start,(float)Math.random()*random*300,beam.getSource().getFacing());
                        Vector2f.add(point, MathUtils.getRandomPointInCircle(new Vector2f(), random*WIDTH/3), point);
                        Vector2f vel = new Vector2f(beam.getSource().getVelocity());
                        Vector2f.add(vel, MathUtils.getPointOnCircumference(new Vector2f(), random*WIDTH/2 + (float)Math.random()*25, beam.getSource().getFacing()), vel);

                        engine.addHitParticle(
                                point,
                                vel,
                                3+7*(float)Math.random(),
                                0.5f,
                                0.1f+0.5f*(float)Math.random(),
                                new Color(95,165,200,255)
                        );
                    }
                    engine.addHitParticle(
                            start,
                            beam.getSource().getVelocity(),
                            50+50*(float)Math.random(),
                            1,
                            0.1f+0.2f*(float)Math.random(),
                            new Color(100,150,255,255)
                    );
                    engine.addHitParticle(
                            start,
                            beam.getSource().getVelocity(),
                            40,
                            1,
                            0.05f,
                            new Color(255,255,255,255)
                    );
                }
            }

            float theWidth = WIDTH * (Math.min(1,(float)FastTrig.cos(20*MathUtils.FPI * Math.min(timer.getElapsed(),0.05f)) + 1)) ;

            beam.setWidth(random*theWidth);

            if (!hasFired){
                hasFired=true;
                //play sound (to avoid limitations with the way weapon sounds are handled)
                Global.getSoundPlayer().playSound(id, 0.75f+0.5f*(float)Math.random(), 1.5f, start, beam.getSource().getVelocity());
            }
        } else {
            hasFired=false;
        }
        //Beam Arcs
        CombatEntityAPI target = beam.getDamageTarget();
        if (target instanceof ShipAPI && beam.getBrightness() >= 1.0F) {
            float dur = beam.getDamage().getDpsDuration();

            if (!this.wasZero) dur = 0.0F;
            this.wasZero = (beam.getDamage().getDpsDuration() <= 0.0F);
            this.fireInterval.advance(dur);

            if (this.fireInterval.intervalElapsed()) {
                ShipAPI ship = (ShipAPI)target;
                boolean hitShield = (target.getShield() != null && target.getShield().isWithinArc(beam.getRayEndPrevFrame()));
                float pierceChance = ((ShipAPI)target).getHardFluxLevel() - 0.1F;
                pierceChance *= ship.getMutableStats().getDynamic().getValue("shield_pierced_mult");

                boolean piercedShield = (hitShield && (float)Math.random() < pierceChance);


                if (!hitShield || piercedShield) {
                    Vector2f point = beam.getRayEndPrevFrame();
                    float emp = beam.getDamage().getFluxComponent() * 1.0F;
                    float dam = beam.getDamage().getDamage() * 1.0F;
                    engine.spawnEmpArcPierceShields(
                            beam.getSource(), point, beam.getDamageTarget(), beam.getDamageTarget(),
                            DamageType.ENERGY,
                            dam,
                            emp,
                            100000.0F,
                            "tachyon_lance_emp_impact",
                            beam.getWidth() + 9.0F,
                            beam.getFringeColor(),
                            beam.getCoreColor());
                }
            }
        }
    }
}