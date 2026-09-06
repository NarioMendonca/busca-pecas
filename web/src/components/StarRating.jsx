import { Star } from 'lucide-react';
import './StarRating.css';

/** Avaliação em estrelas (0 a 5), com contagem opcional de avaliações. */
export default function StarRating({ value = 0, count }) {
  const pct = (Math.max(0, Math.min(5, value)) / 5) * 100;

  return (
    <div className="star-rating">
      <div className="star-stack" aria-label={`Avaliação ${value.toFixed(1)} de 5`}>
        <div className="star-row star-base">
          {Array.from({ length: 5 }).map((_, i) => (
            <Star key={i} size={15} strokeWidth={1.8} />
          ))}
        </div>
        <div className="star-row star-fill" style={{ width: `${pct}%` }}>
          {Array.from({ length: 5 }).map((_, i) => (
            <Star key={i} size={15} strokeWidth={1.8} fill="currentColor" />
          ))}
        </div>
      </div>
      <span className="star-value">
        {value.toFixed(1).replace('.', ',')}
        {count != null ? ` (${count})` : ''}
      </span>
    </div>
  );
}
