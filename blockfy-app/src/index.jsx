import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import App from "./App.jsx";
import Catalog from "./pages/Catalog.jsx";
import LandingPage from "./pages/LandingPage.jsx";
import Listen from "./pages/Listen.jsx";
import Login from "./pages/Login.jsx";
import Signup from "./pages/Signup.jsx";
import Subscription from "./pages/Subscription.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />,
        children: [
            {
                path: "/",
                element: <LandingPage />
            },
            {
                path: "/catalog/:mediaType",
                element: <Catalog />
            },
            {
                path: "/signup",
                element: <Signup />,
            },
            {
                path: "/login",
                element: <Login />,
            },
            {
                path: "/subscription",
                element: <Subscription />
            },
            {
                path: "/listen/:mediaType/:mediaId",
                element: <Listen />
            },
        ]
    },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);
