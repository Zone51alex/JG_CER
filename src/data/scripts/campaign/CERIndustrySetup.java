package data.scripts.campaign;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class CERIndustrySetup implements EveryFrameScript {

    private boolean ran = false;

    public CERIndustrySetup() {
        if (!ran) {
            setupCorvidIndustries();
            ran = true;
        }
    }

    private void setupCorvidIndustries() {
        MarketAPI market = Global.getSector().getEconomy().getMarket("OT_a");
        if (market == null || !"da_cer".equals(market.getFactionId())) return;

        boolean hasIndEvo = Global.getSettings().getModManager().isModEnabled("IndEvo");
        if (!hasIndEvo) return;  // no point otherwise

        addIfMissing(market, "IndEvo_dryDock");

        // optional: upgrade orbitalworks if IndEvo has better variant
        // if (market.getIndustry("orbitalworks") != null && hasIndEvo) {
        //     market.removeIndustry("orbitalworks", null, false);
        //     market.addIndustry("IndEvo_advancedHeavyIndustry"); // if exists
        // }
    }

    private void addIfMissing(MarketAPI m, String id) {
        if (m.getIndustry(id) != null) return;
        if (Global.getSettings().getIndustrySpec(id) == null) return;  // still missing → skip
        m.addIndustry(id);
    }

    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {

    }
}