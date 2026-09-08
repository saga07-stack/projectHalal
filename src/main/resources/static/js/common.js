/**
 * Common JavaScript
 * This file contains shared functionality across the entire application
 */

// Define functions first
const show = () => {
	fetch(/shared_shop/showAll/items)
	.then(response => response.json())
	.then(data => {
		console.log('Data received:', data);
	}
)};

// Add your common functions and event listeners here
document.addEventListener('DOMContentLoaded', function() {
    // Initialize common functionality when DOM is ready

	   	   // Handle showAll button
	   const showAll = document.getElementById('showAll');
	   
	   if(showAll) {
	       console.log('showAll button found');
	       showAll.addEventListener('click', show);
	   } else {
	       console.log('showAll button not found on this page');
	   }	
	
	   
});

