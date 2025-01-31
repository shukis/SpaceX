package com.spaceix.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.3.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.3.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

//@ThemePreviews
//@Composable
//private fun Preview() = SpotItTheme {
//    Surface {
//        Column(Modifier.padding(16.dp)) {
//            PreviewItem("bodyLarge", Typography.bodyLarge)
//            PreviewItem("bodyMedium", Typography.bodyMedium)
//            PreviewItem("bodySmall", Typography.bodySmall)
//            PreviewItem("titleLarge", Typography.titleLarge)
//            PreviewItem("labelSmall", Typography.labelSmall)
//        }
//    }
//}
//
//@Composable
//private fun PreviewItem(name: String, style: TextStyle) {
//    Text(
//        text = "$name | ${LoremIpsum(2).values.joinToString(" ")}",
//        style = style,
//        maxLines = 1,
//        overflow = TextOverflow.Ellipsis
//    )
//    Spacer(Modifier.height(8.dp))
//}