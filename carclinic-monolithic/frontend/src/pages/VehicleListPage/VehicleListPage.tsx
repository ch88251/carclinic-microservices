import { QueryClientProvider, QueryClient } from '@tanstack/react-query';
import VehicleList from '../../components/VehicleList/VehicleList';
import './VehicleListPage.css';

const queryClient = new QueryClient();

function VehicleListPage() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="vehicle-list-page">
        <VehicleList />
      </div>
    </QueryClientProvider>
  );
}

export default VehicleListPage;
