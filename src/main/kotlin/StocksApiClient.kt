import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class StocksApiClient {

    fun getData(targetIssueCode : String) : Double?{


        val url = URL("https://api-recruit.directional.io/v1/stocks?page=0&size=2000")

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
            val issueCode = contentObject.getString("issueCode")
            val changeRate = contentObject.getDouble("changeRate")



            if (issueCode == targetIssueCode) {
                return changeRate
            }
        }

        return null

    }

}