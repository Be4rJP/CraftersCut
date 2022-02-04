package be4rjp.crafterscut.impl;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.nms.INMSHandler;
import org.bukkit.plugin.Plugin;

public class ImplCraftersCutAPI extends CraftersCutAPI {


    public ImplCraftersCutAPI(Plugin plugin, INMSHandler nmsHandler) {
        super(plugin, nmsHandler);
    }
}
