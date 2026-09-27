package com.mitchej123.hodgepodge.mixins.late.advancedsolarpanels;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

class SolarHelmetCoordinatesTest {

    @Test
    void fieldCorrectionPreservesTheFollowingIntegerCastAndSkyOffset() throws Exception {
        Method correction = MixinItemAdvancedSolarHelmet.class
                .getDeclaredMethod("hodgepodge$floorBlockCoordinate", double.class);
        correction.setAccessible(true);
        Object mixin = new MixinItemAdvancedSolarHelmet();
        double[] positions = { -320.75, -32.5, -16, -1, -0.5, -0.001, 0, 0.5, 64, 255.75, 320.5 };
        for (double position : positions) {
            double corrected = (double) correction.invoke(mixin, position);
            assertEquals((int) Math.floor(position), (int) corrected, "biome position " + position);
            assertEquals((int) Math.floor(position) + 1, (int) corrected + 1, "sky position " + position);
        }
    }
}
