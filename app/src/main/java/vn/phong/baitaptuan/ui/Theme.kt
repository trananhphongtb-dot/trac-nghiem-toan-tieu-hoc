package vn.phong.baitaptuan.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import vn.phong.baitaptuan.model.Subject

private val LightColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD7EFD8),
    onPrimaryContainer = Color(0xFF0B2E0E),
    secondary = Color(0xFF546E7A),
    background = Color(0xFFF3F6F3),
    onBackground = Color(0xFF1A1C1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1A),
    surfaceVariant = Color(0xFFE4EAE4),
    onSurfaceVariant = Color(0xFF4A524A),
    outline = Color(0xFFB7C0B7),
    error = Color(0xFFC62828)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF0B2E0E),
    primaryContainer = Color(0xFF1F4A22),
    onPrimaryContainer = Color(0xFFD7EFD8),
    secondary = Color(0xFF90A4AE),
    background = Color(0xFF101412),
    onBackground = Color(0xFFE2E6E2),
    surface = Color(0xFF1A1F1B),
    onSurface = Color(0xFFE2E6E2),
    surfaceVariant = Color(0xFF2A322B),
    onSurfaceVariant = Color(0xFFB9C2B9),
    outline = Color(0xFF55605A),
    error = Color(0xFFEF9A9A)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content
    )
}

/** Màu nhấn riêng cho từng môn. */
fun Subject.accent(): Color = when (this) {
    Subject.TOAN -> Color(0xFF1E88E5)
    Subject.TIENG_VIET -> Color(0xFFE64A19)
    Subject.TIENG_ANH -> Color(0xFF8E24AA)
    Subject.TU_NHIEN -> Color(0xFF2E9E4F)
    Subject.LICH_SU_DIA_LI -> Color(0xFF8D6E63)
}

fun Subject.emoji(): String = when (this) {
    Subject.TOAN -> "🔢"
    Subject.TIENG_VIET -> "📖"
    Subject.TIENG_ANH -> "🔤"
    Subject.TU_NHIEN -> "🌱"
    Subject.LICH_SU_DIA_LI -> "🗺️"
}

fun Subject.tagline(): String = when (this) {
    Subject.TOAN -> "Số học, hình học, giải toán"
    Subject.TIENG_VIET -> "Từ ngữ, ngữ pháp, chính tả"
    Subject.TIENG_ANH -> "Từ vựng, mẫu câu, ngữ pháp"
    Subject.TU_NHIEN -> "Tự nhiên, xã hội, khoa học"
    Subject.LICH_SU_DIA_LI -> "Lịch sử, địa lí Việt Nam"
}
