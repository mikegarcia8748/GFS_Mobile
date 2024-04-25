package com.gfs.mobile.system.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    SettingsContent(
        callback = SettingsCallback(
            onBackPressed = {
                navController.popBackStack()
            }
        )
    )
}

@Composable
private fun SettingsContent(
    callback: SettingsCallback
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_settings)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsContentPreview() {
    GFSMaterialTheme {
        SettingsContent(
            callback = SettingsCallback(
                onBackPressed = { }
            )
        )
    }
}