package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTGuiOptions implements IClassTransformer {
    public SMCCTGuiOptions() {
    }
    class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            String remappedName = name;
            if (remappedName.equals("func_73866_w_")) {
                return new MVinitGui(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return (MethodVisitor)(remappedName.equals("func_73875_a") ? new MVactionPerformed(this.cv.visitMethod(access, name, desc, signature, exceptions)) : this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
        }
    }

    class MVactionPerformed extends MethodVisitor {
        public MVactionPerformed(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            super.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiButton", "field_73741_f", "I");
            this.mv.visitIntInsn(17, 190);
            Label l1 = new Label();
            this.mv.visitJumpInsn(160, l1);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiOptions", "field_73882_e", "Lnet/minecraft/client/Minecraft;");
            this.mv.visitFieldInsn(180, "net/minecraft/client/Minecraft", "field_71474_y", "Lnet/minecraft/client/settings/GameSettings;");
            this.mv.visitMethodInsn(182, "net/minecraft/client/settings/GameSettings", "func_74303_b", "()V");
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiOptions", "field_73882_e", "Lnet/minecraft/client/Minecraft;");
            this.mv.visitTypeInsn(187, "shadersmodcore/client/GuiShaders");
            this.mv.visitInsn(89);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiOptions", "field_74051_d", "Lnet/minecraft/client/settings/GameSettings;");
            this.mv.visitMethodInsn(183, "shadersmodcore/client/GuiShaders", "<init>", "(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/settings/GameSettings;)V");
            this.mv.visitMethodInsn(182, "net/minecraft/client/Minecraft", "func_71373_a", "(Lnet/minecraft/client/gui/GuiScreen;)V");
            this.mv.visitLabel(l1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            SMCLog.finest("    shaders button action", new Object[0]);
        }
    }

    class MVinitGui extends MethodVisitor {
        int state = 0;

        public MVinitGui(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            super.visitInsn(opcode);
            if (opcode == 87 && this.state == 1) {
                this.state = 2;
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiOptions", "field_73887_h", "Ljava/util/List;");
                this.mv.visitTypeInsn(187, "net/minecraft/client/gui/GuiButton");
                this.mv.visitInsn(89);
                this.mv.visitIntInsn(17, 190);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiOptions", "field_73880_f", "I");
                this.mv.visitInsn(5);
                this.mv.visitInsn(108);
                this.mv.visitIntInsn(17, 152);
                this.mv.visitInsn(100);
                this.mv.visitIntInsn(16, 77);
                this.mv.visitInsn(96);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, "net/minecraft/client/gui/GuiOptions", "field_73881_g", "I");
                this.mv.visitIntInsn(16, 6);
                this.mv.visitInsn(108);
                this.mv.visitIntInsn(16, 120);
                this.mv.visitInsn(96);
                this.mv.visitIntInsn(16, 6);
                this.mv.visitInsn(100);
                this.mv.visitIntInsn(16, 73);
                this.mv.visitIntInsn(16, 20);
                this.mv.visitLdcInsn("Shaders...");
                this.mv.visitMethodInsn(183, "net/minecraft/client/gui/GuiButton", "<init>", "(IIIIILjava/lang/String;)V");
                this.mv.visitMethodInsn(185, "java/util/List", "add", "(Ljava/lang/Object;)Z");
                this.mv.visitInsn(87);
                SMCLog.finest("    add shaders button", new Object[0]);
            }

        }

        public void visitLdcInsn(Object cst) {
            if (cst instanceof String && ((String)cst).equals("options.language") && this.state == 0) {
                this.state = 1;
                this.mv.visitInsn(100);
                this.mv.visitIntInsn(16, 57);
                this.mv.visitInsn(100);
                this.mv.visitIntInsn(16, 20);
                SMCLog.finest("    decrease language button size", new Object[0]);
            }

            super.visitLdcInsn(cst);
        }
    }

    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", new Object[]{par1, par2});
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 1);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
