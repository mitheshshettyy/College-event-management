// theme.js - robust dark/light toggle (adds class to <html>)
// Place this file at: src/main/resources/static/assets/js/theme.js
(function () {
  const root = document.documentElement; // <html>
  const KEY = 'em_theme';

  function applyTheme(theme) {
    if (theme === 'dark') {
      root.classList.add('dark');
      localStorage.setItem(KEY, 'dark');
    } else {
      root.classList.remove('dark');
      localStorage.setItem(KEY, 'light');
    }
    updateButton(theme);
  }

  function updateButton(theme) {
    const btn = document.querySelector('.theme-toggle') || document.querySelector('#theme-toggle');
    if (!btn) return;
    btn.setAttribute('data-theme', theme);
    // small icon/text
    btn.textContent = theme === 'dark' ? '🌙 Dark' : '☀️ Light';
  }

  // Initialize on DOMContentLoaded (safe even if script loaded with defer)
  document.addEventListener('DOMContentLoaded', () => {
    // load saved preference
    const saved = localStorage.getItem(KEY);
    if (saved === 'dark' || saved === 'light') {
      applyTheme(saved);
    } else {
      // respect system preference if no saved value
      const prefersDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
      applyTheme(prefersDark ? 'dark' : 'light');
    }

    // wire the button (support id or class)
    const toggle = document.querySelector('.theme-toggle') || document.querySelector('#theme-toggle');
    if (toggle) {
      toggle.addEventListener('click', () => {
        const cur = root.classList.contains('dark') ? 'dark' : 'light';
        applyTheme(cur === 'dark' ? 'light' : 'dark');
      });
    }
  });
})();
