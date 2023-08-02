package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTextureAtlasSprite implements IClassTransformer {
    public SMCCTTextureAtlasSprite() {
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

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            access = access & -7 | 1;
            return this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            String nameM = SMCRemap.remapper.mapMethodName(this.classname, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            access = access & -7 | 1;
            if (nameM.equals("func_94219_l") && descM.equals("()V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureAtlasSprite.MVanimation(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("load") && descM.equals("(Lnet/minecraft/client/resources/ResourceManager;Lnet/minecraft/util/ResourceLocation;)Z")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureAtlasSprite.MVload(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_130100_a") && descM.equals("(Lnet/minecraft/client/resources/Resource;)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureAtlasSprite.MVloadSprite(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("uploadFrameTexture") && descM.equals("(III)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTTextureAtlasSprite.MVuploadFrameTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

     static class MVanimation extends MethodVisitor {
        public MVanimation(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/renderer/texture/TextureUtil") && nameM.equals("func_110998_a") && descM.equals("([IIIIIZZ)V")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "updateSubImage", "([IIIIIZZ)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVload extends MethodVisitor {
        public MVload(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/resources/ResourceManager") && nameM.equals("func_110536_a") && descM.equals("(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/Resource;")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "loadResource", "(Lnet/minecraft/client/resources/ResourceManager;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/Resource;");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVloadSprite extends MethodVisitor {
        public MVloadSprite(MethodVisitor mv) {
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
            if (ownerM.equals("java/awt/image/BufferedImage") && nameM.equals("getRGB") && descM.equals("(IIII[III)[I")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "loadAtlasSprite", "(Ljava/awt/image/BufferedImage;IIII[III)[I");
            } else if (ownerM.equals("net/minecraft/client/renderer/texture/TextureAtlasSprite") && nameM.equals("func_130101_a") && descM.equals("([IIII)[I")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "extractFrame", "([IIII)[I");
            } else if (ownerM.equals("net/minecraft/client/renderer/texture/TextureAtlasSprite") && nameM.equals("fixTransparentColor") && descM.equals("([I)V")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "fixTransparentColor", "(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;[I)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVuploadFrameTexture extends MethodVisitor {
        public MVuploadFrameTexture(MethodVisitor mv) {
            super(262144, (MethodVisitor)null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(21, 1);
            mv.visitVarInsn(21, 2);
            mv.visitVarInsn(21, 3);
            mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTex", "uploadFrameTexture", "(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;III)V");
            mv.visitInsn(177);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", (String)null, l0, l2, 0);
            mv.visitLocalVariable("frameIndex", "I", (String)null, l0, l2, 1);
            mv.visitLocalVariable("xPos", "I", (String)null, l0, l2, 2);
            mv.visitLocalVariable("yPos", "I", (String)null, l0, l2, 3);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
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
