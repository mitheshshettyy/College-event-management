
// assets/js/api.js
const BASE_PATH = '/api';

const api = {
  async request(path, opts = {}) {
    const url = path.startsWith('/') ? BASE_PATH + path : BASE_PATH + '/' + path;
    const res = await fetch(url, {
      headers: { 'Content-Type': 'application/json' },
      ...opts
    });
  },
  get(path) { return this.request(path, { method: 'GET' }); },
  post(path, body) { return this.request(path, { method: 'POST', body: JSON.stringify(body) }); },
  put(path, body) { return body ? this.request(path, { method: 'PUT', body: JSON.stringify(body) }) : this.request(path, { method: 'PUT' }); },
  del(path) { return this.request(path, { method: 'DELETE' }); }
};

