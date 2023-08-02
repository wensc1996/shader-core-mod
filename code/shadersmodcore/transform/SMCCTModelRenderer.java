package shadersmodcore.transform;
import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTModelRenderer implements IClassTransformer {
    public SMCCTModelRenderer() {
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
            return (!"resetDisplayList".equals(name) || !"()V".equals(desc)) && (!"getCompiled".equals(name) || !"()Z".equals(desc)) && (!"getDisplayList".equals(name) || !"()I".equals(desc)) ? this.cv.visitMethod(access, name, desc, signature, exceptions) : null;
        }

        public void visitEnd() {
            String mr_compiled_name = "field_78812_q";
            String mr_displayList_name = "field_78811_r";
            String ga_deleteDisplayLists_name = "func_74523_b";
            MethodVisitor mv = this.cv.visitMethod(1, "getCompiled", "()Z", (String)null, (String[])null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/model/ModelRenderer", mr_compiled_name, "Z");
            mv.visitInsn(172);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            mv = this.cv.visitMethod(1, "getDisplayList", "()I", (String)null, (String[])null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/model/ModelRenderer", mr_displayList_name, "I");
            mv.visitInsn(172);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            mv = this.cv.visitMethod(1, "resetDisplayList", "()V", (String)null, (String[])null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/model/ModelRenderer", mr_compiled_name, "Z");
            Label l1 = new Label();
            mv.visitJumpInsn(153, l1);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/model/ModelRenderer", mr_displayList_name, "I");
            mv.visitMethodInsn(184, "net/minecraft/client/renderer/GLAllocation", ga_deleteDisplayLists_name, "(I)V");
            mv.visitVarInsn(25, 0);
            mv.visitInsn(3);
            mv.visitFieldInsn(181, "net/minecraft/client/model/ModelRenderer", mr_displayList_name, "I");
            mv.visitVarInsn(25, 0);
            mv.visitInsn(3);
            mv.visitFieldInsn(181, "net/minecraft/client/model/ModelRenderer", mr_compiled_name, "Z");
            mv.visitLabel(l1);
            mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            mv.visitInsn(177);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
            super.visitEnd();
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
