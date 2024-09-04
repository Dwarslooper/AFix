/*******************************************************************************
 * Copyright (c) 2023-2024 Cactus Team.
 * This code is part of the Cactus-Client distribution. All Rights reserved.
 ******************************************************************************/

package com.dwarslooper.mods.afix.mixin.impl;

import com.dwarslooper.mods.afix.AFixClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Unique
    private long ctrlLastPressed;

    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_STICKY_KEYS, GLFW.GLFW_FALSE);

        if(key == GLFW.GLFW_KEY_LEFT_CONTROL) ctrlLastPressed = Util.getMeasuringTimeMs();
        if(key == GLFW.GLFW_KEY_RIGHT_ALT) {
            long diff = Util.getMeasuringTimeMs() - ctrlLastPressed;
            if(diff <= 1) {
                try {
                    System.setProperty("java.awt.headless", "false"); // Try to disable headless mode again, if mixin failed
                    Robot robot = new Robot();
                    robot.keyRelease(17);
                } catch (HeadlessException e) {
                    AFixClient.LOGGER.error("Headless mode is enabled, even though it shouldn't be", e);
                } catch (AWTException e) {
                    AFixClient.LOGGER.error("Unknown AWT error", e);
                }
            }
        }

    }

}
