import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PricesApiClient {
    fun getData(indexCode: String) : Double?{


        val url1 = URL("https://api-recruit.directional.io/v1/indexes/$indexCode")
        val url2 = URL("https://api-recruit.directional.io/v1/prices?page=0&size=2000")


        val conn1 = url1.openConnection() as HttpURLConnection
        val conn2 = url2.openConnection() as HttpURLConnection
        conn1.requestMethod = "GET"
        conn2.requestMethod = "GET"

        // API 응답 읽기
        val responseCode1 = conn1.responseCode
        val responseCode2 = conn2.responseCode
        val responseData1 = conn1.inputStream.bufferedReader().use { it.readText() }
        val responseData2 = conn2.inputStream.bufferedReader().use { it.readText() }


        val jsonObject1 = JSONObject(responseData1)
        val jsonObject2 = JSONObject(responseData2)

        val issueCodes1 = jsonObject1.getJSONArray("issueCodes")
        //println(issueCodes1)
        val contentArray2 = jsonObject2.getJSONArray("content")



        // 각 요소들을 추출하여 출력
        for (i in 0 until issueCodes1.length()) {
            val issueCode1 = issueCodes1.getString(i)

            for (j in 0 until contentArray2.length()) {
                val contentObject2 = contentArray2.getJSONObject(j)
                val issueCode2 = contentObject2.getString("issueCode")

                if (issueCode1 == issueCode2) {
                    val changeRate = contentObject2.getDouble("changeRate")
                  // println("issueCode $issueCode1, changeRate is $changeRate")
                    return changeRate
                }
            }
        }


        return null

    }
    fun getMultiData(indexCode: String) : String?{

        if(indexCode == "fail")
            return "fail"

        val url1 = URL("https://api-recruit.directional.io/v1/indexes/$indexCode")
        val url2 = URL("https://api-recruit.directional.io/v1/prices?page=0&size=2000")

        // HTTP 연결 설정
        val conn1 = url1.openConnection() as HttpURLConnection
        val conn2 = url2.openConnection() as HttpURLConnection
        conn1.requestMethod = "GET"
        conn2.requestMethod = "GET"

        // API 응답 읽기
        val responseCode1 = conn1.responseCode
        val responseCode2 = conn2.responseCode
        val responseData1 = conn1.inputStream.bufferedReader().use { it.readText() }
        val responseData2 = conn2.inputStream.bufferedReader().use { it.readText() }

        val json1 = JSONObject(responseData1)
        val issueCodes1 = json1.getJSONArray("issueCodes")

        val json2 = JSONObject(responseData2)
        val content2 = json2.getJSONArray("content")

        val mergedIssueCodes = JSONArray()


        // 첫 번째 데이터의 issueCodes를 순회하면서 두 번째 데이터와 비교
        for (i in 0 until issueCodes1.length()) {
            val issueCode = issueCodes1.getString(i)

            // 두 번째 데이터에서 해당 issueCode를 찾음
            for (j in 0 until content2.length()) {
                val issueData = content2.getJSONObject(j)
                if (issueData.getString("issueCode") == issueCode) {
                    // 해당 issueCode에 대한 정보를 새로운 JSON 객체에 추가
                    val mergedIssueData = JSONObject()
                    mergedIssueData.put("issueCode", issueCode)
                    mergedIssueData.put("changeRate", issueData.getDouble("changeRate")) // 예시에서 changeRate만 가져옴
                    mergedIssueCodes.put(mergedIssueData)
                    break
                }
            }
        }

        // changeRate를 기준으로 오름차순 정렬
        val sortedArray = JSONArray()
        val sortedList = mutableListOf<JSONObject>()
        for (i in 0 until mergedIssueCodes.length()) {
            sortedList.add(mergedIssueCodes.getJSONObject(i))
        }
        sortedList.sortBy { it.getDouble("changeRate") }
        sortedList.forEach { sortedArray.put(it) }

        // 결과 JSON 객체 생성
        val result = JSONObject()
        result.put("indexCode", json1.getString("indexCode"))
        result.put("name", json1.getString("name"))
        result.put("issueCodes", sortedArray)
        //println(result)

        return result.toString()




    }

}