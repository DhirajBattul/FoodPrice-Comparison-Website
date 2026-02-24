import React, { useState } from "react";
import axios from "axios";
import "./App.css"; // We will style this next

function App() {
  const [query, setQuery] = useState("");
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSearch = async () => {
    if (!query) return;
    setLoading(true);
    setError("");
    setData(null);

    try {
      // Calling your Comparison Service on Port 8080
      const response = await axios.get(`http://localhost:8080/compare/${query}`);
      setData(response.data);
    } catch (err) {
      setError("Service is down or item not found.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      <h1 className="title">🍔 Food Price War 🍕</h1>
      
      <div className="search-box">
        <input
          type="text"
          placeholder="Search for Biryani, Pizza..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onKeyPress={(e) => e.key === "Enter" && handleSearch()}
        />
        <button onClick={handleSearch}>Compare</button>
      </div>

      {loading && <p className="loading">Fetching best prices...</p>}
      {error && <p className="error">{error}</p>}

      {data && (
        <div className="result-container">
          {/* Summary Message */}
          <div className="winner-banner">
            {data.bestPriceSource === "Equal" 
              ? "It's a Tie! Buy from anywhere." 
              : `🏆 Winner: ${data.bestPriceSource} is cheaper!`}
          </div>

          <div className="cards-wrapper">
            {/* Zomato Card */}
            <ProductCard 
              product={data.zomatoResult} 
              source="Zomato" 
              isWinner={data.bestPriceSource === "Zomato"} 
              color="#cb202d" // Zomato Red
            />

            <div className="vs-badge">VS</div>

            {/* Swiggy Card */}
            <ProductCard 
              product={data.swiggyResult} 
              source="Swiggy" 
              isWinner={data.bestPriceSource === "Swiggy"} 
              color="#fc8019" // Swiggy Orange
            />
          </div>
        </div>
      )}
    </div>
  );
}

// Reusable Component for displaying a product
const ProductCard = ({ product, source, isWinner, color }) => {
  if (!product) return <div className="card error-card">{source} Not Available</div>;

  return (
    <div className={`card ${isWinner ? "winner-card" : ""}`} style={{ borderColor: color }}>
      <div className="card-header" style={{ backgroundColor: color }}>
        {source}
      </div>
      <img src={product.imageUrl || "https://via.placeholder.com/150"} alt={product.name} className="product-img" />
      <div className="card-body">
        <h3>{product.name}</h3>
        <p className="restaurant">🏠 {product.restaurantName}</p>
        <div className="price-tag">₹{product.price}</div>
        <p className="rating">⭐ {product.rating}</p>
      </div>
    </div>
  );
};

export default App;