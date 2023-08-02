package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTBlock implements IClassTransformer {
    public SMCCTBlock() {
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
            return (MethodVisitor)(remappedName.equals("func_71888_h") ? new SMCCTBlock.MVgetAoLight(this.cv.visitMethod(access, name, desc, signature, exceptions)) : this.cv.visitMethod(access, name, desc, signature, exceptions));
        }
    }

    class MVgetAoLight extends MethodVisitor {
        public MVgetAoLight(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitLdcInsn(Object cst) {
            if (cst instanceof Float && (Float)cst == 0.2F) {
                this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "blockAoLight", "F");
                SMCLog.logger.info("   blockAoLight");
            } else {
                this.mv.visitLdcInsn(cst);
            }
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
