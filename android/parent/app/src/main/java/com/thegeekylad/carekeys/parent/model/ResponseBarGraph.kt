package com.thegeekylad.carekeys.parent.model

import android.util.Log
import com.google.gson.JsonArray
import com.thegeekylad.carekeys.parent.util.Constants.Companion.TAG
import org.json.JSONArray
import org.json.JSONObject


enum class BarGraphDataPoint {
    DEPRESSION_INTENSITY,
    VIOLENCE_INTENSITY,
    SELF_HARM_INTENSITY,
    SEXUAL_MISCONDUCT_INTENSITY,
    EQ;

    companion object {
        fun getIndex(barGraphDataPoint: BarGraphDataPoint) =
            when(barGraphDataPoint) {
                DEPRESSION_INTENSITY -> 1
                VIOLENCE_INTENSITY -> 2
                SELF_HARM_INTENSITY -> 3
                SEXUAL_MISCONDUCT_INTENSITY -> 4
                EQ -> 5
            }
    }
}

data class ResponseBarGraph(val data: Array<Array<String>>) {
    override fun toString(): String {
        val sb = StringBuffer()
        data.forEach {
            sb.append(it[0]).appendLine()
        }
        return sb.toString()
    }

    companion object {
        fun parse(body: String): MutableList<MutableList<String>> {
            val op: MutableList<MutableList<String>> = mutableListOf()

            val obj = JSONObject(body)
            val arr = obj.getJSONArray("data")

            for (i in 0..arr.length() - 1) {
                val subArr = arr[i] as JSONArray
                val subList = mutableListOf<String>()
                for (j in 0..subArr.length() - 1)
                    subList.add(subArr[j].toString())
                op.add(subList)
            }

            return op
        }
    }
}