package at.sfraungruber.assetbrowser.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TabbedComponent(vararg tabs: Tab) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spaces.ScreenPadding),
            horizontalArrangement = Arrangement.spacedBy(Spaces.Medium),
        ) {
            tabs.forEachIndexed { tabIndex, tabName ->
                FilterChip(
                    selected = selectedTabIndex == tabIndex,
                    onClick = { selectedTabIndex = tabIndex },
                    label = { Text(tabName.title) },
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = Spaces.Medium),
        )

        tabs[selectedTabIndex].content()
    }
}

data class Tab(
    val title: String,
    val content: @Composable () -> Unit,
)
