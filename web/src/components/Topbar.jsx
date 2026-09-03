import { Bell, ChevronDown, Menu, User } from 'lucide-react';
import './Topbar.css';

export default function Topbar({ onOpenMobile }) {
  return (
    <header className="topbar">
      <button
        type="button"
        className="topbar-menu"
        onClick={onOpenMobile}
        aria-label="Abrir menu"
      >
        <Menu size={22} />
      </button>

      <div className="topbar-right">
        <button type="button" className="icon-btn" aria-label="Notificações" title="Notificações">
          <Bell size={21} strokeWidth={1.9} />
          <span className="dot" aria-hidden="true" />
        </button>

        <button type="button" className="user-chip" aria-label="Menu do usuário">
          <span className="avatar" aria-hidden="true">
            <User size={20} strokeWidth={2} />
          </span>
          <span className="greeting">Olá, Cliente</span>
          <ChevronDown size={16} className="chevron" />
        </button>
      </div>
    </header>
  );
}
