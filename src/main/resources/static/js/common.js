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

                favouriteButton.addEventListener("click", () => {
                    favouriteButton.value = item.id;
                    console.log("favouriteButton clicked", favouriteButton.value);
                    // Toggle favorite styling
                    if (favouriteButton.classList.contains("active")) {
                        favouriteButton.classList.remove("active");
                        favouriteButton.innerHTML = "♡ Favourite";
                    } else {
                        favouriteButton.classList.add("active");
                        favouriteButton.innerHTML = "♥ Favourite";
                    }
                });

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
            console.log("Items loaded successfully", data);

            // Store loaded data to sessionStorage for restoring state
            sessionStorage.setItem("lastLoadedItems", JSON.stringify(data));
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}

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


const favouriteBtn = document.getElementById("favouriteBtn");

favouriteBtn.addEventListener("click", () => {

    alert("buttongot clicked");
    fetch(`${FixedUrl}items/favorite/list`)
        .then((response) => {
            if (response.status === 401) {
                console.log("cart is empty");
            }
        })
        .then((data) => {
            window.location.href = `${FixedUrl}client/basket/list`;
            console.log("Cart data:", data.name);
        })
        .catch((error) => {
            console.error("Error fetching favourite items:", error);

        })
});

//------------------favourite list button-----------comes to end here -----------

//---------header search form----------------

const searchForm = document.querySelector(".search-form");
if (searchForm) {

    console.log("Search form found");
} else {
    console.log("Search form not found");
}

searchForm.addEventListener("submit", (event) => {
    const searchInput = document.querySelector(".search-input");

    console.log("Search form submitted " + searchInput.value);
    fetch(`${FixedUrl}items/search${searchInput.value}`)
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

		                favouriteButton.addEventListener("click", () => {
		                    favouriteButton.value = item.id;
		                    console.log("favouriteButton clicked", favouriteButton.value);
		                    // Toggle favorite styling
		                    if (favouriteButton.classList.contains("active")) {
		                        favouriteButton.classList.remove("active");
		                        favouriteButton.innerHTML = "♡ Favourite";
		                    } else {
		                        favouriteButton.classList.add("active");
		                        favouriteButton.innerHTML = "♥ Favourite";
		                    }
		                });

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
		            console.log("Items loaded successfully", data);

		            // Store loaded data to sessionStorage for restoring state
		            sessionStorage.setItem("lastLoadedItems", JSON.stringify(data));
		        })
		        .catch((error) => {
		            console.error("Error fetching data:", error);
		        });

		
	});




