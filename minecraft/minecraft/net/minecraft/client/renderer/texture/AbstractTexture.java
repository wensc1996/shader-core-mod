package net.minecraft.client.renderer.texture;

import shadersmodcore.client.MultiTexID;
import shadersmodcore.client.ShadersTex;

public class AbstractTexture implements TextureObject {
    public MultiTexID multiTex;
    public MultiTexID getMultiTexID()
    {
        return ShadersTex.getMultiTexID(this);
    }
}
