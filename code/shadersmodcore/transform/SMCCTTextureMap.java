//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class SMCCTTextureMap implements IClassTransformer {
    public SMCCTTextureMap() {
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
            if (name.equals("atlasWidth")) {
                return null;
            } else {
                return name.equals("atlasHeight") ? null : super.visitField(access, name, desc, signature, value);
            }
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, "atlasWidth", "I", (String)null, (Object)null);
                fv.visitEnd();
                fv = this.cv.visitField(1, "atlasHeight", "I", (String)null, (Object)null);
                fv.visitEnd();
            }

            String nameM = SMCRemap.remapper.mapMethodName(this.classname, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (nameM.equals("func_110571_b") && descM.equals("(Lnet/minecraft/client/resources/ResourceManager;)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureMap.MVloadAtlas(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_94248_c") && descM.equals("()V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureMap.MVanimation(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVanimation extends MethodVisitor {
        public MVanimation(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitMethodInsn(182, "net/minecraft/client/renderer/texture/TextureMap", "getMultiTexID", "()Lshadersmodcore/client/MultiTexID;");
            this.mv.visitFieldInsn(179, "shadersmodcore/client/ShadersTex", "updatingTex", "Lshadersmodcore/client/MultiTexID;");
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitInsn(1);
                this.mv.visitFieldInsn(179, "shadersmodcore/client/ShadersTex", "updatingTex", "Lshadersmodcore/client/MultiTexID;");
            }

            this.mv.visitInsn(opcode);
        }
    }

    class MVloadAtlas extends MethodVisitor {
        int lastAload = 0;

        public MVloadAtlas(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 188 && operand == 10) {
                this.mv.visitInsn(6);
                this.mv.visitInsn(104);
            }

            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitVarInsn(int opcode, int var) {
            if (opcode == 25) {
                this.lastAload = var;
            }

            this.mv.visitVarInsn(opcode, var);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/resources/ResourceManager") && nameM.equals("func_110536_a") && descM.equals("(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/Resource;")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "loadResource", "(Lnet/minecraft/client/resources/ResourceManager;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/Resource;");
            } else if (ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") && nameM.equals("func_110991_a") && descM.equals("(III)V")) {
                this.mv.visitVarInsn(25, this.lastAload);
                this.mv.visitVarInsn(25, 0);
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "setupTextureMap", "(IIILnet/minecraft/client/renderer/texture/Stitcher;Lnet/minecraft/client/renderer/texture/TextureMap;)V");
            } else if (ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") && nameM.equals("func_110998_a") && descM.equals("([IIIIIZZ)V")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "updateTextureMap", "([IIIIIZZ)V");
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
