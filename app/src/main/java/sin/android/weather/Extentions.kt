package sin.android.weather

import java.text.SimpleDateFormat
import java.util.*

fun getNowTime(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy kk:mm")
    return dateFormat.format(Calendar.getInstance().timeInMillis)
}
fun addTimeString(time: Long): String {
    val dateFormat = SimpleDateFormat("kk:mm:ss")
    return "${dateFormat.format(time)}"
}