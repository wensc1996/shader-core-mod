//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.ResourceManager;

public class DefaultTexture extends AbstractTexture {
    public DefaultTexture() {
        this.func_110551_a((ResourceManager)null);
    }

    public void func_110551_a(ResourceManager resourcemanager) {
        int[] aint = ShadersTex.createAIntImage(1, -1);
        ShadersTex.setupTexture(this.getMultiTexID(), aint, 1, 1, false, false);
    }
}
