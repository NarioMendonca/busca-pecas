import { useState } from 'react';
import {
  Bell,
  CheckCircle2,
  Download,
  Laptop,
  Lock,
  Moon,
  ShieldCheck,
  Sun,
  Trash2,
  User,
} from 'lucide-react';
import Switch from '../components/Switch.jsx';
import './Configuracoes.css';

/**
 * Página de Configurações da conta.
 * Ainda não há endpoints de usuário/preferências na API — os controles abaixo
 * atualizam apenas o estado local (mesmo padrão de simulação usado em
 * BuscarVeiculo). A integração real com o backend vem depois.
 */
export default function Configuracoes() {
  const [perfil, setPerfil] = useState({
    nome: 'Cliente buscaPeças',
    email: 'cliente@email.com',
    telefone: '(11) 99999-0000',
  });
  const [perfilSalvo, setPerfilSalvo] = useState(false);

  const [notificacoes, setNotificacoes] = useState({
    pedidos: true,
    pecasCompativeis: true,
    promocoes: false,
    push: false,
  });

  const [preferencias, setPreferencias] = useState({
    salvarHistorico: true,
    lembrarPlaca: true,
    tema: 'claro',
  });

  const [senha, setSenha] = useState({ atual: '', nova: '', confirmar: '' });
  const [senhaMsg, setSenhaMsg] = useState('');

  function handlePerfilChange(campo, valor) {
    setPerfil((prev) => ({ ...prev, [campo]: valor }));
    setPerfilSalvo(false);
  }

  function handleSalvarPerfil() {
    // Somente simulação visual — integração com a API vem depois.
    setPerfilSalvo(true);
  }

  function toggleNotificacao(campo) {
    setNotificacoes((prev) => ({ ...prev, [campo]: !prev[campo] }));
  }

  function togglePreferencia(campo) {
    setPreferencias((prev) => ({ ...prev, [campo]: !prev[campo] }));
  }

  function handleAtualizarSenha() {
    if (!senha.atual || !senha.nova || !senha.confirmar) {
      setSenhaMsg('erro:Preencha todos os campos de senha.');
      return;
    }
    if (senha.nova.length < 6) {
      setSenhaMsg('erro:A nova senha deve ter pelo menos 6 caracteres.');
      return;
    }
    if (senha.nova !== senha.confirmar) {
      setSenhaMsg('erro:A confirmação não coincide com a nova senha.');
      return;
    }
    // Somente simulação visual — integração com a API vem depois.
    setSenha({ atual: '', nova: '', confirmar: '' });
    setSenhaMsg('ok:Senha atualizada com sucesso.');
  }

  return (
    <div className="config-page">
      <h1 className="page-title">Configurações</h1>
      <p className="page-sub">Ajuste as preferências da sua conta.</p>

      {/* Perfil */}
      <section className="settings-card">
        <header className="settings-card-header">
          <span className="settings-icon">
            <User size={20} strokeWidth={2} />
          </span>
          <div>
            <h2 className="settings-card-title">Perfil</h2>
            <p className="settings-card-sub">Seus dados de contato e identificação.</p>
          </div>
        </header>

        <div className="settings-grid">
          <div className="field">
            <label htmlFor="nome">Nome completo</label>
            <input
              id="nome"
              type="text"
              value={perfil.nome}
              onChange={(e) => handlePerfilChange('nome', e.target.value)}
            />
          </div>
          <div className="field">
            <label htmlFor="email">E-mail</label>
            <input
              id="email"
              type="email"
              value={perfil.email}
              onChange={(e) => handlePerfilChange('email', e.target.value)}
            />
          </div>
          <div className="field">
            <label htmlFor="telefone">Telefone</label>
            <input
              id="telefone"
              type="tel"
              value={perfil.telefone}
              onChange={(e) => handlePerfilChange('telefone', e.target.value)}
            />
          </div>
        </div>

        <div className="settings-actions">
          <button type="button" className="btn-save" onClick={handleSalvarPerfil}>
            Salvar alterações
          </button>
          {perfilSalvo && (
            <span className="inline-feedback ok">
              <CheckCircle2 size={16} /> Alterações salvas.
            </span>
          )}
        </div>
      </section>

      {/* Notificações */}
      <section className="settings-card">
        <header className="settings-card-header">
          <span className="settings-icon">
            <Bell size={20} strokeWidth={2} />
          </span>
          <div>
            <h2 className="settings-card-title">Notificações</h2>
            <p className="settings-card-sub">Escolha como quer ser avisado.</p>
          </div>
        </header>

        <div className="settings-list">
          <div className="settings-item">
            <div>
              <p className="item-title">Atualizações de pedidos</p>
              <p className="item-desc">Receba e-mails sobre o status dos seus pedidos.</p>
            </div>
            <Switch
              checked={notificacoes.pedidos}
              onChange={() => toggleNotificacao('pedidos')}
              label="Atualizações de pedidos"
            />
          </div>

          <div className="settings-item">
            <div>
              <p className="item-title">Peças compatíveis encontradas</p>
              <p className="item-desc">Avise quando novas peças para o seu veículo aparecerem.</p>
            </div>
            <Switch
              checked={notificacoes.pecasCompativeis}
              onChange={() => toggleNotificacao('pecasCompativeis')}
              label="Peças compatíveis encontradas"
            />
          </div>

          <div className="settings-item">
            <div>
              <p className="item-title">Promoções e novidades</p>
              <p className="item-desc">Ofertas, descontos e novidades do buscaPeças.</p>
            </div>
            <Switch
              checked={notificacoes.promocoes}
              onChange={() => toggleNotificacao('promocoes')}
              label="Promoções e novidades"
            />
          </div>

          <div className="settings-item">
            <div>
              <p className="item-title">Notificações push no navegador</p>
              <p className="item-desc">Alertas em tempo real enquanto você navega no site.</p>
            </div>
            <Switch
              checked={notificacoes.push}
              onChange={() => toggleNotificacao('push')}
              label="Notificações push no navegador"
            />
          </div>
        </div>
      </section>

      {/* Preferências */}
      <section className="settings-card">
        <header className="settings-card-header">
          <span className="settings-icon">
            <Laptop size={20} strokeWidth={2} />
          </span>
          <div>
            <h2 className="settings-card-title">Preferências</h2>
            <p className="settings-card-sub">Personalize como o app se comporta.</p>
          </div>
        </header>

        <div className="settings-list">
          <div className="settings-item">
            <div>
              <p className="item-title">Salvar buscas no histórico</p>
              <p className="item-desc">Guarda automaticamente as placas que você consultar.</p>
            </div>
            <Switch
              checked={preferencias.salvarHistorico}
              onChange={() => togglePreferencia('salvarHistorico')}
              label="Salvar buscas no histórico"
            />
          </div>

          <div className="settings-item">
            <div>
              <p className="item-title">Lembrar última placa pesquisada</p>
              <p className="item-desc">Pré-preenche o campo de busca com a última placa usada.</p>
            </div>
            <Switch
              checked={preferencias.lembrarPlaca}
              onChange={() => togglePreferencia('lembrarPlaca')}
              label="Lembrar última placa pesquisada"
            />
          </div>

          <div className="settings-item column">
            <div>
              <p className="item-title">Tema</p>
              <p className="item-desc">Escolha a aparência do painel.</p>
            </div>
            <div className="theme-options" role="radiogroup" aria-label="Tema">
              <button
                type="button"
                role="radio"
                aria-checked={preferencias.tema === 'claro'}
                className={`theme-btn${preferencias.tema === 'claro' ? ' active' : ''}`}
                onClick={() => setPreferencias((prev) => ({ ...prev, tema: 'claro' }))}
              >
                <Sun size={17} strokeWidth={2} /> Claro
              </button>
              <button
                type="button"
                role="radio"
                aria-checked={preferencias.tema === 'escuro'}
                className={`theme-btn${preferencias.tema === 'escuro' ? ' active' : ''}`}
                onClick={() => setPreferencias((prev) => ({ ...prev, tema: 'escuro' }))}
              >
                <Moon size={17} strokeWidth={2} /> Escuro
              </button>
              <button
                type="button"
                role="radio"
                aria-checked={preferencias.tema === 'sistema'}
                className={`theme-btn${preferencias.tema === 'sistema' ? ' active' : ''}`}
                onClick={() => setPreferencias((prev) => ({ ...prev, tema: 'sistema' }))}
              >
                <Laptop size={17} strokeWidth={2} /> Sistema
              </button>
            </div>
          </div>
        </div>
        {preferencias.tema !== 'claro' && (
          <p className="settings-note">
            A aplicação do tema {preferencias.tema === 'escuro' ? 'escuro' : 'automático'} ainda
            será integrada — por enquanto isso só guarda sua preferência.
          </p>
        )}
      </section>

      {/* Segurança */}
      <section className="settings-card">
        <header className="settings-card-header">
          <span className="settings-icon">
            <Lock size={20} strokeWidth={2} />
          </span>
          <div>
            <h2 className="settings-card-title">Segurança</h2>
            <p className="settings-card-sub">Altere sua senha de acesso.</p>
          </div>
        </header>

        <div className="settings-grid">
          <div className="field">
            <label htmlFor="senha-atual">Senha atual</label>
            <input
              id="senha-atual"
              type="password"
              autoComplete="current-password"
              value={senha.atual}
              onChange={(e) => setSenha((prev) => ({ ...prev, atual: e.target.value }))}
            />
          </div>
          <div className="field">
            <label htmlFor="senha-nova">Nova senha</label>
            <input
              id="senha-nova"
              type="password"
              autoComplete="new-password"
              value={senha.nova}
              onChange={(e) => setSenha((prev) => ({ ...prev, nova: e.target.value }))}
            />
          </div>
          <div className="field">
            <label htmlFor="senha-confirmar">Confirmar nova senha</label>
            <input
              id="senha-confirmar"
              type="password"
              autoComplete="new-password"
              value={senha.confirmar}
              onChange={(e) => setSenha((prev) => ({ ...prev, confirmar: e.target.value }))}
            />
          </div>
        </div>

        <div className="settings-actions">
          <button type="button" className="btn-save" onClick={handleAtualizarSenha}>
            Atualizar senha
          </button>
          {senhaMsg && (
            <span className={`inline-feedback ${senhaMsg.startsWith('ok') ? 'ok' : 'error'}`}>
              {senhaMsg.startsWith('ok') ? <CheckCircle2 size={16} /> : <ShieldCheck size={16} />}
              {senhaMsg.split(':')[1]}
            </span>
          )}
        </div>
      </section>

      {/* Conta / dados */}
      <section className="settings-card danger">
        <header className="settings-card-header">
          <span className="settings-icon danger">
            <Trash2 size={20} strokeWidth={2} />
          </span>
          <div>
            <h2 className="settings-card-title">Conta e dados</h2>
            <p className="settings-card-sub">Exporte suas informações ou encerre sua conta.</p>
          </div>
        </header>

        <div className="danger-row">
          <div>
            <p className="item-title">Exportar meus dados</p>
            <p className="item-desc">Baixe uma cópia dos seus dados cadastrados no buscaPeças.</p>
          </div>
          <button type="button" className="btn-outline">
            <Download size={16} strokeWidth={2} /> Exportar
          </button>
        </div>

        <div className="danger-row">
          <div>
            <p className="item-title">Excluir conta</p>
            <p className="item-desc">
              Remove permanentemente sua conta, histórico e favoritos. Essa ação não pode ser
              desfeita.
            </p>
          </div>
          <button type="button" className="btn-danger">
            <Trash2 size={16} strokeWidth={2} /> Excluir conta
          </button>
        </div>
      </section>
    </div>
  );
}