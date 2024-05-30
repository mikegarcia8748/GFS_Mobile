package com.gfs.mobile.system.ui.screen.milling.expense.declaration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import com.gfs.mobile.system.extensions.toPhp
import com.gfs.mobile.system.ui.component.OutlineTextField2
import com.gfs.mobile.system.ui.component.PrimaryButton
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme


@Composable
fun ExpenseDeclarationScreen(
    navController: NavHostController,
    viewModel: ExpenseDeclarationViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    ExpenseDeclarationContent(
        callback = ExpenseDeclarationCallback(
            onBackPressed = { navController.popBackStack() },
            onEnterDescription = { viewModel.onEnterDescription(it) },
            onEnterAmount = { viewModel.onEnterAmount(it) },
            onClickSave = { viewModel.saveExpenseDetail() }
        ),
        uiState = uiState
    )
}

@Composable
private fun ExpenseDeclarationContent(
    callback: ExpenseDeclarationCallback,
    uiState: ExpenseDeclarationUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_expense_declaration)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

            // Description TextField
            OutlineTextField2(
                modifier = Modifier,
                hint = stringResource(id = R.string.label_description),
                value = uiState.description,
                onValueChanged = { callback.onEnterDescription(it) }
            )

            // Amount TextField
            OutlineTextField2(
                modifier = Modifier,
                hint = stringResource(id = R.string.label_amount),
                value = uiState.formattedAmount.toPhp(),
                onValueChanged = { callback.onEnterAmount(it) }
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                modifier = Modifier,
                text = stringResource(id = R.string.label_save),
                onClick = {
                    callback.onClickSave()
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ExpenseDeclarationContentPreview() {
    GFSMaterialTheme {
        ExpenseDeclarationContent(
            callback = ExpenseDeclarationCallback(
                onBackPressed = { },
                onEnterDescription = { },
                onEnterAmount = { },
                onClickSave = { }
            ),
            uiState = ExpenseDeclarationUiState()
        )
    }
}