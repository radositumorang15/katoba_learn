package id.kotoba.app.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.GlossaryController
import id.kotoba.app.controller.GrammarParticle
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PastelBlue
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.ShapeHeroLarge
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun GlossaryScreen(
    controller: GlossaryController,
    modifier: Modifier = Modifier
) {
    val searchQuery by controller.searchQuery.collectAsState()
    val selectedParticle by controller.selectedParticle.collectAsState()
    val aiQuestion by controller.aiQuestion.collectAsState()
    val aiAnswer by controller.aiAnswer.collectAsState()
    val isLoadingAi by controller.isLoadingAi.collectAsState()

    val filteredParticles = controller.defaultParticles.filter {
        it.character.contains(searchQuery, ignoreCase = true) ||
                it.romaji.contains(searchQuery, ignoreCase = true) ||
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.explanation.contains(searchQuery, ignoreCase = true)
    }

    val quickQuestions = listOf(
        "Kenapa pakai は bukan が?",
        "Kapan pakai で vs に?",
        "Fungsi partikel を?",
        "Beda です dan だ?"
    )

    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current
    val keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .clickable(
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
            },
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 760.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                },
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, BorderOutline, CircleShape)
                            .clickable(onClick = { controller.goBack() }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "←", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "📖 Glosarium Tata Bahasa N5",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Referensi partikel & tanya jawab Gemini Sensei",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Gemini AI Sensei Question Box (PRD Fase 4 & 5.9)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeHeroLarge)
                        .background(Color.White)
                        .border(1.dp, PrimaryGreen.copy(alpha = 0.25f), ShapeHeroLarge)
                        .padding(18.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(PastelGreen)
                                    .border(1.dp, PrimaryGreen.copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🤖", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Tanya Sensei (Gemini AI)",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                                Text(
                                    text = "Bingung partikel atau pola kalimat? Tanyakan ke Sensei!",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick suggestion pills
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(quickQuestions) { q ->
                                Box(
                                    modifier = Modifier
                                        .clip(ShapeBadgePill)
                                        .background(SoftPurple)
                                        .border(1.dp, Color(0xFFC7D2FE), ShapeBadgePill)
                                        .clickable {
                                            controller.updateAiQuestion(q)
                                            controller.askSensei(q)
                                            focusManager.clearFocus(force = true)
                                            keyboardController?.hide()
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = q,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF4338CA)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Input & Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = aiQuestion,
                                onValueChange = { controller.updateAiQuestion(it) },
                                placeholder = {
                                    Text(
                                        text = "Tulis pertanyaanmu...",
                                        fontSize = 13.sp,
                                        color = TextSecondary
                                    )
                                },
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    imeAction = androidx.compose.ui.text.input.ImeAction.Search
                                ),
                                keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                                    onSearch = {
                                        if (aiQuestion.isNotBlank() && !isLoadingAi) {
                                            controller.askSensei()
                                            focusManager.clearFocus(force = true)
                                            keyboardController?.hide()
                                        }
                                    }
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp),
                                shape = RoundedCornerShape(26.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryGreen,
                                    unfocusedBorderColor = BorderOutline,
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC)
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(
                                modifier = Modifier
                                    .clip(ShapeBadgePill)
                                    .background(
                                        if (aiQuestion.isNotBlank() && !isLoadingAi) PrimaryGreen else Color(
                                            0xFFCBD5E1
                                        )
                                    )
                                    .clickable(enabled = aiQuestion.isNotBlank() && !isLoadingAi) {
                                        controller.askSensei()
                                        focusManager.clearFocus(force = true)
                                        keyboardController?.hide()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 13.dp)
                            ) {
                                if (isLoadingAi) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = "Tanya",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        // AI Explanation Result Card
                        AnimatedVisibility(
                            visible = aiAnswer != null,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(14.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFF0FDF4))
                                        .border(1.dp, Color(0xFFBBF7D0), RoundedCornerShape(14.dp))
                                        .padding(14.dp)
                                ) {
                                    Column {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "💡 Jawaban Katoba Sensei:",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PrimaryGreen
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.White)
                                                    .clickable { controller.clearAiAnswer() },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(text = "✕", fontSize = 11.sp, color = TextSecondary)
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = aiAnswer ?: "",
                                            fontSize = 13.sp,
                                            color = TextPrimary,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Section Title: Partikel Dasar N5
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daftar Partikel & Tata Bahasa N5",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${filteredParticles.size} Item",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryGreen
                    )
                }
            }

            // Particle Cards
            items(filteredParticles, key = { it.id }) { particle ->
                val isSelected = selectedParticle?.id == particle.id

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ShapeCardMedium)
                        .background(SurfaceCard)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) PrimaryGreen else BorderOutline,
                            shape = ShapeCardMedium
                        )
                        .clickable { controller.selectParticle(particle) }
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) PrimaryGreen else PrimarySurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = particle.character,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else PrimaryGreen
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = particle.romaji.uppercase(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "• ${particle.title}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = PrimaryGreen
                                    )
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = particle.explanation,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    maxLines = if (isSelected) Int.MAX_VALUE else 2
                                )
                            }
                        }

                        // Expanded Details
                        AnimatedVisibility(visible = isSelected) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 14.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFF8FAFC))
                                    .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = "📝 Contoh Kalimat:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = particle.exampleJp,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = particle.exampleRomaji,
                                    fontSize = 12.sp,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    color = PrimaryGreen
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = particle.exampleMeaning,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Box(
                                    modifier = Modifier
                                        .clip(ShapeBadgePill)
                                        .background(PastelGreen)
                                        .clickable {
                                            val prompt =
                                                "Jelaskan lebih detail penggunaan partikel ${particle.character} (${particle.romaji}) dan beri 2 contoh kalimat lagi."
                                            controller.updateAiQuestion(prompt)
                                            controller.askSensei(prompt)
                                        }
                                        .padding(horizontal = 14.dp, vertical = 7.dp)
                                ) {
                                    Text(
                                        text = "✨ Tanya Detail ke Sensei",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryGreen
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
