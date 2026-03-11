import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

const API_BASE_URL = "http://localhost:8080";

function App() {
  const [view, setView] = useState("restaurants"); // 'restaurants', 'products', 'comparison', 'search'
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [products, setProducts] = useState([]);
  const [comparisonData, setComparisonData] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // Load restaurants on page load
  useEffect(() => {
    fetchRestaurants();
  }, []);

  const fetchRestaurants = async () => {
    setLoading(true);
    setError("");
    try {
      const response = await axios.get(`${API_BASE_URL}/restaurants`);
      setRestaurants(response.data);
      setView("restaurants");
    } catch (err) {
      setError("Failed to load restaurants. Make sure backend services are running.");
    } finally {
      setLoading(false);
    }
  };

  const handleRestaurantClick = async (restaurant) => {
    setLoading(true);
    setError("");
    setSelectedRestaurant(restaurant);
    try {
      const response = await axios.get(`${API_BASE_URL}/restaurants/${restaurant.id}/products`);
      setProducts(response.data);
      setView("products");
    } catch (err) {
      setError("Failed to load products.");
    } finally {
      setLoading(false);
    }
  };

  const handleProductClick = async (product) => {
    setLoading(true);
    setError("");
    try {
      const response = await axios.get(`${API_BASE_URL}/compare/product/${product.id}`);
      setComparisonData(response.data);
      setView("comparison");
    } catch (err) {
      setError("Failed to load price comparison.");
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchQuery.trim()) return;
    setLoading(true);
    setError("");
    try {
      const response = await axios.get(`${API_BASE_URL}/compare/${searchQuery}`);
      setSearchResults(response.data);
      setView("search");
    } catch (err) {
      setError("Search failed. Try a different term.");
    } finally {
      setLoading(false);
    }
  };

  const goBack = () => {
    if (view === "comparison") {
      setView("products");
      setComparisonData(null);
    } else if (view === "products") {
      setView("restaurants");
      setSelectedRestaurant(null);
      setProducts([]);
    } else if (view === "search") {
      setView("restaurants");
      setSearchResults([]);
      setSearchQuery("");
    }
  };

  return (
    <div className="app-container">
      <h1 className="title">🍔 Food Price War 🍕</h1>

      {/* Search Box - Always Visible */}
      <div className="search-box">
        <input
          type="text"
          placeholder="Search for Biryani, Pizza, Burger..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSearch()}
        />
        <button onClick={handleSearch}>Search</button>
      </div>

      {loading && <p className="loading">Loading...</p>}
      {error && <p className="error">{error}</p>}

      {/* Navigation Breadcrumb */}
      {(view !== "restaurants" || searchQuery) && (
        <button className="back-btn" onClick={goBack}>
          ← Back
        </button>
      )}

      {/* View: Restaurants */}
      {view === "restaurants" && !searchQuery && (
        <div className="restaurants-grid">
          <h2>Select a Restaurant</h2>
          <div className="cards-wrapper">
            {restaurants.map((restaurant) => (
              <RestaurantCard
                key={restaurant.id}
                restaurant={restaurant}
                onClick={() => handleRestaurantClick(restaurant)}
              />
            ))}
          </div>
        </div>
      )}

      {/* View: Products */}
      {view === "products" && selectedRestaurant && (
        <div className="products-grid">
          <h2>Products at {selectedRestaurant.commonName}</h2>
          <div className="cards-wrapper">
            {products.map((product) => (
              <ProductCard
                key={product.id}
                product={product}
                onClick={() => handleProductClick(product)}
              />
            ))}
          </div>
        </div>
      )}

      {/* View: Comparison */}
      {view === "comparison" && comparisonData && (
        <ComparisonView data={comparisonData} />
      )}

      {/* View: Search Results */}
      {view === "search" && (
        <div className="search-results">
          <h2>Search Results for "{searchQuery}"</h2>
          {searchResults.length === 0 ? (
            <p className="no-results">No products found.</p>
          ) : (
            <div className="search-results-list">
              {searchResults.map((item, index) => (
                <SearchResultCard
                  key={index}
                  data={item}
                  onClick={() => {
                    // For search results, we navigate directly to comparison
                    // by using the product info
                    setComparisonData(item);
                    setView("comparison");
                  }}
                />
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

// Restaurant Card Component
const RestaurantCard = ({ restaurant, onClick }) => {
  return (
    <div className="card restaurant-card" onClick={onClick}>
      <div className="card-header restaurant-header">
        🍽️ {restaurant.commonName}
      </div>
      <div className="card-body">
        <p className="location">📍 {restaurant.commonLocation}</p>
        <p className="platforms">
          <span className="zomato-badge">Zomato</span>
          <span className="swiggy-badge">Swiggy</span>
        </p>
      </div>
    </div>
  );
};

// Product Card Component
const ProductCard = ({ product, onClick }) => {
  return (
    <div className="card product-card" onClick={onClick}>
      {product.commonImageUrl && (
        <img src={product.commonImageUrl} alt={product.commonName} className="product-img" />
      )}
      <div className="card-body">
        <h3>{product.commonName}</h3>
        <p className="category">📂 {product.category}</p>
        <button className="compare-btn">Compare Price</button>
      </div>
    </div>
  );
};

// Search Result Card Component
const SearchResultCard = ({ data, onClick }) => {
  const isEqual = data.bestPriceSource === "Equal";
  const winner = data.bestPriceSource;
  
  return (
    <div className="card search-result-card" onClick={onClick}>
      <div className="search-result-header">
        {isEqual ? (
          <span className="tie-badge">🤝 It's a Tie!</span>
        ) : (
          <span className={`winner-badge ${winner.toLowerCase()}`}>
            🏆 Winner: {winner}
          </span>
        )}
        {data.message && <p className="message">{data.message}</p>}
      </div>
      
      <div className="cards-wrapper">
        {/* Zomato Result */}
        <div className="mini-card zomato">
          <div className="mini-card-header">Zomato</div>
          {data.zomatoResult && (
            <>
              <p className="product-name">{data.zomatoResult.name}</p>
              <p className="restaurant-name">🏠 {data.zomatoResult.restaurantName}</p>
              <p className="price">₹{data.zomatoResult.price}</p>
            </>
          )}
        </div>

        <div className="vs-badge">VS</div>

        {/* Swiggy Result */}
        <div className="mini-card swiggy">
          <div className="mini-card-header">Swiggy</div>
          {data.swiggyResult && (
            <>
              <p className="product-name">{data.swiggyResult.name}</p>
              <p className="restaurant-name">🏠 {data.swiggyResult.restaurantName}</p>
              <p className="price">₹{data.swiggyResult.price}</p>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

// Comparison View Component
const ComparisonView = ({ data }) => {
  const isEqual = data.bestPriceSource === "Equal";
  
  return (
    <div className="result-container">
      <div className="winner-banner">
        {isEqual 
          ? "🤝 It's a Tie! Buy from anywhere." 
          : `🏆 Winner: ${data.bestPriceSource} is cheaper!`}
      </div>
      
      {data.message && <p className="price-message">{data.message}</p>}

      <div className="cards-wrapper">
        {/* Zomato Card */}
        <ProductComparisonCard 
          product={data.zomatoResult} 
          source="Zomato" 
          isWinner={data.bestPriceSource === "Zomato"} 
          color="#cb202d"
        />

        <div className="vs-badge">VS</div>

        {/* Swiggy Card */}
        <ProductComparisonCard 
          product={data.swiggyResult} 
          source="Swiggy" 
          isWinner={data.bestPriceSource === "Swiggy"} 
          color="#fc8019"
        />
      </div>
    </div>
  );
};

// Product Comparison Card Component
const ProductComparisonCard = ({ product, source, isWinner, color }) => {
  if (!product) return (
    <div className="card error-card" style={{ borderColor: color }}>
      <div className="card-header" style={{ backgroundColor: color }}>
        {source}
      </div>
      <div className="card-body">
        <p>Not Available</p>
      </div>
    </div>
  );

  return (
    <div className={`card ${isWinner ? "winner-card" : ""}`} style={{ borderColor: color }}>
      <div className="card-header" style={{ backgroundColor: color }}>
        {source}
      </div>
      {product.imageUrl && (
        <img src={product.imageUrl} alt={product.name} className="product-img" />
      )}
      <div className="card-body">
        <h3>{product.name}</h3>
        {product.description && <p className="description">{product.description}</p>}
        <p className="restaurant">🏠 {product.restaurantName}</p>
        <div className="price-tag">₹{product.price}</div>
        {product.rating && <p className="rating">⭐ {product.rating}</p>}
        {product.category && <p className="category">📂 {product.category}</p>}
      </div>
    </div>
  );
};

export default App;

