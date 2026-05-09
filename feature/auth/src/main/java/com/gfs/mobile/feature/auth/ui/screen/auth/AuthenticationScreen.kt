package com.gfs.mobile.feature.auth.ui.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.feature.auth.R
import com.gfs.mobile.core.domain.model.authorizeusers.AuthorizeUsers
import com.gfs.mobile.core.navigation.DashboardScreen
import com.gfs.mobile.core.ui.component.InputPreview
import com.gfs.mobile.core.ui.component.LoadingDialog
import com.gfs.mobile.core.ui.component.NumPad
import com.gfs.mobile.core.ui.component.Result
import com.gfs.mobile.core.ui.component.ResultDialog
import com.gfs.mobile.core.ui.theme.GFSMaterialTheme
import kotlinx.coroutines.delay

@Composable
fun AuthenticationScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    AuthenticationContent(
        callback = AuthenticationCallback(
            onEnterPIN = { viewModel.setUserInput(it) },
            onClickBackSpace = { viewModel.setBackSpaceAction() },
            onClickSelectAccount = { viewModel.getAuthorizeUsers() },
            onCancelAccountSelection = { viewModel.accountSelectionCanceled() },
            onSelectAccount = { viewModel.setActiveAccount(it) },
            onEmailChanged = { viewModel.onEmailChanged(it) },
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            onClickLoginWithEmail = { viewModel.authenticateWithEmail() }
        ),
        uiState = uiState,
        onToggleMode = { viewModel.toggleLoginMode() }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.checkPreviousUser()
        delay(1000)
        if (uiState.userName.isNullOrEmpty()) {
            viewModel.getAuthorizeUsers()
        }
    }

    if (uiState.hasSixDigit) {
        viewModel.authenticateUser()
    }

    if (uiState.showLoadingDialog) {
        LoadingDialog()
    }

    if (uiState.hasAuthenticated) {
        navController.navigate(DashboardScreen.Dashboard.route)
    }

    if (!uiState.errorMessage.isNullOrEmpty()) {
        ResultDialog(
            result = Result.FAILED,
            message = uiState.errorMessage.orEmpty(),
            buttonText = stringResource(id = R.string.label_close),
            onClickActionButton = { viewModel.dismissErrorDialog() }
        )
    }
}

@Composable
private fun AuthenticationContent(
    callback: AuthenticationCallback,
    uiState: AuthenticationUiState,
    onToggleMode: () -> Unit
) {

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.showAccountSelection) {
                AccountSelection(
                    authorizeUsers = uiState.authorizeUsers,
                    onClickSelectAccount = {
                        callback.onSelectAccount(it)
                    },
                    onCloseBottomSheet = {
                        callback.onCancelAccountSelection()
                    }
                )
            }

            Spacer(modifier = Modifier.weight(.2f))

            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_gfs_logo),
                contentDescription = null)

            Spacer(modifier = Modifier.weight(.1f))

            Crossfade(targetState = uiState.isEmailLogin, label = "LoginMode") { isEmail ->
                if (isEmail) {
                    EmailLoginContent(
                        email = uiState.email,
                        pass = uiState.pass,
                        onEmailChanged = callback.onEmailChanged,
                        onPassChanged = callback.onPasswordChanged,
                        onLoginClick = callback.onClickLoginWithEmail
                    )
                } else {
                    MpinLoginContent(uiState, callback)
                }
            }

            Spacer(modifier = Modifier.weight(.1f))

            TextButton(onClick = onToggleMode) {
                Text(
                    text = if (uiState.isEmailLogin) "Switch to MPIN Login" else "Switch to Admin Login",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun MpinLoginContent(
    uiState: AuthenticationUiState,
    callback: AuthenticationCallback
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AccountChange(
            userName = uiState.userName.orEmpty(),
            onClickSelectAccount = {
                callback.onClickSelectAccount()
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        InputPreview(
            inputLength = uiState.userPIN.length
        )

        Spacer(modifier = Modifier.height(32.dp))

        NumPad(
            modifier = Modifier.height(300.dp),
            enabled = !uiState.hasSixDigit,
            onNumKeyClick = {
                callback.onEnterPIN(it)
            },
            onClickBackSpace = {
                callback.onClickBackSpace()
            }
        )
    }
}

@Composable
private fun EmailLoginContent(
    email: String,
    pass: String,
    onEmailChanged: (String) -> Unit,
    onPassChanged: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = pass,
            onValueChange = onPassChanged,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Composable
private fun AccountChange(
    userName: String,
    onClickSelectAccount: () -> Unit
) {

    var accountVisible by remember { mutableStateOf(true) }
    var userNameVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        delay(300)
        accountVisible = true

        delay(300)
        userNameVisible = true
    }

    AnimatedVisibility(
        visible = accountVisible,
        enter = expandVertically(expandFrom = Alignment.CenterVertically)) {
        Box(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding36))
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.view_padding24))
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.view_padding24))
                )
        ) {

            AnimatedVisibility(
                visible = userNameVisible,
                enter = expandHorizontally(
                    expandFrom = Alignment.CenterHorizontally,
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.view_padding4)),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = userName,
                        onValueChange = { },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = Color.Transparent,
                            disabledTextColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding24)),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_user),
                                tint = MaterialTheme.colorScheme.background,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { onClickSelectAccount() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_swap),
                                    tint = MaterialTheme.colorScheme.background,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountSelection(
    authorizeUsers: List<AuthorizeUsers>,
    onClickSelectAccount: (value: String) -> Unit,
    onCloseBottomSheet: () -> Unit
) {

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        onDismissRequest = { onCloseBottomSheet() },
    ) {

        Column(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.view_padding16))
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.view_padding16)),
                text = stringResource(id = R.string.label_select_account),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )

            authorizeUsers.forEach { authorizeUser ->

                Column {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClickSelectAccount(authorizeUser.userName.orEmpty())
                            }
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        textAlign = TextAlign.Center,
                        text = authorizeUser.userName.orEmpty(),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    HorizontalDivider()
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.view_padding24)))
    }
}

@Composable
@Preview(showBackground = true)
private fun AuthenticationContentPreview() {
    GFSMaterialTheme {
        AuthenticationContent(
            callback = AuthenticationCallback(
                onEnterPIN = { },
                onClickBackSpace = { },
                onClickSelectAccount = { },
                onCancelAccountSelection = { },
                onSelectAccount = { }
            ),
            uiState = AuthenticationUiState(),
            onToggleMode = {}
        )
    }
}
