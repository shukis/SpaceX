package com.spaceix.designsystem.icons.outlined

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Outlined.RocketLaunch: ImageVector
    get() {
        if (_RocketLaunch != null) {
            return _RocketLaunch!!
        }
        _RocketLaunch = ImageVector.Builder(
            name = "Outlined.RocketLaunch",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFFE8EAED))) {
                moveToRelative(226f, 401f)
                lineToRelative(78f, 33f)
                quadToRelative(14f, -28f, 29f, -54f)
                reflectiveQuadToRelative(33f, -52f)
                lineToRelative(-56f, -11f)
                lineToRelative(-84f, 84f)
                close()
                moveTo(368f, 484f)
                lineTo(482f, 597f)
                quadToRelative(42f, -16f, 90f, -49f)
                reflectiveQuadToRelative(90f, -75f)
                quadToRelative(70f, -70f, 109.5f, -155.5f)
                reflectiveQuadTo(806f, 160f)
                quadToRelative(-72f, -5f, -158f, 34.5f)
                reflectiveQuadTo(492f, 304f)
                quadToRelative(-42f, 42f, -75f, 90f)
                reflectiveQuadToRelative(-49f, 90f)
                close()
                moveTo(546f, 419f)
                quadToRelative(-23f, -23f, -23f, -56.5f)
                reflectiveQuadToRelative(23f, -56.5f)
                quadToRelative(23f, -23f, 57f, -23f)
                reflectiveQuadToRelative(57f, 23f)
                quadToRelative(23f, 23f, 23f, 56.5f)
                reflectiveQuadTo(660f, 419f)
                quadToRelative(-23f, 23f, -57f, 23f)
                reflectiveQuadToRelative(-57f, -23f)
                close()
                moveTo(565f, 740f)
                lineTo(649f, 656f)
                lineTo(638f, 600f)
                quadToRelative(-26f, 18f, -52f, 32.5f)
                reflectiveQuadTo(532f, 661f)
                lineToRelative(33f, 79f)
                close()
                moveTo(878f, 87f)
                quadToRelative(19f, 121f, -23.5f, 235.5f)
                reflectiveQuadTo(708f, 541f)
                lineToRelative(20f, 99f)
                quadToRelative(4f, 20f, -2f, 39f)
                reflectiveQuadToRelative(-20f, 33f)
                lineTo(538f, 880f)
                lineToRelative(-84f, -197f)
                lineToRelative(-171f, -171f)
                lineToRelative(-197f, -84f)
                lineToRelative(167f, -168f)
                quadToRelative(14f, -14f, 33.5f, -20f)
                reflectiveQuadToRelative(39.5f, -2f)
                lineToRelative(99f, 20f)
                quadToRelative(104f, -104f, 218f, -147f)
                reflectiveQuadToRelative(235f, -24f)
                close()
                moveTo(157f, 639f)
                quadToRelative(35f, -35f, 85.5f, -35.5f)
                reflectiveQuadTo(328f, 638f)
                quadToRelative(35f, 35f, 34.5f, 85.5f)
                reflectiveQuadTo(327f, 809f)
                quadToRelative(-25f, 25f, -83.5f, 43f)
                reflectiveQuadTo(82f, 884f)
                quadToRelative(14f, -103f, 32f, -161.5f)
                reflectiveQuadToRelative(43f, -83.5f)
                close()
                moveTo(214f, 695f)
                quadToRelative(-10f, 10f, -20f, 36.5f)
                reflectiveQuadTo(180f, 785f)
                quadToRelative(27f, -4f, 53.5f, -13.5f)
                reflectiveQuadTo(270f, 752f)
                quadToRelative(12f, -12f, 13f, -29f)
                reflectiveQuadToRelative(-11f, -29f)
                quadToRelative(-12f, -12f, -29f, -11.5f)
                reflectiveQuadTo(214f, 695f)
                close()
            }
        }.build()

        return _RocketLaunch!!
    }

@Suppress("ObjectPropertyName")
private var _RocketLaunch: ImageVector? = null
