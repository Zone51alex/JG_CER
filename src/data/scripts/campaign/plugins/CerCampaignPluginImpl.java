// Plugin Implementation
package data.scripts.campaign.plugins;

import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.AICoreOfficerPlugin;
import com.fs.starfarer.api.campaign.BaseCampaignPlugin;
import data.campaign.ids.cer_ids;
import data.scripts.campaign.plugins.da_cer.CyanCoreOfficerPluginImpl;

public class CerCampaignPluginImpl extends BaseCampaignPlugin {


    public String getId() {
        return "CerCampaignPluginImpl";
    }

    @Override
    public PluginPick<AICoreOfficerPlugin> pickAICoreOfficerPlugin(String commodityId) {
        if (commodityId.equals(cer_ids.cercyancore)) return new PluginPick<>(new CyanCoreOfficerPluginImpl(), PickPriority.MOD_SET);
        return null;
    }
}

