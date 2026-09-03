import { Camera } from 'lucide-react';
import './CameraDisabled.css';

/**
 * Área da câmera — VISUAL E DESABILITADA.
 * Não abre câmera, não abre file picker, sem hover, sem clique.
 */
export default function CameraDisabled() {
  return (
    <div className="camera-disabled" aria-disabled="true" title="Funcionalidade em breve">
      <span className="camera-circle" aria-hidden="true">
        <Camera size={30} strokeWidth={1.8} />
      </span>
      <p className="camera-title">Toque para abrir a câmera</p>
      <p className="camera-sub">
        A foto será usada para identificar
        <br />
        os dados do veículo.
      </p>
      <span className="camera-badge">Em breve</span>
    </div>
  );
}
