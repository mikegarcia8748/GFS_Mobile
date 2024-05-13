package com.gfs.mobile.system.ui.screen.milling.worker

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun MillWorkerScreen(
    navController: NavHostController,
    viewModel: MillWorkerViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillWorkerContent(
        callback = MillWorkerCallback(
            onBackPressed = {
                navController.popBackStack()
            }
        ),
        uiState = uiState
    )
}

@Composable
private fun MillWorkerContent(
    callback: MillWorkerCallback,
    uiState: MillWorkerUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_employees)
            )
        }
    ) { paddingValues ->

        LazyColumn(
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
private fun MillWorkerContentPreview() {
    GFSMaterialTheme {
        MillWorkerContent(
            callback = MillWorkerCallback(
                onBackPressed = { }
            ),
            uiState = MillWorkerUiState()
        )
    }
}