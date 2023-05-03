package com.fcascan.parcial1.helpers

import android.content.Context
import org.json.JSONArray
import java.io.BufferedReader

class JsonHelper {
    companion object {
        fun loadJSONFromAsset(context: Context, file: Int): JSONArray {
            val inputStream = context.resources.openRawResource(file)

            BufferedReader(inputStream.reader()).use { reader ->
                return JSONArray(reader.readText())
            }
        }
    }
}