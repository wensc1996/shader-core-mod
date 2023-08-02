package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTOpenGlHelper implements IClassTransformer {
    public SMCCTOpenGlHelper() {
    }
    class CVTransform extends ClassVisitor {
        String classname;
        boolean has_activeTexUnit = false;

        public CVTransform(ClassVisitor cv) {
            super(262144, cv);
        }

        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.classname = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (name.equals("activeTexUnit")) {
                this.has_activeTexUnit = true;
            }

            return this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.has_activeTexUnit) {
                this.has_activeTexUnit = true;
                FieldVisitor fv = this.cv.visitField(9, "activeTexUnit", "I", (String)null, (Object)null);
                fv.visitEnd();
                SMCLog.finest("    add field activeTexUnit", new Object[0]);
            }

            String remappedName = name;
            return (MethodVisitor)(remappedName.equals("func_77473_a") ? new MVsetActiveTexture(this.cv.visitMethod(access, name, desc, signature, exceptions)) : this.cv.visitMethod(access, name, desc, signature, exceptions));
        }
    }

    class MVsetActiveTexture extends MethodVisitor {
        public MVsetActiveTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(21, 0);
            this.mv.visitFieldInsn(179, "net/minecraft/client/renderer/OpenGlHelper", "activeTexUnit", "I");
            SMCLog.finest("    set activeTexUnit", new Object[0]);
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
