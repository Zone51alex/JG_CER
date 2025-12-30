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

public class diable_stratusEffect implements EveryFrameWeaponEffectPlugin {

    private final Map<Integer,String> MAIN_SELECTORTOPLEFT = new HashMap<>();
    {
        MAIN_SELECTORTOPLEFT.put(0, "diable_microbuiltin");
        MAIN_SELECTORTOPLEFT.put(1, "diable_magicboxbuiltin");
        MAIN_SELECTORTOPLEFT.put(2, "diable_reioibuiltin");
    }

    private final Map<Integer,String> MAIN_SELECTORBOTTOMLEFT = new HashMap<>();
    {
        MAIN_SELECTORBOTTOMLEFT.put(0, "diable_microbuiltin");
        MAIN_SELECTORBOTTOMLEFT.put(1, "diable_magicboxbuiltin");
        MAIN_SELECTORBOTTOMLEFT.put(2, "diable_reioibuiltin");
    }

    private final Map<Integer,String> MAIN_SELECTORTOPRIGHT = new HashMap<>();
    {
        MAIN_SELECTORTOPRIGHT.put(0, "diable_microbuiltin");
        MAIN_SELECTORTOPRIGHT.put(1, "diable_magicboxbuiltin");
        MAIN_SELECTORTOPRIGHT.put(2, "diable_reioibuiltin");
    }

    private final Map<Integer,String> MAIN_SELECTORBOTTOMRIGHT = new HashMap<>();
    {
        MAIN_SELECTORBOTTOMRIGHT.put(0, "diable_microbuiltin");
        MAIN_SELECTORBOTTOMRIGHT.put(1, "diable_magicboxbuiltin");
        MAIN_SELECTORBOTTOMRIGHT.put(2, "diable_reioibuiltin");
    }

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {

    }
}
