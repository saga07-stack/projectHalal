const $ = (s) => document.querySelector(s);
const rs = (n) => "Rs. " + n.toLocaleString("en-IN");
const spice = (n) => n ? "Spice " + "●".repeat(n) + "○".repeat(3 - n) : "Not spicy";
const esc = (s) => String(s).replace(/[&<>"']/g, (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]));
const hoursText = () => SITE.hours.map(h => `${esc(h[0])}: ${esc(h[1])}`).join("<br>");

function layout() {
  const page = document.body.dataset.page;
  const link = (h, t, k) => `<a href="${h}"${page === k ? ' aria-current="page"' : ""}>${t}</a>`;
  // The home page now uses the shared shop header (common/header.html via
  // Thymeleaf), so #site-header no longer exists there — guard against it.
  const siteHeader = $("#site-header");
  if (siteHeader) {
    siteHeader.innerHTML = `<div class="wrap bar"><a class="brand" href="index.html">${esc(SITE.name)}</a>
    <nav aria-label="Main">${link("menu.html", "Menu", "menu")}${link("contact.html", "About &amp; contact", "contact")}${link("reserve.html", "Book a table", "reserve")}</nav></div>`;
  }
  const siteFooter = $("#site-footer");
  if (siteFooter) {
    siteFooter.innerHTML = `<div class="wrap foot"><div><strong>${esc(SITE.name)}</strong><br>${esc(SITE.address)}<br>${esc(SITE.phone)}</div><div>${hoursText()}</div></div>`;
  }
}
function photo(it) {
  return `<div class="ph"><span>${esc(it.name[0])}</span><img src="images/${it.id}.jpg" alt="${esc(it.name)}" loading="lazy" onerror="this.remove()"></div>`;
}
function card(it) {
  return `<a class="card" href="item.html?id=${it.id}">${photo(it)}<div class="cb"><h3>${esc(it.name)}</h3>
    <p>${esc(it.desc)}</p><p class="meta">${rs(it.price)} · ${it.veg ? "Vegetarian" : "Non-veg"}</p></div></a>`;
}
function home() {
  $("#hero-title").textContent = SITE.name;
  $("#hero-tag").textContent = SITE.tagline;
  $("#featured").innerHTML = ITEMS.filter(i => i.featured).map(card).join("");
  $("#hours").innerHTML = SITE.hours.map(h => `<li><span>${esc(h[0])}</span><span>${esc(h[1])}</span></li>`).join("");
}
function menu() {
  let cat = "All";
  const bar = $("#filters"), list = $("#menu-list");
  const draw = () => {
    bar.innerHTML = ["All", ...CATEGORIES].map(c => `<button type="button" aria-pressed="${c === cat}" data-c="${c}">${c}</button>`).join("");
    list.innerHTML = ITEMS.filter(i => cat === "All" || i.cat === cat).map(card).join("");
  };
  bar.addEventListener("click", (e) => { if (e.target.dataset.c) { cat = e.target.dataset.c; draw(); } });
  draw();
}
function item() {
  const it = ITEMS.find(i => i.id === new URLSearchParams(location.search).get("id"));
  const box = $("#item-detail");
  if (!it) { box.innerHTML = `<div><h1>Dish not found</h1><p>This dish isn't on our menu. <a href="menu.html">Back to the menu</a></p></div>`; return; }
  document.title = `${it.name} – ${SITE.name}`;
  document.querySelector('meta[name="description"]').content = it.desc;
  box.innerHTML = `${photo(it)}<div><p><a href="menu.html">← Menu</a></p><h1>${esc(it.name)}</h1>
    <p class="price">${rs(it.price)}</p><p>${esc(it.desc)}</p>
    <dl><dt>Diet</dt><dd>${it.veg ? "Vegetarian" : "Contains meat"}</dd><dt>Heat</dt><dd>${spice(it.spice)}</dd>
    <dt>Ingredients</dt><dd>${it.ingredients.map(esc).join(", ")}</dd><dt>Allergens</dt><dd>${it.allergens.map(esc).join(", ")}</dd></dl>
    <a class="btn" href="reserve.html">Book a table</a></div>`;
  $("#related").innerHTML = ITEMS.filter(i => i.cat === it.cat && i.id !== it.id).slice(0, 3).map(card).join("");
}
function contact() {
  $("#c-addr").textContent = SITE.address;
  $("#c-phone").innerHTML = `<a href="tel:${SITE.phone.replace(/[^+\d]/g, "")}">${esc(SITE.phone)}</a>`;
  $("#c-hours").innerHTML = hoursText();
  if (SITE.mapEmbed) $("#map").innerHTML = `<iframe src="${SITE.mapEmbed}" title="Map" loading="lazy" allowfullscreen></iframe>`;
}
function reserve() {
  const f = $("#booking-form"), out = $("#status");
  f.date.min = new Date().toISOString().split("T")[0];
  f.addEventListener("submit", async (e) => {
    e.preventDefault();
    if (!f.reportValidity()) return;
    const btn = f.querySelector("button[type=submit]");
    btn.disabled = true; out.className = ""; out.textContent = "Sending your request…";
    const d = Object.fromEntries(new FormData(f));
    d.access_key = SITE.accessKey;
    d.subject = `New table booking – ${d.date}, ${d.time}, ${d.guests} people`;
    d.from_name = SITE.name + " website";
    try {
      const r = await fetch("https://api.web3forms.com/submit", { method: "POST", headers: { "Content-Type": "application/json", Accept: "application/json" }, body: JSON.stringify(d) });
      const j = await r.json();
      if (!j.success) throw new Error(j.message);
      f.reset(); out.className = "ok";
      out.textContent = "Request sent. We'll confirm your table by phone or email soon.";
    } catch (err) {
      out.className = "err";
      out.textContent = `Couldn't send your request. Please try again or call ${SITE.phone}.`;
    }
    btn.disabled = false;
  });
}
layout();
({ home, menu, item, contact, reserve }[document.body.dataset.page] || (() => { }))();
