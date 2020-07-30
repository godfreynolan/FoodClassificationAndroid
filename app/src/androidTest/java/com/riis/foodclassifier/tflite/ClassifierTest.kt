package com.riis.foodclassifier.tflite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.riis.foodclassifier.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ClassifierTest {


    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val testContext = InstrumentationRegistry.getInstrumentation().context


    @Test
    fun testClassifyImage(){
        val classifier = Classifier(
            appContext.assets,
            "food_classifier_model.tflite",
            "labels.txt",
            224
        )
        //val testImageStream: InputStream = this.testContext.assets.open("drawable/waffles_7.bmp")
        val testImage:Bitmap = BitmapFactory.decodeResource(appContext.resources, R.drawable.waffles_7)
        val recognitions = classifier.recognizeImage(testImage)
        assertNotEquals(0, recognitions.size)
        assertEquals(recognitions[0].title, "waffles")
    }

}
