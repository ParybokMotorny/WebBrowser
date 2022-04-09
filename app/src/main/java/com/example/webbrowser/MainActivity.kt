package com.example.webbrowser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.webbrowser.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ok.setOnClickListener {
            try {
                val uri = URL(binding.url.text.toString())
                Thread {
                    var urlConnection: HttpsURLConnection? = null
                    try {
                        urlConnection = uri.openConnection() as HttpsURLConnection
                        urlConnection.requestMethod =
                            "GET" // установка метода получения данных -GET
                        urlConnection.readTimeout =
                            10000 // установка таймаута - 10 000 миллисекунд
                        val input =
                            BufferedReader(InputStreamReader(urlConnection.inputStream)) // читаем  данные
                        val result = input.readLine()
                        runOnUiThread {
                            binding.browse.loadData(result, "text/html; charset=utf-8", "utf-8")
                        }

                    } catch (e: Exception) {
                        Log.e(TAG, "Fail connection", e)
                        e.printStackTrace()
                    } finally {
                        urlConnection?.disconnect()
                    }
                }.start()
            } catch (e: MalformedURLException) {
                Log.e(TAG, "Fail URI", e)
                e.printStackTrace()
            }
        }
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        try {
            val uri = URL(binding.url.text.toString())
            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod =
                        "GET" // установка метода получения данных -GET
                    urlConnection.readTimeout =
                        10000 // установка таймаута - 10 000 миллисекунд
                    val in1 =
                        BufferedReader(InputStreamReader(urlConnection.inputStream)) // читаем  данные
                    val result = in1.readLine()
                    runOnUiThread {
                        binding.browse.loadData(result, "text/html; charset=utf-8", "utf-8")
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e(TAG, "Fail URI", e)
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "WebBrowser"
    }
}
