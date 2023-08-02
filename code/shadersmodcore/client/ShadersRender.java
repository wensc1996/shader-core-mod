//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustrum;
import org.lwjgl.opengl.GL11;

public class ShadersRender {
    public ShadersRender() {
    }

    public static void setFrustrumPosition(Frustrum frustrum, double x, double y, double z) {
        frustrum.func_78547_a(x, y, z);
    }

    public static void clipRenderersByFrustrum(RenderGlobal renderGlobal, Frustrum frustrum, float par2) {
        Shaders.checkGLError("pre clip");
        if (!Shaders.isShadowPass || Shaders.configShadowClipFrustrum) {
            renderGlobal.func_72729_a(frustrum, par2);
            Shaders.checkGLError("clip");
        }

    }

    public static void renderItemFP(ItemRenderer itemRenderer, float par1) {
        GL11.glDepthFunc(518);
        GL11.glPushMatrix();
        itemRenderer.func_78440_a(par1);
        GL11.glPopMatrix();
        GL11.glDepthFunc(515);
        itemRenderer.func_78440_a(par1);
    }
}
