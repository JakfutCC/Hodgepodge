package com.mitchej123.hodgepodge.mixins.late.bibliocraft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

class MeasurementHeightTest {

    @Test
    void floorBothMeasurementOriginsBeforeTheOriginalIntegerCast() throws Exception {
        Object[] mixins = { new MixinPlumbLineHeight() {}, new MixinTapeMeasureHeight() {} };
        for (Object mixin : mixins) {
            Method correction = mixin.getClass().getSuperclass()
                    .getDeclaredMethod("hodgepodge$floorMeasurementY", double.class);
            correction.setAccessible(true);
            for (double y : new double[] { -32000.75, -320.5, -16, -1, -0.5, 0, 0.5, 64, 255.75, 320.5 }) {
                assertEquals((int) Math.floor(y), (int) (double) correction.invoke(mixin, y));
            }
        }
    }
}
