package com.gfs.mobile.system.ui.screen.milling.customer

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
fun MillCustomerScreen(
    navController: NavHostController,
    viewModel: MillCustomerViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillCustomerContent(
        callback = MillCustomerCallback(
            onBackPressed = { navController.popBackStack() }
        ),
        uiState = MillCustomerUiState()
    )
}

@Composable
private fun MillCustomerContent(
    callback: MillCustomerCallback,
    uiState: MillCustomerUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_customer)
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
private fun MillCustomerContentPreview() {
    GFSMaterialTheme {
        MillCustomerContent(
            callback = MillCustomerCallback(
                onBackPressed = { }
            ),
            uiState = MillCustomerUiState()
        )
    }
}