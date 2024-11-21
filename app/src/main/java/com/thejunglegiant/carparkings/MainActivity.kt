package com.thejunglegiant.carparkings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.thejunglegiant.carparkings.data.NotificationWorker
import com.thejunglegiant.carparkings.ui.navigation.RootNode
import com.thejunglegiant.carparkings.ui.theme.CarParkingsTheme
import java.util.concurrent.TimeUnit

class MainActivity : NodeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(1, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)

        setContent {
            CarParkingsTheme {
                NodeHost(integrationPoint = appyxIntegrationPoint) {
                    RootNode(
                        buildContext = it,
                    )
                }
            }
        }
    }
}
