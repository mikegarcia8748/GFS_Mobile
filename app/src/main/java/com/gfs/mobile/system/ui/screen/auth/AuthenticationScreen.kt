package com.gfs.mobile.system.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.navigation.DashboardScreen
import com.gfs.mobile.system.ui.component.InputPreview
import com.gfs.mobile.system.ui.component.LoadingDialog
import com.gfs.mobile.system.ui.component.NumPad
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun AuthenticationScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    AuthenticationContent(
        callback = AuthenticationCallback(
            onEnterPIN = { viewModel.setUserInput(it.toString()) },
            onClickBackSpace = { viewModel.setBackSpaceAction() }
        ),
        uiState = uiState
    )

    if (uiState.hasSixDigit) {
        viewModel.authenticateUser()
    }

    if (uiState.showLoadingDialog) {
        LoadingDialog()
    }

    if (uiState.hasAuthenticated) {
        navController.navigate(DashboardScreen.Dashboard.route)
    }
}

@Composable
private fun AuthenticationContent(
    callback: AuthenticationCallback,
    uiState: AuthenticationUiState
) {
    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16)),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(.5f))

            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_gfs_logo),
                contentDescription = null)

            Spacer(modifier = Modifier.weight(.1f))

            InputPreview(
                inputLength = uiState.userPIN.length
            )

            Spacer(modifier = Modifier.weight(.05f))

            NumPad(
                modifier = Modifier
                    .weight(1f),
                enabled = !uiState.hasSixDigit,
                onNumKeyClick = {
                    callback.onEnterPIN(it)
                },
                onClickBackSpace = {
                    callback.onClickBackSpace()
                }
            )

            Spacer(modifier = Modifier.weight(.05f))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AuthenticationContentPreview() {
    GFSMaterialTheme {
        AuthenticationContent(
            callback = AuthenticationCallback(
                onEnterPIN = { },
                onClickBackSpace = { }
            ),
            uiState = AuthenticationUiState()
        )
    }
}