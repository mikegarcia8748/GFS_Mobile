package com.gfs.mobile.core.navigation

object Graph {
    const val ROOT = "root_nav_graph"
    const val AUTHENTICATION = "auth_graph"
    const val DASHBOARD = "dashboard_graph"
}

sealed class AuthScreen(val route: String) {
    data object EnterPIN: AuthScreen(route = "enter_pin")
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
