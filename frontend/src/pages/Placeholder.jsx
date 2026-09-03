import './Placeholder.css';

export default function Placeholder({ title, description }) {
  return (
    <div className="placeholder-page">
      <h1 className="page-title">{title}</h1>
      <p className="page-sub">{description}</p>
      <section className="placeholder-card">
        <p className="placeholder-strong">Em breve</p>
        <p className="placeholder-text">
          Esta seção está preparada no layout e será integrada em seguida.
        </p>
      </section>
    </div>
  );
}
