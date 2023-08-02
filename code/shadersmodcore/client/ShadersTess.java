//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import shadersmodcore.transform.SMCLog;

public class ShadersTess {
    public static final int vertexStride = 18;
    public float midTextureU;
    public float midTextureV;
    public float normalX;
    public float normalY;
    public float normalZ;
    public float v0x;
    public float v0y;
    public float v0z;
    public float v0u;
    public float v0v;
    public float v1x;
    public float v1y;
    public float v1z;
    public float v1u;
    public float v1v;
    public float v2x;
    public float v2y;
    public float v2z;
    public float v2u;
    public float v2v;
    public float v3x;
    public float v3y;
    public float v3z;
    public float v3u;
    public float v3v;

    public ShadersTess() {
    }

    public static int draw(Tessellator tess) {
        if (!tess.field_78415_z) {
            throw new IllegalStateException("Not tesselating!");
        } else {
            tess.field_78415_z = false;
            if (tess.field_78409_u == 7 && tess.field_78406_i % 4 != 0) {
                SMCLog.warning("%s", new Object[]{"bad vertexCount"});
            }

            int voffset = 0;
            int realDrawMode = tess.field_78409_u;

            int vcount;
            while(voffset < tess.field_78406_i) {
                vcount = Math.min(tess.field_78406_i - voffset, tess.field_78394_d.capacity() / 72);
                if (realDrawMode == 7) {
                    vcount = vcount / 4 * 4;
                }

                tess.field_78392_f.clear();
                tess.field_78393_g.clear();
                tess.field_78395_e.clear();
                tess.field_78395_e.put(tess.field_78405_h, voffset * 18, vcount * 18);
                tess.field_78394_d.position(0);
                tess.field_78394_d.limit(vcount * 72);
                voffset += vcount;
                if (tess.field_78400_o) {
                    tess.field_78392_f.position(3);
                    GL11.glTexCoordPointer(2, 72, tess.field_78392_f);
                    GL11.glEnableClientState(32888);
                }

                if (tess.field_78414_p) {
                    OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                    tess.field_78393_g.position(12);
                    GL11.glTexCoordPointer(2, 72, tess.field_78393_g);
                    GL11.glEnableClientState(32888);
                    OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                }

                if (tess.field_78399_n) {
                    tess.field_78394_d.position(20);
                    GL11.glColorPointer(4, true, 72, tess.field_78394_d);
                    GL11.glEnableClientState(32886);
                }

                if (tess.field_78413_q) {
                    tess.field_78392_f.position(9);
                    GL11.glNormalPointer(72, tess.field_78392_f);
                    GL11.glEnableClientState(32885);
                }

                tess.field_78392_f.position(0);
                GL11.glVertexPointer(3, 72, tess.field_78392_f);
                preDrawArray(tess);
                GL11.glEnableClientState(32884);
                GL11.glDrawArrays(realDrawMode, 0, vcount);
            }

            GL11.glDisableClientState(32884);
            postDrawArray(tess);
            if (tess.field_78400_o) {
                GL11.glDisableClientState(32888);
            }

            if (tess.field_78414_p) {
                OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                GL11.glDisableClientState(32888);
                OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
            }

            if (tess.field_78399_n) {
                GL11.glDisableClientState(32886);
            }

            if (tess.field_78413_q) {
                GL11.glDisableClientState(32885);
            }

            vcount = tess.field_78412_r * 4;
            tess.func_78379_d();
            return vcount;
        }
    }

    public static void preDrawArray(Tessellator tess) {
        if (Shaders.useMultiTexCoord3Attrib && tess.field_78400_o) {
            GL13.glClientActiveTexture(33987);
            GL11.glTexCoordPointer(2, 72, (FloatBuffer)tess.field_78392_f.position(16));
            GL11.glEnableClientState(32888);
            GL13.glClientActiveTexture(33984);
        }

        if (Shaders.useMidTexCoordAttrib && tess.field_78400_o) {
            ARBVertexShader.glVertexAttribPointerARB(Shaders.midTexCoordAttrib, 2, false, 72, (FloatBuffer)tess.field_78392_f.position(16));
            ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.midTexCoordAttrib);
        }

        if (Shaders.useTangentAttrib && tess.field_78400_o) {
            ARBVertexShader.glVertexAttribPointerARB(Shaders.tangentAttrib, 4, false, 72, (FloatBuffer)tess.field_78392_f.position(12));
            ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.tangentAttrib);
        }

        if (Shaders.useEntityAttrib) {
            ARBVertexShader.glVertexAttribPointerARB(Shaders.entityAttrib, 3, false, false, 72, (ShortBuffer)tess.field_78393_g.position(14));
            ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.entityAttrib);
        }

    }

    public static void preDrawArrayVBO(Tessellator tess) {
        if (Shaders.useMultiTexCoord3Attrib && tess.field_78400_o) {
            GL13.glClientActiveTexture(33987);
            GL11.glTexCoordPointer(2, 5126, 72, 64L);
            GL11.glEnableClientState(32888);
            GL13.glClientActiveTexture(33984);
        }

        if (Shaders.useMidTexCoordAttrib && tess.field_78400_o) {
            ARBVertexShader.glVertexAttribPointerARB(Shaders.midTexCoordAttrib, 2, 5126, false, 72, 64L);
            ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.midTexCoordAttrib);
        }

        if (Shaders.useTangentAttrib && tess.field_78400_o) {
            ARBVertexShader.glVertexAttribPointerARB(Shaders.tangentAttrib, 4, 5126, false, 72, 48L);
            ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.tangentAttrib);
        }

        if (Shaders.useEntityAttrib) {
            ARBVertexShader.glVertexAttribPointerARB(Shaders.entityAttrib, 3, 5122, false, 72, 28L);
            ARBVertexShader.glEnableVertexAttribArrayARB(Shaders.entityAttrib);
        }

    }

    public static void postDrawArray(Tessellator tess) {
        if (Shaders.useEntityAttrib) {
            ARBVertexShader.glDisableVertexAttribArrayARB(Shaders.entityAttrib);
        }

        if (Shaders.useMidTexCoordAttrib && tess.field_78400_o) {
            ARBVertexShader.glDisableVertexAttribArrayARB(Shaders.midTexCoordAttrib);
        }

        if (Shaders.useTangentAttrib && tess.field_78400_o) {
            ARBVertexShader.glDisableVertexAttribArrayARB(Shaders.tangentAttrib);
        }

        if (Shaders.useMultiTexCoord3Attrib && tess.field_78400_o) {
            GL13.glClientActiveTexture(33987);
            GL11.glDisableClientState(32888);
            GL13.glClientActiveTexture(33984);
        }

    }

    public static void addVertex(Tessellator tess, double parx, double pary, double parz) {
        ShadersTess stess = tess.shadersTess;
        int[] rawBuffer = tess.field_78405_h;
        int rbi = tess.field_78412_r;
        float fx = (float)(parx + tess.field_78408_v);
        float fy = (float)(pary + tess.field_78407_w);
        float fz = (float)(parz + tess.field_78417_x);
        if (rbi >= tess.field_78388_E - 72) {
            if (tess.field_78388_E >= 16777216) {
                if (tess.field_78411_s % 4 == 0) {
                    tess.func_78381_a();
                    tess.field_78415_z = true;
                }
            } else if (tess.field_78388_E > 0) {
                tess.field_78388_E *= 2;
                tess.field_78405_h = rawBuffer = Arrays.copyOf(tess.field_78405_h, tess.field_78388_E);
                SMCLog.info("Expand tesselator buffer %d", new Object[]{tess.field_78388_E});
            } else {
                tess.field_78388_E = 65536;
                tess.field_78405_h = rawBuffer = new int[tess.field_78388_E];
            }
        }

        int i;
        float x1;
        float y1;
        float z1;
        float x2;
        float y2;
        float z2;
        float u1;
        float v1;
        float u2;
        float v2;
        float vnx;
        float vny;
        float vnz;
        float lensq;
        float mult;
        float d;
        float r;
        float tan1x;
        float tan1y;
        float tan1z;
        float tan2x;
        float tan2y;
        float tan2z;
        float tan3x;
        float tan3y;
        float tan3z;
        float tan1w;
        if (tess.field_78409_u == 7) {
            i = tess.field_78411_s % 4;
            switch (i) {
                case 0:
                    stess.v0x = fx;
                    stess.v0y = fy;
                    stess.v0z = fz;
                    stess.v0u = (float)tess.field_78403_j;
                    stess.v0v = (float)tess.field_78404_k;
                    break;
                case 1:
                    stess.v1x = fx;
                    stess.v1y = fy;
                    stess.v1z = fz;
                    stess.v1u = (float)tess.field_78403_j;
                    stess.v1v = (float)tess.field_78404_k;
                    break;
                case 2:
                    stess.v2x = fx;
                    stess.v2y = fy;
                    stess.v2z = fz;
                    stess.v2u = (float)tess.field_78403_j;
                    stess.v2v = (float)tess.field_78404_k;
                    break;
                case 3:
                    stess.v3x = fx;
                    stess.v3y = fy;
                    stess.v3z = fz;
                    stess.v3u = (float)tess.field_78403_j;
                    stess.v3v = (float)tess.field_78404_k;
                    x1 = stess.v2x - stess.v0x;
                    y1 = stess.v2y - stess.v0y;
                    z1 = stess.v2z - stess.v0z;
                    x2 = stess.v3x - stess.v1x;
                    y2 = stess.v3y - stess.v1y;
                    z2 = stess.v3z - stess.v1z;
                    vnx = y1 * z2 - y2 * z1;
                    vny = z1 * x2 - z2 * x1;
                    vnz = x1 * y2 - x2 * y1;
                    lensq = vnx * vnx + vny * vny + vnz * vnz;
                    mult = (double)lensq != 0.0 ? (float)(1.0 / Math.sqrt((double)lensq)) : 1.0F;
                    vnx *= mult;
                    vny *= mult;
                    vnz *= mult;
                    tess.field_78413_q = true;
                    x1 = stess.v1x - stess.v0x;
                    y1 = stess.v1y - stess.v0y;
                    z1 = stess.v1z - stess.v0z;
                    u1 = stess.v1u - stess.v0u;
                    v1 = stess.v1v - stess.v0v;
                    x2 = stess.v2x - stess.v0x;
                    y2 = stess.v2y - stess.v0y;
                    z2 = stess.v2z - stess.v0z;
                    u2 = stess.v2u - stess.v0u;
                    v2 = stess.v2v - stess.v0v;
                    d = u1 * v2 - u2 * v1;
                    r = d != 0.0F ? 1.0F / d : 1.0F;
                    tan1x = (v2 * x1 - v1 * x2) * r;
                    tan1y = (v2 * y1 - v1 * y2) * r;
                    tan1z = (v2 * z1 - v1 * z2) * r;
                    tan2x = (u1 * x2 - u2 * x1) * r;
                    tan2y = (u1 * y2 - u2 * y1) * r;
                    tan2z = (u1 * z2 - u2 * z1) * r;
                    lensq = tan1x * tan1x + tan1y * tan1y + tan1z * tan1z;
                    mult = (double)lensq != 0.0 ? (float)(1.0 / Math.sqrt((double)lensq)) : 1.0F;
                    tan1x *= mult;
                    tan1y *= mult;
                    tan1z *= mult;
                    lensq = tan2x * tan2x + tan2y * tan2y + tan2z * tan2z;
                    mult = (double)lensq != 0.0 ? (float)(1.0 / Math.sqrt((double)lensq)) : 1.0F;
                    tan2x *= mult;
                    tan2y *= mult;
                    tan2z *= mult;
                    tan3x = vnz * tan1y - vny * tan1z;
                    tan3y = vnx * tan1z - vnz * tan1x;
                    tan3z = vny * tan1x - vnx * tan1y;
                    tan1w = tan2x * tan3x + tan2y * tan3y + tan2z * tan3z < 0.0F ? -1.0F : 1.0F;
                    rawBuffer[rbi + -45] = rawBuffer[rbi + -27] = rawBuffer[rbi + -9] = rawBuffer[rbi + 9] = Float.floatToRawIntBits(stess.normalX = vnx);
                    rawBuffer[rbi + -44] = rawBuffer[rbi + -26] = rawBuffer[rbi + -8] = rawBuffer[rbi + 10] = Float.floatToRawIntBits(stess.normalY = vny);
                    rawBuffer[rbi + -43] = rawBuffer[rbi + -25] = rawBuffer[rbi + -7] = rawBuffer[rbi + 11] = Float.floatToRawIntBits(stess.normalZ = vnz);
                    rawBuffer[rbi + -42] = rawBuffer[rbi + -24] = rawBuffer[rbi + -6] = rawBuffer[rbi + 12] = Float.floatToRawIntBits(tan1x);
                    rawBuffer[rbi + -41] = rawBuffer[rbi + -23] = rawBuffer[rbi + -5] = rawBuffer[rbi + 13] = Float.floatToRawIntBits(tan1y);
                    rawBuffer[rbi + -40] = rawBuffer[rbi + -22] = rawBuffer[rbi + -4] = rawBuffer[rbi + 14] = Float.floatToRawIntBits(tan1z);
                    rawBuffer[rbi + -39] = rawBuffer[rbi + -21] = rawBuffer[rbi + -3] = rawBuffer[rbi + 15] = Float.floatToRawIntBits(tan1w);
                    stess.midTextureU = (Float.intBitsToFloat(rawBuffer[rbi + -51]) + Float.intBitsToFloat(rawBuffer[rbi + -33]) + Float.intBitsToFloat(rawBuffer[rbi + -15]) + (float)tess.field_78403_j) / 4.0F;
                    stess.midTextureV = (Float.intBitsToFloat(rawBuffer[rbi + -50]) + Float.intBitsToFloat(rawBuffer[rbi + -32]) + Float.intBitsToFloat(rawBuffer[rbi + -14]) + (float)tess.field_78404_k) / 4.0F;
                    rawBuffer[rbi + -38] = rawBuffer[rbi + -20] = rawBuffer[rbi + -2] = rawBuffer[rbi + 16] = Float.floatToRawIntBits(stess.midTextureU);
                    rawBuffer[rbi + -37] = rawBuffer[rbi + -19] = rawBuffer[rbi + -1] = rawBuffer[rbi + 17] = Float.floatToRawIntBits(stess.midTextureV);
            }
        } else if (tess.field_78409_u == 4) {
            i = tess.field_78411_s % 3;
            switch (i) {
                case 0:
                    stess.v0x = fx;
                    stess.v0y = fy;
                    stess.v0z = fz;
                    stess.v0u = (float)tess.field_78403_j;
                    stess.v0v = (float)tess.field_78404_k;
                    break;
                case 1:
                    stess.v1x = fx;
                    stess.v1y = fy;
                    stess.v1z = fz;
                    stess.v1u = (float)tess.field_78403_j;
                    stess.v1v = (float)tess.field_78404_k;
                    break;
                case 2:
                    stess.v2x = fx;
                    stess.v2y = fy;
                    stess.v2z = fz;
                    stess.v2u = (float)tess.field_78403_j;
                    stess.v2v = (float)tess.field_78404_k;
                    x1 = stess.v1x - stess.v0x;
                    y1 = stess.v1y - stess.v0y;
                    z1 = stess.v1z - stess.v0z;
                    x2 = stess.v2x - stess.v1x;
                    y2 = stess.v2y - stess.v1y;
                    z2 = stess.v2z - stess.v1z;
                    vnx = y1 * z2 - y2 * z1;
                    vny = z1 * x2 - z2 * x1;
                    vnz = x1 * y2 - x2 * y1;
                    lensq = vnx * vnx + vny * vny + vnz * vnz;
                    mult = (double)lensq != 0.0 ? (float)(1.0 / Math.sqrt((double)lensq)) : 1.0F;
                    vnx *= mult;
                    vny *= mult;
                    vnz *= mult;
                    tess.field_78413_q = true;
                    x1 = stess.v1x - stess.v0x;
                    y1 = stess.v1y - stess.v0y;
                    z1 = stess.v1z - stess.v0z;
                    u1 = stess.v1u - stess.v0u;
                    v1 = stess.v1v - stess.v0v;
                    x2 = stess.v2x - stess.v0x;
                    y2 = stess.v2y - stess.v0y;
                    z2 = stess.v2z - stess.v0z;
                    u2 = stess.v2u - stess.v0u;
                    v2 = stess.v2v - stess.v0v;
                    d = u1 * v2 - u2 * v1;
                    r = d != 0.0F ? 1.0F / d : 1.0F;
                    tan1x = (v2 * x1 - v1 * x2) * r;
                    tan1y = (v2 * y1 - v1 * y2) * r;
                    tan1z = (v2 * z1 - v1 * z2) * r;
                    tan2x = (u1 * x2 - u2 * x1) * r;
                    tan2y = (u1 * y2 - u2 * y1) * r;
                    tan2z = (u1 * z2 - u2 * z1) * r;
                    lensq = tan1x * tan1x + tan1y * tan1y + tan1z * tan1z;
                    mult = (double)lensq != 0.0 ? (float)(1.0 / Math.sqrt((double)lensq)) : 1.0F;
                    tan1x *= mult;
                    tan1y *= mult;
                    tan1z *= mult;
                    lensq = tan2x * tan2x + tan2y * tan2y + tan2z * tan2z;
                    mult = (double)lensq != 0.0 ? (float)(1.0 / Math.sqrt((double)lensq)) : 1.0F;
                    tan2x *= mult;
                    tan2y *= mult;
                    tan2z *= mult;
                    tan3x = vnz * tan1y - vny * tan1z;
                    tan3y = vnx * tan1z - vnz * tan1x;
                    tan3z = vny * tan1x - vnx * tan1y;
                    tan1w = tan2x * tan3x + tan2y * tan3y + tan2z * tan3z < 0.0F ? -1.0F : 1.0F;
                    rawBuffer[rbi + -27] = rawBuffer[rbi + -9] = rawBuffer[rbi + 9] = Float.floatToRawIntBits(stess.normalX = vnx);
                    rawBuffer[rbi + -26] = rawBuffer[rbi + -8] = rawBuffer[rbi + 10] = Float.floatToRawIntBits(stess.normalY = vny);
                    rawBuffer[rbi + -25] = rawBuffer[rbi + -7] = rawBuffer[rbi + 11] = Float.floatToRawIntBits(stess.normalZ = vnz);
                    rawBuffer[rbi + -24] = rawBuffer[rbi + -6] = rawBuffer[rbi + 12] = Float.floatToRawIntBits(tan1x);
                    rawBuffer[rbi + -23] = rawBuffer[rbi + -5] = rawBuffer[rbi + 13] = Float.floatToRawIntBits(tan1y);
                    rawBuffer[rbi + -22] = rawBuffer[rbi + -4] = rawBuffer[rbi + 14] = Float.floatToRawIntBits(tan1z);
                    rawBuffer[rbi + -21] = rawBuffer[rbi + -3] = rawBuffer[rbi + 15] = Float.floatToRawIntBits(tan1w);
                    stess.midTextureU = (Float.intBitsToFloat(rawBuffer[rbi + -33]) + Float.intBitsToFloat(rawBuffer[rbi + -15]) + (float)tess.field_78403_j) / 3.0F;
                    stess.midTextureV = (Float.intBitsToFloat(rawBuffer[rbi + -32]) + Float.intBitsToFloat(rawBuffer[rbi + -14]) + (float)tess.field_78404_k) / 3.0F;
                    rawBuffer[rbi + -20] = rawBuffer[rbi + -2] = rawBuffer[rbi + 16] = Float.floatToRawIntBits(stess.midTextureU);
                    rawBuffer[rbi + -19] = rawBuffer[rbi + -1] = rawBuffer[rbi + 17] = Float.floatToRawIntBits(stess.midTextureV);
            }
        }

        ++tess.field_78411_s;
        rawBuffer[rbi + 0] = Float.floatToRawIntBits(fx);
        rawBuffer[rbi + 1] = Float.floatToRawIntBits(fy);
        rawBuffer[rbi + 2] = Float.floatToRawIntBits(fz);
        rawBuffer[rbi + 3] = Float.floatToRawIntBits((float)tess.field_78403_j);
        rawBuffer[rbi + 4] = Float.floatToRawIntBits((float)tess.field_78404_k);
        rawBuffer[rbi + 5] = tess.field_78402_m;
        rawBuffer[rbi + 6] = tess.field_78401_l;
        rawBuffer[rbi + 7] = Shaders.getEntityData();
        rawBuffer[rbi + 8] = Shaders.getEntityData2();
        rawBuffer[rbi + 9] = Float.floatToRawIntBits(stess.normalX);
        rawBuffer[rbi + 10] = Float.floatToRawIntBits(stess.normalY);
        rawBuffer[rbi + 11] = Float.floatToRawIntBits(stess.normalZ);
        rbi += 18;
        tess.field_78412_r = rbi;
        ++tess.field_78406_i;
    }
}
