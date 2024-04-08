package io.directional.recruitment

import HolidaysApiClient
import IndexesApiClient
import PricesApiClient
import org.json.JSONObject
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class RecruitmentApplication

fun main(args: Array<String>) {
    runApplication<RecruitmentApplication>(*args)
    /*val holidaysApiClient = HolidaysApiClient()
    val IndexesApiClient = IndexesApiClient()


    val holidaysData = holidaysApiClient.getHolidaysData()

    val PricesApiClient = PricesApiClient()
    */
   /* println("지수코드를 입력 해주세요 : ")
        var i: String
        i = readLine()!!.toString() // 입력받는부분

        val indexCodeData = IndexesApiClient.getIndexCode(i)
        val priceData = PricesApiClient.getData(i)
        println("지수 코드: $i ," + "지수 이름 : $indexCodeData , " + "변동률 $priceData")*/
    /* if (priceData != null) {
        print(" 종목코드 : $i " + " 변동률 : $priceData" )
        println()
    } else {
        println("No name found for issue code $i")
    }*/



    /*if (holidaysData != null) {
        println("휴장일 데이터: $holidaysData")
    } else {
        println("휴장일 데이터를 가져오는 데 실패했습니다.")
    }*/

   // val jsonArray = JSONArray(indexData)






    /*println("인덱스를 입력 해주세요 : ")
    var i : String
    i = readLine()!!.toInt().toString() // 입력받는부분

    val indexCodeData = IndexesApiClient.getIndexCode(i)

    if (indexData != null) {
        println("인덱스코드 데이터: $indexCodeData")ㄴ
    } else {
        println("인덱스 데이터를 가져오는 데 실패했습니다.")
    }*/




}


@RestController
class TestController {

    val holidaysApiClient = HolidaysApiClient()
    val IndexesApiClient = IndexesApiClient()


    val holidaysData = holidaysApiClient.getHolidaysData()

    val pricesApiClient = PricesApiClient()

    //단일조회 API
    @PostMapping("/singleView")
    fun singleView(@RequestBody body : String): String {

        /*인터페이스 설계서
        정보 : http://localhost:8080/singleView || http://내 ip:8080/singleView
        데이터 유형 : JSON
        전송방식 : REST API
        DATA 예시 : post { "indexCode" : "5300" }
        성공시 응답 : {"indexCodeData":"KRX 300","priceData":-2.54,"indexCode":"5300"}
        실패시 응답 : "지수 코드를 확인해 주세요."
        */

        val jsonObject = JSONObject(body)
        val indexCode = jsonObject.getString("indexCode")
        val indexCodeData = IndexesApiClient.getIndexCode(indexCode)
        if(indexCodeData == null)
                return "지수 코드를 확인해 주세요."

        val priceData = pricesApiClient.getData(indexCode)
        val reJsonObject = JSONObject()
        reJsonObject.put("indexCodeData", indexCodeData)
        reJsonObject.put("indexCode", indexCode)
        reJsonObject.put("priceData", priceData)


        return reJsonObject.toString()
    }

    //다수조회 API
    @PostMapping("/multipleView")
     fun multipleView(@RequestBody body : String): String? {
        /*인터페이스 설계서
       정보 : http://localhost:8080/multipleView || http://내 ip:8080/multipleView
       데이터 유형 : JSON
       전송방식 : REST API
       DATA 예시 : post { "name" : "KRX 300" }
       성공시 응답 : {"issueCodes":[{"changeRate":-6.28,"issueCode":"101490"},{"changeRate":-4.95,"issueCode":"036930"},{"changeRate":-4.95,"issueCode":"067310"},
       {"changeRate":-4.5,"issueCode":"353200"},{"changeRate":-4.49,"issueCode":"095340"},{"changeRate":-3.85,"issueCode":"195870"},
       {"changeRate":-3.77,"issueCode":"098460"},{"changeRate":-3.23,"issueCode":"074600"},{"changeRate":-2.96,"issueCode":"213420"},
       {"changeRate":-2.83,"issueCode":"376300"},{"changeRate":-2.82,"issueCode":"058970"},{"changeRate":-2.72,"issueCode":"166090"},
       {"changeRate":-2.61,"issueCode":"272290"},{"changeRate":-2.51,"issueCode":"042700"},{"changeRate":-2.33,"issueCode":"090460"},{"changeRate":-2.25,"issueCode":"403870"},
       {"changeRate":-2.04,"issueCode":"039030"},{"changeRate":-1.83,"issueCode":"108320"},{"changeRate":-1.5,"issueCode":"012510"},{"changeRate":-1.31,"issueCode":"000660"},{
       "changeRate":-0.85,"issueCode":"189300"},{"changeRate":-0.2,"issueCode":"084370"},{"changeRate":-0.12,"issueCode":"240810"},{"changeRate":-0.1,"issueCode":"192650"},
       {"changeRate":0.11,"issueCode":"078600"},{"changeRate":0.4,"issueCode":"005070"},{"changeRate":0.49,"issueCode":"034220"},{"changeRate":1.14,"issueCode":"066970"},
       {"changeRate":1.25,"issueCode":"000990"},{"changeRate":2.62,"issueCode":"007660"},{"changeRate":2.92,"issueCode":"420770"},{"changeRate":3.6,"issueCode":"011070"},
       {"changeRate":4.07,"issueCode":"033240"},
       {"changeRate":5.1,"issueCode":"307950"}],"name":"KRX 정보기술","indexCode":"5064"}
       실패시 응답 : "지수명을 확인해주세요"
       */
        val jsonObject = JSONObject(body)
        val name = jsonObject.getString("name")
        val indexCode = IndexesApiClient.getIndexData(name)

        val multiData = pricesApiClient.getMultiData(indexCode)

        //오류리턴
        if(indexCode == "fail"){
            return "지수명을 확인해주세요"
        }

        return multiData


     }



}

