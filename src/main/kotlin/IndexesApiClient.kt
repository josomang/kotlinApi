
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class IndexesApiClient {
    private val INDEXES_API_URL = "https://api-recruit.directional.io/v1/indexes"


    fun getIndexData(targetName : String) : String{


            val url = URL("https://api-recruit.directional.io/v1/indexes?page=0&size=2000")

            // HTTP 연결 설정
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            // API 응답 읽기
        val responseCode = conn.responseCode
        val responseData = conn.inputStream.bufferedReader().use { it.readText() }

        val jsonObject = JSONObject(responseData)
        val contentArray = jsonObject.getJSONArray("content")



        // 각 요소들을 추출하여 출력
        for (i in 0 until contentArray.length()) {
            val contentObject = contentArray.getJSONObject(i)
            val indexCode = contentObject.getString("indexCode")
            val name = contentObject.getString("name")

            if (name == targetName) {
                return indexCode
            }
        }

        return "fail"

    }

    fun getIndexCode(indexCode: String): String? {


            val url = URL("https://api-recruit.directional.io/v1/indexes/$indexCode")
            // HTTP 연결 설정
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            val responseCode = conn.responseCode

        if (responseCode == HttpURLConnection.HTTP_OK){
            val responseData = conn.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(responseData)

            val indexCode = jsonObject.getString("indexCode")
            val name = jsonObject.getString("name")



            return name
        }
        else {
            return null
        }

    }

}