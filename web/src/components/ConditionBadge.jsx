import { CheckCircle2, Clock, ShieldCheck } from 'lucide-react';
import './ConditionBadge.css';

const CONFIG = {
  original: { icon: ShieldCheck, label: 'Original', tone: 'green' },
  remanufaturado: { icon: ShieldCheck, label: 'Original Remanufaturado', tone: 'amber' },
  'paralelo-qualidade': { icon: ShieldCheck, label: 'Paralelo de Qualidade', tone: 'orange' },
  'paralelo-economico': { icon: CheckCircle2, label: 'Paralelo Econômico', tone: 'red' },
  usado: { icon: Clock, label: 'Usado', tone: 'gray' },
};

/** Selo de condição da peça (original, remanufaturado, paralelo, usado). */
export default function ConditionBadge({ tipo, description }) {
  const config = CONFIG[tipo] ?? CONFIG.usado;
  const Icon = config.icon;

  return (
    <div className={`condition-badge tone-${config.tone}`}>
      <div className="condition-badge-title">
        <Icon size={17} strokeWidth={2.2} />
        <span>{config.label}</span>
      </div>
      {description && <p className="condition-badge-desc">{description}</p>}
    </div>
  );
}
