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
const CATEGORIES = ["Mains", "Momo", "Snacks", "Drinks","Alcohol","Desserts"];
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
  },
  
  // ---------- MAINS (12) ----------
  {
    id: "chicken-bhat-set", name: "Chicken Bhat Set", cat: "Mains", price: 520, veg: false, spice: 2, featured: false,
    desc: "Rice with dal, chicken curry, saag, pickle and papad.",
    ingredients: ["Rice", "Lentils", "Chicken", "Spinach", "Achar pickle"], allergens: ["None common"]
  },
  {
    id: "mutton-bhat-set", name: "Mutton Bhat Set", cat: "Mains", price: 650, veg: false, spice: 2, featured: false,
    desc: "Rice with dal, slow-cooked mutton curry, saag and pickle.",
    ingredients: ["Rice", "Lentils", "Mutton", "Spinach", "Achar pickle"], allergens: ["None common"]
  },
  {
    id: "khasi-ko-masu", name: "Khasi ko Masu", cat: "Mains", price: 700, veg: false, spice: 3, featured: false,
    desc: "Traditional goat curry cooked on the bone with whole spices.",
    ingredients: ["Goat meat", "Onion", "Garlic", "Ginger", "Timur pepper"], allergens: ["None common"]
  },
  {
    id: "dhido-gundruk", name: "Dhido with Gundruk", cat: "Mains", price: 400, veg: true, spice: 1, featured: false,
    desc: "Buckwheat porridge served with fermented greens soup and ghee.",
    ingredients: ["Buckwheat flour", "Gundruk", "Ghee", "Garlic"], allergens: ["Dairy"]
  },
  {
    id: "chicken-chowmein", name: "Chicken Chowmein", cat: "Mains", price: 300, veg: false, spice: 2, featured: false,
    desc: "Stir-fried noodles with chicken, cabbage and carrot.",
    ingredients: ["Noodles", "Chicken", "Cabbage", "Carrot", "Soy sauce"], allergens: ["Gluten", "Soy"]
  },
  {
    id: "veg-chowmein", name: "Veg Chowmein", cat: "Mains", price: 250, veg: true, spice: 1, featured: false,
    desc: "Wok-tossed noodles with fresh seasonal vegetables.",
    ingredients: ["Noodles", "Cabbage", "Carrot", "Capsicum", "Soy sauce"], allergens: ["Gluten", "Soy"]
  },
  {
    id: "thenthuk", name: "Thenthuk", cat: "Mains", price: 380, veg: false, spice: 2, featured: false,
    desc: "Hand-pulled noodle soup from the Tibetan-Nepali tradition.",
    ingredients: ["Wheat dough", "Chicken", "Tomato", "Greens", "Spices"], allergens: ["Gluten"]
  },
  {
    id: "chicken-fried-rice", name: "Chicken Fried Rice", cat: "Mains", price: 320, veg: false, spice: 1, featured: false,
    desc: "Wok-fried rice with egg, chicken and spring onion.",
    ingredients: ["Rice", "Chicken", "Egg", "Spring onion", "Soy sauce"], allergens: ["Egg", "Soy"]
  },
  {
    id: "veg-fried-rice", name: "Veg Fried Rice", cat: "Mains", price: 270, veg: true, spice: 1, featured: false,
    desc: "Fried rice with peas, carrot, beans and spring onion.",
    ingredients: ["Rice", "Peas", "Carrot", "Beans", "Soy sauce"], allergens: ["Soy"]
  },
  {
    id: "aloo-tama-bodi", name: "Aloo Tama Bodi", cat: "Mains", price: 330, veg: true, spice: 1, featured: false,
    desc: "Potato, bamboo shoot and black-eyed bean curry, Newari style.",
    ingredients: ["Potato", "Bamboo shoot", "Black-eyed beans", "Turmeric", "Cumin"], allergens: ["None common"]
  },
  {
    id: "paneer-butter-masala", name: "Paneer Butter Masala", cat: "Mains", price: 420, veg: true, spice: 1, featured: false,
    desc: "Soft paneer in a rich tomato-butter gravy, served with naan.",
    ingredients: ["Paneer", "Tomato", "Butter", "Cream", "Spices"], allergens: ["Dairy", "Gluten"]
  },
  {
    id: "buff-thukpa", name: "Buff Thukpa", cat: "Mains", price: 330, veg: false, spice: 2, featured: false,
    desc: "Hearty noodle soup with buffalo meat and vegetables.",
    ingredients: ["Noodles", "Buffalo meat", "Vegetables", "Spices"], allergens: ["Gluten"]
  },

  // ---------- MOMO (8) ----------
  {
    id: "buff-momo", name: "Buff Momo", cat: "Momo", price: 250, veg: false, spice: 2, featured: false,
    desc: "Classic Kathmandu-style buffalo momo with spicy chutney.",
    ingredients: ["Buffalo meat", "Wheat dough", "Onion", "Ginger", "Tomato achar"], allergens: ["Gluten", "Sesame"]
  },
  {
    id: "jhol-momo", name: "Jhol Momo", cat: "Momo", price: 320, veg: false, spice: 2, featured: true,
    desc: "Steamed momo served in a warm, tangy sesame-tomato soup.",
    ingredients: ["Chicken", "Wheat dough", "Sesame", "Tomato", "Timur"], allergens: ["Gluten", "Sesame"]
  },
  {
    id: "fried-chicken-momo", name: "Fried Chicken Momo", cat: "Momo", price: 300, veg: false, spice: 2, featured: false,
    desc: "Crispy golden fried dumplings with a spicy dip.",
    ingredients: ["Chicken", "Wheat dough", "Onion", "Garlic"], allergens: ["Gluten", "Sesame"]
  },
  {
    id: "chilli-momo", name: "Chilli Momo", cat: "Momo", price: 340, veg: false, spice: 3, featured: false,
    desc: "Fried momo tossed in a sweet-spicy chilli sauce with capsicum.",
    ingredients: ["Chicken", "Wheat dough", "Capsicum", "Chilli sauce", "Onion"], allergens: ["Gluten", "Soy"]
  },
  {
    id: "kothey-momo", name: "Kothey Momo", cat: "Momo", price: 310, veg: false, spice: 2, featured: false,
    desc: "Half-steamed, half-pan-fried crescent dumplings.",
    ingredients: ["Chicken", "Wheat dough", "Garlic", "Spring onion"], allergens: ["Gluten", "Sesame"]
  },
  {
    id: "paneer-momo", name: "Paneer Momo", cat: "Momo", price: 270, veg: true, spice: 1, featured: false,
    desc: "Steamed dumplings stuffed with spiced paneer and herbs.",
    ingredients: ["Paneer", "Wheat dough", "Coriander", "Ginger"], allergens: ["Gluten", "Dairy", "Sesame"]
  },
  {
    id: "cheese-momo", name: "Cheese Corn Momo", cat: "Momo", price: 290, veg: true, spice: 0, featured: false,
    desc: "Creamy cheese and sweet corn filling, mild and kid friendly.",
    ingredients: ["Cheese", "Sweet corn", "Wheat dough", "Black pepper"], allergens: ["Gluten", "Dairy"]
  },
  {
    id: "tandoori-momo", name: "Tandoori Momo", cat: "Momo", price: 360, veg: false, spice: 3, featured: false,
    desc: "Momo marinated in yogurt and spices, grilled in the tandoor.",
    ingredients: ["Chicken", "Wheat dough", "Yogurt", "Tandoori masala"], allergens: ["Gluten", "Dairy"]
  },

  // ---------- SNACKS (12) ----------
  {
    id: "buff-sukuti", name: "Buff Sukuti", cat: "Snacks", price: 380, veg: false, spice: 3, featured: false,
    desc: "Dried, spiced buffalo meat pan-tossed with onion and chilli.",
    ingredients: ["Dried buffalo meat", "Onion", "Green chilli", "Timur", "Mustard oil"], allergens: ["None common"]
  },
  {
    id: "buff-choila", name: "Buff Choila", cat: "Snacks", price: 360, veg: false, spice: 3, featured: true,
    desc: "Newari grilled buffalo meat marinated in mustard oil and spices.",
    ingredients: ["Buffalo meat", "Mustard oil", "Ginger", "Garlic", "Fenugreek"], allergens: ["Mustard"]
  },
  {
    id: "buff-sekuwa", name: "Buff Sekuwa", cat: "Snacks", price: 380, veg: false, spice: 3, featured: false,
    desc: "Charcoal-grilled buffalo skewers with Himalayan spices.",
    ingredients: ["Buffalo meat", "Timur pepper", "Garlic", "Ginger"], allergens: ["None common"]
  },
  {
    id: "chicken-choila", name: "Chicken Choila", cat: "Snacks", price: 340, veg: false, spice: 3, featured: false,
    desc: "Smoky grilled chicken tossed in a spicy Newari marinade.",
    ingredients: ["Chicken", "Mustard oil", "Green chilli", "Ginger", "Garlic"], allergens: ["Mustard"]
  },
  {
    id: "wai-wai-sadeko", name: "Wai Wai Sadeko", cat: "Snacks", price: 150, veg: true, spice: 3, featured: false,
    desc: "Crunchy instant noodles mixed with onion, tomato, chilli and lime.",
    ingredients: ["Wai Wai noodles", "Onion", "Tomato", "Green chilli", "Lime"], allergens: ["Gluten"]
  },
  {
    id: "chicken-pakoda", name: "Chicken Pakoda", cat: "Snacks", price: 320, veg: false, spice: 2, featured: false,
    desc: "Crispy gram-flour battered chicken pieces with mint chutney.",
    ingredients: ["Chicken", "Gram flour", "Cumin", "Chilli powder"], allergens: ["None common"]
  },
  {
    id: "veg-pakoda", name: "Veg Pakoda", cat: "Snacks", price: 200, veg: true, spice: 1, featured: false,
    desc: "Onion, potato and spinach fritters, fried until golden.",
    ingredients: ["Onion", "Potato", "Spinach", "Gram flour"], allergens: ["None common"]
  },
  {
    id: "samosa", name: "Samosa Chaat", cat: "Snacks", price: 180, veg: true, spice: 2, featured: false,
    desc: "Crushed samosa topped with chickpeas, yogurt and tamarind sauce.",
    ingredients: ["Potato", "Chickpeas", "Yogurt", "Tamarind", "Wheat dough"], allergens: ["Gluten", "Dairy"]
  },
  {
    id: "panipuri", name: "Pani Puri", cat: "Snacks", price: 120, veg: true, spice: 2, featured: false,
    desc: "Crisp hollow shells filled with spiced water, chickpeas and potato.",
    ingredients: ["Semolina puri", "Chickpeas", "Potato", "Mint water"], allergens: ["Gluten"]
  },
  {
    id: "french-fries", name: "French Fries", cat: "Snacks", price: 200, veg: true, spice: 0, featured: false,
    desc: "Golden crispy potato fries with ketchup.",
    ingredients: ["Potato", "Salt", "Oil"], allergens: ["None common"]
  },
  {
    id: "aloo-achar", name: "Aloo ko Achar", cat: "Snacks", price: 160, veg: true, spice: 2, featured: false,
    desc: "Boiled potato salad with sesame, lemon and Himalayan spices.",
    ingredients: ["Potato", "Sesame", "Lemon", "Green chilli", "Timur"], allergens: ["Sesame"]
  },
  {
    id: "paneer-chilli", name: "Paneer Chilli", cat: "Snacks", price: 350, veg: true, spice: 3, featured: false,
    desc: "Crispy paneer cubes tossed with capsicum and spicy sauce.",
    ingredients: ["Paneer", "Capsicum", "Onion", "Chilli sauce", "Soy sauce"], allergens: ["Dairy", "Soy"]
  },

  // ---------- DRINKS (8) ----------
  {
    id: "black-tea", name: "Black Tea", cat: "Drinks", price: 60, veg: true, spice: 0, featured: false,
    desc: "Strong Ilam black tea, served hot.",
    ingredients: ["Ilam tea leaves", "Water", "Sugar (optional)"], allergens: ["None common"]
  },
  {
    id: "lemon-tea", name: "Lemon Honey Tea", cat: "Drinks", price: 100, veg: true, spice: 0, featured: false,
    desc: "Light tea with fresh lemon and a spoon of honey.",
    ingredients: ["Tea", "Lemon", "Honey"], allergens: ["None common"]
  },
  {
    id: "butter-tea", name: "Butter Tea (Cha)", cat: "Drinks", price: 140, veg: true, spice: 0, featured: false,
    desc: "Salty Himalayan tea with yak butter, a mountain favourite.",
    ingredients: ["Tea", "Butter", "Salt", "Milk"], allergens: ["Dairy"]
  },
  {
    id: "milk-coffee", name: "Milk Coffee", cat: "Drinks", price: 130, veg: true, spice: 0, featured: false,
    desc: "Freshly brewed Nepali coffee with hot milk.",
    ingredients: ["Coffee", "Milk", "Sugar"], allergens: ["Dairy"]
  },
  {
    id: "mango-lassi", name: "Mango Lassi", cat: "Drinks", price: 180, veg: true, spice: 0, featured: false,
    desc: "Thick chilled yogurt blended with ripe mango.",
    ingredients: ["Yogurt", "Mango", "Sugar", "Cardamom"], allergens: ["Dairy"]
  },
  {
    id: "fresh-lime-soda", name: "Fresh Lime Soda", cat: "Drinks", price: 120, veg: true, spice: 0, featured: false,
    desc: "Sparkling soda with fresh lime, sweet or salted.",
    ingredients: ["Lime", "Soda water", "Sugar or salt"], allergens: ["None common"]
  },
  {
    id: "masala-coke", name: "Jeera Masala Soda", cat: "Drinks", price: 130, veg: true, spice: 1, featured: false,
    desc: "Fizzy soda with roasted cumin, black salt and mint.",
    ingredients: ["Soda water", "Cumin", "Black salt", "Mint"], allergens: ["None common"]
  },
  {
    id: "hot-chocolate", name: "Hot Chocolate", cat: "Drinks", price: 200, veg: true, spice: 0, featured: false,
    desc: "Rich, creamy hot chocolate, perfect for cold evenings.",
    ingredients: ["Milk", "Cocoa", "Sugar"], allergens: ["Dairy"]
  },

  // ---------- ALCOHOL (5) ----------
  {
    id: "gorkha-beer", name: "Gorkha Beer", cat: "Alcohol", price: 450, veg: true, spice: 0, featured: false,
    desc: "Popular Nepali lager, 650ml bottle. Must be 18+.",
    ingredients: ["Barley malt", "Hops", "Water", "Yeast"], allergens: ["Gluten"]
  },
  {
    id: "nepal-ice", name: "Nepal Ice Beer", cat: "Alcohol", price: 480, veg: true, spice: 0, featured: false,
    desc: "Crisp, smooth Nepali lager, 650ml bottle. Must be 18+.",
    ingredients: ["Barley malt", "Hops", "Water", "Yeast"], allergens: ["Gluten"]
  },
  {
    id: "tongba", name: "Tongba", cat: "Alcohol", price: 400, veg: true, spice: 0, featured: true,
    desc: "Warm fermented millet drink sipped through a bamboo straw. 18+.",
    ingredients: ["Fermented millet", "Hot water"], allergens: ["None common"]
  },
  {
    id: "raksi", name: "Ghar ko Raksi", cat: "Alcohol", price: 300, veg: true, spice: 0, featured: false,
    desc: "Traditional Nepali distilled spirit, served in a small glass. 18+.",
    ingredients: ["Millet or rice", "Water"], allergens: ["None common"]
  },
  {
    id: "khukuri-rum", name: "Khukuri Rum", cat: "Alcohol", price: 350, veg: true, spice: 0, featured: false,
    desc: "Nepali dark rum, served neat, on the rocks or with cola. 18+.",
    ingredients: ["Sugarcane spirit", "Caramel"], allergens: ["None common"]
  },

  // ---------- DESSERTS (5) ----------
  {
    id: "yomari", name: "Yomari", cat: "Desserts", price: 200, veg: true, spice: 0, featured: true,
    desc: "Steamed Newari rice-flour dumpling filled with molasses and sesame.",
    ingredients: ["Rice flour", "Chaku (molasses)", "Sesame", "Khoya"], allergens: ["Sesame", "Dairy"]
  },
  {
    id: "juju-dhau", name: "Juju Dhau", cat: "Desserts", price: 180, veg: true, spice: 0, featured: false,
    desc: "King of yogurts from Bhaktapur, thick, creamy and lightly sweet.",
    ingredients: ["Buffalo milk", "Sugar", "Cardamom"], allergens: ["Dairy"]
  },
  {
    id: "kheer", name: "Rice Kheer", cat: "Desserts", price: 170, veg: true, spice: 0, featured: false,
    desc: "Slow-cooked rice pudding with cardamom, nuts and raisins.",
    ingredients: ["Rice", "Milk", "Sugar", "Cardamom", "Cashew", "Raisins"], allergens: ["Dairy", "Nuts"]
  },
  {
    id: "gulab-jamun", name: "Gulab Jamun", cat: "Desserts", price: 150, veg: true, spice: 0, featured: false,
    desc: "Soft milk dumplings soaked in warm rose-cardamom syrup.",
    ingredients: ["Khoya", "Flour", "Sugar syrup", "Rose water"], allergens: ["Dairy", "Gluten"]
  },
  {
    id: "sel-roti", name: "Sel Roti", cat: "Desserts", price: 120, veg: true, spice: 0, featured: false,
    desc: "Ring-shaped sweet rice bread fried until crisp outside, soft inside.",
    ingredients: ["Rice flour", "Sugar", "Ghee", "Banana", "Cardamom"], allergens: ["Dairy"]
  }
  
];
