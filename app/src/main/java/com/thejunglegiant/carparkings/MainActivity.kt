package com.thejunglegiant.carparkings

import android.os.Bundle
import androidx.activity.compose.setContent
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.thejunglegiant.carparkings.ui.navigation.RootNode
import com.thejunglegiant.carparkings.ui.theme.CarParkingsTheme

class MainActivity : NodeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
