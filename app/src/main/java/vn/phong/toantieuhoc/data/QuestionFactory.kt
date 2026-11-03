package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.Topic
import kotlin.random.Random

private var counter = 0

/** Tạo 1 câu trắc nghiệm 4 đáp án, tự trộn thứ tự đáp án. */
fun makeQuestion(
    grade: Int,
    topic: Topic,
    level: Difficulty,
    text: String,
    correct: String,
    distractors: List<String>,
    explanation: String,
    random: Random
): Question {
    val clean = distractors.filter { it != correct }.distinct().take(3).toMutableList()
    // Dự phòng nếu đáp án nhiễu trùng nhau, cộng thêm lệch nhỏ cho số.
    var pad = 1
    while (clean.size < 3) {
        val guess = (correct.toIntOrNull()?.plus(pad * 7) ?: 0).toString()
        if (guess != correct && guess !in clean) clean.add(guess)
        pad++
    }
    val all = (clean + correct).shuffled(random)
    counter++
    return Question(
        id = "Q$counter",
        grade = grade,
        topic = topic,
        level = level,
        text = text,
        options = all,
        correctIndex = all.indexOf(correct),
        explanation = explanation
    )
}

/** Đáp án nhiễu cho số nguyên: lệch 1, 10, 100, đổi chỗ, nhầm phép tính. */
fun numberDistractors(correct: Int, random: Random): List<String> {
    val candidates = mutableListOf<Int>()
    candidates += correct + 1
    candidates += correct - 1
    candidates += correct + 10
    candidates += correct - 10
    candidates += correct + 100
    candidates += correct - 100
    candidates += correct * 2
    if (correct >= 10) {
        val s = correct.toString()
        val swapped = (s.drop(1) + s.first()).toIntOrNull()
        if (swapped != null && swapped != correct) candidates += swapped
    }
    return candidates.filter { it != correct && it >= 0 }.distinct().shuffled(random).map { it.toString() }
}

fun formatNumber(value: Double): String {
    return if (value == value.toInt().toDouble()) value.toInt().toString()
    else {
        val rounded = (value * 100).toInt() / 100.0
        if (rounded == rounded.toInt().toDouble()) rounded.toInt().toString() else rounded.toString()
    }
}
