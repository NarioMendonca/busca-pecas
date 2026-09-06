import { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import {
  ArrowLeft,
  ArrowRight,
  Filter,
  Heart,
  IdCard,
  Info,
  Pencil,
  Search,
  ShieldCheck,
  Wrench,
} from 'lucide-react';
import ConditionBadge from '../components/ConditionBadge.jsx';
import StarRating from '../components/StarRating.jsx';
import { buscarVeiculoPorPlaca } from '../services/api.js';
import './ResultadosBusca.css';

const MOCK_RESULTADOS = [
  {
    id: 1,
    nome: 'Carburador Original GM',
    codigo: '93259323',
    aplicacao: 'Corsa 1.0 MPFI 8V 2001/2001',
    tipo: 'original',
    descricao: 'Peça 100% original de fábrica.',
    avaliacao: 4.9,
    avaliacoesCount: 128,
    vendedor: 'AutoPeças Sul',
    local: 'Porto Alegre - RS',
    lojaPremium: true,
    preco: 890,
    parcelasVezes: 6,
    parcelaValor: 148.33,
  },
  {
    id: 2,
    nome: 'Carburador Remanufaturado',
    codigo: '93259323-RM',
    aplicacao: 'Corsa 1.0 MPFI 8V 2001/2001',
    tipo: 'remanufaturado',
    descricao: 'Peça original de fábrica recondicionada.',
    avaliacao: 4.6,
    avaliacoesCount: 87,
    vendedor: 'Reman Sul',
    local: 'Curitiba - PR',
    lojaPremium: true,
    preco: 620,
    parcelasVezes: 6,
    parcelaValor: 103.33,
  },
  {
    id: 3,
    nome: 'Carburador Paralelo de Qualidade',
    codigo: '93259323-P',
    aplicacao: 'Corsa 1.0 MPFI 8V 2001/2001',
    tipo: 'paralelo-qualidade',
    descricao: 'Peça paralela de alta qualidade e desempenho.',
    avaliacao: 4.2,
    avaliacoesCount: 53,
    vendedor: 'Top Peças',
    local: 'São Paulo - SP',
    lojaPremium: false,
    preco: 420,
    parcelasVezes: 6,
    parcelaValor: 70,
  },
  {
    id: 4,
    nome: 'Carburador Paralelo',
    codigo: '93259323-P2',
    aplicacao: 'Corsa 1.0 MPFI 8V 2001/2001',
    tipo: 'paralelo-economico',
    descricao: 'Peça paralela econômica. Custo-benefício.',
    avaliacao: 3.6,
    avaliacoesCount: 28,
    vendedor: 'Mega Peças',
    local: 'Belo Horizonte - MG',
    lojaPremium: false,
    preco: 290,
    parcelasVezes: 6,
    parcelaValor: 48.33,
  },
  {
    id: 5,
    nome: 'Carburador Usado',
    codigo: '93259323-U',
    aplicacao: 'Corsa 1.0 MPFI 8V 2001/2001',
    tipo: 'usado',
    descricao: 'Peça usada original. Verifique o estado.',
    avaliacao: 3.1,
    avaliacoesCount: 19,
    vendedor: 'Desmanche Bom Preço',
    local: 'Campinas - SP',
    lojaPremium: false,
    preco: 180,
    parcelasVezes: 6,
    parcelaValor: 30,
  },
];

function formatBRL(value) {
  return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

export default function ResultadosBusca() {
  const { placa } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [veiculo, setVeiculo] = useState(location.state?.veiculo ?? null);
  const [carregando, setCarregando] = useState(!location.state?.veiculo);
  const [erro, setErro] = useState('');
  const [peca, setPeca] = useState('Carburador');

  // Se a página foi acessada direto pela URL (sem vir da busca), refaz a
  // consulta usando a placa da rota.
  useEffect(() => {
    if (veiculo || !placa) return;
    setCarregando(true);
    buscarVeiculoPorPlaca(placa)
      .then(setVeiculo)
      .catch((err) => setErro(err.message || 'Não foi possível carregar o veículo.'))
      .finally(() => setCarregando(false));
  }, [placa, veiculo]);

  const resultados = MOCK_RESULTADOS;
  const inicialDaMarca = (veiculo?.marca?.[0] ?? '?').toUpperCase();
  const specLinha = [veiculo?.combustivel, [veiculo?.ano, veiculo?.anoModelo].filter(Boolean).join('/')]
    .filter(Boolean)
    .join(' • ');

  return (
    <div className="resultados-page">
      <button type="button" className="back-link" onClick={() => navigate('/buscar-veiculo')}>
        <ArrowLeft size={18} /> Voltar à busca
      </button>

      <h1 className="page-title">Resultados da busca</h1>
      <p className="page-sub">
        {peca ? `${peca} encontrado` : 'Peças encontradas'} para o veículo abaixo.
      </p>

      {carregando && (
        <section className="vehicle-card vehicle-card-loading">Carregando dados do veículo...</section>
      )}

      {!carregando && erro && (
        <section className="vehicle-card vehicle-card-error">
          <Info size={18} /> {erro}
        </section>
      )}

      {!carregando && !erro && veiculo && (
        <section className="vehicle-card">
          <div className="vehicle-brand">
            <span className="brand-avatar" aria-hidden="true">
              {inicialDaMarca}
            </span>
            <div>
              <p className="vehicle-brand-name">{veiculo.marca}</p>
              <p className="vehicle-model">{veiculo.modelo}</p>
              {specLinha && <p className="vehicle-spec">{specLinha}</p>}
            </div>
          </div>

          <div className="vehicle-plate">
            <IdCard size={20} strokeWidth={1.8} />
            <div>
              <p className="plate-label-sm">Placa</p>
              <p className="plate-value">{veiculo.placa}</p>
            </div>
          </div>

          <button type="button" className="btn-change-vehicle" onClick={() => navigate('/buscar-veiculo')}>
            <Pencil size={16} /> Alterar veículo
          </button>
        </section>
      )}

      <section className="search-bar-card">
        <div className="search-input-wrap">
          <Search size={18} strokeWidth={2} className="search-icon" />
          <div className="search-input-col">
            <span className="search-label">Peça pesquisada</span>
            <input
              type="text"
              value={peca}
              onChange={(e) => setPeca(e.target.value)}
              placeholder="Ex.: Carburador, pastilha de freio..."
            />
          </div>
        </div>

        <button type="button" className="btn-filtros" disabled title="Filtros — em breve">
          <Filter size={17} strokeWidth={2} /> Filtros
        </button>

        <div className="ordenar-wrap">
          <span className="search-label">Ordenar por</span>
          <select disabled defaultValue="relevancia" title="Ordenação — em breve">
            <option value="relevancia">Relevância</option>
          </select>
        </div>
      </section>

      <p className="results-count">{resultados.length} resultados encontrados</p>

      <div className="results-list">
        {resultados.map((item) => (
          <article className="result-row" key={item.id}>
            <div className="result-thumb" aria-hidden="true">
              <Wrench size={24} strokeWidth={1.6} />
            </div>

            <div className="result-info">
              <p className="result-name">{item.nome}</p>
              <p className="result-code">Código: {item.codigo}</p>
              <p className="result-app">Aplicação: {item.aplicacao}</p>
            </div>

            <ConditionBadge tipo={item.tipo} description={item.descricao} />

            <div className="result-rating">
              <span className="result-rating-label">Avaliação do vendedor</span>
              <StarRating value={item.avaliacao} count={item.avaliacoesCount} />
            </div>

            <div className="result-seller">
              <p className="seller-name">{item.vendedor}</p>
              <p className="seller-local">{item.local}</p>
              {item.lojaPremium && (
                <span className="seller-premium">
                  <ShieldCheck size={13} strokeWidth={2} /> Loja Premium
                </span>
              )}
            </div>

            <div className="result-price">
              <p className="price-value">{formatBRL(item.preco)}</p>
              <p className="price-installment">
                em {item.parcelasVezes}x de {formatBRL(item.parcelaValor)}
              </p>
              <button type="button" className="btn-ver-detalhes">
                Ver detalhes <ArrowRight size={15} strokeWidth={2.2} />
              </button>
            </div>

            <button type="button" className="fav-btn" aria-label={`Favoritar ${item.nome}`}>
              <Heart size={20} strokeWidth={1.8} />
            </button>
          </article>
        ))}
      </div>

      <div className="info-banner">
        <Info size={16} strokeWidth={2} />
        Sempre confirme a compatibilidade da peça com seu veículo antes da compra.
      </div>
    </div>
  );
}
