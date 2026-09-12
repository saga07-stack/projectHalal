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
				data.forEach((item) => {
				    const div = document.createElement("div");

				    const image = document.createElement("img");

				 image.src = `/shared_shop/images/${item.image}`;
				    // Case 2: file exist gardaina bhane (404), automatically fallback ma switch garne
				    image.onerror = function () {
				        this.onerror = null; // ⚠️ Important — infinite loop rokna (niche explain garchu)
				        this.src = "/shared_shop/images/common/no_image.jpg";
				    };

				    div.appendChild(image);

				    const name = document.createElement("h3");
				    name.textContent = item.name;
				    div.appendChild(name);

				    const price = document.createElement("p");
				    price.textContent = item.price;
				    div.appendChild(price);

				    const description = document.createElement("p");
				    description.textContent = item.description;
				    div.appendChild(description);

				    itemsContainer.appendChild(div);
				
				
				});			
				console.log("data", data);
			})
			.catch((error) => {
				console.error("Error fetching data:", error);
			})
			.finally(() => {
				showAll.disabled = false; // Flag OFF garne, aba naya fetch garna milcha
			});
		
	});
	
	
	
