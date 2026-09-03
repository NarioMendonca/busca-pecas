import './PlateInput.css';

/** Campo de placa: aceita letras/números, converte para maiúsculas, máx. 7 (padrão Mercosul). */
export default function PlateInput({ value, onChange, error }) {
  function handleChange(e) {
    const clean = e.target.value
      .toUpperCase()
      .replace(/[^A-Z0-9]/g, '')
      .slice(0, 7);
    onChange(clean);
  }

  return (
    <div className="plate-field">
      <label className="plate-label" htmlFor="placa">
        Placa do veículo
      </label>
      <input
        id="placa"
        type="text"
        className={`plate-input${error ? ' has-error' : ''}`}
        placeholder="ABC1D23"
        value={value}
        onChange={handleChange}
        autoComplete="off"
        spellCheck={false}
        maxLength={7}
        aria-invalid={Boolean(error)}
      />
      {error ? (
        <span className="plate-error">{error}</span>
      ) : (
        <span className="plate-hint">Digite no formato Mercosul (ex.: ABC1D23)</span>
      )}
    </div>
  );
}
