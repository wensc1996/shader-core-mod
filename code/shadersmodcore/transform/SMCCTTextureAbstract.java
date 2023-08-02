package shadersmodcore.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import shadersmodcore.transform.SMCLog;

public class SMCCTTextureAbstract implements IClassTransformer {
    public SMCCTTextureAbstract() {
    }

    class CVTransform extends ClassVisitor {
        String classname;
        boolean endFields = false;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            String nameM = SMCRemap.remapper.mapFieldName(this.classname, name, desc);
            return nameM.equals("multiTex") ? null : this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, "multiTex", "Lshadersmodcore/client/MultiTexID;", (String)null, (Object)null);
                fv.visitEnd();
            }

            String nameM = SMCRemap.remapper.mapMethodName(this.classname, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            return nameM.equals("getMultiTexID") && descM.equals("()Lshadersmodcore/client/MultiTexID;") ? null : this.cv.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            MethodVisitor mv = this.cv.visitMethod(1, "getMultiTexID", "()Lshadersmodcore/client/MultiTexID;", (String)null, (String[])null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "getMultiTexID", "(Lnet/minecraft/client/renderer/texture/AbstractTexture;)Lshadersmodcore/client/MultiTexID;");
            mv.visitInsn(176);
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
