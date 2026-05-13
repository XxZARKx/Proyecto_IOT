import { Navigate, Route, Routes } from 'react-router-dom'
import DashboardLayout from '../../components/layout/DashboardLayout'
import LoginPage from '../../features/auth/LoginPage'
import DashboardPage from '../../features/dashboard/DashboardPage'
import ProductListPage from '../../features/products/ProductListPage'
import ProductFormPage from '../../features/products/ProductFormPage'
import ProductDetailPage from '../../features/products/ProductDetailPage'
import CategoryPage from '../../features/categories/CategoryPage'
import InventoryPage from '../../features/inventory/InventoryPage'
import MovementHistoryPage from '../../features/inventory/MovementHistoryPage'
import AlertsPage from '../../features/alerts/AlertsPage'
import IotDevicesPage from '../../features/iot/IotDevicesPage'
import SensorReadingsPage from '../../features/iot/SensorReadingsPage'
import IotSimulatorPage from '../../features/iot/IotSimulatorPage'
import AnalyticsPage from '../../features/analytics/AnalyticsPage'
import DemandPredictionPage from '../../features/analytics/DemandPredictionPage'
import ReportsPage from '../../features/reports/ReportsPage'
import UsersPage from '../../features/users/UsersPage'
import AuditPage from '../../features/audit/AuditPage'
import PrivateRoute from './PrivateRoute'
import PublicRoute from './PublicRoute'

export default function AppRouter() {
  return (
    <Routes>
      <Route element={<PublicRoute />}>
        <Route path="/login" element={<LoginPage />} />
      </Route>
      <Route element={<PrivateRoute />}>
        <Route element={<DashboardLayout />}>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/products" element={<ProductListPage />} />
          <Route path="/products/create" element={<ProductFormPage />} />
          <Route path="/products/:id" element={<ProductDetailPage />} />
          <Route path="/products/:id/edit" element={<ProductFormPage />} />
          <Route path="/categories" element={<CategoryPage />} />
          <Route path="/inventory" element={<InventoryPage />} />
          <Route path="/inventory/entry" element={<InventoryPage type="ENTRADA" />} />
          <Route path="/inventory/exit" element={<InventoryPage type="SALIDA" />} />
          <Route path="/inventory/adjustment" element={<InventoryPage type="AJUSTE" />} />
          <Route path="/inventory/history" element={<MovementHistoryPage />} />
          <Route path="/alerts" element={<AlertsPage />} />
          <Route path="/iot/devices" element={<IotDevicesPage />} />
          <Route path="/iot/readings" element={<SensorReadingsPage />} />
          <Route path="/iot/simulator" element={<IotSimulatorPage />} />
          <Route path="/analytics" element={<AnalyticsPage />} />
          <Route path="/analytics/prediction" element={<DemandPredictionPage />} />
          <Route path="/reports" element={<ReportsPage />} />
          <Route path="/audit" element={<AuditPage />} />
          <Route path="/users" element={<UsersPage />} />
        </Route>
      </Route>
    </Routes>
  )
}
