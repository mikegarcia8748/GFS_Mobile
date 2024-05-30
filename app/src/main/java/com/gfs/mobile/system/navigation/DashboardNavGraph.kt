package com.gfs.mobile.system.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gfs.mobile.system.ui.screen.dashboard.DashboardScreen
import com.gfs.mobile.system.ui.screen.milling.attendance.MillAttendanceScreen
import com.gfs.mobile.system.ui.screen.milling.billing.MillBillingScreen
import com.gfs.mobile.system.ui.screen.milling.customer.MillCustomerScreen
import com.gfs.mobile.system.ui.screen.milling.expense.declaration.ExpenseDeclarationScreen
import com.gfs.mobile.system.ui.screen.milling.expense.list.ExpensesListScreen
import com.gfs.mobile.system.ui.screen.milling.inventory.MillInventoryScreen
import com.gfs.mobile.system.ui.screen.milling.loan.MillWorkerLoanScreen
import com.gfs.mobile.system.ui.screen.milling.payment.MillPaymentScreen
import com.gfs.mobile.system.ui.screen.milling.payroll.MillWorkerPayrollScreen
import com.gfs.mobile.system.ui.screen.milling.worker.MillWorkerScreen
import com.gfs.mobile.system.ui.screen.settings.SettingsScreen

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

    composable(DashboardScreen.DailyExpense.route) {
        ExpenseDeclarationScreen(navController = navController)
    }

    composable(DashboardScreen.ExpenseDeclaration.route) {
        ExpensesListScreen(navController = navController)
    }
}

sealed class DashboardScreen(val route: String) {

    data object Dashboard: DashboardScreen(route = "dashboard")
    data object Settings: DashboardScreen(route = "settings")
    data object MillBilling: DashboardScreen(route = "mill_billing")
    data object MillBillingPayment: DashboardScreen(route = "mill_billing_payment")
    data object MillAttendance: DashboardScreen(route = "mill_worker_attendance")
    data object MillWorkers: DashboardScreen(route = "mill_workers")
    data object MillInventory: DashboardScreen(route = "mill_inventory")
    data object MillPayroll: DashboardScreen(route = "mill_payroll")
    data object MillCustomers: DashboardScreen(route = "mill_customers")
    data object MillWorkersLoan: DashboardScreen(route = "mill_workers_loan")
    data object ExpenseDeclaration: DashboardScreen(route = "mill_expense_declaration")
    data object DailyExpense: DashboardScreen(route = "mill_daily_expense")
}