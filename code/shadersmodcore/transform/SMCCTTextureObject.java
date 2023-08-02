package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureObject implements IClassTransformer {
    public SMCCTTextureObject() {
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
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            return nameM.equals("getMultiTexID") && descM.equals("()Lshadersmodcore/client/MultiTexID;") ? null : this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            MethodVisitor mv = this.cv.visitMethod(1025, "getMultiTexID", "()Lshadersmodcore/client/MultiTexID;", (String)null, (String[])null);
            mv.visitEnd();
            this.cv.visitEnd();
        }
    }


    public byte[] transform(String par1, String par2, byte[] par3) {
        SMCLog.fine("transforming %s %s", new Object[]{par1, par2});
        ClassReader cr = new ClassReader(par3);
        ClassWriter cw = new ClassWriter(cr, 0);
        CVTransform cv = new CVTransform(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
