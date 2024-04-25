package com.gfs.mobile.system.ui.screen.milling.payroll

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gfs.mobile.system.R
import com.gfs.mobile.system.data.model.payroll.DailySalaryDetail
import com.gfs.mobile.system.data.model.payroll.WorkerPayrollDetail
import com.gfs.mobile.system.extensions.toPhp
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import kotlinx.coroutines.delay
import java.math.BigDecimal

@Composable
fun MillWorkerPayrollScreen(
    navController: NavHostController,
    viewModel: MillWorkerPayrollViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillWorkerPayrollContent(
        callback = MillWorkerPayrollCallback(
            onBackPressed = { navController.popBackStack() }
        ),
        uiState = uiState
    )
}

@Composable
private fun MillWorkerPayrollContent(
    callback: MillWorkerPayrollCallback,
    uiState: MillWorkerPayrollUiState
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.anim_searching))
    val progress by animateLottieCompositionAsState(composition = composition)
    var displayMessage by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(300)
        displayMessage = true
    }

    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_payroll)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

            val payrollDetails = uiState.workerPayrollDetail.orEmpty()

            if (payrollDetails.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LottieAnimation(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .fillMaxHeight(.3f),
                        composition = composition,
                        progress = { progress }
                    )

                    AnimatedVisibility(visible = displayMessage) {
                        Text(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                            text = stringResource(id = R.string.sentence_no_payroll_records_found),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

            } else {

                LazyRow {

                    items(payrollDetails.size) {
                        WorkerPayrollComputation(
                            workerPayrollDetail = payrollDetails[it]
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkerPayrollComputation(
    workerPayrollDetail: WorkerPayrollDetail
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.view_padding4)),
    ) {

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.view_padding16)),
        ) {

            // Worker Icon, FullName & Alias
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.ic_worker),
                        contentDescription = null
                    )

                    val fullName = workerPayrollDetail.fullname.orEmpty()
                    val alias = workerPayrollDetail.alias.orEmpty()

                    Text(
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.view_padding8)),
                        text = "$fullName ($alias)",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding8))
                )
            }

            // Attendance
            item {
                Text(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                    text = stringResource(id = R.string.label_attendance),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            val attendance = workerPayrollDetail.dailySalaryDetail.orEmpty()

            items(attendance.size) {
                AttendanceDetail( dailySalaryDetail = attendance[it] )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.view_padding4))
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                )

            }

            // GROSS Salary
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = stringResource(id = R.string.label_Gross_salary),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = workerPayrollDetail.grossSalary?.toBigDecimal()?.toPhp().orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Deduction
            item {
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                    text = stringResource(id = R.string.label_deductions),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            val deductions = workerPayrollDetail.deductedLoan.orEmpty()

            // Loan deduction
            items(deductions.size) {
                DeductionsDetail(
                    label = stringResource(id = R.string.label_loan),
                    value = deductions[it].amount?.toBigDecimal() ?: BigDecimal(0.0)
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                )
            }

            // NET Salary
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = stringResource(id = R.string.label_net_salary),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = BigDecimal(7700.0).toPhp(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }
    }
}

@Composable
private fun AttendanceDetail(
    dailySalaryDetail: DailySalaryDetail?
) {

    val isPresent = dailySalaryDetail?.isPresent ?: false

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.view_padding16))
            .padding(vertical = dimensionResource(id = R.dimen.view_padding4)),
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding4)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Day of The Week
                Text(
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.view_padding16)),
                    text = dailySalaryDetail?.day.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                // Attendance Status
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (isPresent) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding8))
                        ),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(all = dimensionResource(id = R.dimen.view_padding4)),
                        text = if (isPresent) stringResource(id = R.string.label_present)
                        else stringResource(id = R.string.label_absent),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isPresent) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Daily Gross Earnings
                if (isPresent) {
                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding4)),
                        text = dailySalaryDetail?.grossEarnings?.toBigDecimal()?.toPhp().orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (isPresent) {

            item {
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.view_padding4)),
                    text = stringResource(id = R.string.label_deductions),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            // Deductions
            val deductions = dailySalaryDetail?.expenses.orEmpty()

            items(deductions.size) {

                DeductionsDetail(
                    label = deductions[it]?.label.orEmpty(),
                    value = deductions[it]?.amount?.toBigDecimal() ?: BigDecimal(0.0)
                )
            }

            // 20% Calculation
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = stringResource(id = R.string.label_twenty_percent),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = BigDecimal(7700.0).toPhp(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            val dividends = dailySalaryDetail?.dividends.orEmpty()

            // Dividends
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = "Divided By $dividends",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = dailySalaryDetail?.dividedAmount?.toBigDecimal()?.toPhp().orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }

            // Daily Net Salary
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = stringResource(id = R.string.label_daily_salary),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                        text = dailySalaryDetail?.netSalary?.toBigDecimal()?.toPhp().orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun DeductionsDetail(
    label: String,
    value: BigDecimal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.view_padding8)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier,
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier,
            text = value.toPhp(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MillWorkerPayrollContentPreview() {
    GFSMaterialTheme {
        MillWorkerPayrollContent(
            callback = MillWorkerPayrollCallback(
                onBackPressed = { }
            ),
            uiState = MillWorkerPayrollUiState()
        )
    }
}