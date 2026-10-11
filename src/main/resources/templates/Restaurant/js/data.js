// === EDIT THIS FILE: restaurant details + menu items ===
const SITE = {
  name: "Himal Kitchen",
  tagline: "Nepali home cooking, made fresh every day.",
  address: "Your street address, City",
  phone: "+977-000-000000",
  hours: [["Sunday – Friday", "10:00 – 21:00"], ["Saturday", "11:00 – 22:00"]],
  mapEmbed: "", // paste the src URL from Google Maps > Share > Embed a map
  accessKey: "YOUR_WEB3FORMS_ACCESS_KEY" // free key from web3forms.com, using the restaurant Gmail
};
const CATEGORIES = ["Mains", "Momo", "Snacks", "Drinks"];
// id is also the photo name: images/<id>.jpg (optional)
const ITEMS = [
  {
    id: "Dalbhat", name: "Dal Bhat Set", cat: "Mains", price: 450, veg: true, spice: 1, featured: true,
    desc: "Steamed rice with lentil soup, seasonal vegetables, pickle and papad.",
    ingredients: ["Rice", "Lentils", "Seasonal vegetables", "Achar pickle", "Papad"], allergens: ["None common"]
  },
  {
    id: "chicken-momos", name: "Chicken Momo", cat: "Momo", price: 280, veg: false, spice: 2, featured: true,
    desc: "Hand-folded steamed dumplings served with tomato-sesame chutney.",
    ingredients: ["Chicken", "Wheat dough", "Onion", "Ginger", "Tomato achar"], allergens: ["Gluten", "Sesame"]
  },
  {
    id: "vegitableMomo", name: "Veg Momo", cat: "Momo", price: 240, veg: true, spice: 1, featured: true,
    desc: "Cabbage, carrot and paneer dumplings, steamed or fried.",
    ingredients: ["Cabbage", "Carrot", "Paneer", "Wheat dough"], allergens: ["Gluten", "Dairy", "Sesame"]
  },
  {
    id: "thukpa", name: "Chicken Thukpa", cat: "Mains", price: 350, veg: false, spice: 2, featured: false,
    desc: "Warming Himalayan noodle soup with chicken and vegetables.",
    ingredients: ["Noodles", "Chicken", "Vegetables", "Spices"], allergens: ["Gluten"]
  },
  {
    id: "sekuwa", name: "Chicken Sekuwa", cat: "Snacks", price: 400, veg: false, spice: 3, featured: false,
    desc: "Charcoal-grilled marinated chicken with spiced onion salad.",
    ingredients: ["Chicken", "Timur pepper", "Garlic", "Ginger"], allergens: ["None common"]
  },
  {
    id: "chatamari", name: "Chatamari", cat: "Snacks", price: 300, veg: true, spice: 1, featured: false,
    desc: "Newari rice-flour crepe with a savoury topping of your choice.",
    ingredients: ["Rice flour", "Onion", "Tomato", "Egg (optional)"], allergens: ["Egg (optional)"]
  },
  {
    id: "masala-tea", name: "Masala Chiya", cat: "Drinks", price: 80, veg: true, spice: 0, featured: false,
    desc: "Milk tea simmered with cardamom, ginger and cloves.",
    ingredients: ["Tea", "Milk", "Cardamom", "Ginger", "Cloves"], allergens: ["Dairy"]
  },
  {
    id: "lassi", name: "Sweet Lassi", cat: "Drinks", price: 150, veg: true, spice: 0, featured: false,
    desc: "Chilled yogurt drink, lightly sweetened.",
    ingredients: ["Yogurt", "Sugar", "Cardamom"], allergens: ["Dairy"]
  }
  
];
