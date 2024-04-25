package com.gfs.mobile.system.ui.screen.milling.inventory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun MillInventoryScreen(
    navController: NavHostController,
    viewModel: MillInventoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillInventoryContent(
        callback = MillInventoryCallback(
            onBackPressed = {
                navController.popBackStack()
            }
        ),
        uiState = uiState
    )
}

@Composable
private fun MillInventoryContent(
    callback: MillInventoryCallback,
    uiState: MillInventoryUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_inventory)
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {


        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MillInventoryContentPreview() {
    GFSMaterialTheme {
        MillInventoryContent(
            callback = MillInventoryCallback(
                onBackPressed = { }
            ),
            uiState = MillInventoryUiState()
        )
    }
}