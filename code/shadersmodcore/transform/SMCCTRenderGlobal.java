package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTRenderGlobal implements IClassTransformer {
    public SMCCTRenderGlobal() {
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
            String remappedName = name;
            if (remappedName.equals("func_72713_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                return new MVrenderEntities(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (remappedName.equals("func_72719_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                return new MVendisTexFog(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (remappedName.equals("func_72714_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                return new MVrenderSky(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (!remappedName.equals("func_72717_a") && !remappedName.equals("drawBlockDamageTexture")) {
                if (remappedName.equals("func_72731_b")) {
                    SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                    return new MVendisTexFog(this.cv.visitMethod(access, name, desc, signature, exceptions));
                } else {
                    return this.cv.visitMethod(access, name, desc, signature, exceptions);
                }
            } else {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                return new MVdrawBlockDamageTexture(this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
        }
    }

    class MVdrawBlockDamageTexture extends MethodVisitor {
        public MVdrawBlockDamageTexture(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            super.visitMethodInsn(opcode, owner, name, desc);
            if (owner.equals("org/lwjgl/opengl/GL11")) {
                if (name.equals("glBlendFunc")) {
                    this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginBlockDestroyProgress", "()V");
                } else if (name.equals("glPopMatrix")) {
                    this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endBlockDestroyProgress", "()V");
                }
            }

        }
    }

    class MVendisTexFog extends MethodVisitor {
        int lastInt = 0;

        public MVendisTexFog(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            this.mv.visitIntInsn(opcode, operand);
            if (opcode != 17 || operand != 3553 && operand != 2912) {
                this.lastInt = 0;
            } else {
                this.lastInt = operand;
            }

        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            this.mv.visitMethodInsn(opcode, owner, name, desc);
            if (owner.equals("org/lwjgl/opengl/GL11")) {
                if (name.equals("glEnable")) {
                    if (this.lastInt == 3553) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "enableTexture2D", "()V");
                    } else if (this.lastInt == 2912) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "enableFog", "()V");
                    }
                } else if (name.equals("glDisable")) {
                    if (this.lastInt == 3553) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableTexture2D", "()V");
                    } else if (this.lastInt == 2912) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableFog", "()V");
                    }
                }
            }

            this.lastInt = 0;
        }
    }

    class MVrenderEntities extends MethodVisitor {
        int state = 0;

        public MVrenderEntities(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitLdcInsn(Object cst) {
            if (cst instanceof String) {
                String scst = (String)cst;
                if (scst.equals("entities")) {
                    this.state = 1;
                } else if (scst.equals("tileentities")) {
                    this.state = 3;
                }
            }

            this.mv.visitLdcInsn(cst);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            this.mv.visitMethodInsn(opcode, owner, name, desc);
            if (this.state == 1) {
                this.state = 2;
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginEntities", "()V");
            } else if (this.state == 3) {
                this.state = 4;
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endEntities", "()V");
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginTileEntities", "()V");
            } else if (this.state == 4) {
                String nameM = name;
                if (nameM.equals("func_78483_a")) {
                    this.state = 5;
                    this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endTileEntities", "()V");
                }
            }

        }
    }

    class MVrenderSky extends MVendisTexFog {
        int state = 0;
        boolean detectedOptifine = false;
        int lastInt = 0;
        int lastVar = 0;

        public MVrenderSky(MethodVisitor mv) {
            super(mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            this.mv.visitIntInsn(opcode, operand);
            if (opcode == 17) {
                this.lastInt = operand;
            } else {
                this.lastInt = 0;
            }

        }

        public void visitVarInsn(int opcode, int var) {
            this.mv.visitVarInsn(opcode, var);
            if (opcode == 25) {
                this.lastVar = var;
            } else {
                this.lastVar = 0;
            }

        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            String ownerM = owner;
            String nameM = name;
            String descM = desc;
            switch (this.state) {
                case 0:
                    if (ownerM.equals("net/minecraft/util/Vec3") && nameM.equals("field_72450_a")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "setSkyColor", "(Lnet/minecraft/util/Vec3;)V");
                        this.mv.visitVarInsn(25, this.lastVar);
                    }
                    break;
                case 1:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("field_72771_w")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "preSkyList", "()V");
                    }
            }

            this.mv.visitFieldInsn(opcode, owner, name, desc);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = owner;
            String nameM = name;
            String descM = desc;
            switch (this.state) {
                case 0:
                    if (ownerM.equals("Config") && nameM.equals("isSkyEnabled")) {
                        this.detectedOptifine = true;
                    }
                    break;
                case 2:
                    if (ownerM.equals("net/minecraft/client/multiplayer/WorldClient") && nameM.equals("func_72867_j")) {
                        ++this.state;
                    }
            }

            this.mv.visitMethodInsn(opcode, owner, name, desc);
            if (owner.equals("org/lwjgl/opengl/GL11")) {
                if (name.equals("glEnable")) {
                    if (this.lastInt == 3553) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "enableTexture2D", "()V");
                    } else if (this.lastInt == 2912) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "enableFog", "()V");
                    }
                } else if (name.equals("glDisable")) {
                    if (this.lastInt == 3553) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableTexture2D", "()V");
                    } else if (this.lastInt == 2912) {
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableFog", "()V");
                    }
                } else if (name.equals("glRotatef")) {
                    if (this.state == 3) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "preCelestialRotate", "()V");
                    } else if (this.state == 4) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "postCelestialRotate", "()V");
                    }
                }
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
