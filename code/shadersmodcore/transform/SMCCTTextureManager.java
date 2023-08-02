package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureManager implements IClassTransformer {
    public SMCCTTextureManager() {
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
            if (nameM.equals("func_110577_a") && descM.equals("(Lnet/minecraft/util/ResourceLocation;)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s%s", new Object[]{this.classname, name, desc, nameM, descM});
                return new SMCCTTextureManager.MVbindTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_110549_a") && descM.equals("(Lnet/minecraft/client/resources/ResourceManager;)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s%s", new Object[]{this.classname, name, desc, nameM, descM});
                return new SMCCTTextureManager.MVonReload(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVbindTexture extends MethodVisitor {
        public MVbindTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/renderer/texture/TextureObject") && nameM.equals("func_110552_b") && descM.equals("()I")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "bindTexture", "(Lnet/minecraft/client/renderer/texture/TextureObject;)V");
            } else if (!ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") || !nameM.equals("func_94277_a") || !descM.equals("(I)V")) {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVonReload extends MethodVisitor {
        public MVonReload(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/renderer/texture/TextureObject") && nameM.equals("func_110552_b") && descM.equals("()I")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "deleteMultiTex", "(Lnet/minecraft/client/renderer/texture/TextureObject;)I");
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
