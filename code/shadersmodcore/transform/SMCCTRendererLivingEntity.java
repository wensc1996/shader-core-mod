package shadersmodcore.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class SMCCTRendererLivingEntity implements IClassTransformer {
    public SMCCTRendererLivingEntity() {
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
            String nameM = name;
            if (nameM.equals("field_77045_g") || nameM.equals("field_77046_h")) {
                access = access & -8 | 1;
            }

            return this.cv.visitField(access, name, desc, signature, value);
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            String nameM = name;
            if (nameM.equals("func_130000_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVdoRenderLiving(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_77038_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVrenderLivingLabel(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVdoRenderLiving extends MethodVisitor {
        private int lastInt = 0;
        private int state = 0;
        Label labelEndVH = null;

        public MVdoRenderLiving(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "useEntityHurtFlash", "Z");
            Label label1 = new Label();
            this.mv.visitJumpInsn(153, label1);
            this.mv.visitVarInsn(25, 1);
            this.mv.visitFieldInsn(180, "net/minecraft/entity/EntityLivingBase", "field_70737_aN", "I");
            Label label2 = new Label();
            this.mv.visitJumpInsn(157, label2);
            this.mv.visitVarInsn(25, 1);
            this.mv.visitFieldInsn(180, "net/minecraft/entity/EntityLivingBase", "field_70725_aQ", "I");
            Label label3 = new Label();
            this.mv.visitJumpInsn(158, label3);
            this.mv.visitLabel(label2);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            this.mv.visitIntInsn(16, 102);
            Label label4 = new Label();
            this.mv.visitJumpInsn(167, label4);
            this.mv.visitLabel(label3);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            this.mv.visitInsn(3);
            this.mv.visitLabel(label4);
            this.mv.visitFrame(4, 0, (Object[])null, 1, new Object[]{Opcodes.INTEGER});
            this.mv.visitVarInsn(25, 0);
            this.mv.visitVarInsn(25, 1);
            this.mv.visitVarInsn(25, 1);
            this.mv.visitVarInsn(23, 9);
            this.mv.visitMethodInsn(182, "net/minecraft/entity/EntityLivingBase", "func_70013_c", "(F)F");
            this.mv.visitVarInsn(23, 9);
            this.mv.visitMethodInsn(182, "net/minecraft/client/renderer/entity/RendererLivingEntity", "func_77030_a", "(Lnet/minecraft/entity/EntityLivingBase;FF)I");
            this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "setEntityHurtFlash", "(II)V");
            this.mv.visitLabel(label1);
            this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 17) {
                this.lastInt = operand;
            }

            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitLdcInsn(Object cst) {
            if (cst instanceof Integer) {
                int icst = (Integer)cst;
                if (icst == 32826 && this.labelEndVH != null) {
                    this.mv.visitLabel(this.labelEndVH);
                    this.labelEndVH = null;
                }
            }

            this.mv.visitLdcInsn(cst);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = owner;
            String nameM = name;
            String descM = desc;
            if (opcode == 182 && ownerM.equals("net/minecraft/client/renderer/entity/RendererLivingEntity") && descM.equals("(Lnet/minecraft/entity/EntityLivingBase;F)V") && nameM.equals("func_77029_c")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "resetEntityHurtFlash", "()V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "useEntityHurtFlash", "Z");
                this.labelEndVH = new Label();
                this.mv.visitJumpInsn(154, this.labelEndVH);
                this.state = 1;
            } else {
                super.visitMethodInsn(opcode, owner, name, desc);
                if (opcode == 184) {
                    if (ownerM.equals("org/lwjgl/opengl/GL11")) {
                        if (descM.equals("(I)V") && nameM.equals("glDepthFunc")) {
                            if (this.state == 3) {
                                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginLivingDamage", "()V");
                                ++this.state;
                            } else if (this.state == 4) {
                                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endLivingDamage", "()V");
                                ++this.state;
                            }
                        }
                    } else if (ownerM.equals("net/minecraft/client/renderer/OpenGlHelper") && descM.equals("(I)V") && nameM.equals("func_77473_a")) {
                        if (this.state == 1) {
                            ++this.state;
                        } else if (this.state == 2) {
                            this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableLightmap", "()V");
                            ++this.state;
                        } else if (this.state == 5) {
                            ++this.state;
                        } else if (this.state == 6) {
                            this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "enableLightmap", "()V");
                            ++this.state;
                        }
                    }
                }

            }
        }
    }

    class MVrenderLivingLabel extends MethodVisitor {
        int pushedInt = 0;

        public MVrenderLivingLabel(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            this.mv.visitIntInsn(opcode, operand);
            if (opcode == 17 && operand == 3553) {
                this.pushedInt = 3553;
            }

        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (this.pushedInt == 3553) {
                this.pushedInt = 0;
                if (opcode == 184 && owner.equals("org/lwjgl/opengl/GL11") && desc.equals("(I)V")) {
                    if (name.equals("glDisable")) {
                        owner = "shadersmodcore/client/Shaders";
                        name = "sglDisableT2D";
                    } else if (name.equals("glEnable")) {
                        owner = "shadersmodcore/client/Shaders";
                        name = "sglEnableT2D";
                    }
                }
            }

            this.mv.visitMethodInsn(opcode, owner, name, desc);
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
