//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

class GuiSlotShaders extends GuiSlot {
    private ArrayList shaderslist;
    private int scrollBarX;
    final GuiShaders shadersGui;

    public GuiSlotShaders(GuiShaders par1GuiShaders) {
        super(par1GuiShaders.getMc(), par1GuiShaders.field_73880_f / 2 + 20, par1GuiShaders.field_73881_g, 40, par1GuiShaders.field_73881_g - 70, 16);
        this.scrollBarX = par1GuiShaders.field_73880_f / 2 + 14;
        this.shadersGui = par1GuiShaders;
        this.shaderslist = Shaders.listofShaders();
    }

    public void updateList() {
        this.shaderslist = Shaders.listofShaders();
    }

    protected int func_77217_a() {
        return this.shaderslist.size();
    }

    protected void func_77213_a(int par1, boolean par2) {
        Shaders.setShaderPack((String)this.shaderslist.get(par1));
        this.shadersGui.needReinit = false;
        Shaders.loadShaderPack();
        Shaders.uninit();
    }

    protected boolean func_77218_a(int par1) {
        return ((String)this.shaderslist.get(par1)).equals(Shaders.currentshadername);
    }

    protected int func_77225_g() {
        return this.scrollBarX;
    }

    protected int func_77212_b() {
        return this.func_77217_a() * 18;
    }

    protected void func_77221_c() {
        this.shadersGui.func_73873_v_();
    }

    protected void func_77206_b(int par1, int par2, int par3, int par4) {
    }

    protected void drawContainerBackground(Tessellator tess) {
    }

    protected void func_77214_a(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
        this.shadersGui.drawCenteredString((String)this.shaderslist.get(par1), this.scrollBarX / 2, par3 + 1, 16777215);
    }
}
