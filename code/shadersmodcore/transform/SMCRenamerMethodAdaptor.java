//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.transform;

import org.objectweb.asm.MethodVisitor;

public class SMCRenamerMethodAdaptor extends MethodVisitor {
    public SMCRenamerMethodAdaptor(MethodVisitor mv) {
        super(262144, mv);
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        this.mv.visitFieldInsn(opcode, owner, SMCRemap.remapperW.mapFieldName(owner, name, desc), desc);
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        this.mv.visitMethodInsn(opcode, owner, SMCRemap.remapperW.mapMethodName(owner, name, desc), desc);
    }
}
