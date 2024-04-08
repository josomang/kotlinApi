import java.net.HttpURLConnection
import java.net.URL

class HolidaysApiClient {

    private val HOLIDAYS_API_URL = "/v1/holidays"

    fun getHolidaysData(): String? {
        return try {
            // 휴장일 API
            val url = URL("https://api-recruit.directional.io/v1/holidays")

            // HTTP 연결 설정
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            // API 응답 읽기
            val inStream = conn.inputStream
            inStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}