import { NavLink } from 'react-router-dom';
import {
  Car,
  CircleHelp,
  History,
  Menu,
  Search,
  Settings,
  ShoppingCart,
  Star,
  X,
} from 'lucide-react';
import './Sidebar.css';

const NAV_ITEMS = [
  { to: '/buscar-veiculo', label: 'Buscar veículo', icon: Search },
  { to: '/historico', label: 'Histórico', icon: History },
  { to: '/veiculos', label: 'Veículos', icon: Car },
  { to: '/favoritos', label: 'Favoritos', icon: Star },
  { to: '/pedidos', label: 'Pedidos', icon: ShoppingCart },
  { to: '/configuracoes', label: 'Configurações', icon: Settings },
];

function LogoMark() {
  return (
    <span className="logo-mark" aria-hidden="true">
      <svg viewBox="0 0 48 48" width="44" height="44">
        <circle cx="20" cy="20" r="13" fill="none" stroke="#5b9bff" strokeWidth="3" />
        <circle cx="20" cy="20" r="6.5" fill="none" stroke="#9cc2ff" strokeWidth="2" />
        <circle cx="20" cy="20" r="2.4" fill="#dbe9ff" />
        <rect x="28" y="28" width="14" height="6" rx="3" fill="#5b9bff" transform="rotate(45 28 28)" />
      </svg>
    </span>
  );
}

export default function Sidebar({ expanded, mobileOpen, onToggle, onCloseMobile }) {
  const showLabels = expanded || mobileOpen;

  return (
    <aside
      className={`sidebar ${expanded ? 'expanded' : 'compact'} ${mobileOpen ? 'mobile-open' : ''}`}
      aria-label="Navegação principal"
    >
      <div className="sidebar-top">
        <button
          type="button"
          className="hamburger"
          onClick={onToggle}
          aria-label={expanded ? 'Recolher menu' : 'Expandir menu'}
          title={expanded ? 'Recolher menu' : 'Expandir menu'}
        >
          {expanded ? <X size={22} /> : <Menu size={22} />}
        </button>

        {showLabels ? (
          <div className="brand">
            <LogoMark />
            <div className="brand-text">
              <span className="brand-name">
                busca<span className="brand-accent">Peças</span>
              </span>
              <span className="brand-slogan">Encontre a peça certa.</span>
            </div>
          </div>
        ) : (
          <div className="brand-mini" title="buscaPeças">
            <LogoMark />
          </div>
        )}
      </div>

      <nav className="sidebar-nav">
        {NAV_ITEMS.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.to}
              to={item.to}
              onClick={onCloseMobile}
              data-tip={item.label}
              className={({ isActive }) => `nav-item${isActive ? ' active' : ''}`}
            >
              <Icon size={22} strokeWidth={1.9} className="nav-icon" />
              {showLabels && <span className="nav-label">{item.label}</span>}
            </NavLink>
          );
        })}
      </nav>

      <div className="sidebar-bottom">
        <NavLink
          to="/ajuda"
          onClick={onCloseMobile}
          data-tip="Ajuda"
          className={({ isActive }) => `nav-item${isActive ? ' active' : ''}`}
        >
          <CircleHelp size={22} strokeWidth={1.9} className="nav-icon" />
          {showLabels && <span className="nav-label">Ajuda</span>}
        </NavLink>
      </div>
    </aside>
  );
}
