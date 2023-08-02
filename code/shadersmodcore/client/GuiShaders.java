//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Util;
import org.lwjgl.Sys;

public class GuiShaders extends GuiScreen {
    protected GuiScreen parentGui;
    private int updateTimer = -1;
    public boolean needReinit = false;
    public boolean needRefreshResource = false;
    private GuiSlotShaders shaderList;

    public GuiShaders(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
        this.parentGui = par1GuiScreen;
    }

    private static String toStringOnOff(boolean value) {
        return value ? "On" : "Off";
    }

    public void func_73866_w_() {
        if (Shaders.shadersConfig == null) {
            Shaders.loadConfig();
        }

        List buttonList = this.field_73887_h;
        int width = this.field_73880_f;
        int height = this.field_73881_g;
        buttonList.add(new GuiButton(15, width * 3 / 4 - 60, 70, 160, 18, "RenderResMul: " + String.format("%.04f", Shaders.configRenderResMul)));
        buttonList.add(new GuiButton(16, width * 3 / 4 - 60, 90, 160, 18, "ShadowResMul: " + String.format("%.04f", Shaders.configShadowResMul)));
        buttonList.add(new GuiButton(10, width * 3 / 4 - 60, 110, 160, 18, "HandDepth: " + String.format("%.04f", Shaders.configHandDepthMul)));
        buttonList.add(new GuiButton(9, width * 3 / 4 - 60, 130, 160, 18, "CloudShadow: " + toStringOnOff(Shaders.configCloudShadow)));
        buttonList.add(new GuiButton(14, width * 3 / 4 - 60, 150, 160, 18, "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum)));
        buttonList.add(new GuiButton(4, width * 3 / 4 - 60, 170, 160, 18, "tweakBlockDamage: " + toStringOnOff(Shaders.configTweakBlockDamage)));
        buttonList.add(new GuiButton(19, width * 3 / 4 - 60, 190, 160, 18, "OldLighting: " + toStringOnOff(Shaders.configOldLighting)));
        buttonList.add(new GuiButton(6, width * 3 / 4 - 60, height - 25, 160, 20, "Done"));
        buttonList.add(new GuiButton(5, width / 4 - 80, height - 25, 160, 20, "Open shaderpacks folder"));
        this.shaderList = new GuiSlotShaders(this);
        this.shaderList.func_77220_a(7, 8);
        this.needReinit = false;
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73742_g) {
            float val;
            float[] choices;
            int i;
            switch (par1GuiButton.field_73741_f) {
                case 4:
                    Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                    par1GuiButton.field_73744_e = "tweakBlockDamage: " + toStringOnOff(Shaders.configTweakBlockDamage);
                    break;
                case 5:
                    switch (Util.func_110647_a()) {
                    case MACOS:
                        try {
                            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderpacksdir.getAbsolutePath()});
                            return;
                        } catch (IOException var8) {
                            var8.printStackTrace();
                            break;
                        }

                        case WINDOWS:
                        String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderpacksdir.getAbsolutePath());

                        try {
                            Runtime.getRuntime().exec(var2);
                            return;
                        } catch (IOException var7) {
                            var7.printStackTrace();
                        }
                }

                boolean var8 = false;

                try {
                    Class var3 = Class.forName("java.awt.Desktop");
                    Object var4 = var3.getMethod("getDesktop").invoke((Object)null);
                    var3.getMethod("browse", URI.class).invoke(var4, (new File(this.field_73882_e.field_71412_D, Shaders.shaderpacksdirname)).toURI());
                } catch (Throwable var6) {
                    var6.printStackTrace();
                    var8 = true;
                }

                if (var8) {
                    System.out.println("Opening via system class!");
                    Sys.openURL("file://" + Shaders.shaderpacksdir.getAbsolutePath());
                }
                break;
                case 6:
                    new File(Shaders.shadersdir, "current.cfg");

                    try {
                        Shaders.storeConfig();
                    } catch (Exception var5) {
                    }

                    if (this.needReinit) {
                        this.needReinit = false;
                        Shaders.loadShaderPack();
                        Shaders.uninit();
                        this.field_73882_e.field_71438_f.func_72712_a();
                    }

                    if (this.needRefreshResource) {
                        this.needRefreshResource = false;
                        this.field_73882_e.func_110436_a();
                    }

                    this.field_73882_e.func_71373_a(this.parentGui);
                    break;
                case 7:
                case 8:
                default:
                    this.shaderList.func_77219_a(par1GuiButton);
                    break;
                case 9:
                    Shaders.configCloudShadow = !Shaders.configCloudShadow;
                    par1GuiButton.field_73744_e = "CloudShadow: " + toStringOnOff(Shaders.configCloudShadow);
                    break;
                case 10:
                    val = Shaders.configHandDepthMul;
                    choices = new float[]{0.0625F, 0.125F, 0.25F, 0.5F, 1.0F};
                    if (!func_73877_p()) {
                        for(i = 0; i < choices.length && choices[i] <= val; ++i) {
                        }

                        if (i == choices.length) {
                            i = 0;
                        }
                    } else {
                        for(i = choices.length - 1; i >= 0 && val <= choices[i]; --i) {
                        }

                        if (i < 0) {
                            i = choices.length - 1;
                        }
                    }

                    Shaders.configHandDepthMul = choices[i];
                    par1GuiButton.field_73744_e = "HandDepth: " + String.format("%.4f", Shaders.configHandDepthMul);
                    break;
                case 11:
                    Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                    Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
                    par1GuiButton.field_73744_e = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case 12:
                    Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                    par1GuiButton.field_73744_e = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case 13:
                    Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                    par1GuiButton.field_73744_e = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case 14:
                    Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                    par1GuiButton.field_73744_e = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                case 15:
                    val = Shaders.configRenderResMul;
                    choices = new float[]{0.25F, 0.33333334F, 0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F};
                    if (!func_73877_p()) {
                        for(i = 0; i < choices.length && choices[i] <= val; ++i) {
                        }

                        if (i == choices.length) {
                            i = 0;
                        }
                    } else {
                        for(i = choices.length - 1; i >= 0 && val <= choices[i]; --i) {
                        }

                        if (i < 0) {
                            i = choices.length - 1;
                        }
                    }

                    Shaders.configRenderResMul = choices[i];
                    par1GuiButton.field_73744_e = "RenderResMul: " + String.format("%.4f", Shaders.configRenderResMul);
                    Shaders.scheduleResize();
                    break;
                case 16:
                    val = Shaders.configShadowResMul;
                    choices = new float[]{0.25F, 0.33333334F, 0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F, 3.0F, 4.0F};
                    if (!func_73877_p()) {
                        for(i = 0; i < choices.length && choices[i] <= val; ++i) {
                        }

                        if (i == choices.length) {
                            i = 0;
                        }
                    } else {
                        for(i = choices.length - 1; i >= 0 && val <= choices[i]; --i) {
                        }

                        if (i < 0) {
                            i = choices.length - 1;
                        }
                    }

                    Shaders.configShadowResMul = choices[i];
                    par1GuiButton.field_73744_e = "ShadowResMul: " + String.format("%.4f", Shaders.configShadowResMul);
                    Shaders.scheduleResizeShadow();
                    break;
                case 17:
                    Shaders.configNormalMap = !Shaders.configNormalMap;
                    par1GuiButton.field_73744_e = "NormapMap: " + toStringOnOff(Shaders.configNormalMap);
                    this.needRefreshResource = true;
                    break;
                case 18:
                    Shaders.configSpecularMap = !Shaders.configSpecularMap;
                    par1GuiButton.field_73744_e = "SpecularMap: " + toStringOnOff(Shaders.configSpecularMap);
                    this.needRefreshResource = true;
                    break;
                case 19:
                    Shaders.configOldLighting = !Shaders.configOldLighting;
                    par1GuiButton.field_73744_e = "OldLighting: " + toStringOnOff(Shaders.configOldLighting);
                    Shaders.updateBlockLightLevel();
                    this.field_73882_e.field_71438_f.func_72712_a();
            }
        }

    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.shaderList.func_77211_a(par1, par2, par3);
        if (this.updateTimer <= 0) {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }

        this.func_73732_a(this.field_73886_k, "Shaders ", this.field_73880_f / 2, 16, 16777215);
        this.func_73732_a(this.field_73886_k, " v2.2.3", this.field_73880_f - 40, 10, 8421504);
        super.func_73863_a(par1, par2, par3);
    }

    public void func_73876_c() {
        super.func_73876_c();
        --this.updateTimer;
    }

    public Minecraft getMc() {
        return this.field_73882_e;
    }

    public void drawCenteredString(String par1, int par2, int par3, int par4) {
        this.func_73732_a(this.field_73886_k, par1, par2, par3, par4);
    }
}
