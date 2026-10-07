console.log("js loaded");

const itemsContainer = document.getElementById("container");
const FixedUrl = "http://localhost:55000/shared_shop/";

// Function to load all items from API
function loadAllItems() {
    if (!itemsContainer) {
        console.log("Container not found, skipping item load");
        return;
    }

    console.log("Loading all items...");

    fetch(`${FixedUrl}items/all`)
        .then((response) => response.json())
        .then((data) => {
            itemsContainer.innerHTML = ""; // Clear previous items

            // Create a grid wrapper for items
            const gridWrapper = document.createElement("div");
            gridWrapper.className = "items-grid";

            data.forEach((item) => {
                // Create main item card
                const itemCard = document.createElement("div");
                itemCard.className = "item-card";
                 itemCard.setAttribute("data-item-id", item.id);
                // Image container
                const imageContainer = document.createElement("div");
                imageContainer.className = "item-image-container";

                const image = document.createElement("img");
                image.className = "item-image";
                image.src = `/shared_shop/images/${item.imagePath}`;
                image.alt = item.name || "Product Image";
				
				
				image.addEventListener("click", ()=>{
					//console.log("image clicked", item.id);
					
				//	alert("image clicked "+ `${FixedUrl}items/detail/${item.id}`);
					window.location.href = `${FixedUrl}items/details/${item.id}`
					
				
					
				})

                // Case 2: file exist gardaina bhane (404), automatically fallback ma switch garne
                image.onerror = function() {
                    this.onerror = null; // ⚠️ Important — infinite loop rokna
                    this.src = "/shared_shop/images/common/no_image.jpg";
                };

                imageContainer.appendChild(image);
                itemCard.appendChild(imageContainer);

                // Item content
                const contentSection = document.createElement("div");
                contentSection.className = "item-content";

                // Item name
                const name = document.createElement("h3");
                name.className = "item-name";
                name.textContent = item.name;
                contentSection.appendChild(name);

                // Item price
                const price = document.createElement("div");
                price.className = "item-price";
                price.textContent = "Rs " + item.price;
                contentSection.appendChild(price);

                // Item description
                const description = document.createElement("p");
                description.className = "item-description";
                description.textContent = item.description;
                contentSection.appendChild(description);

                itemCard.appendChild(contentSection);

                // Button group
                const buttonGroup = document.createElement("div");
                buttonGroup.className = "item-buttons";

                // Favourite button
                const favouriteButton = document.createElement("button");
                favouriteButton.className = "btn btn-favourite";
                favouriteButton.innerHTML = "♡ Favourite";
                favouriteButton.setAttribute("data-item-id", item.id);

				 favouriteButton.addEventListener("click", () => toggleFavourite(item.id, favouriteButton));

                buttonGroup.appendChild(favouriteButton);

                // Add to Cart button
                const addToCartButton = document.createElement("button");
                addToCartButton.className = "btn btn-cart";
                addToCartButton.textContent = "Add to Cart";
                addToCartButton.setAttribute("data-item-id", item.id);

              addToCartButton.addEventListener("click", () => {
                    addToCartButton.value = item.id;
                    console.log("addToCartButton clicked", addToCartButton.value);
                    fetch(`${FixedUrl}items/cart/add/${addToCartButton.value}`)
                        .then((response) => {
                            if (response.status === 401) {
                                window.location.href = `${FixedUrl}login`
                            }
                            return response.json()
                        })
                        .then((data) => {
                            console.log("Added to cart:", data);
                        })
                        .catch((error) => {
                            console.error("Error adding to cart:", error);
                        });

                    // Visual feedback
                    const originalText = addToCartButton.textContent;
                    addToCartButton.textContent = "✓ Added!";
                    addToCartButton.classList.add("active");
                    setTimeout(() => {
                        addToCartButton.textContent = originalText;
                        addToCartButton.classList.remove("active");
                    }, 2000);
                });

                buttonGroup.appendChild(addToCartButton);

                itemCard.appendChild(buttonGroup);
                gridWrapper.appendChild(itemCard);
            });

            itemsContainer.appendChild(gridWrapper);
			markFavourites();
            console.log("Items loaded successfully", data);

            // Store loaded data to sessionStorage for restoring state
            sessionStorage.setItem("lastLoadedItems", JSON.stringify(data));
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}
// হার্ট ভরা (♥) বা ফাঁকা (♡) করা
function setFavouriteStyle(button, isFav) {
    button.classList.toggle("active", isFav);
    button.innerHTML = isFav ? "♥ Favourite" : "♡ Favourite";
}

// বাটন চাপলে যা হয়
function toggleFavourite(itemId, button) {
    console.log("[FAV] 1. button clicked, itemId =", itemId);

    fetch(`${FixedUrl}items/favorite/toggle/${itemId}`)
        .then((response) => {
            console.log("[FAV] 2. response status =", response.status);
            if (response.status === 401) {          // login নেই
                window.location.href = `${FixedUrl}login`;
                return null;
            }
            return response.json();
        })
        .then((data) => {
            if (!data) return;
            console.log("[FAV] 3. server JSON =", data);
            setFavouriteStyle(button, data.favourite);
        })
        .catch((error) => console.error("[FAV] error:", error));
}

// পেজ লোড হলে আগের favourite গুলোর হার্ট ভরে দেয়
function markFavourites() {
    fetch(`${FixedUrl}items/favorite/ids`)
        .then((response) => (response.status === 401 ? [] : response.json()))
        .then((ids) => {
            console.log("[FAV] favourite ids from server =", ids);
            ids.forEach((id) => {
                const btn = document.querySelector(`.btn-favourite[data-item-id="${id}"]`);
                if (btn) setFavouriteStyle(btn, true);
            });
        })
        .catch((error) => console.error("[FAV] markFavourites error:", error));
}

// Favourites পেজের তালিকা ভরা
function loadFavouritePage() {
    const box = document.getElementById("favouriteContainer");
    if (!box) return;   // অন্য পেজে কিছু করবে না

    fetch(`${FixedUrl}items/favorite/list`)
        .then((response) => {
            if (response.status === 401) {
                window.location.href = `${FixedUrl}login`;
                return null;
            }
            return response.json();
        })
        .then((items) => {
            if (!items) return;
            console.log("[FAV] favourites to display =", items);   // আগে console

            if (items.length === 0) {
                box.textContent = "No favourite items yet.";
                return;
            }

            const grid = document.createElement("div");
            grid.className = "items-grid";

            items.forEach((item) => {                              // তারপর পেজে দেখানো
                const card = document.createElement("div");
                card.className = "item-card";

                const imageBox = document.createElement("div");
                imageBox.className = "item-image-container";
                const img = document.createElement("img");
                img.className = "item-image";
                img.src = item.imagePath
                    ? `/shared_shop/images/${item.imagePath}`
                    : "/shared_shop/images/common/no_image.jpg";
                img.onerror = function () {
                    this.onerror = null;
                    this.src = "/shared_shop/images/common/no_image.jpg";
                };
                imageBox.appendChild(img);

                const content = document.createElement("div");
                content.className = "item-content";
                const name = document.createElement("h3");
                name.className = "item-name";
                name.textContent = item.name;
                const price = document.createElement("div");
                price.className = "item-price";
                price.textContent = "Rs " + item.price;
                content.appendChild(name);
                content.appendChild(price);

                const buttons = document.createElement("div");
                buttons.className = "item-buttons";
                const removeBtn = document.createElement("button");
                removeBtn.className = "btn btn-favourite active";
                removeBtn.textContent = "♥ Remove";
                removeBtn.addEventListener("click", () => {
                    fetch(`${FixedUrl}items/favorite/toggle/${item.id}`)
                        .then((r) => r.json())
                        .then((d) => {
                            console.log("[FAV] removed:", d);
                            card.remove();                         // কার্ডটা পেজ থেকে সরাও
                        });
                });
                buttons.appendChild(removeBtn);

                card.appendChild(imageBox);
                card.appendChild(content);
                card.appendChild(buttons);
                grid.appendChild(card);
            });

            box.appendChild(grid);
        })
        .catch((error) => console.error("[FAV] load page error:", error));
}
document.addEventListener("DOMContentLoaded", loadFavouritePage);
// Check if we're on login page
function isLoginPage() {
    return window.location.pathname.includes('/login');
}

// Auto-load items when the page is loaded (only if not on login page)
document.addEventListener("DOMContentLoaded", function() {
    if (!isLoginPage() && itemsContainer) {
        console.log("Page loaded, auto-loading items...");
        loadAllItems();
    }
});

// Event listener for back to top button
/*const backToTopButton = document.getElementById("backToTopButton");
if(backToTopButton) {
    console.log("Back to top button found");
}else {
    console.log("Back to top button not found");
}
if (backToTopButton) {
    backToTopButton.addEventListener("click", () => {
        console.log("Back to top button clicked");
        // Go back 1 step in history
        window.history.back();
        // If not on login page, reload items after navigation
        if (!isLoginPage() && itemsContainer) {
            setTimeout(() => {
                loadAllItems();
            }, 300);
        }
    });
}*/

// Handle logo click - navigate to home or load items based on current page
const logoElement = document.querySelector(".header-logo");
if (logoElement) {
    logoElement.addEventListener("click", () => {
        if (isLoginPage()) {
            // On login page, navigate to home
            console.log("Logo clicked on login page, navigating to home");
            window.location.href = FixedUrl;
        } else {
            // On other pages, load all items
            console.log("Logo clicked, loading all items");
            loadAllItems();
        }
    });
}

// Handle cart button
const CartButtonList = document.getElementById("CartButtonList");
if (CartButtonList) {
    CartButtonList.addEventListener("click", () => {
        fetch(`${FixedUrl}items/cart/list`)
            .then((response) => {
                if (response.status === 401) {
                    console.log("cart is empty");
                }
            })
            .then((data) => {
                window.location.href = `${FixedUrl}client/basket/list`;
                console.log("Cart data:", data.name);
            });
    });
}

//------------------favourite list button----------------------


/*const addFavouriteBtn = document.getElementById("favouriteListBtn");

addFavouriteBtn.addEventListener("click", () => {

    fetch(`${FixedUrl}items/favorite/list`)
        .then((response) => {
            if (response.status === 401) {
                console.log("cart is empty");
            }
        })
        .then((data) => {
            window.location.href = `${FixedUrl}favourite/list`;
            console.log("Cart data:", data.name);
        })
        .catch((error) => {
            console.error("Error fetching favourite items:", error);

        })
});*/

//------------------favourite list button-----------comes to end here -----------

//---------header search form----------------

const searchForm = document.querySelector(".search-form");
if (searchForm) {

    console.log("Search form found");
} else {
    console.log("Search form not found");
}

if (searchForm) searchForm.addEventListener("submit", (event) => {
	event.preventDefault(); // Prevent default form submission
    const searchInput = document.querySelector(".search-input");

    console.log("Search form submitted " + searchInput.value);

	fetch(`${FixedUrl}items/search?keyword=${encodeURIComponent(searchInput.value)}`)
	.then((response) => response.json())
	.then((data)=>{
		itemsContainer.innerHTML = ""; // Clear previous items

		            // Create a grid wrapper for items
		            const gridWrapper = document.createElement("div");
		            gridWrapper.className = "items-grid";

		            data.forEach((item) => {
		                // Create main item card
		                const itemCard = document.createElement("div");
		                itemCard.className = "item-card";

		                // Image container
		                const imageContainer = document.createElement("div");
		                imageContainer.className = "item-image-container";

		                const image = document.createElement("img");
		                image.className = "item-image";
		                image.src = `/shared_shop/images/${item.imagePath}`;
		                image.alt = item.name || "Product Image";

		                // Case 2: file exist gardaina bhane (404), automatically fallback ma switch garne
		                image.onerror = function() {
		                    this.onerror = null; // ⚠️ Important — infinite loop rokna
		                    this.src = "/shared_shop/images/common/no_image.jpg";
		                };

		                imageContainer.appendChild(image);
		                itemCard.appendChild(imageContainer);

		                // Item content
		                const contentSection = document.createElement("div");
		                contentSection.className = "item-content";

		                // Item name
		                const name = document.createElement("h3");
		                name.className = "item-name";
		                name.textContent = item.name;
		                contentSection.appendChild(name);

		                // Item price
		                const price = document.createElement("div");
		                price.className = "item-price";
		                price.textContent = "Rs " + item.price;
		                contentSection.appendChild(price);

		                // Item description
		                const description = document.createElement("p");
		                description.className = "item-description";
		                description.textContent = item.description;
		                contentSection.appendChild(description);

		                itemCard.appendChild(contentSection);

		                // Button group
		                const buttonGroup = document.createElement("div");
		                buttonGroup.className = "item-buttons";

		                // Favourite button
		                const favouriteButton = document.createElement("button");
		                favouriteButton.className = "btn btn-favourite";
		                favouriteButton.innerHTML = "♡ Favourite";
		                favouriteButton.setAttribute("data-item-id", item.id);

						favouriteButton.addEventListener("click", () => toggleFavourite(item.id, favouriteButton));

		                buttonGroup.appendChild(favouriteButton);

		                // Add to Cart button
		                const addToCartButton = document.createElement("button");
		                addToCartButton.className = "btn btn-cart";
		                addToCartButton.textContent = "Add to Cart";
		                addToCartButton.setAttribute("data-item-id", item.id);
						
						addToCartButton.addEventListener("click", ()=>{
							addToCart(item.id, addToCartButton);
						})

		                

		                buttonGroup.appendChild(addToCartButton);

		                itemCard.appendChild(buttonGroup);
		                gridWrapper.appendChild(itemCard);
		            });

		            itemsContainer.appendChild(gridWrapper);
					markFavourites();
		            console.log("Items loaded successfully", data);

		            // Store loaded data to sessionStorage for restoring state
		            sessionStorage.setItem("lastLoadedItems", JSON.stringify(data));
		        })
		        .catch((error) => {
		            console.error("Error fetching data:", error);
		        });

		
	});
	
	function addToCart(itemId, button) {
	    fetch(`${FixedUrl}items/cart/add/${itemId}`)
	        .then((response) => {
	            if (response.status === 401) {
	                window.location.href = `${FixedUrl}login`;
	                return null; // redirect hune bela json() parse nagarne
	            }
	            return response.json();
	        })
	        .then((data) => {
	            if (data) console.log("Added to cart:", data);
	        })
	        .catch((error) => {
	            console.error("Error adding to cart:", error);
	        });

	    // Visual feedback
	    if (button) {
	        const originalText = button.textContent;
	        button.textContent = "✓ Added!";
	        button.classList.add("active");
	        setTimeout(() => {
	            button.textContent = originalText;
	            button.classList.remove("active");
	        }, 5000);
	    }
	}
	
// ================== Notification Popup ==================

// Facebook-style red count bubble on the bell icon.
// Shows the given count, hides itself when count is 0.
function updateNotificationBadge(count) {
	const badge = document.getElementById("notificationBadge");
	if (!badge) return;
	const safeCount = Number(count) || 0;
	if (safeCount > 0) {
		badge.textContent = safeCount > 99 ? "99+" : safeCount;
		badge.style.display = "flex";
	} else {
		badge.textContent = "0";
		badge.style.display = "none";
	}
}

// "Read" the notifications -> badge disappears, just like Facebook does on open.
function clearNotificationBadge() {
	updateNotificationBadge(0);
}

// Build the <li><a>...</a></li> list of low-stock notifications, each linking to the item's detail page.
function renderNotificationList(data) {
	const notificationList = document.getElementById("notificationList");
	const notificationEmpty = document.querySelector(".notification-empty");

	if (!notificationList) {
		console.log("notificationList not found");
		return;
	}

	notificationList.innerHTML = "";

	if (!data || data.length === 0) {
		if (notificationEmpty) notificationEmpty.textContent = "No notifications yet.";
		return;
	}

	if (notificationEmpty) notificationEmpty.textContent = "";

	data.forEach((item) => {
		const li = document.createElement("li");
		li.className = "notification-item";

		const link = document.createElement("a");
		link.className = "notification-item-link";
		link.href = `${FixedUrl}admin/item/detail/${item.id}`;

		const title = document.createElement("p");
		title.className = "notification-item-title";
		title.textContent = `Item "${item.name}" has only ${item.stock} left in stock. Restock soon.`;
		link.appendChild(title);

		li.appendChild(link);
		notificationList.appendChild(li);
	});
}

// Fetches the current low-stock notifications and renders them in the popup list.
function fetchAndRenderNotifications() {
	fetch(`${FixedUrl}admin/notification/list`)
		.then((response) => {
			if (response.status === 401) {
				window.location.href = `${FixedUrl}login`;
				return null;
			}
			return response.json();
		})
		.then((data) => {
			if (!data) return;
//			console.log("notification data:", data);
			renderNotificationList(data);
		})
		.catch((error) => {
			console.error("Error fetching notification data:", error);
		});
}

// Lightweight check used on page load / polling to keep the red badge count up to date
// without opening the popup.
function checkNotificationBadgeCount() {
	const badge = document.getElementById("notificationBadge");
	if (!badge) return; // user has no notification bell (not admin/staff)

	fetch(`${FixedUrl}admin/notification/list`)
		.then((response) => (response.status === 401 ? [] : response.json()))
		.then((data) => updateNotificationBadge(data ? data.length : 0))
		.catch((error) => console.error("Error checking notification badge:", error));
}

function notification() {
	//console.log("notification() called");
	const popup = document.getElementById("notificationPopup");
	if (!popup) return;

	popup.classList.toggle("show");
	const isOpen = popup.classList.contains("show");
	console.log("Notification popup toggled. Current state:", isOpen);

	if (isOpen) {
		fetchAndRenderNotifications();
		// Like Facebook: opening the popup marks the notifications as seen -> badge clears.
		clearNotificationBadge();
	}
}

function closeNotification() {
	console.log("closeNotification() called");
	const popup = document.getElementById("notificationPopup");
	if (popup) {
		popup.classList.remove("show");
		//console.log("Notification popup closed");
	}
}

// Show the badge as soon as the page loads, and keep it fresh with periodic polling.
document.addEventListener("DOMContentLoaded", () => {
	checkNotificationBadgeCount();
	setInterval(checkNotificationBadgeCount, 60000); // re-check every 60s
});

// Close notification popup when clicking outside of it
document.addEventListener("click", function(event) {
	const popup = document.getElementById("notificationPopup");
	const container = document.querySelector(".notification-button-container");
	
	if (popup && container) {
		// Only close if click is NOT on the button or popup
		if (!container.contains(event.target) && popup.classList.contains("show")) {
			popup.classList.remove("show");
		//	console.log("Notification popup closed (clicked outside)");
		}
	}
});


	
			
		
		