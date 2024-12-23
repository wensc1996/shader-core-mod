//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBPixelBufferObject;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import shadersmodcore.transform.SMCLog;

public class Shaders {
    public static final String versionString = "2.2.3";
    public static final int versionNumber = 131587;
    public static final int buildNumber = 38;
    private static Minecraft mc;
    public static boolean isInitialized = false;
    private static boolean notFirstInit = false;
    public static ContextCapabilities capabilities;
    public static boolean hasGlGenMipmap = false;
    public static int numberResetDisplayList = 0;
    private static int renderDisplayWidth = 0;
    private static int renderDisplayHeight = 0;
    public static int renderWidth = 0;
    public static int renderHeight = 0;
    public static boolean isRenderingWorld = false;
    public static boolean isRenderingSky = false;
    public static boolean isCompositeRendered = false;
    public static boolean isRenderingDfb = false;
    public static boolean isShadowPass = false;
    public static int activeTexUnit = 0;
    public static boolean isHandRendered;
    public static boolean renderItemPass1DepthMask = false;
    public static ItemStack itemToRender;
    private static float[] sunPosition = new float[4];
    private static float[] moonPosition = new float[4];
    private static float[] upPosition = new float[4];
    private static float[] upPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
    private static float[] sunPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
    private static float[] moonPosModelView = new float[]{0.0F, -100.0F, 0.0F, 0.0F};
    private static float[] tempMat = new float[16];
    private static float clearColorR;
    private static float clearColorG;
    private static float clearColorB;
    private static float skyColorR;
    private static float skyColorG;
    private static float skyColorB;
    private static long worldTime = 0L;
    private static long lastWorldTime = 0L;
    private static long diffWorldTime = 0L;
    private static float sunAngle = 0.0F;
    private static float shadowAngle = 0.0F;
    private static int moonPhase = 0;
    private static long systemTime = 0L;
    private static long lastSystemTime = 0L;
    private static long diffSystemTime = 0L;
    private static int frameCounter = 0;
    private static float frameTimeCounter = 0.0F;
    private static int systemTimeInt32 = 0;
    private static float rainStrength = 0.0F;
    private static float wetness = 0.0F;
    public static float wetnessHalfLife = 600.0F;
    public static float drynessHalfLife = 200.0F;
    public static float eyeBrightnessHalflife = 10.0F;
    private static boolean usewetness = false;
    private static int isEyeInWater = 0;
    private static int eyeBrightness = 0;
    private static float eyeBrightnessFadeX = 0.0F;
    private static float eyeBrightnessFadeY = 0.0F;
    private static float eyePosY = 0.0F;
    private static float centerDepth = 0.0F;
    private static float centerDepthSmooth = 0.0F;
    private static float centerDepthSmoothHalflife = 1.0F;
    private static boolean centerDepthSmoothEnabled = false;
    private static int superSamplingLevel = 1;
    private static boolean updateChunksErrorRecorded = false;
    private static boolean lightmapEnabled = false;
    private static boolean fogEnabled = true;
    public static int entityAttrib = 10;
    public static int midTexCoordAttrib = 11;
    public static int tangentAttrib = 12;
    public static boolean useEntityAttrib = false;
    public static boolean useMidTexCoordAttrib = false;
    public static boolean useMultiTexCoord3Attrib = false;
    public static boolean useTangentAttrib = false;
    public static boolean progUseEntityAttrib = false;
    public static boolean progUseMidTexCoordAttrib = false;
    public static boolean progUseTangentAttrib = false;
    public static int atlasSizeX = 0;
    public static int atlasSizeY = 0;
    public static int uniformEntityHurt = -1;
    public static int uniformEntityFlash = -1;
    public static boolean useEntityHurtFlash;
    private static double[] previousCameraPosition = new double[3];
    private static double[] cameraPosition = new double[3];
    private static int shadowPassInterval = 0;
    public static boolean needResizeShadow = false;
    private static int shadowMapWidth = 1024;
    private static int shadowMapHeight = 1024;
    private static int spShadowMapWidth = 1024;
    private static int spShadowMapHeight = 1024;
    private static float shadowMapFOV = 90.0F;
    private static float shadowMapHalfPlane = 160.0F;
    private static boolean shadowMapIsOrtho = true;
    private static int shadowPassCounter = 0;
    private static int preShadowPassThirdPersonView;
    public static boolean shouldSkipDefaultShadow = false;
    private static boolean waterShadowEnabled = false;
    private static final int MaxDrawBuffers = 8;
    private static final int MaxColorBuffers = 8;
    private static final int MaxDepthBuffers = 3;
    private static final int MaxShadowColorBuffers = 8;
    private static final int MaxShadowDepthBuffers = 2;
    private static int usedColorBuffers = 0;
    private static int usedDepthBuffers = 0;
    private static int usedShadowColorBuffers = 0;
    private static int usedShadowDepthBuffers = 0;
    private static int usedColorAttachs = 0;
    private static int usedDrawBuffers = 0;
    private static int dfb = 0;
    private static int sfb = 0;
    private static int[] gbuffersFormat = new int[8];
    public static int activeProgram = 0;
    public static final int ProgramNone = 0;
    public static final int ProgramBasic = 1;
    public static final int ProgramTextured = 2;
    public static final int ProgramTexturedLit = 3;
    public static final int ProgramSkyBasic = 4;
    public static final int ProgramSkyTextured = 5;
    public static final int ProgramTerrain = 6;
    public static final int ProgramWater = 7;
    public static final int ProgramEntities = 8;
    public static final int ProgramSpiderEyes = 9;
    public static final int ProgramHand = 10;
    public static final int ProgramWeather = 11;
    public static final int ProgramComposite = 12;
    public static final int ProgramComposite1 = 13;
    public static final int ProgramComposite2 = 14;
    public static final int ProgramComposite3 = 15;
    public static final int ProgramComposite4 = 16;
    public static final int ProgramComposite5 = 17;
    public static final int ProgramComposite6 = 18;
    public static final int ProgramComposite7 = 19;
    public static final int ProgramFinal = 20;
    public static final int ProgramShadow = 21;
    public static final int ProgramCount = 22;
    public static final int MaxCompositePasses = 8;
    private static final String[] programNames = new String[]{"", "gbuffers_basic", "gbuffers_textured", "gbuffers_textured_lit", "gbuffers_skybasic", "gbuffers_skytextured", "gbuffers_terrain", "gbuffers_water", "gbuffers_entities", "gbuffers_spidereyes", "gbuffers_hand", "gbuffers_weather", "composite", "composite1", "composite2", "composite3", "composite4", "composite5", "composite6", "composite7", "final", "shadow"};
    private static final int[] programBackups = new int[]{0, 0, 1, 2, 1, 2, 3, 6, 3, 2, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static int[] programsID = new int[22];
    private static int[] programsRef = new int[22];
    private static int programIDCopyDepth = 0;
    private static String[] programsDrawBufSettings = new String[22];
    private static String newDrawBufSetting = null;
    private static IntBuffer[] programsDrawBuffers = new IntBuffer[22];
    static IntBuffer activeDrawBuffers = null;
    private static String[] programsColorAtmSettings = new String[22];
    private static String newColorAtmSetting = null;
    private static String activeColorAtmSettings = null;
    private static int[] programsCompositeMipmapSetting = new int[22];
    private static int newCompositeMipmapSetting = 0;
    private static int activeCompositeMipmapSetting = 0;
    public static Properties loadedShaders = null;
    public static Properties shadersConfig = null;
    public static TextureObject defaultTexture = null;
    public static boolean normalMapEnabled = false;
    public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
    public static boolean[] shadowMipmapEnabled = new boolean[2];
    public static boolean[] shadowFilterNearest = new boolean[2];
    public static boolean[] shadowColorMipmapEnabled = new boolean[8];
    public static boolean[] shadowColorFilterNearest = new boolean[8];
    public static boolean configTweakBlockDamage = false;
    public static boolean configCloudShadow = true;
    public static float configHandDepthMul = 0.125F;
    public static float configRenderResMul = 1.0F;
    public static float configShadowResMul = 1.0F;
    public static int configTexMinFilB = 0;
    public static int configTexMinFilN = 0;
    public static int configTexMinFilS = 0;
    public static int configTexMagFilB = 0;
    public static int configTexMagFilN = 0;
    public static int configTexMagFilS = 0;
    public static boolean configShadowClipFrustrum = true;
    public static boolean configNormalMap = true;
    public static boolean configSpecularMap = true;
    public static boolean configOldLighting = false;
    public static final int texMinFilRange = 3;
    public static final int texMagFilRange = 2;
    public static final String[] texMinFilDesc = new String[]{"Nearest", "Nearest-Nearest", "Nearest-Linear"};
    public static final String[] texMagFilDesc = new String[]{"Nearest", "Linear"};
    public static final int[] texMinFilValue = new int[]{9728, 9984, 9986};
    public static final int[] texMagFilValue = new int[]{9728, 9729};
    static IShaderPack shaderPack = null;
    static File currentshader;
    static String currentshadername;
    static String packNameNone = "(none)";
    static String packNameDefault = "(internal)";
    static String shaderpacksdirname = "shaderpacks";
    static String optionsfilename = "optionsshaders.txt";
    static File shadersdir;
    static File shaderpacksdir;
    static File configFile;
    public static final boolean enableShadersOption = true;
    private static final boolean enableShadersDebug = true;
    public static float blockLightLevel05;
    public static float blockLightLevel06;
    public static float blockLightLevel08;
    public static float aoLevel;
    public static float blockAoLight;
    public static float sunPathRotation;
    public static float shadowAngleInterval;
    public static int fogMode;
    public static float fogColorR;
    public static float fogColorG;
    public static float fogColorB;
    public static float shadowIntervalSize;
    public static int terrainIconSize;
    public static int[] terrainTextureSize;
    private static HFNoiseTexture noiseTexture;
    private static boolean noiseTextureEnabled;
    private static int noiseTextureResolution;
    private static final int bigBufferSize = 1812;
    private static final ByteBuffer bigBuffer;
    private static final FloatBuffer previousProjection;
    private static final FloatBuffer projection;
    private static final FloatBuffer projectionInverse;
    private static final FloatBuffer previousModelView;
    private static final FloatBuffer modelView;
    private static final FloatBuffer modelViewInverse;
    private static final FloatBuffer shadowProjection;
    private static final FloatBuffer shadowProjectionInverse;
    private static final FloatBuffer shadowModelView;
    private static final FloatBuffer shadowModelViewInverse;
    private static final FloatBuffer tempMatrixDirectBuffer;
    private static final FloatBuffer tempDirectFloatBuffer;
    private static final IntBuffer dfbColorTextures;
    private static final IntBuffer dfbDepthTextures;
    private static final IntBuffer sfbColorTextures;
    private static final IntBuffer sfbDepthTextures;
    static final IntBuffer dfbDrawBuffers;
    static final IntBuffer sfbDrawBuffers;
    static final IntBuffer drawBuffersNone;
    static final IntBuffer drawBuffersAll;
    static final IntBuffer drawBuffersClear0;
    static final IntBuffer drawBuffersClear1;
    static final IntBuffer drawBuffersClearColor;
    static final IntBuffer drawBuffersColorAtt0;
    static final IntBuffer[] drawBuffersBuffer;
    private static int idPixelBufferReadDepth;
    private static ByteBuffer byteBufferReadDepth;
    private static FloatBuffer floatBufferReadDepth;
    private static Pattern gbufferFormatPattern;
    private static Pattern gbufferMipmapEnabledPattern;
    static float[] invertMat4x_m;
    static float[] invertMat4x_inv;
    public static int[] entityData;
    public static int entityDataIndex;

    private Shaders() {
    }

    private static ByteBuffer nextByteBuffer(int size) {
        ByteBuffer buffer = bigBuffer;
        int pos = buffer.limit();
        buffer.position(pos).limit(pos + size);
        return buffer.slice();
    }

    private static IntBuffer nextIntBuffer(int size) {
        ByteBuffer buffer = bigBuffer;
        int pos = buffer.limit();
        buffer.position(pos).limit(pos + size * 4);
        return buffer.asIntBuffer();
    }

    private static FloatBuffer nextFloatBuffer(int size) {
        ByteBuffer buffer = bigBuffer;
        int pos = buffer.limit();
        buffer.position(pos).limit(pos + size * 4);
        return buffer.asFloatBuffer();
    }

    private static IntBuffer[] nextIntBufferArray(int count, int size) {
        IntBuffer[] aib = new IntBuffer[count];

        for(int i = 0; i < count; ++i) {
            aib[i] = nextIntBuffer(size);
        }

        return aib;
    }

    public static void loadConfig() {
        SMCLog.info("Load ShadersMod configuration.", new Object[0]);

        try {
            if (!shaderpacksdir.exists()) {
                shaderpacksdir.mkdir();
            }
        } catch (Exception var3) {
            SMCLog.severe("Failed openning shaderpacks directory.", new Object[0]);
        }

        shadersConfig = new Properties();
        shadersConfig.setProperty("shaderPack", "");
        FileReader value;
        if (configFile.exists()) {
            try {
                value = new FileReader(configFile);
                shadersConfig.load(value);
                value.close();
            } catch (Exception var2) {
            }
        }

        if (!configFile.exists()) {
            try {
                storeConfig();
            } catch (Exception var1) {
            }
        }

        value = null;
        configNormalMap = Boolean.parseBoolean(shadersConfig.getProperty("normalMapEnabled", "true"));
        configSpecularMap = Boolean.parseBoolean(shadersConfig.getProperty("specularMapEnabled", "true"));
        configTweakBlockDamage = Boolean.parseBoolean(shadersConfig.getProperty("tweakBlockDamage", shadersConfig.getProperty("dtweak", "false")));
        configCloudShadow = Boolean.parseBoolean(shadersConfig.getProperty("cloudShadow", "true"));
        configHandDepthMul = Float.parseFloat(shadersConfig.getProperty("handDepthMul", "0.125"));
        configRenderResMul = Float.parseFloat(shadersConfig.getProperty("renderResMul", "1.0"));
        configShadowResMul = Float.parseFloat(shadersConfig.getProperty("shadowResMul", "1.0"));
        configShadowClipFrustrum = Boolean.parseBoolean(shadersConfig.getProperty("shadowClipFrustrum", "true"));
        configOldLighting = Boolean.parseBoolean(shadersConfig.getProperty("oldLighting", "false"));
        configTexMinFilB = Integer.parseInt(shadersConfig.getProperty("TexMinFilB", "0")) % 3;
        configTexMinFilN = Integer.parseInt(shadersConfig.getProperty("TexMinFilN", Integer.toString(configTexMinFilB))) % 3;
        configTexMinFilS = Integer.parseInt(shadersConfig.getProperty("TexMinFilS", Integer.toString(configTexMinFilB))) % 3;
        configTexMagFilB = Integer.parseInt(shadersConfig.getProperty("TexMagFilB", "0")) % 2;
        configTexMagFilN = Integer.parseInt(shadersConfig.getProperty("TexMagFilN", "0")) % 2;
        configTexMagFilS = Integer.parseInt(shadersConfig.getProperty("TexMagFilS", "0")) % 2;
        currentshadername = shadersConfig.getProperty("shaderPack", packNameDefault);
        loadShaderPack();
    }

    public static void storeConfig() {
        SMCLog.info("Save ShadersMod configuration.", new Object[0]);
        shadersConfig.setProperty("normalMapEnabled", Boolean.toString(configNormalMap));
        shadersConfig.setProperty("specularMapEnabled", Boolean.toString(configSpecularMap));
        shadersConfig.setProperty("tweakBlockDamage", Boolean.toString(configTweakBlockDamage));
        shadersConfig.setProperty("cloudShadow", Boolean.toString(configCloudShadow));
        shadersConfig.setProperty("handDepthMul", Float.toString(configHandDepthMul));
        shadersConfig.setProperty("renderResMul", Float.toString(configRenderResMul));
        shadersConfig.setProperty("shadowResMul", Float.toString(configShadowResMul));
        shadersConfig.setProperty("shadowClipFrustrum", Boolean.toString(configShadowClipFrustrum));
        shadersConfig.setProperty("oldLighting", Boolean.toString(configOldLighting));
        shadersConfig.setProperty("TexMinFilB", Integer.toString(configTexMinFilB));
        shadersConfig.setProperty("TexMinFilN", Integer.toString(configTexMinFilN));
        shadersConfig.setProperty("TexMinFilS", Integer.toString(configTexMinFilS));
        shadersConfig.setProperty("TexMagFilB", Integer.toString(configTexMagFilB));
        shadersConfig.setProperty("TexMagFilN", Integer.toString(configTexMagFilN));
        shadersConfig.setProperty("TexMagFilS", Integer.toString(configTexMagFilS));

        try {
            FileWriter writer = new FileWriter(configFile);
            shadersConfig.store(writer, (String)null);
            writer.close();
        } catch (Exception var1) {
        }

    }

    public static void setShaderPack(String par1name) {
        currentshadername = par1name;
        shadersConfig.setProperty("shaderPack", par1name);
    }

    public static void loadShaderPack() {
        if (shaderPack != null) {
            shaderPack.close();
            shaderPack = null;
        }

        String packName = shadersConfig.getProperty("shaderPack", packNameDefault);
        if (!packName.isEmpty() && !packName.equals(packNameNone)) {
            if (packName.equals(packNameDefault)) {
                shaderPack = new ShaderPackDefault();
            } else {
                try {
                    File packFile = new File(shaderpacksdir, packName);
                    if (packFile.isDirectory()) {
                        shaderPack = new ShaderPackFolder(packName, packFile);
                    } else if (packFile.isFile() && packName.toLowerCase().endsWith(".zip")) {
                        shaderPack = new ShaderPackZip(packName, packFile);
                    }
                } catch (Exception var2) {
                }
            }
        }

        if (shaderPack != null) {
            SMCLog.info("Loaded shaderpack.", new Object[0]);
        } else {
            SMCLog.info("Did not load shaderpack.", new Object[0]);
            shaderPack = new ShaderPackNone();
        }

    }

    static ArrayList listofShaders() {
        ArrayList<String> list = new ArrayList();
        list.add(packNameNone);
        list.add(packNameDefault);

        try {
            if (!shaderpacksdir.exists()) {
                shaderpacksdir.mkdir();
            }

            File[] listOfFiles = shaderpacksdir.listFiles();

            for(int i = 0; i < listOfFiles.length; ++i) {
                File file = listOfFiles[i];
                String name = file.getName();
                if (file.isDirectory() || file.isFile() && name.toLowerCase().endsWith(".zip")) {
                    list.add(name);
                }
            }
        } catch (Exception var5) {
        }

        return list;
    }

    static String versiontostring(int vv) {
        String vs = Integer.toString(vv);
        return Integer.toString(Integer.parseInt(vs.substring(1, 3))) + "." + Integer.toString(Integer.parseInt(vs.substring(3, 5))) + "." + Integer.toString(Integer.parseInt(vs.substring(5)));
    }

    static void checkOptifine() {
    }

    public static int checkFramebufferStatus(String location) {
        int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (status != 36053) {
            System.err.format("FramebufferStatus 0x%04X at %s\n", status, location);
        }

        return status;
    }

    public static int checkGLError(String location) {
        int errorCode = GL11.glGetError();
        if (errorCode != 0) {
            boolean skipPrint = false;
            if (!skipPrint) {
                if (errorCode == 1286) {
                    int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
                    System.err.format("GL error 0x%04X: %s (Fb status 0x%04X) at %s\n", errorCode, GLU.gluErrorString(errorCode), status, location);
                } else {
                    System.err.format("GL error 0x%04X: %s at %s\n", errorCode, GLU.gluErrorString(errorCode), location);
                }
            }
        }

        return errorCode;
    }

    public static int checkGLError(String location, String info) {
        int errorCode = GL11.glGetError();
        if (errorCode != 0) {
            System.err.format("GL error 0x%04x: %s at %s %s\n", errorCode, GLU.gluErrorString(errorCode), location, info);
        }

        return errorCode;
    }

    public static int checkGLError(String location, String info1, String info2) {
        int errorCode = GL11.glGetError();
        if (errorCode != 0) {
            System.err.format("GL error 0x%04x: %s at %s %s %s\n", errorCode, GLU.gluErrorString(errorCode), location, info1, info2);
        }

        return errorCode;
    }

    private static String printChatAndLogError(String str) {
        SMCLog.severe(str, new Object[0]);
        mc.field_71456_v.func_73827_b().func_73765_a(str);
        return str;
    }

    public static void printIntBuffer(String title, IntBuffer buf) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
        int lim = buf.limit();

        for(int i = 0; i < lim; ++i) {
            sb.append(" ").append(buf.get(i));
        }

        sb.append("]");
        SMCLog.info(sb.toString(), new Object[0]);
    }

    public static void startup(Minecraft mc) {
        Shaders.mc = mc;
        SMCLog.info("ShadersMod version 2.2.3", new Object[0]);
        loadConfig();
    }

    private static String toStringYN(boolean b) {
        return b ? "Y" : "N";
    }

    public static void updateBlockLightLevel() {
        if (configOldLighting) {
            blockLightLevel05 = 0.5F;
            blockLightLevel06 = 0.6F;
            blockLightLevel08 = 0.8F;
        } else {
            blockLightLevel05 = 1.0F;
            blockLightLevel06 = 1.0F;
            blockLightLevel08 = 1.0F;
        }

    }

    public static void init() {
        if (!isInitialized) {
            mc = Minecraft.func_71410_x();
            checkGLError("Shaders.init pre");
            capabilities = GLContext.getCapabilities();
            SMCLog.info("OpenGL Version : %s", new Object[]{GL11.glGetString(7938)});
            SMCLog.info("Vendor :  %s", new Object[]{GL11.glGetString(7936)});
            SMCLog.info("Renderer : %s", new Object[]{GL11.glGetString(7937)});
            SMCLog.info("Capabilities " + (capabilities.OpenGL20 ? " 2.0" : " -") + " " + (capabilities.OpenGL21 ? " 2.1" : " -") + " " + (capabilities.OpenGL30 ? " 3.0" : " -") + " " + (capabilities.OpenGL32 ? " 3.2" : " -") + " " + (capabilities.OpenGL40 ? " 4.0" : " -"), new Object[0]);
            if (!capabilities.OpenGL21) {
                printChatAndLogError("No OpenGL 2.1.");
            }

            if (!capabilities.GL_EXT_framebuffer_object) {
                printChatAndLogError("No EXT_framebuffer_object.");
            }

            if (!capabilities.OpenGL20 || !capabilities.GL_EXT_framebuffer_object) {
                printChatAndLogError("Your GPU is not compatible with the Shaders mod.");
            }

            hasGlGenMipmap = capabilities.OpenGL30;
            dfbDrawBuffers.position(0).limit(8);
            dfbColorTextures.position(0).limit(8);
            dfbDepthTextures.position(0).limit(3);
            sfbDrawBuffers.position(0).limit(8);
            sfbDepthTextures.position(0).limit(2);
            sfbColorTextures.position(0).limit(8);
            int maxDrawBuffers = GL11.glGetInteger(34852);
            int maxColorAttach = GL11.glGetInteger(36063);
            SMCLog.info("GL_MAX_DRAW_BUFFERS = " + maxDrawBuffers, new Object[0]);
            SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT = " + maxColorAttach, new Object[0]);
            SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS = " + GL11.glGetInteger(34930), new Object[0]);
            usedColorBuffers = 4;
            usedDepthBuffers = 1;
            usedShadowColorBuffers = 0;
            usedShadowDepthBuffers = 0;
            usedColorAttachs = 1;
            usedDrawBuffers = 1;
            Arrays.fill(gbuffersFormat, 6408);
            Arrays.fill(shadowHardwareFilteringEnabled, false);
            Arrays.fill(shadowMipmapEnabled, false);
            Arrays.fill(shadowFilterNearest, false);
            Arrays.fill(shadowColorMipmapEnabled, false);
            Arrays.fill(shadowColorFilterNearest, false);
            centerDepthSmoothEnabled = false;
            noiseTextureEnabled = false;
            sunPathRotation = 0.0F;
            shadowIntervalSize = 2.0F;
            aoLevel = 0.8F;
            blockAoLight = 1.0F - aoLevel;
            useEntityAttrib = false;
            useMidTexCoordAttrib = false;
            useMultiTexCoord3Attrib = false;
            useTangentAttrib = false;
            waterShadowEnabled = false;
            updateChunksErrorRecorded = false;
            updateBlockLightLevel();

            int p;
            for(int i = 0; i < 22; ++i) {
                if (programNames[i] == "") {
                    programsID[i] = programsRef[i] = 0;
                    programsDrawBufSettings[i] = null;
                    programsColorAtmSettings[i] = null;
                    programsCompositeMipmapSetting[i] = 0;
                } else {
                    newDrawBufSetting = null;
                    newColorAtmSetting = null;
                    newCompositeMipmapSetting = 0;
                    p = setupProgram(i, "/shaders/" + programNames[i] + ".vsh", "/shaders/" + programNames[i] + ".fsh");
                    programsID[i] = programsRef[i] = p;
                    programsDrawBufSettings[i] = p != 0 ? newDrawBufSetting : null;
                    programsColorAtmSettings[i] = p != 0 ? newColorAtmSetting : null;
                    programsCompositeMipmapSetting[i] = p != 0 ? newCompositeMipmapSetting : 0;
                }
            }

            new HashMap();

            for(p = 0; p < 22; ++p) {
                if (p == 20) {
                    programsDrawBuffers[p] = null;
                } else if (programsID[p] == 0) {
                    if (p == 21) {
                        programsDrawBuffers[p] = drawBuffersNone;
                    } else {
                        programsDrawBuffers[p] = drawBuffersColorAtt0;
                    }
                } else {
                    String str = programsDrawBufSettings[p];
                    if (str != null) {
                        IntBuffer intbuf = drawBuffersBuffer[p];
                        int numDB = str.length();
                        if (numDB > usedDrawBuffers) {
                            usedDrawBuffers = numDB;
                        }

                        if (numDB > maxDrawBuffers) {
                            numDB = maxDrawBuffers;
                        }

                        programsDrawBuffers[p] = intbuf;
                        intbuf.limit(numDB);

                        for(int i = 0; i < numDB; ++i) {
                            int d = 0;
                            if (str.length() > i) {
                                int ca = str.charAt(i) - 48;
                                if (p != 21) {
                                    if (ca >= 0 && ca <= 7) {
                                        d = ca + '賠';
                                        if (ca > usedColorAttachs) {
                                            usedColorAttachs = ca;
                                        }

                                        if (ca > usedColorBuffers) {
                                            usedColorBuffers = ca;
                                        }
                                    }
                                } else if (ca >= 0 && ca <= 1) {
                                    d = ca + '賠';
                                    if (ca > usedShadowColorBuffers) {
                                        usedShadowColorBuffers = ca;
                                    }
                                }
                            }

                            intbuf.put(i, d);
                        }
                    } else if (p != 21) {
                        programsDrawBuffers[p] = dfbDrawBuffers;
                        usedDrawBuffers = usedColorBuffers;
                    } else {
                        programsDrawBuffers[p] = sfbDrawBuffers;
                    }
                }
            }

            usedColorAttachs = usedColorBuffers;
            shadowPassInterval = usedShadowDepthBuffers > 0 ? 1 : 0;
            shouldSkipDefaultShadow = usedShadowDepthBuffers > 0;
            dfbDrawBuffers.position(0).limit(usedDrawBuffers);
            dfbColorTextures.position(0).limit(usedColorBuffers);

            for(p = 0; p < usedDrawBuffers; ++p) {
                dfbDrawBuffers.put(p, '賠' + p);
            }

            if (usedDrawBuffers > maxDrawBuffers) {
                printChatAndLogError("Not enough draw buffers! Requires " + usedDrawBuffers + ".  Has " + maxDrawBuffers + ".");
            }

            sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);

            for(p = 0; p < usedShadowColorBuffers; ++p) {
                sfbDrawBuffers.put(p, '賠' + p);
            }

            for(p = 0; p < 22; ++p) {
                int n;
                for(n = p; programsID[n] == 0 && programBackups[n] != n; n = programBackups[n]) {
                }

                if (n != p && p != 21) {
                    programsID[p] = programsID[n];
                    programsDrawBufSettings[p] = programsDrawBufSettings[n];
                    programsDrawBuffers[p] = programsDrawBuffers[n];
                }
            }

            resize();
            resizeShadow();
            if (noiseTextureEnabled) {
                setupNoiseTexture();
            }

            if (defaultTexture == null) {
                defaultTexture = ShadersTex.createDefaultTexture();
            }

            isInitialized = true;
            if (idPixelBufferReadDepth <= 0) {
                idPixelBufferReadDepth = GL15.glGenBuffers();
                GL15.glBindBuffer(35051, idPixelBufferReadDepth);
                checkGLError("BindBuffer");
                ARBPixelBufferObject.glBufferDataARB(35051, 16L, 35041);
                checkGLError("BufferData");
                GL11.glPixelStorei(3329, 1);
                GL15.glBindBuffer(35051, 0);
                checkGLError("BindBuffer 0");
            }

            resetDisplayList();
            if (notFirstInit) {
                mc.field_71456_v.func_73827_b().func_73765_a("Shaders initialized.");
            }

            checkGLError("Shaders.init");
        }

    }

    public static void resetDisplayList() {
        ++numberResetDisplayList;
        SMCLog.info("Reset model renderers", new Object[0]);
        if (useMidTexCoordAttrib || useMultiTexCoord3Attrib || useTangentAttrib) {
            Iterator<Render> it = RenderManager.field_78727_a.field_78729_o.values().iterator();

            while(it.hasNext()) {
                Render ren = (Render)it.next();
                if (ren instanceof RendererLivingEntity) {
                    RendererLivingEntity rle = (RendererLivingEntity)ren;
                    resetDisplayListModel(rle.field_77045_g);
                    resetDisplayListModel(rle.field_77046_h);
                }
            }
        }

        SMCLog.info("Reset world renderers", new Object[0]);
        mc.field_71438_f.func_72712_a();
        SMCLog.info(".", new Object[0]);
    }

    public static void resetDisplayListModel(ModelBase mbase) {
        if (mbase != null) {
            Iterator it = mbase.field_78092_r.iterator();

            while(it.hasNext()) {
                Object obj = it.next();
                if (obj instanceof ModelRenderer) {
                    resetDisplayListModelRenderer((ModelRenderer)obj);
                }
            }
        }

    }

    public static void resetDisplayListModelRenderer(ModelRenderer mrr) {
        mrr.resetDisplayList();
        if (mrr.field_78805_m != null) {
            int i = 0;

            for(int n = mrr.field_78805_m.size(); i < n; ++i) {
                resetDisplayListModelRenderer((ModelRenderer)mrr.field_78805_m.get(i));
            }
        }

    }

    private static int setupProgram(int program, String vShaderPath, String fShaderPath) {
        checkGLError("pre setupProgram");
        int programid = ARBShaderObjects.glCreateProgramObjectARB();
        checkGLError("create");
        if (programid != 0) {
            progUseEntityAttrib = false;
            progUseMidTexCoordAttrib = false;
            progUseTangentAttrib = false;
            int vShader = createVertShader(vShaderPath);
            int fShader = createFragShader(fShaderPath);
            checkGLError("create");
            if (vShader == 0 && fShader == 0) {
                ARBShaderObjects.glDeleteObjectARB(programid);
                programid = 0;
            } else {
                if (vShader != 0) {
                    ARBShaderObjects.glAttachObjectARB(programid, vShader);
                    checkGLError("attach");
                }

                if (fShader != 0) {
                    ARBShaderObjects.glAttachObjectARB(programid, fShader);
                    checkGLError("attach");
                }

                if (progUseEntityAttrib) {
                    ARBVertexShader.glBindAttribLocationARB(programid, entityAttrib, "mc_Entity");
                    checkGLError("mc_Entity");
                }

                if (progUseMidTexCoordAttrib) {
                    ARBVertexShader.glBindAttribLocationARB(programid, midTexCoordAttrib, "mc_midTexCoord");
                    checkGLError("mc_midTexCoord");
                }

                if (progUseTangentAttrib) {
                    ARBVertexShader.glBindAttribLocationARB(programid, tangentAttrib, "at_tangent");
                    checkGLError("at_tangent");
                }

                ARBShaderObjects.glLinkProgramARB(programid);
                if (vShader != 0) {
                    ARBShaderObjects.glDetachObjectARB(programid, vShader);
                    ARBShaderObjects.glDeleteObjectARB(vShader);
                }

                if (fShader != 0) {
                    ARBShaderObjects.glDetachObjectARB(programid, fShader);
                    ARBShaderObjects.glDeleteObjectARB(fShader);
                }

                programsID[program] = programid;
                useProgram(program);
                ARBShaderObjects.glValidateProgramARB(programid);
                useProgram(0);
                printLogInfo(programid, vShaderPath + "," + fShaderPath);
                int valid = GL20.glGetProgrami(programid, 35715);
                if (valid == 1) {
                    SMCLog.info("Program " + programNames[program] + " loaded", new Object[0]);
                } else {
                    printChatAndLogError("[Shaders] Error : Invalid program " + programNames[program]);
                    ARBShaderObjects.glDeleteObjectARB(programid);
                    programid = 0;
                }
            }
        }

        return programid;
    }

    private static int createVertShader(String filename) {
        int vertShader = ARBShaderObjects.glCreateShaderObjectARB(35633);
        if (vertShader == 0) {
            return 0;
        } else {
            String vertexCode = "";
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
            } catch (Exception var9) {
                try {
                    reader = new BufferedReader(new FileReader(new File(filename)));
                } catch (Exception var8) {
                    ARBShaderObjects.glDeleteObjectARB(vertShader);
                    return 0;
                }
            }

            if (reader != null) {
                String line;
                try {
                    while((line = reader.readLine()) != null) {
                        vertexCode = vertexCode + line + "\n";
                        if (line.matches("attribute [_a-zA-Z0-9]+ mc_Entity.*")) {
                            useEntityAttrib = true;
                            progUseEntityAttrib = true;
                        } else if (line.matches("attribute [_a-zA-Z0-9]+ mc_midTexCoord.*")) {
                            useMidTexCoordAttrib = true;
                            progUseMidTexCoordAttrib = true;
                        } else if (line.matches(".*gl_MultiTexCoord3.*")) {
                            useMultiTexCoord3Attrib = true;
                        } else if (line.matches("attribute [_a-zA-Z0-9]+ at_tangent.*")) {
                            useTangentAttrib = true;
                            progUseTangentAttrib = true;
                        }
                    }
                } catch (Exception var10) {
                    SMCLog.severe("Couldn't read " + filename + "!", new Object[0]);
                    var10.printStackTrace();
                    ARBShaderObjects.glDeleteObjectARB(vertShader);
                    return 0;
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception var7) {
                    SMCLog.warning("Couldn't close " + filename + "!", new Object[0]);
                }
            }

            ARBShaderObjects.glShaderSourceARB(vertShader, vertexCode);
            ARBShaderObjects.glCompileShaderARB(vertShader);
            printLogInfo(vertShader, filename);
            return vertShader;
        }
    }

    private static int createFragShader(String filename) {
        int fragShader = ARBShaderObjects.glCreateShaderObjectARB(35632);
        if (fragShader == 0) {
            return 0;
        } else {
            StringBuilder fragCode = new StringBuilder(1048576);
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
            } catch (Exception var12) {
                try {
                    reader = new BufferedReader(new FileReader(new File(filename)));
                } catch (Exception var11) {
                    ARBShaderObjects.glDeleteObjectARB(fragShader);
                    return 0;
                }
            }

            if (reader != null) {
                try {
                    label323:
                    while(true) {
                        while(true) {
                            String line;
                            do {
                                if ((line = reader.readLine()) == null) {
                                    break label323;
                                }

                                fragCode.append(line).append('\n');
                            } while(line.matches("#version .*"));

                            if (line.matches("uniform [ _a-zA-Z0-9]+ shadow;.*")) {
                                if (usedShadowDepthBuffers < 1) {
                                    usedShadowDepthBuffers = 1;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ watershadow;.*")) {
                                waterShadowEnabled = true;
                                if (usedShadowDepthBuffers < 2) {
                                    usedShadowDepthBuffers = 2;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowtex0;.*")) {
                                if (usedShadowDepthBuffers < 1) {
                                    usedShadowDepthBuffers = 1;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowtex1;.*")) {
                                if (usedShadowDepthBuffers < 2) {
                                    usedShadowDepthBuffers = 2;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowcolor;.*")) {
                                if (usedShadowColorBuffers < 1) {
                                    usedShadowColorBuffers = 1;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowcolor0;.*")) {
                                if (usedShadowColorBuffers < 1) {
                                    usedShadowColorBuffers = 1;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ shadowcolor1;.*")) {
                                if (usedShadowColorBuffers < 2) {
                                    usedShadowColorBuffers = 2;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ depthtex0;.*")) {
                                if (usedDepthBuffers < 1) {
                                    usedDepthBuffers = 1;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ depthtex1;.*")) {
                                if (usedDepthBuffers < 2) {
                                    usedDepthBuffers = 2;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ depthtex2;.*")) {
                                if (usedDepthBuffers < 3) {
                                    usedDepthBuffers = 3;
                                }
                            } else if (line.matches("uniform [ _a-zA-Z0-9]+ gdepth;.*")) {
                                if (gbuffersFormat[1] == 6408) {
                                    gbuffersFormat[1] = 34836;
                                }
                            } else if (usedColorBuffers < 5 && line.matches("uniform [ _a-zA-Z0-9]+ gaux1;.*")) {
                                usedColorBuffers = 5;
                            } else if (usedColorBuffers < 6 && line.matches("uniform [ _a-zA-Z0-9]+ gaux2;.*")) {
                                usedColorBuffers = 6;
                            } else if (usedColorBuffers < 7 && line.matches("uniform [ _a-zA-Z0-9]+ gaux3;.*")) {
                                usedColorBuffers = 7;
                            } else if (usedColorBuffers < 8 && line.matches("uniform [ _a-zA-Z0-9]+ gaux4;.*")) {
                                usedColorBuffers = 8;
                            } else if (usedColorBuffers < 5 && line.matches("uniform [ _a-zA-Z0-9]+ colortex4;.*")) {
                                usedColorBuffers = 5;
                            } else if (usedColorBuffers < 6 && line.matches("uniform [ _a-zA-Z0-9]+ colortex5;.*")) {
                                usedColorBuffers = 6;
                            } else if (usedColorBuffers < 7 && line.matches("uniform [ _a-zA-Z0-9]+ colortex6;.*")) {
                                usedColorBuffers = 7;
                            } else if (usedColorBuffers < 8 && line.matches("uniform [ _a-zA-Z0-9]+ colortex7;.*")) {
                                usedColorBuffers = 8;
                            } else if (usedColorBuffers < 8 && line.matches("uniform [ _a-zA-Z0-9]+ centerDepthSmooth;.*")) {
                                centerDepthSmoothEnabled = true;
                            } else {
                                String[] parts;
                                if (line.matches("/\\* SHADOWRES:[0-9]+ \\*/.*")) {
                                    parts = line.split("(:| )", 4);
                                    SMCLog.info("Shadow map resolution: " + parts[2], new Object[0]);
                                    spShadowMapWidth = spShadowMapHeight = Integer.parseInt(parts[2]);
                                    shadowMapWidth = shadowMapHeight = Math.round((float)spShadowMapWidth * configShadowResMul);
                                } else if (line.matches("[ \t]*const[ \t]*int[ \t]*shadowMapResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Shadow map resolution: " + parts[1], new Object[0]);
                                    spShadowMapWidth = spShadowMapHeight = Integer.parseInt(parts[1]);
                                    shadowMapWidth = shadowMapHeight = Math.round((float)spShadowMapWidth * configShadowResMul);
                                } else if (line.matches("/\\* SHADOWFOV:[0-9\\.]+ \\*/.*")) {
                                    parts = line.split("(:| )", 4);
                                    SMCLog.info("Shadow map field of view: " + parts[2], new Object[0]);
                                    shadowMapFOV = Float.parseFloat(parts[2]);
                                    shadowMapIsOrtho = false;
                                } else if (line.matches("/\\* SHADOWHPL:[0-9\\.]+ \\*/.*")) {
                                    parts = line.split("(:| )", 4);
                                    SMCLog.info("Shadow map half-plane: " + parts[2], new Object[0]);
                                    shadowMapHalfPlane = Float.parseFloat(parts[2]);
                                    shadowMapIsOrtho = true;
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*shadowDistance[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Shadow map distance: " + parts[1], new Object[0]);
                                    shadowMapHalfPlane = Float.parseFloat(parts[1]);
                                    shadowMapIsOrtho = true;
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*shadowIntervalSize[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Shadow map interval size: " + parts[1], new Object[0]);
                                    shadowIntervalSize = Float.parseFloat(parts[1]);
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*generateShadowMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("Generate shadow mipmap", new Object[0]);
                                    Arrays.fill(shadowMipmapEnabled, true);
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*generateShadowColorMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("Generate shadow color mipmap", new Object[0]);
                                    Arrays.fill(shadowColorMipmapEnabled, true);
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("Hardware shadow filtering enabled.", new Object[0]);
                                    Arrays.fill(shadowHardwareFilteringEnabled, true);
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering0[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowHardwareFiltering0", new Object[0]);
                                    shadowHardwareFilteringEnabled[0] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering1[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowHardwareFiltering1", new Object[0]);
                                    shadowHardwareFilteringEnabled[1] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex0Mipmap|shadowtexMipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowtex0Mipmap", new Object[0]);
                                    shadowMipmapEnabled[0] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowtex1Mipmap", new Object[0]);
                                    shadowMipmapEnabled[1] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Mipmap|shadowColor0Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowcolor0Mipmap", new Object[0]);
                                    shadowColorMipmapEnabled[0] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Mipmap|shadowColor1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowcolor1Mipmap", new Object[0]);
                                    shadowColorMipmapEnabled[1] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex0Nearest|shadowtexNearest|shadow0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowtex0Nearest", new Object[0]);
                                    shadowFilterNearest[0] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowtex1Nearest|shadow1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowtex1Nearest", new Object[0]);
                                    shadowFilterNearest[1] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Nearest|shadowColor0Nearest|shadowColor0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowcolor0Nearest", new Object[0]);
                                    shadowColorFilterNearest[0] = true;
                                } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Nearest|shadowColor1Nearest|shadowColor1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
                                    SMCLog.info("shadowcolor1Nearest", new Object[0]);
                                    shadowColorFilterNearest[1] = true;
                                } else if (line.matches("/\\* WETNESSHL:[0-9\\.]+ \\*/.*")) {
                                    parts = line.split("(:| )", 4);
                                    SMCLog.info("Wetness halflife: " + parts[2], new Object[0]);
                                    wetnessHalfLife = Float.parseFloat(parts[2]);
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*wetnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Wetness halflife: " + parts[1], new Object[0]);
                                    wetnessHalfLife = Float.parseFloat(parts[1]);
                                } else if (line.matches("/\\* DRYNESSHL:[0-9\\.]+ \\*/.*")) {
                                    parts = line.split("(:| )", 4);
                                    SMCLog.info("Dryness halflife: " + parts[2], new Object[0]);
                                    drynessHalfLife = Float.parseFloat(parts[2]);
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*drynessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Dryness halflife: " + parts[1], new Object[0]);
                                    drynessHalfLife = Float.parseFloat(parts[1]);
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*eyeBrightnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Eye brightness halflife: " + parts[1], new Object[0]);
                                    eyeBrightnessHalflife = Float.parseFloat(parts[1]);
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*centerDepthHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Center depth halflife: " + parts[1], new Object[0]);
                                    centerDepthSmoothHalflife = Float.parseFloat(parts[1]);
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*sunPathRotation[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Sun path rotation: " + parts[1], new Object[0]);
                                    sunPathRotation = Float.parseFloat(parts[1]);
                                } else if (line.matches("[ \t]*const[ \t]*float[ \t]*ambientOcclusionLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("AO Level: " + parts[1], new Object[0]);
                                    aoLevel = Float.parseFloat(parts[1]);
                                    blockAoLight = 1.0F - aoLevel;
                                } else if (line.matches("[ \t]*const[ \t]*int[ \t]*superSamplingLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    int ssaa = Integer.parseInt(parts[1]);
                                    if (ssaa > 1) {
                                        SMCLog.info("Super sampling level: " + ssaa + "x", new Object[0]);
                                        superSamplingLevel = ssaa;
                                    } else {
                                        superSamplingLevel = 1;
                                    }
                                } else if (line.matches("[ \t]*const[ \t]*int[ \t]*noiseTextureResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
                                    parts = line.split("(=[ \t]*|;)");
                                    SMCLog.info("Noise texture enabled", new Object[0]);
                                    SMCLog.info("Noise texture resolution: " + parts[1], new Object[0]);
                                    noiseTextureResolution = Integer.parseInt(parts[1]);
                                    noiseTextureEnabled = true;
                                } else {
                                    String name;
                                    Matcher m;
                                    if (line.matches("[ \t]*const[ \t]*int[ \t]*\\w+Format[ \t]*=[ \t]*[RGBA81632F]*[ \t]*;.*")) {
                                        m = gbufferFormatPattern.matcher(line);
                                        m.matches();
                                        name = m.group(1);
                                        String value = m.group(2);
                                        int bufferindex = getBufferIndexFromString(name);
                                        int format = getTextureFormatFromString(value);
                                        if (bufferindex >= 0 && format != 0) {
                                            gbuffersFormat[bufferindex] = format;
                                            System.out.format("%s format: %s\n", name, value);
                                        }
                                    } else if (line.matches("/\\* GAUX4FORMAT:RGBA32F \\*/.*")) {
                                        SMCLog.info("gaux4 format : RGB32AF", new Object[0]);
                                        gbuffersFormat[7] = 34836;
                                    } else if (line.matches("/\\* GAUX4FORMAT:RGB32F \\*/.*")) {
                                        SMCLog.info("gaux4 format : RGB32F", new Object[0]);
                                        gbuffersFormat[7] = 34837;
                                    } else if (line.matches("/\\* GAUX4FORMAT:RGB16 \\*/.*")) {
                                        SMCLog.info("gaux4 format : RGB16", new Object[0]);
                                        gbuffersFormat[7] = 32852;
                                    } else if (line.matches("[ \t]*const[ \t]*bool[ \t]*\\w+MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*")) {
                                        if (filename.matches(".*composite[0-9]?.fsh") || filename.matches(".*final.fsh")) {
                                            m = gbufferMipmapEnabledPattern.matcher(line);
                                            m.matches();
                                            name = m.group(1);
                                            int bufferindex = getBufferIndexFromString(name);
                                            if (bufferindex >= 0) {
                                                newCompositeMipmapSetting |= 1 << bufferindex;
                                                System.out.format("%s mipmap enabled for %s\n", name, filename);
                                            }
                                        }
                                    } else if (line.matches("/\\* DRAWBUFFERS:[0-7N]* \\*/.*")) {
                                        parts = line.split("(:| )", 4);
                                        newDrawBufSetting = parts[2];
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception var13) {
                    SMCLog.severe("Couldn't read " + filename + "!", new Object[0]);
                    var13.printStackTrace();
                    ARBShaderObjects.glDeleteObjectARB(fragShader);
                    return 0;
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception var10) {
                    SMCLog.severe("Couldn't close " + filename + "!", new Object[0]);
                }
            }

            ARBShaderObjects.glShaderSourceARB(fragShader, fragCode);
            ARBShaderObjects.glCompileShaderARB(fragShader);
            printLogInfo(fragShader, filename);
            return fragShader;
        }
    }

    private static boolean printLogInfo(int obj, String name) {
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(obj, 35716, iVal);
        int length = iVal.get();
        if (length > 1) {
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            ARBShaderObjects.glGetInfoLogARB(obj, iVal, infoLog);
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            if (infoBytes[length - 1] == 0) {
                infoBytes[length - 1] = 10;
            }

            String out = new String(infoBytes);
            SMCLog.info("Info log: " + name + "\n" + out, new Object[0]);
            return false;
        } else {
            return true;
        }
    }

    public static void setDrawBuffers(IntBuffer drawBuffers) {
        if (activeDrawBuffers != drawBuffers) {
            activeDrawBuffers = drawBuffers;
            GL20.glDrawBuffers(drawBuffers);
        }

    }

    public static void useProgram(int program) {
        checkGLError("pre-useProgram");
        if (isShadowPass) {
            program = 21;
            if (programsID[21] == 0) {
                normalMapEnabled = false;
                return;
            }
        }

        if (activeProgram != program) {
            activeProgram = program;
            ARBShaderObjects.glUseProgramObjectARB(programsID[program]);
            if (programsID[program] == 0) {
                normalMapEnabled = false;
            } else {
                if (checkGLError("useProgram ", programNames[program]) != 0) {
                    programsID[program] = 0;
                }

                IntBuffer drawBuffers = programsDrawBuffers[program];
                if (isRenderingDfb) {
                    setDrawBuffers(drawBuffers);
                    checkGLError(programNames[program], " draw buffers = ", programsDrawBufSettings[program]);
                }

                activeCompositeMipmapSetting = programsCompositeMipmapSetting[program];
                uniformEntityHurt = ARBShaderObjects.glGetUniformLocationARB(programsID[activeProgram], "entityHurt");
                uniformEntityFlash = ARBShaderObjects.glGetUniformLocationARB(programsID[activeProgram], "entityFlash");
                switch (program) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                    case 11:
                        normalMapEnabled = true;
                        setProgramUniform1i("texture", 0);
                        setProgramUniform1i("lightmap", 1);
                        setProgramUniform1i("normals", 2);
                        setProgramUniform1i("specular", 3);
                        setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
                        setProgramUniform1i("watershadow", 4);
                        setProgramUniform1i("shadowtex0", 4);
                        setProgramUniform1i("shadowtex1", 5);
                        setProgramUniform1i("depthtex0", 6);
                        setProgramUniform1i("depthtex1", 12);
                        setProgramUniform1i("shadowcolor", 13);
                        setProgramUniform1i("shadowcolor0", 13);
                        setProgramUniform1i("shadowcolor1", 14);
                        setProgramUniform1i("noisetex", 15);
                        break;
                    case 9:
                    default:
                        normalMapEnabled = false;
                        break;
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                        normalMapEnabled = false;
                        setProgramUniform1i("gcolor", 0);
                        setProgramUniform1i("gdepth", 1);
                        setProgramUniform1i("gnormal", 2);
                        setProgramUniform1i("composite", 3);
                        setProgramUniform1i("gaux1", 7);
                        setProgramUniform1i("gaux2", 8);
                        setProgramUniform1i("gaux3", 9);
                        setProgramUniform1i("gaux4", 10);
                        setProgramUniform1i("colortex0", 0);
                        setProgramUniform1i("colortex1", 1);
                        setProgramUniform1i("colortex2", 2);
                        setProgramUniform1i("colortex3", 3);
                        setProgramUniform1i("colortex4", 7);
                        setProgramUniform1i("colortex5", 8);
                        setProgramUniform1i("colortex6", 9);
                        setProgramUniform1i("colortex7", 10);
                        setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
                        setProgramUniform1i("watershadow", 4);
                        setProgramUniform1i("shadowtex0", 4);
                        setProgramUniform1i("shadowtex1", 5);
                        setProgramUniform1i("gdepthtex", 6);
                        setProgramUniform1i("depthtex0", 6);
                        setProgramUniform1i("depthtex1", 11);
                        setProgramUniform1i("depthtex2", 12);
                        setProgramUniform1i("shadowcolor", 13);
                        setProgramUniform1i("shadowcolor0", 13);
                        setProgramUniform1i("shadowcolor1", 14);
                        setProgramUniform1i("noisetex", 15);
                        break;
                    case 21:
                        setProgramUniform1i("tex", 0);
                        setProgramUniform1i("texture", 0);
                        setProgramUniform1i("lightmap", 1);
                        setProgramUniform1i("normals", 2);
                        setProgramUniform1i("specular", 3);
                        setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
                        setProgramUniform1i("watershadow", 4);
                        setProgramUniform1i("shadowtex0", 4);
                        setProgramUniform1i("shadowtex1", 5);
                        setProgramUniform1i("shadowcolor", 13);
                        setProgramUniform1i("shadowcolor0", 13);
                        setProgramUniform1i("shadowcolor1", 14);
                        setProgramUniform1i("noisetex", 15);
                }

                ItemStack stack = mc.field_71439_g.field_71071_by.func_70448_g();
                Item item = stack != null ? stack.func_77973_b() : null;
                int itemID;
                int blockID;
                if (item != null) {
                    itemID = item.field_77779_bT;
                    blockID = item instanceof ItemBlock ? ((ItemBlock)item).func_77883_f() : -1;
                } else {
                    itemID = -1;
                    blockID = -1;
                }

                setProgramUniform1i("heldItemId", itemID);
                setProgramUniform1i("heldBlockLightValue", blockID != -1 ? Block.field_71984_q[blockID] : 0);
                setProgramUniform1i("fogMode", fogEnabled ? fogMode : 0);
                setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
                setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
                setProgramUniform1i("worldTime", (int)worldTime % 24000);
                setProgramUniform1i("moonPhase", moonPhase);
                setProgramUniform1f("frameTimeCounter", frameTimeCounter);
                setProgramUniform1f("sunAngle", sunAngle);
                setProgramUniform1f("shadowAngle", shadowAngle);
                setProgramUniform1f("rainStrength", rainStrength);
                setProgramUniform1f("aspectRatio", (float)renderWidth / (float)renderHeight);
                setProgramUniform1f("viewWidth", (float)renderWidth);
                setProgramUniform1f("viewHeight", (float)renderHeight);
                setProgramUniform1f("near", 0.05F);
                setProgramUniform1f("far", (float)(256 >> mc.field_71474_y.field_74339_e));
                setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
                setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
                setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
                setProgramUniform3f("previousCameraPosition", (float)previousCameraPosition[0], (float)previousCameraPosition[1], (float)previousCameraPosition[2]);
                setProgramUniform3f("cameraPosition", (float)cameraPosition[0], (float)cameraPosition[1], (float)cameraPosition[2]);
                setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
                setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
                setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
                setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
                setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
                setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
                if (usedShadowDepthBuffers > 0) {
                    setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
                    setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
                    setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
                    setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
                }

                setProgramUniform1f("wetness", wetness);
                setProgramUniform1f("eyeAltitude", eyePosY);
                setProgramUniform2i("eyeBrightness", eyeBrightness & '\uffff', eyeBrightness >> 16);
                setProgramUniform2i("eyeBrightnessSmooth", Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
                setProgramUniform2i("terrainTextureSize", terrainTextureSize[0], terrainTextureSize[1]);
                setProgramUniform1i("terrainIconSize", terrainIconSize);
                setProgramUniform1i("isEyeInWater", isEyeInWater);
                setProgramUniform1i("hideGUI", mc.field_71474_y.field_74319_N ? 1 : 0);
                setProgramUniform1f("centerDepthSmooth", centerDepthSmooth);
                setProgramUniform2i("atlasSize", atlasSizeX, atlasSizeY);
                checkGLError("useProgram ", programNames[program]);
            }
        }
    }

    public static void setProgramUniform1i(String name, int x) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
            ARBShaderObjects.glUniform1iARB(uniform, x);
            checkGLError(programNames[activeProgram], name);
        }

    }

    public static void setProgramUniform2i(String name, int x, int y) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
            ARBShaderObjects.glUniform2iARB(uniform, x, y);
            checkGLError(programNames[activeProgram], name);
        }

    }

    public static void setProgramUniform1f(String name, float x) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
            ARBShaderObjects.glUniform1fARB(uniform, x);
            checkGLError(programNames[activeProgram], name);
        }

    }

    public static void setProgramUniform3f(String name, float x, float y, float z) {
        int gp = programsID[activeProgram];
        if (gp != 0) {
            int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
            ARBShaderObjects.glUniform3fARB(uniform, x, y, z);
            checkGLError(programNames[activeProgram], name);
        }

    }

    public static void setProgramUniformMatrix4ARB(String name, boolean transpose, FloatBuffer matrix) {
        int gp = programsID[activeProgram];
        if (gp != 0 && matrix != null) {
            int uniform = ARBShaderObjects.glGetUniformLocationARB(gp, name);
            ARBShaderObjects.glUniformMatrix4ARB(uniform, transpose, matrix);
            checkGLError(programNames[activeProgram], name);
        }

    }

    private static int getBufferIndexFromString(String name) {
        if (!name.equals("colortex0") && !name.equals("gcolor")) {
            if (!name.equals("colortex1") && !name.equals("gdepth")) {
                if (!name.equals("colortex2") && !name.equals("gnormal")) {
                    if (!name.equals("colortex3") && !name.equals("composite")) {
                        if (!name.equals("colortex4") && !name.equals("gaux1")) {
                            if (!name.equals("colortex5") && !name.equals("gaux2")) {
                                if (!name.equals("colortex6") && !name.equals("gaux3")) {
                                    return !name.equals("colortex7") && !name.equals("gaux4") ? -1 : 7;
                                } else {
                                    return 6;
                                }
                            } else {
                                return 5;
                            }
                        } else {
                            return 4;
                        }
                    } else {
                        return 3;
                    }
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    private static int getTextureFormatFromString(String par) {
        if (par.matches("[ \t]*R8[ \t]*")) {
            return 33321;
        } else if (par.matches("[ \t]*RG8[ \t]*")) {
            return 33323;
        } else if (par.matches("[ \t]*RGB8[ \t]*")) {
            return 32849;
        } else if (par.matches("[ \t]*RGBA8[ \t]*")) {
            return 32856;
        } else if (par.matches("[ \t]*R16[ \t]*")) {
            return 33322;
        } else if (par.matches("[ \t]*RG16[ \t]*")) {
            return 33324;
        } else if (par.matches("[ \t]*RGB16[ \t]*")) {
            return 32852;
        } else if (par.matches("[ \t]*RGBA16[ \t]*")) {
            return 32859;
        } else if (par.matches("[ \t]*R32F[ \t]*")) {
            return 33326;
        } else if (par.matches("[ \t]*RG32F[ \t]*")) {
            return 33328;
        } else if (par.matches("[ \t]*RGB32F[ \t]*")) {
            return 34837;
        } else {
            return par.matches("[ \t]*RGBA32F[ \t]*") ? '蠔' : 0;
        }
    }

    private static void setupNoiseTexture() {
        if (noiseTexture == null) {
            noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
        }

    }

    private static IntBuffer fillIntBufferZero(IntBuffer buf) {
        int limit = buf.limit();

        for(int i = buf.position(); i < limit; ++i) {
            buf.put(i, 0);
        }

        return buf;
    }

    public static void uninit() {
        if (isInitialized) {
            checkGLError("Shaders.uninit pre");

            for(int i = 0; i < 22; ++i) {
                if (programsRef[i] != 0) {
                    ARBShaderObjects.glDeleteObjectARB(programsRef[i]);
                    checkGLError("del programRef");
                }

                programsRef[i] = 0;
                programsID[i] = 0;
                programsDrawBufSettings[i] = null;
                programsDrawBuffers[i] = null;
                programsCompositeMipmapSetting[i] = 0;
            }

            if (dfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
                dfb = 0;
                checkGLError("del dfb");
            }

            if (sfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
                sfb = 0;
                checkGLError("del sfb");
            }

            if (dfbDepthTextures != null) {
                GL11.glDeleteTextures(dfbDepthTextures);
                fillIntBufferZero(dfbDepthTextures);
                checkGLError("del dfbDepthTextures");
            }

            if (dfbColorTextures != null) {
                GL11.glDeleteTextures(dfbColorTextures);
                fillIntBufferZero(dfbColorTextures);
                checkGLError("del dfbTextures");
            }

            if (sfbDepthTextures != null) {
                GL11.glDeleteTextures(sfbDepthTextures);
                fillIntBufferZero(sfbDepthTextures);
                checkGLError("del shadow depth");
            }

            if (sfbColorTextures != null) {
                GL11.glDeleteTextures(sfbColorTextures);
                fillIntBufferZero(sfbColorTextures);
                checkGLError("del shadow color");
            }

            if (dfbDrawBuffers != null) {
                fillIntBufferZero(dfbDrawBuffers);
            }

            if (noiseTexture != null) {
                noiseTexture.destroy();
                noiseTexture = null;
            }

            SMCLog.info("Uninit", new Object[0]);
            shadowPassInterval = 0;
            shouldSkipDefaultShadow = false;
            isInitialized = false;
            notFirstInit = true;
            checkGLError("Shaders.uninit");
        }

    }

    public static void scheduleResize() {
        renderDisplayHeight = 0;
    }

    public static void scheduleResizeShadow() {
        needResizeShadow = true;
    }

    private static void resize() {
        renderDisplayWidth = mc.field_71443_c;
        renderDisplayHeight = mc.field_71440_d;
        renderWidth = Math.round((float)renderDisplayWidth * configRenderResMul);
        renderHeight = Math.round((float)renderDisplayHeight * configRenderResMul);
        setupFrameBuffer();
    }

    private static void resizeShadow() {
        needResizeShadow = false;
        shadowMapWidth = Math.round((float)spShadowMapWidth * configShadowResMul);
        shadowMapHeight = Math.round((float)spShadowMapHeight * configShadowResMul);
        setupShadowFrameBuffer();
    }

    private static void setupFrameBuffer() {
        if (dfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
            GL11.glDeleteTextures(dfbDepthTextures);
            GL11.glDeleteTextures(dfbColorTextures);
        }

        dfb = EXTFramebufferObject.glGenFramebuffersEXT();
        GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
        GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(usedColorBuffers));
        dfbDepthTextures.position(0);
        dfbColorTextures.position(0);
        EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
        GL20.glDrawBuffers(0);
        GL11.glReadBuffer(0);

        int status;
        for(status = 0; status < usedDepthBuffers; ++status) {
            GL11.glBindTexture(3553, dfbDepthTextures.get(status));
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
            GL11.glTexParameteri(3553, 34891, 6409);
            GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, (ByteBuffer)null);
        }

        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
        GL20.glDrawBuffers(dfbDrawBuffers);
        GL11.glReadBuffer(0);
        checkGLError("FT d");

        for(status = 0; status < usedColorBuffers; ++status) {
            GL11.glBindTexture(3553, dfbColorTextures.get(status));
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(3553, 0, gbuffersFormat[status], renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, '賠' + status, 3553, dfbColorTextures.get(status), 0);
            checkGLError("FT c");
        }

        status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        if (status == 36058) {
            printChatAndLogError("Failed using multiple internal formats in frame buffer.");

            for(int i = 0; i < usedColorBuffers; ++i) {
                GL11.glBindTexture(3553, dfbColorTextures.get(i));
                GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, '賠' + i, 3553, dfbColorTextures.get(i), 0);
                checkGLError("FT c");
            }

            status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            if (status == 36053) {
                printChatAndLogError("Please update graphics driver.");
            }
        }

        GL11.glBindTexture(3553, 0);
        if (status != 36053) {
            printChatAndLogError("Failed creating framebuffer! (Status " + status + ")");
        } else {
            SMCLog.info("Framebuffer created.", new Object[0]);
        }

    }

    private static void setupShadowFrameBuffer() {
        if (usedShadowDepthBuffers != 0) {
            if (sfb != 0) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
                GL11.glDeleteTextures(sfbDepthTextures);
                GL11.glDeleteTextures(sfbColorTextures);
            }

            sfb = EXTFramebufferObject.glGenFramebuffersEXT();
            EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
            GL20.glDrawBuffers(0);
            GL11.glReadBuffer(0);
            GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
            GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
            sfbDepthTextures.position(0);
            sfbColorTextures.position(0);

            int status;
            int filter;
            for(status = 0; status < usedShadowDepthBuffers; ++status) {
                GL11.glBindTexture(3553, sfbDepthTextures.get(status));
                GL11.glTexParameterf(3553, 10242, 10496.0F);
                GL11.glTexParameterf(3553, 10243, 10496.0F);
                filter = shadowFilterNearest[status] ? 9728 : 9729;
                GL11.glTexParameteri(3553, 10241, filter);
                GL11.glTexParameteri(3553, 10240, filter);
                if (shadowHardwareFilteringEnabled[status]) {
                    GL11.glTexParameteri(3553, 34892, 34894);
                }

                GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, (FloatBuffer)null);
            }

            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
            checkGLError("FT sd");

            for(status = 0; status < usedShadowColorBuffers; ++status) {
                GL11.glBindTexture(3553, sfbColorTextures.get(status));
                GL11.glTexParameterf(3553, 10242, 10496.0F);
                GL11.glTexParameterf(3553, 10243, 10496.0F);
                filter = shadowColorFilterNearest[status] ? 9728 : 9729;
                GL11.glTexParameteri(3553, 10241, filter);
                GL11.glTexParameteri(3553, 10240, filter);
                GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, (ByteBuffer)null);
                EXTFramebufferObject.glFramebufferTexture2DEXT(36160, '賠' + status, 3553, sfbColorTextures.get(status), 0);
                checkGLError("FT sc");
            }

            GL11.glBindTexture(3553, 0);
            status = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
            if (status != 36053) {
                printChatAndLogError("Failed creating shadow framebuffer! (Status " + status + ")");
            } else {
                SMCLog.info("Shadow framebuffer created.", new Object[0]);
            }

        }
    }

    public static void beginRender(Minecraft minecraft, float f, long l) {
        if (!isShadowPass) {
            checkGLError("pre beginRender");
            mc = minecraft;
            mc.field_71424_I.func_76320_a("init");
            if (!isInitialized) {
                init();
            }

            if (mc.field_71443_c != renderDisplayWidth || mc.field_71440_d != renderDisplayHeight) {
                resize();
            }

            if (needResizeShadow) {
                resizeShadow();
            }

            worldTime = mc.field_71441_e.func_72820_D();
            diffWorldTime = (worldTime - lastWorldTime) % 24000L;
            if (diffWorldTime < 0L) {
                diffWorldTime += 24000L;
            }

            lastWorldTime = worldTime;
            moonPhase = mc.field_71441_e.func_72853_d();
            systemTime = System.currentTimeMillis();
            if (lastSystemTime == 0L) {
                lastSystemTime = systemTime;
            }

            diffSystemTime = systemTime - lastSystemTime;
            lastSystemTime = systemTime;
            frameTimeCounter += (float)diffSystemTime * 0.001F;
            frameTimeCounter %= 100000.0F;
            rainStrength = minecraft.field_71441_e.func_72867_j(f);
            float fadeScalar = (float)diffSystemTime * 0.01F;
            float temp1 = (float)Math.exp(Math.log(0.5) * (double)fadeScalar / (double)(wetness < rainStrength ? drynessHalfLife : wetnessHalfLife));
            wetness = wetness * temp1 + rainStrength * (1.0F - temp1);
            EntityLivingBase eye = mc.field_71451_h;
            eyePosY = (float)eye.field_70163_u * f + (float)eye.field_70137_T * (1.0F - f);
            eyeBrightness = eye.func_70070_b(f);
            temp1 = (float)diffSystemTime * 0.01F;
            float temp2 = (float)Math.exp(Math.log(0.5) * (double)temp1 / (double)eyeBrightnessHalflife);
            eyeBrightnessFadeX = eyeBrightnessFadeX * temp2 + (float)(eyeBrightness & '\uffff') * (1.0F - temp2);
            eyeBrightnessFadeY = eyeBrightnessFadeY * temp2 + (float)(eyeBrightness >> 16) * (1.0F - temp2);
            isEyeInWater = mc.field_71474_y.field_74320_O == 0 && !mc.field_71451_h.func_70608_bn() && mc.field_71439_g.func_70055_a(Material.field_76244_g) ? 1 : 0;
            Vec3 skyColorV = mc.field_71441_e.func_72833_a(mc.field_71451_h, f);
            skyColorR = (float)skyColorV.field_72450_a;
            skyColorG = (float)skyColorV.field_72448_b;
            skyColorB = (float)skyColorV.field_72449_c;
            isRenderingWorld = true;
            isCompositeRendered = false;
            isHandRendered = false;
            if (usedShadowDepthBuffers >= 1) {
                GL13.glActiveTexture(33988);
                GL11.glBindTexture(3553, sfbDepthTextures.get(0));
                if (usedShadowDepthBuffers >= 2) {
                    GL13.glActiveTexture(33989);
                    GL11.glBindTexture(3553, sfbDepthTextures.get(1));
                }
            }

            int i;
            for(i = 0; i < 4 && 4 + i < usedColorBuffers; ++i) {
                GL13.glActiveTexture('蓇' + i);
                GL11.glBindTexture(3553, dfbColorTextures.get(4 + i));
            }

            GL13.glActiveTexture(33990);
            GL11.glBindTexture(3553, dfbDepthTextures.get(0));
            if (usedDepthBuffers >= 2) {
                GL13.glActiveTexture(33995);
                GL11.glBindTexture(3553, dfbDepthTextures.get(1));
                if (usedDepthBuffers >= 3) {
                    GL13.glActiveTexture(33996);
                    GL11.glBindTexture(3553, dfbDepthTextures.get(2));
                }
            }

            for(i = 0; i < usedShadowColorBuffers; ++i) {
                GL13.glActiveTexture('蓍' + i);
                GL11.glBindTexture(3553, sfbColorTextures.get(i));
            }

            if (noiseTextureEnabled) {
                GL13.glActiveTexture('蓀' + noiseTexture.textureUnit);
                GL11.glBindTexture(3553, noiseTexture.getID());
                GL11.glTexParameteri(3553, 10242, 10497);
                GL11.glTexParameteri(3553, 10243, 10497);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10241, 9729);
            }

            GL13.glActiveTexture(33984);
            previousCameraPosition[0] = cameraPosition[0];
            previousCameraPosition[1] = cameraPosition[1];
            previousCameraPosition[2] = cameraPosition[2];
            previousProjection.position(0);
            projection.position(0);
            previousProjection.put(projection);
            previousProjection.position(0);
            projection.position(0);
            previousModelView.position(0);
            modelView.position(0);
            previousModelView.put(modelView);
            previousModelView.position(0);
            modelView.position(0);
            EntityRenderer.field_78515_b = 0;
            if (usedShadowDepthBuffers > 0 && --shadowPassCounter <= 0) {
                mc.field_71424_I.func_76318_c("shadow pass");
                preShadowPassThirdPersonView = mc.field_71474_y.field_74320_O;
                boolean preShadowPassAdvancedOpengl = mc.field_71474_y.field_74349_h;
                mc.field_71474_y.field_74349_h = false;
                isShadowPass = true;
                shadowPassCounter = shadowPassInterval;
                EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
                GL20.glDrawBuffers(programsDrawBuffers[21]);
                useProgram(21);
                mc.field_71460_t.func_78471_a(f, l);
                isShadowPass = false;
                mc.field_71474_y.field_74349_h = preShadowPassAdvancedOpengl;
                mc.field_71474_y.field_74320_O = preShadowPassThirdPersonView;
                if (hasGlGenMipmap) {
                    if (usedShadowDepthBuffers >= 1) {
                        if (shadowMipmapEnabled[0]) {
                            GL13.glActiveTexture(33988);
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, shadowFilterNearest[0] ? 9984 : 9987);
                        }

                        if (usedShadowDepthBuffers >= 2 && shadowMipmapEnabled[1]) {
                            GL13.glActiveTexture(33989);
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, shadowFilterNearest[1] ? 9984 : 9987);
                        }

                        GL13.glActiveTexture(33984);
                    }

                    if (usedShadowColorBuffers >= 1) {
                        if (shadowColorMipmapEnabled[0]) {
                            GL13.glActiveTexture(33997);
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, shadowColorFilterNearest[0] ? 9984 : 9987);
                        }

                        if (usedShadowColorBuffers >= 2 && shadowColorMipmapEnabled[1]) {
                            GL13.glActiveTexture(33998);
                            GL30.glGenerateMipmap(3553);
                            GL11.glTexParameteri(3553, 10241, shadowColorFilterNearest[1] ? 9984 : 9987);
                        }

                        GL13.glActiveTexture(33984);
                    }
                }
            }

            mc.field_71424_I.func_76319_b();
            EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
            GL11.glViewport(0, 0, renderWidth, renderHeight);
            activeDrawBuffers = null;
            ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
            useProgram(2);
            checkGLError("end beginRender");
        }
    }

    public static void setViewport(int vx, int vy, int vw, int vh) {
        GL11.glColorMask(true, true, true, true);
        if (isShadowPass) {
            GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
        } else {
            GL11.glViewport(0, 0, renderWidth, renderHeight);
            EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
            isRenderingDfb = true;
            GL11.glEnable(2884);
            GL11.glEnable(2929);
            setDrawBuffers(drawBuffersNone);
            useProgram(2);
            checkGLError("beginRenderPass");
        }

    }

    public static int setFogMode(int val) {
        fogMode = val;
        return val;
    }

    public static void setFogColor(float r, float g, float b) {
        fogColorR = r;
        fogColorG = g;
        fogColorB = b;
    }

    public static void setClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
        clearColorR = red;
        clearColorG = green;
        clearColorB = blue;
    }

    public static void clearRenderBuffer() {
        if (isShadowPass) {
            checkGLError("shadow clear pre");
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
            GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            GL20.glDrawBuffers(programsDrawBuffers[21]);
            checkFramebufferStatus("shadow clear");
            GL11.glClear(16640);
            checkGLError("shadow clear");
        } else {
            checkGLError("clear pre");
            GL20.glDrawBuffers(36064);
            GL11.glClear(16384);
            GL20.glDrawBuffers(36065);
            GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glClear(16384);

            for(int i = 2; i < usedColorBuffers; ++i) {
                GL20.glDrawBuffers('賠' + i);
                GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                GL11.glClear(16384);
            }

            setDrawBuffers(dfbDrawBuffers);
            checkFramebufferStatus("clear");
            checkGLError("clear");
        }
    }

    public static void setCamera(float f) {
        EntityLivingBase viewEntity = mc.field_71451_h;
        double x = viewEntity.field_70142_S + (viewEntity.field_70165_t - viewEntity.field_70142_S) * (double)f;
        double y = viewEntity.field_70137_T + (viewEntity.field_70163_u - viewEntity.field_70137_T) * (double)f;
        double z = viewEntity.field_70136_U + (viewEntity.field_70161_v - viewEntity.field_70136_U) * (double)f;
        cameraPosition[0] = x;
        cameraPosition[1] = y;
        cameraPosition[2] = z;
        GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
        invertMat4x((FloatBuffer)projection.position(0), (FloatBuffer)projectionInverse.position(0));
        projection.position(0);
        projectionInverse.position(0);
        GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
        invertMat4x((FloatBuffer)modelView.position(0), (FloatBuffer)modelViewInverse.position(0));
        modelView.position(0);
        modelViewInverse.position(0);
        if (isShadowPass) {
            GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            if (shadowMapIsOrtho) {
                GL11.glOrtho((double)(-shadowMapHalfPlane), (double)shadowMapHalfPlane, (double)(-shadowMapHalfPlane), (double)shadowMapHalfPlane, 0.05000000074505806, 256.0);
            } else {
                GLU.gluPerspective(shadowMapFOV, (float)shadowMapWidth / (float)shadowMapHeight, 0.05F, 256.0F);
            }

            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -100.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            float celestialAngle = mc.field_71441_e.func_72826_c(f);
            sunAngle = celestialAngle < 0.75F ? celestialAngle + 0.25F : celestialAngle - 0.75F;
            float angle = celestialAngle * -360.0F;
            float angleInterval = shadowAngleInterval > 0.0F ? angle % shadowAngleInterval - shadowAngleInterval * 0.5F : 0.0F;
            if ((double)sunAngle <= 0.5) {
                GL11.glRotatef(angle - angleInterval, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
                shadowAngle = sunAngle;
            } else {
                GL11.glRotatef(angle + 180.0F - angleInterval, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
                shadowAngle = sunAngle - 0.5F;
            }

            if (shadowMapIsOrtho) {
                float trans = shadowIntervalSize;
                float trans2 = trans / 2.0F;
                GL11.glTranslatef((float)x % trans - trans2, (float)y % trans - trans2, (float)z % trans - trans2);
            }

            GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
            invertMat4x((FloatBuffer)shadowProjection.position(0), (FloatBuffer)shadowProjectionInverse.position(0));
            shadowProjection.position(0);
            shadowProjectionInverse.position(0);
            GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
            invertMat4x((FloatBuffer)shadowModelView.position(0), (FloatBuffer)shadowModelViewInverse.position(0));
            shadowModelView.position(0);
            shadowModelViewInverse.position(0);
            setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
            setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
            setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
            setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
            setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
            setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
            setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
            setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
            setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
            setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
            mc.field_71474_y.field_74320_O = 1;
            checkGLError("setCamera");
        } else {
            checkGLError("setCamera");
        }
    }

    public static void preCelestialRotate() {
        setUpPosition();
        GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
        checkGLError("preCelestialRotate");
    }

    public static void postCelestialRotate() {
        FloatBuffer modelView = tempMatrixDirectBuffer;
        modelView.clear();
        GL11.glGetFloat(2982, modelView);
        modelView.get(tempMat, 0, 16);
        multiplyMat4xVec4(tempMat, sunPosModelView, sunPosition);
        multiplyMat4xVec4(tempMat, moonPosModelView, moonPosition);
        checkGLError("postCelestialRotate");
    }

    public static void setUpPosition() {
        FloatBuffer modelView = tempMatrixDirectBuffer;
        modelView.clear();
        GL11.glGetFloat(2982, modelView);
        modelView.get(tempMat, 0, 16);
        multiplyMat4xVec4(tempMat, upPosModelView, upPosition);
    }

    private static float[] multiplyMat4xVec4(float[] matA, float[] vecB, float[] vecOut) {
        vecOut[0] = matA[0] * vecB[0] + matA[4] * vecB[1] + matA[8] * vecB[2] + matA[12] * vecB[3];
        vecOut[1] = matA[1] * vecB[0] + matA[5] * vecB[1] + matA[9] * vecB[2] + matA[13] * vecB[3];
        vecOut[2] = matA[2] * vecB[0] + matA[6] * vecB[1] + matA[10] * vecB[2] + matA[14] * vecB[3];
        vecOut[3] = matA[3] * vecB[0] + matA[7] * vecB[1] + matA[11] * vecB[2] + matA[15] * vecB[3];
        return vecOut;
    }

    private static FloatBuffer invertMat4x(FloatBuffer matIn, FloatBuffer invMatOut) {
        float[] m = invertMat4x_m;
        float[] inv = invertMat4x_inv;
        matIn.get(m);
        inv[0] = m[5] * m[10] * m[15] - m[5] * m[11] * m[14] - m[9] * m[6] * m[15] + m[9] * m[7] * m[14] + m[13] * m[6] * m[11] - m[13] * m[7] * m[10];
        inv[4] = -m[4] * m[10] * m[15] + m[4] * m[11] * m[14] + m[8] * m[6] * m[15] - m[8] * m[7] * m[14] - m[12] * m[6] * m[11] + m[12] * m[7] * m[10];
        inv[8] = m[4] * m[9] * m[15] - m[4] * m[11] * m[13] - m[8] * m[5] * m[15] + m[8] * m[7] * m[13] + m[12] * m[5] * m[11] - m[12] * m[7] * m[9];
        inv[12] = -m[4] * m[9] * m[14] + m[4] * m[10] * m[13] + m[8] * m[5] * m[14] - m[8] * m[6] * m[13] - m[12] * m[5] * m[10] + m[12] * m[6] * m[9];
        inv[1] = -m[1] * m[10] * m[15] + m[1] * m[11] * m[14] + m[9] * m[2] * m[15] - m[9] * m[3] * m[14] - m[13] * m[2] * m[11] + m[13] * m[3] * m[10];
        inv[5] = m[0] * m[10] * m[15] - m[0] * m[11] * m[14] - m[8] * m[2] * m[15] + m[8] * m[3] * m[14] + m[12] * m[2] * m[11] - m[12] * m[3] * m[10];
        inv[9] = -m[0] * m[9] * m[15] + m[0] * m[11] * m[13] + m[8] * m[1] * m[15] - m[8] * m[3] * m[13] - m[12] * m[1] * m[11] + m[12] * m[3] * m[9];
        inv[13] = m[0] * m[9] * m[14] - m[0] * m[10] * m[13] - m[8] * m[1] * m[14] + m[8] * m[2] * m[13] + m[12] * m[1] * m[10] - m[12] * m[2] * m[9];
        inv[2] = m[1] * m[6] * m[15] - m[1] * m[7] * m[14] - m[5] * m[2] * m[15] + m[5] * m[3] * m[14] + m[13] * m[2] * m[7] - m[13] * m[3] * m[6];
        inv[6] = -m[0] * m[6] * m[15] + m[0] * m[7] * m[14] + m[4] * m[2] * m[15] - m[4] * m[3] * m[14] - m[12] * m[2] * m[7] + m[12] * m[3] * m[6];
        inv[10] = m[0] * m[5] * m[15] - m[0] * m[7] * m[13] - m[4] * m[1] * m[15] + m[4] * m[3] * m[13] + m[12] * m[1] * m[7] - m[12] * m[3] * m[5];
        inv[14] = -m[0] * m[5] * m[14] + m[0] * m[6] * m[13] + m[4] * m[1] * m[14] - m[4] * m[2] * m[13] - m[12] * m[1] * m[6] + m[12] * m[2] * m[5];
        inv[3] = -m[1] * m[6] * m[11] + m[1] * m[7] * m[10] + m[5] * m[2] * m[11] - m[5] * m[3] * m[10] - m[9] * m[2] * m[7] + m[9] * m[3] * m[6];
        inv[7] = m[0] * m[6] * m[11] - m[0] * m[7] * m[10] - m[4] * m[2] * m[11] + m[4] * m[3] * m[10] + m[8] * m[2] * m[7] - m[8] * m[3] * m[6];
        inv[11] = -m[0] * m[5] * m[11] + m[0] * m[7] * m[9] + m[4] * m[1] * m[11] - m[4] * m[3] * m[9] - m[8] * m[1] * m[7] + m[8] * m[3] * m[5];
        inv[15] = m[0] * m[5] * m[10] - m[0] * m[6] * m[9] - m[4] * m[1] * m[10] + m[4] * m[2] * m[9] + m[8] * m[1] * m[6] - m[8] * m[2] * m[5];
        float det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];
        if ((double)det != 0.0) {
            for(int i = 0; i < 16; ++i) {
                inv[i] /= det;
            }
        } else {
            Arrays.fill(inv, 0.0F);
        }

        invMatOut.put(inv);
        return invMatOut;
    }

    public static void genCompositeMipmap() {
        if (hasGlGenMipmap) {
            if ((activeCompositeMipmapSetting & 1) != 0) {
                GL13.glActiveTexture(33984);
                GL11.glTexParameteri(3553, 10241, 9987);
                GL30.glGenerateMipmap(3553);
            }

            if ((activeCompositeMipmapSetting & 2) != 0) {
                GL13.glActiveTexture(33985);
                GL11.glTexParameteri(3553, 10241, 9987);
                GL30.glGenerateMipmap(3553);
            }

            if ((activeCompositeMipmapSetting & 4) != 0) {
                GL13.glActiveTexture(33986);
                GL11.glTexParameteri(3553, 10241, 9987);
                GL30.glGenerateMipmap(3553);
            }

            if ((activeCompositeMipmapSetting & 8) != 0) {
                GL13.glActiveTexture(33987);
                GL11.glTexParameteri(3553, 10241, 9987);
                GL30.glGenerateMipmap(3553);
            }

            for(int i = 0; i < 4 && 4 + i < usedColorBuffers; ++i) {
                if ((activeCompositeMipmapSetting & 16 << i) != 0) {
                    GL13.glActiveTexture('蓇' + i);
                    GL11.glTexParameteri(3553, 10241, 9987);
                    GL30.glGenerateMipmap(3553);
                }
            }

            GL13.glActiveTexture(33984);
        }

    }

    public static void drawComposite() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex3f(0.0F, 0.0F, 0.0F);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex3f(1.0F, 0.0F, 0.0F);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex3f(1.0F, 1.0F, 0.0F);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex3f(0.0F, 1.0F, 0.0F);
        GL11.glEnd();
    }

    public static void renderCompositeFinal() {
        if (!isShadowPass) {
            checkGLError("pre-renderCompositeFinal");
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3553);
            GL11.glDisable(3008);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthFunc(519);
            GL11.glDepthMask(false);
            GL11.glDisable(2896);
            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, dfbColorTextures.get(0));
            GL13.glActiveTexture(33985);
            GL11.glBindTexture(3553, dfbColorTextures.get(1));
            GL13.glActiveTexture(33986);
            GL11.glBindTexture(3553, dfbColorTextures.get(2));
            GL13.glActiveTexture(33987);
            GL11.glBindTexture(3553, dfbColorTextures.get(3));
            if (usedShadowDepthBuffers >= 1) {
                GL13.glActiveTexture(33988);
                GL11.glBindTexture(3553, sfbDepthTextures.get(0));
                if (usedShadowDepthBuffers >= 2) {
                    GL13.glActiveTexture(33989);
                    GL11.glBindTexture(3553, sfbDepthTextures.get(1));
                }
            }

            int i;
            for(i = 0; i < 4 && 4 + i < usedColorBuffers; ++i) {
                GL13.glActiveTexture('蓇' + i);
                GL11.glBindTexture(3553, dfbColorTextures.get(4 + i));
            }

            GL13.glActiveTexture(33990);
            GL11.glBindTexture(3553, dfbDepthTextures.get(0));
            if (usedDepthBuffers >= 2) {
                GL13.glActiveTexture(33995);
                GL11.glBindTexture(3553, dfbDepthTextures.get(1));
                if (usedDepthBuffers >= 3) {
                    GL13.glActiveTexture(33996);
                    GL11.glBindTexture(3553, dfbDepthTextures.get(2));
                }
            }

            for(i = 0; i < usedShadowColorBuffers; ++i) {
                GL13.glActiveTexture('蓍' + i);
                GL11.glBindTexture(3553, sfbColorTextures.get(i));
            }

            if (noiseTextureEnabled) {
                GL13.glActiveTexture('蓀' + noiseTexture.textureUnit);
                GL11.glBindTexture(3553, noiseTexture.getID());
                GL11.glTexParameteri(3553, 10242, 10497);
                GL11.glTexParameteri(3553, 10243, 10497);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10241, 9729);
            }

            GL13.glActiveTexture(33984);
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
            GL20.glDrawBuffers(dfbDrawBuffers);
            checkGLError("pre-composite");

            for(i = 0; i < 8; ++i) {
                if (programsID[12 + i] != 0) {
                    useProgram(12 + i);
                    checkGLError(programNames[12 + i]);
                    if (activeCompositeMipmapSetting != 0) {
                        genCompositeMipmap();
                    }

                    drawComposite();
                }
            }

            isRenderingDfb = false;
            EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
            GL11.glViewport(0, 0, mc.field_71443_c, mc.field_71440_d);
            if (EntityRenderer.field_78517_a) {
                boolean maskR = EntityRenderer.field_78515_b != 0;
                GL11.glColorMask(maskR, !maskR, !maskR, true);
            }

            GL11.glDepthMask(true);
            GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
            GL11.glClear(16640);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3553);
            GL11.glDisable(3008);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthFunc(519);
            GL11.glDepthMask(false);
            checkGLError("pre-final");
            useProgram(20);
            checkGLError("final");
            if (activeCompositeMipmapSetting != 0) {
                genCompositeMipmap();
            }

            drawComposite();
            checkGLError("renderCompositeFinal");
            isCompositeRendered = true;
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glEnable(3042);
            GL11.glDepthFunc(515);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            useProgram(0);
            RenderHelper.func_74519_b();
        }
    }

    public static void endRender() {
        if (isShadowPass) {
            checkGLError("shadow endRender");
        } else {
            if (!isCompositeRendered) {
                renderCompositeFinal();
            }

            isRenderingWorld = false;
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(3042);
            useProgram(0);
            checkGLError("endRender end");
        }
    }

    public static void beginSky() {
        isRenderingSky = true;
        fogEnabled = true;
        setDrawBuffers(dfbDrawBuffers);
        useProgram(5);
        pushEntity(-2, 0);
    }

    public static void setSkyColor(Vec3 v3color) {
        skyColorR = (float)v3color.field_72450_a;
        skyColorG = (float)v3color.field_72448_b;
        skyColorB = (float)v3color.field_72449_c;
        setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
    }

    public static void drawHorizon() {
        Tessellator tess = Tessellator.field_78398_a;
        float farDistance = (float)(256 >> mc.field_71474_y.field_74339_e);
        double xzq = (double)farDistance * 0.9238;
        double xzp = (double)farDistance * 0.3826;
        double xzn = -xzp;
        double xzm = -xzq;
        double top = 16.0;
        double bot = -cameraPosition[1];
        tess.func_78382_b();
        tess.func_78377_a(xzn, bot, xzm);
        tess.func_78377_a(xzn, top, xzm);
        tess.func_78377_a(xzm, top, xzn);
        tess.func_78377_a(xzm, bot, xzn);
        tess.func_78377_a(xzm, bot, xzn);
        tess.func_78377_a(xzm, top, xzn);
        tess.func_78377_a(xzm, top, xzp);
        tess.func_78377_a(xzm, bot, xzp);
        tess.func_78377_a(xzm, bot, xzp);
        tess.func_78377_a(xzm, top, xzp);
        tess.func_78377_a(xzn, top, xzp);
        tess.func_78377_a(xzn, bot, xzp);
        tess.func_78377_a(xzn, bot, xzp);
        tess.func_78377_a(xzn, top, xzp);
        tess.func_78377_a(xzp, top, xzq);
        tess.func_78377_a(xzp, bot, xzq);
        tess.func_78377_a(xzp, bot, xzq);
        tess.func_78377_a(xzp, top, xzq);
        tess.func_78377_a(xzq, top, xzp);
        tess.func_78377_a(xzq, bot, xzp);
        tess.func_78377_a(xzq, bot, xzp);
        tess.func_78377_a(xzq, top, xzp);
        tess.func_78377_a(xzq, top, xzn);
        tess.func_78377_a(xzq, bot, xzn);
        tess.func_78377_a(xzq, bot, xzn);
        tess.func_78377_a(xzq, top, xzn);
        tess.func_78377_a(xzp, top, xzm);
        tess.func_78377_a(xzp, bot, xzm);
        tess.func_78377_a(xzp, bot, xzm);
        tess.func_78377_a(xzp, top, xzm);
        tess.func_78377_a(xzn, top, xzm);
        tess.func_78377_a(xzn, bot, xzm);
        tess.func_78381_a();
    }

    public static void preSkyList() {
        GL11.glColor3f(fogColorR, fogColorG, fogColorB);
        drawHorizon();
        GL11.glColor3f(skyColorR, skyColorG, skyColorB);
    }

    public static void endSky() {
        isRenderingSky = false;
        setDrawBuffers(dfbDrawBuffers);
        useProgram(lightmapEnabled ? 3 : 2);
        popEntity();
    }

    public static void beginUpdateChunks() {
        checkGLError("beginUpdateChunks1");
        checkFramebufferStatus("beginUpdateChunks1");
        if (!isShadowPass) {
            useProgram(6);
        }

        checkGLError("beginUpdateChunks2");
        checkFramebufferStatus("beginUpdateChunks2");
    }

    public static void endUpdateChunks() {
        checkGLError("endUpdateChunks1");
        checkFramebufferStatus("endUpdateChunks1");
        if (!isShadowPass) {
            useProgram(6);
        }

        checkGLError("endUpdateChunks2");
        checkFramebufferStatus("endUpdateChunks2");
    }

    public static boolean shouldRenderClouds(GameSettings gs) {
        checkGLError("shouldRenderClouds");
        return isShadowPass ? configCloudShadow : gs.field_74345_l;
    }

    public static void beginClouds() {
        fogEnabled = true;
        pushEntity(-3, 0);
        useProgram(2);
    }

    public static void endClouds() {
        disableFog();
        popEntity();
    }

    public static void beginTerrain() {
        if (isRenderingWorld) {
            if (isShadowPass) {
                GL11.glDisable(2884);
            }

            fogEnabled = true;
            useProgram(6);
        }

    }

    public static void endTerrain() {
        if (isRenderingWorld) {
            if (isShadowPass) {
                GL11.glEnable(2884);
            }

            useProgram(lightmapEnabled ? 3 : 2);
        }

    }

    public static void beginTileEntities() {
        if (isRenderingWorld) {
            checkGLError("beginTileEntities");
            useProgram(6);
        }

    }

    public static void endTileEntities() {
        if (isRenderingWorld) {
            checkGLError("endTileEntities");
            useProgram(lightmapEnabled ? 3 : 2);
            ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
        }

    }

    public static void beginBlockDestroyProgress() {
        if (isRenderingWorld) {
            useProgram(6);
            if (configTweakBlockDamage) {
                setDrawBuffers(drawBuffersColorAtt0);
                GL11.glDepthMask(false);
            }
        }

    }

    public static void endBlockDestroyProgress() {
        if (isRenderingWorld) {
            GL11.glDepthMask(true);
            useProgram(3);
        }

    }

    public static void beginEntities() {
        if (isRenderingWorld) {
            useProgram(8);
            if (programsID[activeProgram] != 0) {
                useEntityHurtFlash = uniformEntityHurt != -1 || uniformEntityFlash != -1;
                if (uniformEntityHurt != -1) {
                    ARBShaderObjects.glUniform1iARB(uniformEntityHurt, 0);
                }

                if (uniformEntityHurt != -1) {
                    ARBShaderObjects.glUniform1iARB(uniformEntityFlash, 0);
                }
            } else {
                useEntityHurtFlash = false;
            }
        }

    }

    public static void nextEntity() {
        if (isRenderingWorld) {
            useProgram(8);
        }

    }

    public static void beginSpiderEyes() {
        if (isRenderingWorld) {
            useProgram(9);
            if (programsID[9] == programsID[2]) {
                GL11.glEnable(3008);
                GL11.glBlendFunc(1, 1);
            }
        }

    }

    public static void endEntities() {
        if (isRenderingWorld) {
            useProgram(lightmapEnabled ? 3 : 2);
        }

    }

    public static void setEntityHurtFlash(int hurt, int flash) {
        if (useEntityHurtFlash && isRenderingWorld && !isShadowPass) {
            if (uniformEntityHurt != -1) {
                ARBShaderObjects.glUniform1iARB(uniformEntityHurt, hurt);
            }

            if (uniformEntityFlash != -1) {
                ARBShaderObjects.glUniform1iARB(uniformEntityFlash, flash >> 24);
            }

            checkGLError("setEntityHurtFlash");
        }

    }

    public static void resetEntityHurtFlash() {
        setEntityHurtFlash(0, 0);
    }

    public static void beginLivingDamage() {
        if (isRenderingWorld) {
            ShadersTex.bindTexture(defaultTexture);
            if (!isShadowPass) {
                setDrawBuffers(drawBuffersColorAtt0);
            }
        }

    }

    public static void endLivingDamage() {
        if (isRenderingWorld && !isShadowPass) {
            setDrawBuffers(programsDrawBuffers[8]);
        }

    }

    public static void beginLitParticles() {
        Tessellator.field_78398_a.func_78375_b(0.0F, 0.0F, 0.0F);
        useProgram(3);
    }

    public static void beginParticles() {
        Tessellator.field_78398_a.func_78375_b(0.0F, 0.0F, 0.0F);
        useProgram(2);
    }

    public static void endParticles() {
        Tessellator.field_78398_a.func_78375_b(0.0F, 0.0F, 0.0F);
        useProgram(3);
    }

    public static void readCenterDepth() {
        if (!isShadowPass && centerDepthSmoothEnabled) {
            GL15.glBindBuffer(35051, idPixelBufferReadDepth);
            ByteBuffer byteBufferNew = GL15.glMapBuffer(35051, 35000, 16L, byteBufferReadDepth);
            byteBufferReadDepth = byteBufferNew;
            centerDepth = byteBufferNew.getFloat(0);
            GL15.glUnmapBuffer(35051);
            GL15.glBindBuffer(35051, 0);
            GL15.glBindBuffer(35051, idPixelBufferReadDepth);
            GL11.glPixelStorei(3329, 1);
            GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, 0L);
            GL15.glBindBuffer(35051, 0);
            float fadeScalar = (float)diffSystemTime * 0.01F;
            float fadeFactor = (float)Math.exp(Math.log(0.5) * (double)fadeScalar / (double)centerDepthSmoothHalflife);
            centerDepthSmooth = centerDepthSmooth * fadeFactor + centerDepth * (1.0F - fadeFactor);
        }

    }

    public static void beginWeather() {
        if (!isShadowPass) {
            if (usedDepthBuffers >= 3) {
                GL13.glActiveTexture(33996);
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
                GL13.glActiveTexture(33984);
            }

            GL11.glEnable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            useProgram(11);
        }

    }

    public static void endWeather() {
        GL11.glDisable(3042);
        useProgram(3);
    }

    public static void preWater() {
        if (isShadowPass) {
            if (usedShadowDepthBuffers >= 2) {
                GL13.glActiveTexture(33989);
                checkGLError("pre copy shadow depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, shadowMapWidth, shadowMapHeight);
                checkGLError("copy shadow depth");
                GL13.glActiveTexture(33984);
            }
        } else {
            if (usedDepthBuffers >= 2) {
                GL13.glActiveTexture(33995);
                checkGLError("pre copy depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
                checkGLError("copy depth");
                GL13.glActiveTexture(33984);
            }

            ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
        }

    }

    public static void beginWaterFancy() {
        if (isRenderingWorld && !isShadowPass) {
            useProgram(7);
            GL11.glEnable(3042);
            setDrawBuffers(drawBuffersNone);
        }

    }

    public static void midWaterFancy() {
        if (isRenderingWorld && !isShadowPass) {
            setDrawBuffers(programsDrawBuffers[7]);
        }

    }

    public static void beginWater() {
        if (isRenderingWorld) {
            if (!isShadowPass) {
                useProgram(7);
                GL11.glEnable(3042);
                GL11.glDepthMask(true);
            } else {
                GL11.glDepthMask(true);
            }
        }

    }

    public static void endWater() {
        if (isRenderingWorld) {
            if (isShadowPass) {
            }

            useProgram(lightmapEnabled ? 3 : 2);
        }

    }

    public static void beginProjectRedHalo() {
        if (isRenderingWorld) {
            useProgram(1);
        }

    }

    public static void endProjectRedHalo() {
        if (isRenderingWorld) {
            useProgram(3);
        }

    }

    public static void applyHandDepth() {
        if ((double)configHandDepthMul != 1.0) {
            GL11.glScaled(1.0, 1.0, (double)configHandDepthMul);
        }

    }

    public static void beginHand() {
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        useProgram(10);
        checkGLError("beginHand");
        checkFramebufferStatus("beginHand");
    }

    public static void endHand() {
        checkGLError("pre endHand");
        checkFramebufferStatus("pre endHand");
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glBlendFunc(770, 771);
        checkGLError("endHand");
    }

    public static void beginFPOverlay() {
        GL11.glDisable(2896);
        GL11.glDisable(3042);
    }

    public static void endFPOverlay() {
    }

    public static void glEnableWrapper(int cap) {
        GL11.glEnable(cap);
        if (cap == 3553) {
            enableTexture2D();
        } else if (cap == 2912) {
            enableFog();
        }

    }

    public static void glDisableWrapper(int cap) {
        GL11.glDisable(cap);
        if (cap == 3553) {
            disableTexture2D();
        } else if (cap == 2912) {
            disableFog();
        }

    }

    public static void sglEnableT2D(int cap) {
        GL11.glEnable(cap);
        enableTexture2D();
    }

    public static void sglDisableT2D(int cap) {
        GL11.glDisable(cap);
        disableTexture2D();
    }

    public static void sglEnableFog(int cap) {
        GL11.glEnable(cap);
        enableFog();
    }

    public static void sglDisableFog(int cap) {
        GL11.glDisable(cap);
        disableFog();
    }

    public static void enableTexture2D() {
        if (isRenderingSky) {
            useProgram(5);
        } else if (activeProgram == 1) {
            useProgram(lightmapEnabled ? 3 : 2);
        }

    }

    public static void disableTexture2D() {
        if (isRenderingSky) {
            useProgram(4);
        } else if (activeProgram == 2 || activeProgram == 3) {
            useProgram(1);
        }

    }

    public static void enableFog() {
        fogEnabled = true;
        setProgramUniform1i("fogMode", fogMode);
    }

    public static void disableFog() {
        fogEnabled = false;
        setProgramUniform1i("fogMode", 0);
    }

    public static void sglFogi(int pname, int param) {
        GL11.glFogi(pname, param);
        if (pname == 2917) {
            fogMode = param;
            if (fogEnabled) {
                setProgramUniform1i("fogMode", fogMode);
            }
        }

    }

    public static void enableLightmap() {
        lightmapEnabled = true;
        if (activeProgram == 2) {
            useProgram(3);
        }

    }

    public static void disableLightmap() {
        lightmapEnabled = false;
        if (activeProgram == 3) {
            useProgram(2);
        }

    }

    public static int getEntityData() {
        return entityData[entityDataIndex * 2];
    }

    public static int getEntityData2() {
        return entityData[entityDataIndex * 2 + 1];
    }

    public static int setEntityData1(int data1) {
        entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & '\uffff' | data1 << 16;
        return data1;
    }

    public static int setEntityData2(int data2) {
        entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & -65536 | data2 & '\uffff';
        return data2;
    }

    public static void pushEntity(int data0, int data1) {
        ++entityDataIndex;
        entityData[entityDataIndex * 2] = data0 & '\uffff' | data1 << 16;
        entityData[entityDataIndex * 2 + 1] = 0;
    }

    public static void pushEntity(int data0) {
        ++entityDataIndex;
        entityData[entityDataIndex * 2] = data0 & '\uffff';
        entityData[entityDataIndex * 2 + 1] = 0;
    }

    public static void pushEntity(Block block) {
        ++entityDataIndex;
        entityData[entityDataIndex * 2] = block.field_71990_ca & '\uffff' | block.func_71857_b() << 16;
        entityData[entityDataIndex * 2 + 1] = 0;
    }

    public static void pushEntity(RenderBlocks rb, Block block, int x, int y, int z) {
        ++entityDataIndex;
        entityData[entityDataIndex * 2] = block.field_71990_ca & '\uffff' | block.func_71857_b() << 16;
        entityData[entityDataIndex * 2 + 1] = rb.field_78669_a.func_72805_g(x, y, z);
    }

    public static void popEntity() {
        entityData[entityDataIndex * 2] = 0;
        entityData[entityDataIndex * 2 + 1] = 0;
        --entityDataIndex;
    }

    static {
        shadersdir = new File(Minecraft.func_71410_x().field_71412_D, "shaders");
        shaderpacksdir = new File(Minecraft.func_71410_x().field_71412_D, shaderpacksdirname);
        configFile = new File(Minecraft.func_71410_x().field_71412_D, optionsfilename);
        blockLightLevel05 = 0.5F;
        blockLightLevel06 = 0.6F;
        blockLightLevel08 = 0.8F;
        aoLevel = 0.8F;
        blockAoLight = 1.0F - aoLevel;
        sunPathRotation = 0.0F;
        shadowAngleInterval = 0.0F;
        fogMode = 0;
        shadowIntervalSize = 2.0F;
        terrainIconSize = 16;
        terrainTextureSize = new int[2];
        noiseTextureEnabled = false;
        noiseTextureResolution = 256;
        bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(1812).limit(0);
        previousProjection = nextFloatBuffer(16);
        projection = nextFloatBuffer(16);
        projectionInverse = nextFloatBuffer(16);
        previousModelView = nextFloatBuffer(16);
        modelView = nextFloatBuffer(16);
        modelViewInverse = nextFloatBuffer(16);
        shadowProjection = nextFloatBuffer(16);
        shadowProjectionInverse = nextFloatBuffer(16);
        shadowModelView = nextFloatBuffer(16);
        shadowModelViewInverse = nextFloatBuffer(16);
        tempMatrixDirectBuffer = nextFloatBuffer(16);
        tempDirectFloatBuffer = nextFloatBuffer(16);
        dfbColorTextures = nextIntBuffer(8);
        dfbDepthTextures = nextIntBuffer(3);
        sfbColorTextures = nextIntBuffer(8);
        sfbDepthTextures = nextIntBuffer(2);
        dfbDrawBuffers = nextIntBuffer(8);
        sfbDrawBuffers = nextIntBuffer(8);
        drawBuffersNone = nextIntBuffer(8);
        drawBuffersAll = nextIntBuffer(8);
        drawBuffersClear0 = nextIntBuffer(8);
        drawBuffersClear1 = nextIntBuffer(8);
        drawBuffersClearColor = nextIntBuffer(8);
        drawBuffersColorAtt0 = nextIntBuffer(8);
        drawBuffersBuffer = nextIntBufferArray(22, 8);
        drawBuffersNone.limit(0);
        drawBuffersColorAtt0.put(36064).position(0).limit(1);
        gbufferFormatPattern = Pattern.compile("[ \t]*const[ \t]*int[ \t]*(\\w+)Format[ \t]*=[ \t]*([RGBA81632F]*)[ \t]*;.*");
        gbufferMipmapEnabledPattern = Pattern.compile("[ \t]*const[ \t]*bool[ \t]*(\\w+)MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*");
        invertMat4x_m = new float[16];
        invertMat4x_inv = new float[16];
        entityData = new int[32];
        entityDataIndex = 0;
    }
}
