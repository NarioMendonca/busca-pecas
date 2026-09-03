import './MercosulPlate.css';

/** Representação estilizada da placa Mercosul (somente visual, sem imagem externa). */
export default function MercosulPlate({ value = '' }) {
  const chars = (value || '').padEnd(7, '·').slice(0, 7).split('');

  return (
    <div className="plate-visual" aria-hidden="true">
      <div className="plate-strip">
        <span className="plate-mercosul">MERCOSUL</span>
        <span className="plate-country">BRASIL</span>
        <span className="plate-flag" title="Brasil">
          <svg viewBox="0 0 22 16" width="22" height="16">
            <rect x="0" y="0" width="22" height="16" rx="2" fill="#009c3b" />
            <polygon points="11,2.5 19,8 11,13.5 3,8" fill="#ffdf00" />
            <circle cx="11" cy="8" r="2.6" fill="#002776" />
          </svg>
        </span>
      </div>
      <div className="plate-body">
        <span className="plate-br">BR</span>
        <span className="plate-chars">
          {chars.map((c, i) => (
            <span key={i} className={`plate-char${c === '·' ? ' empty' : ''}`}>
              {c === '·' ? 'X' : c}
            </span>
          ))}
        </span>
      </div>
    </div>
  );
}
