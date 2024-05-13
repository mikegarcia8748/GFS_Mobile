package com.gfs.mobile.system.ui.screen.milling.billing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.gfs.mobile.system.data.model.customer.CustomerModel
import com.gfs.mobile.system.extensions.toPhp
import com.gfs.mobile.system.navigation.DashboardScreen
import com.gfs.mobile.system.ui.component.LoadingDialog
import com.gfs.mobile.system.ui.component.OutlineTextField2
import com.gfs.mobile.system.ui.component.PrimaryButton
import com.gfs.mobile.system.ui.component.Result
import com.gfs.mobile.system.ui.component.ResultDialog
import com.gfs.mobile.system.ui.component.SearchTextField
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun MillBillingScreen(
    navController: NavHostController,
    viewModel: MillBillingViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillBillingContent(
        callback = MillBillingCallback(
            onBackPressed = {
                if (uiState.selectCustomer) {
                    viewModel.onCancelSelectCustomer()
                } else {
                    navController.popBackStack()
                }
            },
            onSelectCustomer = { viewModel.onSetCustomer(it) },
            onClickSearch = { viewModel.onClickSelectCustomer() },
            onSearchCustomerName = { viewModel.onSearchCustomerName(it) },
            onCancelCustomerSearch = { viewModel.onCancelSelectCustomer() },
            onAddCustomer = { name, alias -> viewModel.addCustomer(name, alias) },
            onEnterCustomWeight = { viewModel.onSetCustomWeight(it) },
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

    if (uiState.showLoadingDialog) {
        LoadingDialog()
    }

    if (!uiState.errorMessage.isNullOrEmpty()) {
        ResultDialog(
            result = Result.FAILED,
            message = uiState.errorMessage.orEmpty(),
            buttonText = stringResource(id = R.string.label_close),
            onClickActionButton = {
                    viewModel.dismissErrorDialog()
            }
        )
    }
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

            if (uiState.selectCustomer) {

                item {
                    AnimatedVisibility(visible = uiState.selectCustomer) {
                        CustomerSelect(
                            value = uiState.searchValue,
                            customerList = uiState.customerList,
                            isSearching = uiState.isSearching,
                            onValueChange = { callback.onSearchCustomerName(it) },
                            onCustomerSelected = { callback.onSelectCustomer(it) },
                            onClickCancel = {
                                callback.onCancelCustomerSearch()
                            },
                            onAddCustomer = { name, alias ->
                                callback.onAddCustomer(name, alias)
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
                        value = uiState.customer?.name.orEmpty(),
                        enabled = false,
                        onValueChanged = { },
                        onClick = { callback.onClickSearch() }
                    )
                }

                item {
                    AnimatedVisibility(visible = !uiState.selectCustomer) {
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
private fun CustomerSelect(
    customerList: List<CustomerModel>,
    isSearching: Boolean,
    onValueChange: (value: String) -> Unit,
    value: String,
    onClickCancel: () -> Unit,
    onCustomerSelected: (customer: CustomerModel) -> Unit,
    onAddCustomer: (name: String, alias: String) -> Unit,
) {


    var showAddCustomer by remember { mutableStateOf(false) }

    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    if (showAddCustomer) {
        AddCustomerBottomSheet(
            onAddCustomer = { name, alias ->
                onAddCustomer(name, alias)
                showAddCustomer = false
            },
            onCancelAddCustomer = {
                showAddCustomer = false
            }
        )
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

        Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.view_padding8)))

        if (isSearching) {
            repeat(10) {
                CustomerLoading()
            }
        }

        if (customerList.isEmpty()) {

            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.anim_searching))
            val progress by animateLottieCompositionAsState(composition = composition)

            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
            }

            LottieAnimation(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.view_result_dialog)),
                composition = composition,
                progress = { progress }
            )

            PrimaryButton (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                text = stringResource(id = R.string.label_add_customer),
                onClick = {
                    showAddCustomer = true
                }
            )

        } else {
            customerList.forEach { customer ->
                CustomerItem(
                    customer = customer,
                    onClickCustomer = {
                        onCustomerSelected(it)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCustomerBottomSheet(
    onAddCustomer: (name: String, alias: String) -> Unit,
    onCancelAddCustomer: () -> Unit
) {
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        onDismissRequest = {
            onCancelAddCustomer()
        }
    ) {

        var customerName by remember { mutableStateOf("") }
        var customerAlias by remember { mutableStateOf("") }

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

            OutlineTextField2(
                modifier = Modifier,
                hint = stringResource(id = R.string.label_customer_name),
                value = customerName,
                maxLines = 1,
                imeAction = ImeAction.Next,
                onValueChanged = {
                    customerName = it
                }
            )

            OutlineTextField2(
                modifier = Modifier,
                hint = stringResource(id = R.string.label_customer_alias),
                value = customerAlias,
                maxLines = 1,
                imeAction = ImeAction.Done,
                onValueChanged = {
                    customerAlias = it
                }
            )

            PrimaryButton (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                text = stringResource(id = R.string.label_add_customer),
                onClick = {
                    onAddCustomer(customerName, customerAlias)
                }
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.view_padding24)))
        }
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
                onSelectCustomer = { },
                onSearchCustomerName = { },
                onClickSearch = { },
                onCancelCustomerSearch = { },
                onEnterCustomWeight = { },
                onEnter60Kilos = { },
                onEnter50Kilos = { },
                onEnter30Kilos = { },
                onEnter25Kilos = { },
                onChaffWeight = { },
                onClickSaveBilling = { },
                 onAddCustomer = { name, alias -> }
            ),
            uiState = MillBillingUiState()
        )
    }
}

@Composable
private fun CustomerItem(
    customer: CustomerModel,
    onClickCustomer: (customer: CustomerModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.view_padding8))
            .clickable {
                onClickCustomer(customer)
            }
    ) {

        Text(
            modifier = Modifier,
            text = customer.name.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.view_padding4)),
            text = customer.alias.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CustomerLoading() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.view_padding16))
    ) {

        Row {
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.view_padding24))
                    .shimmer()
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding4))
                    )
                    .weight(1f)
            )

            Spacer(
                modifier = Modifier
                    .weight(.5f)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.view_padding4))
        ) {
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.view_padding16))
                    .shimmer()
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding4))
                    )
                    .weight(.5f)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CustomerLoadingPreview() {
    GFSMaterialTheme {
        CustomerLoading()
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun RiceEntryPreview() {
//    GFSMaterialTheme {
//        RiceEntry(
//            modifier = Modifier,
//            label = stringResource(id = R.string.label_60_kilos),
//            value = "1",
//            hint = stringResource(id = R.string.label_quantity),
//            priceValue = "1000",
//            imeAction = ImeAction.Done,
//            onValueChange = { }
//        )
//    }
//}

//@Composable
//@Preview(showBackground = true)
//private fun TotalDisplayPreview() {
//    GFSMaterialTheme {
//        TotalDisplay(label = stringResource(id = R.string.label_sub_total), value = "0.0")
//    }
//}