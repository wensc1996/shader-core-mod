package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureDownload implements IClassTransformer {
    public SMCCTTextureDownload() {
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
            String nameM = SMCRemap.remapper.mapMethodName(this.classname, name, desc);
            return nameM.equals("getMultiTexID") ? null : this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            MethodVisitor mv = SMCRemap.getAdaptor(this.cv.visitMethod(1, "getMultiTexID", "()Lshadersmodcore/client/MultiTexID;", (String)null, (String[])null));
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/renderer/ThreadDownloadImageData", "field_110559_g", "Z");
            Label l1 = new Label();
            mv.visitJumpInsn(154, l1);
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(182, "net/minecraft/client/renderer/ThreadDownloadImageData", "func_110552_b", "()I");
            mv.visitInsn(87);
            mv.visitLabel(l1);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(183, "net/minecraft/client/renderer/texture/AbstractTexture", "getMultiTexID", "()Lshadersmodcore/client/MultiTexID;");
            mv.visitInsn(176);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/ThreadDownloadImageData;", (String)null, l0, l3, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            this.cv.visitEnd();
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
