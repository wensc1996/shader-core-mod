package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureSimple implements IClassTransformer {
    private static final int logDetail = 0;

    public SMCCTTextureSimple() {
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
            if (nameM.equals("func_110551_a") && descM.equals("(Lnet/minecraft/client/resources/ResourceManager;)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s%s", new Object[]{this.classname, name, desc, nameM, descM});
                return new SMCCTTextureSimple.MVloadTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVloadTexture extends MethodVisitor {
        public MVloadTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") && nameM.equals("func_110989_a") && descM.equals("(ILjava/awt/image/BufferedImage;ZZ)I")) {
                this.mv.visitVarInsn(25, 1);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/texture/SimpleTexture", "field_110568_b", "Lnet/minecraft/util/ResourceLocation;");
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(182, "net/minecraft/client/renderer/texture/SimpleTexture", "getMultiTexID", "()Lshadersmodcore/client/MultiTexID;");
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "loadSimpleTexture", "(ILjava/awt/image/BufferedImage;ZZLnet/minecraft/client/resources/ResourceManager;Lnet/minecraft/util/ResourceLocation;Lshadersmodcore/client/MultiTexID;)I");
                SMCLog.finer("    loadSimpleTexture", new Object[0]);
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
