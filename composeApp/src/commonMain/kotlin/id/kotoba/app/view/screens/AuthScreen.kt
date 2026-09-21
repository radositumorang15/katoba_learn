package id.kotoba.app.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.AuthController
import id.kotoba.app.controller.AuthMode
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.ErrorRed
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun AuthScreen(
    controller: AuthController
) {
    val authMode by controller.authMode.collectAsState()
    val email by controller.email.collectAsState()
    val displayName by controller.displayName.collectAsState()
    val password by controller.password.collectAsState()
    val errorMessage by controller.errorMessage.collectAsState()
    val isLoading by controller.isLoading.collectAsState()
    val isAgeAndPrivacyAccepted by controller.isAgeAndPrivacyAccepted.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Logo icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(PastelGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "言",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (authMode == AuthMode.SIGN_IN) "Selamat Datang Kembali!" else "Buat Akun Kotoba",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "Simpan progres belajar kanamu ke cloud",
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .clip(ShapeBadgePill)
                    .background(PrimarySurface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "⚡ Neon Serverless Auth & PostgreSQL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mode Selector Pill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeBadgePill)
                    .background(PrimarySurface)
                    .padding(4.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(ShapeBadgePill)
                            .background(if (authMode == AuthMode.SIGN_IN) PrimaryGreen else Color.Transparent)
                            .clickable { controller.setAuthMode(AuthMode.SIGN_IN) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Masuk",
                            color = if (authMode == AuthMode.SIGN_IN) Color.White else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(ShapeBadgePill)
                            .background(if (authMode == AuthMode.SIGN_UP) PrimaryGreen else Color.Transparent)
                            .clickable { controller.setAuthMode(AuthMode.SIGN_UP) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Daftar Baru",
                            color = if (authMode == AuthMode.SIGN_UP) Color.White else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Display Name (if sign up)
            if (authMode == AuthMode.SIGN_UP) {
                OutlinedTextField(
                    value = displayName,
                    onValueChange = { controller.onDisplayNameChange(it) },
                    label = { Text("Nama Panggilan (Nickname)") },
                    placeholder = { Text("Contoh: Kenji") },
                    shape = ShapeCardMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = BorderOutline
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { controller.onEmailChange(it) },
                label = { Text("Email") },
                placeholder = { Text("nama@email.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = ShapeCardMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = BorderOutline
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { controller.onPasswordChange(it) },
                label = { Text("Password") },
                placeholder = { Text("Minimal 8 karakter") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = ShapeCardMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = BorderOutline
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (authMode == AuthMode.SIGN_UP) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeCardMedium)
                        .clickable { controller.toggleAgeAndPrivacyAccepted() }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Checkbox(
                        checked = isAgeAndPrivacyAccepted,
                        onCheckedChange = { controller.toggleAgeAndPrivacyAccepted() },
                        colors = androidx.compose.material3.CheckboxDefaults.colors(
                            checkedColor = PrimaryGreen
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Saya berusia 13+ tahun & menyetujui Kebijakan Privasi serta Syarat Ketentuan Kotoba.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage ?: "",
                    color = ErrorRed,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Submit CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeBadgePill)
                    .background(if (isLoading) Color(0xFF94A3B8) else PrimaryGreen)
                    .clickable(enabled = !isLoading) { controller.submit() }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        isLoading -> "Menghubungkan ke Neon... ⏳"
                        authMode == AuthMode.SIGN_IN -> "Masuk ke Kotoba"
                        else -> "Daftar Sekarang"
                    },
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Guest mode button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeBadgePill)
                    .border(1.dp, BorderOutline, ShapeBadgePill)
                    .clickable { controller.continueAsGuest() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mode Tamu (Eksplorasi Offline)",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
}
