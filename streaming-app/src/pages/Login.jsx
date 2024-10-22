import React, { useEffect, useState } from "react";
import "./Login.css";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (event) => {};

    return (
        <div className="outer-login-form">
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="input-box">
                    <label htmlFor="email">E-mail</label>
                    <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="input-box">
                    <label htmlFor="password">Senha</label>
                    <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <div className="outer-submit-button">
                    <button className="submit-button" type="submit">Entrar</button>
                </div>
            </form>
        </div>
    );
}