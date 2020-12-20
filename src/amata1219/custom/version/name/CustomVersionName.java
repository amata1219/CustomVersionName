package amata1219.custom.version.name;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class CustomVersionName extends Plugin implements Listener {

    private String name;

    @Override
    public void onEnable() {
        File f = new File(this.getDataFolder(), "config.yml");

        if (!f.exists()) {
            try {
                this.getDataFolder().mkdirs();
                f.createNewFile();
            } catch (IOException e) {

            }

            FileOutputStream os = null;
            InputStream is = this.getResourceAsStream("config.yml");
            try {
                os = new FileOutputStream(f);
                ByteStreams.copy(is, os);
            } catch (Throwable e) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                e.printStackTrace();
                return;
            }
        }

        try {
            Configuration c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
            this.name = c.getString("VersionName");
        } catch (IOException var24) {
            var24.printStackTrace();
        }

        this.getProxy().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        e.getResponse().getVersion().setName(this.name);
    }

}
