package id.kotoba.app.model.repository

import id.kotoba.app.model.ItemStatus
import id.kotoba.app.model.KanaItem
import id.kotoba.app.model.UserItemProgress
import id.kotoba.app.platform.getPlatformTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SrsRepository {

    // Map of itemId -> UserItemProgress
    private val _itemProgressMap = MutableStateFlow<Map<String, UserItemProgress>>(emptyMap())
    val itemProgressMap: StateFlow<Map<String, UserItemProgress>> = _itemProgressMap.asStateFlow()

    private val _dueCount = MutableStateFlow(0)
    val dueCount: StateFlow<Int> = _dueCount.asStateFlow()

    private val _masteredCount = MutableStateFlow(0)
    val masteredCount: StateFlow<Int> = _masteredCount.asStateFlow()

    init {
        // Seed default items from Level 1 so Review Box immediately has active items
        val now = getPlatformTimeMillis()
        val initialMap = mutableMapOf<String, UserItemProgress>()
        
        // Seed first 4 items as due today for demonstration
        val sampleKana = KanaRepository.level1Kana.take(4)
        sampleKana.forEachIndexed { i, item ->
            initialMap[item.id] = UserItemProgress(
                id = "srs_${item.id}",
                userId = "default",
                itemId = item.id,
                status = ItemStatus.REVIEWING,
                intervalDays = 1,
                // Due 1 hour ago so it's ready in Review Box immediately
                nextReviewDateMillis = now - 3600000L,
                lastReviewedAtMillis = now - 86400000L,
                correctStreak = 1
            )
        }
        _itemProgressMap.value = initialMap
        recalculateCounts()
    }

    fun getProgress(itemId: String): UserItemProgress {
        return _itemProgressMap.value[itemId] ?: UserItemProgress(
            itemId = itemId,
            status = ItemStatus.NEW
        )
    }

    fun recordReview(userId: String, itemId: String, isCorrect: Boolean): UserItemProgress {
        val now = getPlatformTimeMillis()
        val current = getProgress(itemId)
        val newProgress: UserItemProgress

        if (isCorrect) {
            val newStreak = current.correctStreak + 1
            val newIntervalDays = when (newStreak) {
                1 -> 1
                2 -> 3
                3 -> 7
                4 -> 14
                else -> maxOf(14, (current.intervalDays * current.easeFactor).toInt())
            }
            val newStatus = if (newStreak >= 4) ItemStatus.MASTERED else ItemStatus.REVIEWING
            val nextReviewDate = now + (newIntervalDays.toLong() * 24L * 60L * 60L * 1000L)

            newProgress = current.copy(
                userId = userId,
                status = newStatus,
                intervalDays = newIntervalDays,
                nextReviewDateMillis = nextReviewDate,
                lastReviewedAtMillis = now,
                correctStreak = newStreak
            )
        } else {
            // Failed review: reset interval and slightly reduce ease
            val newEase = maxOf(1.3f, current.easeFactor - 0.2f)
            val nextReviewDate = now + (1L * 24L * 60L * 60L * 1000L) // due tomorrow

            newProgress = current.copy(
                userId = userId,
                status = ItemStatus.LEARNING,
                easeFactor = newEase,
                intervalDays = 1,
                nextReviewDateMillis = nextReviewDate,
                lastReviewedAtMillis = now,
                correctStreak = 0
            )
        }

        val updated = _itemProgressMap.value.toMutableMap()
        updated[itemId] = newProgress
        _itemProgressMap.value = updated
        recalculateCounts()

        return newProgress
    }

    fun getDueItems(allItems: List<KanaItem>): List<KanaItem> {
        val map = _itemProgressMap.value
        val now = getPlatformTimeMillis()
        val due = allItems.filter { item ->
            val prog = map[item.id]
            prog != null && prog.status != ItemStatus.NEW && prog.nextReviewDateMillis <= now
        }
        // If due list is empty but we have items, provide at least the first 3 items for practice
        return if (due.isEmpty() && allItems.isNotEmpty()) {
            allItems.take(3)
        } else {
            due
        }
    }

    private fun recalculateCounts() {
        val now = getPlatformTimeMillis()
        var due = 0
        var mastered = 0
        for ((_, prog) in _itemProgressMap.value) {
            if (prog.status == ItemStatus.MASTERED) {
                mastered++
            }
            if (prog.status != ItemStatus.NEW && prog.nextReviewDateMillis <= now) {
                due++
            }
        }
        _dueCount.value = due
        _masteredCount.value = mastered
    }
}
