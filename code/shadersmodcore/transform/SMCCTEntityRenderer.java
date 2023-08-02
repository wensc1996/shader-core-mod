package shadersmodcore.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class SMCCTEntityRenderer implements IClassTransformer {
    public SMCCTEntityRenderer() {
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
            if (nameM.equals("func_78483_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVdisableLightmap(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78463_b")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVenableLightmap(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78469_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVsetFogColorBuffer(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78468_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVsetupFog(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_82829_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVrenderCloudsCheck(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78476_b")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVrenderHand(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78471_a")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new SMCCTEntityRenderer.MVrenderWorld(SMCRemap.getAdaptor(this.cv.visitMethod(access, name, desc, signature, exceptions)));
            } else {
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVdisableLightmap extends MethodVisitor {
        public MVdisableLightmap(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableLightmap", "()V");
            }

            this.mv.visitInsn(opcode);
        }
    }

    class MVenableLightmap extends MethodVisitor {
        public MVenableLightmap(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "enableLightmap", "()V");
            }

            this.mv.visitInsn(opcode);
        }
    }

    class MVrenderCloudsCheck extends MethodVisitor {
        public MVrenderCloudsCheck(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("net/minecraft/client/settings/GameSettings") && nameM.equals("func_74309_c") && descM.equals("()Z")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "shouldRenderClouds", "(Lnet/minecraft/client/settings/GameSettings;)Z");
            } else if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72718_b") && descM.equals("(F)V")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginClouds", "()V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endClouds", "()V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVrenderHand extends MethodVisitor {
        Label la1 = new Label();

        public MVrenderHand(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            if (ownerM.equals("org/lwjgl/util/glu/Project") && nameM.equals("gluPerspective") && descM.equals("(FFFF)V")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "applyHandDepth", "()V");
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            } else if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glPushMatrix") && descM.equals("()V")) {
                this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "isCompositeRendered", "Z");
                this.mv.visitJumpInsn(154, this.la1);
            } else if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glPopMatrix") && descM.equals("()V")) {
                this.mv.visitInsn(177);
                this.mv.visitLabel(this.la1);
                this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVrenderWorld extends MethodVisitor {
        private static final int stateEnd = 28;
        int state = 0;
        String section = "";
        Label labelAfterUpdate = null;
        Label labelEndUpdate = null;
        Label labelNoSky = null;

        public MVrenderWorld(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 0);
            this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/EntityRenderer", "field_78531_r", "Lnet/minecraft/client/Minecraft;");
            this.mv.visitVarInsn(23, 1);
            this.mv.visitVarInsn(22, 2);
            this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginRender", "(Lnet/minecraft/client/Minecraft;FJ)V");
        }

        public void visitLdcInsn(Object cst) {
            if (cst instanceof String) {
                String scst = (String)cst;
                this.section = scst;
            }

            this.mv.visitLdcInsn(cst);
        }

        public void visitIntInsn(int opcode, int operand) {
            this.mv.visitIntInsn(opcode, operand);
        }

        public void visitJumpInsn(int opcode, Label label) {
            switch (this.state) {
                case 4:
                    if (opcode == 162) {
                        ++this.state;
                        this.mv.visitInsn(88);
                        this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "isShadowPass", "Z");
                        this.mv.visitJumpInsn(154, label);
                        return;
                    }
                    break;
                case 10:
                    if (opcode == 154) {
                        ++this.state;
                        this.labelAfterUpdate = label;
                        this.labelEndUpdate = new Label();
                        this.mv.visitJumpInsn(opcode, label);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginUpdateChunks", "()V");
                        return;
                    }
                    break;
                case 11:
                    if (label == this.labelAfterUpdate) {
                        this.mv.visitJumpInsn(opcode, this.labelEndUpdate);
                        return;
                    }
            }

            this.mv.visitJumpInsn(opcode, label);
        }

        public void visitLabel(Label label) {
            switch (this.state) {
                case 11:
                    if (label == this.labelAfterUpdate) {
                        ++this.state;
                        this.mv.visitLabel(this.labelEndUpdate);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endUpdateChunks", "()V");
                        this.mv.visitLabel(label);
                        this.labelAfterUpdate = this.labelEndUpdate = null;
                        return;
                    }
                default:
                    this.mv.visitLabel(label);
            }
        }

        public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
            switch (this.state) {
                case 7:
                    this.state = 8;
                    this.mv.visitLabel(this.labelNoSky);
                    this.labelNoSky = null;
                    this.mv.visitFrame(type, nLocal, local, nStack, stack);
                    return;
                case 27:
                    ++this.state;
                    this.mv.visitFrame(type, nLocal, local, nStack, stack);
                    this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endRender", "()V");
                    return;
                default:
                    this.mv.visitFrame(type, nLocal, local, nStack, stack);
            }
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapFieldName(owner, name, desc);
            String descM = SMCRemap.remapper.mapType(desc);
            switch (this.state) {
                case 3:
                    if (ownerM.equals("net/minecraft/client/settings/GameSettings") && nameM.equals("field_74339_e")) {
                        ++this.state;
                    }
                    break;
                case 23:
                case 25:
                    if (ownerM.equals("net/minecraft/client/renderer/EntityRenderer") && nameM.equals("field_78503_V")) {
                        this.state = 26;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "renderCompositeFinal", "()V");
                    }
            }

            this.mv.visitFieldInsn(opcode, owner, name, desc);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = SMCRemap.remapper.mapType(owner);
            String nameM = SMCRemap.remapper.mapMethodName(owner, name, desc);
            String descM = SMCRemap.remapper.mapMethodDesc(desc);
            switch (this.state) {
                case 0:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glViewport") && descM.equals("(IIII)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "setViewport", "(IIII)V");
                        return;
                    }
                    break;
                case 1:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glClear") && descM.equals("(I)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "clearRenderBuffer", "()V");
                        return;
                    }
                    break;
                case 2:
                    if (ownerM.equals("net/minecraft/client/renderer/EntityRenderer") && nameM.equals("func_78479_a") && descM.equals("(FI)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitVarInsn(23, 1);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "setCamera", "(F)V");
                        return;
                    }
                    break;
                case 3:
                    if (ownerM.equals("Config") && nameM.equals("isSkyEnabled") && descM.equals("()Z")) {
                        this.state = 6;
                        this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "isShadowPass", "Z");
                        this.labelNoSky = new Label();
                        this.mv.visitJumpInsn(154, this.labelNoSky);
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }
                case 4:
                case 7:
                case 10:
                case 11:
                case 25:
                default:
                    break;
                case 5:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72714_a") && descM.equals("(F)V")) {
                        this.state = 8;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginSky", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endSky", "()V");
                        return;
                    }
                    break;
                case 6:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72714_a") && descM.equals("(F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginSky", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endSky", "()V");
                        return;
                    }
                    break;
                case 8:
                    if ((ownerM.equals("net/minecraft/client/renderer/culling/ICamera") || ownerM.equals("net/minecraft/client/renderer/culling/Frustrum")) && nameM.equals("func_78547_a") && descM.equals("(DDD)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersRender", "setFrustrumPosition", "(Lnet/minecraft/client/renderer/culling/Frustrum;DDD)V");
                        return;
                    }
                    break;
                case 9:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72729_a") && descM.equals("(Lnet/minecraft/client/renderer/culling/ICamera;F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/ShadersRender", "clipRenderersByFrustrum", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/client/renderer/culling/Frustrum;F)V");
                        return;
                    }
                    break;
                case 12:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72719_a") && descM.equals("(Lnet/minecraft/entity/EntityLivingBase;ID)I")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginTerrain", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endTerrain", "()V");
                        return;
                    }
                    break;
                case 13:
                    if (ownerM.equals("net/minecraft/client/particle/EffectRenderer") && nameM.equals("func_78872_b") && descM.equals("(Lnet/minecraft/entity/Entity;F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginLitParticles", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }

                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glDisable") && descM.equals("(I)V")) {
                        this.state = 16;
                    }
                    break;
                case 14:
                    if (ownerM.equals("net/minecraft/client/particle/EffectRenderer") && nameM.equals("func_78874_a") && descM.equals("(Lnet/minecraft/entity/Entity;F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginParticles", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endParticles", "()V");
                        return;
                    }
                    break;
                case 15:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glDisable") && descM.equals("(I)V")) {
                        this.state = 16;
                    }
                    break;
                case 16:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glDepthMask") && descM.equals("(Z)V")) {
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginHand", "()V");
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitVarInsn(23, 1);
                        this.mv.visitVarInsn(21, 13);
                        this.mv.visitMethodInsn(183, "net/minecraft/client/renderer/EntityRenderer", "func_78476_b", "(FI)V");
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endHand", "()V");
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "preWater", "()V");
                        return;
                    }

                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72719_a") && descM.equals("(Lnet/minecraft/entity/EntityLivingBase;ID)I")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginWaterFancy", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }

                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("renderAllSortedRenderers") && descM.equals("(ID)I")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginWaterFancy", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }
                    break;
                case 17:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72733_a") && descM.equals("(ID)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "midWaterFancy", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }

                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("renderAllSortedRenderers") && descM.equals("(ID)I")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "midWaterFancy", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }
                    break;
                case 18:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glShadeModel") && descM.equals("(I)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endWater", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }
                    break;
                case 19:
                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("func_72719_a") && descM.equals("(Lnet/minecraft/entity/EntityLivingBase;ID)I")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginWater", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endWater", "()V");
                        return;
                    }

                    if (ownerM.equals("net/minecraft/client/renderer/RenderGlobal") && nameM.equals("renderAllSortedRenderers") && descM.equals("(ID)I")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginWater", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endWater", "()V");
                        return;
                    }
                    break;
                case 20:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glDisable") && descM.equals("(I)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", "isShadowPass", "Z");
                        Label l117 = new Label();
                        this.mv.visitJumpInsn(153, l117);
                        this.mv.visitInsn(177);
                        this.mv.visitLabel(l117);
                        this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "readCenterDepth", "()V");
                        return;
                    }
                    break;
                case 21:
                    if (ownerM.equals("net/minecraft/client/renderer/EntityRenderer") && nameM.equals("func_78474_d") && descM.equals("(F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginWeather", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endWeather", "()V");
                        return;
                    }
                    break;
                case 22:
                    if (ownerM.equals("org/lwjgl/opengl/GL11") && nameM.equals("glDisable") && descM.equals("(I)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "disableFog", "()V");
                        return;
                    }
                    break;
                case 23:
                    if (ownerM.equals("net/minecraft/client/particle/EffectRenderer") && nameM.equals("func_78872_b") && descM.equals("(Lnet/minecraft/entity/Entity;F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginLitParticles", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        return;
                    }
                    break;
                case 24:
                    if (ownerM.equals("net/minecraft/client/particle/EffectRenderer") && nameM.equals("func_78874_a") && descM.equals("(Lnet/minecraft/entity/Entity;F)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginParticles", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endParticles", "()V");
                        return;
                    }
                    break;
                case 26:
                    if (ownerM.equals("net/minecraft/client/renderer/EntityRenderer") && nameM.equals("func_78476_b") && descM.equals("(FI)V")) {
                        ++this.state;
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "beginFPOverlay", "()V");
                        this.mv.visitMethodInsn(opcode, owner, name, desc);
                        this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "endFPOverlay", "()V");
                        return;
                    }
            }

            this.mv.visitMethodInsn(opcode, owner, name, desc);
        }

        public void visitEnd() {
            if (this.state != 28) {
                SMCLog.warning("  state %d expect %d", new Object[]{this.state, 28});
            }

            this.mv.visitEnd();
        }
    }

    class MVsetFogColorBuffer extends MethodVisitor {
        public MVsetFogColorBuffer(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(23, 1);
            this.mv.visitVarInsn(23, 2);
            this.mv.visitVarInsn(23, 3);
            this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "setFogColor", "(FFF)V");
        }
    }

    class MVsetupFog extends MethodVisitor {
        public MVsetupFog(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (owner.equals("org/lwjgl/opengl/GL11") && name.equals("glFogi") && desc.equals("(II)V")) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "sglFogi", "(II)V");
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
