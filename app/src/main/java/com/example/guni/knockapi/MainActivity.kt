package com.example.guni.knockapi

import android.renderscript.ScriptGroup
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var textViewUrl: TextView
    lateinit var editTextUrl: EditText
    lateinit var buttonGet: Button
    lateinit var editTextResponse: EditText
    lateinit var textViewTime: TextView
    var startTime: Long = 0
    var endTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewUrl = findViewById(R.id.textViewUrl) as TextView
        textViewTime = findViewById(R.id.textViewTime) as TextView
        editTextUrl = findViewById(R.id.editTextUrl) as EditText
        buttonGet = findViewById(R.id.buttonGet) as Button
        editTextResponse = findViewById(R.id.editTextResponse) as EditText

        editTextUrl.setText("https://api.github.com/users/guni973")
    }

    // Buttonの動作確認
    fun onButtonGetTest(view: View) {
        Toast.makeText(this, "click test", Toast.LENGTH_SHORT).show()
    }

    fun onButtonGet(view: View) {
        Thread(Runnable {
            try {
                val url = URL(editTextUrl.text.toString())
                startTime = System.currentTimeMillis()
                val con = url.openConnection() as HttpURLConnection
                val str = InputStreamToString(con.inputStream)

                endTime = System.currentTimeMillis()
                Log.d("HTTP", str)

                runOnUiThread {
                    editTextResponse.setText(str.toString())
                    textViewTime.text = "処理時間: " + (endTime - startTime) + "ms"
                }
            } catch (ex: Exception) {
                println(ex)
            }
        }).start()
    }

    companion object {

        @Throws(IOException::class)
        internal fun InputStreamToString(`is`: InputStream): String {
            val br = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String

            for (line in br.readLines()) {
                sb.append(line)
                System.out.println(line);
            }
            br.close()
            return sb.toString()
        }
    }
}
