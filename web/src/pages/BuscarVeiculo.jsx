import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Camera, Keyboard, Loader2, Search } from 'lucide-react';
import Button from '../components/Button.jsx';
import CameraDisabled from '../components/CameraDisabled.jsx';
import MercosulPlate from '../components/MercosulPlate.jsx';
import PlateInput from '../components/PlateInput.jsx';
import SecurityBanner from '../components/SecurityBanner.jsx';
import { buscarVeiculoPorPlaca } from '../services/api.js';
import './BuscarVeiculo.css';

export default function BuscarVeiculo() {
  const [placa, setPlaca] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  async function handleBuscar() {
    if (placa.length < 7) {
      setError('Digite os 7 caracteres da placa (ex.: ABC1D23).');
      return;
    }
    setError('');
    setLoading(true);
    try {
      const veiculo = await buscarVeiculoPorPlaca(placa);
      navigate(`/resultados/${placa}`, { state: { veiculo } });
    } catch (err) {
      setError(err.message || 'Não foi possível buscar o veículo. Tente novamente.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="buscar-page">
      <h1 className="page-title">Buscar veículo</h1>
      <p className="page-sub">
        Informe a placa ou tire uma foto para identificar o veículo
        <br />
        e encontrar as peças compatíveis.
      </p>

      <section className="search-card">
        <div className="tabs" role="tablist" aria-label="Modo de busca">
          <button type="button" role="tab" aria-selected="true" className="tab active">
            <Keyboard size={20} strokeWidth={1.9} />
            Buscar pela placa
          </button>
          <span className="tabs-divider" aria-hidden="true" />
          <span role="tab" aria-selected="false" aria-disabled="true" className="tab disabled">
            <Camera size={20} strokeWidth={1.9} />
            Tirar foto da placa
          </span>
        </div>

        <div className="card-columns">
          <div className="col">
            <h2 className="col-title">Digite a placa do veículo</h2>
            <MercosulPlate value={placa} />
            <PlateInput value={placa} onChange={(v) => { setPlaca(v); setError(''); }} error={error} />
            <div className="col-action">
              <Button onClick={handleBuscar} disabled={loading}>
                {loading ? (
                  <Loader2 size={19} strokeWidth={2.2} className="spin" />
                ) : (
                  <Search size={19} strokeWidth={2.2} />
                )}
                {loading ? 'Buscando...' : 'Buscar veículo'}
              </Button>
            </div>
          </div>

          <div className="col-divider" aria-hidden="true">
            <span className="ou-badge">ou</span>
          </div>

          <div className="col">
            <h2 className="col-title">Tire uma foto da placa</h2>
            <CameraDisabled />
          </div>
        </div>
      </section>

      <SecurityBanner />
    </div>
  );
}