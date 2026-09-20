import { QueryClientProvider, QueryClient } from '@tanstack/react-query';
import StaffList from '../../components/StaffList/StaffList';
import './StaffListPage.css';

const queryClient = new QueryClient();

function StaffListPage() {
  return (
    <QueryClientProvider client={queryClient}>
      <div className="staff-list-page">
        <StaffList />
      </div>
    </QueryClientProvider>
  );
}

export default StaffListPage;
