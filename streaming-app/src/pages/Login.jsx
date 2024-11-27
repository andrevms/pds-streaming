import React, { useEffect, useState } from "react";
import { useOutletContext, useNavigate } from "react-router-dom";
import "./Login.css";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const { updateTitle } = useOutletContext();

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch("http://localhost:8080/auth/signin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                password
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                console.log("Success:", data);
            })
            .catch((error) => {
                console.error("Error:", error);
            })
            .finally(() => {
                setUsername("");
                setPassword("");
            });
    };

    useEffect(() => {
        document.title = `Login | Streaming`;
        updateTitle(`Login`);
    });

    return (
        <div className="outer-login-form">
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Nome de usuário</label>
                    <input className="login-input" type="text" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="password">Senha</label>
                    <input className="login-input" type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <div className="outer-submit-button">
                    <button className="submit-button" type="submit">Entrar</button>
                </div>
            </form>
        </div>
    );
}