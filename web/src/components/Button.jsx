import './Button.css';

export default function Button({ children, onClick, type = 'button', disabled = false }) {
  return (
    <button type={type} className="btn-primary" onClick={onClick} disabled={disabled}>
      {children}
    </button>
  );
}
