package com.maxst.stt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

	private lateinit var voiceIntent: Intent
	private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		voiceIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
			putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
			putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
		}

		val speechButton = findViewById<TextView>(R.id.speechButton)
		speechButton.setOnClickListener {
			speechRecognizer.startListening(voiceIntent)
		}

		speechRecognizer.setRecognitionListener(object : RecognitionListener {
			override fun onReadyForSpeech(params: Bundle?) {
				Log.d(TAG, "onReadyForSpeech")
			}

			override fun onRmsChanged(rmsdB: Float) {
				Log.d(TAG, "onRmsChanged")
			}

			override fun onBufferReceived(buffer: ByteArray?) {
				Log.d(TAG, "onBufferReceived")
			}

			override fun onPartialResults(partialResults: Bundle?) {
				Log.d(TAG, "onPartialResults")
			}

			override fun onEvent(eventType: Int, params: Bundle?) {
				Log.d(TAG, "onEvent")
			}

			override fun onBeginningOfSpeech() {
				Log.d(TAG, "onBeginningOfSpeech")
			}

			override fun onEndOfSpeech() {
				Log.d(TAG, "onEndOfSpeech")
			}

			override fun onError(error: Int) {
				Log.d(TAG, "onError")
			}

			override fun onResults(results: Bundle?) {
				Log.d(TAG, "onResults")
				val key = SpeechRecognizer.RESULTS_RECOGNITION
				if (results != null && !results.isEmpty) {
					results.getStringArrayList(key)?.let {
						val rs = arrayOfNulls<String>(it.size)
						it.toArray(rs)
						Toast.makeText(this@MainActivity, rs[0], Toast.LENGTH_SHORT).show()
					}
				}
				//speechRecognizer.startListening(voiceIntent) //음성인식이 계속 되는 구문이니 필요에 맞게 쓰시길 바람
			}
		})
	}
}
