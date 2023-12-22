package com.thejunglegiant.carparkings.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thejunglegiant.carparkings.ui.theme.PrimaryColor
import com.thejunglegiant.carparkings.ui.theme.BlackShade900Color
import com.thejunglegiant.carparkings.ui.theme.WhiteColor

/**
 * Shows a TabLayout
 * @param selectedTabIndex index of selected tab, starts from 0 and 0 by default
 * @param tabs list of tab models were passed title and badge count for each tab
 * @param onTabClicked callback for selected tab index
 * @param divider composable func for custom bottom divider, empty by default
 * @param indicator composable func for custom selected tab indicator, by default implemented from design system
 * @param maxBadgeCounterValue max counter value, 999 will transform anything larger than 999 into 999+
 * */

private val TabLayoutHeight = 48.dp

@Composable
fun CpTabLayout(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    tabs: List<String>,
    onTabClicked: (tabIndex: Int) -> Unit = {},
    divider: @Composable () -> Unit = @Composable {},
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.Indicator(
            color = PrimaryColor,
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    },
) {
    if (tabs.size > 5) {
        throw IllegalArgumentException("Maximum tabs count must be 5 according to design guidlines")
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = indicator,
        divider = divider,
        modifier = modifier
            .height(TabLayoutHeight)
    ) {
        tabs.forEachIndexed { index, tabModel ->
            val isSelected = selectedTabIndex == index

            Tab(
                modifier = Modifier
                    .height(TabLayoutHeight)
                    .background(WhiteColor),
                selected = isSelected,
                onClick = { onTabClicked(index) }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = tabModel,
                        color = if (isSelected) {
                            PrimaryColor
                        } else {
                            BlackShade900Color
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
