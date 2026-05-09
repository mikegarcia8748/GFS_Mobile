package com.gfs.mobile.system.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gfs.mobile.core.navigation.DashboardScreen
import com.gfs.mobile.feature.dashboard.ui.screen.dashboard.DashboardScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance.MillAttendanceScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.billing.MillBillingScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.customer.MillCustomerScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.expense.declaration.ExpenseDeclarationScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.expense.list.ExpensesListScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.inventory.MillInventoryScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.loan.MillWorkerLoanScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.payment.MillPaymentScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.payroll.MillWorkerPayrollScreen
import com.gfs.mobile.feature.ricemill.ui.screen.milling.worker.MillWorkerScreen
import com.gfs.mobile.feature.dashboard.ui.screen.settings.SettingsScreen

fun NavGraphBuilder.dashboardNavGraph(navController: NavHostController) {

    composable(DashboardScreen.Dashboard.route) {
        DashboardScreen(navController = navController)
    }

    composable(DashboardScreen.MillBilling.route) {
        MillBillingScreen(navController = navController)
    }

    composable(DashboardScreen.MillBillingPayment.route) {
        MillPaymentScreen(navController = navController)
    }

    composable(DashboardScreen.MillAttendance.route) {
        MillAttendanceScreen(navController = navController)
    }

    composable(DashboardScreen.MillInventory.route) {
        MillInventoryScreen(navController = navController)
    }

    composable(DashboardScreen.MillPayroll.route) {
        MillWorkerPayrollScreen(navController = navController)
    }

    composable(DashboardScreen.MillPayroll.route) {
        MillWorkerPayrollScreen(navController = navController)
    }

    composable(DashboardScreen.Settings.route) {
        SettingsScreen(navController = navController)
    }

    composable(DashboardScreen.MillCustomers.route) {
        MillCustomerScreen(navController = navController)
    }

    composable(DashboardScreen.MillWorkersLoan.route) {
        MillWorkerLoanScreen(navController = navController)
    }

    composable(DashboardScreen.MillWorkers.route) {
        MillWorkerScreen(navController = navController)
    }

    composable(DashboardScreen.ExpenseDeclaration.route) {
        ExpenseDeclarationScreen(navController = navController)
    }

    composable(DashboardScreen.DailyExpense.route) {
        ExpensesListScreen(navController = navController)
    }
}


