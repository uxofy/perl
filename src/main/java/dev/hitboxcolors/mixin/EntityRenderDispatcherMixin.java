package dev.hitboxcolors.mixin;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    // renderHitbox(matrices, vertices, entity, tickDelta, red, green, blue)
    // static => slots: 0 matrices, 1 vertices, 2 entity, 3 tickDelta, 4 red, 5 green, 6 blue

    @ModifyVariable(method = "renderHitbox", at = @At("HEAD"), argsOnly = true, index = 4)
    private static float hc$red(float red, MatrixStack m, VertexConsumer v, Entity e) {
        if (isPearl(e)) return 1.0F;
        if (isWindCharge(e)) return 0.0F;
        return red;
    }

    @ModifyVariable(method = "renderHitbox", at = @At("HEAD"), argsOnly = true, index = 5)
    private static float hc$green(float green, MatrixStack m, VertexConsumer v, Entity e) {
        if (isPearl(e)) return 0.0F;
        if (isWindCharge(e)) return 0.4F;
        return green;
    }

    @ModifyVariable(method = "renderHitbox", at = @At("HEAD"), argsOnly = true, index = 6)
    private static float hc$blue(float blue, MatrixStack m, VertexConsumer v, Entity e) {
        if (isPearl(e)) return 0.0F;
        if (isWindCharge(e)) return 1.0F;
        return blue;
    }

    private static boolean isPearl(Entity e) {
        return e.getType() == EntityType.ENDER_PEARL;
    }

    private static boolean isWindCharge(Entity e) {
        EntityType<?> t = e.getType();
        return t == EntityType.WIND_CHARGE || t == EntityType.BREEZE_WIND_CHARGE;
    }
}
