package com.example.rssfeedpractice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    var questionList=ArrayList<Question>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FetchQuestions().execute()
    }//end onCreate()

    private  inner class FetchQuestions : AsyncTask<Void, Void, ArrayList<Question>>(){
        val parser = MyXmlPullParserHandler()
        override fun doInBackground(vararg params: Void?): ArrayList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            questionList= urlConnection.getInputStream()?.let {
                parser.parse(it)
            }!!
            return questionList
        }

        override fun onPostExecute(result: ArrayList<Question>?) {
            super.onPostExecute(result)
            rv_main.adapter=RecyclerAdapter(questionList)
            rv_main.layoutManager= LinearLayoutManager(this@MainActivity)
        }
    }

}//end class