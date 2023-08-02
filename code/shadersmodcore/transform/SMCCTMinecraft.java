//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class SMCCTMinecraft implements IClassTransformer {
    public SMCCTMinecraft() {
    }

    class CVTransform extends ClassVisitor {
        String classname;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            String remappedName = SMCRemap.remapper.mapMethodName(this.classname, name, desc);
            return (MethodVisitor)(remappedName.equals("func_71384_a") ? new SMCCTMinecraft.MVstartGame(super.visitMethod(access, name, desc, signature, exceptions)) : super.visitMethod(access, name, desc, signature, exceptions));
        }
    }

    class MVstartGame extends MethodVisitor {
        int state = 0;

        public MVstartGame(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitLdcInsn(Object cst) {
            if (cst instanceof String && ((String)cst).equals("Startup") && this.state == 0) {
                this.state = 1;
            }

            super.visitLdcInsn(cst);
        }

        public void visitVarInsn(int opcode, int var) {
            if (opcode == 25 && var == 0 && this.state == 1) {
                this.state = 2;
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "startup", "(Lnet/minecraft/client/Minecraft;)V");
                SMCLog.finest("    startup", new Object[0]);
            }

            super.visitVarInsn(opcode, var);
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
