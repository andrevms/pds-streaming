import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // Importar o hook useNavigate
import { useOutletContext } from "react-router-dom";
import "./Login.css";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const { updateTitle } = useOutletContext();
    const navigate = useNavigate(); // Hook para navegação

    const loginUser = (data) => {
        localStorage.setItem("username", data.username);
        localStorage.setItem("jwtToken", data.jwtToken);
        localStorage.setItem("roles", JSON.stringify(data.roles));
    };

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
                loginUser(data);

                if (data.roles.includes("ROLE_PENDING_USER")) {
                    navigate("/subscription");
                }
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
        document.title = `Login | Blockburst`;
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
