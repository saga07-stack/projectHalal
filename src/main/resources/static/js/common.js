console.log("js loaded");

const showAll = document.getElementById("showAll");
const itemsContainer = document.getElementById("container");
const FixedUrl ="http://localhost:55000/shared_shop/";
showAll.addEventListener("click", () => {
 
 
	showAll.disabled = true; // Disable the button to prevent multiple clicks
    
    
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
					image.onerror = function () {
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
						.then((response) =>{
							
						
							if(response.status === 401){
							window.location.href=`${FixedUrl}login`
								
							}
							response.json()
							 } )
						.then((data) =>{
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
				console.log("data", data);
			})
			.catch((error) => {
				console.error("Error fetching data:", error);
			})
			.finally(() => {
				showAll.disabled = false; // Flag OFF garne, aba naya fetch garna milcha
			});
        
	});
	
	const CartButtonList = document.getElementById("CartButtonList");
	CartButtonList.addEventListener("click", () => {
    
		fetch(`${FixedUrl}items/cart/list`)	
		.then((response) =>{
			if(response.status === 401){
				console.log("cart is empty");
			}
		})
		.then((data) =>{
			window.location.href=`${FixedUrl}client/basket/list`;
			console.log("Cart data:", data.name);
			
		});
		
		
	});

	
	
	
