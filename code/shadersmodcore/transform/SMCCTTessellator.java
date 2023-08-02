package shadersmodcore.transform;

import org.objectweb.asm.*;
import net.minecraft.launchwrapper.IClassTransformer;

public class SMCCTTessellator implements IClassTransformer {
    private static boolean inputHasStaticBuffer = false;

    public SMCCTTessellator() {
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
            String nameM = name;
            if (name.equals("shadersTess")) {
                return null;
            } else {
                if ((access & 8) == 0 || !nameM.equals("field_78394_d") && !nameM.equals("field_78395_e") && !nameM.equals("field_78392_f") && !nameM.equals("field_78393_g") && !nameM.equals("field_78389_A") && !nameM.equals("field_78406_i") && !nameM.equals("field_78390_B")) {
                    access = access & -7 | 1;
                } else {
                    SMCCTTessellator.inputHasStaticBuffer = true;
                    access = access & -15 | 1;
                }

                return this.cv.visitField(access, name, desc, signature, value);
            }
        }

        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!this.endFields) {
                this.endFields = true;
                FieldVisitor fv = this.cv.visitField(1, "shadersTess", "Lshadersmodcore/client/ShadersTess;", (String)null, (Object)null);
                fv.visitEnd();
            }

            String nameM = name;
            String descM = desc;
            if (nameM.equals("<clinit>")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVclinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("<init>") && descM.equals("()V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVinit(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("<init>") && descM.equals("(I)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVinitI(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78381_a") && descM.equals("()I")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVdraw(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78379_d") && descM.equals("()V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                access = access & -7 | 1;
                return new MVreset(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78377_a") && descM.equals("(DDD)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVaddVertex(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else if (nameM.equals("func_78375_b") && descM.equals("(FFF)V")) {
                SMCLog.finer("  patching method %s.%s%s = %s", new Object[]{this.classname, name, desc, nameM});
                return new MVsetNormal(this.cv.visitMethod(access, name, desc, signature, exceptions));
            } else {
                access = access & -7 | 1;
                return this.cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }
    }

    class MVaddVertex extends MethodVisitor {
        public MVaddVertex(MethodVisitor mv) {
            super(262144, (MethodVisitor)null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(466, l0);
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(24, 1);
            mv.visitVarInsn(24, 3);
            mv.visitVarInsn(24, 5);
            mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTess", "addVertex", "(Lnet/minecraft/client/renderer/Tessellator;DDD)V");
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(467, l1);
            mv.visitInsn(177);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/Tessellator;", (String)null, l0, l2, 0);
            mv.visitLocalVariable("par1", "D", (String)null, l0, l2, 1);
            mv.visitLocalVariable("par3", "D", (String)null, l0, l2, 3);
            mv.visitLocalVariable("par5", "D", (String)null, l0, l2, 5);
            mv.visitMaxs(7, 7);
            mv.visitEnd();
        }
    }

    class MVclinit extends MethodVisitor {
        public MVclinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            String ownerM = owner;
            String nameM = name;
            String descM = desc;
            if (opcode != 179 || !nameM.equals("field_78394_d") && !nameM.equals("field_78395_e") && !nameM.equals("field_78392_f") && !nameM.equals("field_78393_g") && !nameM.equals("field_78389_A") && !nameM.equals("field_78406_i") && !nameM.equals("field_78390_B")) {
                if (opcode == 178 && (nameM.equals("field_78394_d") || nameM.equals("field_78390_B"))) {
                    this.mv.visitInsn(1);
                } else if (opcode == 178 && nameM.equals("field_78389_A")) {
                    this.mv.visitInsn(3);
                } else {
                    this.mv.visitFieldInsn(opcode, owner, name, desc);
                }
            } else {
                this.mv.visitInsn(87);
            }
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            String ownerM = owner;
            String nameM = name;
            String descM = desc;
            if (ownerM.equals("net/minecraft/client/renderer/GLAllocation") && nameM.equals("func_74524_c") && descM.equals("(I)Ljava/nio/ByteBuffer;")) {
                this.mv.visitInsn(87);
                this.mv.visitInsn(1);
            } else if (ownerM.equals("net/minecraft/client/renderer/GLAllocation") && nameM.equals("func_74527_f") && descM.equals("(I)Ljava/nio/IntBuffer;")) {
                this.mv.visitInsn(87);
                this.mv.visitInsn(1);
            } else if (ownerM.equals("java/nio/ByteBuffer") && nameM.equals("asIntBuffer") && descM.equals("()Ljava/nio/IntBuffer;")) {
                this.mv.visitInsn(87);
                this.mv.visitInsn(1);
            } else if (ownerM.equals("java/nio/ByteBuffer") && nameM.equals("asFloatBuffer") && descM.equals("()Ljava/nio/FloatBuffer;")) {
                this.mv.visitInsn(87);
                this.mv.visitInsn(1);
            } else if (ownerM.equals("java/nio/ByteBuffer") && nameM.equals("asShortBuffer") && descM.equals("()Ljava/nio/ShortBuffer;")) {
                this.mv.visitInsn(87);
                this.mv.visitInsn(1);
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVdraw extends MethodVisitor {
        public MVdraw(MethodVisitor mv) {
            super(262144, (MethodVisitor)null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(185, l0);
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(184, "shadersmodcore/client/ShadersTess", "draw", "(Lnet/minecraft/client/renderer/Tessellator;)I");
            mv.visitInsn(172);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/Tessellator;", (String)null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    class MVinit extends MethodVisitor {
        public MVinit(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (opcode == 183 && owner.equals("java/lang/Object") && name.equals("<init>") && desc.equals("()V")) {
                this.mv.visitLdcInsn(new Integer(65536));
                this.mv.visitMethodInsn(183, "net/minecraft/client/renderer/Tessellator", "<init>", "(I)V");
            } else {
                this.mv.visitMethodInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVinitI extends MethodVisitor {
        public MVinitI(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 177) {
                if (SMCCTTessellator.inputHasStaticBuffer) {
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(21, 1);
                    this.mv.visitInsn(7);
                    this.mv.visitInsn(104);
                    this.mv.visitMethodInsn(184, "net/minecraft/client/renderer/GLAllocation", "func_74524_c", "(I)Ljava/nio/ByteBuffer;");
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78394_d", "Ljava/nio/ByteBuffer;");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "field_78394_d", "Ljava/nio/ByteBuffer;");
                    this.mv.visitMethodInsn(182, "java/nio/ByteBuffer", "asIntBuffer", "()Ljava/nio/IntBuffer;");
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78395_e", "Ljava/nio/IntBuffer;");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "field_78394_d", "Ljava/nio/ByteBuffer;");
                    this.mv.visitMethodInsn(182, "java/nio/ByteBuffer", "asFloatBuffer", "()Ljava/nio/FloatBuffer;");
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78392_f", "Ljava/nio/FloatBuffer;");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "field_78394_d", "Ljava/nio/ByteBuffer;");
                    this.mv.visitMethodInsn(182, "java/nio/ByteBuffer", "asShortBuffer", "()Ljava/nio/ShortBuffer;");
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78393_g", "Ljava/nio/ShortBuffer;");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(21, 1);
                    this.mv.visitIntInsn(188, 10);
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78405_h", "[I");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitIntInsn(16, 10);
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78406_i", "I");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(178, "net/minecraft/client/renderer/Tessellator", "field_78397_c", "Z");
                    Label l28 = new Label();
                    this.mv.visitJumpInsn(153, l28);
                    this.mv.visitMethodInsn(184, "org/lwjgl/opengl/GLContext", "getCapabilities", "()Lorg/lwjgl/opengl/ContextCapabilities;");
                    this.mv.visitFieldInsn(180, "org/lwjgl/opengl/ContextCapabilities", "GL_ARB_vertex_buffer_object", "Z");
                    this.mv.visitJumpInsn(153, l28);
                    this.mv.visitInsn(4);
                    Label l29 = new Label();
                    this.mv.visitJumpInsn(167, l29);
                    this.mv.visitLabel(l28);
                    this.mv.visitFrame(0, 2, new Object[]{"net/minecraft/client/renderer/Tessellator", Opcodes.INTEGER}, 1, new Object[]{"net/minecraft/client/renderer/Tessellator"});
                    this.mv.visitInsn(3);
                    this.mv.visitLabel(l29);
                    this.mv.visitFrame(0, 2, new Object[]{"net/minecraft/client/renderer/Tessellator", Opcodes.INTEGER}, 2, new Object[]{"net/minecraft/client/renderer/Tessellator", Opcodes.INTEGER});
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78389_A", "Z");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "field_78389_A", "Z");
                    Label l31 = new Label();
                    this.mv.visitJumpInsn(153, l31);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "field_78406_i", "I");
                    this.mv.visitMethodInsn(184, "net/minecraft/client/renderer/GLAllocation", "func_74527_f", "(I)Ljava/nio/IntBuffer;");
                    this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78390_B", "Ljava/nio/IntBuffer;");
                    this.mv.visitVarInsn(25, 0);
                    this.mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "field_78390_B", "Ljava/nio/IntBuffer;");
                    this.mv.visitMethodInsn(184, "org/lwjgl/opengl/ARBVertexBufferObject", "glGenBuffersARB", "(Ljava/nio/IntBuffer;)V");
                    this.mv.visitLabel(l31);
                    this.mv.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
                }

                this.mv.visitVarInsn(25, 0);
                this.mv.visitTypeInsn(187, "shadersmodcore/client/ShadersTess");
                this.mv.visitInsn(89);
                this.mv.visitMethodInsn(183, "shadersmodcore/client/ShadersTess", "<init>", "()V");
                this.mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "shadersTess", "Lshadersmodcore/client/ShadersTess;");
            }

            this.mv.visitInsn(opcode);
        }
    }

    class MVreset extends MethodVisitor {
        public MVreset(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            String ownerM = owner;
            String nameM = name;
            String descM = desc;
            if (opcode == 178 && ownerM.equals("net/minecraft/client/renderer/Tessellator") && nameM.equals("field_78394_d")) {
                this.mv.visitVarInsn(25, 0);
                this.mv.visitFieldInsn(180, owner, name, desc);
            } else {
                this.mv.visitFieldInsn(opcode, owner, name, desc);
            }
        }
    }

    class MVsetNormal extends MethodVisitor {
        public MVsetNormal(MethodVisitor mv) {
            super(262144, (MethodVisitor)null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, "net/minecraft/client/renderer/Tessellator", "shadersTess", "Lshadersmodcore/client/ShadersTess;");
            mv.visitVarInsn(58, 4);
            mv.visitVarInsn(25, 0);
            mv.visitInsn(4);
            mv.visitFieldInsn(181, "net/minecraft/client/renderer/Tessellator", "field_78413_q", "Z");
            mv.visitVarInsn(25, 4);
            mv.visitVarInsn(23, 1);
            mv.visitFieldInsn(181, "shadersmodcore/client/ShadersTess", "normalX", "F");
            mv.visitVarInsn(25, 4);
            mv.visitVarInsn(23, 2);
            mv.visitFieldInsn(181, "shadersmodcore/client/ShadersTess", "normalY", "F");
            mv.visitVarInsn(25, 4);
            mv.visitVarInsn(23, 3);
            mv.visitFieldInsn(181, "shadersmodcore/client/ShadersTess", "normalZ", "F");
            mv.visitInsn(177);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/Tessellator;", (String)null, l0, l5, 0);
            mv.visitLocalVariable("par1", "F", (String)null, l0, l5, 1);
            mv.visitLocalVariable("par2", "F", (String)null, l0, l5, 2);
            mv.visitLocalVariable("par3", "F", (String)null, l0, l5, 3);
            mv.visitLocalVariable("stess", "Lshadersmodcore/client/ShadersTess;", (String)null, l0, l5, 4);
            mv.visitMaxs(2, 5);
            mv.visitEnd();
        }
    }

    class MVsortQuad extends MethodVisitor {
        public MVsortQuad(MethodVisitor mv) {
            super(262144, mv);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (opcode == 16 && operand == 32) {
                operand = 72;
            }

            super.visitIntInsn(opcode, operand);
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
