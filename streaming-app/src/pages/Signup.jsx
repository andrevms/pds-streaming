import React, { useEffect, useState } from "react";
import { useOutletContext, useNavigate } from "react-router-dom";
import Logotype from "../components/Logotype";

export default function Signup() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const role = "ROLE_PENDING_USER";

    const { updateTitle } = useOutletContext();

    const registerUser = (data) => {
        localStorage.setItem("username", data.username);
        localStorage.setItem("roles", JSON.stringify(data.roles));
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch("http://localhost:8080/auth/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                email,
                password,
                firstName,
                lastName,
                role
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
                registerUser(data);
            })
            .catch((error) => {
                console.error("Error:", error);
            })
            .finally(() => {
                setUsername("");
                setEmail("");
                setPassword("");
                setRepeatedPassword("");
                setFirstName("");
                setLastName("");
            });
    };

    useEffect(() => {
        document.title = `Criar Conta | Blockburst`;
        updateTitle(`Criar Conta`);
    });

    return (
        <div className="outer-login-form">
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Nome de usuário</label>
                    <input className="signup-input" type="text" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">E-mail</label>
                    <input className="signup-input" type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="password">Senha</label>
                    <input className="signup-input" type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="password">Repetir senha</label>
                    <input className="signup-input" type="password" id="repeated-password" name="repeated-password" value={repeatedPassword} onChange={(e) => setRepeatedPassword(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Primeiro nome</label>
                    <input className="signup-input" type="text" id="first-name" name="first-name" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">Sobrenome</label>
                    <input className="signup-input" type="text" id="last-name" name="last-name" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
                </div>
                <div className="outer-submit-button">
                    <button className="submit-button" type="submit">Criar Conta</button>
                </div>
            </form>
        </div>
    );
}
