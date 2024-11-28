import React, { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import "./App.css";

export default function App() {
    const [title, setTitle] = useState("");

    const updateTitle = (title) => {
        setTitle(title);
    };

    return (
        <div className="App">
            <Header />
            <div className="main-content">
                <h1 className="title">{title}</h1>
                <Outlet context={{ updateTitle }} />
            </div>
            <Footer />
        </div>
    );
}