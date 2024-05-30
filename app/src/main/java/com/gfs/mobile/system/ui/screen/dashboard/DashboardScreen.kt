package com.gfs.mobile.system.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.navigation.DashboardScreen
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    DashboardContent(
        callback = DashboardCallback(
            onClickMillBilling = {
            },
            onClickDashboardItem = {

                val route = when (it) {
                    0 -> { DashboardScreen.MillBilling.route }
                    1 -> { DashboardScreen.MillInventory.route }
                    2-> { DashboardScreen.MillWorkers.route }
                    3 -> { DashboardScreen.MillAttendance.route }
                    4 -> { DashboardScreen.MillPayroll.route }
                    5 -> { DashboardScreen.MillWorkersLoan.route }
                    6 -> { DashboardScreen.ExpenseDeclaration.route }
                    7 -> { DashboardScreen.DailyExpense.route }
                    8 -> { DashboardScreen.MillWorkersLoan.route }
                    9 -> { DashboardScreen.MillCustomers.route }
                    else -> { DashboardScreen.Settings.route }
                }
                navController.navigate(route)
            }
        ),
        uiState = uiState
    )
}

@Composable
private fun DashboardContent(
    callback: DashboardCallback,
    uiState: DashboardUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.app_name),
                enableBackButton = false
            )
        }
    ) { paddingValues ->

        val dashboardMenuItems = listOf(
            DashboardMenuItem(painterResource(id = R.drawable.ic_mill), stringResource(id = R.string.label_mill)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_warehouse), stringResource(id = R.string.label_inventory)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_worker), stringResource(id = R.string.label_employee)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_attendance), stringResource(id = R.string.label_attendance)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_calculator), stringResource(id = R.string.label_payroll)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_loan), stringResource(id = R.string.label_loan)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_calculator), stringResource(id = R.string.label_expense_declaration)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_loan), stringResource(id = R.string.label_daily_expenses)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_customer), stringResource(id = R.string.label_customer)),
            DashboardMenuItem(painterResource(id = R.drawable.ic_settings), stringResource(id = R.string.label_settings)),
        )

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16)),
        ) {

            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.label_welcome),
                fontWeight = FontWeight.SemiBold
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                style = MaterialTheme.typography.titleLarge,
                text = uiState.userName,
                fontWeight = FontWeight.Normal
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                columns = GridCells.Fixed(2)
            ) {

                items(dashboardMenuItems.size) {
                    val item = dashboardMenuItems[it]

                    DashboardButton(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding4))
                            .padding(horizontal = dimensionResource(id = R.dimen.view_padding4)),
                        icon = item.icon,
                        label = item.label,
                        onClick = {
                            callback.onClickDashboardItem(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardButton(
    modifier: Modifier,
    icon: Painter,
    label: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .then(modifier)
            .padding(horizontal = dimensionResource(id = R.dimen.view_padding8)),
        onClick = { onClick() }
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.view_padding32))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.view_padding36)),
                painter = icon,
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                style = MaterialTheme.typography.bodyMedium,
                text = label,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DashboardContentPreview() {
    GFSMaterialTheme {
        DashboardContent(
            callback = DashboardCallback(
                onClickMillBilling = { },
                onClickDashboardItem = { }
            ),
            uiState = DashboardUiState()
        )
    }
}