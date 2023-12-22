package com.thejunglegiant.carparkings.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Shows a PromTabLayoutWithHorizontalPager
 * @param tabs list of tab models were passed title and badge count for each tab
 * @param onTabClicked callback for selected tab index
 * @param pageContent a composable func for Pager's page content
 * */

@ExperimentalFoundationApi
@Composable
fun CpTabLayoutWithPager(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    onTabClicked: (tabIndex: Int) -> Unit = {},
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabs.size
    }

    LaunchedEffect(selectedTabIndex) {
        if (pagerState.isScrollInProgress.not()) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(modifier = modifier) {
        CpTabLayout(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
            tabs = tabs,
            onTabClicked = {
                selectedTabIndex = it
                onTabClicked(it)
            }
        )
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            pageContent = pageContent
        )
    }
}
