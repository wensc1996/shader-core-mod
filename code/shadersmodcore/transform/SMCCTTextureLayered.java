package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureLayered implements IClassTransformer {
    public SMCCTTextureLayered() {
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
            if (nameM.equals("func_110551_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVloadTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVloadTexture extends MethodVisitor {
        public MVloadTexture(MethodVisitor mv) {
            super(262144);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(25, 1);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/renderer/texture/LayeredTexture", "field_110567_b", "Ljava/util/List;");
            mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "loadLayeredTexture", "(Lnet/minecraft/client/renderer/texture/LayeredTexture;Lnet/minecraft/client/resources/ResourceManager;Ljava/util/List;)V");
            mv.visitInsn(177);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/texture/LayeredTexture;", (String)null, l0, l2, 0);
            mv.visitLocalVariable("manager", "Lnet/minecraft/client/resources/ResourceManager;", (String)null, l0, l2, 1);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
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
