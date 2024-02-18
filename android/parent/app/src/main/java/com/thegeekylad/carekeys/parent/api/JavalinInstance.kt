package com.thegeekylad.carekeys.parent.api

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.thegeekylad.carekeys.parent.util.Constants
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel
import io.javalin.Javalin

class JavalinInstance(private val viewModel: AppViewModel, private val context: Context) : DefaultLifecycleObserver {

    private lateinit var app: Javalin

    private fun start() {
        app = Javalin.create { config ->
            config.enableCorsForAllOrigins()
        }.start(Constants.PORT.toInt())

        // register endpoints
        val api = Api(app, viewModel, context)
        api.onText()
    }

    private fun stop() {
        app.stop()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stop()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        start()
    }
}