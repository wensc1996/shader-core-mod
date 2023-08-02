package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureDynamic implements IClassTransformer {
    public SMCCTTextureDynamic() {
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
            if (nameM.equals("<init>") && descM.equals("(II)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureDynamic.MVinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_110564_a") && descM.equals("()V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureDynamic.MVupdate(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }


    class MVinit extends MethodVisitor {
        public MVinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 188 && operand == 10) {
                this.mv.visitInsn(6);
                this.mv.visitInsn(104);
            }

            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") && nameM.equals("func_110991_a") && descM.equals("(III)V")) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "initDynamicTexture", "(IIILnet/minecraft/client/renderer/texture/DynamicTexture;)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }


    class MVupdate extends MethodVisitor {
        public MVupdate(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") && nameM.equals("func_110988_a") && descM.equals("(I[III)V")) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "updateDynamicTexture", "(I[IIILnet/minecraft/client/renderer/texture/DynamicTexture;)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
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
