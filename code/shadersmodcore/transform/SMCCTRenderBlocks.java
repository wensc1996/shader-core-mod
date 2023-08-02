package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class SMCCTRenderBlocks implements IClassTransformer {
    static final String[] fieldsBlockLightLevel = new String[]{null, "blockLightLevel05", "blockLightLevel06", "blockLightLevel08"};

    public SMCCTRenderBlocks() {
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
            if (remappedName.equals("func_78612_b")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                return new MVrenBlkByRenType(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (!remappedName.equals("func_102027_b") && !remappedName.equals("func_78578_a")) {
                if (!remappedName.equals("func_78609_c") && !remappedName.equals("func_78569_d") && !remappedName.equals("func_78574_w") && !remappedName.equals("func_78621_p") && !remappedName.equals("func_78601_u") && !remappedName.equals("func_78588_a")) {
                    if (remappedName.equals("func_78608_c")) {
                        SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                        return new MVrenBlkPistonExt(this.cv.visitMethod(access, name, desc, signature, exceptions));
                    } else {
                        return this.cv.visitMethod(access, name, desc, signature, exceptions);
                    }
                } else {
                    SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                    return new MVrenBlkFVar(this.cv.visitMethod(access, name, desc, signature, exceptions));
                }
            } else {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, remappedName});
                return new MVrenBlkWithAO(access, name, desc, signature, exceptions, this.cv.visitMethod(access, name, desc, signature, exceptions));
            }
        }
    }

    class MVrenBlkByRenType extends MethodVisitor {
        int nPatch = 0;

        public MVrenBlkByRenType(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitCode() {
            this.mv.visitCode();
            this.mv.visitVarInsn(25, 1);
            this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "pushEntity", "(Lnet/minecraft/block/Block;)V");
            ++this.nPatch;
        }

        public void visitInsn(int opcode) {
            if (opcode == 172) {
                this.mv.visitMethodInsn(184, "shadersmodcore/client/Shaders", "popEntity", "()V");
                ++this.nPatch;
            }

            this.mv.visitInsn(opcode);
        }

        public void visitEnd() {
            this.mv.visitEnd();
        }
    }

    class MVrenBlkFVar extends MethodVisitor {
        int nPatch = 0;
        int state = 0;

        public MVrenBlkFVar(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitLdcInsn(Object cst) {
            int match1 = 0;
            if (cst instanceof Float) {
                float fcst = (Float)cst;
                if (fcst == 0.5F) {
                    match1 = 1;
                } else if (fcst == 0.6F) {
                    match1 = 2;
                } else if (fcst == 0.8F) {
                    match1 = 3;
                }
            }

            if (match1 != 0 && this.state < 3) {
                ++this.state;
                String fieldName = SMCCTRenderBlocks.fieldsBlockLightLevel[match1];
                this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", fieldName, "F");
                ++this.nPatch;
            } else {
                this.mv.visitLdcInsn(cst);
            }
        }

        public void visitEnd() {
            this.mv.visitEnd();
        }
    }

    class MVrenBlkPistonExt extends MethodVisitor {
        int nPatch = 0;
        int state = 0;

        public MVrenBlkPistonExt(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
            this.state = 1;
            this.mv.visitTableSwitchInsn(min, max, dflt, labels);
        }

        public void visitLdcInsn(Object cst) {
            int match1 = 0;
            if (cst instanceof Float) {
                float fcst = (Float)cst;
                if (fcst == 0.5F) {
                    match1 = 1;
                } else if (fcst == 0.6F) {
                    match1 = 2;
                } else if (fcst == 0.8F) {
                    match1 = 3;
                }
            }

            if (match1 != 0 && this.state == 1) {
                String fieldName = SMCCTRenderBlocks.fieldsBlockLightLevel[match1];
                this.mv.visitFieldInsn(178, "shadersmodcore/client/Shaders", fieldName, "F");
                ++this.nPatch;
            } else {
                this.mv.visitLdcInsn(cst);
            }
        }

        public void visitEnd() {
            this.mv.visitEnd();
        }
    }

    class MVrenBlkWithAO extends MethodVisitor {
        MethodVisitor mv1;
        MethodNode mn;
        int nPatch = 0;

        public MVrenBlkWithAO(int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv) {
            super(262144);
            super.mv = this.mn = new MethodNode(access, name, desc, signature, exceptions);
            this.mv1 = mv;
        }

        public void visitEnd() {
            this.mn.visitEnd();
            this.mn.accept(this.mv1);
        }

        public void visitLdcInsn(Object cst) {
            int match1 = 0;
            if (cst instanceof Float) {
                float fcst = (Float)cst;
                if (fcst == 0.5F) {
                    match1 = 1;
                } else if (fcst == 0.6F) {
                    match1 = 2;
                } else if (fcst == 0.8F) {
                    match1 = 3;
                }
            }

            if (match1 != 0) {
                int match2 = 0;
                InsnList insns = this.mn.instructions;
                AbstractInsnNode insn = insns.getLast();
                if (insn != null && insn.getOpcode() == 23) {
                    insn = insn.getPrevious();
                    if (insn != null && insn.getOpcode() == 25) {
                        insn = insn.getPrevious();
                        if (insn != null && insn.getOpcode() == 25) {
                            insn = insn.getPrevious();
                            if (insn != null && insn.getOpcode() == 25) {
                                insn = insn.getPrevious();
                                if (insn != null && insn.getOpcode() == 25) {
                                    match2 = match1;
                                }
                            }
                        }
                    }
                }

                if (insn != null && insn.getOpcode() == 25) {
                    insn = insn.getPrevious();
                    if (insn != null && insn.getOpcode() == 25) {
                        insn = insn.getPrevious();
                        if (insn != null && insn.getOpcode() == 25) {
                            insn = insn.getPrevious();
                            if (insn != null && insn.getOpcode() == 25) {
                                match2 = match1;
                            }
                        }
                    }
                }

                if (match2 != 0) {
                    String fieldName = SMCCTRenderBlocks.fieldsBlockLightLevel[match2];
                    this.mn.visitFieldInsn(178, "shadersmodcore/client/Shaders", fieldName, "F");
                    ++this.nPatch;
                    return;
                }
            }

            this.mn.visitLdcInsn(cst);
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
