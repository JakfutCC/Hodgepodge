package com.mitchej123.hodgepodge.mixins.late.advancedsolarpanels;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.mitchej123.hodgepodge.mixins.late.ic2.MixinSolarHelmetCoordinates;

class SolarHelmetCoordinatesTest {

    @Test
    void fieldCorrectionPreservesTheFollowingIntegerCastAndSkyOffset() throws Exception {
        assertCoordinates(new MixinItemAdvancedSolarHelmet());
    }

    @Test
    void ic2FieldCorrectionPreservesTheFollowingIntegerCastAndSkyOffset() throws Exception {
        assertCoordinates(new MixinSolarHelmetCoordinates());
    }

    private static void assertCoordinates(Object mixin) throws Exception {
        Method correction = mixin.getClass().getDeclaredMethod("hodgepodge$floorBlockCoordinate", double.class);
        correction.setAccessible(true);
        double[] positions = { -320.75, -32.5, -16, -1, -0.5, -0.001, 0, 0.5, 64, 255.75, 320.5 };
        for (double position : positions) {
            double corrected = (double) correction.invoke(mixin, position);
            assertEquals((int) Math.floor(position), (int) corrected, "biome position " + position);
            assertEquals((int) Math.floor(position) + 1, (int) corrected + 1, "sky position " + position);
        }
    }
}
