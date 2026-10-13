/**
 * 
 */


(function () {
    const slider = document.querySelector('.hero-slider');
    if (!slider) return;
    const slides = Array.from(slider.querySelectorAll('.hero-slide'));
    const dotsWrap = slider.querySelector('.hero-dots');
    const INTERVAL = 4000;
    let current = 0;
    let timer = null;
    let hovered = false;

    slides.forEach(function (_, i) {
        const dot = document.createElement('button');
        dot.type = 'button';
        dot.className = 'hero-dot';
        dot.setAttribute('aria-label', 'Go to slide ' + (i + 1));
        dot.addEventListener('click', function () { show(i); start(); });
        dotsWrap.appendChild(dot);
    });
    const dots = Array.from(dotsWrap.children);

    function show(n) {
        current = (n + slides.length) % slides.length;
        slides.forEach(function (s, i) {
            const on = i === current;
            s.classList.toggle('is-active', on);
            s.setAttribute('aria-hidden', String(!on));
        });
        dots.forEach(function (d, i) { d.classList.toggle('is-active', i === current); });
    }

    function stop() { clearInterval(timer); timer = null; }

    function start() {
        stop();
        if (!hovered && !document.hidden) {
            timer = setInterval(function () { show(current + 1); }, INTERVAL);
        }
    }

    slider.querySelector('.hero-next').addEventListener('click', function () { show(current + 1); start(); });
    slider.querySelector('.hero-prev').addEventListener('click', function () { show(current - 1); start(); });

    slider.addEventListener('mouseenter', function () { hovered = true; stop(); });
    slider.addEventListener('mouseleave', function () { hovered = false; start(); });

    let touchX = null;
    slider.addEventListener('touchstart', function (e) { touchX = e.touches[0].clientX; }, { passive: true });
    slider.addEventListener('touchend', function (e) {
        if (touchX === null) return;
        const dx = e.changedTouches[0].clientX - touchX;
        if (Math.abs(dx) > 40) { show(dx < 0 ? current + 1 : current - 1); start(); }
        touchX = null;
    });

    document.addEventListener('visibilitychange', function () {
        if (document.hidden) { stop(); } else { start(); }
    });

    show(0);
    start();
})();

