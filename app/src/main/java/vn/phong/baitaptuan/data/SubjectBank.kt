package vn.phong.baitaptuan.data

import vn.phong.baitaptuan.model.ExamKind
import vn.phong.baitaptuan.model.Question
import vn.phong.baitaptuan.model.Subject
import vn.phong.baitaptuan.model.WeekDef
import kotlin.random.Random

/**
 * Ngân hàng nội dung cho các môn ngoài Toán (Tiếng Việt, Tiếng Anh, Tự nhiên và
 * Xã hội / Khoa học, Lịch sử và Địa lí).
 *
 * Mỗi môn, mỗi lớp là danh sách [Topic]: một chủ đề phủ một khoảng tuần. Một
 * chủ đề gồm 3 loại nội dung:
 *  - [BankItem]: câu cố định (câu hỏi, đáp án đúng, các đáp án nhiễu, lời giải).
 *  - [PairSet]: danh sách cặp (khóa - giá trị). Mỗi cặp sinh được 2 câu: hỏi
 *    giá trị từ khóa và hỏi khóa từ giá trị, đáp án nhiễu lấy từ các cặp khác.
 *  - [CatSet]: các nhóm từ. Hỏi "từ nào thuộc nhóm X", đáp án nhiễu lấy từ
 *    các nhóm khác.
 * Mỗi lượt làm bài lấy ngẫu nhiên và xáo thứ tự câu, thứ tự đáp án; đề tuần
 * ưu tiên nội dung của chính tuần đó, thiếu thì lấy bổ sung từ các tuần khác
 * của cùng lớp (ôn tập).
 */

internal class BankItem(val q: String, val correct: String, val wrong: List<String>, val expl: String)

internal class PairSet(
    val forward: String,          // có {k}; đáp án là giá trị
    val backward: String?,        // có {v}; đáp án là khóa, null = không hỏi chiều ngược
    val pairs: List<Pair<String, String>>,
    val explTemplate: String      // có {k} và {v}
)

internal class CatSet(
    val template: String,         // có {c}: tên nhóm; đáp án là một từ thuộc nhóm
    val cats: List<Pair<String, List<String>>>,
    val askCategory: String?      // có {w}: hỏi từ này thuộc nhóm nào; null = không hỏi
)

internal class Topic(
    val grade: Int,
    val weeks: IntRange,
    val title: String,
    val items: List<BankItem> = emptyList(),
    val pairSets: List<PairSet> = emptyList(),
    val catSets: List<CatSet> = emptyList()
)

// ---------- Cú pháp khai báo gọn ----------

/** Mỗi dòng: câu hỏi | đáp án đúng | sai 1 | sai 2 | sai 3 [|| lời giải]. */
internal fun items(block: String): List<BankItem> = block.lines()
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") }
    .mapNotNull { line ->
        val halves = line.split("||")
        val f = halves[0].split("|").map { it.trim() }
        if (f.size < 5) null
        else BankItem(f[0].replace("\\n", "\n"), f[1], f.drop(2).filter { it.isNotEmpty() }, halves.getOrNull(1)?.trim() ?: "")
    }

/** Mỗi dòng: từ viết đúng | sai 1 | sai 2 | sai 3. */
internal fun spell(block: String, question: String = "Từ nào viết đúng chính tả?"): List<BankItem> = block.lines()
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") }
    .mapNotNull { line ->
        val f = line.split("|").map { it.trim() }
        if (f.size < 4) null else BankItem(question, f[0], f.drop(1), "Viết đúng là “${f[0]}”.")
    }

/** Mỗi dòng: khóa | giá trị. */
internal fun pairList(block: String): List<Pair<String, String>> = block.lines()
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") }
    .mapNotNull { line ->
        val f = line.split("|").map { it.trim() }
        if (f.size < 2) null else f[0] to f[1]
    }

/** Mỗi dòng: tên nhóm | từ 1, từ 2, từ 3... */
internal fun catList(block: String): List<Pair<String, List<String>>> = block.lines()
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") }
    .mapNotNull { line ->
        val f = line.split("|").map { it.trim() }
        if (f.size < 2) null else f[0] to f[1].split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }

// ---------- Tra cứu chủ đề ----------

private fun allTopics(subject: Subject): List<Topic> = when (subject) {
    Subject.TIENG_VIET -> tiengVietTopics()
    Subject.TIENG_ANH -> tiengAnhTopics()
    Subject.TU_NHIEN -> tuNhienTopics()
    Subject.LICH_SU_DIA_LI -> lichSuDiaLiTopics()
    Subject.TOAN -> emptyList()
}

private val cache = HashMap<Subject, List<Topic>>()

private fun topicsOf(subject: Subject, grade: Int): List<Topic> {
    val all = synchronized(cache) { cache.getOrPut(subject) { allTopics(subject) } }
    return all.filter { it.grade == grade }
}

/** Định nghĩa tuần cho môn ngoài Toán. Tuần nào cũng có, tuần không có chủ đề riêng là ôn tập. */
fun weekDefFor(subject: Subject, grade: Int, week: Int): WeekDef {
    if (subject == Subject.TOAN) return weekDef(grade, week)
    val t = topicsOf(subject, grade).firstOrNull { week in it.weeks }
    val title = t?.title ?: "Ôn tập và luyện tập"
    return WeekDef(grade, week, title, false, emptyList(), subject)
}

// ---------- Sinh câu ----------

private fun makeQuestion(random: Random, form: String, text: String, correct: String, wrongs: List<String>, expl: String): Question? {
    val clean = wrongs.filter { it != correct && it.isNotBlank() }.distinct()
    if (correct.isBlank() || clean.size < 3) return null
    val opts = (listOf(correct) + clean.shuffled(random).take(3)).shuffled(random)
    return Question(text, opts, opts.indexOf(correct), expl, form)
}

private fun candidates(random: Random, topics: List<Topic>): List<Question> {
    val out = mutableListOf<Question>()
    for (t in topics) {
        for (item in t.items) {
            makeQuestion(random, "BANK", item.q, item.correct, item.wrong, item.expl.ifBlank { "Đáp án đúng là “${item.correct}”." })?.let(out::add)
        }
        for (ps in t.pairSets) {
            for ((k, v) in ps.pairs) {
                val expl = ps.explTemplate.replace("{k}", k).replace("{v}", v)
                val otherV = ps.pairs.map { it.second }.filter { it != v }
                makeQuestion(random, "PAIR", ps.forward.replace("{k}", k), v, otherV, expl)?.let(out::add)
                if (ps.backward != null) {
                    val otherK = ps.pairs.map { it.first }.filter { it != k }
                    makeQuestion(random, "PAIR", ps.backward.replace("{v}", v), k, otherK, expl)?.let(out::add)
                }
            }
        }
        for (cs in t.catSets) {
            for ((cat, words) in cs.cats) {
                val others = cs.cats.filter { it.first != cat }.flatMap { it.second }
                for (w in words) {
                    makeQuestion(random, "CAT", cs.template.replace("{c}", cat), w, others, "“$w” là $cat.")?.let(out::add)
                    if (cs.askCategory != null) {
                        val otherCats = cs.cats.map { it.first }.filter { it != cat }
                        makeQuestion(random, "CAT", cs.askCategory.replace("{w}", w), cat, otherCats, "“$w” thuộc nhóm: $cat.")?.let(out::add)
                    }
                }
            }
        }
    }
    return out
}

private fun newRandom(): Random = Random(System.nanoTime() xor System.currentTimeMillis())

private fun pick(primary: List<Question>, review: List<Question>, count: Int, random: Random): List<Question> {
    val seen = HashSet<String>()
    fun key(q: Question) = q.text + "|" + q.options.sorted().joinToString(",")
    val out = mutableListOf<Question>()
    for (q in primary.shuffled(random) + review.shuffled(random)) {
        if (out.size >= count) break
        if (q.options.distinct().size != 4) continue
        if (seen.add(key(q))) out += q
    }
    return out.shuffled(random)
}

/** Đề tuần: nội dung của tuần đó trước, thiếu thì bổ sung bằng các chủ đề khác cùng lớp. */
fun generateSubjectWeekly(subject: Subject, grade: Int, week: Int, count: Int = 40): List<Question> {
    val random = newRandom()
    val topics = topicsOf(subject, grade)
    val main = topics.filter { week in it.weeks }
    val primary = candidates(random, main)
    val earlier = topics.filter { it !in main && it.weeks.first < week }
    val review = candidates(random, earlier) + candidates(random, topics.filter { it !in main && it !in earlier })
    return pick(primary, review, count, random)
}

/** Đề kỳ: lấy từ tất cả chủ đề có tuần nằm trong phạm vi đề. */
fun generateSubjectExam(subject: Subject, grade: Int, exam: ExamKind, count: Int = 40): List<Question> {
    val random = newRandom()
    val topics = topicsOf(subject, grade)
    val inRange = topics.filter { it.weeks.first <= exam.weeks.last && it.weeks.last >= exam.weeks.first }
    val primary = candidates(random, inRange)
    val review = candidates(random, topics.filter { it !in inRange })
    return pick(primary, review, count, random)
}
