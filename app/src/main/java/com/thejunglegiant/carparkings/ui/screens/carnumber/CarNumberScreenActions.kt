package com.thejunglegiant.carparkings.ui.screens.carnumber

sealed class CarNumberScreenActions {
    object GoBack : CarNumberScreenActions()
    object ErrorNumber : CarNumberScreenActions()
}
