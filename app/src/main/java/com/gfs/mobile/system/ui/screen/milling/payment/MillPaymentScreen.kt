package com.gfs.mobile.system.ui.screen.milling.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.extensions.formatAmountWithCurrency
import com.gfs.mobile.system.extensions.toPhp
import com.gfs.mobile.system.ui.component.NumPad
import com.gfs.mobile.system.ui.component.OutlineTextField2
import com.gfs.mobile.system.ui.component.PrimaryButton
import com.gfs.mobile.system.ui.component.Result
import com.gfs.mobile.system.ui.component.ResultDialog
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import kotlinx.coroutines.delay

@Composable
fun MillPaymentScreen(
    navController: NavHostController,
    viewModel: MillPaymentViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillPaymentContent(
        callback = MillPaymentCallback(
            onBackPressed = { navController.popBackStack() },
            onEnterAmountPaid = { viewModel.setInputAmount(it) },
            onClickBackSpace = { viewModel.clearInputAmount() },
            onSaveTransaction = { viewModel.saveTransaction() }
        ),
        uiState = uiState
    )

    if (uiState.transactionSave) {
        ResultDialog(
            result = Result.SUCCESS,
            message = stringResource(id = R.string.sentence_transaction_save),
            buttonText = stringResource(id = R.string.sentence_back_to_billing),
            onClickActionButton = {
                viewModel.dismissSuccessDialog()
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun MillPaymentContent(
    callback: MillPaymentCallback,
    uiState: MillPaymentUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_payment)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding16))
                )
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                text = stringResource(id = R.string.sentence_amount_to_pay),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )

            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                text = uiState.amountToPay?.toPhp().toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )

            OutlineTextField2(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding24)),
                hint = "Enter Amount Paid",
                value = uiState.formattedAmountPaid.formatAmountWithCurrency(),
                keyboardType = KeyboardType.Number,
                textAlign = TextAlign.Center,
                enabled = false,
                onValueChanged = { }
            )

            Row(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding16))
                    .padding(horizontal = dimensionResource(id = R.dimen.view_padding16)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.label_customer_change),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = uiState.amountChange?.toPhp().orEmpty(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            NumPad(
                enabledPeriod = true,
                onNumKeyClick = { callback.onEnterAmountPaid(it) },
                onClickBackSpace = { callback.onClickBackSpace() }
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                text = stringResource(id = R.string.label_pay),
                onClick = { callback.onSaveTransaction() }
            )
        }
        
    }
}

@Composable
@Preview(showBackground = true)
private fun MillPaymentContentPreview() {
    GFSMaterialTheme {
        MillPaymentContent(
            callback = MillPaymentCallback(
                onBackPressed = { },
                onEnterAmountPaid = { },
                onClickBackSpace = { },
                onSaveTransaction = { }
            ),
            uiState = MillPaymentUiState()
        )
    }
}