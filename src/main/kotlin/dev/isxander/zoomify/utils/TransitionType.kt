package dev.isxander.zoomify.utils

import dev.isxander.settxi.impl.SettingDisplayName
import kotlin.math.*

enum class TransitionType(override val displayName: String) : Transition, SettingDisplayName {
    INSTANT("zoomify.transition.instant") {
        override fun apply(t: Double) =
            t
    },
    LINEAR("zoomify.transition.linear") {
        override fun apply(t: Double) =
            t
    },
    EASE_IN_SINE("zoomify.transition.ease_in_sine") {
        override fun apply(t: Double): Double =
            1 - cos((t * PI) / 2)

        override fun inverse(x: Double) =
            acos(-(x - 1)) * 2 / PI
    },
    EASE_OUT_SINE("zoomify.transition.ease_out_sine") {
        override fun apply(t: Double): Double =
            sin((t * PI) / 2)

        override fun inverse(x: Double) =
            asin(-x) * 2 / PI
    },
    EASE_IN_OUT_SINE("zoomify.transition.ease_in_out_sine") {
        override fun apply(t: Double): Double =
            -(cos(PI * t) - 1) / 2
    },
    EASE_IN_QUAD("zoomify.transition.ease_in_quad") {
        override fun apply(t: Double): Double =
            t * t

        override fun inverse(x: Double) =
            sqrt(x)
    },
    EASE_OUT_QUAD("zoomify.transition.ease_out_quad") {
        override fun apply(t: Double): Double =
            1 - (1 - t) * (1 - t)

        override fun inverse(x: Double) =
            -(sqrt(-(x - 1)) - 1)

    },
    EASE_IN_OUT_QUAD("zoomify.transition.ease_in_out_quad") {
        override fun apply(t: Double): Double =
            if (t < 0.5)
                2 * t * t
            else
                1 - (-2 * t + 2).pow(2) / 2
    },
    EASE_IN_CUBIC("zoomify.transition.ease_in_cubic") {
        override fun apply(t: Double): Double =
            t.pow(3)

        override fun inverse(x: Double) =
            x.pow(1 / 3.0)
    },
    EASE_OUT_CUBIC("zoomify.transition.ease_out_cubic") {
        override fun apply(t: Double): Double =
            1 - (1 - t).pow(3)

        override fun inverse(x: Double) =
            -((-(x - 1)).pow(1 / 3.0))
    },
    EASE_IN_OUT_CUBIC("zoomify.transition.ease_in_out_cubic") {
        override fun apply(t: Double): Double =
            if (t < 0.5)
                4 * t * t * t
            else
                1 - (-2 * t + 2).pow(3) / 2
    },
    EASE_IN_EXP("zoomify.transition.ease_in_exp") {
        override fun apply(t: Double): Double =
            if (t == 0.0) 0.0 else 2.0.pow(10 * t - 10)

        override fun inverse(x: Double) =
            if (x == 0.0) 0.0 else log(x, 1024.0) + 1
    },
    EASE_OUT_EXP("zoomify.transition.ease_out_exp") {
        override fun apply(t: Double): Double =
            if (t == 1.0) 1.0 else 1.0 - 2.0.pow(-10.0 * t)

        override fun inverse(x: Double) =
            if (x == 1.0) 1.0 else -log(1 - x, 1024.0)
    },
    EASE_IN_OUT_EXP("zoomify.transition.ease_in_out_exp") {
        override fun apply(t: Double): Double =
            if (t == 0.0)
                0.0
            else if (t == 1.0)
                1.0
            else if (t < 0.5)
                2.0.pow(20.0 * t - 10.0) / 2.0
            else (2.0 - 2.0.pow(-20.0 * t + 10.0)) / 2
    };

    fun opposite(): TransitionType = when (this) {
        INSTANT -> INSTANT
        LINEAR -> LINEAR
        EASE_IN_SINE -> EASE_OUT_SINE
        EASE_OUT_SINE -> EASE_IN_SINE
        EASE_IN_OUT_SINE -> EASE_IN_OUT_SINE
        EASE_IN_QUAD -> EASE_OUT_QUAD
        EASE_OUT_QUAD -> EASE_IN_QUAD
        EASE_IN_OUT_QUAD -> EASE_IN_OUT_QUAD
        EASE_IN_CUBIC -> EASE_OUT_CUBIC
        EASE_OUT_CUBIC -> EASE_IN_CUBIC
        EASE_IN_OUT_CUBIC -> EASE_IN_OUT_CUBIC
        EASE_IN_EXP -> EASE_OUT_EXP
        EASE_OUT_EXP -> EASE_IN_EXP
        EASE_IN_OUT_EXP -> EASE_IN_OUT_EXP
    }
}

fun interface Transition {
    fun apply(t: Double): Double

    fun inverse(x: Double): Double {
        throw UnsupportedOperationException()
    }

    fun hasInverse() = try {
        inverse(0.0)
        true
    } catch (_: UnsupportedOperationException) {
        false
    }
}
