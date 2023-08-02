//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.transform;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCClassTransformer implements IClassTransformer {
    protected Map<String, IClassTransformer> ctMap = new HashMap();

    public SMCClassTransformer() {
        this.ctMap.put("net.minecraft.block.Block", new SMCCTBlock());
        this.ctMap.put("net.minecraft.client.Minecraft", new SMCCTMinecraft());
        this.ctMap.put("net.minecraft.client.gui.GuiOptions", new SMCCTGuiOptions());
        this.ctMap.put("net.minecraft.client.renderer.OpenGlHelper", new SMCCTOpenGlHelper());
        this.ctMap.put("net.minecraft.client.renderer.RenderBlocks", new SMCCTRenderBlocks());
        this.ctMap.put("net.minecraft.client.renderer.RenderGlobal", new SMCCTRenderGlobal());
        this.ctMap.put("net.minecraft.client.renderer.entity.Render", new SMCCTRender());
        this.ctMap.put("net.minecraft.client.renderer.entity.RendererLivingEntity", new SMCCTRendererLivingEntity());
        this.ctMap.put("net.minecraft.client.renderer.ThreadDownloadImageData", new SMCCTTextureDownload());
        this.ctMap.put("net.minecraft.client.renderer.texture.AbstractTexture", new SMCCTTextureAbstract());
        this.ctMap.put("net.minecraft.client.renderer.texture.TextureObject", new SMCCTTextureObject());
        this.ctMap.put("net.minecraft.client.renderer.texture.SimpleTexture", new SMCCTTextureSimple());
        this.ctMap.put("net.minecraft.client.renderer.texture.LayeredTexture", new SMCCTTextureLayered());
        this.ctMap.put("net.minecraft.client.renderer.texture.DynamicTexture", new SMCCTTextureDynamic());
        this.ctMap.put("net.minecraft.client.renderer.texture.TextureMap", new SMCCTTextureMap());
        this.ctMap.put("net.minecraft.client.renderer.texture.TextureAtlasSprite", new SMCCTTextureAtlasSprite());
        this.ctMap.put("net.minecraft.client.renderer.texture.TextureClock", new SMCCTTextureClock());
        this.ctMap.put("net.minecraft.client.renderer.texture.TextureCompass", new SMCCTTextureCompass());
        this.ctMap.put("net.minecraft.client.renderer.texture.TextureManager", new SMCCTTextureManager());
        this.ctMap.put("net.minecraft.client.renderer.entity.RenderEnderman", new SMCCTRenderMobEye());
        this.ctMap.put("net.minecraft.client.renderer.entity.RenderSpider", new SMCCTRenderMobEye());
        this.ctMap.put("net.minecraft.client.renderer.Tessellator", new SMCCTTessellator());
        this.ctMap.put("net.minecraft.client.renderer.EntityRenderer", new SMCCTEntityRenderer());
        this.ctMap.put("net.minecraft.client.model.ModelRenderer", new SMCCTModelRenderer());
    }

    public byte[] transform(String par1, String par2, byte[] par3) {
        byte[] bytecode = par3;
        IClassTransformer ct = (IClassTransformer)this.ctMap.get(par2);
        if (ct != null) {
            bytecode = ct.transform(par1, par2, par3);
            SMCLog.saveTransformedClass(bytecode, par2);
            int len1 = par3.length;
            int len2 = bytecode.length;
            SMCLog.fine(" %d (%+d)", new Object[]{len2, len2 - len1});
        }

        return bytecode;
    }
}
