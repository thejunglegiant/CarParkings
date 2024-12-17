package com.thejunglegiant.carparkings.ui.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.operationstrategies.QueueOperations
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.thejunglegiant.carparkings.data.models.ParkingSpotDTO
import com.thejunglegiant.carparkings.ui.screens.carnumber.CarNumberScreen
import com.thejunglegiant.carparkings.ui.screens.login.LoginPage
import com.thejunglegiant.carparkings.ui.screens.main.MainScreen
import com.thejunglegiant.carparkings.ui.screens.map.MapScreen
import com.thejunglegiant.carparkings.ui.screens.pay.PayScreen
import com.thejunglegiant.carparkings.ui.screens.profile.ProfileScreen
import com.thejunglegiant.carparkings.ui.screens.profile.UserEditScreen
import kotlinx.parcelize.Parcelize

class RootNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        initialElement = NavTarget.Main,
        savedStateMap = buildContext.savedStateMap,
        operationStrategy = QueueOperations(),
    ),
) : ParentNode<RootNode.NavTarget>(
    navModel = backStack,
    buildContext = buildContext,
) {

    sealed class NavTarget : Parcelable {
        @Parcelize
        object Main : NavTarget()
        @Parcelize
        object Map : NavTarget()
        @Parcelize
        object Pay : NavTarget()
        @Parcelize
        object CarNumber : NavTarget()
        @Parcelize
        object Profile : NavTarget()
        @Parcelize
        object UserEdit : NavTarget()
        @Parcelize
        object Login : NavTarget()
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is NavTarget.Main -> node(buildContext) {
                MainScreen(
                    onChooseParkingClicked = {
                        backStack.push(NavTarget.Map)
                    },
                    onPayClicked = {
                        backStack.singleTop(NavTarget.Pay)
                    },
                    onAddNumberClicked = {
                        backStack.singleTop(NavTarget.CarNumber)
                    },
                    onProfileClicked = {
                        backStack.push(NavTarget.Profile)
                    },
                )
            }
            NavTarget.Map -> node(buildContext) {
                MapScreen(
                    onParkingSpotSelected = {
                        backStack.singleTop(
                            NavTarget.Main
                        )
                    }
                )
            }
            NavTarget.Pay -> node(buildContext) {
                PayScreen()
            }
            NavTarget.CarNumber -> node(buildContext) {
                CarNumberScreen(
                    onBackClicked = {
                        backStack.pop()
                    }
                )
            }
            NavTarget.Profile -> node(buildContext) {
                ProfileScreen(
                    onEditClicked = {
                        backStack.push(NavTarget.UserEdit)
                    },
                    onExitClicked = {
                        backStack.push(NavTarget.Login)
                    }
                )
            }
            NavTarget.UserEdit -> node(buildContext) {
                UserEditScreen(
                    name = "Олексій",
                    surname = "Морозов",
                    email = "oleksii.m02@gmail.com",
                    gender = "Чоловік", {},{},{},{},{backStack.pop()}
                )
            }
            NavTarget.Login -> node(buildContext) {
                LoginPage()
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackFader()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                children<NavTarget> { child ->
                    child()
                }
            }
        }
    }
}
