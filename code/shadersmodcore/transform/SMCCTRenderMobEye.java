package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTRenderMobEye implements IClassTransformer {
    public SMCCTRenderMobEye() {
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
            String nameM = name;
            String descM = desc;
            if (nameM.equals("func_77097_a") && descM.equals("(Lnet/minecraft/entity/monster/EntitySpider;IF)I")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVrenderMobEye(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_77074_a") && descM.equals("(Lnet/minecraft/entity/monster/EntityEnderman;IF)I")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVrenderMobEye(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVrenderMobEye extends MethodVisitor {
        int state = 0;

        public MVrenderMobEye(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 4 && this.state == 2) {
                ++this.state;
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginSpiderEyes", "()V");
            }

            this.mv.visitInsn(opcode);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (owner.equals("org/lwjgl/opengl/GL11") && name.equals("glColor4f") && desc.equals("(FFFF)V") && this.state < 2) {
                ++this.state;
            }

            this.mv.visitMethodInsn(opcode, owner, name, desc);
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

