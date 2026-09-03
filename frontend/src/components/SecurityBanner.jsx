import { ChevronRight, ShieldCheck } from 'lucide-react';
import './SecurityBanner.css';

export default function SecurityBanner() {
  return (
    <section className="security">
      <span className="security-icon" aria-hidden="true">
        <ShieldCheck size={24} strokeWidth={1.9} />
      </span>
      <div className="security-text">
        <p className="security-title">Seus dados estão seguros</p>
        <p className="security-desc">
          As informações do veículo são utilizadas apenas para busca de peças.
        </p>
      </div>
      <a href="/ajuda" className="security-link">
        Saiba mais <ChevronRight size={16} />
      </a>
    </section>
  );
}
