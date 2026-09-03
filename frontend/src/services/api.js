/**
 * Central de chamadas à API (Spring Boot).
 *
 * Exemplos futuros:
 *   GET  /api/veiculos/{placa}
 *   POST /api/veiculos/buscar
 *   GET  /api/historico
 *
 * Por enquanto apenas stubs — sem fetch real.
 */

const API_BASE_URL = import.meta.env.VITE_API_URL ?? '';

export function getApiBaseUrl() {
  return API_BASE_URL;
}

export async function buscarVeiculoPorPlaca() {
  throw new Error('Integração com a API ainda não habilitada.');
}

export async function listarHistorico() {
  return [];
}
