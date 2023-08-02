//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package shadersmodcore.transform;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.SimpleRemapper;

public class SMCRemap {
    private static boolean deobfuscatedEnv = false;
    public static Map<String, String> mapSrgToMcp = null;
    public static Map<String, String> mapMcpToSrg = null;
    public static Remapper remapper;
    public static Remapper remapperW;

    public SMCRemap() {
    }

    private static void readCSV(String fileName, Map<String, String[]> map) {
        BufferedReader rd = null;

        try {
            rd = new BufferedReader(new FileReader(fileName));
            rd.readLine();

            String line;
            while((line = rd.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length > 1) {
                    map.put(tokens[0], tokens);
                }
            }
        } catch (FileNotFoundException var15) {
            var15.printStackTrace();
        } catch (IOException var16) {
            var16.printStackTrace();
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }

        }

    }

    private static String getNamePart(String fullName) {
        int i = fullName.lastIndexOf(47);
        return i >= 0 ? fullName.substring(i + 1) : fullName;
    }

    private static String getParent(String fullName) {
        int i = fullName.lastIndexOf(47);
        return i >= 0 ? fullName.substring(0, i) : "";
    }

    private static String getDesc(String desc, Map<String, String[]> mapC) {
        StringBuilder s = new StringBuilder();
        int i = 0;

        while(i < desc.length()) {
            int p = desc.indexOf(76, i);
            if (p != -1) {
                int q = desc.indexOf(59, p + 1);
                if (q == -1) {
                    return desc;
                }

                s.append(desc, i, p);
                s.append('L');
                s.append(getClassPack(desc.substring(p + 1, q), mapC));
                s.append(';');
                i = q + 1;
            } else {
                s.append(desc, i, desc.length());
                i = desc.length();
            }
        }

        return s.toString();
    }

    private static String getClassPack(String fullName, Map<String, String[]> mapC) {
        String name = getNamePart(fullName);
        String pack = getParent(fullName);
        if (pack.equals("net/minecraft/src")) {
            String[] packrecord = (String[])mapC.get(name);
            if (packrecord != null && packrecord.length >= 2) {
                pack = packrecord[1];
            }
        }

        return pack + "/" + name;
    }

    private static String stringForPrint(String s) {
        return s != null ? s : "";
    }

    private static void parseSrg(String inFileName, Map<String, String[]> mapC, Map<String, String[]> mapF, Map<String, String[]> mapM) {
        BufferedReader rd = null;

        try {
            rd = new BufferedReader(new FileReader(inFileName));

            String line;
            while((line = rd.readLine()) != null) {
                String[] tokens = (String[])Arrays.copyOf(line.split(" "), 10);
                String[] data = null;
                if (tokens[0].equals("CL:")) {
                    tokens[6] = getClassPack(tokens[2], mapC);
                }

                if (tokens[0].equals("FD:")) {
                    tokens[6] = getClassPack(getParent(tokens[2]), mapC);
                    tokens[7] = getNamePart(tokens[2]);
                    tokens[8] = tokens[7];
                    data = (String[])mapF.get(tokens[7]);
                    if (data != null && data.length >= 2) {
                        tokens[8] = data[1];
                        mapSrgToMcp.put(tokens[6] + "." + tokens[7], tokens[8]);
                        mapMcpToSrg.put(tokens[6] + "." + tokens[8], tokens[7]);
                    }
                } else if (tokens[0].equals("MD:")) {
                    tokens[6] = getClassPack(getParent(tokens[3]), mapC);
                    tokens[7] = getNamePart(tokens[3]);
                    tokens[8] = tokens[7];
                    tokens[9] = getDesc(tokens[4], mapC);
                    data = (String[])mapM.get(getNamePart(tokens[3]));
                    if (data != null && data.length >= 2) {
                        tokens[8] = data[1];
                        mapSrgToMcp.put(tokens[6] + "." + tokens[7] + tokens[9], tokens[8]);
                        mapMcpToSrg.put(tokens[6] + "." + tokens[8] + tokens[9], tokens[7]);
                    }
                }
            }
        } catch (FileNotFoundException var18) {
            var18.printStackTrace();
        } catch (IOException var19) {
            var19.printStackTrace();
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

        }

    }

    public static void addClassMap(String srcName, String srgName) {
        mapMcpToSrg.put(srcName, srgName);
        mapSrgToMcp.put(srgName, srcName);
    }

    public static void addInheritedMethod(String owner, String base, String name, String desc) {
        String srcName = (String)mapSrgToMcp.get(base + "." + name + desc);
        mapMcpToSrg.put(owner + "." + srcName + desc, name);
        mapSrgToMcp.put(owner + "." + name + desc, srcName);
    }

    public static void addInheritedField(String owner, String base, String name) {
        String srcName = (String)mapSrgToMcp.get(base + "." + name);
        mapMcpToSrg.put(owner + "." + srcName, name);
        mapSrgToMcp.put(owner + "." + name, srcName);
    }

    public static void initMap() {
        if (mapMcpToSrg == null) {
            mapMcpToSrg = new HashMap();
            mapSrgToMcp = new HashMap();
            Map<String, String[]> mapC = new HashMap();
            Map<String, String[]> mapF = new HashMap();
            Map<String, String[]> mapM = new HashMap();
            readCSV("..\\conf\\packages.csv", mapC);
            readCSV("..\\conf\\fields.csv", mapF);
            readCSV("..\\conf\\methods.csv", mapM);
            parseSrg("..\\conf\\joined.srg", mapC, mapF, mapM);
            addClassMap("net/minecraft/src/Config", "Config");
            addClassMap("net/minecraft/src/CustomColorizer", "CustomColorizer");
            addInheritedMethod("net/minecraft/client/multiplayer/WorldClient", "net/minecraft/world/World", "func_72867_j", "(F)F");
            addInheritedMethod("net/minecraft/client/multiplayer/WorldClient", "net/minecraft/world/World", "func_72826_c", "(F)F");
            addInheritedMethod("net/minecraft/entity/EntityLivingBase", "net/minecraft/entity/Entity", "func_70013_c", "(F)F");
            addInheritedField("net/minecraft/client/gui/GuiOptions", "net/minecraft/client/gui/GuiScreen", "field_73882_e");
            addInheritedField("net/minecraft/client/gui/GuiOptions", "net/minecraft/client/gui/GuiScreen", "field_73880_f");
            addInheritedField("net/minecraft/client/gui/GuiOptions", "net/minecraft/client/gui/GuiScreen", "field_73881_g");
            addInheritedField("net/minecraft/client/gui/GuiOptions", "net/minecraft/client/gui/GuiScreen", "field_73887_h");
        }

    }

    private static void putv(Map map, String key, String value) {
        String oldval = (String)map.get(key);
        if (oldval == null) {
            System.out.format("**** add  %s,%s\n", key, value);
            map.put(key, value);
        } else if (!oldval.equals(value)) {
            System.out.format("**** repl %s,%s %s\n", key, value, oldval);
            map.put(key, value);
        } else {
            System.out.format("**** same %s,%s\n", key, value);
        }

    }
    static final class SMCRemap$1 extends Remapper {
        SMCRemap$1() {
        }
    }

    public static MethodVisitor getAdaptor(MethodVisitor mv) {
        return (MethodVisitor)(!deobfuscatedEnv ? mv : new SMCRenamerMethodAdaptor(mv));
    }

    static {
        Boolean deobEnv = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
        deobfuscatedEnv = deobEnv != null && deobEnv;
        if (!deobfuscatedEnv) {
            remapperW = remapper = new SMCRemap$1();
        } else {
            initMap();
            remapper = new SimpleRemapper(mapMcpToSrg);
            remapperW = new SimpleRemapper(mapSrgToMcp);
        }

    }
}
