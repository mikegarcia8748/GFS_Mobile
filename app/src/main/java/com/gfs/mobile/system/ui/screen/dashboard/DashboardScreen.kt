package com.gfs.mobile.system.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.navigation.DashboardScreen
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun DashboardScreen(
    navController: NavHostController,
) {

    DashboardContent(
        callback = DashboardCallback(
            onClickMillBilling = {
                navController.navigate(DashboardScreen.MillBilling.route)
            },
            onClickInventory = {
                navController.navigate(DashboardScreen.MillInventory.route)
            },
            onClickEmployees = {
                navController.navigate(DashboardScreen.MillWorkers.route)
            },
            onClickAttendance = {
                navController.navigate(DashboardScreen.MillAttendance.route)
            },
            onClickPayroll = {
                navController.navigate(DashboardScreen.MillPayroll.route)
            },
            onClickSettings = {
                navController.navigate(DashboardScreen.Settings.route)
            },
            onClickCustomer = {
                navController.navigate(DashboardScreen.MillCustomers.route)
            },
            onClickLoan = {
                navController.navigate(DashboardScreen.MillWorkersLoan.route)
            }
        ),
        uiState = DashboardUiState()
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

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

            Text(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.label_dashboard),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(.2f))

            Row(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8))
                    .fillMaxWidth()
            ) {

                val buttons = listOf(stringResource(id = R.string.label_mill), stringResource(id = R.string.label_inventory))
                val icons = listOf(painterResource(id = R.drawable.ic_mill), painterResource(id = R.drawable.ic_warehouse))

                repeat(2) {
                    DashboardButton(
                        modifier = Modifier
                            .weight(1f),
                        icon = icons[it],
                        label = buttons[it],
                        onClick = {
                            if (it == 0)
                                callback.onClickMillBilling()
                            else
                                callback.onClickInventory()
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8))
                    .fillMaxWidth()
            ) {

                val buttons = listOf(stringResource(id = R.string.label_employee), stringResource(id = R.string.label_attendance))
                val icons = listOf(painterResource(id = R.drawable.ic_worker), painterResource(id = R.drawable.ic_attendance))

                repeat(2) {
                    DashboardButton(
                        modifier = Modifier
                            .weight(1f),
                        icon = icons[it],
                        label = buttons[it],
                        onClick = {
                            if (it == 0)
                                callback.onClickEmployees()
                            else
                                callback.onClickAttendance()
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8))
                    .fillMaxWidth()
            ) {

                val buttons = listOf(stringResource(id = R.string.label_payroll), stringResource(id = R.string.label_loan))
                val icons = listOf(painterResource(id = R.drawable.ic_calculator), painterResource(id = R.drawable.ic_loan))

                repeat(2) {
                    DashboardButton(
                        modifier = Modifier
                            .weight(1f),
                        icon = icons[it],
                        label = buttons[it],
                        onClick = {
                            if (it == 0)
                                callback.onClickPayroll()
                            else
                                callback.onClickLoan()
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8))
                    .fillMaxWidth()
            ) {

                val buttons = listOf(stringResource(id = R.string.label_customer), stringResource(id = R.string.label_settings))
                val icons = listOf(painterResource(id = R.drawable.ic_customer), painterResource(id = R.drawable.ic_settings))

                repeat(2) {
                    DashboardButton(
                        modifier = Modifier
                            .weight(1f),
                        icon = icons[it],
                        label = buttons[it],
                        onClick = {
                            if (it == 0)
                                callback.onClickCustomer()
                            else
                                callback.onClickSettings()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
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
                onClickInventory = { },
                onClickEmployees = { },
                onClickAttendance = { },
                onClickPayroll = { },
                onClickSettings = { },
                onClickCustomer = { },
                onClickLoan = { }
            ),
            uiState = DashboardUiState()
        )
    }
}