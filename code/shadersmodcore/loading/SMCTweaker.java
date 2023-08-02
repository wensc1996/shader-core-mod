//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class SMCTweaker implements ITweaker {
    public List<String> args;
    public File gameDir;
    public File assetsDir;
    public String profile;

    public SMCTweaker() {
    }

    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = args;
        this.gameDir = gameDir;
        this.assetsDir = assetsDir;
        this.profile = profile;
    }

    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        launchClassLoader.addTransformerExclusion("shadersmodcore.loading.");
        launchClassLoader.addTransformerExclusion("shadersmodcore.transform.");
        launchClassLoader.addTransformerExclusion("shadersmodcore.client.");
        launchClassLoader.registerTransformer("shadersmodcore.transform.SMCClassTransformer");
    }

    public String[] getLaunchArguments() {
        ArrayList argumentList = (ArrayList)Launch.blackboard.get("ArgumentList");
        if (argumentList.isEmpty()) {
            new ArrayList();
            if (this.gameDir != null) {
                argumentList.add("--gameDir");
                argumentList.add(this.gameDir.getPath());
            }

            if (this.assetsDir != null) {
                argumentList.add("--assetsDir");
                argumentList.add(this.assetsDir.getPath());
            }

            argumentList.add("--version");
            argumentList.add(this.profile);
            argumentList.addAll(this.args);
        }

        return new String[0];
    }

    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }
}
