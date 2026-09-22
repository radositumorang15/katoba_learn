package id.kotoba.app.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.AiConversationController
import id.kotoba.app.model.remote.AiChatMessage
import id.kotoba.app.view.theme.BorderOutline
import id.kotoba.app.view.theme.PastelCoral
import id.kotoba.app.view.theme.PastelGreen
import id.kotoba.app.view.theme.PrimaryGreen
import id.kotoba.app.view.theme.PrimarySurface
import id.kotoba.app.view.theme.ShapeBadgePill
import id.kotoba.app.view.theme.ShapeCardMedium
import id.kotoba.app.view.theme.SoftPurple
import id.kotoba.app.view.theme.SurfaceCard
import id.kotoba.app.view.theme.TextPrimary
import id.kotoba.app.view.theme.TextSecondary

@Composable
fun AiConversationScreen(
    controller: AiConversationController,
    modifier: Modifier = Modifier
) {
    val messages by controller.messages.collectAsState()
    val inputText by controller.inputText.collectAsState()
    val isLoading by controller.isLoading.collectAsState()
    val errorMessage by controller.errorMessage.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size, isLoading) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Centered responsive container across Desktop, Tablet & Mobile
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .imePadding()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 760.dp)
                .fillMaxWidth()
        ) {
            // Top App Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(width = 1.dp, color = BorderOutline)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back button
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF1F5F9))
                            .border(1.dp, BorderOutline, CircleShape)
                            .clickable(onClick = { controller.goBack() }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "←", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Avatar Sensei
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(PastelGreen)
                            .border(1.5.dp, PrimaryGreen.copy(alpha = 0.25f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⛩️", fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Katoba Sensei",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(ShapeBadgePill)
                                    .background(PrimarySurface)
                                    .border(1.dp, PrimaryGreen.copy(alpha = 0.2f), ShapeBadgePill)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "Gemini Flash AI",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = if (isLoading) "Sensei sedang mengetik..." else "Latihan Ngobrol Bebas (JLPT N5)",
                            fontSize = 12.sp,
                            color = if (isLoading) PrimaryGreen else TextSecondary
                        )
                    }
                }
            }

            // Offline / Error notification banner
            AnimatedVisibility(visible = errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clip(ShapeBadgePill)
                        .background(PastelCoral)
                        .clickable { controller.dismissError() }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚠️ ${errorMessage ?: ""}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF991B1B)
                        )
                        Text(
                            text = "✕",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF991B1B)
                        )
                    }
                }
            }

            // Quick Suggestion Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(controller.quickPrompts) { prompt ->
                    Box(
                        modifier = Modifier
                            .clip(ShapeBadgePill)
                            .background(Color.White)
                            .border(1.5.dp, BorderOutline, ShapeBadgePill)
                            .clickable { controller.sendQuickPrompt(prompt) }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = prompt,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryGreen
                        )
                    }
                }
            }

            // Chat Message List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus(force = true)
                        keyboardController?.hide()
                    },
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(messages, key = { it.id }) { message ->
                    ChatMessageItem(
                        message = message,
                        onPlayAudio = { controller.playAudio(message.text) }
                    )
                }

                if (isLoading) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(ShapeBadgePill)
                                .background(PrimarySurface)
                                .border(1.dp, PrimaryGreen.copy(alpha = 0.2f), ShapeBadgePill)
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = PrimaryGreen,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sensei sedang menyusun balasan...",
                                fontSize = 12.sp,
                                color = PrimaryGreen
                            )
                        }
                    }
                }
            }

            // Bottom Input Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(width = 1.dp, color = BorderOutline)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { controller.updateInputText(it) },
                        placeholder = {
                            Text(
                                text = "Ketik pesan bahasa Jepang...",
                                fontSize = 13.sp,
                                color = TextSecondary
                            )
                        },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            imeAction = androidx.compose.ui.text.input.ImeAction.Send
                        ),
                        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                            onSend = {
                                if (inputText.isNotBlank() && !isLoading) {
                                    controller.sendMessage()
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

                    // Send Button
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(if (inputText.isNotBlank() && !isLoading) PrimaryGreen else Color(0xFFCBD5E1))
                            .clickable(enabled = inputText.isNotBlank() && !isLoading) {
                                controller.sendMessage()
                                focusManager.clearFocus(force = true)
                                keyboardController?.hide()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "➤",
                            fontSize = 17.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(
    message: AiChatMessage,
    onPlayAudio: () -> Unit
) {
    val isUser = message.sender == "user"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PastelGreen)
                    .border(1.5.dp, PrimaryGreen.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⛩️", fontSize = 19.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
        }

        Box(
            modifier = Modifier
                .widthIn(min = 80.dp, max = 540.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = if (isUser) 20.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 20.dp
                    )
                )
                .background(if (isUser) PrimaryGreen else SurfaceCard)
                .border(
                    width = 1.dp,
                    color = if (isUser) PrimaryGreen else BorderOutline,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Column {
                if (!isUser) {
                    // Card Header with Pill Badge and Audio Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(ShapeBadgePill)
                                .background(PrimarySurface)
                                .border(1.dp, PrimaryGreen.copy(alpha = 0.25f), ShapeBadgePill)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Katoba Sensei",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryGreen
                            )
                        }

                        // Audio button with SoftPurple background
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(SoftPurple)
                                .clickable { onPlayAudio() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🔊", fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (isUser) {
                    Text(
                        text = message.text,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        lineHeight = 22.sp
                    )
                } else {
                    // Formatted Sensei message for clean visual hierarchy
                    SenseiMessageContent(text = message.text)
                }
            }
        }
    }
}

@Composable
private fun SenseiMessageContent(text: String) {
    val lines = text.lines().map { it.trim() }.filter { it.isNotBlank() }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        lines.forEach { line ->
            val containsJapanese = line.any { it in '\u3040'..'\u30ff' || it in '\u4e00'..'\u9faf' }
            val isRomajiHint = (line.startsWith("(") && line.endsWith(")")) ||
                    line.startsWith("Romaji:", ignoreCase = true) ||
                    line.startsWith("ROMAJI:", ignoreCase = true)

            when {
                containsJapanese -> {
                    Text(
                        text = line,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        lineHeight = 24.sp
                    )
                }
                isRomajiHint -> {
                    Text(
                        text = line,
                        fontSize = 13.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryGreen,
                        lineHeight = 19.sp
                    )
                }
                else -> {
                    Text(
                        text = line,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}
