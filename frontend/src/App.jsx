import { Navigate, Route, Routes } from 'react-router-dom';
import AppLayout from './layouts/AppLayout.jsx';
import Ajuda from './pages/Ajuda.jsx';
import BuscarVeiculo from './pages/BuscarVeiculo.jsx';
import Configuracoes from './pages/Configuracoes.jsx';
import Favoritos from './pages/Favoritos.jsx';
import Historico from './pages/Historico.jsx';
import Pedidos from './pages/Pedidos.jsx';
import Veiculos from './pages/Veiculos.jsx';

export default function App() {
  return (
    <Routes>
      <Route element={<AppLayout />}>
        <Route index element={<Navigate to="/buscar-veiculo" replace />} />
        <Route path="/buscar-veiculo" element={<BuscarVeiculo />} />
        <Route path="/historico" element={<Historico />} />
        <Route path="/veiculos" element={<Veiculos />} />
        <Route path="/favoritos" element={<Favoritos />} />
        <Route path="/pedidos" element={<Pedidos />} />
        <Route path="/configuracoes" element={<Configuracoes />} />
        <Route path="/ajuda" element={<Ajuda />} />
        <Route path="*" element={<Navigate to="/buscar-veiculo" replace />} />
      </Route>
    </Routes>
  );
}
