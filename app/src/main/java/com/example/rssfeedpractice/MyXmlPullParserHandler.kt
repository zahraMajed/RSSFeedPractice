package com.example.rssfeedpractice

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

class MyXmlPullParserHandler {
    var questionList= ArrayList<Question>()
    var questionText=""
    private val ns: String? = null

    fun parse(inputStream: InputStream): ArrayList<Question>{
        inputStream.use {
            inputStream ->
            val parser= Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readRssFeed(parser)
        }//end inputStream block
    }//end parser()

    fun readRssFeed (parser: XmlPullParser):  ArrayList<Question> {
        parser.require(XmlPullParser.START_TAG, ns, "feed")

        while (parser.next() !=  XmlPullParser.END_TAG){

            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            if (parser.name=="entry"){
                parser.require(XmlPullParser.START_TAG, ns, "entry")
                while (parser.next() !=  XmlPullParser.END_TAG){
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }//if
                    when (parser.name) {
                        "title" -> questionText = readtitle(parser)
                        else -> skip(parser)
                    }//when
                    }//while2
                questionList.add(Question(questionText))

            }//end if entry
            else {
                skip(parser)
            }
        }//end while 1
        return questionList
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    fun readtitle(parser: XmlPullParser): String{
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title=readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    fun readText(parser: XmlPullParser): String{
        var result=""
        if (parser.next()== XmlPullParser.TEXT) {
            result=parser.text
            parser.nextTag()
        }
        return result
    }
}