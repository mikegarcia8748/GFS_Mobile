package com.gfs.mobile.system.ui.screen.milling.billing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gfs.mobile.system.R
import com.gfs.mobile.system.extensions.toPhp
import com.gfs.mobile.system.navigation.DashboardScreen
import com.gfs.mobile.system.ui.component.OutlineTextField2
import com.gfs.mobile.system.ui.component.PrimaryButton
import com.gfs.mobile.system.ui.component.SearchTextField
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun MillBillingScreen(
    navController: NavHostController,
    viewModel: MillBillingViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillBillingContent(
        callback = MillBillingCallback(
            onBackPressed = {
                if (uiState.isSearching) {
                    viewModel.onCancelCustomerSearch()
                } else {
                    navController.popBackStack()
                }
            },
            onEnterCustomerName = { viewModel.onSetCustomerName(it) },
            onClickSearch = { viewModel.onClickSearch()},
            onSearchCustomerName = { viewModel.onSearchCustomerName(it)},
            onCancelCustomerSearch = { viewModel.onCancelCustomerSearch()},
            onEnterCustomWeight = { viewModel.onSetCustomWeight(it)},
            onEnter60Kilos = { viewModel.onSet60Kilos(it) },
            onEnter50Kilos = { viewModel.onSet50Kilos(it) },
            onEnter30Kilos = { viewModel.onSet30Kilos(it) },
            onEnter25Kilos = { viewModel.onSet25Kilos(it) },
            onChaffWeight = { viewModel.onSetChaffWeight(it) },
            onClickSaveBilling = {
                viewModel.proceedToPay()
                navController.navigate(DashboardScreen.MillBillingPayment.route)
            }
        ),
        uiState = uiState
    )
}

@Composable
private fun MillBillingContent(
    callback: MillBillingCallback,
    uiState: MillBillingUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.label_rice_mill),
                onBackPressed = { callback.onBackPressed() }
            )
        }
    ) { paddingValues ->

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

            item {
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = R.string.label_billing),
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (uiState.isSearching) {

                item {
                    AnimatedVisibility(visible = uiState.isSearching) {
                        CustomerSearch(
                            value = uiState.searchValue,
                            onValueChange = { callback.onSearchCustomerName(it) },
                            onClickCancel = {
                                callback.onCancelCustomerSearch()
                            }
                        )
                    }
                }
            } else {
                item {
                    OutlineTextField2(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                        hint = stringResource(id = R.string.label_customer_name),
                        value = uiState.customerName,
                        enabled = false,
                        onValueChanged = { },
                        onClick = { callback.onClickSearch() }
                    )
                }

                item {
                    AnimatedVisibility(visible = !uiState.isSearching) {
                        MillBillingWeightEntry(
                            callback = callback,
                            uiState = uiState
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomerSearch(
    value: String,
    onValueChange: (value: String) -> Unit,
    onClickCancel: () -> Unit
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.anim_searching))
    val progress by animateLottieCompositionAsState(composition = composition)

    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchTextField(
            modifier = Modifier,
            value = value,
            onValueChanged = { onValueChange(it) },
            onClickCancel = { onClickCancel() }
        )

        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}

@Composable
private fun MillBillingWeightEntry(
    callback: MillBillingCallback,
    uiState: MillBillingUiState
) {
    Column {

        Text(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.view_padding16)),
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(id = R.string.label_rice_weight),
            fontWeight = FontWeight.SemiBold
        )

        // Custom Rice Weight
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_custom_weight),
            value = uiState.riceCustomWeight.orEmpty(),
            hint = stringResource(id = R.string.label_kilograms),
            priceValue = uiState.riceCustomWeightPrice.toPhp(),
            imeAction = ImeAction.Next,
            onValueChange = { callback.onEnterCustomWeight(it) }
        )

        // 60 Kilograms
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_60_kilos),
            value = uiState.rice60Kilos.orEmpty(),
            hint = stringResource(id = R.string.label_quantity),
            priceValue = uiState.rice60KilosPrice.toPhp(),
            imeAction = ImeAction.Next,
            onValueChange = { callback.onEnter60Kilos(it) }
        )

        // 50 Kilograms
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_50_kilos),
            value = uiState.rice50Kilos.orEmpty(),
            hint = stringResource(id = R.string.label_quantity),
            priceValue = uiState.rice50KilosPrice.toPhp(),
            imeAction = ImeAction.Next,
            onValueChange = { callback.onEnter50Kilos(it) }
        )

        // 30 Kilograms
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_30_kilos),
            value = uiState.rice30Kilos.orEmpty(),
            hint = stringResource(id = R.string.label_quantity),
            priceValue = uiState.rice30KilosPrice.toPhp(),
            imeAction = ImeAction.Next,
            onValueChange = { callback.onEnter30Kilos(it) }
        )

        // 25 Kilograms
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_25_kilos),
            value = uiState.rice25Kilos.orEmpty(),
            hint = stringResource(id = R.string.label_quantity),
            priceValue = uiState.rice25KilosPrice.toPhp(),
            imeAction = ImeAction.Next,
            onValueChange = { callback.onEnter25Kilos(it) }
        )

        TotalDisplay(
            label = stringResource(id = R.string.label_sub_total),
            value = uiState.billSubTotalAmount.toPhp()
        )

        HorizontalDivider()

        Text(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.view_padding16)),
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(id = R.string.label_chaff_weight),
            fontWeight = FontWeight.SemiBold
        )

        // Chaff Weight
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_custom_weight),
            value = uiState.chaffWeight.orEmpty(),
            hint = stringResource(id = R.string.label_kilograms),
            priceValue = uiState.chaffPrice.toPhp(),
            imeAction = ImeAction.Done,
            onValueChange = { callback.onChaffWeight(it) }
        )

        TotalDisplay(
            label = stringResource(id = R.string.label_total),
            value = uiState.billTotalAmount.toPhp()
        )

        PrimaryButton (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
            text = stringResource(id = R.string.sentence_proceed_to_payment),
            onClick = { callback.onClickSaveBilling() }
        )

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.view_padding36)))
    }
}

@Composable
private fun RiceEntry(
    modifier: Modifier,
    label: String,
    value: String,
    priceValue: String,
    hint: String,
    imeAction: ImeAction,
    onValueChange: (value: String) -> Unit
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.view_padding4)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier
                .weight(.3f),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        OutlineTextField2(
            modifier = modifier
                .weight(.5f),
            hint = hint,
            value = value,
            keyboardType = KeyboardType.Number,
            textAlign = TextAlign.Center,
            imeAction = imeAction,
            onValueChanged = {
                if (hint == "Kilograms") {
                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        onValueChange(it)
                    }
                } else {
                    if (it.isDigitsOnly()) {
                        onValueChange(it)
                    }
                }
            }
        )

        OutlineTextField2(
            modifier = modifier
                .weight(.7f),
            hint = stringResource(id = R.string.label_price),
            value = priceValue,
            textAlign = TextAlign.Center,
            enabled = false,
            onValueChanged = { }
        )
    }
}

@Composable
private fun TotalDisplay(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
            .padding(vertical = dimensionResource(id = R.dimen.view_padding4)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier
                .weight(.5f)
                .padding(top = dimensionResource(id = R.dimen.view_padding16)),
            style = MaterialTheme.typography.bodyMedium,
            text = label,
            fontWeight = FontWeight.Medium
        )

        Text(
            modifier = Modifier
                .weight(.5f)
                .padding(top = dimensionResource(id = R.dimen.view_padding16)),
            style = MaterialTheme.typography.bodyLarge,
            text = value,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MillBillingContentPreview() {
    GFSMaterialTheme {
        MillBillingContent(
            callback = MillBillingCallback(
                onBackPressed = { },
                onEnterCustomerName = { },
                onSearchCustomerName = { },
                onClickSearch = { },
                onCancelCustomerSearch = { },
                onEnterCustomWeight = { },
                onEnter60Kilos = { },
                onEnter50Kilos = { },
                onEnter30Kilos = { },
                onEnter25Kilos = { },
                onChaffWeight = { },
                onClickSaveBilling = { }
            ),
            uiState = MillBillingUiState()
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RiceEntryPreview() {
    GFSMaterialTheme {
        RiceEntry(
            modifier = Modifier,
            label = stringResource(id = R.string.label_60_kilos),
            value = "1",
            hint = stringResource(id = R.string.label_quantity),
            priceValue = "1000",
            imeAction = ImeAction.Done,
            onValueChange = { }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TotalDisplayPreview() {
    GFSMaterialTheme {
        TotalDisplay(label = stringResource(id = R.string.label_sub_total), value = "0.0")
    }
}