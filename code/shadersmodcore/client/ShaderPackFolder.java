//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ShaderPackFolder implements IShaderPack {
    protected File packFile;

    public ShaderPackFolder(String name, File file) {
        this.packFile = file;
    }

    public void close() {
    }

    public InputStream getResourceAsStream(String resName) {
        try {
            File resFile = new File(this.packFile, resName.substring(1));
            if (resFile != null) {
                return new BufferedInputStream(new FileInputStream(resFile));
            }
        } catch (Exception var3) {
        }

        return null;
    }
}
