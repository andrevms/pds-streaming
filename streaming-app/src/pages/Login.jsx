import React, { useEffect, useState } from "react";
import { useOutletContext, useNavigate } from "react-router-dom";
import "./Login.css";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (event) => {};
    const { updateTitle } = useOutletContext();

    useEffect(() => {
        document.title = `Login | Streaming`;
        updateTitle(`Login`);
    });

    return (
        <div className="outer-login-form">
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="input-box">
                    <label className="input-label" htmlFor="email">E-mail</label>
                    <input className="login-input" type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
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